package chess;

import java.util.ArrayList;
import java.util.Collection;
public class Pawn {
    private ChessGame.TeamColor teamColor;

    public Pawn(ChessGame.TeamColor teamcolor) {
        this.teamColor = teamcolor;
    }

    /**
     * find all the moves this Pawn can make. Pawn goes forward but does not take forward, only diagnol. remember start and promotion.
     *
     * @param board and the start position
     * @return an array of the valid moves
     */
    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //team white
        if (teamColor == ChessGame.TeamColor.WHITE) {
            ChessPosition leftTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            ChessPosition rightTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            ChessPiece left = board.getPiece(leftTakePosition);
            ChessPiece right = board.getPiece(rightTakePosition);
            // one spot forward
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            if ((board.getPiece(endPosition) == null)) {
                if (endPosition.getRow() == 8) {
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                }
                else {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }            }
            // take piece left
            if ((board.getPiece(leftTakePosition) != null)) {
                if (left.getTeamColor() != teamColor) {
                    if (endPosition.getRow() == 1) {
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(startPosition, leftTakePosition, null));
                    }
                }
            }
            //take piece right
            if ((board.getPiece(rightTakePosition) != null)) {
                if (right.getTeamColor() != teamColor) {
                    if (endPosition.getRow() == 1) {
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(startPosition, rightTakePosition, null));
                    }
                }
            }

            // starting move.
            if (startPosition.getRow() == 2) {
                //normal move
                ChessPosition alternatePosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                if ((board.getPiece(endPosition) == null) && (board.getPiece(alternatePosition) == null)) {
                    moves.add(new ChessMove(startPosition, alternatePosition, null));
                }
            }
        }



        // team black
        if (teamColor == ChessGame.TeamColor.BLACK) {
            ChessPosition leftTakePosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            ChessPosition rightTakePosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            ChessPiece left = board.getPiece(leftTakePosition);
            ChessPiece right = board.getPiece(rightTakePosition);
            // one spot forward
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            if ((board.getPiece(endPosition) == null)) {
                if (endPosition.getRow() == 1) {
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT));
                }
                else {
                    moves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
            // take piece left
            if ((board.getPiece(leftTakePosition) != null)) {
                if (left.getTeamColor() != teamColor) {
                    if (endPosition.getRow() == 1) {
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(startPosition, leftTakePosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(startPosition, leftTakePosition, null));
                    }
                }
            }
            //take piece right
            if ((board.getPiece(rightTakePosition) != null)) {
                if (right.getTeamColor() != teamColor) {
                    if (endPosition.getRow() == 1) {
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(startPosition, rightTakePosition, ChessPiece.PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(startPosition, rightTakePosition, null));
                    }
                }
            }

            // starting move.
            if (startPosition.getRow() == 7) {
                //normal move
                ChessPosition alternatePosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                if ((board.getPiece(endPosition) == null) && (board.getPiece(alternatePosition) == null)) {
                    moves.add(new ChessMove(startPosition, alternatePosition, null));
                }
            }
        }
        return moves;
    }
}
