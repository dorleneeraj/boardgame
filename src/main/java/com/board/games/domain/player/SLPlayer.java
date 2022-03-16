package com.board.games.domain.player;

import com.board.games.JacocoExcludeGenerated;
import com.board.games.domain.game.Dice;
import com.board.games.domain.game.Game;
import com.board.games.domain.game.SLGame;
import com.board.games.domain.move.Move;
import com.board.games.domain.move.SLMove;
import com.board.games.domain.move.SLMoveType;
import com.board.games.domain.token.Token;

/**
 * Player for {@link SLGame} game
 */
public class SLPlayer extends Player {

    private final Token token;

    ///////////////////////////////////////////////////////////////////////////
    // Instance Variables to capture the statistics of a player for a 
    // particular Snake Ladder Game
    ///////////////////////////////////////////////////////////////////////////
    private int totalLadderMoves = 0;
    private int totalTilesClimbed = 0;

    private int totalSnakeMoves = 0;
    private int totalTilesDescended = 0;

    private int totalLuckyMoves = 0;
    private int totalUnluckyMoves = 0;

    private int totalTurnsPlayed = 0;

    private int totalDiceRollsWithSix = 0;
    private int currentConsecutiveStreak = 0;
    private ConsecutiveTurns consecutiveTurnState = ConsecutiveTurns.NOT_STARTED;

    private int maxConsecutiveStreak = 0;
    private int longestLadderClimb = 0;
    private int steepestSnakeDescend = 0;

    private int lowestLadderClimb = 0;
    private int minimumSnakeDescend = 0;

    public SLPlayer(String name, Token token) {
        super(name);
        this.token = token;
    }

    @Override
    public void addMove(Move playerMove) {
        captureTurnStats((SLMove) playerMove);
        captureConsecutiveTurnDetails((SLMove) playerMove);
        super.addMove(playerMove);
    }

    /**
     * <p>
     * The {@link SLMove} encapsulates a lot of information regarding the turn of the player.
     * Gathers the statistics and updates the local counters for the same
     * </p>
     *
     * @param currentMove
     */
    protected void captureTurnStats(SLMove currentMove) {
        SLMoveType playerMoveType = currentMove.getMoveType();
        totalTurnsPlayed += 1;
        if (SLMoveType.LADDER_ADVANCE.equals(playerMoveType)) {
            totalLadderMoves += 1;
            totalLuckyMoves += 1;
            totalTilesClimbed += currentMove.getTotalTilesClimbed();
            if (currentMove.getTotalTilesClimbed() > this.longestLadderClimb) {
                this.longestLadderClimb = currentMove.getTotalTilesClimbed();
            }
            if (lowestLadderClimb == 0) {
                this.lowestLadderClimb = currentMove.getTotalTilesClimbed();
            } else if (currentMove.getTotalTilesClimbed() < this.lowestLadderClimb) {
                this.lowestLadderClimb = currentMove.getTotalTilesClimbed();
            }
        }
        if (SLMoveType.SNAKE_DESCEND.equals(playerMoveType)) {
            totalUnluckyMoves += 1;
            totalSnakeMoves += 1;
            totalTilesDescended += currentMove.getTotalTilesDescended();
            if (currentMove.getTotalTilesDescended() > this.steepestSnakeDescend) {
                this.steepestSnakeDescend = currentMove.getTotalTilesDescended();
            }
            if (minimumSnakeDescend == 0) {
                this.minimumSnakeDescend = currentMove.getTotalTilesDescended();
            } else if (currentMove.getTotalTilesDescended() < this.minimumSnakeDescend) {
                this.minimumSnakeDescend = currentMove.getTotalTilesDescended();
            }
        }
        if (currentMove.isMissedSnakeLuckily() || SLMoveType.ADVANCE_LUCKY_MOVE.equals(playerMoveType)) {
            totalLuckyMoves += 1;
        }
        if (SLMoveType.UNLUCKY_MOVE.equals(playerMoveType)) {
            totalUnluckyMoves += 1;
        }
    }

    /**
     * <p>
     * Updates the information related to the consecutive streak of turns for the current Player
     * </p>
     *
     * @param currentMove
     */
    protected void captureConsecutiveTurnDetails(SLMove currentMove) {
        int currentDiceRoll = currentMove.getDiceRoll();
        boolean isCurrentRollASix = false;

        if (currentMove.isRolledASix()) {
            isCurrentRollASix = true;
            totalDiceRollsWithSix += 1;
        }

        if (ConsecutiveTurns.STARTED.equals(consecutiveTurnState)) {
            currentConsecutiveStreak += currentDiceRoll;
            if (!isCurrentRollASix) {
                consecutiveTurnState = ConsecutiveTurns.COMPLETED;
                if (currentConsecutiveStreak > maxConsecutiveStreak) {
                    maxConsecutiveStreak = currentConsecutiveStreak;
                }
            }
        } else {
            if (isCurrentRollASix) {
                currentConsecutiveStreak = 6;
                consecutiveTurnState = ConsecutiveTurns.STARTED;
            }
        }
    }

