import java.util.Locale;

/**
 * Represents various types of valuable minerals that can be found underground.
 * Each enum constant defines a mineral with a specific name, worth, and weight.
 */
public enum ValuableType {

    IRONIUM("Ironium", 30, 10),
    BRONZIUM("Bronzium", 60, 10),
    SILVERIUM("Silverium", 100, 10),
    GOLDIUM("Goldium", 250, 20),
    PLATINUM("Platinum", 750, 30),
    EINSTEINIUM("Einsteinium", 2000, 40),
    EMERALD("Emerald", 5000, 60),
    RUBY("Ruby", 20000, 80),
    DIAMOND("Diamond", 100000, 100),
    AMAZONITE("Amazonite", 500000, 120);

    private final String mineralName;
    private final int worth;
    private final int weight;

    /**
     * Constructor for creating a ValuableType with specific properties.
     *
     * @param mineralName The name of the mineral
     * @param worth       The worth of the mineral
     * @param weight      The weight of the mineral
     */
    ValuableType(String mineralName, int worth, int weight) {
        this.mineralName = mineralName;
        this.worth = worth;
        this.weight = weight;
    }

    /**
     * Gets the name of the mineral.
     *
     * @return The name of the mineral
     */
    public String getMineralName() {
        return mineralName;
    }

    /**
     * Gets the image path for the valuable mineral.
     *
     * @return The image path representing this valuable mineral
     */
    public String getImagePath() {
        //Prevent "Turkish i" problem
        return "assets/underground/valuable_" + mineralName.toLowerCase(Locale.ENGLISH) + ".png";
    }

    /**
     * Gets the worth of the mineral.
     *
     * @return The worth of the mineral
     */
    public int getWorth() {
        return worth;
    }

    /**
     * Gets the weight of the mineral.
     *
     * @return The weight of the mineral
     */
    public int getWeight() {
        return weight;
    }
}
