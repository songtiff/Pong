package edu.csueastbay.cs401.psander.engine.scenes;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneTest {

    static class SceneMock extends Scene {

        @Override
        public void init() {

        }
    }

    @BeforeEach
    public void init() {
        SceneManager.getInstance().init();
    }

    @Test
    public void TestGameOBjectAdditionAndRemoval() {
        var scene = new SceneMock();
        var go = new GameObject("test");

        assertDoesNotThrow( () -> scene.addGameObject(go));
        assertDoesNotThrow( () -> scene.removeGameObject(go));
    }

    @Test
    public void addAndRemoveGameObjects() {
        var scene = new SceneMock();
        var go = new GameObject("test");

        assertDoesNotThrow(() -> scene.addGameObject(go));
        assertDoesNotThrow(() -> scene.removeGameObject(go));
    }
}
