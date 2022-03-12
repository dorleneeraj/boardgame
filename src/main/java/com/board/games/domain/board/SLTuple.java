package com.board.games.domain.board;

/**
 *
 */
public class SLTuple {

    private final int start;
    private final int end;

    public SLTuple(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
