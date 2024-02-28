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
            if (startPosition.getRow() + 1 < 9 && startPosition.getColumn() + 1 < 9) {
                ChessPosition rightTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                ChessPiece right = board.getPiece(rightTakePosition);
            }
            if ((startPosition.getColumn() - 1) > 0) {
                ChessPosition leftTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                ChessPiece left = board.getPiece(leftTakePosition);
            }

            ChessPosition endPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            if ((board.getPiece(endPosition) == null)) {
                moves.addAll(promotionPiece(8, startPosition, endPosition, endPosition));
            }

            // take piece left
            if ((startPosition.getColumn() - 1) > 0) {
                ChessPosition leftTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
                ChessPiece left = board.getPiece(leftTakePosition);

                if ((board.getPiece(leftTakePosition) != null)) {
                    if (left.getTeamColor() != teamColor) {
                        moves.addAll(promotionPiece(1, startPosition, endPosition, leftTakePosition));
//
                    }
                }
            }
            //take piece right
            if (startPosition.getRow() + 1 < 9 && startPosition.getColumn() + 1 < 9) {
                ChessPosition rightTakePosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
                ChessPiece right = board.getPiece(rightTakePosition);

                if ((board.getPiece(rightTakePosition) != null)) {
                    if (right.getTeamColor() != teamColor) {
                        moves.addAll(promotionPiece(1, startPosition, endPosition, rightTakePosition));
//
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
            ChessPosition endPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            if ((board.getPiece(endPosition) == null)) {
                moves.addAll(promotionPiece(1, startPosition, endPosition, endPosition));
//
            }
            // take piece left
            if ((startPosition.getColumn() - 1) > 0 && (startPosition.getRow() -1) > 0) {
                ChessPosition leftTakePosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
                ChessPiece left = board.getPiece(leftTakePosition);

                if ((board.getPiece(leftTakePosition) != null)) {
                    if (left.getTeamColor() != teamColor) {
                        moves.addAll(promotionPiece(1, startPosition, endPosition, leftTakePosition));
//
                    }
                }
            }
            //take piece right
            if ((startPosition.getRow() - 1) > 0 && (startPosition.getColumn() + 1) < 9) {
                ChessPosition rightTakePosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
                ChessPiece right = board.getPiece(rightTakePosition);

                if ((board.getPiece(rightTakePosition) != null)) {
                    if (right.getTeamColor() != teamColor) {
                        moves.addAll(promotionPiece(1, startPosition, endPosition, rightTakePosition));
//
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

    public ArrayList<ChessMove> promotionPiece(int row, ChessPosition startPosition, ChessPosition endPosition, ChessPosition takePosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (endPosition.getRow() == row) {
            moves.add(new ChessMove(startPosition, takePosition, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(startPosition, takePosition, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(startPosition, takePosition, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(startPosition, takePosition, ChessPiece.PieceType.KNIGHT));
        } else {
            moves.add(new ChessMove(startPosition, takePosition, null));
        }
        return moves;
    }
}
