package com.board.games.domain.cell;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.game.SLGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.move.SLMovesFactory;
import com.board.games.domain.token.Token;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

import java.util.List;

/**
 * <p>
 * Implementation of the {@link Cell} indicating that this is a Snake cell
 * for {@link SLGame}.
 * Once a {@link Token} is arrived at this cell, two steps occur in the order:
 * <p>
 *     <ol>
 *         <li>The <code>snakeStartCell</code> first accepts the Token,
 *         indicating a move of type {@link SLMoveType#NORMAL_ADVANCE}</li>
 *         <li>
 *             The <code>snakeEndCell</code> accepts the token, which moves
 *             the token further down the Snake,
 *             indicating a move of type {@link SLMoveType#SNAKE_DESCEND}</li>
 *     </ol>
 * </p>
 * <p>
 * The final position of the Token will be that of the <code>snakeEndCell</code>
 * </p>
 */
public class SnakeCell extends SLBoardCell {

    private final SLBoardCell snakeStartCell;
    private final SLBoardCell snakeEndCell;

    public SnakeCell(SLBoardCell snakeStartCell, SLBoardCell snakeEndCell) {
        super(snakeStartCell.getCellPosition());
        this.snakeStartCell = snakeStartCell;
        this.snakeEndCell = snakeEndCell;
    }

    @Override
    public Move acceptToken(Token token) throws GameException {
        Move intermediateMove = snakeStartCell.acceptToken(token);
        removeToken(token);
        Move snakeStep = snakeEndCell.acceptToken(token);
        return SLMovesFactory.getSnakeDescendMove(intermediateMove, snakeStep);
    }

    @Override
    @JacocoExcludeGenerated
    public Boolean removeToken(Token token) {
        return snakeStartCell.removeToken(token);
    }

    @Override
    public Integer getCellPosition() {
        return this.snakeStartCell.getCellPosition();
    }

    @Override
    public List<Token> getCurrentTokensOnCell() throws GameException {
        throw ExceptionUtil.getInvalidCellOperationException(
                "Snake cells " + "cannot have any tokens. Once the token" + " is arrived at a snake" +
                        " cell, it is moved down the snake");
    }
}
