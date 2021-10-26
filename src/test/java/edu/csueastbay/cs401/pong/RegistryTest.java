package edu.csueastbay.cs401.pong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryTest {
    Registry registry;

    @BeforeEach
    void loadRegistry() {
        registry = new Registry();
        registry.register("Testy Testerson", "ttesterson", "This is my description");
        registry.register("Bob smith", "bsmith", "Bob is the man");
    }

    @Test
    void getFirst() {
        registry.reset();
        registry.next();
        assertEquals("Bob smith", registry.getStudentName() );
        assertEquals("bsmith", registry.getPackageName() );
        assertEquals("Bob is the man", registry.getDescription() );
    }

    @Test
    void getSecond() {
        registry.reset();
        registry.next();
        registry.next();
        assertEquals("Testy Testerson", registry.getStudentName() );
        assertEquals("ttesterson", registry.getPackageName() );
        assertEquals("This is my description", registry.getDescription() );
    }

    @Test
    void outOfBounds() {
        registry.reset();
        registry.next();
        registry.next();
        assertFalse(registry.next());
    }
}