    /**
     * Rolls the Dice and returns the Integer within the range 1 to 6
     *
     * @return
     */
    @JacocoExcludeGenerated
    public Integer rollDice(Dice dice) {
        return dice.rollDice();
    }

    @JacocoExcludeGenerated
    public Integer getCurrentPosition() {
        return this.token.getPosition();
    }

    @JacocoExcludeGenerated
    public int getTotalLadderMoves() {
        return totalLadderMoves;
    }

    @JacocoExcludeGenerated
    public int getTotalTilesClimbed() {
        return totalTilesClimbed;
    }

    @JacocoExcludeGenerated
    public int getTotalSnakeMoves() {
        return totalSnakeMoves;
    }

    @JacocoExcludeGenerated
    public int getTotalTilesDescended() {
        return totalTilesDescended;
    }

    @JacocoExcludeGenerated
    public int getTotalLuckyMoves() {
        return totalLuckyMoves;
    }

    @JacocoExcludeGenerated
    public int getTotalUnluckyMoves() {
        return totalUnluckyMoves;
    }

    @JacocoExcludeGenerated
    public int getTotalTurnsPlayed() {
        return totalTurnsPlayed;
    }

    @JacocoExcludeGenerated
    public int getTotalDiceRollsWithSix() {
        return totalDiceRollsWithSix;
    }

    @JacocoExcludeGenerated
    public int getMaxConsecutiveStreak() {
        return maxConsecutiveStreak;
    }

    @JacocoExcludeGenerated
    public int getCurrentConsecutiveStreak() {
        return currentConsecutiveStreak;
    }

    @JacocoExcludeGenerated
    public ConsecutiveTurns getConsecutiveTurnState() {
        return consecutiveTurnState;
    }

    @JacocoExcludeGenerated
    public void setTotalLadderMoves(int totalLadderMoves) {
        this.totalLadderMoves = totalLadderMoves;
    }

    @JacocoExcludeGenerated
    public void setTotalTilesClimbed(int totalTilesClimbed) {
        this.totalTilesClimbed = totalTilesClimbed;
    }

    @JacocoExcludeGenerated
    public void setTotalSnakeMoves(int totalSnakeMoves) {
        this.totalSnakeMoves = totalSnakeMoves;
    }

    @JacocoExcludeGenerated
    public void setTotalTilesDescended(int totalTilesDescended) {
        this.totalTilesDescended = totalTilesDescended;
    }

    @JacocoExcludeGenerated
    public void setTotalLuckyMoves(int totalLuckyMoves) {
        this.totalLuckyMoves = totalLuckyMoves;
    }

    @JacocoExcludeGenerated
    public void setTotalUnluckyMoves(int totalUnluckyMoves) {
        this.totalUnluckyMoves = totalUnluckyMoves;
    }

    @JacocoExcludeGenerated
    public void setTotalTurnsPlayed(int totalTurnsPlayed) {
        this.totalTurnsPlayed = totalTurnsPlayed;
    }

    @JacocoExcludeGenerated
    public void setTotalDiceRollsWithSix(int totalDiceRollsWithSix) {
        this.totalDiceRollsWithSix = totalDiceRollsWithSix;
    }

    @JacocoExcludeGenerated
    public void setMaxConsecutiveStreak(int maxConsecutiveStreak) {
        this.maxConsecutiveStreak = maxConsecutiveStreak;
    }

    @JacocoExcludeGenerated
    public void setCurrentConsecutiveStreak(int currentConsecutiveStreak) {
        this.currentConsecutiveStreak = currentConsecutiveStreak;
    }

    @JacocoExcludeGenerated
    public void setConsecutiveTurnState(ConsecutiveTurns consecutiveTurnState) {
        this.consecutiveTurnState = consecutiveTurnState;
    }

    @JacocoExcludeGenerated
    public int getLongestLadderClimb() {
        return longestLadderClimb;
    }

    @JacocoExcludeGenerated
    public void setLongestLadderClimb(int longestLadderClimb) {
        this.longestLadderClimb = longestLadderClimb;
    }

    @JacocoExcludeGenerated
    public int getSteepestSnakeDescend() {
        return steepestSnakeDescend;
    }

    @JacocoExcludeGenerated
    public void setSteepestSnakeDescend(int steepestSnakeDescend) {
        this.steepestSnakeDescend = steepestSnakeDescend;
    }

    @JacocoExcludeGenerated
    public int getMinimumSnakeDescend() {
        return minimumSnakeDescend;
    }

    @JacocoExcludeGenerated
    public void setMinimumSnakeDescend(int minimumSnakeDescend) {
        this.minimumSnakeDescend = minimumSnakeDescend;
    }

    @JacocoExcludeGenerated
    public int getLowestLadderClimb() {
        return lowestLadderClimb;
    }

    @JacocoExcludeGenerated
    public void setLowestLadderClimb(int lowestLadderClimb) {
        this.lowestLadderClimb = lowestLadderClimb;
    }

    @Override
    public void quitGame(Game game) {

    }

    @JacocoExcludeGenerated
    public Token getToken() {
        return token;
    }

    public enum ConsecutiveTurns {
        NOT_STARTED, STARTED, COMPLETED
    }
}
