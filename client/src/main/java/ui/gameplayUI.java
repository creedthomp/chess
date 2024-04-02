package ui;

import Websocket.WebSocketFacade;
import chess.*;

import javax.websocket.Session;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;
import static ui.EscapeSequences.SET_TEXT_COLOR_WHITE;

public class gameplayUI {
    private final WebSocketFacade ws;
    Session session;
    // MAYBE ADD A PLAYER COLOR
    String authToken;
    ChessGame game;
    ChessBoard board;

    public gameplayUI(String auth, ChessGame gameChess) {
        game = gameChess;
        board = game.getBoard();
        board.resetBoard();
        authToken = auth;
    }

    public void displayGameplayCommands() {

        System.out.println();
        System.out.println("1. Help");
        System.out.println("2. Highlight Legal Moves");
        System.out.println("3. Make Move");
        System.out.println("4. Redraw Chess Board");
        System.out.println("5. Leave");
        System.out.println("6. Resign");
        //getGameplayInput(scanner);
    }

    public void getGameplayInput(String team) throws InvalidMoveException {
        Scanner scanner = new Scanner(System.in);
        displayGameplayCommands();
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                help(team);
                break;
            case "2":
                highlightMoves(scanner, team);
                break;
            case "3":
                makeMove(scanner, team);
                break;
            case "4":
                redrawBoard(scanner, team);
                break;
            case "5":
                leave();
                break;
            case "6":
                resign(team);
                break;
        }
    }

    void help(String team) throws InvalidMoveException {
        System.out.println();
        System.out.println("Select make move and enter a start and end position (e6->b7)");
        System.out.println("or");
        System.out.println("Select highlight moves and enter the position of a piece to see all possible moves (e6)");
        getGameplayInput(team);
    }

    void highlightMoves(Scanner scanner, String team) throws InvalidMoveException {
        chessBoardUI chessboard = new chessBoardUI();
        System.out.println("Enter a position: ");
        String position = scanner.nextLine();
        ChessPosition actualPos = getPosition(position);
        // TO DO: check the team color for backwards true or false. call menu again to loop, message saying dont make a move until other player has gone
        if (actualPos.getRow() == -1 || actualPos.getColumn() == -1) {
            getGameplayInput(team);
        }
        else {
            chessboard.printBoard(board, backwards(team), actualPos);
            System.out.println(SET_TEXT_COLOR_RED + "DO NOT ENTER AN INPUT UNTIL OPPONENT HAS GONE" + SET_TEXT_COLOR_WHITE);
            getGameplayInput(team);
        }
    }

    void makeMove(Scanner scanner, String team) throws InvalidMoveException {
        chessBoardUI chessboard = new chessBoardUI();
        ChessMove chessMove;
        System.out.println("Enter a start and end position (e6->b7): ");
        String move = scanner.nextLine();
        String[] parts = move.split("->");
        ChessPosition startPosition = getPosition(parts[0]);
        ChessPosition endPosition = getPosition(parts[1]);
        chessMove = getMove(scanner, startPosition, endPosition, team);
        if (validateMove(chessMove)) {
            game.makeMove(chessMove);
        }
        else {
            System.out.println("Error: Invalid Move");
        }
        chessboard.printBoard(board, backwards(team), null);
        System.out.println(SET_TEXT_COLOR_RED + "DO NOT ENTER AN INPUT UNTIL OPPONENT HAS GONE" + SET_TEXT_COLOR_WHITE);
        getGameplayInput(team);
        // maybe add try catch for the invalid moves
    }

    void redrawBoard(Scanner scanner, String team) throws InvalidMoveException {
        chessBoardUI chessboard = new chessBoardUI();
        chessboard.printBoard(board, backwards(team), null);
        getGameplayInput(team);
    }

    void leave() {

    }

    void resign(String team) {
        // delete game??
        switch (team) {
            case "BLACK":
                System.out.println("WHITE WINS");
                break;
            case "WHITE":
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

    boolean backwards(String team) {
        return Objects.equals(team, "WHITE");
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

    ChessMove getMove(Scanner scanner, ChessPosition startPosition, ChessPosition endPosition, String team) {
        ChessMove chessMove;
        ChessPiece piece = board.getPiece(startPosition);
        if (Objects.equals(piece.getPieceType(),ChessPiece.PieceType.PAWN)) {
            if (((endPosition.getRow() == 7) && (Objects.equals(team, "WHITE"))) || ((endPosition.getRow() == 0) && (Objects.equals(team, "BLACK")))) {
                promote();
                String promotion = scanner.nextLine();
                chessMove = new ChessMove(startPosition, endPosition, getPromote(promotion));
                return chessMove;
            }
        }
        chessMove = new ChessMove(startPosition, endPosition, null);
        return chessMove;
    }

    boolean validateMove(ChessMove move) {
        ChessPosition start = move.getStartPosition();
        Collection<ChessMove> valid = game.validMoves(start);
        for (ChessMove chessMove : valid) {
            if (Objects.equals(chessMove, move)) {
                return true;
            }
        }
        return false;
    }

}
