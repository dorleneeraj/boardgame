package com.board.games.domain.cell;

import com.board.games.domain.player.Move;
import com.board.games.domain.player.Token;

import java.util.List;

public interface Cell {

    Move acceptToken(Token token);

    Boolean removeToken(Token token);

    Integer getCellPosition();

    List<Token> getCurrentTokensOnCell();
}
