package com.board.games;

import com.board.games.domain.game.Dice;
import com.board.games.domain.game.Game;
import com.board.games.domain.player.Player;
import com.board.games.domain.player.SLPlayer;
import com.board.games.domain.player.Token;
import com.board.games.factory.BoardFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Driver code. Simulates an execution of the Game
 */
public class GameSimulator {
    
    private static final Logger LOGGER = LogManager.getLogger(GameSimulator.class);
    
    public static void main(String[] args) {
        
    }

    private static List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new SLPlayer("Neeraj", new Token(1, "Blue", 0)));
        players.add(new SLPlayer("Ketki", new Token(2, "Blue", 0)));
        return players;
    }
}
