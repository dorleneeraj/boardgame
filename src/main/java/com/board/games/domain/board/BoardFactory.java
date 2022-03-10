package com.board.games.domain.board;

/**
 * Static factory to get {@link BoardGenerator}
 */
public class BoardFactory {
    public static BoardGenerator getDefaultSLBoardGenerator() {
        return new DefaultSLBoardGenerator();
    }
}
