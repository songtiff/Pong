package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;

import java.util.Random;

public class PuckFactory {
    private static double fieldWidth;
    private static double fieldHeight;

    public PuckFactory(double fieldWidth, double fieldHeight) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
    }

    public static Puckable createPuck() {
        Random random = new Random();
        Puckable puck = null;
        switch (random.nextInt(1)) {
            case 0:
                puck = new Puck(fieldWidth, fieldHeight);
                break;
        }

        return puck;
    }
}
