package com.board.games.domain.cell;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.SLMove;
import com.board.games.domain.player.Token;
import com.board.games.factory.MovesFactory;

import java.util.List;

/**
 * Ladder decorator for the BoardCell
 */
public class LadderCell implements Cell {
    protected Cell boardCell;
    protected Cell ladderEndCell;

    public LadderCell(Cell boardCell, Cell ladderEndCell) {
        this.boardCell = boardCell;
        this.ladderEndCell = ladderEndCell;
    }

    @Override
    public Move acceptToken(Token token) {
        Move intermediateMove = acceptTokenCurrentCell(token);
        removeToken(token);
        Move ladderAcceptMove = ladderEndCell.acceptToken(token);
       // Move ladderMove = MovesFactory.getLadderAdvanceMove(intermediateMove, ladderAcceptMove);
        return null;
    }

    protected Move acceptTokenCurrentCell(Token token) {
        Integer tokenInitialPosition = token.getPosition();
        return boardCell.acceptToken(token);
    }

    @Override
    public Boolean removeToken(Token token) {
        return boardCell.removeToken(token);
    }

    @Override
    public Integer getCellPosition() {
        return this.boardCell.getCellPosition();
    }

    @Override
    public List<Token> getCurrentTokensOnCell() {
        throw new RuntimeException("Ladder Cells can't have Tokens");
    }
}
