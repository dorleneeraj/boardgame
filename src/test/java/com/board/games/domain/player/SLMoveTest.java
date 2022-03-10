package com.board.games.domain.player;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.MoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.MovesFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link SLMove}
 */
class SLMoveTest {

    @Test
    public void test_basicSLMoveData() {
        SLMove slMove = new SLMove(MoveType.NORMAL_ADVANCE, 10, 15, "Normal Advance Move");
        assertEquals(10, slMove.getFromPosition());
        assertEquals(15, slMove.getToPosition());
        assertEquals(5, slMove.getTotalTilesMoved());
        assertEquals(0, slMove.getTotalTilesClimbed());
        assertEquals(0, slMove.getTotalTilesDescended());
        assertEquals(0, slMove.getIntermediatePosition());
        assertNull(slMove.getInterMediateMove());
        assertEquals("Normal Advance Move", slMove.getMoveComments());
    }

    @Test
    public void test_SLMoveSettersGetters() {
        SLMove slMove = new SLMove(MoveType.NORMAL_ADVANCE, 10, 15, "Normal Advance Move");
        SLMove intermediateStep = (SLMove) MovesFactory.getAdvanceMove(45, 60);

        slMove.setTotalTilesClimbed(8);
        slMove.setTotalTilesDescended(10);
        slMove.setIntermediatePosition(14);
        slMove.setTotalTilesMoved(50);
        slMove.setInterMediateMove(intermediateStep);

        assertEquals(8, slMove.getTotalTilesClimbed());
        assertEquals(8, slMove.getMoveAttribute(Move.MOVE_TOTAL_CLIMBED));

        assertEquals(10, slMove.getTotalTilesDescended());
        assertEquals(10, slMove.getMoveAttribute(Move.MOVE_TOTAL_DESCENDED));

        assertEquals(14, slMove.getIntermediatePosition());
        assertEquals(14, slMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_POSITION));

        assertEquals(50, slMove.getTotalTilesMoved());
        assertEquals(50, slMove.getMoveAttribute(Move.MOVE_TOTAL_TILES));

        assertEquals(intermediateStep, slMove.getInterMediateMove());
        assertEquals(intermediateStep, slMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE));
    }
}