package com.board.games.domain.player;

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
    public Integer rollDice(Dice dice) {
        return dice.rollDice();
    }

    public Integer getCurrentPosition() {
        return this.token.getPosition();
    }

    public int getTotalLadderMoves() {
        return totalLadderMoves;
    }

    public int getTotalTilesClimbed() {
        return totalTilesClimbed;
    }

    public int getTotalSnakeMoves() {
        return totalSnakeMoves;
    }

    public int getTotalTilesDescended() {
        return totalTilesDescended;
    }

    public int getTotalLuckyMoves() {
        return totalLuckyMoves;
    }

    public int getTotalUnluckyMoves() {
        return totalUnluckyMoves;
    }

    public int getTotalTurnsPlayed() {
        return totalTurnsPlayed;
    }

    public int getTotalDiceRollsWithSix() {
        return totalDiceRollsWithSix;
    }

    public int getMaxConsecutiveStreak() {
        return maxConsecutiveStreak;
    }

    public int getCurrentConsecutiveStreak() {
        return currentConsecutiveStreak;
    }

    public ConsecutiveTurns getConsecutiveTurnState() {
        return consecutiveTurnState;
    }

    public void setTotalLadderMoves(int totalLadderMoves) {
        this.totalLadderMoves = totalLadderMoves;
    }

    public void setTotalTilesClimbed(int totalTilesClimbed) {
        this.totalTilesClimbed = totalTilesClimbed;
    }

    public void setTotalSnakeMoves(int totalSnakeMoves) {
        this.totalSnakeMoves = totalSnakeMoves;
    }

    public void setTotalTilesDescended(int totalTilesDescended) {
        this.totalTilesDescended = totalTilesDescended;
    }

    public void setTotalLuckyMoves(int totalLuckyMoves) {
        this.totalLuckyMoves = totalLuckyMoves;
    }

    public void setTotalUnluckyMoves(int totalUnluckyMoves) {
        this.totalUnluckyMoves = totalUnluckyMoves;
    }

    public void setTotalTurnsPlayed(int totalTurnsPlayed) {
        this.totalTurnsPlayed = totalTurnsPlayed;
    }

    public void setTotalDiceRollsWithSix(int totalDiceRollsWithSix) {
        this.totalDiceRollsWithSix = totalDiceRollsWithSix;
    }
    
    
    public void setMaxConsecutiveStreak(int maxConsecutiveStreak) {
        this.maxConsecutiveStreak = maxConsecutiveStreak;
    }

    public void setCurrentConsecutiveStreak(int currentConsecutiveStreak) {
        this.currentConsecutiveStreak = currentConsecutiveStreak;
    }

    public void setConsecutiveTurnState(ConsecutiveTurns consecutiveTurnState) {
        this.consecutiveTurnState = consecutiveTurnState;
    }

    public int getLongestLadderClimb() {
        return longestLadderClimb;
    }

    public void setLongestLadderClimb(int longestLadderClimb) {
        this.longestLadderClimb = longestLadderClimb;
    }

    public int getSteepestSnakeDescend() {
        return steepestSnakeDescend;
    }

    public void setSteepestSnakeDescend(int steepestSnakeDescend) {
        this.steepestSnakeDescend = steepestSnakeDescend;
    }

    public int getMinimumSnakeDescend() {
        return minimumSnakeDescend;
    }

    public void setMinimumSnakeDescend(int minimumSnakeDescend) {
        this.minimumSnakeDescend = minimumSnakeDescend;
    }

    public int getLowestLadderClimb() {
        return lowestLadderClimb;
    }

    public void setLowestLadderClimb(int lowestLadderClimb) {
        this.lowestLadderClimb = lowestLadderClimb;
    }

    @Override
    public void quitGame(Game game) {

    }

    public Token getToken() {
        return token;
    }

    public enum ConsecutiveTurns {
        NOT_STARTED, STARTED, COMPLETED
    }
}
