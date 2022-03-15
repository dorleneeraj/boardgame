package com.board.games.statistics;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.game.Game;
import com.board.games.domain.game.SLGame;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A class that maintains the state of the game in progress
 * </p>
 */
@JacocoExcludeGenerated
public class SLGameStatsTracker implements GameTracker {

    private static final Logger LOGGER = LogManager.getLogger(SLGameStatsTracker.class);
    private List<GameStatisticsData> gamesData = new ArrayList<>();

    private volatile static SLGameStatsTracker gameStatsTracker = null;

    private SLGameStatsTracker() {

    }

    public static SLGameStatsTracker getTracker() {
        if (null == gameStatsTracker) {
            synchronized (SLGameStatsTracker.class) {
                if (null == gameStatsTracker) {
                    gameStatsTracker = new SLGameStatsTracker();
                }
            }
        }
        return gameStatsTracker;
    }

    @Override
    public void trackGameProgress(Game game) {
        GameStatisticsData data = buildStats(game);
        printReport(data);
        gamesData.add(data);
    }

    public GameStatisticsData buildStats(Game game) {
        GameStatisticsData data = new GameStatisticsData((SLGame) game);
        data.analyzeData();
        return data;
    }

    private void printReport(GameStatisticsData data) {
        LOGGER.info("########################################");
        LOGGER.info("Game Completed :: Printing the Game Statistics...");
        LOGGER.info("Game Won by :: " + data.winner.getName());
        LOGGER.info("Total turns played in Game (all players): " + data.totalTurnsInTheGame);

        LOGGER.info("Total turns involving a Ladder: " + data.totalLadderAdvanceMoves);
        LOGGER.info("Total tiles climbed by ladder in the game: " + data.totalTilesClimbed);
        LOGGER.info("Longest climb in a single game: " + data.longestLadderClimb);
        LOGGER.info("Minimum climb in a single game: " + data.minimumLadderClimb);
        LOGGER.info("Average climb in a single game (Total Tiles Climbed / Total Ladder Advance Moves): " + data.averageLadderClimb);

        LOGGER.info("Total turns involving a Snake: " + data.totalSnakeDescendMoves);
        LOGGER.info("Total tiles descended by snake in the game: " + data.totalTilesDescended);
        LOGGER.info("Steepest descend in a single game: " + data.steepestSnakeDescend);
        LOGGER.info("Minimum descend in a single game: " + data.minimumSnakeDescend);
        LOGGER.info("Average descend in a single game (Total Tiles Descended / Total Snake Descend Moves): " + data.averageSnakeDescend);

        LOGGER.info("Total lucky rolls in the game: " + data.totalLuckyRolls);
        LOGGER.info("Total unlucky rolls in the game: " + data.totalUnluckyRolls);
        LOGGER.info("Tiles covered in longest turn of the game: " + data.longestTurn);
        LOGGER.info("Total Rolls with 6 in the game: " + data.totalRollsWithSix);

        if (data.winner != null) {
            LOGGER.info("Printing Winner Statistics:");
            LOGGER.info("Total turns played by winner: " + data.winner.getTotalTurnsPlayed());
            LOGGER.info("Number of times 6 rolled by winner on dice: " + data.winner.getTotalDiceRollsWithSix());
            LOGGER.info("Maximum ");
            LOGGER.info("Total lucky turns by winner: " + data.winner.getTotalLuckyMoves());
            LOGGER.info("Total Ladder moves by winner: " + data.winner.getTotalLadderMoves());
            LOGGER.info("Total unlucky moves by winner:" + data.winner.getTotalUnluckyMoves());
            LOGGER.info("Total snake descend moves by winner:" + data.winner.getTotalSnakeMoves());
        }

        LOGGER.info("########################################");
    }


    /**
     *
     */
    protected static class GameStatisticsData {
        protected int totalTurnsInTheGame;

        protected int totalLadderAdvanceMoves;
        protected int totalTilesClimbed;
        protected int longestLadderClimb;
        protected int minimumLadderClimb;
        protected double averageLadderClimb;

        protected int totalSnakeDescendMoves;
        protected int totalTilesDescended;
        protected int steepestSnakeDescend;
        protected int minimumSnakeDescend;
        protected double averageSnakeDescend;

        protected int longestTurn;
        protected int totalRollsWithSix;

        protected int totalUnluckyRolls;
        protected int minUnluckyRolls;
        protected int maxUnluckyRolls;
        double averageUnluckyRolls;

        protected int totalLuckyRolls;
        protected int minLuckyRolls;
        protected int maxLuckyRolls;
        double averageLuckyRolls;

        protected SLPlayer winner;

        private final SLGame game;

        public GameStatisticsData(SLGame game) {
            this.game = game;
        }

        protected void analyzeData() {
            SLGame slGame = (SLGame) game;
            this.winner = slGame.getCurrentPlayer();
            List<? extends Player> players = ((SLGame) game).getGamePlayers();

            totalTurnsInTheGame = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalTurnsPlayed()).sum();

            totalLadderAdvanceMoves = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalLadderMoves()).sum();
            totalTilesClimbed = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalTilesClimbed()).sum();
            longestLadderClimb = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getLongestLadderClimb()).max().orElse(0);
            minimumLadderClimb = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getLowestLadderClimb()).min().orElse(0);
            if (totalLadderAdvanceMoves > 0) {
                averageLadderClimb = Math.ceil(totalTilesClimbed / totalLadderAdvanceMoves);
            }

            totalSnakeDescendMoves = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalSnakeMoves()).sum();
            totalTilesDescended = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalTilesDescended()).sum();
            steepestSnakeDescend = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getSteepestSnakeDescend()).max().orElse(0);
            minimumSnakeDescend = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getMinimumSnakeDescend()).min().orElse(0);
            if (totalSnakeDescendMoves > 0) {
                averageSnakeDescend = Math.ceil(totalTilesDescended / totalSnakeDescendMoves);
            }

            longestTurn = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getMaxConsecutiveStreak()).max().orElse(0);
            totalUnluckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalUnluckyMoves()).sum();
            totalLuckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalLuckyMoves()).sum();
            totalRollsWithSix = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalDiceRollsWithSix()).sum();

            minLuckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalLuckyMoves()).min().orElse(0);
            maxLuckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalLuckyMoves()).max().orElse(0);
            averageLuckyRolls = Math.ceil(totalLuckyRolls / players.size());

            minUnluckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalUnluckyMoves()).min().orElse(0);
            maxUnluckyRolls = slGame.getGamePlayers().stream().mapToInt(player -> ((SLPlayer) player).getTotalUnluckyMoves()).max().orElse(0);
            averageUnluckyRolls = Math.ceil(totalUnluckyRolls / players.size());
        }
    }
}