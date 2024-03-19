package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

import static ui.EscapeSequences.*;
public class chessBoardUI {
    private static boolean isBackwards = false;
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

        printBothBoards();
    }

    //I should make  ChessPiece[][] this a param eventually
    public static void printBothBoards(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        ChessBoard pieces = new ChessBoard();
        pieces.resetBoard();
        ChessPiece[][] board = pieces.getBoard();

        setDarkerGray(out);
        printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
        drawBoard(out, new String[]{" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "}, board);
        printBorders(out, new String[]{" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "});
        out.println();
        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
        isBackwards = true;
        drawBoard(out, new String[]{" 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "}, board);
        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "});
        isBackwards = false;
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void drawBoard(PrintStream out, String[] labelList, ChessPiece[][] board) {
        int counter;
        if (isBackwards) {
            counter = 7;
        } else {
            counter = 0;
        }
        for (String label : labelList) {
            if (counter % 2 == 0) {
                drawOneRow(out, SET_BG_COLOR_LIGHT_GREY, label, board[counter]);
            }
            else {
                drawOneRow(out, SET_BG_COLOR_DARK_GREY, label, board[counter]);
            }

            if (isBackwards) {
                counter--;
            }
            else {
                counter++;
            }
        }
    }


    private static void drawOneRow(PrintStream out, String color, String label, ChessPiece[] rowList) {
        if (isBackwards) {
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
                        printTheMiddleSquare(out, rowList[j], ChessGame.TeamColor.WHITE);
                    } else {
                        printTheMiddleSquare(out, rowList[j], rowList[j].getTeamColor());
                    
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

    private static void printTheMiddleSquare(PrintStream out, ChessPiece piece, ChessGame.TeamColor color) { // probably need to add a chess board as a param
            String printPiece = "";
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
            else {
                printPiece = "   ";
            }
            out.print(printPiece);
            out.print(EMPTY);
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
        

}
