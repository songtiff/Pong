package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AIPaddleTest {
    edu.csueastbay.cs401.ttruong.ClassicPong game;

    @BeforeEach
    void setUp() {
        game = new edu.csueastbay.cs401.ttruong.ClassicPong(10, 1300, 860, new AnchorPane());
    }

    //test if paddle moves in the correct direction
}
