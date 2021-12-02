package edu.csueastbay.cs401.lbernard;

import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class BonusablePuck extends Circle implements BonusPuckable {

    public static final double STARTING_SPEED = 5.0;
    public static final int STARTING_RADIOUS = 5;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;

    public BonusablePuck(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
    }

    public void multiplyRadius(double n) {
        setRadius(getRadius() * n);
    }

    @Override
    public void reset() {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadius(STARTING_RADIOUS);
        setFill(Color.WHITE);

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }

    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setDirection(double angle) {
        this.direction = angle;
    }

    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
    }





}
