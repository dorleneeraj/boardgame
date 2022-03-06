package com.board.games.domain.cell;

import com.board.games.domain.player.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link @SLBoardCellTest}
 */
class SLBoardCellTest {

    private static SLBoardCell slBoardCell;
    private static Token token1;
    private static Token token2;

    @BeforeAll
    static void setUp() {
        slBoardCell = new SLBoardCell(10);
        token1 = new Token(1, "Red", 0);
        token2 = new Token(2, "Yellow", 0);
    }

    @Test
    void acceptToken() {
        slBoardCell.acceptToken(token1);
        slBoardCell.acceptToken(token2);
        assertEquals(10, token1.getPosition());
        assertEquals(10, token2.getPosition());
        assertNotNull(slBoardCell.getCurrentTokensOnCell());
        assertEquals(2, slBoardCell.getCurrentTokensOnCell().size());
        assertEquals(token1, slBoardCell.getCurrentTokensOnCell().get(0));
        assertEquals(token2, slBoardCell.getCurrentTokensOnCell().get(1));
    }

    @Test
    void removeToken() {
        slBoardCell.removeToken(token1);
        slBoardCell.removeToken(token2);
        assertTrue(slBoardCell.getCurrentTokensOnCell().isEmpty());
    }

    @Test
    void getCellPosition() {
        assertEquals(10, slBoardCell.getCellPosition());
    }
}