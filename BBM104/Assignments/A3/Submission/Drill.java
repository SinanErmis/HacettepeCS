import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

/**
 * Represents the Drill object in the game, which is a type of GameObject and implements Updatable.
 * The Drill can move and interact with the game world.
 */
public class Drill extends GameObject implements Updatable {
    private static final float FUEL_DECREASE_RATE_PER_SECONDS = 1f;
    private static final int FUEL_DECREASE_PER_MOVE = 100;

    private int currentXIndex;
    private int currentYIndex; //From -2 to grid.length - 1. -2 is the top of the world, 0 is the top of the soil
    private final GameObject[][] grid;
    private float gravityTimer;
    private boolean isUpPressed;
    private boolean isDownPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;

    private double currentFuel = 10000;
    private int collectedWorth;
    private int collectedWeight;

    private final Text fuelText;
    private final Text haulText;
    private final Text moneyText;

    /**
     * Constructs a Drill object with initial position and game grid references.
     *
     * @param xPos      Initial x-coordinate position
     * @param yPos      Initial y-coordinate position
     * @param grid      The game grid
     * @param fuelText  Text element for displaying fuel status
     * @param haulText  Text element for displaying haul status
     * @param moneyText Text element for displaying money status
     */
    public Drill(double xPos, double yPos, GameObject[][] grid, Text fuelText, Text haulText, Text moneyText) {
        super("assets/drill/drill_01.png", xPos, yPos);

        currentXIndex = HULoadGame.CELL_COUNT_HORIZONTAL / 2;
        currentYIndex = -1;

        this.grid = grid;
        this.fuelText = fuelText;
        this.haulText = haulText;
        this.moneyText = moneyText;
    }

    /**
     * Updates the state of the Drill over time.
     *
     * @param deltaTime Time elapsed since the last update
     */
    public void update(float deltaTime) {
        decreaseFuel(deltaTime);
        handleMovement();
        applyGravity(deltaTime);
    }

    /**
     * Decreases the current fuel level of the Drill based on elapsed time.
     *
     * @param deltaTime Time elapsed since the last update
     */
    private void decreaseFuel(float deltaTime) {
        setCurrentFuel(currentFuel - deltaTime * FUEL_DECREASE_RATE_PER_SECONDS);
    }

    /**
     * Sets the current fuel level of the Drill and updates the corresponding UI text.
     *
     * @param currentFuel The new current fuel level
     */
    private void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
        fuelText.setText("Fuel: " + currentFuel);

