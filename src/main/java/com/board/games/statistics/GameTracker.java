package com.board.games.statistics;

import com.board.games.domain.game.Game;

/**
 * <p>Generic interface for Tracker object. </p>
 */
public interface GameTracker {
    void trackGameProgress(Game game);
}
