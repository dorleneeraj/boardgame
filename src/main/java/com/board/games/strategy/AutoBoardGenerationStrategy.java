package com.board.games.strategy;

import com.board.games.domain.board.Board;
import com.board.games.domain.board.Dimension;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.LadderCell;
import com.board.games.domain.cell.SnakeCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Auto - generates a board for Snake and Ladder
 */
public class AutoBoardGenerationStrategy implements BoardGenerationStrategy {

    private Dimension boardDimension;

    public AutoBoardGenerationStrategy(Dimension boardDimension) {
        this.boardDimension = boardDimension;
    }

    @Override
    public Board generateBoard() {

        List<Cell> boardCells = new ArrayList<>();
        int size = this.boardDimension.getColumn() * this.boardDimension.getRow();
        IntStream.range(1, size + 1).forEach(cellNumber -> {
            boardCells.add(new SLBoardCell(cellNumber));
        });

        List<Tuple> ladderTuples = Arrays.asList(new Tuple(4, 25), new Tuple(13, 46), new Tuple(42, 63),
                new Tuple(50, 69), new Tuple(62, 81), new Tuple(74, 92));

        List<Tuple> snakeTuples = Arrays.asList(new Tuple(99, 41), new Tuple(89, 53), new Tuple(76, 58),
                new Tuple(66, 45), new Tuple(54, 31), new Tuple(43, 18), new Tuple(40, 3), new Tuple(27, 5));

        ladderTuples.forEach(tuple -> {
            if (tuple.start > tuple.end) {
                throw new RuntimeException("Invalid ladder configuration - start: " + tuple.start + ", end: " + tuple.end +
                        ", ladder start cannot be greater than ladder end");
            }
            
            Cell ladderStartCell = boardCells.get(tuple.start - 1);
            Cell ladderEndCell = boardCells.get(tuple.end - 1);
            boardCells.set(tuple.start - 1, new LadderCell(ladderStartCell, ladderEndCell));
        });

        snakeTuples.forEach(tuple -> {
            if (tuple.start < tuple.end) {
                throw new RuntimeException("Invalid snake configuration - end: " + tuple.end + ", start: " + tuple.start +
                        ", snake start cannot be less than snake end");
            }

            Cell snakeStartCell = boardCells.get(tuple.start - 1);
            Cell snakeEndCell = boardCells.get(tuple.end - 1);
            boardCells.set(tuple.start - 1, new SnakeCell(snakeStartCell, snakeEndCell));
        });
        
        return new Board(this.boardDimension, boardCells);
    }

    class Tuple {
        int start;
        int end;

        public Tuple(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }
}
