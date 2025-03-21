import javafx.animation.AnimationTimer;
import java.util.List;

/**
 * Manages the game loop by updating a list of Updatable objects on each frame.
 * Extends AnimationTimer to handle game loop functionality.
 */
public class GameLoopHandler extends AnimationTimer {

    private List<Updatable> updatables;
    private long lastFrameTime;

    /**
     * Constructs a GameLoopHandler with a list of Updatable objects.
     *
     * @param updatables The list of Updatable objects to be updated in the game loop
     */
    public GameLoopHandler(List<Updatable> updatables) {
        this.updatables = updatables;
        lastFrameTime = System.nanoTime();
    }

    /**
     * Called on each frame of the game loop.
     * Updates all registered Updatable objects based on elapsed time since the last frame.
     *
     * @param now The current timestamp in nanoseconds
     */
    @Override
    public void handle(long now) {
        // Calculate time elapsed since the last frame in seconds
        float deltaTimeSeconds = (now - lastFrameTime) / 1_000_000_000f;

        // Update each Updatable object with the elapsed time
        for (Updatable updatable : updatables) {
            updatable.update(deltaTimeSeconds);
        }

        // Record the current frame time for the next update
        lastFrameTime = now;
    }
}
