package chess;

import java.util.ArrayList;
import java.util.Collection;

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
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = null;

        switch (this.type) {
            case BISHOP:
                Bishop bishop = new Bishop(this.pieceColor);
                moves = bishop.allMoves(board, myPosition);
                break;
            case KING:
                King king = new King(this.pieceColor);
                moves = king.allMoves(board, myPosition);
                break;
            case KNIGHT:
                Knight knight = new Knight(this.pieceColor);
                moves = knight.allMoves(board, myPosition);
                break;
            case QUEEN:
                Queen queen = new Queen(this.pieceColor);
                moves = queen.allMoves(board, myPosition);
                break;
            case ROOK:
                Rook rook = new Rook(this.pieceColor);
                moves = rook.allMoves(board, myPosition);
                break;
            // case PAWN:
            //     Pawn pawn = new Pawn(this.pieceColor);
            //     moves = pawn.allMoves(board, myPosition);
            //     break;
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
}

