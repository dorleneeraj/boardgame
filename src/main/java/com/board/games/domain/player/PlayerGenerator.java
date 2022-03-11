package com.board.games.domain.player;

import java.util.List;

/**
 *
 */
public interface PlayerGenerator {
    List<? extends Player> getPlayersQueue();
}
