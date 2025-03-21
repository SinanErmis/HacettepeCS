/**
 * Represents soil game object that extends GameObject.
 * Each Soil instance is initialized with a random image path.
 */
public class Soil extends GameObject {

    /**
     * Constructs a Soil object at the specified position with a random image.
     *
     * @param xPos   The x-coordinate position of the Soil
     * @param yPos   The y-coordinate position of the Soil
     * @param isTop  Indicates whether the Soil is at the top of the grid
     */
    public Soil(int xPos, int yPos, boolean isTop) {
        super(getRandomImagePath(isTop), xPos, yPos);
    }

    /**
     * Generates a random image path for the Soil based on its position.
     *
     * @param isTop Indicates whether the Soil is at the top of the grid
     * @return A randomly selected image path for the Soil
     */
    private static String getRandomImagePath(boolean isTop) {
        if (isTop) {
            int random = ((int) (Math.random() * 2)) + 1;
            return "assets/underground/top_0" + random + ".png";
        } else {
            int random = ((int) (Math.random() * 5)) + 1;
            return "assets/underground/soil_0" + random + ".png";
        }
    }
}