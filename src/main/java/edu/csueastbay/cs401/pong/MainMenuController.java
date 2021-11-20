package edu.csueastbay.cs401.pong;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Registry registry;

    @FXML
    private StackPane scalePane;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox studentGameList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registry = new Registry();
        registry.register("Classic Pong", "classic", "Classic Pong Game");

        registry.reset();

        DoubleBinding gameScale = Bindings.createDoubleBinding(
                ()-> {
                    System.out.println(scalePane.getWidth()+" "+scalePane.getHeight());
                    return Math.min(1, Math.min(
                            scalePane.getWidth()/gamePane.getPrefWidth(),
                            scalePane.getHeight()/gamePane.getPrefHeight()
                    ));
                },
                scalePane.widthProperty(), scalePane.heightProperty()
        );

        gamePane.setManaged(false);
        gamePane.scaleXProperty().bind(gameScale);
        gamePane.scaleYProperty().bind(gameScale);
        gamePane.translateXProperty().bind(Bindings.createDoubleBinding(
                ()->(scalePane.getWidth()-gamePane.getPrefWidth()*gameScale.get())/2,
                scalePane.widthProperty(), gameScale));
        gamePane.translateYProperty().bind(Bindings.createDoubleBinding(
                ()->(scalePane.getHeight()-gamePane.getPrefHeight()*gameScale.get())/2,
                scalePane.heightProperty(), gameScale));

        while (registry.next()) {
            Button gameButton = new Button();
            gameButton.setText(registry.getStudentName());
            gameButton.setPrefWidth(180);
            gameButton.setOnAction(new EventHandler<ActionEvent>() {
                private final String packageName = registry.getPackageName();
                @Override
                public void handle(ActionEvent actionEvent) {
                    loadGamePane("/edu/csueastbay/cs401/" + packageName +"/field.fxml");
                }
            });
            studentGameList.getChildren().add(gameButton);
        }
    }


    private void loadGamePane(String template) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(template));
            gamePane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
