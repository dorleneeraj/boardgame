package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.BoardGenerator;
import com.board.games.domain.player.PlayerGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for {@link SnakeAndLadder}
 */
class SnakeAndLadderTest {

    private static SnakeAndLadder snakeAndLadder;
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

        snakeAndLadder = Mockito.spy(new SnakeAndLadder(boardGenerator, playerCount, dice, playerGenerator));
    }

    @Test
    void initializeGame() {

    }

    @Test
    void addPlayersToTheGame() {
        snakeAndLadder.addPlayersToTheGame();
        Mockito.verify(snakeAndLadder, Mockito.times(playerCount)).addNewPlayerToGame();
    }

    @Test
    void generateBoard() {
        snakeAndLadder.generateBoard(snakeAndLadder.generationStrategy);
        Mockito.verify(snakeAndLadder.generationStrategy).generateBoard();
    }

    @Test
    void initializeGameStates() {
        snakeAndLadder.initializeGameStates();
        Queue<GameState> gameStates = snakeAndLadder.getGameStates();
        assertNotNull(gameStates);
        assertEquals(6, gameStates.size());
        assertEquals(GameState.NOT_STARTED, gameStates.poll());
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