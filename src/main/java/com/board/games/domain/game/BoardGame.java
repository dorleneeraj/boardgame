package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
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

    static {
        gameRegistry.put(SnakeAndLadderGame.class.getSimpleName(), " Snake and Ladders!!");
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
            updateTurnStatistics();
            setGameState(updateAndGetNextState());
        }
    }

    @Override
    public void endGame() {
        if (!GameState.GAME_COMPLETED.equals(currentGameState)) {

        }
        this.generateGameAnalytics();
        setGameState(updateAndGetNextState());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Abstract Method Declaration
    ///////////////////////////////////////////////////////////////////////////

    protected abstract void initializeGameStates();

    protected abstract void validateGameState();

    protected abstract void selectNextPlayer();

    protected abstract GameState updateAndGetNextState();

    public abstract void updateTurnStatistics();

    protected abstract void generateGameAnalytics();

    protected abstract void takeTurn();


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

    protected void setGameState(GameState state) {
        this.currentGameState = state;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

}

