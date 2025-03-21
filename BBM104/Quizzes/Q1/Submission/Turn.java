/**
 * Represents a turn in the game, consisting of two dice rolls.
 */
public class Turn {

    private int firstDice; // The value of the first dice roll.
    private int secondDice; // The value of the second dice roll.
    private TurnResult result; // The result of the turn.

    /**
     * Constructor for initializing a Turn object with specified dice rolls.
     *
     * @param firstDice  The value of the first dice roll.
     * @param secondDice The value of the second dice roll.
     */
    public Turn(int firstDice, int secondDice) {
        this.firstDice = firstDice;
        this.secondDice = secondDice;

        // Determine the result of the turn based on the dice rolls.
        if (firstDice == 1 && secondDice == 1) {
            result = TurnResult.GameOver;
            return;
        }
        if (firstDice == 1 || secondDice == 1) {
            result = TurnResult.NoScoreGain;
            return;
        }
        if (firstDice == 0 /*&& secondDice == 0*/) {
            result = TurnResult.Skipped;
            return;
        }
        result = TurnResult.ScoreGain;
    }

    /**
     * Constructor for initializing a Turn object from a string representation of the turn.
     *
     * @param turnLine A string representing the turn in the format "firstDice-secondDice".
     */
    public Turn(String turnLine) {
        String[] diceStrings = turnLine.trim().split("-");

        firstDice = Integer.parseInt(diceStrings[0]);
        secondDice = Integer.parseInt(diceStrings[1]);

        // ! Very, very ugly implementation, can be made with a ruleset approach.
        if (firstDice == 1 && secondDice == 1) {
            result = TurnResult.GameOver;
            return;
        }
        if (firstDice == 1 || secondDice == 1) {
            result = TurnResult.NoScoreGain;
            return;
        }
        if (firstDice == 0 /*&& secondDice == 0*/) {
            result = TurnResult.Skipped;
            return;
        }
        result = TurnResult.ScoreGain;
    }

    /**
     * Retrieves the result of the turn.
     *
     * @return The result of the turn.
     */
    public TurnResult getResult() {
        return result;
    }

    /**
     * Retrieves the total points earned in the turn.
     * Note: This method should only be called if the result is TurnResult.ScoreGain.
     *
     * @return The total points earned in the turn.
     */
    public int getPoints() {
        // Assert result == TurnResult.ScoreGain
        return firstDice + secondDice;
    }

    /**
     * Provides a string representation of the turn.
     *
     * @return A string representing the turn in the format "firstDice-secondDice".
     */
    public String toString() {
        return String.format("%d-%d", firstDice, secondDice);
    }
}
