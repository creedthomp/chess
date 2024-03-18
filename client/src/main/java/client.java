import ui.preLoginUI;

import java.util.Objects;
import java.util.Scanner;

public class client {

    private static void notLoggedIn(ServerFacade server) {
        preLoginUI preLogin = new preLoginUI();
        Scanner scanner = new Scanner(System.in);
        preLogin.printPreLoginMenu();
        String input = scanner.nextLine(); // Read the input once
        switch (input) {
            case "1":
                System.out.println("Enter Username: ");
                System.out.println("Enter Password: ");
                System.out.println("Enter Email: ");
                break;
            case "2":
                System.out.println("Enter Username: ");
                System.out.println("Enter Password: ");
                break;
            case "3":
                System.out.println("quit somehow ");
                break;
            case "4":
                System.out.println("Input the number for what you want to do");
                preLogin.printPreLoginMenu();
                break;
            default:
                System.out.println("Invalid Response");
        }
    }
}
