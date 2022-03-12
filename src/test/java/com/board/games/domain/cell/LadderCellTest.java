package com.board.games.domain.cell;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.token.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the {@link LadderCell}
 */
class LadderCellTest {

    private static LadderCell ladderCell;
    private static SLBoardCell boardCell;
    private static SLBoardCell ladderEndCell;
    private static Token token;

    @BeforeAll
    static void setUp() {
        boardCell = Mockito.spy(new SLBoardCell(10));
        ladderEndCell = Mockito.spy(new SLBoardCell(25));
        token = new Token(1, Token.TokenColour.RED, 0);
        ladderCell = Mockito.spy(new LadderCell(boardCell, ladderEndCell));
    }

    @Test
    void testAcceptToken() {
        Move move = ladderCell.acceptToken(token);
        assertNotNull(move);
        assertTrue(move instanceof SLMove);
        assertNotNull(move.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_MOVE));
        assertEquals(SLMoveType.LADDER_ADVANCE, move.getMoveAttribute(SLMove.MOVE_TYPE));
        InOrder order = Mockito.inOrder(ladderCell, boardCell, ladderEndCell);
        Mockito.verify(boardCell).acceptToken(token);
        Mockito.verify(ladderCell).removeToken(token);
        Mockito.verify(boardCell).removeToken(token);
        Mockito.verify(ladderEndCell).acceptToken(token);
        assertEquals(25, token.getPosition());
        assertTrue(boardCell.getCurrentTokensOnCell().isEmpty());
        assertTrue(ladderEndCell.getCurrentTokensOnCell().size() == 1);
        assertTrue(ladderEndCell.getCurrentTokensOnCell().contains(token));
    }

    @Test
    void testGetCurrentTokensOnCell() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            ladderCell.getCurrentTokensOnCell();
        });
    }
}