        if (currentFuel <= 0) {
            System.out.println("Game Over by fuel");
            HULoadGame.triggerGoodEnding(collectedWorth);
            //update ui
        }
    }

    /**
     * Applies gravity to the Drill, causing it to move downward if conditions are met.
     *
     * @param deltaTime Time elapsed since the last frame
     */
    private void applyGravity(float deltaTime) {
        // Check if enough time has passed to apply gravity
        // If so, move the drill down one grid space if bottom is not blocked

        //System.out.println("Applying gravity : " + deltaTime);

        gravityTimer += deltaTime;
//
        if (gravityTimer >= 1.5f) {
            gravityTimer = 0;
            if (currentYIndex < -1) {
                moveToTarget(currentXIndex, currentYIndex + 1);
                return;
            }
            if (currentYIndex < HULoadGame.CELL_COUNT_VERTICAL - HULoadGame.AIR_HEIGHT - 1) {
                if (grid[currentXIndex][currentYIndex + 1].getClass().equals(Empty.class)) {
                    moveToTarget(currentXIndex, currentYIndex + 1);
                }
            }
        }


    }

    /**
     * Handles keyboard input for movement of the Drill.
     */
    private void handleMovement() {
        if (KeyPolling.getInstance().isDown(KeyCode.UP)) {
            if (!isUpPressed) {
                isUpPressed = true;
                //We cannot drill upwards unless we are on top of the world or top grid is empty
                // Since this rule only applies for moving upwards handle this case here
                if (currentYIndex <= 0 || grid[currentXIndex][currentYIndex - 1].getClass().equals(Empty.class)) {
                    moveToTarget(currentXIndex, currentYIndex - 1);
                }
            }
        } else {
            isUpPressed = false;
        }

        if (KeyPolling.getInstance().isDown(KeyCode.DOWN)) {
            if (!isDownPressed) {
                isDownPressed = true;
                moveToTarget(currentXIndex, currentYIndex + 1);
            }
        } else {
            isDownPressed = false;
        }

        if (KeyPolling.getInstance().isDown(KeyCode.LEFT)) {
            if (!isLeftPressed) {
                isLeftPressed = true;
                moveToTarget(currentXIndex - 1, currentYIndex);
                changeSprite(false);
            }
        } else {
            isLeftPressed = false;
        }

        if (KeyPolling.getInstance().isDown(KeyCode.RIGHT)) {
            if (!isRightPressed) {
                isRightPressed = true;
                moveToTarget(currentXIndex + 1, currentYIndex);
                changeSprite(true);
            }
        } else {
            isRightPressed = false;
        }
    }

    /**
     * Changes the sprite of the Drill based on its facing direction.
     *
     * @param isFacingRight True if the Drill is facing right, false otherwise
     */
    private void changeSprite(boolean isFacingRight) {
        double translateY = imageView.getTranslateY();
        double translateX = imageView.getTranslateX();
        imageView = new ImageView(isFacingRight ? "file:assets/drill/drill_60.png" : "file:assets/drill/drill_01.png");
        imageView.setTranslateX(translateX);
        imageView.setTranslateY(translateY);
        HULoadGame.updateDrawables();
    }

    /**
     * Moves the Drill to the specified target grid position.
     * If the target position is out of bounds or blocked, the Drill does not move.
     * Also, drill cannot dig upwards.
     *
     * @param targetIndexX The target x-index in the game grid
     * @param targetIndexY The target y-index in the game grid
     */
    private void moveToTarget(int targetIndexX, int targetIndexY) {
        gravityTimer = 0f;
        setCurrentFuel(currentFuel - FUEL_DECREASE_PER_MOVE);
        if (targetIndexX < 0 || targetIndexX >= HULoadGame.CELL_COUNT_HORIZONTAL || targetIndexY < -HULoadGame.AIR_HEIGHT || targetIndexY >= HULoadGame.CELL_COUNT_VERTICAL - HULoadGame.AIR_HEIGHT) {
            return;
        }

        if (targetIndexY < 0) {
            currentXIndex = targetIndexX;
            currentYIndex = targetIndexY;
            getImage().setTranslateX(currentXIndex * HULoadGame.CELL_DIMENSION);
            getImage().setTranslateY((currentYIndex + HULoadGame.AIR_HEIGHT) * HULoadGame.CELL_DIMENSION);
            return;
        }
        Class<? extends GameObject> type = grid[targetIndexX][targetIndexY].getClass();

        if (type.equals(Boulder.class)) {
            return;
        }

        if (type.equals(Lava.class)) {
            HULoadGame.triggerBadEnding();
            System.out.print("Game Over by lava");
            //update ui
        }

        if (type.equals(Valuable.class)) {
            Valuable valuable = (Valuable) grid[targetIndexX][targetIndexY];
            collectedWeight += valuable.getWeight();
            collectedWorth += valuable.getWorth();
            haulText.setText("Haul: " + collectedWeight);
            moneyText.setText("Money: " + collectedWorth);
        }

        //I can inject game class into drill...or I can simply call a static method and create a non-global singleton instance in game class
        HULoadGame.destroyFromGrid(targetIndexX, targetIndexY);

        currentXIndex = targetIndexX;
        currentYIndex = targetIndexY;
        getImage().setTranslateX(currentXIndex * HULoadGame.CELL_DIMENSION);
        getImage().setTranslateY((currentYIndex + HULoadGame.AIR_HEIGHT) * HULoadGame.CELL_DIMENSION);

    }
}
