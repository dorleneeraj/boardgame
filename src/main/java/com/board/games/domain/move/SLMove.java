package com.board.games.domain.move;

import com.board.games.JacocoExcludeGenerated;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the details of a single move by the Token on the Snakes and
 * Ladder game board
 *
 * @see Move
 */
public class SLMove implements Move {

    public static final String MOVE_TYPE = "MOVE_TYPE";
    public static final String MOVE_FROM_POSITION = "MOVE_FROM_POSITION";
    public static final String MOVE_TO_POSITION = "MOVE_TO_POSITION";
    public static final String MOVE_EXTRA_DETAILS = "MOVE_EXTRA_DETAILS";
    public static final String MOVE_TOTAL_TILES = "MOVE_TOTAL_TILES";
    public static final String MOVE_TOTAL_CLIMBED = "MOVE_TOTAL_CLIMBED";
    public static final String MOVE_TOTAL_DESCENDED = "MOVE_TOTAL_DESCENDED";
    public static final String MOVE_INTERMEDIATE_MOVE = "MOVE_INTERMEDIATE_MOVE";
    public static final String MOVE_INTERMEDIATE_POSITION = "MOVE_INTERMEDIATED_POSITION";

    protected final Map<String, Object> moveDetails = new HashMap<>();

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables to capture the details of the Move
    ///////////////////////////////////////////////////////////////////////////
    protected final SLMoveType SLMoveType;
    protected final String moveComments;

    protected int fromPosition = 0;
    protected int toPosition = 0;

    protected Move interMediateMove = null;
    protected int intermediatePosition = 0;

    protected int totalTilesClimbed = 0;
    protected int totalTilesMoved = 0;
    protected int totalTilesDescended = 0;

    protected int diceRoll = 0;

    protected boolean missedSnakeLuckily = false;
    protected boolean rolledASix = false;

    public SLMove(SLMoveType SLMoveType, int fromPosition, int toPosition, String comments, int diceRoll) {
        this.SLMoveType = SLMoveType;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.moveComments = comments;
        this.totalTilesMoved = Math.abs(toPosition - fromPosition);
        this.diceRoll = diceRoll;
        if (this.diceRoll == 6) {
            rolledASix = true;
        }

        moveDetails.put(MOVE_TYPE, this.SLMoveType);
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
    @JacocoExcludeGenerated
    public Map<String, Object> getMoveDetails() {
        return this.moveDetails;
    }

    @JacocoExcludeGenerated
    public int getFromPosition() {
        return fromPosition;
    }

    @JacocoExcludeGenerated
    public int getToPosition() {
        return toPosition;
    }

    @JacocoExcludeGenerated
    public SLMoveType getMoveType() {
        return SLMoveType;
    }

    @JacocoExcludeGenerated
    public String getMoveComments() {
        return moveComments;
    }

    @JacocoExcludeGenerated
    public int getTotalTilesMoved() {
        return totalTilesMoved;
    }

    @JacocoExcludeGenerated
    public void setTotalTilesMoved(int totalTilesMoved) {
        if (totalTilesMoved != 0) {
            this.totalTilesMoved = totalTilesMoved;
            this.moveDetails.put(MOVE_TOTAL_TILES, totalTilesMoved);
        }
    }

    @JacocoExcludeGenerated
    public Move getInterMediateMove() {
        return interMediateMove;
    }

    @JacocoExcludeGenerated
    public void setInterMediateMove(Move interMediateMove) {
        if (null != interMediateMove) {
            this.interMediateMove = interMediateMove;
            this.moveDetails.put(MOVE_INTERMEDIATE_MOVE, this.interMediateMove);
        }
    }

    @JacocoExcludeGenerated
    public int getTotalTilesClimbed() {
        return totalTilesClimbed;
    }

    @JacocoExcludeGenerated
    public void setTotalTilesClimbed(int totalTilesClimbed) {
        if (totalTilesClimbed != 0) {
            this.totalTilesClimbed = totalTilesClimbed;
            this.moveDetails.put(MOVE_TOTAL_CLIMBED, this.totalTilesClimbed);
        }
    }

    @JacocoExcludeGenerated
    public int getTotalTilesDescended() {
        return totalTilesDescended;
    }

    @JacocoExcludeGenerated
    public void setTotalTilesDescended(int totalTilesDescended) {
        if (totalTilesDescended != 0) {
            this.totalTilesDescended = totalTilesDescended;
            this.moveDetails.put(MOVE_TOTAL_DESCENDED, this.totalTilesDescended);
        }
    }

    @JacocoExcludeGenerated
    public int getIntermediatePosition() {
        return intermediatePosition;
    }

    @JacocoExcludeGenerated
    public void setIntermediatePosition(int intermediatePosition) {
        if (intermediatePosition != 0) {
            this.intermediatePosition = intermediatePosition;
            this.moveDetails.put(MOVE_INTERMEDIATE_POSITION, this.intermediatePosition);
        }
    }

    @JacocoExcludeGenerated
    public int getDiceRoll() {
        return diceRoll;
    }

    @JacocoExcludeGenerated
    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }

    @JacocoExcludeGenerated
    public boolean isMissedSnakeLuckily() {
        return missedSnakeLuckily;
    }

    @JacocoExcludeGenerated
    public void setMissedSnakeLuckily(boolean missedSnakeLuckily) {
        this.missedSnakeLuckily = missedSnakeLuckily;
    }

    @JacocoExcludeGenerated
    public boolean isRolledASix() {
        return rolledASix;
    }

    @JacocoExcludeGenerated
    public void setRolledASix(boolean rolledASix) {
        this.rolledASix = rolledASix;
    }

    @Override
    @JacocoExcludeGenerated
    public Object getMoveAttribute(String moveKey) {
        return moveDetails.get(moveKey);
    }

    @Override
    @JacocoExcludeGenerated
    public void addMoveAttribute(String moveKey, Object moveAttribute) {
        moveDetails.put(moveKey, moveAttribute);
    }

    @Override
    @JacocoExcludeGenerated
    public String toString() {
        return "TokenMove{" + "moveDetails=" + moveDetails + '}';
    }
}
