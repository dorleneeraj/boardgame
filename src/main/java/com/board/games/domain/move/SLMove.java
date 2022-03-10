package com.board.games.domain.move;

import com.board.games.domain.move.Move;
import com.board.games.domain.move.MoveType;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the details of a single move by the Token on the Snakes and Ladder game board
 *
 * @see Move
 */
public class SLMove implements Move {

    private final MoveType moveType;
    private final String moveComments;
    private final Map<String, Object> moveDetails = new HashMap<>();
    private int fromPosition = 0;
    private int toPosition = 0;
    private int totalTilesMoved = 0;
    private Move interMediateMove = null;
    private int intermediatePosition = 0;
    private int totalTilesClimbed = 0;
    private int totalTilesDescended = 0;
    private int diceRoll = 0;

    public SLMove(MoveType moveType, int fromPosition, int toPosition, String comments) {
        this.moveType = moveType;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.moveComments = comments;
        this.totalTilesMoved = Math.abs(toPosition - fromPosition);

        moveDetails.put(MOVE_TYPE, this.moveType);
        moveDetails.put(MOVE_FROM_POSITION, this.fromPosition);
        moveDetails.put(MOVE_TO_POSITION, this.toPosition);
        moveDetails.put(MOVE_EXTRA_DETAILS, this.moveComments);
        moveDetails.put(MOVE_TOTAL_TILES, this.totalTilesMoved);
        moveDetails.put(MOVE_INTERMEDIATE_MOVE, interMediateMove);
        moveDetails.put(MOVE_INTERMEDIATE_POSITION, intermediatePosition);
        moveDetails.put(MOVE_TOTAL_CLIMBED, this.totalTilesClimbed);
        moveDetails.put(MOVE_TOTAL_DESCENDED, this.totalTilesDescended);
    }

    @Override
    public Map<String, Object> getMoveDetails() {
        return this.moveDetails;
    }

    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public String getMoveComments() {
        return moveComments;
    }

    public int getTotalTilesMoved() {
        return totalTilesMoved;
    }

    public Move getInterMediateMove() {
        return interMediateMove;
    }

    public void setTotalTilesMoved(int totalTilesMoved) {
        if (totalTilesMoved != 0) {
            this.totalTilesMoved = totalTilesMoved;
            this.moveDetails.put(MOVE_TOTAL_TILES, totalTilesMoved);
        }
    }

    public void setInterMediateMove(Move interMediateMove) {
        if (null != interMediateMove) {
            this.interMediateMove = interMediateMove;
            this.moveDetails.put(MOVE_INTERMEDIATE_MOVE, this.interMediateMove);
        }
    }

    public int getTotalTilesClimbed() {
        return totalTilesClimbed;
    }

    public void setTotalTilesClimbed(int totalTilesClimbed) {
        if (totalTilesClimbed != 0) {
            this.totalTilesClimbed = totalTilesClimbed;
            this.moveDetails.put(MOVE_TOTAL_CLIMBED, this.totalTilesClimbed);
        }
    }

    public int getTotalTilesDescended() {
        return totalTilesDescended;
    }

    public void setTotalTilesDescended(int totalTilesDescended) {
        if (totalTilesDescended != 0) {
            this.totalTilesDescended = totalTilesDescended;
            this.moveDetails.put(MOVE_TOTAL_DESCENDED, this.totalTilesDescended);
        }
    }

    public int getIntermediatePosition() {
        return intermediatePosition;
    }

    public void setIntermediatePosition(int intermediatePosition) {
        if (intermediatePosition != 0) {
            this.intermediatePosition = intermediatePosition;
            this.moveDetails.put(MOVE_INTERMEDIATE_POSITION, this.intermediatePosition);
        }
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }

    @Override
    public Object getMoveAttribute(String moveKey) {

        return moveDetails.get(moveKey);
    }

    @Override
    public void addMoveAttribute(String moveKey, Object moveAttribute) {
        moveDetails.put(moveKey, moveAttribute);
    }

    @Override
    public String toString() {
        return "TokenMove{" +
                "moveDetails=" + moveDetails +
                '}';
    }
}
