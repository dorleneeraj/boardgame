package com.board.games.domain.move;

import com.board.games.domain.cell.Cell;

import java.util.Map;

/**
 * <p>
 * Whenever any token/ piece is moved from a {@link Cell} to another Cell it constitutes a move. Every game will
 * have different details of the move.
 * </p>
 * <br/>
 * <p>
 * For ex: A move in the game of Chess will have to encapsulate which piece was
 * moved, did it involve opponent's piece being killed etc etc.
 * </p>
 * <br/>
 * <p>
 * For the game of Snake and Ladder, a move will constitute of various things like:
 * <p>
 *         <ol>
 *          <li>The token was moved from which cell</li>
 *          <li>The token was moved to which cell</li>
 *          <li>Did a move involved an intermediate Ladder Cell or a Snake Cell</li>
 *          <li>In case of a Ladder/ Snake Cell, how many tiles were climbed or descended respectively</li>
 *          <li>Total number of tiles moved in either of the cases</li>
 *         </ol>
 *
 *     </p>
 * </p>
 */
public interface Move {

    public static final String MOVE_TYPE = "MOVE_TYPE";
    public static final String MOVE_FROM_POSITION = "MOVE_FROM_POSITION";
    public static final String MOVE_TO_POSITION = "MOVE_TO_POSITION";
    public static final String MOVE_EXTRA_DETAILS = "MOVE_EXTRA_DETAILS";
    public static final String MOVE_TOTAL_TILES = "MOVE_TOTAL_TILES";
    public static final String MOVE_TOTAL_CLIMBED = "MOVE_TOTAL_CLIMBED";
    public static final String MOVE_TOTAL_DESCENDED = "MOVE_TOTAL_DESCENDED";
    public static final String MOVE_INTERMEDIATE_MOVE = "MOVE_INTERMEDIATE_MOVE";
    public static final String MOVE_INTERMEDIATE_POSITION = "MOVE_INTERMEDIATED_POSITION";

    Map<String, Object> getMoveDetails();

    Object getMoveAttribute(String moveKey);

    void addMoveAttribute(String moveKey, Object moveAttribute);
}
