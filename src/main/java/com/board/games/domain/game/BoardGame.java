package com.board.games.domain.game;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.board.Board;
import com.board.games.domain.player.Player;
import com.board.games.statistics.GameTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class BoardGame implements Game {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////

    private static final Logger LOGGER = LogManager.getLogger(BoardGame.class);
    protected static Map<String, String> gameRegistry = new HashMap<>();

    protected Board gameBoard;
    protected GameState currentGameState = GameState.NOT_STARTED;
    protected Integer playerCount;
    protected List<GameTracker> gameTrackerTrackers = new ArrayList<>();

    static {
        gameRegistry.put(SLGame.class.getSimpleName(), " Snake and Ladders!!");
    }

    public BoardGame(Board gameBoard, Integer playerCount) {
        this.playerCount = playerCount;
        this.gameBoard = gameBoard;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Game lifecycle methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGame() {
        LOGGER.info("******** Starting a Game of {} ********", gameRegistry.get(this.getClass().getSimpleName()));
        initializeGame();
        playGame();
        endGame();
    }

    @Override
    public void playGame() {
        setGameState(updateAndGetNextState());
        while (!GameState.GAME_COMPLETED.equals(currentGameState)) {
            playTurn();
            updateMoveStatistics();
            setGameState(updateAndGetNextState());
        }
    }

    @Override
    public void endGame() {
        if (!GameState.GAME_COMPLETED.equals(currentGameState)) {

        }
        generateGameAnalytics();
        setGameState(updateAndGetNextState());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Abstract Method Declaration
    ///////////////////////////////////////////////////////////////////////////

    protected abstract void initializeGameStates();

    protected abstract void validateGameState();

    protected abstract void selectNextPlayer();

    protected abstract GameState updateAndGetNextState();

    public abstract void updateMoveStatistics();

    protected abstract void takeTurn();

    public abstract List<? extends Player> getGamePlayers();

    ///////////////////////////////////////////////////////////////////////////
    // Default implementations
    ///////////////////////////////////////////////////////////////////////////

    protected void initializeGame() {
        initializeGameStates();
        validateGameState();
        setGameState(updateAndGetNextState());
    }

    protected void playTurn() {
        selectNextPlayer();
        takeTurn();
    }

    protected void generateGameAnalytics() {
        for (GameTracker gameTracker : getGameTrackers()) {
            gameTracker.trackGameProgress(this);
        }
    }

    @JacocoExcludeGenerated
    protected List<GameTracker> getGameTrackers() {
        return this.gameTrackerTrackers;
    }

    protected void setGameState(GameState state) {
        this.currentGameState = state;
    }

    @Override
    @JacocoExcludeGenerated
    public void addGameStatisticTracker(GameTracker gameTracker) {
        this.gameTrackerTrackers.add(gameTracker);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    @JacocoExcludeGenerated
    public Board getGameBoard() {
        return gameBoard;
    }

    @JacocoExcludeGenerated
    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

}

