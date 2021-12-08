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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class MenuController {

    private Stage stage;

    private Scene scene;

    private LevelController level;

    private GameOverController gameOver;

    private InfoController info;

    @FXML
    private Button levels;

    @FXML
    private Button infoBtn;

    @FXML
    private Button exitGame;

    public MenuController(Stage stage) throws IOException {
        this.stage = stage;
        this.level = new LevelController(stage);
        this.gameOver = new GameOverController(stage);
        this.info = new InfoController(stage);
        gameOver.setMenu(this);
        level.setMenu(this);
        gameOver.setLevelController(level);
        info.setMenu(this);
    }

    @FXML
    public void initialize() throws IOException {
        
        
    }

    public void activate() throws IOException {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOfLifeView2.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene gameOver = new Scene(root);
        root.requestFocus();
        this.stage.setScene(gameOver);*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        this.scene = new Scene(root);
        root.requestFocus();
        this.stage.setScene(scene);
        this.stage.show();
    }

    @FXML
    public void showLevelsScene(ActionEvent e) throws IOException{
        level.activate();
    }

    @FXML
    public void showInfoScene(ActionEvent e) throws IOException {
        // level.activate();
        this.info.activate();
    }

    @FXML
    public void handleExit(ActionEvent e) throws IOException {
        Stage stage = (Stage) infoBtn.getScene().getWindow();
        stage.close();
    }

    public void setDungeonController(DungeonController dc) {
        //this.dc = dc;
    }

    public LevelController getLevel() {
        return level;
    }

    public void setLevel(LevelController level) {
        this.level = level;
    }

    public GameOverController getGameOver() {
        return gameOver;
    }

    public void setGameOver(GameOverController gameOver) {
        this.gameOver = gameOver;
    }

    public InfoController getInfo() {
        return info;
    }

    public void setInfo(InfoController info) {
        this.info = info;
    }

    
}

