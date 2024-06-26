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
    public Status state;
    public enum Status {
        PLAYING,
        OVER
    }
    public ChessGame() {
    this.turn = TeamColor.WHITE;
    this.board = new ChessBoard();
    state = Status.PLAYING;
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
        //ChessBoard newboard = cloneBoard(board);
        if (board.getPiece(startPosition) != null) {
            ChessPiece thisPiece = board.getPiece(startPosition);
           Collection<ChessMove> possibleMoves = thisPiece.pieceMoves(this.board, startPosition);
            Iterator<ChessMove> iterator = possibleMoves.iterator();
            while (iterator.hasNext()) {
                ChessBoard newboard = cloneBoard(board);
                ChessMove move = iterator.next();

               // now check if a piece moves and that puts the king in check
               board.addPiece(move.getEndPosition(), thisPiece);
               board.addPiece(move.getStartPosition(), null);
               // I can make the move on this board and then set the board back to the copy I made bc thats how it was originally
               if (isInCheck(thisPiece.getTeamColor())) {
                   iterator.remove();
               }
               board = newboard;
           }
            return possibleMoves;
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
        ChessPosition start = move.getStartPosition();
        Collection<ChessMove> validMoves = validMoves(start);

        if (validMoves == null) {
            throw new InvalidMoveException();
        }
        if (board.getPiece(move.getStartPosition()).getTeamColor() != turn) {
            throw new InvalidMoveException();
        }
        // if the move is valid
        if (((validMoves.contains(move)))) {
            //ChessBoard newboard = cloneBoard(board);
            ChessPiece thisPiece = board.getPiece(move.getStartPosition());
            // add the piece
            if (thisPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                if (move.getPromotionPiece() != null) {
                    board.addPiece(move.getEndPosition(), new ChessPiece(turn, move.getPromotionPiece()));
                    board.addPiece(move.getStartPosition(), null);
                }
                else {
                    board.addPiece(move.getEndPosition(), thisPiece);
                    // this will remove the piece we just moved from its former position
                    board.addPiece(move.getStartPosition(), null);
                }

            }
            else {
                board.addPiece(move.getEndPosition(), thisPiece);
                // this will remove the piece we just moved from its former position
                board.addPiece(move.getStartPosition(), null);
            }

            if (!((validMoves.contains(move)))) {
                throw new InvalidMoveException();
            }

        }

        if (!((validMoves.contains(move)))) {
            throw new InvalidMoveException();
        }

        if (turn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

            Collection<ChessMove> opMoves = opponentsMoves(board, teamColor);
            ChessPosition kingSpot = myKingPosition(board, teamColor);
            for (ChessMove move : opMoves) {
                if (move.getEndPosition().equals(kingSpot)) {
                    return true;
                }
            }

     //   }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition kingPosition = myKingPosition(board, teamColor);
        Collection<ChessMove> moves = validMoves(kingPosition);
        if (moves.isEmpty()) {
            if (isInCheck(teamColor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessPosition kingPosition = myKingPosition(board, teamColor);
        Collection<ChessMove> moves = validMoves(kingPosition);
        ChessPiece king = board.getPiece(kingPosition);
        if (moves.isEmpty()) {
            if (!isInCheck(teamColor)) {
                if (!(king.pieceMoves(board, kingPosition).isEmpty())) {
                    return true;
                }

            }
        }
        return false;
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


    public Collection<ChessMove> opponentsMoves(ChessBoard board, TeamColor color) {
        Collection<ChessMove> opMoves = new HashSet<>();
        //gather all opponents moves
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currentposition = new ChessPosition(i, j);
                // if the piece there is the opponents

                if (board.getPiece(currentposition) != null && board.getPiece(currentposition).getTeamColor() != color) {
                    ChessPiece piece = board.getPiece(currentposition);
                    // add ALL the moves of that piece to the set
                    opMoves.addAll(piece.pieceMoves(board, currentposition));
                }
            }
        }
        return opMoves;
    }

    public ChessPosition myKingPosition(ChessBoard board, TeamColor color) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currentposition = new ChessPosition(i, j);
                // do I have to seperate these ifs so I dont check if a null has a color
                if (board.getPiece(currentposition) != null && board.getPiece(currentposition).getTeamColor() == color && board.getPiece(currentposition).getPieceType() == ChessPiece.PieceType.KING) {
                    return currentposition;
                }
                // this should never happen
            }
        }
        return null;
    }


    public ChessBoard cloneBoard(ChessBoard board) {
        // clone the board
        ChessBoard newboard = new ChessBoard();

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition spot = new ChessPosition(j, i);
                if (board.getPiece(spot) != null) {
                    newboard.addPiece(spot, board.getPiece(spot));
                }
            }
        }
        return newboard;
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
