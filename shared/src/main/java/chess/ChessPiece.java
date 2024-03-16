package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final PieceType type;
    private final ChessGame.TeamColor pieceColor;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
        //return ChessGame.TeamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     * @param board and myPosition
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = null;

        switch (this.type) {
            case BISHOP:
                chess.Bishop bishop = new chess.Bishop(pieceColor);
                moves = bishop.finalMoves(board, myPosition);
                break;

            case KING:
                chess.King king = new chess.King(pieceColor);
                moves = king.finalMoves(board, myPosition);
                break;

            case ROOK:
                chess.Rook rook = new chess.Rook(pieceColor);
                moves = rook.finalMoves(board, myPosition);
                break;

            case KNIGHT:
                chess.Knight knight = new chess.Knight(pieceColor);
                moves = knight.finalMoves(board, myPosition);
                break;

            case PAWN:
                chess.Pawn pawn = new chess.Pawn(pieceColor);
                moves = pawn.finalMoves(board, myPosition);
                break;

            case QUEEN:
                chess.Queen queen = new chess.Queen(pieceColor);
                moves = queen.finalMoves(board, myPosition);
                break;
        }
        return moves;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "type=" + type +
                ", pieceColor=" + pieceColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }
}

