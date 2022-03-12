package com.board.games.statistics;

import com.board.games.domain.game.SnakeAndLadderGame;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.domain.token.TokenColour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 */
class SLGameStatsTrackerTest {

    private static SnakeAndLadderGame game;
    private static List<SLPlayer> players;
    private static SLGameStatsTracker.GameStatisticsData gameStatistics;

    @BeforeAll
    public static void setUp() {
        game = mock(SnakeAndLadderGame.class);
        gameStatistics = new SLGameStatsTracker.GameStatisticsData(game);
        players = new ArrayList<>();
        addPlayers(players);
        Mockito.doReturn(players).when(game).getGamePlayers();
        when(game.getCurrentPlayer()).thenReturn((SLPlayer) players.get(3));
    }


    private static void addPlayers(List<SLPlayer> players) {

        players.add(buildPlayerWithData("player1", new Token(1, TokenColour.GREEN, 65), 14, 2,
                20, 15, 3, 1, 14, 10,
                7, 8, 3, 14, 3));

        players.add(buildPlayerWithData("player2", new Token(2, TokenColour.BLUE, 73), 21, 1,
                18, 8, 4, 3, 7, 11,
                11, 7, 9, 7, 2));

        players.add(buildPlayerWithData("player3", new Token(3, TokenColour.YELLOW, 89), 18, 1, 22,
                23, 18, 2, 16, 9, 4, 11, 12, 22, 4));

        players.add(buildPlayerWithData("player4", new Token(4, TokenColour.RED, 100), 11, 3, 31, 16, 5,
                1, 15, 8, 1, 14, 0, 27, 1));
    }

    @Test
    void performOperation() {
        gameStatistics.analyzeData();

        assertEquals(64, gameStatistics.totalTurnsInTheGame);

        assertEquals(7, gameStatistics.totalLadderAdvanceMoves);
        assertEquals(91, gameStatistics.totalTilesClimbed);
        assertEquals(23, gameStatistics.longestLadderClimb);
        assertEquals(3, gameStatistics.minimumLadderClimb);
        assertEquals(13, gameStatistics.averageLadderClimb);

        assertEquals(7, gameStatistics.totalSnakeDescendMoves);
        assertEquals(52, gameStatistics.totalTilesDescended);
        assertEquals(11, gameStatistics.steepestSnakeDescend);
        assertEquals(1, gameStatistics.minimumSnakeDescend);
        assertEquals(7, gameStatistics.averageSnakeDescend);

        assertEquals(27, gameStatistics.longestTurn);
        assertEquals(10, gameStatistics.totalRollsWithSix);
        assertEquals(24, gameStatistics.totalUnluckyRolls);
        assertEquals(40, gameStatistics.totalLuckyRolls);

        assertEquals(7, gameStatistics.minLuckyRolls);
        assertEquals(14, gameStatistics.maxLuckyRolls);
        assertEquals(10, gameStatistics.averageLuckyRolls);

        assertEquals(0, gameStatistics.minUnluckyRolls);
        assertEquals(12, gameStatistics.maxUnluckyRolls);
        assertEquals(6, gameStatistics.averageUnluckyRolls);
    }


    private static SLPlayer buildPlayerWithData(String name, Token token, int totalTurnsPlayed, int totalLadderMoves, int totalTilesClimbed, int longestClimb, int lowestClimb,
                                                int totalSnakeMoves, int totalTilesDescended, int steepestDescend, int minDescend,
                                                int totalLuckyMoves, int totalUnluckyMoves, int maxStreak, int totalRollsWithSix) {
        SLPlayer player = new SLPlayer(name, token);
        player.setTotalTurnsPlayed(totalTurnsPlayed);
        player.setTotalLadderMoves(totalLadderMoves);
        player.setTotalTilesClimbed(totalTilesClimbed);
        player.setLongestLadderClimb(longestClimb);
        player.setLowestLadderClimb(lowestClimb);
        player.setTotalSnakeMoves(totalSnakeMoves);
        player.setTotalTilesDescended(totalTilesDescended);
        player.setSteepestSnakeDescend(steepestDescend);
        player.setMinimumSnakeDescend(minDescend);
        player.setTotalLuckyMoves(totalLuckyMoves);
        player.setTotalUnluckyMoves(totalUnluckyMoves);
        player.setMaxConsecutiveStreak(maxStreak);
        player.setTotalDiceRollsWithSix(totalRollsWithSix);
        return player;
    }
}