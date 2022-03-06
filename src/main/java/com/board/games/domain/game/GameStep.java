package com.board.games.domain.game;

/**
 * Implements the State Pattern. The Context class {@link BoardGame} will process the step in the game. Each implementation
 * of this interface will have logic to handle the step.
 */
public interface GameStep {
    public void processGameStep(BoardGame game);
}
