package com.board.games.domain.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An entity representing a board game player
 */
public abstract class Player {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////
    private String name;
    private final Stack<Move> playerMoves = new Stack<>();

    public Player(String name) {
        this.name = name;
    }

    public List<Move> getAllMoves() {
        return new ArrayList<>(playerMoves);
    }

    public void addMove(Move playerMove) {
        this.playerMoves.push(playerMove);
    }

    public Move getLastMove() {
        return playerMoves.peek();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
