package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GoalTest {

    Goal testGoal;

    @BeforeEach
    void setUp() {
        testGoal = new Goal("Test Goal", 10, 10, 10, 100);
    }

    @Test
    void getID() {
        assertEquals("Test Goal", testGoal.getID(),
                "Should be able to get the goal ID");
    }

    @Test
    void getType() {
        assertEquals("Goal", testGoal.getType(),
                "getType should return 'Goal'");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testGoal.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Goal", bang.getType());
        assertEquals("Test Goal", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(50, 50, 10, 10);
        Collision bang = testGoal.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Goal", bang.getType());
        assertEquals("Test Goal", bang.getObjectID());
        assertEquals(15, bang.getCenterX());
        assertEquals(60, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(110, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(20, bang.getRight());
    }




}