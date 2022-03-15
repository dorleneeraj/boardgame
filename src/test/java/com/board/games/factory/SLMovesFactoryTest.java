package com.board.games.factory;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMovesFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link SLMovesFactory}
 */
class SLMovesFactoryTest {

    @Test
    void getAdvanceMove() {
        Move move = SLMovesFactory.getAdvanceMove(10, 15);
        assertEquals(5, move.getMoveAttribute(SLMove.MOVE_TOTAL_TILES));
        assertEquals(10, move.getMoveAttribute(SLMove.MOVE_FROM_POSITION));
        assertEquals(15, move.getMoveAttribute(SLMove.MOVE_TO_POSITION));
        assertEquals(SLMoveType.NORMAL_ADVANCE.toString(), move.getMoveAttribute(SLMove.MOVE_TYPE).toString());
        assertEquals("Normal Token Advance", move.getMoveAttribute(SLMove.MOVE_EXTRA_DETAILS).toString());
        assertNull(move.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_MOVE));
        assertEquals(0, move.getMoveAttribute(SLMove.MOVE_TOTAL_CLIMBED));
        assertEquals(0, move.getMoveAttribute(SLMove.MOVE_TOTAL_DESCENDED));
        assertEquals(0, move.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_POSITION));
    }

    @Test
    void getLadderAdvanceMove() throws Exception {
        Move intermediateStep = SLMovesFactory.getAdvanceMove(10, 15);
        Move ladderStep = SLMovesFactory.getAdvanceMove(15, 32);
        Move ladderMove = SLMovesFactory.getLadderAdvanceMove(intermediateStep, ladderStep);
        assertEquals(10, ladderMove.getMoveAttribute(SLMove.MOVE_FROM_POSITION));
        assertEquals(32, ladderMove.getMoveAttribute(SLMove.MOVE_TO_POSITION));
        assertEquals(15, ladderMove.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_POSITION));
        assertEquals(22, ladderMove.getMoveAttribute(SLMove.MOVE_TOTAL_TILES));
        assertEquals(17, ladderMove.getMoveAttribute(SLMove.MOVE_TOTAL_CLIMBED));
        assertEquals(intermediateStep, ladderMove.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_MOVE));
        assertEquals(SLMoveType.LADDER_ADVANCE, ladderMove.getMoveAttribute(SLMove.MOVE_TYPE));
        assertEquals("Encountered Ladder at position:15", ladderMove.getMoveAttribute(SLMove.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getSnakeDescendMove() throws Exception {
        Move intermediateStep = SLMovesFactory.getAdvanceMove(55, 58);
        Move snakeStep = SLMovesFactory.getAdvanceMove(58, 23);
        Move snakeMove = SLMovesFactory.getSnakeDescendMove(intermediateStep, snakeStep);
        assertEquals(55, snakeMove.getMoveAttribute(SLMove.MOVE_FROM_POSITION));
        assertEquals(23, snakeMove.getMoveAttribute(SLMove.MOVE_TO_POSITION));
        assertEquals(58, snakeMove.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_POSITION));
        assertEquals(38, snakeMove.getMoveAttribute(SLMove.MOVE_TOTAL_TILES));
        assertEquals(35, snakeMove.getMoveAttribute(SLMove.MOVE_TOTAL_DESCENDED));
        assertEquals(intermediateStep, snakeMove.getMoveAttribute(SLMove.MOVE_INTERMEDIATE_MOVE));
        assertEquals(SLMoveType.SNAKE_DESCEND, snakeMove.getMoveAttribute(SLMove.MOVE_TYPE));
        assertEquals("Encountered Snake at position:58", snakeMove.getMoveAttribute(SLMove.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getUnluckyMove() {
        Move unluckyMove = SLMovesFactory.getUnluckyMove(98, 4);
        assertEquals(SLMoveType.UNLUCKY_MOVE, unluckyMove.getMoveAttribute(SLMove.MOVE_TYPE));
        assertEquals(98, unluckyMove.getMoveAttribute(SLMove.MOVE_FROM_POSITION));
        assertEquals(98, unluckyMove.getMoveAttribute(SLMove.MOVE_TO_POSITION));
        assertEquals(0, unluckyMove.getMoveAttribute(SLMove.MOVE_TOTAL_TILES));
        assertEquals("Encountered an Unlucky Move at: 98", unluckyMove.getMoveAttribute(SLMove.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getLuckyMove() {
        Move luckyMove = SLMovesFactory.getLuckyMove(98, 100);
        assertEquals(SLMoveType.ADVANCE_LUCKY_MOVE, luckyMove.getMoveAttribute(SLMove.MOVE_TYPE));
        assertEquals(98, luckyMove.getMoveAttribute(SLMove.MOVE_FROM_POSITION));
        assertEquals(100, luckyMove.getMoveAttribute(SLMove.MOVE_TO_POSITION));
        assertEquals(2, luckyMove.getMoveAttribute(SLMove.MOVE_TOTAL_TILES));
        assertEquals("Encountered an Lucky Move at: 98", luckyMove.getMoveAttribute(SLMove.MOVE_EXTRA_DETAILS));
    }
}