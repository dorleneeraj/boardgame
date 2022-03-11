package com.board.games.domain.game;

import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.board.DefaultSLBoardGenerator;
import com.board.games.domain.player.PlayerGenerator;
import com.board.games.domain.player.SLDefaultPlayerGenerator;

import static com.board.games.domain.game.SnakeAndLadderGame.*;

/**
 * <p>Simple static factory to get the required games</p>
 */
public class GamesFactory {

    public static SnakeAndLadderGame getDefaultSLGame() {
        return getSLGame(DefaultSLBoardGenerator.getInstance(), SLDefaultPlayerGenerator.getInstance(2), 2);
    }

    public static SnakeAndLadderGame getSLGame(BoardGenerator boardGenerator, PlayerGenerator playerGenerator, int playerCount) {
        return new SLGameBuilder().buildBoardWith(boardGenerator).addPlayersUsing(playerGenerator).withDice(new Dice()).build();
    }
}
