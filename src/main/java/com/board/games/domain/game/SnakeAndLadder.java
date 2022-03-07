package com.board.games.domain.game;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.player.Move;
import com.board.games.domain.player.MoveType;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.strategy.BoardGenerationStrategy;

import java.util.LinkedList;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SnakeAndLadder extends BoardGame {

    private Dice dice;
    private Queue<SLPlayer> playersQueue = new LinkedList<>();
    private SLPlayer currentPlayer;

    protected SnakeAndLadder(BoardGenerationStrategy generationStrategy, int playerCount) {
        super(generationStrategy, playerCount);
    }

    @Override
    protected void validateGame() {

    }

    @Override
    protected Player getNextPlayer() {
        if (null != playersQueue && !playersQueue.isEmpty()) {
            currentPlayer = playersQueue.peek();
        }
        return currentPlayer;
    }

    @Override
    protected void movePlayer() {
        Integer diceRoll = currentPlayer.rollDice(this.dice);
        Integer currentPosition = currentPlayer.getCurrentPostion();
        Cell currentCell = this.gameBoard.getCellByNumber(currentPlayer.getCurrentPostion());
        Cell nextCell = this.gameBoard.getCellByNumber(currentPosition + diceRoll);
        currentPlayer.addMove(performMove(currentCell, nextCell));
    }

    private Move performMove(Cell fromCell, Cell toCell) {
        return null;
    }

    @Override
    protected void updateGameState() {
        Move move = currentPlayer.getLastMove();
        if (MoveType.ADVANCE_LUCKY_MOVE.equals(move.getMoveAttribute(Move.MOVE_TYPE))) {
            setGameState(GameState.GAME_COMPLETED);
        } else {
            setGameState(GameState.STARTED);
        }
    }

    @Override
    public void updateTurnStatistics() {

    }

    @Override
    protected void generateGameAnalytics() {

    }

    @Override
    protected void addNewPlayerToGame() {

    }


}
