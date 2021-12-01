package edu.csueastbay.cs401.pong;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Registry registry;

    @FXML
    private BorderPane baseBorderPane;
    @FXML
    private StackPane scalePane;
    @FXML
    private Label titleLabel;
    @FXML
    private VBox studentGameList;
    @FXML
    private Group gameGroup;

    private DoubleProperty prefWidth, prefHeight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registry = new Registry();
        registry.register("Classic Pong", "classic", "Classic Pong Game");
        registry.register("Pyae Maung", "pyae", "Organic Pong");

        registry.reset();

        prefWidth = new SimpleDoubleProperty(scalePane.getPrefWidth());
        prefHeight = new SimpleDoubleProperty(scalePane.getPrefHeight());

        NumberBinding gameScale = Bindings.min(
                Bindings.divide(scalePane.widthProperty(), prefWidth),
                Bindings.divide(scalePane.heightProperty(), prefHeight)
        );
        gameGroup.scaleXProperty().bind(gameScale);
        gameGroup.scaleYProperty().bind(gameScale);

        while (registry.next()) {
            Button gameButton = new Button();
            gameButton.setText(registry.getStudentName());
            gameButton.setPrefWidth(280);
            gameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    loadGamePane("/edu/csueastbay/cs401/" + registry.getPackageName() +"/field.fxml");
                }
            });
            studentGameList.getChildren().add(gameButton);
        }
    }

    private void loadGamePane(String template) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(template));
            if(root instanceof Region region) {
                prefWidth.set(region.getPrefWidth());
                prefHeight.set(region.getPrefHeight());
            }
            gameGroup.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
