package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.player.Player;
import com.board.games.strategy.BoardGenerationStrategy;

/**
 * A board game will have a lifecycle of its own. To start a game, a board needs to be generated with the required cells and its subtypes.
 * Players need to assigned and manage the flow of the game till it ends. This class has a template method to successfully execute a game.
 */
public abstract class BoardGame implements Game {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////

    protected BoardGenerationStrategy generationStrategy;
    protected Board gameBoard;
    protected GameState currentGameState = GameState.NOT_STARTED;
    private Integer playerCount;

    public BoardGame(BoardGenerationStrategy generationStrategy, Integer playerCount) {
        this.generationStrategy = generationStrategy;
        this.playerCount = playerCount;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Game lifecycle methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGame() {
        initializeGame();
        while (!GameState.FINISHED.equals(currentGameState)) {
            processPlayerTurn();
        }
        endGame();
    }

    @Override
    public void endGame() {
        if (!GameState.GAME_COMPLETED.equals(currentGameState)) {

        }
        this.generateGameAnalytics();
        setGameState(GameState.FINISHED);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Abstract Method Declaration
    ///////////////////////////////////////////////////////////////////////////

    protected abstract void validateGame();

    protected abstract Player getNextPlayer();

    protected abstract void updateGameState();

    public abstract void updateTurnStatistics();

    protected abstract void generateGameAnalytics();

    protected abstract void addNewPlayerToGame();

    protected abstract void movePlayer();

    ///////////////////////////////////////////////////////////////////////////
    // Default implementations
    ///////////////////////////////////////////////////////////////////////////

    protected void initializeGame() {
        validateGame();
        this.gameBoard = generateBoard(this.generationStrategy);
        for (int i = 0; i < this.playerCount; i++) {
            addNewPlayerToGame();
        }
        setGameState(GameState.STARTED);
    }

    protected void setGameState(GameState state) {
        this.currentGameState = state;
    }

    protected Board generateBoard(BoardGenerationStrategy boardGenerationStrategy) {
        return boardGenerationStrategy.generateBoard();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Template Method
    ///////////////////////////////////////////////////////////////////////////

    protected void processPlayerTurn() {
        getNextPlayer();
        movePlayer();
        updateTurnStatistics();
        updateGameState();
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

