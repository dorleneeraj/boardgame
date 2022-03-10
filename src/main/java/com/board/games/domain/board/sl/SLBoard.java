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

    protected SLBoard(Dimension boardDimension, List<SLBoardCell> boardCells) {
        super(boardDimension);
        validateBoard();
        this.boardCells = boardCells;
        this.boardSize = boardCells.size();
    }

    /**
     * <p>
     * Performs the required validation for the board
     * </p>
     */
    @Override
    protected void validateBoard() {
        Dimension boardDimension = getBoardDimension();
        if (null == boardDimension) {
            throw new RuntimeException("Board Dimension cannot be null for a Snake and Ladders game");
        }
        if (boardDimension.getRow() != boardDimension.getColumn()) {
            throw new RuntimeException("Snake and Ladders board is a square. Its row and column values should match");
        }
    }

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
