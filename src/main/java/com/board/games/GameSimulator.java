package com.board.games;

import com.board.games.domain.game.Game;
import com.board.games.domain.game.GamesFactory;
import com.board.games.exception.GameException;
import com.board.games.statistics.GameTracker;
import com.board.games.statistics.SLGameStatsTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * Main Driver code. Simulates an execution of the Game.
 * <br/>
 * <b>Note:</b> The number of times the simulation can be configured by
 * providing a command line argument
 * </p>
 */
@JacocoExcludeGenerated
public class GameSimulator {

    private static final Logger LOGGER = LogManager.getLogger(GameSimulator.class);

    public static void main(String[] args) {
        GameTracker tracker = SLGameStatsTracker.getTracker();
        int simulationRuns = getSimulationRuns(args);
        for (int i = 0; i < simulationRuns; i++) {
            try {
                Game game = GamesFactory.getDefaultSLGame();
                game.addGameStatisticTracker(tracker);
                game.startGame();
            } catch (GameException gameException) {
                LOGGER.error("Cannot start the game :: " + gameException.getMessage(), gameException);
            }
        }
        tracker.generateAnalysisAcrossRuns();
    }

    /**
     * <p>Parses the first argument to get the number of simulations that
     * needs to be run</p>
     *
     * @param args CommandLine arguments provided for execution
     * @return number of simulations that needs to be executed
     */
    private static int getSimulationRuns(String[] args) {
        int simulationRuns = 1;
        if (null != args && args.length > 0) {
            try {
                simulationRuns = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                LOGGER.warn("Entry :: " + args[0] + " is not a valid argument" + " for simulation runs. Running " +
                        "simulation once");
            }
        } else {
            LOGGER.info("Number of simulation runs can be configured by " + "providing a command line argument. " +
                    "Currently simulation " + "will run only once");
        }
        return simulationRuns;
    }
}
