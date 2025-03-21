import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the main application class for the HU Load Game.
 * This class extends JavaFX's Application and manages the game initialization, rendering, and update loop.
 */
public class HULoadGame extends Application {

    public static final int CELL_DIMENSION = 70;
    public static final int CELL_COUNT_HORIZONTAL = 16;
    public static final int CELL_COUNT_VERTICAL = 15;
    public static final int AIR_HEIGHT = 2;
    public static final int WINDOW_WIDTH = CELL_DIMENSION * CELL_COUNT_HORIZONTAL;
    public static final int WINDOW_HEIGHT = CELL_DIMENSION * CELL_COUNT_VERTICAL;

    private final List<GameObject> gameObjects = new ArrayList<>();
    private final GameObject[][] grid = new GameObject[CELL_COUNT_HORIZONTAL][CELL_COUNT_VERTICAL - AIR_HEIGHT];
    private final List<Updatable> updatables = new ArrayList<>();
    private List<Node> nodesToDraw;
    private Stage stage;

    private final Text fuelText = new Text(10, 20, "Fuel: 10000");
    private final Text haulText = new Text(10, 40, "Haul: 0");
    private final Text moneyText = new Text(10, 60, "Money: 0");

    // Private singleton instance. Singleton applied here is to ensure that only one instance of the game is running, not global availability.
    // For more about singleton design pattern, see: https://gameprogrammingpatterns.com/singleton.html
    private static HULoadGame instance;

    private int collectedMoney = -1;
    private boolean isGameOver = false;


    /**
     * Initializes the game state before starting the game loop.
     * Creates game objects, sets up UI elements, and generates the underground grid.
     */
    @Override
    public void init() {
        instance = this;
        generateUnderground();
        fuelText.setFill(Color.WHITE);
        fuelText.setFont(Font.font(20));
        haulText.setFill(Color.WHITE);
        haulText.setFont(Font.font(20));
        moneyText.setFill(Color.WHITE);
        moneyText.setFont(Font.font(20));

        Drill drill = new Drill((CELL_COUNT_HORIZONTAL / 2) * CELL_DIMENSION, CELL_DIMENSION, grid, fuelText, haulText, moneyText);
        gameObjects.add(drill);
        updatables.add(drill);
    }

    /**
     * Entry point of the JavaFX application.
     * Initializes the game objects, sets up the UI components, and starts the game loop.
     *
     * @param stage The primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        updateDrawables();
        stage.setTitle("HU Load Game");
        stage.setResizable(false);
        stage.show();

        GameLoopHandler gameLoopHandler = new GameLoopHandler(updatables);
        gameLoopHandler.start();
    }

    /**
     * Generates the underground grid with various game elements (Soil, Boulder, Lava, Valuables).
     */
    private void generateUnderground() {
        Random random = new Random();

        // Place boulders at the edges of the play area
        for (int i = 0; i < CELL_COUNT_HORIZONTAL; i++) {
            grid[i][CELL_COUNT_VERTICAL - AIR_HEIGHT - 1] = new Boulder(i * CELL_DIMENSION, (CELL_COUNT_VERTICAL - 1) * CELL_DIMENSION); // Bottom edge
        }
        for (int j = 1; j < CELL_COUNT_VERTICAL - AIR_HEIGHT; j++) {
            grid[0][j] = new Boulder(0, (j + AIR_HEIGHT) * CELL_DIMENSION); // Left edge
            grid[CELL_COUNT_HORIZONTAL - 1][j] = new Boulder((CELL_COUNT_HORIZONTAL - 1) * CELL_DIMENSION, (j + AIR_HEIGHT) * CELL_DIMENSION); // Right edge
        }

        // Place top soil layer
        for (int i = 0; i < CELL_COUNT_HORIZONTAL; i++) {
            int xPos = i * CELL_DIMENSION;
            grid[i][0] = new Soil(xPos, AIR_HEIGHT * CELL_DIMENSION, true); // Top edge
        }

        // Generate random underground elements except for the edges
        for (int i = 1; i < CELL_COUNT_HORIZONTAL - 1; i++) {
            for (int j = 1; j < CELL_COUNT_VERTICAL - AIR_HEIGHT - 1; j++) {
                GameObject element;

                int xPos = i * CELL_DIMENSION;
                int yPos = (j + AIR_HEIGHT) * CELL_DIMENSION;

                // Determine element type based on random probability
                int rand = random.nextInt(100);

                // Underground layers
                if (rand < 75) {
                    element = new Soil(xPos, yPos, false);
                } else if (rand < 85) {
                    element = new Lava(xPos, yPos);
                } else if (rand < 95) {
                    element = generateRandomValuable(xPos, yPos);
                } else {
                    element = new Boulder(xPos, yPos);
                }

                grid[i][j] = element;
            }
        }

        // Add all grid items to game objects list as well
        for (int i = 0; i < CELL_COUNT_HORIZONTAL; i++) {
            for (int j = 0; j < CELL_COUNT_VERTICAL - AIR_HEIGHT; j++) {
                if (grid[i][j] != null) {
                    gameObjects.add(grid[i][j]);
                }
            }
        }


    }

