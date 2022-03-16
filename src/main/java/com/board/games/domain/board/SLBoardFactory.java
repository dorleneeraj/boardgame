package com.board.games.domain.board;

import com.board.games.domain.cell.Cell;
import com.board.games.domain.cell.LadderCell;
import com.board.games.domain.cell.SLBoardCell;
import com.board.games.domain.cell.SLFinalCell;
import com.board.games.domain.cell.SnakeCell;
import com.board.games.domain.game.SLGame;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <p>A Simple static factory to generate an instance of {@link Board} for
 * the {@link SLGame}</p>
 */
public class SLBoardFactory {

    /**
     * <p>
     * Generates the default board for the Snake and Ladder Game
     * </p>
     *
     * @return
     * @throws GameException
     */
    public static Board getDefaultBoard() throws GameException {
        List<SLTuple> ladderTuples = Arrays.asList(new SLTuple(4, 25), new SLTuple(13, 46), new SLTuple(42, 63),
                new SLTuple(50, 69), new SLTuple(62, 81), new SLTuple(74, 92));
        List<SLTuple> snakeTuples = Arrays.asList(new SLTuple(99, 41), new SLTuple(89, 53), new SLTuple(76, 58),
                new SLTuple(66, 45), new SLTuple(54, 31), new SLTuple(43, 18), new SLTuple(40, 3), new SLTuple(27, 5));
        Dimension dimension = new Dimension(10, 10);
        return getConfigurableBoard(ladderTuples, snakeTuples, dimension);
    }

    /**
     * <p>Generates the Board from the given details<p/>
     *
     * @param ladderTuples {@link LadderCell} tuples that need to be included
     *                     on the board
     * @param snakeTuples  {@link SnakeCell} tuples that need to be included
     *                     on the board
     * @param dimension    {@link Dimension} for the board
     * @return {@link Board} for the game of {@link SLGame}
     * @throws GameException
     */
    public static Board getConfigurableBoard(List<SLTuple> ladderTuples, List<SLTuple> snakeTuples, Dimension dimension)
            throws GameException {

        validateConfiguration(ladderTuples, snakeTuples, dimension);

        int size = dimension.getColumn() * dimension.getRow();
        List<SLBoardCell> boardCells = new ArrayList<>();
        IntStream.range(1, size + 1).forEach(cellNumber -> {
            boardCells.add(new SLBoardCell(cellNumber));
        });

        try {
            addLadders(ladderTuples, boardCells);
            addSnakes(snakeTuples, boardCells);
        } catch (Exception e) {
            throw ExceptionUtil.getInvalidCellConfigurationException(e.getMessage());
        }
        boardCells.set(size - 1, new SLFinalCell(boardCells.get(size - 1)));
        setNeighbours(boardCells);
        return new Board(dimension, boardCells);
    }

    /**
     * <p>Validates the data provided to construct the board</p>
     * <p>
     * Following constraints are applied:
     * <ol>
     *     <li>Dimension for the given board cannot be null</li>
     *     <li>The board for {@link SLGame} is a square, so <code>row</code>
     *     and <code>column</code> values should be equal and positive</li>
     *     <li>There should be at least 1 ladder and snake tuple for the
     *     Snake and Ladder game respectively</li>
     *     <li>Ladders and Snakes should not overlap with each other,
     *     meaning, their start and end should not coincide</li>
     * </ol>
     * </p>
     *
     * @param ladderTuples
     * @param snakeTuples
     * @throws GameException
     */
    protected static void validateConfiguration(List<SLTuple> ladderTuples, List<SLTuple> snakeTuples,
                                                Dimension dimension)
            throws GameException {

        if (null == dimension) {
            throw ExceptionUtil.getInvalidBoardConfigurationException(
                    "Dimension cannot be null to construct a Snake " + "and " + "Ladder" + " game board");
        }

        if (dimension.getColumn() != dimension.getRow()) {
            throw ExceptionUtil.getInvalidBoardConfigurationException(
                    "Snake " + "and Ladder board is a square. Row " + "and Column in the " + "dimension should be " +
                            "equal");
        }

        if (dimension.getColumn() <= 0 || dimension.getRow() <= 0) {
            throw ExceptionUtil.getInvalidBoardConfigurationException(
                    "Snake " + "and Ladder board needs to have a " + "positive row and " + "column" + " dimensions");
        }

        if (null == ladderTuples || ladderTuples.isEmpty() || null == snakeTuples || snakeTuples.isEmpty()) {
            throw ExceptionUtil.getInvalidCellConfigurationException(
                    "Snake " + "and Ladder game requires ladder and " + "snake cells");
        }
        boolean foundOverlappingCell = ladderTuples.stream().filter(ladderTuple -> snakeTuples.contains(ladderTuple))
                .findFirst().isPresent();

        if (foundOverlappingCell) {
            throw ExceptionUtil.getInvalidCellConfigurationException(
                    "Ladder " + "and Snake tuples cannot overlap " + "with each other");
        }
    }

