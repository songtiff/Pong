package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ClassicPong extends Game {


    private double fieldHeight;
    private double fieldWidth;
    private PuckFactory puckFactory;
    private AnchorPane field;

    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight, AnchorPane field) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = field;
        this.puckFactory = new PuckFactory(fieldWidth, fieldHeight);

        Puckable puck = puckFactory.createPuck();
        puck.setID("Classic");
        addPuck(puck);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
    }


    @Override
    public void collisionHandler(Puckable puck, Collision collision) {

        switch (collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    newPuck();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    newPuck();
                }
                break;
            case "Paddle":
                double puckCenter = puck.getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setDirection(angle);

        }
    }

    public void newPuck() {
        ArrayList<Puckable> pucks = getPucks();
        pucks.forEach((old_puck) -> {
            field.getChildren().remove((Node) old_puck);
        });
        clearPucks();
        Puckable puck = puckFactory.createPuck();
        puck.setID("Random");
        addPuck(puck);
        field.getChildren().add((Node)puck);
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }
}