    /**
     * Generates a random valuable game object at the specified position.
     *
     * @param xPos The x-coordinate position of the valuable
     * @param yPos The y-coordinate position of the valuable
     * @return A randomly generated Valuable game object
     */
    private GameObject generateRandomValuable(int xPos, int yPos) {
        Random random = new Random();
        int index = random.nextInt(ValuableType.values().length);
        ValuableType valuableType = ValuableType.values()[index];
        return new Valuable(valuableType, xPos, yPos);
    }


    /**
     * Replaces the specified grid cell with an Empty game object.
     * Removes the existing game object from the game state and adds an Empty game object in its place.
     *
     * @param x X index of the grid cell to destroy
     * @param y Y index of the grid cell to destroy
     */
    public static void destroyFromGrid(int x, int y) {
        Empty empty = new Empty(x * CELL_DIMENSION, y * (CELL_DIMENSION + AIR_HEIGHT));
        instance.gameObjects.remove(instance.grid[x][y]);
        instance.gameObjects.add(empty);

        instance.grid[x][y] = empty;
        updateDrawables();
    }

    public static void triggerGoodEnding(int money) {
        instance.collectedMoney = money;
        instance.isGameOver = true;
        updateDrawables();
    }

    public static void triggerBadEnding() {
        instance.isGameOver = true;
        updateDrawables();
    }

    /**
     * Updates the drawable nodes based on the game state.
     * Renders game objects, UI text elements, and handles game over conditions.
     */
    public static void updateDrawables() {
        if (instance.isGameOver) {
            Rectangle gameOverBackground = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            gameOverBackground.setFill(instance.collectedMoney == -1 ? Color.RED : Color.GREEN);

            Text gameOverText = new Text(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT / 2.0, "GAME OVER\n" + (instance.collectedMoney == -1 ? "" : instance.collectedMoney));
            gameOverText.setTextOrigin(VPos.CENTER);
            gameOverText.setTranslateX(gameOverText.getTranslateX() - 100);
            gameOverText.setTextAlignment(TextAlignment.CENTER);
            gameOverText.setFill(Color.WHITE);
            gameOverText.setFont(Font.font(40));

            instance.nodesToDraw.add(gameOverBackground);
            instance.nodesToDraw.add(gameOverText);

        } else {
            instance.nodesToDraw = instance.gameObjects.stream().map(GameObject::getImage).collect(Collectors.toList());
            instance.nodesToDraw.add(instance.fuelText);
            instance.nodesToDraw.add(instance.haulText);
            instance.nodesToDraw.add(instance.moneyText);
        }

        Group root = new Group(instance.nodesToDraw);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.DARKBLUE);
        instance.stage.setScene(scene);
        KeyPolling.getInstance().pollScene(scene);
    }

}
