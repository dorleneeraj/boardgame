package com.board.games.domain.cell;

import com.board.games.domain.game.SnakeAndLadderGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.token.Token;
import com.board.games.domain.move.SLMovesFactory;

import java.util.List;

/**
 * <p>
 * Implementation of the {@link Cell} indicating that this is the final/ winning cell for {@link SnakeAndLadderGame}.
 * Once a {@link Token} is arrived at this cell, the game ends.
 * </p>
 *
 * <p>
 * It decorates a {@link SLBoardCell} when this Cell will be constructed
 * </p>
 */
public class SLFinalCell extends SLBoardCell {

    private final SLBoardCell boardCell;

    public SLFinalCell(SLBoardCell boardCell) {
        this.boardCell = boardCell;
    }

    /**
     * <p>
     * The {@link SLFinalCell#boardCell} performs the accept operation internally
     *
     * </p>
     *
     * @param token The {@link Token} that needs to be accepted
     * @return {@link SLMove} with move type {@link SLMoveType#ADVANCE_LUCKY_MOVE}
     */
    @Override
    public Move acceptToken(Token token) {
        SLMove move = (SLMove) boardCell.acceptToken(token);
        return SLMovesFactory.getLuckyMove(move.getFromPosition(), move.getToPosition());
    }

    @Override
    public Boolean removeToken(Token token) {
        return false;
    }

    @Override
    public Integer getCellPosition() {
        return boardCell.getCellPosition();
    }

    @Override
    public List<Token> getCurrentTokensOnCell() {
        return boardCell.getCurrentTokensOnCell();
    }
}