package com.board.games.exception;

/**
 * <p>Simple static factory to generate {@link GameException} with different
 * prefix messages</p>
 */
public class ExceptionUtil {

    public static GameException getInvalidBoardConfigurationException(String message) {
        return buildException("Invalid Board Configuration Exception", message);
    }

    public static GameException getInvalidCellConfigurationException(String message) {
        return buildException("Invalid Cell Configuration Exception", message);
    }

    public static GameException getInvalidCellOperationException(String message) {
        return buildException("Invalid Cell Operation Performed Exception", message);
    }

    public static GameException getInvalidGameConfigurationException(String message) {
        return buildException("Invalid Game Configuration Exception", message);
    }

    public static GameException getGamePlayerConfigurationException(String message) {
        return buildException("Invalid Player Configuration Exception", message);
    }

    public static GameException getInvalidMoveOperationException(String message) {
        return buildException("Performed Invalid Move Exception :: ", message);
    }

    protected static GameException buildException(String prefix, String message) {
        return new GameException(prefix + " :: " + message);
    }
}
