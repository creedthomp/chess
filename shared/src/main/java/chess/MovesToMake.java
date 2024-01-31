package chess;

import java.util.Collection;
import java.util.HashSet;

public class MovesToMake {
    private ChessGame.TeamColor color;
    public MovesToMake(ChessGame.TeamColor color) {
        this.color = color;
    }


    public boolean blocked(ChessBoard board, ChessPosition endposition) {
        //return true if it is the other team and we can move there
        ChessPiece blocking = board.getPiece(endposition);
        return blocking.getTeamColor() != color;
    }
    public Collection<ChessMove> diagMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        //upright
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() + i) < 9 && (startPosition.getColumn() + i) < 9) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }
        //down left
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() - i) > 0 && (startPosition.getColumn() - i) > 0) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // up left
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() + i) < 9 && (startPosition.getColumn() - i) > 0) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }

        //down right
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() - i) > 0 && (startPosition.getColumn() + i) < 9) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }
        return moves;
    }




    public Collection<ChessMove> straightMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        //upright
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() + i) < 9 && (startPosition.getColumn()) < 9) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }
        //down left
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow()) > 0 && (startPosition.getColumn() - i) > 0) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }

        // up left
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow() - i) > 0 && (startPosition.getColumn()) > 0) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn());
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }

        //down right
        for (int i = 1; i < 8; i++) {

            if ((startPosition.getRow()) > 0 && (startPosition.getColumn() + i) < 9) {
                ChessPosition endposition = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + i);
                // piece in goal spot?
                if (board.getPiece(endposition) == null) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                }

                else if (blocked(board, endposition)) {
                    moves.add(new ChessMove(startPosition, endposition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }
        return moves;
    }

    public ChessMove oneMove(ChessBoard board, ChessPosition startPosition, int row, int col) {
        ChessMove moves = null;

        int totalRow = startPosition.getRow() + row;
        int totalCol = startPosition.getColumn() + col;

        if (totalRow > 0 && totalRow < 9 && totalCol > 0 && totalCol < 9) {
            ChessPosition endposition = new ChessPosition(totalRow, totalCol);

            if (board.getPiece(endposition) == null) {
                return new ChessMove(startPosition, endposition, null);
            }

            // if the piece in the way is the other color
            else if (blocked(board, endposition)) {
                return new ChessMove(startPosition, endposition, null);
            }

        }
        return moves;
    }


}
