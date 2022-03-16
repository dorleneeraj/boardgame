package com.board.games.statistics;

import com.board.games.domain.game.SLGame;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.board.games.statistics.SLGameStatsTracker.GameStatisticsData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
class SLGameStatsTrackerTest {

    private static SLGame game;
    private static List<SLPlayer> players;
    private static GameStatisticsData gameStatisticsData;

    @BeforeAll
    public static void setUp() {
        game = mock(SLGame.class);
        gameStatisticsData = new SLGameStatsTracker.GameStatisticsData(game);
        players = new ArrayList<>();
        addPlayers(players);
        Mockito.doReturn(players).when(game).getGamePlayers();
        when(game.getCurrentPlayer()).thenReturn(players.get(3));
    }


    private static void addPlayers(List<SLPlayer> players) {

        players.add(
                buildPlayerWithData("player1", new Token(1, Token.TokenColour.GREEN, 65), 14, 2, 20, 15, 3, 1, 14, 10,
                        7, 8, 3, 14, 3));

        players.add(
                buildPlayerWithData("player2", new Token(2, Token.TokenColour.BLUE, 73), 21, 1, 18, 8, 4, 3, 7, 11, 11,
                        7, 9, 7, 2));

        players.add(
                buildPlayerWithData("player3", new Token(3, Token.TokenColour.YELLOW, 89), 18, 1, 22, 23, 18, 2, 16, 9,
                        4, 11, 12, 22, 4));

        players.add(
                buildPlayerWithData("player4", new Token(4, Token.TokenColour.RED, 100), 11, 3, 31, 16, 5, 1, 15, 8, 1,
                        14, 0, 27, 1));
    }

    private static SLPlayer buildPlayerWithData(String name, Token token, int totalTurnsPlayed, int totalLadderMoves,
                                                int totalTilesClimbed, int longestClimb, int lowestClimb,
                                                int totalSnakeMoves, int totalTilesDescended, int steepestDescend,
                                                int minDescend, int totalLuckyMoves, int totalUnluckyMoves,
                                                int maxStreak, int totalRollsWithSix) {
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

    @Test
    void performOperation() {
        gameStatisticsData.analyzeData();

        assertEquals(64, gameStatisticsData.totalTurnsInTheGame);
        assertEquals(7, gameStatisticsData.totalLadderAdvanceMoves);
        assertEquals(91, gameStatisticsData.totalTilesClimbed);
        assertEquals(23, gameStatisticsData.longestLadderClimb);
        assertEquals(3, gameStatisticsData.minimumLadderClimb);
        assertEquals(13, gameStatisticsData.averageLadderClimb);
        assertEquals(7, gameStatisticsData.totalSnakeDescendMoves);
        assertEquals(52, gameStatisticsData.totalTilesDescended);
        assertEquals(11, gameStatisticsData.steepestSnakeDescend);
        assertEquals(1, gameStatisticsData.minimumSnakeDescend);
        assertEquals(7, gameStatisticsData.averageSnakeDescend);
        assertEquals(27, gameStatisticsData.longestTurn);
        assertEquals(10, gameStatisticsData.totalRollsWithSix);
        assertEquals(24, gameStatisticsData.totalUnluckyRolls);
        assertEquals(40, gameStatisticsData.totalLuckyRolls);
        assertEquals(7, gameStatisticsData.minLuckyRolls);
        assertEquals(14, gameStatisticsData.maxLuckyRolls);
        assertEquals(10, gameStatisticsData.averageLuckyRolls);
        assertEquals(0, gameStatisticsData.minUnluckyRolls);
        assertEquals(12, gameStatisticsData.maxUnluckyRolls);
        assertEquals(6, gameStatisticsData.averageUnluckyRolls);
    }
}