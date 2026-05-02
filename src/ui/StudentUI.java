package ui;

import model.Preference;
import model.User;

import java.util.Scanner;

public class StudentUI {

    private static final String DIV = "========================================";
    private final Scanner sc;
    private final User user;
    private Preference lastPreference = null;
    private final PreferenceUI preferenceUI;

    public StudentUI(Scanner sc, User user) {
        this.sc = sc;
        this.user = user;
        this.preferenceUI = new PreferenceUI(sc);
    }

    public void run() {
        while (true) {
            System.out.println("\n<< 학생 메인 메뉴 >>  [" + user.getName() + "님]");
            System.out.println(DIV);
            System.out.println("  1. 시간표 생성  (조건 입력)");
            System.out.println("  2. 조건 수정 후 재생성");
            System.out.println("  0. 로그아웃");
            System.out.println(DIV);
            System.out.print("선택 >> ");

            switch (sc.nextLine().trim()) {
                case "1" -> lastPreference = preferenceUI.runFull(null);
                case "2" -> {
                    if (lastPreference == null) {
                        System.out.println("[!] 먼저 시간표를 생성해주세요.");
                    } else {
                        lastPreference = preferenceUI.runFull(lastPreference);
                    }
                }
                case "0" -> { return; }
                default  -> System.out.println("[!] 올바른 번호를 입력해주세요.");
            }
        }
    }
}
