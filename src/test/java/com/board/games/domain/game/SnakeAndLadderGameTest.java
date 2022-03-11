package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.SnakeCell;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.player.PlayerGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link SnakeAndLadderGame}
 */
class SnakeAndLadderGameTest {

    private static SnakeAndLadderGame snakeAndLadderGame;
    private static Board board;
    private static int playerCount;
    private static BoardGenerator boardGenerator;
    private static Dice dice;
    private static PlayerGenerator playerGenerator;

    @BeforeAll
    public static void setUp() {
        board = Mockito.mock(Board.class);
        boardGenerator = Mockito.mock(BoardGenerator.class);
        dice = Mockito.mock(Dice.class);
        playerGenerator = Mockito.mock(PlayerGenerator.class);
        playerCount = 4;

        snakeAndLadderGame = Mockito.spy(new SnakeAndLadderGame(board, playerCount, dice, new LinkedList<>()));
    }

    @Test
    void initializeGame() {

    }

    @Test
    void initializeGameStates() {
        snakeAndLadderGame.initializeGameStates();
        Queue<GameState> gameStates = snakeAndLadderGame.getGameStates();
        assertNotNull(gameStates);
        assertEquals(5, gameStates.size());
        assertEquals(GameState.INITIALIZED, gameStates.poll());
        assertEquals(GameState.STARTED, gameStates.poll());
        assertEquals(GameState.PLAYING, gameStates.poll());
        assertEquals(GameState.GAME_COMPLETED, gameStates.poll());
        assertEquals(GameState.FINISHED, gameStates.poll());
    }

    @Test
    void selectNextPlayer() {


    }

    @Test
    void movePlayer() {
    }

    @Test
    void addNewPlayerToGame() {
    }

    @Test
    void checkAndUpdateSnakeMiss() {
        SLMove move = SLMovesFactory.getAdvanceMove(10, 16);
        List<SLBoardCell> snakeCell = Arrays.asList(new SnakeCell(new SLBoardCell(6), new SLBoardCell(2)));
        snakeAndLadderGame.checkAndUpdateSnakeMiss(move, snakeCell);
        assertTrue(move.isMissedSnakeLuckily());
    }
}