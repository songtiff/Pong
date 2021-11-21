package edu.csueastbay.cs401.psander.engine.scenes;

/**
 * Abstract base scene class. All concrete
 * scene implementations should derive from this
 * one.
 */
public abstract class Scene {
    /**
     * Performs the update step, triggering any
     * processing that needs to be done once a frame.
     * @param delta The elapsed time since the previous
     *              frame, in seconds.
     */
    public abstract void update(double delta);
}
