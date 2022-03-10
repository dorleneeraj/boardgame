package com.board.games.domain.board;

/**
 * <p>
 * Strategy to generate boards for different Board Games for different games
 * </p>
 */
public interface BoardGenerator {
    Board generateBoard();
}
