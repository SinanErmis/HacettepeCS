/**
 * Represents a valuable game object in the underground grid.
 * Extends the GameObject class and encapsulates properties like worth and weight.
 */
public class Valuable extends GameObject {

    private int worth;
    private int weight;

    /**
     * Constructs a Valuable object of a specific type at the given position.
     *
     * @param valuableType The type of valuable (from ValuableType enum)
     * @param xPos         The x-coordinate position of the valuable
     * @param yPos         The y-coordinate position of the valuable
     */
    public Valuable(ValuableType valuableType, double xPos, double yPos) {
        super(valuableType.getImagePath(), xPos, yPos);
        this.worth = valuableType.getWorth();
        this.weight = valuableType.getWeight();
    }

    /**
     * Gets the worth of this valuable object.
     *
     * @return The worth of the valuable
     */
    public int getWorth() {
        return this.worth;
    }

    /**
     * Gets the weight of this valuable object.
     *
     * @return The weight of the valuable
     */
    public int getWeight() {
        return this.weight;
    }
}
