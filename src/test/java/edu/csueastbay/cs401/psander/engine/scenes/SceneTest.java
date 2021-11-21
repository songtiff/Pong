package edu.csueastbay.cs401.psander.engine.scenes;

import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneTest {

    static class SceneMock extends Scene {

        @Override
        public void init() {

        }
    }

    static class ScriptMock extends Script {
        double Delta;

        @Override
        public void update(double delta) {
            Delta = delta;
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

    @Test
    public void TestScriptExecution() {
        var scene = new SceneMock();
        var go = new GameObject("test");
        var script = new ScriptMock();
        go.addComponent(script);

        scene.addGameObject(go);
        scene.update(1.0);

        assertEquals(1.0, script.Delta);
    }
}
