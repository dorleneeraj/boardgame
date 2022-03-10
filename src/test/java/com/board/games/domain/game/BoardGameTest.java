package com.board.games.domain.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
    void startGame() {
        InOrder inOrder = Mockito.inOrder(boardGame);
        Mockito.doCallRealMethod().when(boardGame).startGame();
        boardGame.startGame();
        inOrder.verify(boardGame).initializeGame();
        inOrder.verify(boardGame).playGame();
        inOrder.verify(boardGame).endGame();
    }

    @Test
    void playGame() {
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
            inOrder.verify(boardGame).playTurn();
            inOrder.verify(boardGame).updateTurnStatistics();
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
}