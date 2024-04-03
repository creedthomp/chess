package ui;

import Websocket.WebSocketFacade;
import chess.*;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

import static ui.EscapeSequences.*;
public class chessBoardUI {


    //Session session;
    private static boolean isBackwards = false;

    private boolean highlight = false;
    // i think I can change from main now that prints right
    public static void main(String[] args) {
//        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//        out.print(ERASE_SCREEN);
//
//        ChessBoard pieces = new ChessBoard();
//        pieces.resetBoard();
//        setDarkerGray(out);
//        printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
//        drawBoard(out, new String[]{" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "}, pieces);
//        printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
//
//        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
//        drawBoard(out, new String[]{" 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "}, pieces);
//        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
//
//       out.print(SET_BG_COLOR_BLACK);
//       out.print(SET_TEXT_COLOR_WHITE);

        //printBothBoards();
    }

    //I should make  ChessPiece[][] this a param eventually
    public void printBoard(ChessBoard board, boolean backwards, ChessPosition highlightPos){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        if (backwards) {
            setDarkerGray(out);
            printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
            isBackwards = true;
            drawBoard(out, new String[]{" 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "}, board, highlightPos);
            printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
            isBackwards = false;
            out.println();
        }
        else {
            printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
            //isBackwards = true;
            drawBoard(out, new String[]{" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "}, board, highlightPos);
            printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
            //isBackwards = false;
            out.println();

        }
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private void drawBoard(PrintStream out, String[] labelList, ChessBoard kindaBoard, ChessPosition highlightPos) {
        Collection<ChessMove> potentialMoves = null;
        if (highlightPos != null) {
            potentialMoves = getMoves(kindaBoard, highlightPos);
        }
        ChessPiece[][] board = kindaBoard.getBoard();
        int counter;
        if (isBackwards) {
            counter = 7;
        } else {
            counter = 0;
        }
        for (String label : labelList) {
            if (counter % 2 == 0) {
                drawOneRow(out, SET_BG_COLOR_LIGHT_GREY, label, board[counter], counter, potentialMoves);
            }
            else {
                drawOneRow(out, SET_BG_COLOR_DARK_GREY, label, board[counter], counter, potentialMoves);
            }

            if (isBackwards) {
                counter--;
            }
            else {
                counter++;
            }
        }
    }


    private static void drawOneRow(PrintStream out, String color, String label, ChessPiece[] rowList, int row, Collection<ChessMove> potentialMoves) {
        if (!isBackwards) {
            rowList = reverseRow(rowList);
        }
        for (int i = 0; i < 3; i++) {
            printSideHelp(out, i, label);
            for (int j = 0; j < 8; j++) { // i am going to switch like my whole implementaiton
                switch (color) {
                   case SET_BG_COLOR_LIGHT_GREY:
                       color = SET_BG_COLOR_DARK_GREY;
                       setDarkGray(out);
                       break;
                    case SET_BG_COLOR_DARK_GREY:
                        color = SET_BG_COLOR_LIGHT_GREY;
                        setLightGray(out);
                        break;
                }
                if (i != 1) {
                    printTopBottomSquare(out);
                }
                else{
                    if (rowList[j] == null) {
                        printTheMiddleSquare(out, rowList[j], ChessGame.TeamColor.WHITE, potentialMoves, new ChessPosition(row, j), color);
                    } else {
                        printTheMiddleSquare(out, rowList[j], rowList[j].getTeamColor(), potentialMoves, new ChessPosition(row, j), color);
                    
                    }
                }
            }
            printSideHelp(out, i, label);
            out.println();
        }
    }

    private static void setLightGray(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private static void setDarkGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private static void setDarkerGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARKER_GREY);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
    }
    private static void printTopBottomSquare(PrintStream out) {
            //out.print(" ");
            out.print(EMPTY.repeat(3));
    }

    private static void printTheMiddleSquare(PrintStream out, ChessPiece piece, ChessGame.TeamColor color, Collection<ChessMove> potentialMoves, ChessPosition positionToCheck, String currentColor) { // probably need to add a chess board as a param
            String printPiece = "   ";
            if (color == ChessGame.TeamColor.BLACK){
                out.print(SET_TEXT_COLOR_BLACK);
            }
            else {
                out.print(SET_TEXT_COLOR_WHITE);
            }
            out.print(EMPTY);
            if (piece != null) {
                switch (piece.getPieceType()) {
                    case KING -> printPiece = " K ";
                    case QUEEN -> printPiece = " Q ";
                    case BISHOP -> printPiece = " B ";
                    case ROOK -> printPiece = " R ";
                    case KNIGHT -> printPiece = " N ";
                    case PAWN -> printPiece = " P ";
                    default -> printPiece = "   ";
                }
            }

            if ((potentialMoves == null) || (potentialMoves.isEmpty())) {
                printThePiece(out, printPiece, false, currentColor);
            }

            else {
                printThePiece(out, printPiece, containsThisSquare(potentialMoves, positionToCheck), currentColor);
            }
    }

    private static void printBorders(PrintStream out, String[] list) {
        setDarkerGray(out);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(EMPTY);
        for (int i = 0; i < 8; i++) {
            out.print(EMPTY);
            out.print(list[i]);
            out.print(EMPTY);
        }
        out.print(EMPTY);
        setBlack(out);
        out.println();
    }

    private static void printSideHelp(PrintStream out, int i, String label) {
        out.print(SET_TEXT_COLOR_WHITE);
        if (i == 1) {
            setDarkerGray(out);
            out.print(label);
        }
        else {
            setDarkerGray(out);
            out.print(EMPTY);
        }
        setBlack(out);
    }
    
    private static ChessPiece[] reverseRow(ChessPiece[] rowList) {
        ChessPiece[] backwards = new ChessPiece[8];
        int counter = 0;
        for (int i = 7; 0 <= i; i--) {
            backwards[counter] = rowList[i];
            counter++;
        }
        return backwards;
    }

    Collection<ChessMove> getMoves(ChessBoard kindaBoard, ChessPosition pos) {
        ChessPosition newPos = new ChessPosition(pos.getRow(), pos.getColumn());
        Collection<ChessMove> validMoves;
        ChessPiece piece = kindaBoard.getPiece(newPos);
        validMoves = piece.pieceMoves(kindaBoard, newPos);
        return validMoves;
    }

    static boolean containsThisSquare(Collection<ChessMove> potentialMoves, ChessPosition pos) {
        for (ChessMove move : potentialMoves) {
            ChessPosition newEnd = new ChessPosition(move.getEndPosition().getRow() - 1, move.getEndPosition().getColumn() - 1);
            if (Objects.equals(newEnd, pos)) {
                return true;
            }
        }
        return false;
    }

    static void printThePiece(PrintStream out, String piece, boolean highlight, String color) {
        if (highlight) {
            out.print(SET_BG_COLOR_GREEN);
            out.print(piece);
            switch (color) {
                case SET_BG_COLOR_LIGHT_GREY:
                    setLightGray(out);
                    out.print(EMPTY);
                    break;
                case SET_BG_COLOR_DARK_GREY:
                    setDarkGray(out);
                    out.print(EMPTY);
                    break;
            }

        }
        else {
            out.print(piece);
            out.print(EMPTY);
        }
    }


}
