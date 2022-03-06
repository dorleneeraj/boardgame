package com.board.games.domain.player;

import com.board.games.domain.board.Board;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.game.BoardGame;
import com.board.games.domain.game.Dice;
import com.board.games.domain.game.SnakeAndLadder;
import com.board.games.factory.MovesFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Player for {@link SnakeAndLadder} game
 */
public class SLPlayer extends Player {

    private Token token;
    private Dice dice;

    public SLPlayer(String name, Token token) {
        super(name);
        this.token = token;
    }

    /**
     * Rolls the Dice and returns the Integer within the range 1 to 6
     *
     * @return
     */
    private Integer rollDice() {
        return dice.rollDice();
    }

    private SLMove movePlayer(Cell fromCell, Cell toCell) {
        SLMove move = null;
        if (null != fromCell) {
            fromCell.removeToken(this.token);
        }
        if (null != toCell) {
            move = (SLMove) toCell.acceptToken(this.token);
        } else {
            move = MovesFactory.getUnluckyMove();
        }
        return move;
    }

    private void updatePlayerPosition(Integer newPosition) {
        this.token.setPosition(newPosition);
    }

    public Integer getCurrentPosition() {
        return this.token.getPosition();
    }

    @Override
    public PlayerTurnInfo playGameTurn(BoardGame game) {
        Board gameBoard = game.getGameBoard();
        Integer roll = rollDice();
        Integer currentPlayerPosition = getCurrentPosition();
        Cell currentPlayerCell = gameBoard.getCellByNumber(currentPlayerPosition);
        Cell nextCell = gameBoard.getCellByNumber(currentPlayerPosition + roll);
        movePlayer(currentPlayerCell, nextCell);
        return buildStats();
    }

    protected PlayerTurnInfo buildStats() {

        return null;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    @Override
    public List<Move> getAllMoves() {
        return null;
    }
}
