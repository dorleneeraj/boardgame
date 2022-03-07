package com.board.games.domain.player;

import com.board.games.domain.game.Dice;
import com.board.games.domain.game.SnakeAndLadder;

import java.util.List;

/**
 * Player for {@link SnakeAndLadder} game
 */
public class SLPlayer extends Player {

    private Token token;
    
    public SLPlayer(String name, Token token) {
        super(name);
        this.token = token;
    }

    /**
     * Rolls the Dice and returns the Integer within the range 1 to 6
     *
     * @return
     */
    public Integer rollDice(Dice dice) {
        return dice.rollDice();
    }
    
    public Integer getCurrentPostion(){
        return this.token.getPosition();
    }
    
}
