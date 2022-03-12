package com.board.games.domain.player;

import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.token.Token;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Test class for {@link SLPlayer}</p>
 */
class SLPlayerTest {

    private Token token;
    private String name;
    private SLPlayer player;

    @BeforeEach
    public void setUp() {
        String name = "Test Player";
        token = Mockito.mock(Token.class);
        player = new SLPlayer(name, token);
    }

    @Test
    void test_addNormalMove() {
        SLMove move = SLMovesFactory.getAdvanceMove(10, 15);
        player.addMove(move);
        assertEquals(1, player.getTotalTurnsPlayed());
    }

    @Test
    void test_addLadderMove() {
        SLMove intermediateMove = SLMovesFactory.getAdvanceMove(10, 15);
        SLMove ladderStep = SLMovesFactory.getAdvanceMove(15, 42);
        SLMove ladderMove = SLMovesFactory.getLadderAdvanceMove(intermediateMove, ladderStep);

        player.addMove(ladderMove);
        assertEquals(1, player.getTotalTurnsPlayed());
        assertEquals(1, player.getTotalLadderMoves());
        assertEquals(1, player.getTotalLuckyMoves());
        assertEquals(27, player.getTotalTilesClimbed());

        intermediateMove = SLMovesFactory.getAdvanceMove(42, 46);
        ladderStep = SLMovesFactory.getAdvanceMove(46, 70);
        ladderMove = SLMovesFactory.getLadderAdvanceMove(intermediateMove, ladderStep);

        player.addMove(ladderMove);
        assertEquals(2, player.getTotalTurnsPlayed());
        assertEquals(2, player.getTotalLadderMoves());
        assertEquals(2, player.getTotalLuckyMoves());
        assertEquals(51, player.getTotalTilesClimbed());
    }

    @Test
    void test_addSnakeMove() {
        SLMove intermediateMove = SLMovesFactory.getAdvanceMove(10, 15);
        SLMove snakeStep = SLMovesFactory.getAdvanceMove(15, 6);
        SLMove snakeMove = SLMovesFactory.getSnakeDescendMove(intermediateMove, snakeStep);

        player.addMove(snakeMove);
        assertEquals(1, player.getTotalTurnsPlayed());
        assertEquals(1, player.getTotalSnakeMoves());
        assertEquals(1, player.getTotalUnluckyMoves());
        assertEquals(9, player.getTotalTilesDescended());

        intermediateMove = SLMovesFactory.getAdvanceMove(9, 12);
        snakeStep = SLMovesFactory.getAdvanceMove(12, 5);
        snakeMove = SLMovesFactory.getSnakeDescendMove(intermediateMove, snakeStep);

        player.addMove(snakeMove);
        assertEquals(2, player.getTotalTurnsPlayed());
        assertEquals(2, player.getTotalSnakeMoves());
        assertEquals(2, player.getTotalUnluckyMoves());
        assertEquals(16, player.getTotalTilesDescended());

    }

    @Test
    void test_addLuckyAdvanceMove() {
        SLMove luckyMove = SLMovesFactory.getLuckyMove(95, 100);
        player.setTotalTurnsPlayed(25);
        player.setTotalLuckyMoves(4);
        player.addMove(luckyMove);

        assertEquals(26, player.getTotalTurnsPlayed());
        assertEquals(5, player.getTotalLuckyMoves());
    }

    @Test
    void test_addLuckyEscapeMove() {
        SLMove luckyMove = SLMovesFactory.getAdvanceMove(10, 12);
        player.setTotalTurnsPlayed(25);
        player.setTotalLuckyMoves(4);
        luckyMove.setMissedSnakeLuckily(true);
        player.addMove(luckyMove);

        assertEquals(26, player.getTotalTurnsPlayed());
        assertEquals(5, player.getTotalLuckyMoves());
    }

    @Test
    void test_addUnluckyMove() {
        SLMove unluckyMove = SLMovesFactory.getUnluckyMove(95, 6);
        player.setTotalTurnsPlayed(25);
        player.setTotalUnluckyMoves(4);
        player.addMove(unluckyMove);

        assertEquals(26, player.getTotalTurnsPlayed());
        assertEquals(5, player.getTotalUnluckyMoves());
    }

