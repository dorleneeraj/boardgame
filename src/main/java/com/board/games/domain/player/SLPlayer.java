package com.board.games.domain.player;

import com.board.games.domain.game.Dice;
import com.board.games.domain.game.Game;
import com.board.games.domain.game.SnakeAndLadder;
import com.board.games.domain.token.Token;

/**
 * Player for {@link SnakeAndLadder} game
 */
public class SLPlayer extends Player {

    private final Token token;

    protected SLPlayer(String name, Token token) {
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

    public Integer getCurrentPosition() {
        return this.token.getPosition();
    }

    @Override
    public void quitGame(Game game) {

    }

    public Token getToken() {
        return token;
    }

    public static class SLPlayerBuilder {
        private Token token;
        private String name;

        public SLPlayerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SLPlayerBuilder withToken(Token token) {
            this.token = token;
            return this;
        }

        public SLPlayer build() {
            if (null == this.name || ("").equals(this.name)) {
                throw new RuntimeException("Player Name cannot be null or empty");
            }

            if (null == this.token) {
                throw new RuntimeException("Player Token cannot be null");
            }
            SLPlayer player = new SLPlayer(this.name, this.token);
            return player;
        }
    }
}
