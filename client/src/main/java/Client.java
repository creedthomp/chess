import Websocket.WebSocketFacade;
import chess.ChessGame;
import dataAccess.DataAccessException;
import models.GameInformation;
import models.UserInformation;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.FinalResponse;
import ui.*;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Objects;
import java.util.Scanner;

public class Client implements NotificationHandler {
    private final ServerFacade server;
    private final String serverUrl;

    private final WebSocketFacade ws;

    private static boolean signedIn;

    private static FinalResponse authToken;

    private ChessGame game;

    public Client(String serverUrl) throws DataAccessException {
        this.server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        ws = new WebSocketFacade(serverUrl, this);
    }

//    public static void run() throws DataAccessException {
//        Client client = new Client(serverUrl);
//        client.notLoggedInMenu();
//    }

    //maybe put this in the params ServerFacade server
    void notLoggedInMenu() throws DataAccessException {
        preLoginUI preLogin = new preLoginUI();
        Scanner scanner = new Scanner(System.in);
        preLogin.printPreLoginMenu();
        String input = scanner.nextLine(); // Read the input once
        if (signedIn == true) {
            loggedInMenu();
        }
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
    private void loggedInMenu() throws DataAccessException {
        postLoginUI postlogin = new postLoginUI();
        Scanner scanner = new Scanner(System.in);
        postlogin.printPostLoginMenu();
        String input = scanner.nextLine(); // Read the input once
        switch (input) {
            case "1":
                createGame(scanner);
                //loggedInMenu();
                break;
            case "2":
                listGames();
                loggedInMenu();
                break;
            case "3":
                joinGame(scanner);
                break;
            case "4":
                joinGame(scanner);
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
                System.out.println("Invalid Response. Please enter a number.");
                loggedInMenu();
        }

    }

    private void register(Scanner scanner) throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest();
        System.out.println("Enter Username: ");
        String username = scanner.next();
        registerRequest.username = username;
        System.out.println("Enter Password: ");
        String password = scanner.next();
        registerRequest.password = password;
        System.out.println("Enter Email: ");
        String email = scanner.next();
        registerRequest.email = email;
        try {
            authToken = server.register(registerRequest);
            signedIn = true;
            loggedInMenu();
        }
        catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            //System.out.println("idk why error");
            notLoggedInMenu();
        }
    }

    private void login(Scanner scanner) throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest();
        System.out.println("Enter Username: ");
        String username = scanner.next();
        loginRequest.setUsername(username);
        System.out.println("Enter Password: ");
        String password = scanner.next();
        loginRequest.setPassword(password);
        try {
            authToken = server.login(loginRequest);
            signedIn = true;
            loggedInMenu();
        }
        catch (DataAccessException exception) {
            System.out.println("User has not been registered");
            notLoggedInMenu();
        }
    }

    private void quit() {
        // idk what to do here
    }

    private void help() {
        System.out.println("Input the number for what you want to do:");
        // is this the right thing to do in help?
    }

    private void createGame(Scanner scanner) throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest();
        //System.out.println("Game name must be one word");
        System.out.println("Enter gameName: ");
        String gameName = scanner.next();
        createGameRequest.setGameName(gameName);
        try {
            FinalResponse response = server.createGame(createGameRequest, authToken);
            for (GameInformation gameInformation : response.getGameList()) {
                if (Objects.equals(gameInformation.getGameID(), response.getGameID())){
                    game = gameInformation.getGame();
                }
            }
            loggedInMenu();
        }
        catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            loggedInMenu();
        }
    }

    private void listGames() throws DataAccessException {
        // list games somehow
        try {
            //System.out.println("temporary list  ");
            FinalResponse response = server.ListGames(authToken);
            for (GameInformation game : response.getGameList()) {
                System.out.println("Game Name: " + game.getGameName() + ", White: " + game.getWhiteName() + ", Black: " + game.getBlackName() + ", Game ID: " + game.getGameID());
            }
        }
        catch (DataAccessException ex) {
            System.out.println("Error: unauthorized");
            System.out.println(ex.getMessage());
        }
    }

    private void joinGame(Scanner scanner) throws DataAccessException {
        gameplayUI gameplayui = new gameplayUI(authToken.getAuthT(), game, ws);
        chessBoardUI chessBoardui = new chessBoardUI();
        int gameID = 0;
        JoinGameRequest joinGameRequest = new JoinGameRequest();
        System.out.println("Enter Game ID to join: ");
        String gameIDString = scanner.next();
        String team = getteam(scanner);
        joinGameRequest.setPlayerColor(team);
        try {
            gameID = Integer.parseInt(gameIDString);
            joinGameRequest.setGameID(gameID);
            gameplayui.setGameID(gameID);
            // Now you can use gameID as an integer.
        } catch (NumberFormatException e) {
            System.out.println("Invalid Game ID. Please enter a valid number.");
        }
        try {
            server.joinGame(joinGameRequest, authToken);
            signedIn = true;

            // probably change stuff right here for ws
            if (team.isEmpty()) {
                gameplayui.setObserve(true);
                gameplayui.joinGame(null);
                gameplayui.getGameplayInput();
            }
            else {
                gameplayui.setObserve(false);
                if (Objects.equals(team, "BLACK")) {
                    gameplayui.joinGame(ChessGame.TeamColor.BLACK);
                   // ws.joinPlayer(authToken.getAuthToken(),gameID, ChessGame.TeamColor.BLACK);
                    gameplayui.getGameplayInput();
                }
                else {
                    gameplayui.joinGame(ChessGame.TeamColor.WHITE);
                   //ws.joinPlayer(authToken.getAuthToken(),gameID, ChessGame.TeamColor.WHITE);
                    gameplayui.getGameplayInput();
                }
                //gameplayui.getGameplayInput(team);
            }
            //chessBoardUI.printBothBoards();
        }
        catch (Exception exception) { // error here
            System.out.println("Error: bad request");
            loggedInMenu();
        }
        //chessBoardUI.printBothBoards();
    }

//    private void observe(Scanner scanner) throws DataAccessException {
//        JoinGameRequest joinGameRequest = new JoinGameRequest();
//        System.out.println("Enter Game ID to join: ");
//        String gameIDString = scanner.next();
//        String team = "";
//        joinGameRequest.setPlayerColor(team);
//        try {
//            int gameID = Integer.parseInt(gameIDString);
//            joinGameRequest.setGameID(gameID);
//            // Now you can use gameID as an integer.
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid Game ID. Please enter a valid number.");
//            //observe(scanner);
//        }
//        try {
//            server.joinGame(joinGameRequest, authToken);
//            signedIn = true;
//            chessBoardUI.printBothBoards();
//        }
//        catch (DataAccessException exception) {
//            System.out.println("Error: bad request");
//            loggedInMenu();
//        }
//
//    }

    private void logout() {
        //logout somehow
        try {
            server.logout(authToken);
            signedIn = false;
        }
        catch (DataAccessException exception) {
            System.out.println("Error: unauthorized?");
        }
    }

    private String getteam(Scanner scanner) {
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

    @Override
    public void notify(ServerMessage notification) {
        System.out.println(notification.getMessage());
    }
}
