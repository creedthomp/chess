package ui;

import Websocket.WebSocketFacade;
import chess.*;
import dataAccess.DataAccessException;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.Session;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static chess.ChessGame.status.OVER;
import static ui.EscapeSequences.*;

public class gameplayUI implements NotificationHandler {
    private final WebSocketFacade ws;
    Session session;
    // MAYBE ADD A PLAYER COLOR
    String authToken;
    ChessGame game;
    ChessBoard board;
    ChessGame.TeamColor team = null;
    boolean observing = false;
    int gameID;

    public gameplayUI(String auth, ChessGame gameChess, String serverURL) throws DataAccessException {
        game = gameChess;
        board = game.getBoard();
        board.resetBoard();
        authToken = auth;
        ws = new WebSocketFacade(serverURL, this);
        //gameID = gID;
    }

    public void setObserve(boolean bool) {
        observing = bool;
    }

    public void setGameID(int gID) {
        gameID = gID;
    }

    public void setTeamColor(ChessGame.TeamColor color) {
        team = color;
    }
    public void displayGameplayCommands() {

        System.out.println();
        System.out.println("1. Help");
        System.out.println("2. Highlight Legal Moves");
        System.out.println("3. Make Move");
        System.out.println("4. Redraw Chess Board");
        System.out.println("5. Leave");
        System.out.println("6. Resign");
        //getGameplayInput(scanner)
    }

    public void joinGame(ChessGame.TeamColor team) throws DataAccessException {
        chessBoardUI chessboard = new chessBoardUI();
        this.team = team;
        if (Objects.equals(null , team)) {
            ws.joinObserver(authToken,gameID);
            //chessboard.printBoard(board, true, null);
            System.out.println(SET_TEXT_COLOR_GREEN + "Successfully observing game" + SET_TEXT_COLOR_WHITE);
        }
        else if (Objects.equals(BLACK, team)) {
            ws.joinPlayer(authToken ,gameID, ChessGame.TeamColor.BLACK);
            //chessboard.printBoard(board, backwards(), null);
            System.out.println(SET_TEXT_COLOR_GREEN + "Successfully joined game as BLACK" + SET_TEXT_COLOR_WHITE);
            System.out.println(SET_TEXT_COLOR_RED + "DO NOT MAKE A MOVE UNTIL WHITE HAS GONE" + SET_TEXT_COLOR_WHITE);
        }
        else {
            ws.joinPlayer(authToken,gameID, WHITE);
            //chessboard.printBoard(board, backwards(), null);
            System.out.println(SET_TEXT_COLOR_GREEN + "Successfully joined game as WHITE" + SET_TEXT_COLOR_WHITE);
        }
    }

