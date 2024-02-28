package chess;

import java.util.Collection;

public class Bishop {
    private ChessGame.TeamColor color;
    public Bishop(ChessGame.TeamColor color) {
        this.color = color;
    }

    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition myPosition) {

        MovesToMake movemade = new MovesToMake(color);
        return movemade.diagMoves(board, myPosition);
    }
}
