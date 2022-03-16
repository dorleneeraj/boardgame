package com.board.games.domain.cell;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.board.games.domain.token.Token.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
class SLFinalCellTest {

    private SLFinalCell finalCell;
    private SLBoardCell boardCell;
    private Token token;

    @BeforeEach
    void setUp() {
        boardCell = Mockito.spy(new SLBoardCell(100));
        finalCell = Mockito.spy(new SLFinalCell(boardCell));
    }

    @Test
    public void test_acceptTokenLuckyAdvance() throws Exception {
        token = new Token(1, TokenColour.GREEN, 95);
        Move move = finalCell.acceptToken(token);
        assertTrue(move instanceof SLMove);
        assertEquals(SLMoveType.ADVANCE_LUCKY_MOVE, move.getMoveAttribute(SLMove.MOVE_TYPE));
    }

    /**
     * This test validates the scenario where the final cell might be setup as the end cell for a ladder
     * On a usual board, this might not be the case
     */
    @Test
    public void test_acceptTokenLadderAdvance() throws Exception {
        LadderCell ladderCell = new LadderCell(new SLBoardCell(83), finalCell);
        token = new Token(1, TokenColour.GREEN, 80);
        Move move = ladderCell.acceptToken(token);
        assertTrue(move instanceof SLMove);
        assertEquals(SLMoveType.LADDER_ADVANCE, move.getMoveAttribute(SLMove.MOVE_TYPE));
        Mockito.verify(finalCell).acceptToken(token);
    }

}