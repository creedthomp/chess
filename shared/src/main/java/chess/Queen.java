package chess;

import java.util.Collection;

public class Queen {


    private ChessGame.TeamColor color;
    public Queen(ChessGame.TeamColor color) {
        this.color = color;
    }

    public Collection<ChessMove> finalMoves(ChessBoard board, ChessPosition myPosition) {

        MovesToMake movemade = new MovesToMake(color);
        Collection<ChessMove> diag =  movemade.diagMoves(board, myPosition);
        Collection<ChessMove> straight =  movemade.straightMoves(board, myPosition);
        diag.addAll(straight);
        return diag;
    }
}
