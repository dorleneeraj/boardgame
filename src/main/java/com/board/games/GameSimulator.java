package com.board.games;

import com.board.games.domain.game.Game;
import com.board.games.domain.game.GamesFactory;
import com.board.games.statistics.GameTracker;
import com.board.games.statistics.SLGameStatsTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Driver code. Simulates an execution of the Game
 */
public class GameSimulator {

    private static final Logger LOGGER = LogManager.getLogger(GameSimulator.class);

    public static void main(String[] args) {
        GameTracker tracker = SLGameStatsTracker.getTracker();
        for (int i = 0; i < 1; i++) {
            Game game = GamesFactory.getDefaultSLGame();
            game.addGameStatisticTracker(tracker);
            game.startGame();
        }
    }
}
