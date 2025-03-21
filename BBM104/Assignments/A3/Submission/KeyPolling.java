import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Utility class for polling keyboard input using JavaFX Scene.
 * Instead of using JavaFX's built-in event handling, this class provides a way to poll for key presses, which approach is more suitable for games.
 * This class manages which keys are currently being pressed.
 */
public class KeyPolling {
    private static Scene scene;
    private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();
    private static KeyPolling instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private KeyPolling() {
        // Private constructor to prevent external instantiation
    }

    /**
     * Returns the singleton instance of KeyPolling.
     * For more info about singleton pattern, see: <a href="https://gameprogrammingpatterns.com/singleton.html">...</a>
     * @return The KeyPolling instance
     */
    public static KeyPolling getInstance() {
        if (instance == null) {
            instance = new KeyPolling();
        }
        return instance;
    }

    /**
     * Polls the provided JavaFX Scene for keyboard input.
     * Clears previously tracked keys and sets up new key event handlers for the scene.
     *
     * @param scene The JavaFX Scene to poll for keyboard input
     */
    public void pollScene(Scene scene) {
        clearKeys();
        removeCurrentKeyHandlers();
        setScene(scene);
    }

    private void clearKeys() {
        keysCurrentlyDown.clear();
    }

    private void removeCurrentKeyHandlers() {
        if (scene != null) {
            KeyPolling.scene.setOnKeyPressed(null);
            KeyPolling.scene.setOnKeyReleased(null);
        }
    }

    private void setScene(Scene scene) {
        KeyPolling.scene = scene;
        KeyPolling.scene.setOnKeyPressed((keyEvent -> {
            keysCurrentlyDown.add(keyEvent.getCode());
        }));
        KeyPolling.scene.setOnKeyReleased((keyEvent -> {
            keysCurrentlyDown.remove(keyEvent.getCode());
        }));
    }

    /**
     * Checks if the specified KeyCode is currently pressed.
     *
     * @param keyCode The KeyCode to check
     * @return true if the KeyCode is currently pressed, otherwise false
     */
    public boolean isDown(KeyCode keyCode) {
        return keysCurrentlyDown.contains(keyCode);
    }

    /**
     * Returns a string representation of the KeyPolling object, including the scene and keys currently pressed.
     *
     * @return A string representation of the KeyPolling object
     */
    @Override
    public String toString() {
        StringBuilder keysDown = new StringBuilder("KeyPolling on scene (").append(scene).append(")");
        for (KeyCode code : keysCurrentlyDown) {
            keysDown.append(code.getName()).append(" ");
        }
        return keysDown.toString();
    }
}
