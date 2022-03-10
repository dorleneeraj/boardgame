package com.board.games.domain.cell.sl;

import com.board.games.domain.move.Move;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.MovesFactory;

import java.util.List;

/**
 *
 */
public class LadderCell extends SLBoardCell {
    protected SLBoardCell boardCell;
    protected SLBoardCell ladderEndCell;

    public LadderCell(SLBoardCell boardCell, SLBoardCell ladderEndCell) {
        this.boardCell = boardCell;
        this.ladderEndCell = ladderEndCell;
    }

    @Override
    public Move acceptToken(Token token) {
        Move intermediateMove = boardCell.acceptToken(token);
        removeToken(token);
        Move ladderAcceptMove = ladderEndCell.acceptToken(token);
        return MovesFactory.getLadderAdvanceMove(intermediateMove, ladderAcceptMove);
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
        throw new RuntimeException("Ladder Cells can't have Tokens! Tokens are pushed up!");
    }
}
