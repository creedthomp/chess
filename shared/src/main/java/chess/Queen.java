package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Queen {
    private ChessGame.TeamColor teamColor;

    public Queen(ChessGame.TeamColor teamcolor) {
        this.teamColor = teamcolor;
    }

    public Collection<ChessMove> allMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            //up
            if ((startPosition.getRow() + i) < 8) {

                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn());

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
                    // can't move over a piece
                    break;
                }
            }
            else {
                break;
            }
        }


        for (int i = 1; i < 8; i++) {
            // right
            if ((startPosition.getColumn() + i) < 8) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);

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
                    // can't move over a piece
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int i = 1; i < 8; i++) {
            //left
            if ((startPosition.getColumn() - i) >= 0) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() , startPosition.getColumn() - i);

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
                    // can't move over a piece
                    break;

                }

            }
            else {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            // down
            if ((startPosition.getRow() - i) >= 0) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() );

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
                    // can't move over a piece
                    break;

                }

            }
            else {
                break;
            }
        }


        for (int i = 1; i < 8; i++) {
            //up right
            if ((startPosition.getRow() + i) < 8 || (startPosition.getColumn() - i) < 8) {

                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);

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
                    // can't move over a piece
                    break;

                }

            }
            else {
                break;
            }
        }


        for (int i = 1; i < 8; i++) {
            //down right
            if ((startPosition.getRow() - i) >= 0 || (startPosition.getColumn() + i) < 8) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() + i);

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
                    // can't move over a piece
                    break;

                }
            }
            else {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            // up left
            if ((startPosition.getRow() + i) < 8 || (startPosition.getColumn() - i) >= 0) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() - i);

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
                    // can't move over a piece
                    break;

                }

            }
            else {
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            // down left
            if ((startPosition.getRow() - i) >= 0 || (startPosition.getColumn() - i) >= 0) {
                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() - i, startPosition.getColumn() - i);

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
                    // can't move over a piece
                    break;

                }

            }
            else {
                break;
            }
        }

        // is this the right thing to return??
        return moves;
    }
}

