import javafx.scene.image.ImageView;

/**
 * The base class representing a game object.
 * This class is abstract and provides functionality for managing an ImageView.
 */
public abstract class GameObject {
    protected ImageView imageView;

    /**
     * Constructs a GameObject with the specified image path.
     *
     * @param imagePath The path to the image file for this game object
     */
    public GameObject(String imagePath) {
        this.imageView = new ImageView("file:" + imagePath);
        imageView.setFitHeight(HULoadGame.CELL_DIMENSION);
        imageView.setFitWidth(HULoadGame.CELL_DIMENSION);
    }

    /**
     * Constructs a GameObject with the specified image path, x-position, and y-position.
     *
     * @param imagePath The path to the image file for this game object
     * @param xPos      The initial x-coordinate position of the game object
     * @param yPos      The initial y-coordinate position of the game object
     */
    public GameObject(String imagePath, double xPos, double yPos) {
        this.imageView = new ImageView("file:" + imagePath);
        imageView.setFitHeight(HULoadGame.CELL_DIMENSION);
        imageView.setFitWidth(HULoadGame.CELL_DIMENSION);
        imageView.setTranslateX(xPos);
        imageView.setTranslateY(yPos);
    }

    /**
     * Retrieves the ImageView associated with this game object.
     *
     * @return The ImageView of this game object
     */
    public ImageView getImage() {
        return this.imageView;
    }
}
