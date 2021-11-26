package edu.csueastbay.cs401.psander.game.scripts;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.events.EventHub;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;
import edu.csueastbay.cs401.psander.engine.render.TextRenderer;
import edu.csueastbay.cs401.psander.engine.scenes.SceneManager;
import edu.csueastbay.cs401.psander.engine.scripts.Script;
import edu.csueastbay.cs401.psander.game.messages.GoalHitMessage;
import edu.csueastbay.cs401.psander.game.scenes.GameOverScene;

public class BasicGameManager extends Script {

    private GameObject _ball;
    private TextRenderer _player1ScoreText;
    private TextRenderer _player2ScoreText;


    public void init(GameObject ball, TextRenderer player1ScoreText, TextRenderer player2ScoreText) {
        _ball = ball;
        _player1ScoreText = player1ScoreText;
        _player2ScoreText = player2ScoreText;

        EventHub.getInstance().subscribe(GoalHitMessage.class, this, this::goalHit);
    }

    @Override
    public void update(double delta) {

    }

    public void goalHit(GoalHitMessage message) {
        if (message.playerOwner() == 1) {
            var score = PongWare.State().get_player1Score();
            score++;
            PongWare.State().set_player1Score(score);
            _player1ScoreText.setText(Integer.toString(score));

            if (score == PongWare.State().get_winningScore()) {
                PongWare.State().set_winningPlayer(1);
                goToGameOverScrene();
            }
        } else if (message.playerOwner() == 2) {
            var score = PongWare.State().get_player2Score();
            score++;
            PongWare.State().set_player2Score(score);
            _player2ScoreText.setText(Integer.toString(score));

            if (score == PongWare.State().get_winningScore()) {
                PongWare.State().set_winningPlayer(2);
                goToGameOverScrene();
            }
        }

        _ball.Transform().Position().set(1280 / 2, 720/2);
    }

    private void goToGameOverScrene() {
        EventHub.getInstance().unsubscribe(GoalHitMessage.class, this);
        var scene = new GameOverScene();
        scene.init();
        SceneManager.getInstance().swap(scene);
    }
}
