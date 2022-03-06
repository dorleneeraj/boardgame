package com.board.games.domain.player;

import java.util.List;
import java.util.Map;

public class PlayerTurnInfo {
    private final Player player;
    private final Map<String, Object> turnDetailsMap;
    private final List<Move> movesPerformed;

    public PlayerTurnInfo(Player player, Map<String, Object> turnDetailsMap, List<Move> movesPerformed) {
        this.player = player;
        this.turnDetailsMap = turnDetailsMap;
        this.movesPerformed = movesPerformed;
    }
    
    public Object get(String turnInformation){
        return turnDetailsMap.get(turnInformation);
    }
    
    public Player getPlayer(){
        return this.player;
    }

    public List<Move> getMovesPerformed() {
        return movesPerformed;
    }
}
