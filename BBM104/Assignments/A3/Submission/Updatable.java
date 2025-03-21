/**
 * The Updatable interface represents objects that can be updated over time.
 * Classes implementing this interface should provide an implementation for the update method.
 */
public interface Updatable {

    /**
     * Updates the state of the object based on elapsed time since the last update.
     *
     * @param deltaTime The time elapsed in seconds since the last update
     */
    void update(float deltaTime);
}
