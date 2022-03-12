package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.Dimension;
import com.board.games.domain.board.SLBoardFactory;
import com.board.games.domain.board.SLTuple;
import com.board.games.domain.player.PlayerGenerator;
import com.board.games.domain.player.SLDefaultPlayerGenerator;

import java.util.List;

import static com.board.games.domain.game.SnakeAndLadderGame.*;

/**
 * <p>Simple static factory to get the required games</p>
 */
public class GamesFactory {

    public static SnakeAndLadderGame getDefaultSLGame() {
        return getSLGame(SLBoardFactory.getDefaultBoard(), SLDefaultPlayerGenerator.getInstance(4), 4);
    }

    private static SnakeAndLadderGame getSLGame(Board board, PlayerGenerator playerGenerator, int playerCount) {
        return new SLGameBuilder().buildBoardWith(board).addPlayersUsing(playerGenerator).withDice(new Dice()).build();
    }

    public static SnakeAndLadderGame getSLGameWithConfiguration(List<SLTuple> ladderTuples, List<SLTuple> snakeTuples, Dimension boardDimension) {
        Board board = SLBoardFactory.getConfigurableBoard(ladderTuples, snakeTuples, boardDimension);
        return getSLGame(board, SLDefaultPlayerGenerator.getInstance(4), 4);
    }
}
