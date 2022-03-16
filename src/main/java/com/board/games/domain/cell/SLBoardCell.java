package com.board.games.domain.cell;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.game.SLGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.token.Token;
import com.board.games.exception.GameException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * A board cell for {@link SLGame}
 * </p>
 */
public class SLBoardCell implements Cell {

    private final Integer cellNumber;
    private final List<SLBoardCell> neighbours = new ArrayList<>();

    private final Set<Token> currentTokensOnCell = new HashSet<>();  // The 
    // list of all the Tokens the current cell has

    public SLBoardCell(Integer cellNumber) {
        this.cellNumber = cellNumber;
    }

    @Override
    public Move acceptToken(Token token) throws GameException {
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
    @JacocoExcludeGenerated
    public Integer getCellPosition() {
        return cellNumber;
    }

    @JacocoExcludeGenerated
    public List<Token> getCurrentTokensOnCell() throws GameException {
        return new ArrayList<>(currentTokensOnCell);
    }

    @Override
    @JacocoExcludeGenerated
    public List<? extends Cell> getNeighbours() {
        return this.neighbours;
    }

    @Override
    @JacocoExcludeGenerated
    public void setNeighbours(List<? extends Cell> neighbours) {
        neighbours.forEach(neighbour -> this.neighbours.add((SLBoardCell) neighbour));
    }

    /**
     *
     */
    @JacocoExcludeGenerated
    public boolean hasASnakeNeighbours() {
        return neighbours.stream().anyMatch(neighbour -> neighbour instanceof SnakeCell);
    }

}
