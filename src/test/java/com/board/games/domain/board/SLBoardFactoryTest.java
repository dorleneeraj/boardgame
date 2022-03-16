package com.board.games.domain.board;

import com.board.games.exception.GameException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class SLBoardFactoryTest {

    @Test
    public void test_validateLadderAndSnakeTuples() throws Exception {
        List<SLTuple> snakeTuples = Arrays.asList(new SLTuple(56, 45));
        List<SLTuple> ladderTuples = Arrays.asList(new SLTuple(56, 80));

       Throwable th = assertThrows(GameException.class, () -> SLBoardFactory.validateLadderAndSnakeTuples(snakeTuples, ladderTuples, new Dimension(10,10)));
       
       assertTrue(th.getMessage().contains("Ladder and Snake tuples cannot overlap with each other"));
    }
}