    public void getGameplayInput() throws InvalidMoveException, DataAccessException {
        Scanner scanner = new Scanner(System.in);
        displayGameplayCommands();
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                help();
                break;
            case "2":
                highlightMoves(scanner);
                break;
            case "3":
                makeMove(scanner);
                break;
            case "4":
                redrawBoard(scanner);
                break;
            case "5":
                leave();
                break;
            case "6":
                resign();
                break;
        }
    }

    void help() throws InvalidMoveException, DataAccessException {
        System.out.println();
        System.out.println("Select make move and enter a start and end position (e6->b7)");
        System.out.println("or");
        System.out.println("Select highlight moves and enter the position of a piece to see all possible moves (e6)");
        getGameplayInput();
    }

    void highlightMoves(Scanner scanner) throws InvalidMoveException, DataAccessException {
        // if you are in check
        chessBoardUI chessboard = new chessBoardUI();
        if (game.isInCheck(team)) {
            ChessPosition kingPosition = game.myKingPosition(game.getBoard(), team);
            chessboard.printBoard(board, backwards(), kingPosition);
            System.out.println("ALERT: You are in check and must move the King!");
            getGameplayInput();
        }
        // if you are not in check
        else {
            System.out.println("Enter a position: ");
            String position = scanner.nextLine();
            ChessPosition actualPos = getPosition(position);
            // TO DO: check the team color for backwards true or false. call menu again to loop, message saying dont make a move until other player has gone
            if (actualPos.getRow() == -1 || actualPos.getColumn() == -1) {
                getGameplayInput();
            } else {
                chessboard.printBoard(board, backwards(), actualPos);
                getGameplayInput();
            }
        }
    }

    void makeMove(Scanner scanner) throws InvalidMoveException, DataAccessException {
        chessBoardUI chessboard = new chessBoardUI();
        ChessMove chessMove;
        System.out.println("Enter a start and end position (e6->b7): ");
        String move = scanner.nextLine();
        String[] parts = move.split("->");
        ChessPosition startPosition = getPosition(parts[0]);
        ChessPosition endPosition = getPosition(parts[1]);
        chessMove = getMove(scanner, startPosition, endPosition);
        ws.makeMove(authToken, gameID, chessMove, game, team);
        chessboard.printBoard(board, backwards(), null);
        System.out.println(SET_TEXT_COLOR_RED + "DO NOT MAKE A MOVE UNTIL OPPONENT HAS GONE" + SET_TEXT_COLOR_WHITE);
        if (game.state == OVER) {
            System.out.println("GAME OVER");
        }
        else {
            getGameplayInput();
        }
        // maybe add try catch for the invalid moves
    }

    void redrawBoard(Scanner scanner) throws InvalidMoveException, DataAccessException {
        chessBoardUI chessboard = new chessBoardUI();
        chessboard.printBoard(board, backwards(), null);
        getGameplayInput();
    }

    void leave() throws DataAccessException {
        ws.leave(authToken, gameID);
    }

    void resign() throws DataAccessException {
        // delete game??
        ws.resign(authToken, gameID);
        switch (team) {
            case BLACK:
                System.out.println("WHITE WINS");
                break;
            case WHITE:
                System.out.println("BLACK WINS");
                break;
        }
    }

    ChessPosition getPosition(String position) {
        int row;
        int col;
        char column = position.charAt(0);
        try {
            switch (String.valueOf(column)) {
                case "a":
                    row= 1;
                    break;
                case "b":
                    row = 2;
                    break;
                case "c":
                    row = 3;
                    break;
                case "d":
                    row = 4;
                    break;
                case "e":
                    row = 5;
                    break;
                case "f":
                    row = 6;
                    break;
                case "g":
                    row = 7;
                    break;
                case "h":
                    row = 8;
                    break;
                default:
                    row = -1;
                    break;
            }
            column = position.charAt(1);
            switch (String.valueOf(column)) {
                case "1":
                    col = 1;
                    break;
                case "2":
                    col = 2;
                    break;
                case "3":
                    col = 3;
                    break;
                case "4":
                    col = 4;
                    break;
                case "5":
                    col = 5;
                    break;
                case "6":
                    col = 6;
                    break;
                case "7":
                    col = 7;
                    break;
                case "8":
                    col = 8;
                    break;
                default:
                    col = -1;
                    break;
            }

            if (row == -1 || col == -1) {
                System.out.println("Wrong index or format. Required format is e6->b7");
                return new ChessPosition(-1, -1);
            }
        }
        catch (Exception e) {
            System.out.println("Wrong index or format. Required format is e6->b7");
            return new ChessPosition(-1, -1);
        }
        return new ChessPosition(col, row);
    }

    boolean backwards() {
        return !Objects.equals(team, BLACK);
    }

    void promote() {
        System.out.println("Enter a promotion piece: ");
        System.out.println("1. Queen ");
        System.out.println("2. Knight ");
        System.out.println("3. Rook ");
        System.out.println("4. Bishop ");
    }

    ChessPiece.PieceType getPromote(String input) {
        switch (input) {
            case "1":
                return ChessPiece.PieceType.QUEEN;
            case "2":
                return ChessPiece.PieceType.KNIGHT;
            case "3":
                return ChessPiece.PieceType.ROOK;
            case "4":
                return ChessPiece.PieceType.BISHOP;
        }
        return null;
    }

    ChessMove getMove(Scanner scanner, ChessPosition startPosition, ChessPosition endPosition) {
        ChessMove chessMove;
        ChessPiece piece = board.getPiece(startPosition);
        if (Objects.equals(piece.getPieceType(),ChessPiece.PieceType.PAWN)) {
            if (((endPosition.getRow() == 7) && (Objects.equals(team, WHITE))) || ((endPosition.getRow() == 0) && (Objects.equals(team, BLACK)))) {
                promote();
                String promotion = scanner.nextLine();
                chessMove = new ChessMove(startPosition, endPosition, getPromote(promotion));
                return chessMove;
            }
        }
        chessMove = new ChessMove(startPosition, endPosition, null);
        return chessMove;
    }

//    boolean validateMove(ChessMove move) {
//        ChessPosition start = move.getStartPosition();
//        Collection<ChessMove> valid = game.validMoves(start);
//        for (ChessMove chessMove : valid) {
//            if (Objects.equals(chessMove, move)) {
//                return true;
//            }
//        }
//        return false;
//    }


//    public boolean checkMate() {
//        if (Objects.equals(team, BLACK)) {
//            if (game.isInStalemate(WHITE) || game.isInCheckmate(WHITE)) {
//                game.state = OVER;
//                return true;
//            }
//        }
//
//        else {
//            if (game.isInStalemate(BLACK) || game.isInCheckmate(BLACK)) {
//                game.state = OVER;
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void notify(ServerMessage notification) {
        chessBoardUI chessboard = new chessBoardUI();
        //chessboard.printBoard(board, backwards(), null);
        System.out.println("\n");
        switch (notification.getServerMessageType()) {
            case LOAD_GAME -> chessboard.printBoard(board, backwards(), null);
            case ERROR -> System.out.println(SET_TEXT_COLOR_RED + notification.getError());
            case NOTIFICATION -> System.out.println(SET_TEXT_COLOR_MAGENTA + notification.getMessage());
            default -> System.out.println("error: in notify");
        }
    }


}
