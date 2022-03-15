package com.board.games.domain.move;

import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

/**
 * Simple static factory to articulate Move Types
 */
public class SLMovesFactory {

    /**
     * <p>
     * Builds a simple {@link SLMoveType#NORMAL_ADVANCE} move.
     * </p>
     *
     * @param fromPosition Initial position of the Token
     * @param toPosition   Final position of the Token
     * @return {@link SLMove} that captures the details of the advancement
     */
    public static SLMove getAdvanceMove(Integer fromPosition, Integer toPosition) {
        return new SLMove(SLMoveType.NORMAL_ADVANCE, fromPosition, toPosition, "Normal Token Advance", toPosition - fromPosition);
    }

    /**
     * <p>
     * Builds a {@link SLMoveType#LADDER_ADVANCE} move. Ladder move has two steps:
     * <p>
     * In the first step, it moves from the initial position of the Token till the position rolled on the Dice.
     * In the second step, it moves UP from the intermediate position to the final position after encountering a
     * Ladder on the Board.
     * <p>
     * This Move encapsulates all the data related to the {@link SLMoveType#LADDER_ADVANCE} including the
     * {@link SLMove#MOVE_INTERMEDIATE_MOVE}, {@link SLMove#MOVE_TOTAL_CLIMBED} and other basic details
     * </p>
     * </p>
     *
     * @param intermediateStep {@link Move} from the initial position
     * @param ladderStep       {@link Move} from the intermediate position after encountering a Ladder
     * @return
     */
    public static SLMove getLadderAdvanceMove(Move intermediateStep, Move ladderStep) throws GameException {

        if (!(intermediateStep instanceof SLMove || ladderStep instanceof SLMove)) {
            throw ExceptionUtil.getInvalidMoveOperationException("Invalid steps received. Need step of type SLType");
        }

        Integer initialPosition = ((SLMove) intermediateStep).getFromPosition();
        Integer intermediatePosition = ((SLMove) intermediateStep).getToPosition();
        Integer finalPosition = ((SLMove) ladderStep).getToPosition();
        Integer totalStepsClimbed = finalPosition - intermediatePosition;
        Integer diceRoll = intermediatePosition - initialPosition;

        SLMove ladderMove = new SLMove(SLMoveType.LADDER_ADVANCE, initialPosition, finalPosition, "Encountered Ladder at position:" + intermediatePosition, diceRoll);
        ladderMove.setIntermediatePosition(intermediatePosition);
        ladderMove.setTotalTilesClimbed(totalStepsClimbed);
        ladderMove.setInterMediateMove(intermediateStep);

        return ladderMove;
    }

    /**
     * <p>
     * Builds a {@link SLMoveType#SNAKE_DESCEND} move. Snake move has two steps:
     * <p>
     * In the first step, it moves from the initial position of the Token till the position rolled on the Dice.
     * In the second step, it moves DOWN from the intermediate position to the final position after encountering a
     * Snake on the Board.
     * <p>
     * This Move encapsulates all the data related to the {@link SLMoveType#SNAKE_DESCEND} including the
     * {@link SLMove#MOVE_INTERMEDIATE_MOVE}, {@link SLMove#MOVE_TOTAL_DESCENDED} and other basic details
     * </p>
     * </p>
     *
     * @param intermediateStep {@link SLMove} from the initial position
     * @param snakeStep
     * @return
     */
    public static SLMove getSnakeDescendMove(Move intermediateStep, Move snakeStep) throws GameException {

        if (!(intermediateStep instanceof SLMove || snakeStep instanceof SLMove)) {
            throw ExceptionUtil.getInvalidMoveOperationException("Invalid steps received. Need step of type SLType");
        }

        Integer initialPosition = ((SLMove) intermediateStep).getFromPosition();
        Integer intermediatePosition = ((SLMove) intermediateStep).getToPosition();
        Integer finalPosition = ((SLMove) snakeStep).getToPosition();
        Integer totalStepsDescended = Math.abs(finalPosition - intermediatePosition);
        Integer totalTilesMoved = intermediatePosition - initialPosition;
        totalTilesMoved = totalTilesMoved + Math.abs(finalPosition - intermediatePosition);
        Integer diceRoll = intermediatePosition - initialPosition;

        SLMove snakeMove = new SLMove(SLMoveType.SNAKE_DESCEND, initialPosition, finalPosition, "Encountered Snake at position:" + intermediatePosition, diceRoll);
        snakeMove.setIntermediatePosition(intermediatePosition);
        snakeMove.setTotalTilesDescended(totalStepsDescended);
        snakeMove.setInterMediateMove(intermediateStep);
        snakeMove.setTotalTilesMoved(totalTilesMoved);

        return snakeMove;
    }

    /**
     * <p>
     * Gets an {@link SLMoveType#UNLUCKY_MOVE} move. It indicates that the player got an Unlucky roll on the Dice
     * when the Player token could not finish the game in a required single step.
     * </p>
     *
     * @param position current position of the Token
     * @return
     */
    public static SLMove getUnluckyMove(Integer position, Integer diceRoll) {
        return new SLMove(SLMoveType.UNLUCKY_MOVE, position, position, "Encountered an Unlucky Move at: " + position, diceRoll);
    }

    /**
     * @param fromPosition
     * @param toPosition
     * @return
     */
    public static SLMove getLuckyMove(Integer fromPosition, Integer toPosition) {
        return new SLMove(SLMoveType.ADVANCE_LUCKY_MOVE, fromPosition, toPosition, "Encountered an Lucky Move at: " + fromPosition, toPosition - fromPosition);
    }
}
