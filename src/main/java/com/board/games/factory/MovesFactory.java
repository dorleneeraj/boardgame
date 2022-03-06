package com.board.games.factory;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.MoveType;
import com.board.games.domain.player.SLMove;

/**
 * Simple static factory to articulate Move Types
 */
public class MovesFactory {
    
    public static Move getAdvanceMove(Integer fromPosition, Integer toPosition){
        return new SLMove(MoveType.NORMAL_ADVANCE, fromPosition, toPosition, "Normal Token Advance");
    }
    
    public static Move getLadderAdvanceMove(Integer fromPosition, Integer toPosition){
        return new SLMove(MoveType.LADDER_ADVANCE, fromPosition, toPosition, "Encountered Ladder at: " + fromPosition );
    }

    public static Move getSnakeDescendMove(Integer fromPosition, Integer toPosition){
        return new SLMove(MoveType.SNAKE_DESCEND, fromPosition, toPosition, "Encountered Snake at: " + fromPosition );
    }

    public static SLMove getUnluckyMove() {
        return null;
    }
}
