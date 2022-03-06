package com.board.games.domain.game;

import com.board.games.factory.GameSteps;

public class GameNotStarted implements GameStep {
    
    @Override
    public void processGameStep(BoardGame game) {
        game.initializeGame();
        game.setGameState(GameState.STARTED, GameSteps.getStepFromState(GameState.STARTED));
    }
}
