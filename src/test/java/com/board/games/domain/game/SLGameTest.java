package com.board.games.domain.game;

import com.board.games.domain.board.Board;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.token.Token;
import com.board.games.exception.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.Queue;

import static com.board.games.domain.token.Token.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link SLGame}
 */
class SLGameTest {

    private SLGame slGame;
    private Board board;
    private int playerCount;
    private Dice dice;
    private Queue<SLPlayer> players;

    @BeforeEach
    public void setUp() {
        board = Mockito.mock(Board.class);
        dice = Mockito.mock(Dice.class);
        playerCount = 4;
        players = new LinkedList<>();
    }

    @Test
    void test_initializeGameStates() {
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
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
    void test_selectValidNextPlayer() {
        players.add(new SLPlayer("Player 1", Mockito.mock(Token.class)));
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.selectNextPlayer();
        assertEquals("Player 1", slGame.getCurrentPlayer().getName());
    }

    @Test
    void test_selectNextPlayerEmptyQueue() {
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.selectNextPlayer();
        assertNull(slGame.getCurrentPlayer());
    }

    @Test
    void test_performMove() throws Exception {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        Token token = new Token(1, TokenColour.BLUE, 35);
        Cell fromCell = new SLBoardCell(35);
        Cell toCell = Mockito.spy(new SLBoardCell(39));

        Mockito.when(player.getToken()).thenReturn(token);
        Mockito.when(player.getCurrentPosition()).thenReturn(35);
        Mockito.when(board.getCellByNumber(35)).thenReturn(fromCell);
        Mockito.when(board.getCellByNumber(39)).thenReturn(toCell);

        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.selectNextPlayer();
        Move move = slGame.performMove(4);

        assertNotNull(move);
        assertEquals(SLMoveType.NORMAL_ADVANCE, move.getMoveAttribute(SLMove.MOVE_TYPE));
        Mockito.verify(toCell).acceptToken(token);
    }

    @Test
    void test_performStartMove() throws Exception {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        Token token = new Token(1, TokenColour.BLUE, 0);
        Cell fromCell = null;
        Cell toCell = Mockito.spy(new SLBoardCell(3));

        Mockito.when(player.getToken()).thenReturn(token);
        Mockito.when(player.getCurrentPosition()).thenReturn(0);
        Mockito.when(board.getCellByNumber(0)).thenReturn(fromCell);
        Mockito.when(board.getCellByNumber(3)).thenReturn(toCell);

        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.selectNextPlayer();
        Move move = slGame.performMove(3);

        assertNotNull(move);
        assertEquals(SLMoveType.NORMAL_ADVANCE, move.getMoveAttribute(SLMove.MOVE_TYPE));
        Mockito.verify(toCell).acceptToken(token);
    }

    @Test
    void test_performUnluckyMove() throws Exception {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        Token token = new Token(1, TokenColour.BLUE, 0);
        Cell fromCell = new SLBoardCell(98);
        Cell toCell = null;

        Mockito.when(player.getToken()).thenReturn(token);
        Mockito.when(player.getCurrentPosition()).thenReturn(0);
        Mockito.when(board.getCellByNumber(98)).thenReturn(fromCell);
        Mockito.when(board.getCellByNumber(101)).thenReturn(toCell);

        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.selectNextPlayer();
        Move move = slGame.performMove(3);

        assertNotNull(move);
        assertEquals(SLMoveType.UNLUCKY_MOVE, move.getMoveAttribute(SLMove.MOVE_TYPE));
    }

    @Test
    void test_takeNormalTurn() throws Exception {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        SLMove move = Mockito.mock(SLMove.class);
        SLBoardCell cell = Mockito.mock(SLBoardCell.class);

        Mockito.when(move.getToPosition()).thenReturn(9);
        Mockito.when(board.getCellByNumber(9)).thenReturn(cell);
        Mockito.when(cell.hasASnakeNeighbours()).thenReturn(false);
        Mockito.when(player.rollDice(dice)).thenReturn(4);

        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        Mockito.doReturn(move).when(slGame).performMove(4);

        slGame.selectNextPlayer();
        slGame.takeTurn();

        Mockito.verify(player, Mockito.times(1)).addMove(move);
    }

    @Test
    void test_missedSnakeLuckily() throws Exception {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        SLMove move = Mockito.mock(SLMove.class);
        SLBoardCell cell = Mockito.mock(SLBoardCell.class);

        Mockito.when(move.getToPosition()).thenReturn(9);
        Mockito.when(board.getCellByNumber(9)).thenReturn(cell);
        Mockito.when(cell.hasASnakeNeighbours()).thenReturn(true);
        Mockito.when(player.rollDice(dice)).thenReturn(4);

        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        Mockito.doReturn(move).when(slGame).performMove(4);

        slGame.selectNextPlayer();
        slGame.takeTurn();

        Mockito.verify(move).setMissedSnakeLuckily(true);
        Mockito.verify(player, Mockito.times(1)).addMove(move);
    }

    @Test
    public void test_validateGameState() throws Exception {
        players.add(new SLPlayer("player 1", new Token(1, TokenColour.BLUE, 0)));
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.validateGameData();
    }

    @Test
    public void test_validateGameState_nullBoard() throws Exception {
        slGame = Mockito.spy(new SLGame(null, playerCount, dice, players));

        Throwable throwable = assertThrows(GameException.class, () -> {
            slGame.validateGameData();
        });

        assertTrue(throwable.getMessage().contains("Board cannot be null for a board game"));
    }

    @Test
    public void test_validateGameState_emptyPlayerQueue() throws Exception {
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, new LinkedList<>()));

