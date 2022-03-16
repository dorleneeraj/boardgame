package com.board.games.domain.game;

import com.board.games.JacocoExcludeGenerated;

import java.util.Random;

/**
 * <p>Implementation for a Dice</p>
 */
@JacocoExcludeGenerated
public class Dice {
    private final Random random = new Random();
    int max = 6;
    int min = 1;

    /**
     * <p>Generates a random integer within the range of 1 to 6</p>
     *
     * @return
     */
    public Integer rollDice() {
        return random.nextInt((max - min) + 1) + min;
    }
}
