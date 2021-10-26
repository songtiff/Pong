package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    Wall testWall;

    @BeforeEach
    void setUp() {
        testWall = new Wall("Test Wall", 10, 10, 1000, 10);
    }

    @Test
    void getID() {
        assertEquals("Test Wall", testWall.getID(),
                "Test Wall should have a id of 'Test Wall'");
    }

    @Test
    void getType() {
        assertEquals("Wall", testWall.getType(),
                "Wall should have a type of 'Wall'");
    }

    @Test
    void getCollision() {
        Rectangle rect = new Rectangle(10, 10, 10, 10);
        Collision bang = testWall.getCollision(rect);
        assertTrue(bang.isCollided());
        assertEquals("Wall", bang.getType());
        assertEquals("Test Wall", bang.getObjectID());
        assertEquals(510, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(1010, bang.getRight());
    }

    @Test
    void getNoCollision() {
        Rectangle rect = new Rectangle(100, 100, 10, 10);
        Collision bang = testWall.getCollision(rect);
        assertFalse(bang.isCollided());
        assertEquals("Wall", bang.getType());
        assertEquals("Test Wall", bang.getObjectID());
        assertEquals(510, bang.getCenterX());
        assertEquals(15, bang.getCenterY());
        assertEquals(10, bang.getTop());
        assertEquals(20, bang.getBottom());
        assertEquals(10, bang.getLeft());
        assertEquals(1010, bang.getRight());
    }

}