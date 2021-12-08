package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger.Level;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class GameOverController {

    private Stage stage;

    private MenuController menu;

    private DungeonController dc;

    private LevelController level;

    @FXML
    private Pane pane;

    @FXML
    private Button menuBtn;

    @FXML
    private Button retry;

    @FXML
    private Button exitGame;
    
    @FXML
    private Button continueBtn;

    public GameOverController(Stage stage) {
        this.stage = stage;
        this.continueBtn = new Button();
    }

    @FXML
    public void initialize() {
        
    }

    public void activate() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene gameOver = new Scene(root);
        root.requestFocus();
        this.stage.setScene(gameOver);
        this.stage.show();
    }

    public void activate(int i) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene gameOver = new Scene(root);
        root.requestFocus();
        continueBtn.setLayoutX(228.0);
        continueBtn.setLayoutY(338.0);
        continueBtn.setPrefWidth(145.0);
        continueBtn.setPrefHeight(36.0);
        continueBtn.setText("Continue");
        continueBtn.setOnAction(event -> {
            try {
                handleContinue(event);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        pane.getChildren().remove(retry); 
        pane.getChildren().addAll(continueBtn);
        this.stage.setScene(gameOver);
        this.stage.show();
    }

    @FXML
    public void showMenu(ActionEvent e) throws IOException {
        this.menu.activate();
    }

    @FXML
    public void handleRetry(ActionEvent e) throws IOException {
        this.level.loadLevel(dc.getName());
    }

    @FXML
    public void handleExit(ActionEvent e) throws IOException {
        Stage stage = (Stage) exitGame.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleContinue(ActionEvent e) throws IOException {
        this.level.loadLevel(level.getLevel(dc.getName()));
    }

    public void setMenu(MenuController menu) {
        this.menu = menu;
        if (this.menu == null) {
            System.out.println("NULL");
            return;
        }
    }

    public void setLevelController(LevelController level) {
        this.level = level;
    }

    public void setDungeonController(DungeonController dc) {
        this.dc = dc;
    }
}

