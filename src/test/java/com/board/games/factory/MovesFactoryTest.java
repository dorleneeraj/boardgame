package com.board.games.factory;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.MoveType;
import com.board.games.domain.move.MovesFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link MovesFactory}
 */
class MovesFactoryTest {

    @Test
    void getAdvanceMove() {
        Move move = MovesFactory.getAdvanceMove(10, 15);
        assertEquals(5, move.getMoveAttribute(Move.MOVE_TOTAL_TILES));
        assertEquals(10, move.getMoveAttribute(Move.MOVE_FROM_POSITION));
        assertEquals(15, move.getMoveAttribute(Move.MOVE_TO_POSITION));
        assertEquals(MoveType.NORMAL_ADVANCE.toString(), move.getMoveAttribute(Move.MOVE_TYPE).toString());
        assertEquals("Normal Token Advance", move.getMoveAttribute(Move.MOVE_EXTRA_DETAILS).toString());
        assertNull(move.getMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE));
        assertEquals(0, move.getMoveAttribute(Move.MOVE_TOTAL_CLIMBED));
        assertEquals(0, move.getMoveAttribute(Move.MOVE_TOTAL_DESCENDED));
        assertEquals(0, move.getMoveAttribute(Move.MOVE_INTERMEDIATE_POSITION));
    }

    @Test
    void getLadderAdvanceMove() {
        Move intermediateStep = MovesFactory.getAdvanceMove(10, 15);
        Move ladderStep = MovesFactory.getAdvanceMove(15, 32);
        Move ladderMove = MovesFactory.getLadderAdvanceMove(intermediateStep, ladderStep);
        assertEquals(10, ladderMove.getMoveAttribute(Move.MOVE_FROM_POSITION));
        assertEquals(32, ladderMove.getMoveAttribute(Move.MOVE_TO_POSITION));
        assertEquals(15, ladderMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_POSITION));
        assertEquals(22, ladderMove.getMoveAttribute(Move.MOVE_TOTAL_TILES));
        assertEquals(17, ladderMove.getMoveAttribute(Move.MOVE_TOTAL_CLIMBED));
        assertEquals(intermediateStep, ladderMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE));
        assertEquals(MoveType.LADDER_ADVANCE, ladderMove.getMoveAttribute(Move.MOVE_TYPE));
        assertEquals("Encountered Ladder at position:15", ladderMove.getMoveAttribute(Move.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getSnakeDescendMove() {
        Move intermediateStep = MovesFactory.getAdvanceMove(55, 58);
        Move snakeStep = MovesFactory.getAdvanceMove(58, 23);
        Move snakeMove = MovesFactory.getSnakeDescendMove(intermediateStep, snakeStep);
        assertEquals(55, snakeMove.getMoveAttribute(Move.MOVE_FROM_POSITION));
        assertEquals(23, snakeMove.getMoveAttribute(Move.MOVE_TO_POSITION));
        assertEquals(58, snakeMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_POSITION));
        assertEquals(38, snakeMove.getMoveAttribute(Move.MOVE_TOTAL_TILES));
        assertEquals(35, snakeMove.getMoveAttribute(Move.MOVE_TOTAL_DESCENDED));
        assertEquals(intermediateStep, snakeMove.getMoveAttribute(Move.MOVE_INTERMEDIATE_MOVE));
        assertEquals(MoveType.SNAKE_DESCEND, snakeMove.getMoveAttribute(Move.MOVE_TYPE));
        assertEquals("Encountered Snake at position:58", snakeMove.getMoveAttribute(Move.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getUnluckyMove() {
        Move unluckyMove = MovesFactory.getUnluckyMove(98);
        assertEquals(MoveType.UNLUCKY_MOVE, unluckyMove.getMoveAttribute(Move.MOVE_TYPE));
        assertEquals(98, unluckyMove.getMoveAttribute(Move.MOVE_FROM_POSITION));
        assertEquals(98, unluckyMove.getMoveAttribute(Move.MOVE_TO_POSITION));
        assertEquals(0, unluckyMove.getMoveAttribute(Move.MOVE_TOTAL_TILES));
        assertEquals("Encountered an Unlucky Move at: 98", unluckyMove.getMoveAttribute(Move.MOVE_EXTRA_DETAILS));
    }

    @Test
    void getLuckyMove() {
        Move luckyMove = MovesFactory.getLuckyMove(98, 100);
        assertEquals(MoveType.ADVANCE_LUCKY_MOVE, luckyMove.getMoveAttribute(Move.MOVE_TYPE));
        assertEquals(98, luckyMove.getMoveAttribute(Move.MOVE_FROM_POSITION));
        assertEquals(100, luckyMove.getMoveAttribute(Move.MOVE_TO_POSITION));
        assertEquals(2, luckyMove.getMoveAttribute(Move.MOVE_TOTAL_TILES));
        assertEquals("Encountered an Lucky Move at: 98", luckyMove.getMoveAttribute(Move.MOVE_EXTRA_DETAILS));
    }
}