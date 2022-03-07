package com.board.games.factory;

import com.board.games.strategy.DefaultBoardGenerationStrategy;
import com.board.games.strategy.BoardGenerationStrategy;
import com.board.games.domain.board.Dimension;

/**
 * Static factory to get {@link BoardGenerationStrategy}
 */
public class BoardFactory {
    
    public static BoardGenerationStrategy getDefaultBoard(){
        return new DefaultBoardGenerationStrategy();
    }
    
}
