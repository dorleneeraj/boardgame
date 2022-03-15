package com.board.games.domain.board;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLTuple slTuple = (SLTuple) o;
        // To enforce no overlapping of snake and ladder cells.
        return start == slTuple.start || start == slTuple.end || end == slTuple.end || end == slTuple.start;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
