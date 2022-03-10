package com.board.games.domain.game;

import com.board.games.domain.board.BoardFactory;
import com.board.games.domain.player.SLDefaultPlayerGenerator;

public class GamesFactory {

    public static SnakeAndLadder getDefaultSLGame() {
        return new SnakeAndLadder.SnakeAndLadderBuilder(2)
                .withBoardGenerator(BoardFactory.getDefaultSLBoardGenerator())
                .withDice(new Dice())
                .withPlayerGenerator(SLDefaultPlayerGenerator.getInstance(2))
                .build();
    }
}
