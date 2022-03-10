package com.board.games.domain.game;

/**
 * A generic interface for a single/ multi-player board game. It declares all the lifecycle events that a single instance of a game can have
 */
public interface Game {
    void startGame();

    void playGame();

    void endGame();
}
