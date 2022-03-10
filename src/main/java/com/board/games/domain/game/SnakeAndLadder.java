package com.board.games.domain.game;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.MoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.MovesFactory;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SnakeAndLadder extends BoardGame {

    private final Dice dice;
    private final Queue<SLPlayer> playersQueue = new LinkedList<>();
    private SLPlayer currentPlayer;
    private final Queue<GameState> gameStates = new LinkedList<>();

    protected SnakeAndLadder(BoardGenerator generationStrategy, int playerCount, Dice dice, PlayerGenerator playerGenerator) {
        super(generationStrategy, playerCount, playerGenerator);
        this.dice = dice;
    }

    @Override
    protected void initializeGameStates() {
        gameStates.add(GameState.INITIALIZED);
        gameStates.add(GameState.STARTED);
        gameStates.add(GameState.PLAYING);
        gameStates.add(GameState.GAME_COMPLETED);
        gameStates.add(GameState.FINISHED);
    }

    @Override
    protected void selectNextPlayer() {
        if (null != playersQueue && !playersQueue.isEmpty()) {
            currentPlayer = playersQueue.peek();
        }
    }

    @Override
    protected void movePlayer() {
        Integer diceRoll = currentPlayer.rollDice(this.dice);
        currentPlayer.addMove(performMove(diceRoll));
    }

    private Move performMove(int diceRoll) {
        Token currentPlayerToken = currentPlayer.getToken();
        Integer currentPosition = currentPlayer.getCurrentPosition();
        Cell fromCell = this.gameBoard.getCellByNumber(currentPlayer.getCurrentPosition());
        Cell toCell = this.gameBoard.getCellByNumber(currentPosition + diceRoll);
        SLMove currentMove = null;
        if (null != fromCell) {
            fromCell.removeToken(currentPlayerToken);
        }
        if (null != toCell) {
            currentMove = (SLMove) toCell.acceptToken(currentPlayerToken);
        } else {
            currentMove = MovesFactory.getUnluckyMove(currentPlayerToken.getPosition());
        }
        currentMove.setDiceRoll(diceRoll);
        return currentMove;
    }

    @Override
    protected GameState updateAndGetNextState() {
        GameState nextGameState = gameStates.peek();
        if (nextGameState.equals(GameState.PLAYING)) {
            SLMove move = (SLMove) currentPlayer.getLastMove();
            if (MoveType.ADVANCE_LUCKY_MOVE.equals(move.getMoveType())) {
                return GameState.GAME_COMPLETED;
            }
            if (move.getDiceRoll() != 6) {
                playersQueue.add(playersQueue.poll());
            }
            return GameState.PLAYING;
        } else {
            nextGameState = gameStates.poll();
        }
        return nextGameState;
    }

    @Override
    public void updateTurnStatistics() {
        SLMove move = (SLMove) currentPlayer.getLastMove();
    }

    @Override
    protected void generateGameAnalytics() {

    }

    @Override
    protected void addNewPlayerToGame() {
        playersQueue.add((SLPlayer) this.playerGenerator.getNextPlayer());
    }

    public Dice getDice() {
        return dice;
    }

    public Queue<SLPlayer> getPlayersQueue() {
        return playersQueue;
    }

    public SLPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(SLPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Queue<GameState> getGameStates() {
        return gameStates;
    }

    /**
     *
     */
    public static class SnakeAndLadderBuilder {

        BoardGenerator generationStrategy;
        int playerCount;
        Dice dice;
        PlayerGenerator playerGenerator;

        public SnakeAndLadderBuilder(int playerCount) {
            this.playerCount = playerCount;
        }

        public SnakeAndLadderBuilder withBoardGenerator(BoardGenerator boardGenerator) {
            this.generationStrategy = boardGenerator;
            return this;
        }

        public SnakeAndLadderBuilder withPlayerGenerator(PlayerGenerator playerGenerator) {
            this.playerGenerator = playerGenerator;
            return this;
        }

        public SnakeAndLadderBuilder withDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public SnakeAndLadder build() {
            validateGame();
            return new SnakeAndLadder(generationStrategy, playerCount, dice, playerGenerator);
        }

        private void validateGame() {
            if (null == this.dice) {
                throw new RuntimeException("Snake and Ladder game cannot start without a dice");
            }
            if (null == this.generationStrategy) {
                throw new RuntimeException("Game needs to have a Board Generation Strategy");
            }
            if (playerCount <= 0 || playerCount > 4) {
                throw new RuntimeException("Game needs at least 1 player to start the game. There can be no more that 4 players ");
            }
        }
    }
}
