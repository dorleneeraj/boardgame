package com.board.games.exception;

/**
 * <p>Custom exception thrown by the game</p>
 */
public class GameException extends Exception {
    protected GameException(String message) {
        super(message);
    }
}
