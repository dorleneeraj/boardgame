package com.board.games.statistics;

import com.board.games.domain.game.Game;

/**
 * <p>Generic interface for Tracker object. </p>
 */
public interface GameTracker {

    /**
     * <p>Tracks the progress for the given {@link Game}</p>
     *
     * @param game {@link Game} whose progress needs to be tracked
     */
    void trackGameProgress(Game game);

    /**
     * <p>Generates analytics for multiple runs of the games</p>
     */
    void generateAnalysisAcrossRuns();
}
