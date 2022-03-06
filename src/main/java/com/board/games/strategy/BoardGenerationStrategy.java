package com.board.games.strategy;

import com.board.games.domain.board.Board;

/**
 * Strategy to generate boards for different Board Games
 */
public interface BoardGenerationStrategy {
   Board generateBoard(); 
}
