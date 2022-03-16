package com.board.games.domain.game;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.board.Board;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SLGame extends BoardGame {

    private static final Logger LOGGER = LogManager.getLogger(SLGame.class);

    private final Dice dice;
    private final Queue<SLPlayer> playersQueue = new LinkedList<>();
    private final Queue<GameState> gameStates = new LinkedList<>();
    private SLPlayer currentPlayer;

    protected SLGame(Board board, int playerCount, Dice dice, Queue<SLPlayer> playersQueue) {
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
    protected void validateGameData() throws GameException {
        if (null == this.gameBoard) {
            throw ExceptionUtil.getInvalidGameConfigurationException("Board " + "cannot be null for a board game");
        }

        if (null == this.playersQueue || this.playersQueue.isEmpty() || this.playerCount == 0) {
            throw ExceptionUtil.getInvalidGameConfigurationException(
                    "Game " + "needs to have at least 1 player to " + "start with");
        }

        if (null == this.dice) {
            throw ExceptionUtil.getInvalidGameConfigurationException(
                    "Snake " + "and Ladder game needs an instance of" + " Dice");
        }
    }

    @Override
    protected void selectNextPlayer() {
        // Selects the next player depending upon the next in the queue
        if (null != playersQueue && !playersQueue.isEmpty()) {
            currentPlayer = playersQueue.peek();
        }
    }

    @Override
    protected void takeTurn() throws GameException {
        Integer diceRoll = currentPlayer.rollDice(this.dice);
        SLMove currentMove = performMove(diceRoll);
        SLBoardCell currentCell = (SLBoardCell) this.gameBoard.getCellByNumber(currentMove.getToPosition());
        if (currentCell.hasASnakeNeighbours()) {
            currentMove.setMissedSnakeLuckily(true);
        }
        currentPlayer.addMove(currentMove);
    }

    /**
     * @param diceRoll
     * @return
     */
    protected SLMove performMove(int diceRoll) throws GameException {
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
    @JacocoExcludeGenerated
    public List<? extends Player> getGamePlayers() {
        return new ArrayList<>(this.playersQueue);
    }

    @Override
    @JacocoExcludeGenerated
    public void updateMoveStatistics() {
        if (GameState.PLAYING.equals(currentGameState)) {
            SLMove currentMove = (SLMove) currentPlayer.getPreviousMove();
            LOGGER.debug("Performed move by user: {} , Move Type: {}, Move " + "from Position {} and Move to " +
                            "Position: {}," + " Roll on " + "the dice {}", currentPlayer.getName(),
                    currentMove.getMoveType(),
                    currentMove.getFromPosition(), currentMove.getToPosition(), currentMove.getDiceRoll());
        }
    }

    @Override
    protected GameState updateAndGetNextState() {
        GameState nextGameState = gameStates.peek();
        if (nextGameState.equals(GameState.PLAYING)) {
            SLMove move = (SLMove) currentPlayer.getPreviousMove();
            if (SLMoveType.ADVANCE_LUCKY_MOVE.equals(move.getMoveType())) {
                LOGGER.debug("Game Completed. Final Lucky move performed by " + "the user: {} ",
                        currentPlayer.getName());
                gameStates.poll();
                return gameStates.peek();
            }
            if (!move.isRolledASix()) {
                playersQueue.add(playersQueue.poll());
            } else {
                LOGGER.debug("Got a 6 on the Dice! Player {} will play " + "another turn! ", currentPlayer.getName());
            }
            return gameStates.peek();
        } else {
            nextGameState = gameStates.poll();
        }
        return nextGameState;
    }

    public SLPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public Queue<GameState> getGameStates() {
        return gameStates;
    }

    /**
     * <p>Simple builder for the {@link SLGame}</p>
     */
    @JacocoExcludeGenerated
    public static class SLGameBuilder {
        private final Queue<SLPlayer> players = new LinkedList<>();
        private Board gameBoard;
        private int playerCount;
        private Dice dice;

        public SLGameBuilder withGameBoard(Board board) {
            this.gameBoard = board;
            return this;
        }

        public SLGameBuilder addPlayers(List<SLPlayer> players) {
            this.players.addAll(players);
            return this;
        }

        public SLGameBuilder withDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public SLGame build() {
            playerCount = this.players.size();
            return new SLGame(this.gameBoard, this.playerCount, this.dice, this.players);
        }
    }
}
