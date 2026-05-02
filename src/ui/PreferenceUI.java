package ui;

import model.Preference;
import service.DataStore;
import service.TimetableGenerator;
import model.TimetableResult;

import java.util.*;
import java.util.stream.Collectors;

public class PreferenceUI {

    private static final String DIV = "========================================";
    private final Scanner sc;
    private final TimetableGenerator generator = new TimetableGenerator();

    public PreferenceUI(Scanner sc) { this.sc = sc; }

    /** 기존 선호 조건 없이 새로 입력 */
    public Preference inputPreference() {
        return inputForm(null);
    }

    /** 기존 선호 조건을 불러와 수정 (Use Case #7) */
    public Preference modifyPreference(Preference existing) {
        System.out.println("\n<< 조건 수정 >>");
        System.out.println("기존에 입력한 조건을 불러왔습니다.");
        System.out.println(DIV);
        System.out.println("수강 희망 과목 : " + String.join(", ", existing.getCourseNames()));
        System.out.println("목표 학점      : " + existing.getTargetCredits() + "학점");
        System.out.println("공강 요일      : " + (existing.getFreeDays().isEmpty() ? "없음" : String.join(", ", existing.getFreeDays())));
        System.out.println("선호 교수      : " + (existing.getPreferredProfessors().isEmpty() ? "없음" : String.join(", ", existing.getPreferredProfessors())));
        System.out.println(DIV);
        System.out.print("조건을 수정하시겠습니까? (y/n) >> ");
        String ans = sc.nextLine().trim();
        if (ans.equalsIgnoreCase("y")) return inputForm(existing);
        return existing;
    }

    private Preference inputForm(Preference existing) {
        System.out.println("\n<< 시간표 조건 입력 >>");
        System.out.println(DIV);

        // 과목 목록 안내
        List<String> names = DataStore.getCourses().stream()
                .map(c -> c.getName()).distinct().sorted().collect(Collectors.toList());
        System.out.println("[개설 과목 목록]");
        System.out.println("  " + String.join(", ", names));
        System.out.println(DIV);

        // 수강 희망 과목
        String defaultCourses = existing != null ? String.join(",", existing.getCourseNames()) : "";
        System.out.print("수강 희망 과목 (쉼표로 구분)" + (defaultCourses.isEmpty() ? "" : " [" + defaultCourses + "]") + " : ");
        String courseInput = sc.nextLine().trim();
        if (courseInput.isEmpty()) courseInput = defaultCourses;
        List<String> courseNames = Arrays.stream(courseInput.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        // 목표 학점
        int defaultCredit = existing != null ? existing.getTargetCredits() : 15;
        System.out.print("목표 학점 [" + defaultCredit + "] : ");
        String creditInput = sc.nextLine().trim();
        int targetCredits = creditInput.isEmpty() ? defaultCredit : Integer.parseInt(creditInput);

        // 공강 요일
        String defaultDays = existing != null ? String.join(",", existing.getFreeDays()) : "";
        System.out.print("공강 요일 (예: 금,수)" + (defaultDays.isEmpty() ? "" : " [" + defaultDays + "]") + " : ");
        String dayInput = sc.nextLine().trim();
        if (dayInput.isEmpty()) dayInput = defaultDays;
        List<String> freeDays = dayInput.isEmpty() ? Collections.emptyList()
                : Arrays.stream(dayInput.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        // 공강 시간대
        System.out.print("공강 시간대 (예: 9,10, 없으면 Enter) : ");
        String hourInput = sc.nextLine().trim();
        List<Integer> freeHours = hourInput.isEmpty() ? Collections.emptyList()
                : Arrays.stream(hourInput.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                  .map(Integer::parseInt).collect(Collectors.toList());

        // 선호 교수
        String defaultProf = existing != null ? String.join(",", existing.getPreferredProfessors()) : "";
        System.out.print("선호 교수 (없으면 Enter)" + (defaultProf.isEmpty() ? "" : " [" + defaultProf + "]") + " : ");
        String profInput = sc.nextLine().trim();
        if (profInput.isEmpty()) profInput = defaultProf;
        List<String> profs = profInput.isEmpty() ? Collections.emptyList()
                : Arrays.stream(profInput.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        System.out.println("\n[가중치 설정 (0.0 ~ 3.0, 클수록 중요)]");
        double wFree  = readWeight("공강 충족도 가중치     ", existing != null ? existing.getFreeDayWeight()   : 1.0);
        double wProf  = readWeight("선호 교수 포함 가중치  ", existing != null ? existing.getProfessorWeight() : 1.0);
        double wCredit= readWeight("목표 학점 달성 가중치  ", existing != null ? existing.getCreditWeight()    : 1.0);

        System.out.println(DIV);
        System.out.println("[*] 조건이 저장되었습니다. 시간표를 생성합니다...");

        return new Preference(courseNames, targetCredits, freeDays, freeHours, profs, wFree, wProf, wCredit);
    }

    private double readWeight(String label, double defaultVal) {
        System.out.print("  " + label + " [" + defaultVal + "] : ");
        String input = sc.nextLine().trim();
        if (input.isEmpty()) return defaultVal;
        try { return Double.parseDouble(input); } catch (NumberFormatException e) { return defaultVal; }
    }

    /** 조건 입력 후 바로 시간표 생성 결과 표시 (Use Case #3 → #4 → #5) */
    public Preference runFull(Preference existing) {
        Preference pref = existing == null ? inputPreference() : modifyPreference(existing);
        ResultUI resultUI = new ResultUI(sc);
        resultUI.show(generator.generate(pref), pref);
        return pref;
    }
}
