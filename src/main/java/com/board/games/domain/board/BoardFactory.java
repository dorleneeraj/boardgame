package com.board.games.domain.board;

import com.board.games.domain.board.sl.DefaultSLBoardGenerator;

/**
 * Static factory to get {@link BoardGenerator}
 */
public class BoardFactory {
    public static BoardGenerator getDefaultSLBoardGenerator() {
        return new DefaultSLBoardGenerator();
    }
}
