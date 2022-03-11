package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}