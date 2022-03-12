package com.board.games.domain.cell;

import com.board.games.domain.game.SLGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.token.Token;

import java.util.List;

/**
 * <p>
 * Implementation of the {@link Cell} indicating that this is a Ladder cell for {@link SLGame}.
 * Once a {@link Token} is arrived at this cell, two steps occur in the order:
 * <p>
 *     <ol>
 *         <li>The <code>ladderStartCell</code> first accepts the Token, indicating a move of type {@link SLMoveType#NORMAL_ADVANCE}</li>
 *         <li>
 *             The <code>ladderEndCell</code> accepts the token, which moves the token further up the Ladder,
 *             indicating a move of type {@link SLMoveType#LADDER_ADVANCE}</li>
 *     </ol>
 * </p>
 * <p>
 * The final position of the Token will be that of the <code>ladderEndCell</code>
 * </p>
 */
public class LadderCell extends SLBoardCell {
    protected SLBoardCell ladderStartCell;
    protected SLBoardCell ladderEndCell;

    public LadderCell(SLBoardCell ladderStartCell, SLBoardCell ladderEndCell) {
        this.ladderStartCell = ladderStartCell;
        this.ladderEndCell = ladderEndCell;
    }

    @Override
    public Move acceptToken(Token token) {
        Move intermediateMove = ladderStartCell.acceptToken(token);
        removeToken(token);
        Move ladderAcceptMove = ladderEndCell.acceptToken(token);
        return SLMovesFactory.getLadderAdvanceMove(intermediateMove, ladderAcceptMove);
    }

    @Override
    public Boolean removeToken(Token token) {
        return ladderStartCell.removeToken(token);
    }

    @Override
    public Integer getCellPosition() {
        return this.ladderStartCell.getCellPosition();
    }

    @Override
    public List<Token> getCurrentTokensOnCell() {
        throw new RuntimeException("Ladder Cells can't have Tokens! Tokens are pushed up!");
    }
}
