package com.board.games.domain.board.sl;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.Dimension;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.sl.SLBoardCell;
import com.board.games.domain.game.SnakeAndLadder;

import java.util.List;

/**
 * <p>
 * Implementation of {@link Board} for the {@link SnakeAndLadder} game
 * </p>
 */
public class SLBoard extends Board {

    private final int boardSize;
    private final List<SLBoardCell> boardCells;


    public SLBoard(Dimension boardDimension, List<SLBoardCell> boardCells) {
        super(boardDimension);
        this.boardCells = boardCells;
        this.boardSize = boardCells.size();
        validateBoard();
    }

    /**
     * <p>
     * Performs the required validation for the board
     * </p>
     */
    protected void validateBoard() {
        Dimension dimension = getDimension();
        if (null == dimension) {
            throw new RuntimeException("Board Dimension cannot be null for a Snake and Ladders game");
        }
        if (dimension.getRow() != dimension.getColumn()) {
            throw new RuntimeException("Snake and Ladders board is a square. Its row and column values should match");
        }
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
    @Override
    public Cell getCellByNumber(int cellNumber) {
        if (cellNumber >= 1 && cellNumber <= this.boardSize) {
            return this.boardCells.get(cellNumber - 1);
        }
        return null;

    }

    @Override
    public Integer getBoardSize() {
        return this.boardSize;
    }

}
