package com.board.games.domain.cell;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.game.SLGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.token.Token;
import com.board.games.exception.GameException;

import java.util.List;

/**
 * <p>
 * Implementation of the {@link Cell} indicating that this is the final/
 * winning cell for {@link SLGame}.
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
        super(boardCell.getCellPosition());
        this.boardCell = boardCell;
    }

    /**
     * <p>
     * The {@link SLFinalCell#boardCell} performs the accept operation
     * internally
     *
     * </p>
     *
     * @param token The {@link Token} that needs to be accepted
     * @return {@link SLMove} with move type
     * {@link SLMoveType#ADVANCE_LUCKY_MOVE}
     */
    @Override
    public Move acceptToken(Token token) throws GameException {
        int currentTokenCell = token.getPosition();
        SLMove move = (SLMove) boardCell.acceptToken(token);
        if (currentTokenCell >= (boardCell.getCellPosition() - 6)) {
            return SLMovesFactory.getLuckyMove(move.getFromPosition(), move.getToPosition());
        }
        return move;
    }

    @Override
    @JacocoExcludeGenerated
    public Boolean removeToken(Token token) {
        return false;
    }

    @Override
    @JacocoExcludeGenerated
    public Integer getCellPosition() {
        return boardCell.getCellPosition();
    }

    @Override
    @JacocoExcludeGenerated
    public List<Token> getCurrentTokensOnCell() throws GameException {
        return boardCell.getCurrentTokensOnCell();
    }
}
