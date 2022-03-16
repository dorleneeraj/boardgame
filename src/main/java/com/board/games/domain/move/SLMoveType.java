package com.board.games.domain.move;

import com.board.games.domain.game.SLGame;

/**
 * <p>Indicates the type of moves that can be played in the {@link SLGame}</p>
 */
public enum SLMoveType {
    NORMAL_ADVANCE, LADDER_ADVANCE, SNAKE_DESCEND, UNLUCKY_MOVE, ADVANCE_LUCKY_MOVE, QUIT_GAME
}
