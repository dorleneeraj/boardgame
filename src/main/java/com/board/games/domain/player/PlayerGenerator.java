package com.board.games.domain.player;

import com.board.games.domain.player.Player;

import java.util.List;

public interface PlayerGenerator {
    Player getNextPlayer();
}
