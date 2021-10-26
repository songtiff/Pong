package edu.csueastbay.cs401.classic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassicPongTest {

    ClassicPong game;

    @BeforeEach
    void setUp() {
        game = new ClassicPong(10, 1300, 860);
    }

    @Test
    void startState() {

    }

    @Test
    void collisionHandler() {
    }

    @Test
    void mapRange() {
    }
}