package com.board.games.statistics;

import com.board.games.domain.game.Game;

public interface GameTracker {
    void trackGameProgress(Game game);
}
