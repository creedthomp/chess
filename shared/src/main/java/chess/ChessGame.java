package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor turn;
    private ChessBoard board;
    public ChessGame() {
    this.turn = TeamColor.WHITE;
    this.board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) != null) {
            ChessPiece thisPiece = board.getPiece(startPosition);
           return thisPiece.pieceMoves(this.board, startPosition);

        }
        else {return null;}
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // do I need to do something about the invalid move exception??
        ChessPiece thisPiece = board.getPiece(move.getStartPosition());
        // add the piece
        board.addPiece(move.getEndPosition(), thisPiece);
        // this will remove the piece we just moved from its former position
        board.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if (turn == TeamColor.WHITE){
            Collection<ChessMove> opMoves = opponentsMoves(board, teamColor, 1, 1);
            ChessPosition kingSpot = myKingPosition(board, teamColor, 1, 1);
            // now go through all of the ending positions of the opMoves and se if any of them match the kings current pos
            for (ChessMove move : opMoves) {
                if (move.getEndPosition() == kingSpot) {
                    return true;
                }
            }
        }
        else {
            // increment opMoves by -1. starting row 8
            Collection<ChessMove> opMoves = opponentsMoves(board, teamColor, -1, 8);
            ChessPosition kingSpot = myKingPosition(board, teamColor, -1, 8);
            for (ChessMove move : opMoves) {
                if (move.getEndPosition() == kingSpot) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
       this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    public Collection<ChessMove> opponentsMoves(ChessBoard board, TeamColor Color, int rowincrement, int startingrow) {
        Collection<ChessMove> opMoves = new HashSet<>();
        //gather all opponents moves
        for (int i = 1; i < 9; i++) {
            ChessPosition currentposition = new ChessPosition(startingrow, i);
            // if the piece there is the opponents
            // do I need to make sure it is in bounds?? probably not
            if (board.getPiece(currentposition) != null && board.getPiece(currentposition).getTeamColor() != Color) {
                ChessPiece piece = board.getPiece(currentposition);
                // add ALL the moves of that piece to the set
                opMoves.addAll(piece.pieceMoves(board, currentposition));
            }
            startingrow += rowincrement;
        }
        return opMoves;
    }

    public ChessPosition myKingPosition(ChessBoard board, TeamColor Color, int rowincrement, int startingrow) {
        for (int i = 1; i < 9; i++) {
            ChessPosition currentposition = new ChessPosition(startingrow, i);
            // do I have to seperate these ifs so I dont check if a null has a color
            if (board.getPiece(currentposition) != null && board.getPiece(currentposition).getTeamColor() == Color && board.getPiece(currentposition).getPieceType() == ChessPiece.PieceType.KING) {
                return currentposition;
            }
            // this should never happen
            else {return null;}
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "turn=" + turn +
                ", board=" + board +
                '}';
    }
}
