package com.board.games.statistics;

import com.board.games.domain.game.Game;

public interface GameTracker {
    void trackeGameProgress(Game game);
}
