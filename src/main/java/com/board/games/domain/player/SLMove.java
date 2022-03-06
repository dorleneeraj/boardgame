package com.board.games.domain.player;

import com.board.games.domain.player.MoveType;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the details of a single move by the Token on the Snakes and Ladder game board
 *
 * @see com.board.games.domain.player.Move
 */
public class SLMove implements Move {

    private final Integer fromPosition;
    private final Integer toPosition;
    private final MoveType moveType;
    private final String moveComments;
    private final Integer totalTilesMoved;
    private final Map<String, Object> moveDetails = new HashMap<>();

    private Move interMediateMove;
    private Integer intermediatePosition;
    private Integer totalTilesClimbed;
    private Integer totalTilesDescended;

    public SLMove(MoveType moveType, Integer fromPosition, Integer toPosition, String comments) {
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
    }

    @Override
    public Map<String, Object> getMoveDetails() {
        return this.moveDetails;
    }

    public Integer getFromPosition() {
        return fromPosition;
    }

    public Integer getToPosition() {
        return toPosition;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public String getMoveComments() {
        return moveComments;
    }

    public Integer getTotalTilesMoved() {
        return totalTilesMoved;
    }

    public Move getInterMediateMove() {
        if (null == this.interMediateMove && this.moveDetails.containsKey(MOVE_INTERMEDIATE_MOVE)) {
            this.interMediateMove = (SLMove) moveDetails.get(MOVE_INTERMEDIATE_MOVE);
        }
        return interMediateMove;
    }

    public void setInterMediateMove(Move interMediateMove) {
        if (null != interMediateMove) {
            this.interMediateMove = interMediateMove;
            this.moveDetails.put(MOVE_INTERMEDIATE_MOVE, this.interMediateMove);
        }
    }

    public Integer getTotalTilesClimbed() {
        if (this.totalTilesClimbed == 0 && this.moveDetails.containsKey(MOVE_TOTAL_CLIMBED)) {
            this.totalTilesClimbed = (Integer) this.moveDetails.get(MOVE_TOTAL_CLIMBED);
        }
        return totalTilesClimbed;
    }

    public void setTotalTilesClimbed(Integer totalTilesClimbed) {
        if (this.totalTilesClimbed != 0) {
            this.totalTilesClimbed = totalTilesClimbed;
            this.moveDetails.put(MOVE_TOTAL_CLIMBED, this.totalTilesClimbed);
        }
    }

    public Integer getTotalTilesDescended() {
        if (this.totalTilesDescended == 0 && this.moveDetails.containsKey(MOVE_TOTAL_DESCENDED)) {
            this.totalTilesDescended = (Integer) this.moveDetails.get(MOVE_TOTAL_DESCENDED);
        }
        return totalTilesDescended;
    }

    public void setTotalTilesDescended(Integer totalTilesDescended) {
        if (totalTilesDescended != 0) {
            this.totalTilesDescended = totalTilesDescended;
            this.moveDetails.put(MOVE_TOTAL_DESCENDED, this.totalTilesDescended);
        }
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
