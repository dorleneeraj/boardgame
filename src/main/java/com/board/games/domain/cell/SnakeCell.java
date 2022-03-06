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

    Cell boardCell;
    Cell snakeEndCell;

    public SnakeCell(Cell boardCell, Cell snakeEndCell) {
        this.boardCell = boardCell;
        this.snakeEndCell = snakeEndCell;
    }

    @Override
    public Move acceptToken(Token token) {
        Integer tokenInitialPosition = token.getPosition();
        Move intermediateMove = boardCell.acceptToken(token);
        Integer tokenIntermediatePosition = token.getPosition();
        removeToken(token);
        snakeEndCell.acceptToken(token);
        Integer tokenFinalPosition = token.getPosition();
        Move snakeMove = MovesFactory.getSnakeDescendMove(tokenInitialPosition, tokenFinalPosition);
        snakeMove.addMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE, intermediateMove);
        snakeMove.addMoveAttribute(Move.MOVE_TOTAL_DESCENDED, Math.abs(tokenIntermediatePosition - tokenFinalPosition));
        return snakeMove;
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
        throw new RuntimeException("Snake Cells can't have Tokens");
    }
}
