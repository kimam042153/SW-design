package ui;

import model.Course;
import model.Preference;
import model.TimetableResult;

import java.util.List;

public class ResultUI {

    private static final String DIV  = "========================================";
    private static final String LINE = "----------------------------------------";
    private final Scanner sc;

    public ResultUI(Scanner sc) { this.sc = sc; }

    public void show(List<TimetableResult> results, Preference pref) {
        System.out.println("\n<< 추천 시간표 결과 >>");
        System.out.println(DIV);

        if (results.isEmpty()) {
            System.out.println("[!] 시간 충돌 없는 시간표 조합이 없습니다.");
            System.out.println("    과목 수를 줄이거나 조건을 완화해주세요.");
            System.out.println(DIV);
            return;
        }

        System.out.println("총 " + results.size() + "개의 시간표가 생성되었습니다.");
        System.out.println(DIV);

        for (int i = 0; i < results.size(); i++) {
            TimetableResult r = results.get(i);
            System.out.printf("%n[%d위]  우선도 점수: %.1f점  /  총 학점: %d학점%n",
                    i + 1, r.getPriorityScore(), r.getTotalCredits());
            System.out.println(LINE);
            for (Course c : r.getCourses()) {
                System.out.printf("  %-14s %d분반  %s요일 %02d:00-%02d:00  %s 교수  %s%n",
                        c.getName(), c.getSection(), c.getDayOfWeek(),
                        c.getStartHour(), c.getEndHour(),
                        c.getProfessor(), c.getClassroom());
            }
            System.out.println(LINE);
        }

        // 저장 (Use Case #6)
        System.out.print("\n저장할 시간표 번호를 입력하세요 (0: 저장 안 함) >> ");
        String input = sc.nextLine().trim();
        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= results.size()) {
                System.out.println("[*] " + choice + "위 시간표가 저장되었습니다.");
            }
        } catch (NumberFormatException ignored) {}
        System.out.println(DIV);
    }
}
