import model.User;
import ui.LoginUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginUI loginUI = new LoginUI(sc);

        User user = loginUI.run();
        if (user == null) return; // 종료

        // TODO: 로그인 후 화면 연결 (구현 중)
        System.out.println("\n[구현 중] " + user.getRole() + " 메인 화면으로 이동합니다...");
        sc.close();
    }
}
