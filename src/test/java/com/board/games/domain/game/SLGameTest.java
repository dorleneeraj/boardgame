package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.SnakeCell;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMovesFactory;
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
 * Test class for {@link SLGame}
 */
class SLGameTest {

    private static SLGame slGame;
    private static Board board;
    private static int playerCount;
    private static Dice dice;

    @BeforeAll
    public static void setUp() {
        board = Mockito.mock(Board.class);
        dice = Mockito.mock(Dice.class);
        playerCount = 4;

        slGame = Mockito.spy(new SLGame(board, playerCount, dice, new LinkedList<>()));
    }

    @Test
    void initializeGame() {

    }

    @Test
    void initializeGameStates() {
        slGame.initializeGameStates();
        Queue<GameState> gameStates = slGame.getGameStates();
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

}