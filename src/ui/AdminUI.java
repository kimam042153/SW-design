package ui;

import model.Course;
import service.DataStore;

import java.util.List;
import java.util.Scanner;

public class AdminUI {

    private static final String DIV  = "========================================";
    private static final String LINE = "----------------------------------------";
    private final Scanner sc;

    public AdminUI(Scanner sc) { this.sc = sc; }

    public void run() {
        while (true) {
            System.out.println("\n<< 관리자 페이지 - 강의 관리 >> (Use Case #8)");
            System.out.println(DIV);
            System.out.println("  1. 강의 목록 조회");
            System.out.println("  2. 강의 추가");
            System.out.println("  3. 강의 수정");
            System.out.println("  4. 강의 삭제");
            System.out.println("  0. 로그아웃");
            System.out.println(DIV);
            System.out.print("선택 >> ");

            switch (sc.nextLine().trim()) {
                case "1" -> listCourses();
                case "2" -> addCourse();
                case "3" -> editCourse();
                case "4" -> deleteCourse();
                case "0" -> { return; }
                default  -> System.out.println("[!] 올바른 번호를 입력해주세요.");
            }
        }
    }

    private void listCourses() {
        List<Course> courses = DataStore.getCourses();
        System.out.println("\n<< 전체 강의 목록 >> (총 " + courses.size() + "개)");
        System.out.println(DIV);
        System.out.printf("%-4s  %-14s %-4s  %-4s  %-9s  %-8s  %s%n",
                "ID", "과목명", "분반", "학점", "요일·시간", "교수", "강의실");
        System.out.println(LINE);
        for (Course c : courses) {
            System.out.printf("%-4d  %-14s %d분반  %d학점  %s %02d:00-%02d:00  %-8s  %s%n",
                    c.getId(), c.getName(), c.getSection(), c.getCredit(),
                    c.getDayOfWeek(), c.getStartHour(), c.getEndHour(),
                    c.getProfessor(), c.getClassroom());
        }
        System.out.println(DIV);
    }

    private void addCourse() {
        System.out.println("\n<< 강의 추가 >>");
        System.out.println(DIV);
        try {
            System.out.print("과목명     : "); String name      = sc.nextLine().trim();
            System.out.print("분반 번호  : "); int section       = Integer.parseInt(sc.nextLine().trim());
            System.out.print("학점       : "); int credit        = Integer.parseInt(sc.nextLine().trim());
            System.out.print("과목 코드  : "); int code          = Integer.parseInt(sc.nextLine().trim());
            System.out.print("요일 (월화수목금토) : "); String day = sc.nextLine().trim();
            System.out.print("시작 시간  : "); int start         = Integer.parseInt(sc.nextLine().trim());
            System.out.print("종료 시간  : "); int end           = Integer.parseInt(sc.nextLine().trim());
            System.out.print("교수       : "); String professor  = sc.nextLine().trim();
            System.out.print("강의실     : "); String classroom  = sc.nextLine().trim();
            System.out.println(DIV);

            if (end <= start) { System.out.println("[!] 종료 시간은 시작 시간보다 커야 합니다."); return; }

            Course course = new Course(DataStore.nextId(), name, credit, code, section,
                    day, professor, classroom, start, end);
            DataStore.getCourses().add(course);
            System.out.println("[*] 강의가 추가되었습니다. (ID: " + course.getId() + ")");
        } catch (NumberFormatException e) {
            System.out.println("[!] 숫자 형식이 올바르지 않습니다.");
        }
    }

    private void editCourse() {
        listCourses();
        System.out.print("수정할 강의 ID >> ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            Course target = DataStore.getCourses().stream()
                    .filter(c -> c.getId() == id).findFirst().orElse(null);
            if (target == null) { System.out.println("[!] 해당 ID의 강의를 찾을 수 없습니다."); return; }

            System.out.println("\n<< 강의 수정 >> (수정하지 않을 항목은 Enter)");
            System.out.println(DIV);
            System.out.print("과목명     [" + target.getName()      + "] : "); String v;
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setName(v);
            System.out.print("분반 번호  [" + target.getSection()   + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setSection(Integer.parseInt(v));
            System.out.print("요일       [" + target.getDayOfWeek() + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setDayOfWeek(v);
            System.out.print("시작 시간  [" + target.getStartHour() + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setStartHour(Integer.parseInt(v));
            System.out.print("종료 시간  [" + target.getEndHour()   + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setEndHour(Integer.parseInt(v));
            System.out.print("교수       [" + target.getProfessor() + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setProfessor(v);
            System.out.print("강의실     [" + target.getClassroom() + "] : ");
            v = sc.nextLine().trim(); if (!v.isEmpty()) target.setClassroom(v);
            System.out.println(DIV);
            System.out.println("[*] 강의 정보가 수정되었습니다.");
        } catch (NumberFormatException e) {
            System.out.println("[!] 올바른 ID를 입력해주세요.");
        }
    }

    private void deleteCourse() {
        listCourses();
        System.out.print("삭제할 강의 ID >> ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean removed = DataStore.getCourses().removeIf(c -> c.getId() == id);
            System.out.println(removed ? "[*] 강의가 삭제되었습니다." : "[!] 해당 ID의 강의를 찾을 수 없습니다.");
        } catch (NumberFormatException e) {
            System.out.println("[!] 올바른 ID를 입력해주세요.");
        }
    }
}
