package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SnakeAndLadderGame extends BoardGame {

    private final Dice dice;
    private final Queue<SLPlayer> playersQueue = new LinkedList<>();
    private SLPlayer currentPlayer;
    private final Queue<GameState> gameStates = new LinkedList<>();

    protected SnakeAndLadderGame(Board board, int playerCount, Dice dice, Queue<SLPlayer> playersQueue) {
        super(board, playerCount);
        this.dice = dice;
        this.playersQueue.addAll(playersQueue);
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
    protected void validateGameState() {
        if (null == this.gameBoard) {
            throw new RuntimeException("Board cannot be null for a board game");
        }

        if (null == this.playersQueue || this.playersQueue.isEmpty() || this.playerCount == 0) {
            throw new RuntimeException("Game needs to have at least 1 player to start with");
        }

        if (null == this.dice) {
            throw new RuntimeException("Snake and Ladder game needs an instance of Dice");
        }
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
            currentMove = SLMovesFactory.getUnluckyMove(currentPlayerToken.getPosition());
        }
        currentMove.setDiceRoll(diceRoll);
        return currentMove;
    }

    @Override
    protected GameState updateAndGetNextState() {
        GameState nextGameState = gameStates.peek();
        if (nextGameState.equals(GameState.PLAYING)) {
            SLMove move = (SLMove) currentPlayer.getLastMove();
            if (SLMoveType.ADVANCE_LUCKY_MOVE.equals(move.getMoveType())) {
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
    public static class SLGameBuilder {
        private BoardGenerator boardGenerator;
        private Board gameBoard;
        private Queue<SLPlayer> players = new LinkedList<>();
        private int playerCount;
        private PlayerGenerator playerGenerator;
        private Dice dice;

        public SLGameBuilder buildBoardWith(BoardGenerator boardGenerator) {

            this.boardGenerator = boardGenerator;
            return this;
        }

        public SLGameBuilder addPlayersUsing(PlayerGenerator playerGenerator) {
            this.playerGenerator = playerGenerator;
            return this;
        }

        public SLGameBuilder withDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public SnakeAndLadderGame build() {
            generateBoard();
            generatePlayers();
            playerCount = this.players.size();
            return new SnakeAndLadderGame(this.gameBoard, this.playerCount, this.dice, this.players);
        }

        private void generateBoard() {
            if (null != boardGenerator) {
                this.gameBoard = boardGenerator.generateBoard();
            }
        }

        private void generatePlayers() {
            if (null != playerGenerator) {
                playerGenerator.getPlayersQueue().stream().forEach(player -> {
                    if (!(player instanceof SLPlayer)) {
                        throw new RuntimeException("Invalid player received for Snake and Ladder game. It has to be an instance of SLPlayer");
                    }
                    this.players.add((SLPlayer) player);
                });
            }
        }
    }
}
