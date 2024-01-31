package chess;

import java.util.Collection;

public class Rook {

    private ChessGame.TeamColor color;
    public Rook(ChessGame.TeamColor color) {
        this.color = color;
    }

    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition myPosition) {

        MovesToMake movemade = new MovesToMake(color);
        return movemade.straightMoves(board, myPosition);
    }
}
