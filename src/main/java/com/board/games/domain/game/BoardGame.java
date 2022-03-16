package com.board.games.domain.game;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.board.Board;
import com.board.games.domain.player.Player;
import com.board.games.exception.GameException;
import com.board.games.statistics.GameTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * An abstract implementation of a generic Board Game. It declares various
 * Template methods that govern the overall
 * flow of the board game.
 * </p>
 */
public abstract class BoardGame implements Game {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////

    private static final Logger LOGGER = LogManager.getLogger(BoardGame.class);
    protected static Map<String, String> gameRegistry = new HashMap<>();

    static {
        gameRegistry.put(SLGame.class.getSimpleName(), " Snake and Ladders!!");
    }

    protected Board gameBoard;
    protected GameState currentGameState = GameState.NOT_STARTED;
    protected Integer playerCount;
    protected List<GameTracker> gameTrackerTrackers = new ArrayList<>();

    public BoardGame(Board gameBoard, Integer playerCount) {
        this.playerCount = playerCount;
        this.gameBoard = gameBoard;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Game lifecycle methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGame() throws GameException {
        LOGGER.info("******** Starting a Game of {} ********", gameRegistry.get(this.getClass().getSimpleName()));
        initializeGame();
        playGame();
        endGame();
    }

    @Override
    public void playGame() throws GameException {
        setGameState(updateAndGetNextState());
        while (!GameState.GAME_COMPLETED.equals(currentGameState)) {
            processNextTurn();
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

    /**
     * <p>Initializes the states required by a game to progress in its
     * lifecycle</p>
     */
    protected abstract void initializeGameStates();

    /**
     * <p>Validates all the parameters of the game before starting</p>
     *
     * @throws GameException
     */
    protected abstract void validateGameData() throws GameException;

    /**
     * <p>Selects the next player in line depending upon the data-structure
     * used by a game to store its players</p>
     */
    protected abstract void selectNextPlayer();

    /**
     * <p>Depending upon the current state of the game, determines what the
     * next state of the game would be</p>
     *
     * @return
     */
    protected abstract GameState updateAndGetNextState();

    /**
     * <p>A hook to update the statistics after every move if required</p>
     */
    public abstract void updateMoveStatistics();

    /**
     * <p>A hook for each player to take a turn in the game</p>
     *
     * @throws GameException
     */
    protected abstract void takeTurn() throws GameException;

    public abstract List<? extends Player> getGamePlayers();

    ///////////////////////////////////////////////////////////////////////////
    // Default implementations
    ///////////////////////////////////////////////////////////////////////////

    /**
     * <p>Template method to initializes the game. Performs any
     * pre-processing if required before starting the game</p>
     *
     * @throws GameException
     */
    protected void initializeGame() throws GameException {
        initializeGameStates();
        validateGameData();
        setGameState(updateAndGetNextState());
    }

    /**
     * <p>Template method to process a distinct turn of the player in the
     * game</p>
     *
     * @throws GameException
     */
    protected void processNextTurn() throws GameException {
        selectNextPlayer();
        takeTurn();
    }

    /**
     * <p>Generates the game analytics. Processes every {@link GameTracker}
     * object associated with the game</p>
     */
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

