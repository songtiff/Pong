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


    //speed boost variables
    public double puck_speed_boost;

    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight, AnchorPane field) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = field;
        this.puckFactory = new PuckFactory(fieldWidth, fieldHeight);

        Puckable puck = puckFactory.createPuck();
        puck.setID("Classic");
        addPuck(puck);

        SpeedBoost zoom1 = new SpeedBoost(this.fieldWidth, this.fieldHeight);
        zoom1.setId("Boost Top Left");
        zoom1.setCenterX(200);
        zoom1.setCenterY(300);
        zoom1.setRadius(10.0);
        zoom1.setFill(Color.SKYBLUE);
        addObject(zoom1);

        SpeedBoost zoom2 = new SpeedBoost(this.fieldWidth, this.fieldHeight);
        zoom2.setId("Boost Top Right");
        zoom2.setCenterX(1000);
        zoom2.setCenterY(300);
        zoom2.setRadius(10.0);
        zoom2.setFill(Color.DEEPSKYBLUE);
        addObject(zoom2);

        SpeedDebuff slow1 = new SpeedDebuff(this.fieldWidth, this.fieldHeight);
        slow1.setId("Debuff Bottom Right");
        slow1.setCenterX(1000);
        slow1.setCenterY(600);
        slow1.setRadius(10.0);
        slow1.setFill(Color.LIGHTPINK);
        addObject(slow1);

        SpeedDebuff slow2 = new SpeedDebuff(this.fieldWidth, this.fieldHeight);
        slow2.setId("Debuff Bottom Left");
        slow2.setCenterX(200);
        slow2.setCenterY(600);
        slow2.setRadius(10.0);
        slow2.setFill(Color.LIGHTCORAL);
        addObject(slow2);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.ORANGE);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.ORANGE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.VIOLET);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUEVIOLET);
        addPlayerPaddle(2, playerTwo);
    }


    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch (collision.getType()) {
            case "SpeedBuff":
                puck.setSpeed(10.0);
            case "SpeedDebuff":
                puck.setSpeed(3.5);
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
                    puck_speed_boost = puck.getSpeed();
                    puck_speed_boost += 2.5;
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    puck_speed_boost = puck.getSpeed();
                    puck_speed_boost += 2.5;
                }
                puck.setDirection(angle);
                break;
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
