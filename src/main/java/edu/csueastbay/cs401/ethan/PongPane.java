package edu.csueastbay.cs401.ethan;

import edu.csueastbay.cs401.ethan.game.Entity;
import javafx.beans.binding.Bindings;
import javafx.collections.SetChangeListener;
import javafx.collections.WeakSetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class PongPane extends StackPane {

    @FXML
    private Group entityGroup;

    @FXML
    private Label p1Score;

    @FXML
    private Label p2Score;

    public PongPane(PongGame game) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pong_pane.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        entityGroup.getChildren().add(game.pane);
        p1Score.textProperty().bind(game.p1Score.asString());
        p2Score.textProperty().bind(game.p2Score.asString());
    }
}
