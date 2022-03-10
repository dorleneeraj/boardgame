package com.board.games.domain.board;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.game.BoardGame;

import java.util.List;

/**
 * <p>
 * A representation of a board used by the {@link BoardGame}
 * </p>
 */
public class Board {

    private final int boardSize;
    private final List<? extends Cell> boardCells;
    private final Dimension dimension;

    protected Board(Dimension boardDimension, List<? extends Cell> boardCells) {
        this.dimension = boardDimension;
        this.boardCells = boardCells;
        this.boardSize = boardCells.size();
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
    public Cell getCellByNumber(int cellNumber) {
        if (cellNumber >= 1 && cellNumber <= this.boardSize) {
            return this.boardCells.get(cellNumber - 1);
        }
        return null;

    }

    public Integer getBoardSize() {
        return this.boardSize;
    }
}
