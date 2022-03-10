package com.board.games;

import com.board.games.domain.game.Game;
import com.board.games.domain.game.GamesFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Driver code. Simulates an execution of the Game
 */
public class GameSimulator {

    private static final Logger LOGGER = LogManager.getLogger(GameSimulator.class);

    public static void main(String[] args) {
        Game game = GamesFactory.getDefaultSLGame();
        game.startGame();
    }

}
