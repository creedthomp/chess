package ui;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

import static ui.EscapeSequences.*;
public class chessBoardUI {

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        printBorders(out, new String[]{ "   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", "   "}, 10);
        drawBoard(out, new String[]{" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "});
        printBorders(out, new String[]{"   ", " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", "   "}, 10);

//        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "}, 11);
//        drawBoard(out, new String[]{"8", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "});
//        printBorders(out, new String[]{" h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "}, 11);

       out.print(SET_BG_COLOR_BLACK);
       out.print(SET_TEXT_COLOR_WHITE);
    }
    private static void drawBoard(PrintStream out, String[] labelList) {
        int counter = 0;
        for (String label : labelList) {
            if (counter % 2 == 0) {
                drawOneRow(out, SET_BG_COLOR_LIGHT_GREY, label);
            }
            else {
                drawOneRow(out, SET_BG_COLOR_DARK_GREY, label);
            }
            counter++;
        }
    }

    private static void drawOneRow(PrintStream out, String color, String label) {
        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                printSide(out, label);
            }
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
                    printTheMiddleSquare(out);
                }
            }
            if (i == 2) {
                printSide(out, label);
            }
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

    private static void printTopBottomSquare(PrintStream out) {
            //out.print(" ");
            out.print(EMPTY.repeat(3));

    }

    private static void printTheMiddleSquare(PrintStream out) { // probably need to add a chess board as a param
            out.print(EMPTY);
            out.print(" P ");
            out.print(EMPTY);
    }

    private static void printBorders(PrintStream out, String[] list, int loop) {
        for (int i = 0; i < loop; i++) {
            out.print(EMPTY);
            out.print(list[i]);
            out.print(EMPTY);
        }
    }

    private static void printSide(PrintStream out, String number) {
        out.print(number);
    }


}
