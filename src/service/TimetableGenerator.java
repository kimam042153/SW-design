package service;

import model.Course;
import model.Preference;
import model.TimetableResult;

import java.util.*;
import java.util.stream.Collectors;

public class TimetableGenerator {

    public List<TimetableResult> generate(Preference pref) {
        List<Course> allCourses = DataStore.getCourses();

        // 과목명별 분반 그룹화
        Map<String, List<Course>> groups = new LinkedHashMap<>();
        for (String name : pref.getCourseNames()) {
            List<Course> sections = allCourses.stream()
                    .filter(c -> c.getName().equals(name.trim()))
                    .collect(Collectors.toList());
            if (!sections.isEmpty()) groups.put(name, sections);
        }

        if (groups.isEmpty()) return Collections.emptyList();

        // 카테시안 곱 (전체 분반 조합)
        List<List<Course>> combos = new ArrayList<>();
        combos.add(new ArrayList<>());
        for (List<Course> sections : groups.values()) {
            List<List<Course>> next = new ArrayList<>();
            for (List<Course> combo : combos) {
                for (Course c : sections) {
                    List<Course> newCombo = new ArrayList<>(combo);
                    newCombo.add(c);
                    next.add(newCombo);
                }
            }
            combos = next;
        }

        // 시간 충돌 필터링 후 점수 계산, 상위 5개 반환
        return combos.stream()
                .filter(this::noConflict)
                .map(c -> score(c, pref))
                .sorted(Comparator.comparingDouble(TimetableResult::getPriorityScore).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private boolean noConflict(List<Course> courses) {
        for (int i = 0; i < courses.size(); i++)
            for (int j = i + 1; j < courses.size(); j++) {
                Course a = courses.get(i), b = courses.get(j);
                if (a.getDayOfWeek().equals(b.getDayOfWeek()) &&
                        a.getStartHour() < b.getEndHour() &&
                        a.getEndHour() > b.getStartHour()) return false;
            }
        return true;
    }

    private TimetableResult score(List<Course> courses, Preference pref) {
        int totalCredits = courses.stream().mapToInt(Course::getCredit).sum();
        double tw = pref.getCreditWeight() + pref.getProfessorWeight() + pref.getFreeDayWeight();
        if (tw == 0) tw = 3.0;

        // 학점 점수
        double cs = pref.getTargetCredits() > 0
                ? Math.max(0, 1.0 - (double) Math.abs(totalCredits - pref.getTargetCredits()) / pref.getTargetCredits())
                  * pref.getCreditWeight() * 100 / tw
                : pref.getCreditWeight() * 100 / tw;

        // 교수 점수
        List<String> prefs = pref.getPreferredProfessors().stream().map(String::trim).filter(s -> !s.isBlank()).collect(Collectors.toList());
        double ps = prefs.isEmpty() ? pref.getProfessorWeight() * 100 / tw
                : ((double) courses.stream().filter(c -> prefs.contains(c.getProfessor())).count() / courses.size())
                  * pref.getProfessorWeight() * 100 / tw;

        // 공강 점수
        int total = 0, sat = 0;
        for (String d : pref.getFreeDays()) {
            if (!d.isBlank()) { total++; if (courses.stream().noneMatch(c -> c.getDayOfWeek().equals(d.trim()))) sat++; }
        }
        for (int h : pref.getFreeHours()) {
            total++; if (courses.stream().noneMatch(c -> c.getStartHour() <= h && h < c.getEndHour())) sat++;
        }
        double fs = total == 0 ? pref.getFreeDayWeight() * 100 / tw
                : ((double) sat / total) * pref.getFreeDayWeight() * 100 / tw;

        double totalScore = Math.min(100.0, Math.round((cs + ps + fs) * 10.0) / 10.0);
        return new TimetableResult(courses, totalScore, totalCredits);
    }
}
