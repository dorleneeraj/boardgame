package com.board.games.factory;

import com.board.games.domain.game.*;

/**
 * Static factory to get {@link GameStep}
 */
public class GameSteps {

    private static GameStep gameNotStarted = new GameNotStarted();
    private static GameStep gamePlayTurn = new GameStepPlay();
    private static GameStep gameEndGame = new GameStepEnd();
    private static GameStep gameFinished = new GameStepFinished();

    public static GameStep getStepFromState(GameState state) {
        switch (state) {
            case NOT_STARTED:
                return gameNotStarted;
            case STARTED:
                return gamePlayTurn;
            case GAME_COMPLETED:
                return gameEndGame;
            case FINISHED:
                return gameFinished;
        }
        throw new RuntimeException("Invlaid state received");
    }
}
