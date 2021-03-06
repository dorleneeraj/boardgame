package com.board.games.domain.cell;

import com.board.games.domain.board.Board;
import com.board.games.domain.move.Move;
import com.board.games.domain.token.Token;
import com.board.games.exception.GameException;

import java.util.List;

/**
 * <p>
 * Declares the generic interface for a Board cell for any Board Game
 * </p>
 */
public interface Cell {

    /**
     * <p>
     * Accepts the give token to the current cell
     * </p>
     *
     * @param token The {@link Token} that needs to be accepted
     * @return {@link Move}, represents the move that is performed by the
     * Token while entering the current cell
     */
    Move acceptToken(Token token) throws GameException;

    /**
     * <p>
     * Removes the Token from the current Cell
     * </p>
     *
     * @param token {@link Token} that needs to be removed
     * @return {@link Boolean} indicating whether the token was removed or
     * not. Returns <code>true</code> if the token was removed, else returns
     * <code>false</code>
     */
    Boolean removeToken(Token token);

    /**
     * <p>
     * Gets the position of the Cell on the {@link Board}
     * </p>
     *
     * @return The position of the current Cell on the Board
     */
    Integer getCellPosition();

    /**
     * <p>
     * Gets all the {@link Token}s on the current Cell
     * </p>
     *
     * @return {@link List} of all the tokens present on the Cell
     */
    List<Token> getCurrentTokensOnCell() throws GameException;

    List<? extends Cell> getNeighbours();

    void setNeighbours(List<? extends Cell> neighbours);
}
