package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.player.Player;
import com.board.games.strategy.BoardGenerationStrategy;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A board game will have a lifecycle of its own. To start a game, a board needs to be generated with the required cells and its subtypes.
 * Players need to assigned and manage the flow of the game till it ends. This class has a template method to successfully execute a game.
 */
public abstract class BoardGame implements Game {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////

    protected BoardGenerationStrategy generationStrategy;
    protected Queue playersQueue = new LinkedList();
    protected Board gameBoard;
    protected GameStep currentGameStep = new GameNotStarted();
    protected GameState currentGameState = GameState.NOT_STARTED;
    protected Player currentPlayer;

    public BoardGame(BoardGenerationStrategy generationStrategy) {
        this.generationStrategy = generationStrategy;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Game lifecycle methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void startGame() {
        while (!GameState.FINISHED.equals(currentGameState)) {
            currentGameStep.processGameStep(this);
        }
    }

    @Override
    public void endGame() {
        this.generateGameAnalytics();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Abstract Method Declaration
    ///////////////////////////////////////////////////////////////////////////

    protected abstract Player getNextPlayer();

    protected abstract void processPlayerTurn(Player player);

    protected abstract void updateGameState();

    public abstract void updateTurnStatistics();
    
    protected abstract void generateGameAnalytics();

    ///////////////////////////////////////////////////////////////////////////
    // Default implementations
    ///////////////////////////////////////////////////////////////////////////

    protected void initializeGame() {
        this.gameBoard = generateBoard(this.generationStrategy);
    }

    protected void setGameState(GameState state, GameStep step) {
        this.currentGameState = state;
        this.currentGameStep = step;
    }

    protected Board generateBoard(BoardGenerationStrategy boardGenerationStrategy) {
        return boardGenerationStrategy.generateBoard();
    }

    protected void addPlayer(Player player) {
        this.playersQueue.add(player);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    public GameStep getCurrentGameStep() {
        return currentGameStep;
    }

    public void setCurrentGameStep(GameStep currentGameStep) {
        this.currentGameStep = currentGameStep;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
}

