package com.board.games.domain.game;

import com.board.games.exception.GameException;
import com.board.games.statistics.GameTracker;

/**
 * A generic interface for a single/ multi-player board game. It declares all
 * the lifecycle events that a single instance of a game can have
 */
public interface Game {

    /**
     * <p>Game needs an external element to track the game state and progress
     * . Attaches such external {@link GameTracker} objects<p/>
     *
     * @param gameTracker
     */
    void addGameStatisticTracker(GameTracker gameTracker);

    /**
     * <p>Starts the board game</p>
     *
     * @throws GameException
     */
    void startGame() throws GameException;

    /**
     * <p>Indicates an algorithm on how a game needs to be played</p>
     *
     * @throws GameException
     */
    void playGame() throws GameException;

    /**
     * <p>Ends the game</p>
     */
    void endGame();

}
