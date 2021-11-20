package edu.csueastbay.cs401.psander;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Main game class. Responsible for starting the game loop
 * and initiating initial program state. Implemented
 * as a singleton to avoid reallocations in the class's
 * launcher program.
 */
public class PongWare {
    private final double _fieldWidth = 1280;
    private final double _fieldHeight = 720;

    private GraphicsContext _graphicsContext = null;
    private Timeline _timeline = null;

    // Timing
    private long _previousNano;

    // Systems
    private static PongWare _instance = null;

    private PongWare() {}

    public static PongWare getInstance() {
        if (_instance == null)
            _instance = new PongWare();

        return _instance;
    }

    public static double getFieldWidth() { return getInstance()._fieldWidth; }

    public static double getFieldHeight() { return getInstance()._fieldHeight; }

    /**
     * Reinitialize the PongWare object.
     *
     * Because this object is constructed as a singleton
     * (to cut down on excess/repeated memory allocations),
     * this method is required to ensure the game has a handle
     * to the updated graphics context.
     * @param gc The current graphics context from the game's Canvas element.
     */
    public void init(GraphicsContext gc)
    {
        _graphicsContext = gc;

        var game = this;

        _timeline = new Timeline(new KeyFrame(Duration.millis(16.6), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.tick();
            }
        }));

        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();

        _previousNano = System.nanoTime();
    }

    /**
     * Accepts incoming keyboard input to dispatch
     * it to the proper handler.
     * @param code The current key being pressed.
     */
    public void keyDown(KeyCode code) {
    }

    /**
     * Accepts incoming keyboard input to dispatch
     * it to the proper handler.
     * @param code The current key being pressed.
     */
    public void keyUp(KeyCode code) {
    }

    private void tick()
    {
        // Update the time delta.
        var currentNano = System.nanoTime();
        var elapsedNano = currentNano - _previousNano;
        var delta = elapsedNano / 1000.0 / 1000.0 / 1000.0; // Convert ns to sec

        _graphicsContext.setFill(Color.BLACK);
        _graphicsContext.fillRect(0, 0, _fieldWidth, _fieldHeight);

        _previousNano = currentNano;
    }

    public GraphicsContext getGraphicsContext() {
        return _graphicsContext;
    }
}
