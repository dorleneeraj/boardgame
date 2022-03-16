package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.SLBoardFactory;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.player.SLPlayersFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SLGamePlayerTurnsTest {

    private static SLGame game;
    private static Board board;
    private static Dice dice;
    private static List<Integer> mockedRolls = Arrays.asList(1, 2, 3, 6, 6, 6, 2, 1, 4, 6, 3, 6, 4);
    private static List<SLPlayer> players;
    private static Queue<SLPlayer> playerQueue = new LinkedList<>();

    @BeforeAll
    public static void setUp() throws Exception {
        board = SLBoardFactory.getDefaultBoard();
        dice = Mockito.mock(Dice.class);
        players = SLPlayersFactory.getSLPlayers(4);
        playerQueue.addAll(players);
        game = new SLGame.SLGameBuilder().withGameBoard(board).withDice(dice).addPlayers(players).build();
        Mockito.when(dice.rollDice()).thenAnswer(new Answer<Integer>() {

            int count = 0;

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return mockedRolls.get(count++);
            }
        });
    }

    @Test
    public void test_gameAppropriatePlayerTurnSwitch() throws Exception {
        game.initializeGame();
        game.updateAndGetNextState();
        for (int i = 0; i < mockedRolls.size(); i++) {
            game.processNextTurn();
            game.updateAndGetNextState();
            Assertions.assertEquals(playerQueue.peek(), game.getCurrentPlayer());
            if (mockedRolls.get(i) != 6) {
                playerQueue.add(playerQueue.poll());
            }
        }
    }
}
