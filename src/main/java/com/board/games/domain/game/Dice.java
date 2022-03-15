package com.board.games.domain.game;

import com.board.games.JacocoExcludeGenerated;

import java.util.Random;

@JacocoExcludeGenerated
public class Dice {
    private final Random random = new Random();
    int max = 6;
    int min = 1;

    public Integer rollDice() {
        return random.nextInt((max - min) + 1) + min;
    }
}
