package com.board.games.domain.game;

import java.util.Random;

public class Dice {
    private Random random = new Random();
    int max = 6;
    int min = 1;

    public Integer rollDice() {
        return random.nextInt(max - min) + min;
    }
}
