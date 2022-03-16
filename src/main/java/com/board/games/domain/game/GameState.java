package com.board.games.domain.game;

/**
 * <p>Indicates the different logical states in which a particular game can be in</p>
 */
public enum GameState {
    NOT_STARTED, INITIALIZED, STARTED, PLAYING, GAME_COMPLETED, FINISHED
}