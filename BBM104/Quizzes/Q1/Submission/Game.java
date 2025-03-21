import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game with multiple players and turns.
 */
public class Game {

    /**
     * Constructor for initializing a Game object.
     *
     * @param fileName  The name of the output file.
     * @param gameLines An array containing lines of the game data.
     */
    public Game(String fileName, String[] gameLines) {
        // Initialize outputFileName with the provided fileName.
        outputFileName = fileName;

        // Extract player count from the first line of gameLines.
        int playerCount = Integer.parseInt(gameLines[0].trim());
        players = new Player[playerCount];
        activePlayers = new ArrayList<>();
        String[] playerNames = gameLines[1].split(",");

        // Create Player objects and add them to players and activePlayers lists.
        for (int i = 0; i < playerCount; i++) {
            String name = playerNames[i].trim();
            players[i] = new Player(name);
            activePlayers.add(players[i]);
        }

        // Parse turns from gameLines and store them in turns array.
        List<Turn> turns = new ArrayList<>();
        for (int j = 2; j < gameLines.length; j++) {
            turns.add(new Turn(gameLines[j]));
        }
        this.turns = new Turn[turns.size()];
        this.turns = turns.toArray(this.turns);
    }

    private final String outputFileName;
    private Player[] players;
    private Turn[] turns;

    private List<Player> activePlayers;
    private int activePlayerIndex;

    /**
     * Begins playing the game until it is over.
     */
    public void play() {
        int turnIndex = 0;
        // Play turns until the game is over.
        while (!isOver(turnIndex)) {
            playTurn(turnIndex);
            turnIndex++;
        }
        // Declare the winner and write the result to the output file.
        Player lastStanding = activePlayers.get(0);
        FileUtilities.writeToFile(outputFileName, String.format("%s is the winner of the game with the score of %d. Congratulations %s!",
                lastStanding.getName(), lastStanding.getPoints(), lastStanding.getName()), true, false);
    }

    /**
     * Checks if the game is over based on the turn index.
     *
     * @param turnIndex The index of the current turn.
     * @return True if the game is over, false otherwise.
     */
    private Boolean isOver(int turnIndex) {
        return turnIndex >= turns.length;
    }

    /**
     * Executes a turn of the game.
     *
     * @param turnIndex The index of the current turn.
     */
    private void playTurn(int turnIndex) {
        Turn turn = turns[turnIndex];
        Player player = activePlayers.get(activePlayerIndex);
        String playerName = player.getName();
        String message;

        // TODO: Ugly implementation. Get rid of switch and make it with functional programming.
        // (with some sort of pre-initialized TurnResult to Action map)
        switch (turn.getResult()) {
            case ScoreGain:
                // Increase player's points and update message.
                player.addPoints(turn.getPoints());
                message = playerName + " threw " + turn.toString() + " and " + playerName + "’s score is " + player.getPoints() + ".";
                activePlayerIndex++;
                break;
            case NoScoreGain:
                // Update message without changing points.
                message = playerName + " threw " + turn.toString() + " and " + playerName + "’s score is " + player.getPoints() + ".";
                activePlayerIndex++;
                break;
            case Skipped:
                // Update message for skipped turn.
                message = playerName + " skipped the turn and " + playerName + "’s score is " + player.getPoints() + ".";
                activePlayerIndex++;
                break;
            case GameOver:
                // Update message for game over, remove player from active players.
                message = playerName + " threw 1-1. Game over " + playerName + "!";
                activePlayers.remove(activePlayerIndex);
                break;
            default:
                message = "Something weird happened and I probably got 0 from quiz.";
        }

        // Write the turn result message to the output file.
        FileUtilities.writeToFile(outputFileName, message, true, true);

        // ! I don't actually know which one is better below
        // Since top one is branching code I'll go with bottom one
        //if(activePlayers.size() == activePlayerIndex){
        //    activePlayerIndex = 0;
        //}
        // or:
        activePlayerIndex = activePlayerIndex % activePlayers.size();
    }

}