package com.board.games.domain.player.sl;

import com.board.games.domain.player.Player;
import com.board.games.domain.player.PlayerGenerator;
import com.board.games.domain.token.Token;
import com.board.games.domain.token.TokenColour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 */
public class SLDefaultPlayerGenerator implements PlayerGenerator {

    private static SLDefaultPlayerGenerator instance;
    private Queue<SLPlayer> players;
    private int numberOfPlayers;
    private final int INITIAL_PLAYER_POSITION = 0;

    private SLDefaultPlayerGenerator(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        players = new LinkedList<>();
    }

    public static SLDefaultPlayerGenerator getInstance(int numberOfPlayers) {
        instance = new SLDefaultPlayerGenerator(numberOfPlayers);
        instance.init();
        return instance;
    }

    private void init() {
        Stack<TokenColour> tokenColours = getRandomTokenColours();
        for (int i = 1; i <= numberOfPlayers; i++) {
            String name = "Player " + i;
            Token token = new Token(i, tokenColours.pop(), INITIAL_PLAYER_POSITION);
            players.add(new SLPlayer.SLPlayerBuilder().withName(name).withToken(token).build());
        }
    }

    private Stack<TokenColour> getRandomTokenColours() {
        List<TokenColour> tokenColours = Arrays.asList(TokenColour.RED, TokenColour.GREEN, TokenColour.BLUE, TokenColour.YELLOW);
        Stack<TokenColour> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            stack.push(tokenColours.get(list.get(i)));
        }
        return stack;
    }

    @Override
    public Player getNextPlayer() {
        if (null == this.players || this.players.isEmpty()) {
            throw new RuntimeException("No more players found");
        }
        return this.players.poll();
    }
}
