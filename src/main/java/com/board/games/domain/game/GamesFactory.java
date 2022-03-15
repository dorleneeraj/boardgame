package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.Dimension;
import com.board.games.domain.board.SLBoardFactory;
import com.board.games.domain.board.SLTuple;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.player.SLPlayersFactory;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

import java.util.List;
import java.util.stream.Collectors;

import static com.board.games.domain.game.SLGame.*;

/**
 * <p>Simple static factory to get the required games</p>
 */
public class GamesFactory {

    public static SLGame getDefaultSLGame() throws GameException {
        return getSLGame(SLBoardFactory.getDefaultBoard(), 4);
    }

    private static SLGame getSLGame(Board board, int playerCount) throws GameException {
        List<? extends Player> players = SLPlayersFactory.getSLPlayers(playerCount);
        if (null == players || players.isEmpty()) {
            throw ExceptionUtil.getGamePlayerConfigurationException("Snake and Ladder games at least need 1 player to start the game.");
        }
        List<SLPlayer> slPlayers = players.stream().filter(player -> player instanceof SLPlayer).map(player -> (SLPlayer) player).collect(Collectors.toList());
        return new SLGameBuilder().withGameBoard(board).addPlayers(slPlayers).withDice(new Dice()).build();
    }

    public static SLGame getSLGameWithConfiguration(List<SLTuple> ladderTuples, List<SLTuple> snakeTuples, Dimension boardDimension,
                                                    int playerCount) throws GameException {
        Board board = SLBoardFactory.getConfigurableBoard(ladderTuples, snakeTuples, boardDimension);
        return getSLGame(board, playerCount);
    }
}
