package chess;

import java.util.Collection;
import java.util.HashSet;

public class Knight {
    private ChessGame.TeamColor color;
    public Knight(ChessGame.TeamColor color) {
        this.color = color;
    }

    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        MovesToMake movemade = new MovesToMake(color);
        if (movemade.oneMove(board, myPosition, 2, 1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 2, 1)));
        }
        if (movemade.oneMove(board, myPosition, -2, -1) != null) {
            moves.add((movemade.oneMove(board, myPosition, -2, -1)));
        }
        if (movemade.oneMove(board, myPosition, 1, -2) != null) {
            moves.add((movemade.oneMove(board, myPosition, 1, -2)));
        }
        if (movemade.oneMove(board, myPosition, -1, 2) != null) {
            moves.add((movemade.oneMove(board, myPosition, -1, 2)));
        }
        if (movemade.oneMove(board, myPosition, 1, 2) != null) {
            moves.add((movemade.oneMove(board, myPosition, 1, 2)));
        }
        if (movemade.oneMove(board, myPosition, -1, -2) != null) {
            moves.add((movemade.oneMove(board, myPosition, -1, -2)));
        }
        if (movemade.oneMove(board, myPosition, -2, 1) != null) {
            moves.add((movemade.oneMove(board, myPosition, -2, 1)));
        }
        if (movemade.oneMove(board, myPosition, 2, -1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 2, -1)));
        }
        return moves;
    }
}
