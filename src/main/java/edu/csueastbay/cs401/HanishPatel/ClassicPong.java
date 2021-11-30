package edu.csueastbay.cs401.HanishPatel;

// Need to fix

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

import edu.csueastbay.cs401.pong.Game;

public class ClassicPong extends Game {

    private double fieldHeight;
    private double fieldWidth;

    private Paddle paddleOne;
    private Paddle paddleTwo;


    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    puck.reset();
                }
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);

        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    // *** Need to Add Following Functions *** ///

    // Need to change scenes

    // Need to change paddles

    // Change blocks

    // Change ball

    // Add powerups (?)

    // Change background

    // Change scenes

    // Modify some colors

    // Play AI


    // Attempting to change controls

/*
    @Override
    public void keyPressed(KeyCode code)
    {
        switch (code)
        {
            case W:
                paddleOne.moveUp();
                break;
            case D:
                paddleOne.moveDown();
                break;
            case UP:
                paddleTwo.moveUp();
                break;
            case DOWN:
                paddleTwo.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyCode code)
    {
        switch (code)
        {
            case W,D:
                paddleOne.stop();
                break;
            case UP,DOWN:
                paddleTwo.stop();
                break;
        }
    }

 */
}


