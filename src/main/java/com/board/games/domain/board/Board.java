package com.board.games.domain.board;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.game.BoardGame;

/**
 * <p>
 * A representation of a board used by the {@link BoardGame}
 * </p>
 */
public abstract class Board {

    private final Dimension dimension;

    public Board(Dimension dimension) {
        this.dimension = dimension;
    }

    public abstract Cell getCellByNumber(int cellNumber);

    public abstract Integer getBoardSize();

    public Dimension getDimension() {
        return this.dimension;
    }

}
