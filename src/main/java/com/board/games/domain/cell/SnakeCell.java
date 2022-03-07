package com.board.games.domain.cell;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.SLMove;
import com.board.games.domain.player.Token;
import com.board.games.factory.MovesFactory;

import java.util.List;

/**
 * Snake decorator for BoardCell
 */
public class SnakeCell implements Cell {

    private Cell boardCell;
    private Cell snakeEndCell;

    public SnakeCell(Cell boardCell, Cell snakeEndCell) {
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
