package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight {
    private ChessGame.TeamColor teamColor;

    public Knight(ChessGame.TeamColor teamcolor) {
        this.teamColor = teamcolor;
    }

    public Collection<ChessMove> allMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //up 2 right 1
        if ((startPosition.getRow() + 2) < 8 || (startPosition.getColumn() + 1) < 8) {

            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }

        }


        //right 2 up 1
        if ((startPosition.getRow() + 1) < 8 || (startPosition.getColumn() + 2) < 8) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }
        }

        //up 2 left 1
        if ((startPosition.getRow() + 2) < 8 || (startPosition.getColumn() - 1) >= 0) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }

        }


        // left 2 up 1
        if ((startPosition.getRow() + 1) < 8 || (startPosition.getColumn() - 2) >= 0) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1 , startPosition.getColumn() -2);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }
            }

        }
//////////////////////////
        // left 2 down 1
        if ((startPosition.getRow() - 1) >= 0 || (startPosition.getColumn() - 2) >= 0) {

            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }

        }


        //down 2 left 1
        if ((startPosition.getRow() - 2 ) >= 0 || (startPosition.getColumn() - 1) >= 0) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() - 1);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }
        }

        //down 2 right 1
        if ((startPosition.getRow() - 2) >= 0 || (startPosition.getColumn() + 1) < 8) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }

            }

        }


        // right 2 down 1
        if ((startPosition.getRow() - 1) >= 0 || (startPosition.getColumn() + 2) < 8) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() -1 , startPosition.getColumn() + 2);

            // if there is on piece here
            if ((board.getPiece(endPosition) == null)) {

                //add the piece to the array.   am I adding it in the right way?
                moves.add(new ChessMove(startPosition, endPosition, null));
            }

            // not null
            else {
                //chess piece that is in the way
                ChessPiece blocked = board.getPiece(endPosition);

                //take opponents piece
                if (blocked.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }
            }

        }




        // is this the right thing to return??
        return moves;
    }

}
