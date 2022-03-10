package com.board.games.domain.cell.sl;

import com.board.games.domain.move.Move;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.MovesFactory;

import java.util.List;

/**
 * Snake decorator for BoardCell
 */
public class SnakeCell extends SLBoardCell {

    private SLBoardCell boardCell;
    private SLBoardCell snakeEndCell;

    public SnakeCell(SLBoardCell boardCell, SLBoardCell snakeEndCell) {
        this.boardCell = boardCell;
        this.snakeEndCell = snakeEndCell;
    }

    @Override
    public Move acceptToken(Token token) {
        Move intermediateMove = boardCell.acceptToken(token);
        removeToken(token);
        Move snakeStep = snakeEndCell.acceptToken(token);
        return MovesFactory.getSnakeDescendMove(intermediateMove, snakeStep);
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
        throw new RuntimeException("Snake Cells can't have Tokens! Tokens get eaten");
    }
}
