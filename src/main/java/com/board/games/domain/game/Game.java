package com.board.games.domain.game;

import com.board.games.domain.board.Board;

/**
 * A generic interface for a single/ multi-player board game. It declares all the lifecycle events that a single instance of a game can have
 */
public interface Game {
    public void startGame();
    public void endGame();
}
