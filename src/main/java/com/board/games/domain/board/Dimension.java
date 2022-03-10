package com.board.games.domain.board;

import java.util.Objects;

/**
 * <p>
 * Stores the dimension for the {@link Board} of the game
 * </p>
 */
public class Dimension {

    private Integer row;
    private Integer column;

    public Dimension(Integer row, Integer column) {
        Objects.requireNonNull(row);
        Objects.requireNonNull(column);
        if (row <= 0 || column <= 0) {
            throw new RuntimeException("Invalid data received for Board Dimension. Board Dimension needs to have positive " +
                    "values for row and column respectively");
        }
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
