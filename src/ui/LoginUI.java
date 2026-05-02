package ui;

import model.User;
import service.UserService;

import java.util.Scanner;

public class LoginUI {

    private static final String DIV = "========================================";
    private final Scanner sc;
    private final UserService userService = new UserService();

    public LoginUI(Scanner sc) { this.sc = sc; }

    /** 로그인 또는 회원가입 후 User 반환. 종료 시 null. */
    public User run() {
        while (true) {
            System.out.println("\n" + DIV);
            System.out.println("        스마트 시간표 (Smart Timetable)");
            System.out.println(DIV);
            System.out.println("  1. 로그인");
            System.out.println("  2. 회원가입");
            System.out.println("  0. 종료");
            System.out.println(DIV);
            System.out.print("선택 >> ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> { User u = login(); if (u != null) return u; }
                case "2" -> register();
                case "0" -> { System.out.println("프로그램을 종료합니다."); return null; }
                default  -> System.out.println("[!] 올바른 번호를 입력해주세요.");
            }
        }
    }

    private User login() {
        System.out.println("\n<< 로그인 >>");
        System.out.println(DIV);
        System.out.print("ID       : "); String id = sc.nextLine().trim();
        System.out.print("Password : "); String pw = sc.nextLine().trim();
        System.out.println(DIV);
        try {
            User user = userService.login(id, pw);
            System.out.println("[*] 로그인 성공! 환영합니다, " + user.getName() + "님.");
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("[!] " + e.getMessage());
            return null;
        }
    }

    private void register() {
        System.out.println("\n<< 회원가입 >>");
        System.out.println("회원님의 정보를 입력해주세요.");
        System.out.println(DIV);
        System.out.print("ID       : "); String id   = sc.nextLine().trim();
        System.out.print("Password : "); String pw   = sc.nextLine().trim();
        System.out.print("Name     : "); String name = sc.nextLine().trim();
        System.out.println(DIV);
        try {
            userService.register(id, pw, name);
            System.out.println("[*] 회원가입이 완료되었습니다. 로그인해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }
}