        Throwable throwable = assertThrows(GameException.class, () -> {
            slGame.validateGameData();
        });

        assertTrue(throwable.getMessage().contains("Game needs to have at least 1 player to start with"));
    }

    @Test
    public void test_validateGameState_nullDice() {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, null, players));

        Throwable throwable = assertThrows(GameException.class, () -> {
            slGame.validateGameData();
        });

        assertTrue(throwable.getMessage().contains("Snake and Ladder game needs an instance of Dice"));
    }

    @Test
    public void test_updateAndGetNextState_prePlaying() {
        SLPlayer player = Mockito.mock(SLPlayer.class);
        players.add(player);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.initializeGameStates();

        assertEquals(GameState.INITIALIZED, slGame.updateAndGetNextState());
        assertEquals(GameState.STARTED, slGame.updateAndGetNextState());
    }

    @Test
    public void test_updateAndGetNextState_nextTurnPlayerDifferent() {
        SLPlayer player1 = Mockito.mock(SLPlayer.class);
        SLPlayer player2 = Mockito.mock(SLPlayer.class);

        SLMove move = Mockito.mock(SLMove.class);
        Mockito.when(move.getMoveType()).thenReturn(SLMoveType.NORMAL_ADVANCE);
        Mockito.when(move.isRolledASix()).thenReturn(false);

        Mockito.when(player1.getPreviousMove()).thenReturn(move);
        Mockito.when(player2.getPreviousMove()).thenReturn(move);

        players.add(player1);
        players.add(player2);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.initializeGameStates();

        assertEquals(GameState.INITIALIZED, slGame.updateAndGetNextState());
        assertEquals(GameState.STARTED, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(GameState.PLAYING, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(player2, slGame.getCurrentPlayer());
        assertEquals(GameState.PLAYING, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(player1, slGame.getCurrentPlayer());
        assertEquals(GameState.PLAYING, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(player2, slGame.getCurrentPlayer());
    }

    @Test
    public void test_updateAndGetNextState_rolledASix_nextTurnPlayerSame() {
        SLPlayer player1 = Mockito.mock(SLPlayer.class);
        SLPlayer player2 = Mockito.mock(SLPlayer.class);

        SLMove move = Mockito.mock(SLMove.class);
        Mockito.when(move.getMoveType()).thenReturn(SLMoveType.NORMAL_ADVANCE);
        Mockito.when(move.isRolledASix()).thenReturn(true);

        Mockito.when(player1.getPreviousMove()).thenReturn(move);
        Mockito.when(player2.getPreviousMove()).thenReturn(move);

        players.add(player1);
        players.add(player2);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.initializeGameStates();

        assertEquals(GameState.INITIALIZED, slGame.updateAndGetNextState());
        assertEquals(GameState.STARTED, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(GameState.PLAYING, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(player1, slGame.getCurrentPlayer()); // here is the difference, it will be player 1
    }

    @Test
    public void test_updateAndGetNextState_rolledLuckyMove_gameCompleted() {
        SLPlayer player1 = Mockito.mock(SLPlayer.class);

        SLMove move = Mockito.mock(SLMove.class);
        Mockito.when(move.getMoveType()).thenReturn(SLMoveType.ADVANCE_LUCKY_MOVE);
        Mockito.when(move.isRolledASix()).thenReturn(true);

        Mockito.when(player1.getPreviousMove()).thenReturn(move);

        players.add(player1);
        slGame = Mockito.spy(new SLGame(board, playerCount, dice, players));
        slGame.initializeGameStates();

        assertEquals(GameState.INITIALIZED, slGame.updateAndGetNextState());
        assertEquals(GameState.STARTED, slGame.updateAndGetNextState());
        slGame.selectNextPlayer();
        assertEquals(GameState.GAME_COMPLETED, slGame.updateAndGetNextState());
    }

}