package com.board.games.domain.player;

import java.util.Objects;
import java.util.Stack;

/**
 * Token indicates a piece on the board game.
 */
public class Token {

    private Integer tokenNumber;
    private String tokenColour;
    private Integer position = 0;

    public Token(Integer tokenNumber, String tokenColour, Integer position) {
        this.tokenNumber = tokenNumber;
        this.tokenColour = tokenColour;
        this.position = position;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getTokenColour() {
        return tokenColour;
    }

    public void setTokenColour(String tokenColour) {
        this.tokenColour = tokenColour;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenNumber.equals(token.tokenNumber) && tokenColour.equals(token.tokenColour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenNumber, tokenColour);
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenNumber=" + tokenNumber +
                ", tokenColour='" + tokenColour + '\'' +
                ", position=" + position +
                '}';
    }
}
