package com.board.games.domain.player;

import java.util.Queue;

/**
 * 
 */
public interface PlayerGenerator {
    Queue<? extends Player> getPlayersQueue();
}
