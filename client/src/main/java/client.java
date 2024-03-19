import models.UserInformation;
import ui.chessBoardUI;
import ui.postLoginUI;
import ui.preLoginUI;

import java.util.Objects;
import java.util.Scanner;

public class client {
    private final ServerFacade server;
    private final String serverUrl;

    private static boolean signedIn = false;
    public client(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        //private WebSocketFacade ws;
    }

    public static void main(String[] args) {
        notLoggedInMenu();
    }


    //maybe put this in the params ServerFacade server
    private static void notLoggedInMenu() {
        preLoginUI preLogin = new preLoginUI();
        Scanner scanner = new Scanner(System.in);
        preLogin.printPreLoginMenu();
        String input = scanner.nextLine(); // Read the input once
        switch (input) {
            case "1":
                register(scanner);
                break;
            case "2":
                login(scanner);
                break;
            case "3":
                quit();
                break;
            case "4":
                help();
                notLoggedInMenu();
                break;
            default:
                System.out.println("Invalid Response");
                notLoggedInMenu();
        }
    }


    // maybe change params
    private static void loggedInMenu() {
        postLoginUI postlogin = new postLoginUI();
        Scanner scanner = new Scanner(System.in);
        postlogin.printPostLoginMenu();
        String input = scanner.nextLine(); // Read the input once
        switch (input) {
            case "1":
                createGame(scanner);
                loggedInMenu();
                break;
            case "2":
                listGames();
                loggedInMenu();
                break;
            case "3":
                joinGame(scanner);
                break;
            case "4":
                observe(scanner);
                break;
            case "5":
               logout();
                break;
            case "6":
                quit();
                break;
            case "7":
                help();
                loggedInMenu();
                break;
            default:
                System.out.println("Invalid Response");
                loggedInMenu();
        }

    }

    private static void register(Scanner scanner) {
        System.out.println("Enter Username: ");
        String username = scanner.next();
        System.out.println("Enter Password: ");
        String password = scanner.next();
        System.out.println("Enter Email: ");
        String email = scanner.next();
        UserInformation userInfo = new UserInformation(username, password, email);
        // should add this to the db now
        loggedInMenu();
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter Username: ");
        String username = scanner.next();
        System.out.println("Enter Password: ");
        String password = scanner.next();
        signedIn = true;
        loggedInMenu();
    }

    private static void quit() {
        // idk what to do here
    }

    private static void help() {
        System.out.println("Input the number for what you want to do");
        // is this the right thing to do in help?
    }

    private static void createGame(Scanner scanner) {
        System.out.println("Enter gameName: ");
        String gameName = scanner.next();
    }

    private static void listGames() {
        // list games somehow
        System.out.println("temporary list  ");
    }

    private static void joinGame(Scanner scanner) {
        System.out.println("Enter Game ID to join: ");
        String gameIDString = scanner.next();
        String team = getteam(scanner);
        try {
            int gameID = Integer.parseInt(gameIDString);
            // Now you can use gameID as an integer.
        } catch (NumberFormatException e) {
            System.out.println("Invalid Game ID. Please enter a valid number.");
        }
        chessBoardUI.printBothBoards();
    }

    private static void observe(Scanner scanner) {
        System.out.println("Enter Game ID to join: ");
        String gameIDString = scanner.next();
        String team = "";
        try {
            int gameID = Integer.parseInt(gameIDString);
            // Now you can use gameID as an integer.
        } catch (NumberFormatException e) {
            System.out.println("Invalid Game ID. Please enter a valid number.");
            //observe(scanner);
        }
        chessBoardUI.printBothBoards();
    }

    private static void logout() {
        signedIn = false;
        //logout somehow
    }

    private static String getteam(Scanner scanner) {
        System.out.println("Join a team: ");
        System.out.println("1. white");
        System.out.println("2. black");
        System.out.println("3. spectate");
        String team = scanner.next();
        switch (team) {
            case "1" -> {
                return "WHITE";
            }
            case "2" -> {
                return "BLACK";
            }
            case "3" -> {
                return "";
            }
        }
        return "";
    }

}
