package com.board.games.domain.cell;

import com.board.games.domain.game.SnakeAndLadder;
import com.board.games.domain.player.Move;
import com.board.games.domain.player.Token;
import com.board.games.domain.board.Board;
import com.board.games.factory.MovesFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A board cell for {@link SnakeAndLadder}
 */
public class SLBoardCell implements Cell {

    private Integer cellNumber;
    private final List<Token> currentTokensOnCell = new ArrayList<>();

    public SLBoardCell(Integer cellNumber) {
        this.cellNumber = cellNumber;
    }

    @Override
    public Move acceptToken(Token token) {
        Integer currentPosition = token.getPosition();
        token.setPosition(cellNumber);
        currentTokensOnCell.add(token);
        return MovesFactory.getAdvanceMove(currentPosition, cellNumber);
    }

    @Override
    public Boolean removeToken(Token token) {
        return currentTokensOnCell.remove(token);
    }

    @Override
    public Integer getCellPosition() {
        return cellNumber;
    }

    public List<Token> getCurrentTokensOnCell() {
        return currentTokensOnCell;
    }
}
