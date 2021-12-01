package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

//create class for speedbost obj
public class SpeedBoost extends Circle implements Collidable {
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;

    public SpeedBoost(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "SpeedBuff",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getType() {
        return "SpeedBoost";
    }
}
