package com.board.games.domain.game;

import com.board.games.factory.GameSteps;

public class GameStepEnd implements GameStep {
    @Override
    public void processGameStep(BoardGame game) {
        game.endGame();
        game.setGameState(GameState.FINISHED, GameSteps.getStepFromState(GameState.FINISHED));
    }
}
