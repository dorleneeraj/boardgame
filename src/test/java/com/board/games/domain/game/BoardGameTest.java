package com.board.games.domain.game;

import com.board.games.statistics.GameTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

/**
 * Test class for {@link BoardGame}
 * Test cases for the life cycle events of the game
 */
class BoardGameTest {

    private static BoardGame boardGame;

    @BeforeAll
    public static void setUp() {
        boardGame = Mockito.mock(BoardGame.class);
    }

    @Test
    void startGame() throws Exception{
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.doCallRealMethod().when(boardGame).startGame();
        boardGame.startGame();
        inOrder.verify(boardGame).initializeGame();
        inOrder.verify(boardGame).playGame();
        inOrder.verify(boardGame).endGame();
    }

    @Test
    void playGame() throws Exception{
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.when(boardGame.updateAndGetNextState()).thenAnswer(new Answer<GameState>() {
            int count = 0;

            @Override
            public GameState answer(InvocationOnMock invocation) throws Throwable {
                System.out.println(count + " : is the current count");
                if (count == 0) {
                    count++;
                    return GameState.STARTED;
                }

                while (count < 5) {
                    count++;
                    return GameState.PLAYING;
                }

                return GameState.GAME_COMPLETED;
            }
        });

        Mockito.doCallRealMethod().when(boardGame).playGame();
        Mockito.doCallRealMethod().when(boardGame).setGameState(Mockito.any());
        boardGame.playGame();
        inOrder.verify(boardGame).updateAndGetNextState();
        inOrder.verify(boardGame).setGameState(GameState.STARTED);
        for (int i = 0; i < 5; i++) {
            inOrder.verify(boardGame).processNextTurn();
            inOrder.verify(boardGame).updateMoveStatistics();
            inOrder.verify(boardGame).updateAndGetNextState();
            inOrder.verify(boardGame).setGameState(Mockito.any());
        }
    }


    @Test
    void endGame() {
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.when(boardGame.updateAndGetNextState()).thenReturn(GameState.FINISHED);
        Mockito.doCallRealMethod().when(boardGame).endGame();
        Mockito.doCallRealMethod().when(boardGame).setGameState(Mockito.any());
        boardGame.endGame();
        inOrder.verify(boardGame).generateGameAnalytics();
        inOrder.verify(boardGame).updateAndGetNextState();
        inOrder.verify(boardGame).setGameState(GameState.FINISHED);
        Assertions.assertEquals(GameState.FINISHED, boardGame.currentGameState);
    }

    @Test
    void initializeGame() throws Exception{
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.doCallRealMethod().when(boardGame).initializeGame();
        boardGame.initializeGame();
        inOrder.verify(boardGame).initializeGameStates();
        inOrder.verify(boardGame).validateGameData();
        inOrder.verify(boardGame).updateAndGetNextState();
        inOrder.verify(boardGame).setGameState(Mockito.any());
    }

    @Test
    void playTurn() throws Exception{
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.doCallRealMethod().when(boardGame).processNextTurn();
        boardGame.processNextTurn();
        inOrder.verify(boardGame).selectNextPlayer();
        inOrder.verify(boardGame).takeTurn();
    }

    @Test
    void generateGameAnalytics() throws Exception{
        GameTracker gametracker = Mockito.mock(GameTracker.class);
        List<GameTracker> gameTrackers = Arrays.asList(gametracker);
        Mockito.when(boardGame.getGameTrackers()).thenReturn(gameTrackers);
        Mockito.doCallRealMethod().when(boardGame).generateGameAnalytics();
        boardGame.generateGameAnalytics();
        Mockito.verify(gametracker).trackGameProgress(boardGame);
    }
}