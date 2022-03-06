package com.board.games.factory;

import com.board.games.domain.game.Dice;
import com.board.games.domain.player.Player;
import com.board.games.strategy.BoardGenerationStrategy;
import com.board.games.domain.game.BoardGame;
import com.board.games.domain.game.Game;
import com.board.games.domain.game.SnakeAndLadder.SnakeLadderGameBuilder;

import java.util.List;

/**
 * Factory to build different {@link BoardGame}
 */
public class GameFactory {

    public static Game getSnakeAndLaddersGame(List<Player> players, BoardGenerationStrategy boardGenerationStrategy, Dice dice) {
        if (null == players || players.isEmpty()) {
            throw new RuntimeException("At least 2 players are needed to play Snake and Ladder");
        }

        if (players.size() > 4) {
            throw new RuntimeException("More than 4 players are not allowed to play Snake and Ladder");
        }

        return SnakeLadderGameBuilder.getInstance().withBoardGenerationStrategy(boardGenerationStrategy).withPlayers(players).withDice(dice).build();
    }
}