    /**
     * <p>For each cell on the board, its neighbouring cells are set as its
     * neighbours.</p>
     * <p>2 cells after and 2 cells before are considered as neighbours for
     * the cell.</p>
     *
     * @param boardCells
     */
    private static void setNeighbours(List<SLBoardCell> boardCells) {
        for (Cell cell : boardCells) {
            List<SLBoardCell> neighbours = new ArrayList<>();
            int cellNumber = cell.getCellPosition();
            List<Integer> neighbourIndices = Arrays.asList(cellNumber - 1, cellNumber - 2, cellNumber + 1,
                    cellNumber + 1);
            neighbourIndices.forEach(index -> {
                if (index >= 0 && index < boardCells.size()) {
                    neighbours.add(boardCells.get(index));
                }
            });
            cell.setNeighbours(neighbours);
        }
    }

    /**
     * <p>Adds snake cells on the board</p>
     *
     * @param snakeTuples Given list of {@link SLTuple} indicating Snakes
     * @param boardCells
     */
    private static void addSnakes(List<SLTuple> snakeTuples, List<SLBoardCell> boardCells) {
        snakeTuples.forEach(tuple -> {
            if (tuple.getStart() < tuple.getEnd()) {
                throw new RuntimeException(
                        "Snake Cell Configuration error - " + "end: " + tuple.getEnd() + ", start" + ":" + " " +
                                tuple.getStart() + ", snake start cannot be less than snake end");
            }

            if (tuple.getStart() == tuple.getEnd()) {
                throw new RuntimeException("Snake start and Snake end cannot " + "be equal");
            }

            SLBoardCell snakeStartCell = boardCells.get(tuple.getStart() - 1);
            SLBoardCell snakeEndCell = boardCells.get(tuple.getEnd() - 1);
            boardCells.set(tuple.getStart() - 1, new SnakeCell(snakeStartCell, snakeEndCell));
        });
    }

    /**
     * <p>Adds ladder cells on the board</p>
     *
     * @param ladderTuples Given list of {@link SLTuple} indicating Ladders
     * @param boardCells
     */
    private static void addLadders(List<SLTuple> ladderTuples, List<SLBoardCell> boardCells) {
        ladderTuples.forEach(tuple -> {
            if (tuple.getStart() > tuple.getEnd()) {
                throw new RuntimeException(
                        "Ladder Cell Configuration error " + "-" + " start: " + tuple.getStart() + ", end: " +
                                tuple.getEnd() + ", ladder start cannot be greater than ladder end");
            }

            if (tuple.getStart() == tuple.getEnd()) {
                throw new RuntimeException("Ladder start and Ladder end " + "cannot be equal");
            }

            SLBoardCell ladderStartCell = boardCells.get(tuple.getStart() - 1);
            SLBoardCell ladderEndCell = boardCells.get(tuple.getEnd() - 1);
            boardCells.set(tuple.getStart() - 1, new LadderCell(ladderStartCell, ladderEndCell));
        });
    }

}
