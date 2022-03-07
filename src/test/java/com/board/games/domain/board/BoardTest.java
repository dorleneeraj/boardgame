package com.board.games.domain.board;

import com.board.games.domain.cell.LadderCell;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.SnakeCell;
import com.board.games.strategy.BoardGenerationStrategy;
import com.board.games.strategy.DefaultBoardGenerationStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Performs simple tests for {@link Board} and for {@link Dimension}.
 * Validates the {@link DefaultBoardGenerationStrategy}
 */
class BoardTest {

    @Test
    public void test_validateAutoGeneratedBoard() {
        BoardGenerationStrategy strategy = new DefaultBoardGenerationStrategy();
        Board board = strategy.generateBoard();

        assertEquals(100, board.getBoardSize());
        assertNotNull(board.getCellByNumber(95));
        assertNull(board.getCellByNumber(101));
        assertTrue(board.getCellByNumber(99) instanceof SnakeCell);
        assertTrue(board.getCellByNumber(42) instanceof LadderCell);
        assertTrue(board.getCellByNumber(3) instanceof SLBoardCell);
    }

}