    @Test
    void test_addConsecutiveMoves() {
        SLMove advance1 = SLMovesFactory.getAdvanceMove(10, 16);
        player.addMove(advance1);
        assertEquals(SLPlayer.ConsecutiveTurns.STARTED, player.getConsecutiveTurnState());

        SLMove advance2 = SLMovesFactory.getAdvanceMove(16, 22);
        player.addMove(advance2);

        SLMove advance3 = SLMovesFactory.getAdvanceMove(22, 28);
        player.addMove(advance3);
        assertEquals(18, player.getCurrentConsecutiveStreak());

        SLMove advance4 = SLMovesFactory.getAdvanceMove(28, 32);
        player.addMove(advance4);

        assertEquals(4, player.getTotalTurnsPlayed());
        assertEquals(3, player.getTotalDiceRollsWithSix());
        assertEquals(SLPlayer.ConsecutiveTurns.COMPLETED, player.getConsecutiveTurnState());
        assertEquals(22, player.getMaxConsecutiveStreak());
    }

    @Test
    void test_addMultipleContinuousStreaks() {
        test_addConsecutiveMoves();
        addConsecutiveMovesWithLessTurns();
        addConsecutiveMovesWithMoreTurns();
    }

    private void addConsecutiveMovesWithLessTurns() {
        SLMove advance5 = SLMovesFactory.getAdvanceMove(32, 38);
        player.addMove(advance5);
        assertEquals(SLPlayer.ConsecutiveTurns.STARTED, player.getConsecutiveTurnState());

        SLMove advance6 = SLMovesFactory.getAdvanceMove(38, 44);
        player.addMove(advance6);
        assertEquals(12, player.getCurrentConsecutiveStreak());

        SLMove advance7 = SLMovesFactory.getAdvanceMove(44, 47);
        player.addMove(advance7);

        assertEquals(7, player.getTotalTurnsPlayed());
        assertEquals(5, player.getTotalDiceRollsWithSix());
        assertEquals(SLPlayer.ConsecutiveTurns.COMPLETED, player.getConsecutiveTurnState());
        assertEquals(22, player.getMaxConsecutiveStreak());
    }

    private void addConsecutiveMovesWithMoreTurns() {
        SLMove advance8 = SLMovesFactory.getAdvanceMove(47, 53);
        player.addMove(advance8);
        assertEquals(SLPlayer.ConsecutiveTurns.STARTED, player.getConsecutiveTurnState());

        SLMove advance9 = SLMovesFactory.getAdvanceMove(53, 59);
        player.addMove(advance9);

        SLMove advance10 = SLMovesFactory.getAdvanceMove(59, 65);
        player.addMove(advance10);
        assertEquals(18, player.getCurrentConsecutiveStreak());

        SLMove advance11 = SLMovesFactory.getAdvanceMove(65, 71);
        player.addMove(advance11);

        SLMove advance12 = SLMovesFactory.getAdvanceMove(71, 74);
        player.addMove(advance12);

        assertEquals(12, player.getTotalTurnsPlayed());
        assertEquals(9, player.getTotalDiceRollsWithSix());
        assertEquals(SLPlayer.ConsecutiveTurns.COMPLETED, player.getConsecutiveTurnState());
        assertEquals(27, player.getMaxConsecutiveStreak());
    }

    @Test
    void test_addConsecutiveTurnsWithSameTurnsDifferentValue() {

        SLMove advance1 = SLMovesFactory.getAdvanceMove(10, 16);
        player.addMove(advance1);
        assertEquals(SLPlayer.ConsecutiveTurns.STARTED, player.getConsecutiveTurnState());

        SLMove advance2 = SLMovesFactory.getAdvanceMove(16, 22);
        player.addMove(advance2);

        SLMove advance3 = SLMovesFactory.getAdvanceMove(22, 26);
        player.addMove(advance3);

        assertEquals(3, player.getTotalTurnsPlayed());
        assertEquals(2, player.getTotalDiceRollsWithSix());
        assertEquals(SLPlayer.ConsecutiveTurns.COMPLETED, player.getConsecutiveTurnState());
        assertEquals(16, player.getMaxConsecutiveStreak());

        SLMove advance4 = SLMovesFactory.getAdvanceMove(26, 32);
        player.addMove(advance4);
        assertEquals(SLPlayer.ConsecutiveTurns.STARTED, player.getConsecutiveTurnState());

        SLMove advance5 = SLMovesFactory.getAdvanceMove(32, 38);
        player.addMove(advance5);

        SLMove advance6 = SLMovesFactory.getAdvanceMove(38, 40);
        player.addMove(advance6);

        assertEquals(6, player.getTotalTurnsPlayed());
        assertEquals(4, player.getTotalDiceRollsWithSix());
        assertEquals(SLPlayer.ConsecutiveTurns.COMPLETED, player.getConsecutiveTurnState());
        assertEquals(16, player.getMaxConsecutiveStreak());
    }
}