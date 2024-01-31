package chess;

import java.util.Collection;
import java.util.HashSet;

public class King {

    private ChessGame.TeamColor color;
    public King(ChessGame.TeamColor color) {
        this.color = color;
    }

    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        MovesToMake movemade = new MovesToMake(color);
        if (movemade.oneMove(board, myPosition, 1, 1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 1, 1)));
        }
        if (movemade.oneMove(board, myPosition, -1, -1) != null) {
            moves.add((movemade.oneMove(board, myPosition, -1, -1)));
        }
        if (movemade.oneMove(board, myPosition, 1, -1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 1, -1)));
        }
        if (movemade.oneMove(board, myPosition, -1, 1) != null) {
            moves.add((movemade.oneMove(board, myPosition, -1, 1)));
        }
        if (movemade.oneMove(board, myPosition, 0, 1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 0, 1)));
        }
        if (movemade.oneMove(board, myPosition, 0, -1) != null) {
            moves.add((movemade.oneMove(board, myPosition, 0, -1)));
        }
        if (movemade.oneMove(board, myPosition, 1, 0) != null) {
            moves.add((movemade.oneMove(board, myPosition, 1, 0)));
        }
        if (movemade.oneMove(board, myPosition, -1, 0) != null) {
            moves.add((movemade.oneMove(board, myPosition, -1, 0)));
        }
        return moves;
    }
}
