package edu.csueastbay.cs401.psinha;
import edu.csueastbay.cs401.pong.*;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class MyGame {
    private int playerOneScore;
    protected MyPaddle playOnePaddle;
    private int playerTwoScore;
    protected MyPaddle playTwoPaddle;
    private int victoryScore;
    protected ArrayList<Collidable> objects;
    protected ArrayList<Puckable> pucks;

    public MyGame(int victoryScore) {
        this.victoryScore = victoryScore;
        this.objects = new ArrayList<>();
        this.pucks = new ArrayList<>();
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        System.out.println("Running Pyush Pong!");


    }


    public int getPlayerScore(int player) { // can work with this
        if (player == 1) return playerOneScore;
        else if (player == 2) return playerTwoScore;
        return 0;
    }

    public void addPointsToPlayer(int player, int value) {
        if (player == 1)  playerOneScore += value;
        else if (player == 2) playerTwoScore += value;
    }

    public void setVictoryScore(int score) {
        victoryScore = score;
    }

    public int getVictoryScore() {
        return victoryScore;
    }

    public int getVictor() {
        int victor = 0;
        if (playerOneScore >= victoryScore) victor = 1;
        else if (playerTwoScore>= victoryScore) victor =2;
        return victor;
    }

    public void addObject(Collidable object) {
        objects.add(object);
    }

    public ArrayList<Collidable> getObjects() {
        // Shallow copy so the object collection can not be accessed but
        // still breaks encapsulation because the objects in the collection
        // are accessible.
        return (ArrayList<Collidable>) objects.clone();
    }

    public void addPuck(Puckable ball) {
        this.pucks.add(ball);
    }

    public ArrayList<Puckable> getPucks() {
        // Also shallow copy
        return (ArrayList<Puckable>) pucks.clone();
    }
    public void speedUp(Puckable a)
    {
        a.setSpeed(a.getSpeed() + 1);

    }
    public void slowDown (Puckable a)
    {
        if (a.getSpeed() > 0 ) {
            a.setSpeed(a.getSpeed() - 1);
        }

    }
    public void move() {

        playOnePaddle.move();
        playTwoPaddle.move();

        for (Puckable puck : pucks) {
            checkCollision(puck);
            puck.move();
        }
    }

    public void checkCollision(Puckable puck) {
        objects.forEach((object) -> {
            Collision collision = object.getCollision((Shape)puck);
            if (collision.isCollided()) {
                collisionHandler(puck, collision);
            }
        });
    }

    protected void addPlayerPaddle(int player, MyPaddle paddle) {
        if (player == 1) {
            playOnePaddle = paddle;
            addObject(paddle);
        } else if (player == 2) {
            playTwoPaddle = paddle;
            addObject(paddle);
        }
    }

    public abstract void collisionHandler(Puckable puck, Collision collision);

    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playOnePaddle.moveUp();
                break;
            case S:
                playOnePaddle.moveDown();
                break;
            case I:
                playTwoPaddle.moveUp();
                break;
            case K:
                playTwoPaddle.moveDown();
                break;
            case J:
                playTwoPaddle.moveLeft();
                break;
            case L:
                playTwoPaddle.moveRight();
                break;
           case A:
               playOnePaddle.moveLeft();
               break;
            case D:
                playOnePaddle.moveRight();
                break;
            case Z:
                this.speedUp(this.pucks.get(0));
                break;
            case X:
                this.slowDown(this.pucks.get(0));
                break;
        }
    }

    public void keyReleased(KeyCode code) {
        switch (code) {
            case W,A,S,D:
                playOnePaddle.stop();
                break;
            case I, K, J, L:
                playTwoPaddle.stop();
                break;

        }
    }
}
