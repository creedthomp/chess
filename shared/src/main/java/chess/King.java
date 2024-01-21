package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King {
    private ChessGame.TeamColor teamColor;

    public King(ChessGame.TeamColor teamcolor){
        this.teamColor = teamcolor;
    }

    public Collection<ChessMove> allMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

            //up
            if ((startPosition.getRow() + 1) < 9) {

                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() );

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


            //down
            if ((startPosition.getRow() - 1) >= 1 ) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());

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

            //left
            if ((startPosition.getColumn() - 1) >= 1) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() , startPosition.getColumn() - 1);

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


            // right
            if ((startPosition.getColumn() + 1) < 9) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() , startPosition.getColumn() + 1);

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
////
        //up right
        if ((startPosition.getRow() + 1) < 9 && (startPosition.getColumn() + 1) < 9) {

            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1 );

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


        //down right
        if ((startPosition.getRow() - 1) >= 0 && (startPosition.getColumn() + 1) < 9) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);

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

        // up left
        if ((startPosition.getRow() + 1) < 9 && (startPosition.getColumn() - 1) >= 0) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1 , startPosition.getColumn() - 1);

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


        // down left
        if ((startPosition.getRow() - 1) >= 0 && (startPosition.getColumn() - 1) >= 0) {
            //save this position
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1 , startPosition.getColumn() - 1);

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


