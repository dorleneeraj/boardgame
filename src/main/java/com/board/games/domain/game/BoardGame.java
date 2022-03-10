package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;

/**
 * 
 * 
 */
public abstract class BoardGame implements Game {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////

    protected BoardGenerator generationStrategy;
    protected Board gameBoard;
    protected GameState currentGameState = GameState.NOT_STARTED;
    protected Integer playerCount;
    protected PlayerGenerator playerGenerator;

    public BoardGame(BoardGenerator generationStrategy, Integer playerCount, PlayerGenerator playerGenerator) {
        this.generationStrategy = generationStrategy;
        this.playerCount = playerCount;
        this.playerGenerator = playerGenerator;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Game lifecycle methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGame() {
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

    protected abstract void selectNextPlayer();

    protected abstract GameState updateAndGetNextState();

    public abstract void updateTurnStatistics();

    protected abstract void generateGameAnalytics();

    protected abstract void addNewPlayerToGame();

    protected abstract void movePlayer();

    ///////////////////////////////////////////////////////////////////////////
    // Default implementations
    ///////////////////////////////////////////////////////////////////////////

    protected void initializeGame() {
        initializeGameStates();
        this.gameBoard = generateBoard(this.generationStrategy);
        addPlayersToTheGame();
        setGameState(updateAndGetNextState());
    }

    protected void addPlayersToTheGame() {
        for (int i = 0; i < this.playerCount; i++) {
            addNewPlayerToGame();
        }
    }

    protected void setGameState(GameState state) {
        this.currentGameState = state;
    }

    protected Board generateBoard(BoardGenerator boardGenerator) {
        return boardGenerator.generateBoard();
    }

    protected void playTurn() {
        selectNextPlayer();
        movePlayer();
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

