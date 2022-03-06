package com.board.games.domain.cell;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.MoveType;
import com.board.games.domain.player.SLMove;
import com.board.games.domain.player.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

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
        token = new Token(1, "Red", 0);
        ladderCell = Mockito.spy(new LadderCell(boardCell, ladderEndCell));
    }

    @Test
    void testAcceptToken() {
        Move move = ladderCell.acceptToken(token);
        assertNotNull(move);
        assertTrue(move instanceof SLMove);
        assertNotNull(move.getMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE));
        assertEquals(MoveType.LADDER_ADVANCE, move.getMoveAttribute(Move.MOVE_TYPE));
        InOrder order = Mockito.inOrder(ladderCell, boardCell, ladderEndCell);
        Mockito.verify(boardCell).acceptToken(token);
        Mockito.verify(ladderCell).removeToken(token);
        Mockito.verify(ladderEndCell).acceptToken(token);
        assertEquals(25, token.getPosition());
        assertTrue(boardCell.getCurrentTokensOnCell().isEmpty());
        assertTrue(ladderEndCell.getCurrentTokensOnCell().size() == 1);
        assertEquals(token, ladderEndCell.getCurrentTokensOnCell().get(0));
    }

    @Test
    void testGetCurrentTokensOnCell() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            ladderCell.getCurrentTokensOnCell();
        });
    }
}