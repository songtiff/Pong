package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionTest {

    private Collision bang;

    @BeforeEach
    void setUp() {
        bang = new Collision(
                "Wall",
                "Test Wall",
                true,
                50,
                200,
                50,
                125);
    }

    @Test
    void getType() {
        assertEquals("Wall", bang.getType());
    }

    @Test
    void getCenterX() {
        assertEquals(87.5, bang.getCenterX());
    }

    @Test
    void getCenterY() { assertEquals(125, bang.getCenterY()); }

    @Test
    void getTop() {
        assertEquals(50, bang.getTop());
    }

    @Test
    void getBottom() {
        assertEquals(200, bang.getBottom());
    }

    @Test
    void getLeft() {
        assertEquals(50, bang.getLeft());
    }

    @Test
    void getRight() {
        assertEquals(125, bang.getRight());
    }

    @Test
    void isCollided() {
        assert(bang.isCollided());
    }

    @Test
    void getObjectID() {
        assertEquals("Test Wall", bang.getObjectID());
    }
}