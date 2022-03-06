package com.board.games.domain.game;

public class GameStepFinished implements GameStep{

    @Override
    public void processGameStep(BoardGame game) {
        throw new RuntimeException("Game has been finished. No processing can be further performed");
    }
}
