package com.board.games.domain.cell.sl;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.game.SnakeAndLadder;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.sl.SLMovesFactory;
import com.board.games.domain.token.Token;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * A board cell for {@link SnakeAndLadder}
 * </p>
 */
public class SLBoardCell implements Cell {

    private Integer cellNumber;

    // The list of all the Tokens the current cell has
    private final Set<Token> currentTokensOnCell = new HashSet<>();

    public SLBoardCell() {
    }

    public SLBoardCell(Integer cellNumber) {
        this.cellNumber = cellNumber;
    }

    @Override
    public Move acceptToken(Token token) {
        Integer currentPosition = token.getPosition();
        token.setPosition(cellNumber);
        currentTokensOnCell.add(token);
        return SLMovesFactory.getAdvanceMove(currentPosition, cellNumber);
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
        return new ArrayList<>(currentTokensOnCell);
    }
}
