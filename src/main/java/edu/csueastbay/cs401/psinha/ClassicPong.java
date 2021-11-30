package edu.csueastbay.cs401.psinha;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.shape.Shape;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Random;

public class ClassicPong extends Game {

    private double fieldHeight;
    private double fieldWidth;

    boolean toastino2 = false;
    boolean toastino = false;



    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        MyPuck puck = new MyPuck(this.fieldWidth, this.fieldHeight);



        puck.setID("Classic");
        addPuck(puck); // add pucks



        //  set speed
        //when close to victory, increase speed of puck
        // add victory screen or end the game
        // if you score two in a row, reduce speed of your paddle

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



@Override
    public void collisionHandler(Puckable puck, Collision collision) {
        int diff;
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {

                    if(getPlayerScore(1) > getPlayerScore(2))
                    {
                        diff = getPlayerScore(1) - getPlayerScore(2);
                        if (diff > 3)
                        {
                           puck.reset();
                        }
                        else
                        {
                            addPointsToPlayer(1,1);
                            puck.reset();
                            if(getPlayerScore(1) == getVictoryScore())
                            {
                                toastino = true;


                            }
                        }
                    }

                   else if(getPlayerScore(2)>getPlayerScore(1))
                    {
                        diff = getPlayerScore(2) - getPlayerScore(1);
                        if (diff>3)
                        {
                            addPointsToPlayer(1,2);
                            puck.reset();
                            if(getPlayerScore(1) == getVictoryScore())
                            {
                                toastino = true;
                            }
                        }
                        else
                        {
                            addPointsToPlayer(1,1);
                            puck.reset();
                            if(getPlayerScore(1) == getVictoryScore())
                            {
                                toastino = true;
                            }
                        }
                    }

                   else if (getPlayerScore(1) == getPlayerScore(2))
                    {
                        addPointsToPlayer(1,1);
                        puck.reset();
                        if(getPlayerScore(1) == getVictoryScore())
                        {
                            toastino = true;
                        }
                    }
                }

                else if (collision.getObjectID() == "Player 2 Goal") {
                    // p2 lead p1

                    if (getPlayerScore(2) > getPlayerScore(1))
                    {
                        diff = getPlayerScore(2) - getPlayerScore(1);

                        if(diff>3)
                        {
                            puck.reset();
                        }
                        else
                        {
                            addPointsToPlayer(2,1);
                            puck.reset();
                            if(getPlayerScore(2) == getVictoryScore())
                            {
                                toastino2 = true;
                            }

                        }
                    }

                    else if (getPlayerScore(1)>getPlayerScore(2))
                    {
                       diff = getPlayerScore(1) - getPlayerScore(2);

                       if (diff>3)
                       {
                           addPointsToPlayer(2,2);
                           puck.reset();
                           if(getPlayerScore(2) == getVictoryScore())
                           {
                               toastino2 = true;
                           }

                       }
                       else
                       {
                           addPointsToPlayer(2,1);
                           puck.reset();
                           if(getPlayerScore(2) == getVictoryScore())
                           {
                               toastino2 = true;


                           }

                       }
                    }
                    else if (getPlayerScore(1) == getPlayerScore(2))
                    {
                        addPointsToPlayer(1,1);
                        puck.reset();
                        if(getPlayerScore(2) == getVictoryScore())
                        {
                            toastino2 = true;
                        }
                    }
                }
                break;
            case "Paddle":
                double puckCenter = ((MyPuck) puck).getCenterY();
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

}
