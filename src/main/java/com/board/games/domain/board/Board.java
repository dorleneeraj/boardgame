package com.board.games.domain.board;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.game.BoardGame;

/**
 * <p>
 * A representation of a board used by the {@link BoardGame}
 * </p>
 */
public abstract class Board {
    private final Dimension boardDimension;

    protected Board(Dimension boardDimension) {
        this.boardDimension = boardDimension;
    }

    /**
     * <p>
     * Gets the respective {@link Cell} located at the given position. Validates whether the provided position is within
     * the range bounded by the Board dimension
     * </p>
     *
     * @param cellNumber
     * @return
     */
    public abstract Cell getCellByNumber(int cellNumber);

    public abstract Integer getBoardSize();

    protected abstract void validateBoard();

    public Dimension getBoardDimension() {
        return boardDimension;
    }
}
