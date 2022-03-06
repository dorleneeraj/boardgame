package com.board.games.factory;

import com.board.games.strategy.AutoBoardGenerationStrategy;
import com.board.games.strategy.BoardGenerationStrategy;
import com.board.games.domain.board.Dimension;

/**
 * Static factory to get {@link BoardGenerationStrategy}
 */
public class BoardGenerationStrategies {
    
    public static BoardGenerationStrategy getAutoGenerationStrategy(Dimension dimension){
        return new AutoBoardGenerationStrategy(dimension);
    }
    
}
