package com.board.games.domain.token;

import com.board.games.JacocoExcludeGenerated;

import java.util.Objects;

/**
 * <p>
 * Token indicates a piece on the board game.
 * </p>
 */
public class Token {

    private Integer tokenNumber;
    private TokenColour tokenColour;
    private Integer position = 0;

    public Token(Integer tokenNumber, TokenColour tokenColour, Integer position) {
        this.tokenNumber = tokenNumber;
        this.tokenColour = tokenColour;
        this.position = position;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getter - Setters
    ///////////////////////////////////////////////////////////////////////////

    @JacocoExcludeGenerated
    public Integer getTokenNumber() {
        return tokenNumber;
    }

    @JacocoExcludeGenerated
    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    @JacocoExcludeGenerated
    public TokenColour getTokenColour() {
        return tokenColour;
    }

    @JacocoExcludeGenerated
    public void setTokenColour(TokenColour tokenColour) {
        this.tokenColour = tokenColour;
    }

    @JacocoExcludeGenerated
    public Integer getPosition() {
        return position;
    }

    @JacocoExcludeGenerated
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    @JacocoExcludeGenerated
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Token token = (Token) o;
        return tokenNumber.equals(token.tokenNumber) && tokenColour.equals(token.tokenColour);
    }

    @Override
    @JacocoExcludeGenerated
    public int hashCode() {
        return Objects.hash(tokenNumber, tokenColour);
    }

    @Override
    @JacocoExcludeGenerated
    public String toString() {
        return "Token{" + "tokenNumber=" + tokenNumber + ", tokenColour='" + tokenColour + '\'' + ", position=" +
                position + '}';
    }

    public enum TokenColour {
        RED, BLUE, GREEN, YELLOW
    }
}
