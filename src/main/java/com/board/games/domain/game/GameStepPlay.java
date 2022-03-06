package com.board.games.domain.game;

import com.board.games.domain.player.Player;

public class GameStepPlay implements GameStep {
    @Override
    public void processGameStep(BoardGame game) {
        Player currentPlayer = game.getNextPlayer();
        game.processPlayerTurn(currentPlayer);
        game.updateGameState();
        game.updateTurnStatistics();
    }
}
