package com.board.games.domain.game;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.PlayerTurnInfo;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.factory.GameSteps;
import com.board.games.strategy.BoardGenerationStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link BoardGame} implementation for Snake and Ladder game
 */
public class SnakeAndLadder extends BoardGame {

    private Dice dice;
    private PlayerTurnInfo lastPlayerTurnInfo = null;

    protected SnakeAndLadder(BoardGenerationStrategy generationStrategy) {
        super(generationStrategy);
    }

    @Override
    protected Player getNextPlayer() {
        if (null != playersQueue && !playersQueue.isEmpty()) {
            currentPlayer = (Player) playersQueue.peek();
        }
        return currentPlayer;
    }

    @Override
    protected void processPlayerTurn(Player player) {
        this.lastPlayerTurnInfo = player.playGameTurn(this);
    }

    @Override
    protected void updateGameState() {
        if ((Integer) this.lastPlayerTurnInfo.get("DICE_ROLL") != 6) {
            playersQueue.add(playersQueue.poll());
        }
        SLPlayer lastPlayer = (SLPlayer) this.lastPlayerTurnInfo.getPlayer();
        if (lastPlayer.getCurrentPosition() == getGameBoard().getBoardSize()) {
            setGameState(GameState.GAME_COMPLETED, GameSteps.getStepFromState(GameState.GAME_COMPLETED));
        } else {
            setGameState(GameState.STARTED, GameSteps.getStepFromState(GameState.STARTED));
        }
    }

    @Override
    public void updateTurnStatistics() {
        List<Move> lastPlayerMoves = this.lastPlayerTurnInfo.getMovesPerformed();
    }

    @Override
    protected void generateGameAnalytics() {

    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public static class SnakeLadderGameBuilder {

        private BoardGenerationStrategy boardGenerationStrategy;
        private Queue<Player> players = new LinkedList<>();
        private Dice dice;

        public static SnakeLadderGameBuilder getInstance() {
            return new SnakeLadderGameBuilder();
        }

        public SnakeLadderGameBuilder withBoardGenerationStrategy(BoardGenerationStrategy strategy) {
            this.boardGenerationStrategy = strategy;
            return this;
        }

        public SnakeLadderGameBuilder withPlayers(List<Player> players) {
            this.players.addAll(players);
            return this;
        }

        public SnakeLadderGameBuilder withDice(Dice dice) {
            this.dice = dice;
            return this;
        }

        public SnakeAndLadder build() {
            SnakeAndLadder snakeAndLadder = new SnakeAndLadder(boardGenerationStrategy);
            snakeAndLadder.setDice(dice);
            this.players.forEach(player -> {
                ((SLPlayer) player).setDice(this.dice);
                snakeAndLadder.addPlayer(player);
            });
            return snakeAndLadder;
        }


    }
}
