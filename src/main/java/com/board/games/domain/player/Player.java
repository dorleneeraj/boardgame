package com.board.games.domain.player;

import com.board.games.domain.game.Game;
import com.board.games.domain.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * <p>An entity representing a board game player</p>
 */
public abstract class Player {

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables
    ///////////////////////////////////////////////////////////////////////////
    private final String name;
    private final Stack<Move> playerMoves = new Stack<>();

    public Player(String name) {
        this.name = name;
    }

    public List<Move> getAllMoves() {
        return new ArrayList<>(playerMoves);
    }

    /**
     * <p>Adds a {@link Move} played by the player</p>
     *
     * @param playerMove
     */
    public void addMove(Move playerMove) {
        this.playerMoves.push(playerMove);
    }

    /**
     * <p>Returns the previous {@link Move} played by the Player</p>
     *
     * @return
     */
    public Move getPreviousMove() {
        return playerMoves.peek();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getName() {
        return name;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Abstract methods
    ///////////////////////////////////////////////////////////////////////////

    public abstract void quitGame(Game game);
}
