package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.Dimension;
import com.board.games.domain.board.SLBoardFactory;
import com.board.games.domain.board.SLTuple;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.player.SLPlayersFactory;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

import java.util.List;

import static com.board.games.domain.game.SLGame.SLGameBuilder;

/**
 * <p>Simple static factory to get the required games</p>
 */
public class GamesFactory {

    /**
     * <p>Builds the instance of the {@link SLGame} with default parameters</p>
     *
     * @return {@link SLGame} that can be played/ simulated
     * @throws GameException
     */
    public static SLGame getDefaultSLGame() throws GameException {
        return getSLGame(SLBoardFactory.getDefaultBoard(), 4);
    }

    private static SLGame getSLGame(Board board, int playerCount) throws GameException {
        List<SLPlayer> players = SLPlayersFactory.getSLPlayers(playerCount);
        if (null == players || players.isEmpty()) {
            throw ExceptionUtil.getGamePlayerConfigurationException(
                    "Snake " + "and Ladder games at least need 1 " + "player to start the " + "game.");
        }
        return new SLGameBuilder().withGameBoard(board).addPlayers(players).withDice(new Dice()).build();
    }

    /**
     * <p>Builds the instance of the {@link SLGame} from the given
     * configuration</p>
     *
     * @param ladderTuples   Ladder cells that needs to be included
     * @param snakeTuples    Snake cells that needs to be included
     * @param boardDimension Dimension of the Game board
     * @param playerCount    Number of players playing the game
     * @return {@link SLGame} that can be played/ simulated
     * @throws GameException
     */
    public static SLGame getSLGameWithConfiguration(List<SLTuple> ladderTuples, List<SLTuple> snakeTuples,
                                                    Dimension boardDimension, int playerCount)
            throws GameException {
        Board board = SLBoardFactory.getConfigurableBoard(ladderTuples, snakeTuples, boardDimension);
        return getSLGame(board, playerCount);
    }
}
