package com.board.games.domain.cell;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.token.Token;
import com.board.games.domain.token.TokenColour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnakeCellTest {

    private static SnakeCell snakeCell;
    private static SLBoardCell boardCell;
    private static SLBoardCell snakeEndCell;
    private static Token token;

    @BeforeAll
    static void setUp() {
        boardCell = Mockito.spy(new SLBoardCell(35));
        snakeEndCell = Mockito.spy(new SLBoardCell(20));
        token = new Token(1, TokenColour.GREEN, 30);
        snakeCell = Mockito.spy(new SnakeCell(boardCell, snakeEndCell));
    }


    @Test
    void acceptToken() {
        Move move = snakeCell.acceptToken(token);
        assertNotNull(move);
        assertTrue(move instanceof SLMove);
        assertNotNull(move.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_MOVE));
        assertEquals(SLMoveType.SNAKE_DESCEND, move.getMoveAttribute(SLMove.MOVE_TYPE));
        InOrder order = Mockito.inOrder(snakeCell, boardCell, snakeEndCell);
        Mockito.verify(boardCell).acceptToken(token);
        Mockito.verify(snakeCell).removeToken(token);
        Mockito.verify(boardCell).removeToken(token);
        Mockito.verify(snakeEndCell).acceptToken(token);
        assertEquals(20, token.getPosition());
        assertTrue(boardCell.getCurrentTokensOnCell().isEmpty());
        assertTrue(snakeEndCell.getCurrentTokensOnCell().size() == 1);
        assertTrue(snakeEndCell.getCurrentTokensOnCell().contains(token));
    }

    @Test
    void removeToken() {
    }

    @Test
    void getCellPosition() {
    }

    @Test
    void getCurrentTokensOnCell() {
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            snakeCell.getCurrentTokensOnCell();
        });
    }
}