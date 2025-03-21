/**
 * Represents a player in the game.
 */
public class Player {

    private final String name; // The name of the player.
    private int points; // The points scored by the player.

    /**
     * Constructor for initializing a Player object.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the points scored by the player.
     *
     * @return The points scored by the player.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points to the player's score.
     *
     * @param amount The amount of points to add.
     */
    public void addPoints(int amount) {
        points += amount;
    }

}
