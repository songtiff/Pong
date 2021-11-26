package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.input.InputManager;
import edu.csueastbay.cs401.psander.engine.scenes.SceneManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import edu.csueastbay.cs401.psander.game.scenes.BasicGameScene;
import javafx.scene.input.KeyCode;

public class TitleScreenManager extends Script {
    @Override
    public void update(double delta) {
        if (InputManager.getInstance().isKeyPressed(KeyCode.SPACE)) {
            PongWare.State().reset();
            PongWare.State().set_winningScore(10);
            var gameScene = new BasicGameScene();
            gameScene.init();
            SceneManager.getInstance().swap(gameScene);
        }
    }
}
