package edu.csueastbay.cs401.psander.engine.scenes;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base scene class. All concrete
 * scene implementations should derive from this
 * one.
 */
public abstract class Scene {

    private final List<GameObject> _gameObjects = new ArrayList<>();

    /**
     * Initializes the scene.
     */
    public abstract void init();

    /**
     * Performs the update step, triggering any
     * processing that needs to be done once a frame.
     * @param delta The elapsed time since the previous
     *              frame, in seconds.
     */
    public void update(double delta) {
    }

    /**
     * Add a game object to this scene.
     * @param go The game object to add.
     */
    public void addGameObject(GameObject go) {
        _gameObjects.add(go);
    }

    /**
     * Remove a game object from this scene.
     * @param go The game object to remove.
     */
    public void removeGameObject(GameObject go) {
        _gameObjects.remove(go);
    }
}
