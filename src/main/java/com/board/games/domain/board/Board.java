package com.board.games.domain.board;

import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.Cell;

import java.util.List;

/**
 * 
 */
public class Board {
    private final Dimension boardDimension;
    private final List<Cell> boardCells;
    private final Integer boardSize;
    
    public Board(Dimension boardDimension, List<Cell> boardCells) {
        this.boardDimension = boardDimension;
        this.boardCells = boardCells;
        this.boardSize = this.boardDimension.getColumn() * this.boardDimension.getRow();
        if(boardCells.size() != this.boardSize){
            throw new RuntimeException("FATAL :: Board cannot be constructed. Invalid board cells received to construct the board");
        }
    }
    
    public Cell getCellByNumber(int cellNumber){
        if(cellNumber >= 1 && cellNumber <= this.boardSize){
           return this.boardCells.get(cellNumber - 1);   
        }
        return null;
    }

    public Integer getBoardSize() {
        return boardSize;
    }
}
