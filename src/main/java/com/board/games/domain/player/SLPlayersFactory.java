package com.board.games.domain.player;

import com.board.games.domain.token.Token;
import com.board.games.exception.ExceptionUtil;
import com.board.games.exception.GameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static com.board.games.domain.token.Token.TokenColour;

/**
 * <p>Simple static factory to generate a list of {@link SLPlayer} </p>
 */
public class SLPlayersFactory {

    public static final int INITIAL_PLAYER_POSITION = 0;

    public static List<SLPlayer> getSLPlayers(int playerCount) throws GameException {

        if (playerCount <= 0 || playerCount > 4) {
            throw ExceptionUtil.getGamePlayerConfigurationException(
                    "There " + "should at least be 1 player. Player " + "count above 4 is " +
                            "currently not supported");
        }

        List<SLPlayer> players = new ArrayList<>();
        Stack<TokenColour> tokenColours = getRandomTokenColours(playerCount);
        for (int i = 1; i <= playerCount; i++) {
            String name = "Player " + i;
            Token token = new Token(i, tokenColours.pop(), INITIAL_PLAYER_POSITION);
            players.add(new SLPlayer(name, token));
        }
        return players;
    }

    private static Stack<TokenColour> getRandomTokenColours(int playerCount) {
        List<TokenColour> tokenColours = Arrays.asList(TokenColour.RED, TokenColour.GREEN, TokenColour.BLUE,
                TokenColour.YELLOW);
        Stack<TokenColour> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            stack.push(tokenColours.get(list.get(i) - 1));
        }
        return stack;
    }
}
