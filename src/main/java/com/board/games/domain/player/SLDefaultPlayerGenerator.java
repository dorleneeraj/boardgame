package com.board.games.domain.player;

import com.board.games.domain.token.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class SLDefaultPlayerGenerator implements PlayerGenerator {

    private static SLDefaultPlayerGenerator instance;
    private final List<SLPlayer> players;
    private final int numberOfPlayers;
    private final int INITIAL_PLAYER_POSITION = 0;

    private SLDefaultPlayerGenerator(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        players = new ArrayList<>();
    }

    public static SLDefaultPlayerGenerator getInstance(int numberOfPlayers) {
        if (numberOfPlayers <= 0 || numberOfPlayers > 4) {
            throw new RuntimeException("Invalid count of players. Player count should be within range of 1 to 4");
        }
        instance = new SLDefaultPlayerGenerator(numberOfPlayers);
        instance.init();
        return instance;
    }

    private void init() {
        Stack<Token.TokenColour> tokenColours = getRandomTokenColours();
        for (int i = 1; i <= numberOfPlayers; i++) {
            String name = "Player " + i;
            Token token = new Token(i, tokenColours.pop(), INITIAL_PLAYER_POSITION);
            players.add(new SLPlayer(name, token));
        }
    }

    private Stack<Token.TokenColour> getRandomTokenColours() {
        List<Token.TokenColour> tokenColours = Arrays.asList(Token.TokenColour.RED, Token.TokenColour.GREEN, Token.TokenColour.BLUE, Token.TokenColour.YELLOW);
        Stack<Token.TokenColour> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            stack.push(tokenColours.get(list.get(i) - 1));
        }
        return stack;
    }

    @Override
    public List<? extends Player> getPlayersQueue() {
        return this.players;
    }
}
