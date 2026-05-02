import model.User;
import ui.AdminUI;
import ui.LoginUI;
import ui.StudentUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        LoginUI loginUI = new LoginUI(sc);
        User user = loginUI.run();
        if (user == null) { sc.close(); return; }

        if ("ADMIN".equals(user.getRole())) {
            new AdminUI(sc).run();
        } else {
            new StudentUI(sc, user).run();
        }

        System.out.println("\n프로그램을 종료합니다. 안녕히 가세요!");
        sc.close();
    }
}
