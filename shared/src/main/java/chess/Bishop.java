package chess;
import java.util.*;

public class Bishop {
    // do I need to do extends and override all the functions??
    private ChessGame.TeamColor teamColor;

    public Bishop(ChessGame.TeamColor teamcolor) {
        this.teamColor = teamcolor;
    }


    public Collection<ChessMove> allMoves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> moves = new HashSet<>();

        for (int i = 1; i < 9; i++) {
            //up right
            if ((startPosition.getRow() + i) < 9 && (startPosition.getColumn() + i) < 9) {

                //save this position
                ChessPosition endPosition = new ChessPosition(startPosition.getRow() + i, startPosition.getColumn() + i);

                // if there is on piece here
                if ((board.getPiece(endPosition) == null)) {

                    //add the piece to the array.   am I adding it in the right way?
                    moves.add(new ChessMove(startPosition, endPosition , null));
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


        for (int i = 1; i < 9; i++) {
            //down right
            if ((startPosition.getRow() - i) >= 1 && (startPosition.getColumn() + i) < 9) {
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
        for (int i = 1; i < 9; i++) {
            // up left
            if ((startPosition.getRow() + i) < 9 && (startPosition.getColumn() - i) >= 1) {
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
        for (int i = 1; i < 9; i++) {
            // down left
            if ((startPosition.getRow() - i) >= 1 && (startPosition.getColumn() - i) >= 1) {
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

    @Override
    public String toString() {
        return "Bishop{" +
                "teamColor=" + teamColor +
                '}';
    }
}


//    public boolean checkValidity(ChessBoard board, int i, ChessPosition startPosition) {
//        //if ((startPosition.getRow() - i) >= 1 && (startPosition.getRow() + i) < 9 && (startPosition.getColumn() - i) >= 1 && (startPosition.getColumn() + i) < 9) {
//            if (board.getPiece(startPosition) == null) { //if this spot on the board is empty
//                // this is a valid move. return true??
//            }
//            //trying to check if the piece in this spot is the same color as this piece
//            if (!Objects.equals(ChessBoard.getPiece(teamColor)))// How do I get the piece in this spot and its color
//        }
//    }

