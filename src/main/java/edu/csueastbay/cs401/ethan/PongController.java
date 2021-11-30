package edu.csueastbay.cs401.ethan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class PongController extends StackPane {

    private static final PongController instance = new PongController();
    public static PongController getInstance() { return instance; }

    /**
     * Minimal controller adds singleton PongController to scene
     */
    public static class Landing implements Initializable {
        @FXML
        private SubScene subScene;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            Group root = new Group();
            root.getChildren().add(PongController.getInstance());
            subScene.setRoot(root);
            Platform.runLater(PongController.getInstance()::requestFocus);
        }
    }

    PongGame game;

    PongPane pongPane;
    MenuPane menuPane;

    public PongController() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("pong.css")).toExternalForm());
        game = new PongGame();

        focusedProperty().addListener((obj, oldVal, newVal)->{
            if(!newVal) {
                game.setPlaying(false);
                menuPane.setVisible(true);
                requestFocus();
            }
        });

        setOnKeyPressed(this::onKeyPressed);
        game.input.bind(this);

        pongPane = new PongPane(game);
        getChildren().add(pongPane);

        menuPane = createMenus();
        getChildren().add(menuPane);
    }

    private MenuPane createMenus() {
        MenuPane menuPane = new MenuPane();
        var menu = menuPane.getMenu("PAUSED");
        Button button;

        button = new Button("RESUME");
        button.setOnAction(e->{
            menuPane.setVisible(false);
            game.setPlaying(true);
        });
        menu.addButton(button);

        button = new Button("CONTROLS");
        button.setOnAction(e->menuPane.nextMenu("CONTROLS"));
        menu.addButton(button);

        menu = menuPane.getMenu("CONTROLS");
        for(Map.Entry<String, KeyCode> entry : game.input.controls.entrySet()) {
            String control = entry.getKey();
            Button controlButton = new Button(String.format("<%s>", entry.getValue().getName()));
            controlButton.setOnAction(actionEvent->{
                controlButton.setText("<...>");
                setOnKeyPressed(keyEvent->{
                    if(!keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                        game.input.controls.put(control, keyEvent.getCode());
                    }
                    controlButton.setText(String.format("<%s>", game.input.controls.get(control).getChar()));
                    setOnKeyPressed(this::onKeyPressed);
                });
            });
            menu.addLabelledButton(control+": ", controlButton);
        }
        button = new Button("DONE");
        button.setOnAction(e->menuPane.previousMenu());
        menu.addButton(button);

        return menuPane;
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ESCAPE)) {
            if(game.isPlaying()) {
                menuPane.setVisible(true);
                menuPane.setActiveMenu("PAUSED");
                game.setPlaying(false);
            } else if(menuPane.isVisible()) {
                if(!menuPane.previousMenu()) {
                    menuPane.setVisible(false);
                    game.setPlaying(true);
                }
            }
        }
    }

    public void rebindControl(String control, KeyEvent event) {
        if(event.getCode().equals(KeyCode.ESCAPE)) return;
        game.input.controls.put(control, event.getCode());
    }
}
