package chess;
import java.util.*;

public class Bishop {
    private ChessGame.TeamColor teamColor;

    public Bishop(ChessGame.TeamColor teamcolor) {
        this.teamColor = teamcolor;
    }


    public Collection<ChessMove> allMoves(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (Objects.equals(teamColor, "w")) {
            for (int i = 1; i < 8; i++) {
                // see if move is valid
                if (checkValidity(board, i, endPosition )) {
                    moves.add()
                }
            }
            }

            if (Objects.equals(teamColor, "b")) {

            }

        }
    }

    public boolean checkValidity(ChessBoard board, int i, ChessPosition endPosition) {
        if ((endPosition.getRow() - i) >= 0 && (endPosition.getRow() + i) < 8 && (endPosition.getColumn() - i) >= 0 && (endPosition.getColumn() + i) < 8) {
            if (board.getPiece(endPosition) == null) { //if this spot on the board is empty
                // this is a valid move. return true??
            }
            if (!Objects.equals(ChessBoard.getPiece(teamColor)) )// How do I get the piece in this spot and its color
        }
    }

