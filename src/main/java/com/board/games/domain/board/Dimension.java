package com.board.games.domain.board;

/**
 * <p>
 * Stores the dimension for the {@link Board} of the game
 * </p>
 */
public class Dimension {

    private Integer row;
    private Integer column;

    public Dimension(Integer row, Integer column) {
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
