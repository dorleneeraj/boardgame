package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.SnakeCell;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SnakeAndLadderGame extends BoardGame {

    private static final Logger LOGGER = LogManager.getLogger(SnakeAndLadderGame.class);

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
    protected void takeTurn() {
        Integer diceRoll = currentPlayer.rollDice(this.dice);
        SLMove currentMove = performMove(diceRoll);
        Cell currentCell = this.gameBoard.getCellByNumber(currentMove.getToPosition());
        if (hasASnakeCell(currentCell.getNeighbours())) {
            currentMove.setMissedSnakeLuckily(true);
        }
        currentPlayer.addMove(currentMove);
    }

    /**
     * @param diceRoll
     * @return
     */
    protected SLMove performMove(int diceRoll) {
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
            currentMove = SLMovesFactory.getUnluckyMove(currentPlayerToken.getPosition(), diceRoll);
        }

        return currentMove;
    }

    @Override
    public List<? extends Player> getGamePlayers() {
        return new ArrayList<>(this.playersQueue);
    }

    @Override
    public void updateMoveStatistics() {
        SLMove currentMove = (SLMove) currentPlayer.getPreviousMove();
        LOGGER.debug("Performed move by user: {} , Move Type: {}, Move from Position {} and Move to Position: {}," +
                " Roll on the dice {}", currentPlayer.getName(), currentMove.getMoveType(), currentMove.getFromPosition(), currentMove.getToPosition(), currentMove.getDiceRoll());
    }

    /**
     * @param neighbourCells
     */
    protected boolean hasASnakeCell(List<? extends Cell> neighbourCells) {
        return neighbourCells.stream().anyMatch(neighbour -> neighbour instanceof SnakeCell);
    }

    @Override
    protected GameState updateAndGetNextState() {
        GameState nextGameState = gameStates.peek();
        if (nextGameState.equals(GameState.PLAYING)) {
            SLMove move = (SLMove) currentPlayer.getPreviousMove();
            if (SLMoveType.ADVANCE_LUCKY_MOVE.equals(move.getMoveType())) {
                LOGGER.debug("Game Completed. Final Lucky move performed by the user: {} ", currentPlayer.getName());
                return GameState.GAME_COMPLETED;
            }
            if (!move.isRolledASix()) {
                playersQueue.add(playersQueue.poll());
            } else {
                LOGGER.debug("Got a 6 on the Dice! Player {} will play another turn! ", currentPlayer.getName());
            }
            return GameState.PLAYING;
        } else {
            nextGameState = gameStates.poll();
        }
        return nextGameState;
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
