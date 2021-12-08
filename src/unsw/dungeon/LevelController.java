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
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class LevelController {

    private Stage stage;

    private MenuController menu;

    private String level1;
    private String level2;
    private String level3;

    @FXML
    private Button level1Btn;

    @FXML
    private Button level2Btn;

    @FXML
    private Button level3Btn;

    @FXML
    private Button back;

    public LevelController(Stage stage) throws IOException {
        this.stage = stage;
        this.level1 = "maze.json";
        this.level2 = "boulders.json";
        this.level3 = "advanced.json";
        /*
         * FXMLLoader loader = new
         * FXMLLoader(getClass().getResource("GameOfLifeView3.fxml"));
         * loader.setController(this); Parent root = loader.load(); Scene gameOver = new
         * Scene(root); root.requestFocus(); this.stage.setScene(gameOver);
         * this.stage.show();
         */
    }

    @FXML
    public void initialize() {
        //System.out.println("BB");
    }

    public void activate() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LevelsScene.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene gameOver = new Scene(root);
        root.requestFocus();
        this.stage.setScene(gameOver);
        this.stage.show();
    }

    @FXML
    public void showLevelScene1(ActionEvent e) throws IOException {
        loadLevel(level1);
    }

    @FXML
    public void showLevelScene2(ActionEvent e) throws IOException {
        loadLevel(level2);
    }

    @FXML
    public void showLevelScene3(ActionEvent e) throws IOException {
        loadLevel(level3);
    }

    @FXML
    public void handleBack(ActionEvent e) throws IOException {
        menu.activate();
    }

    public void loadLevel(String level) throws IOException {
        if (level == "Done") {
            System.out.println("You have completed the game!");
            return;
        }


        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(level, stage);
        DungeonController controller = dungeonLoader.loadController();
        controller.setGameOverController(menu.getGameOver());
        controller.setName(level);
        menu.getGameOver().setDungeonController(controller);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    public void setMenu(MenuController menu) {
        this.menu = menu;
    }

    public void setDungeonController(DungeonController dc) {
        //this.dc = dc;
    }

    public String getLevel(String level) {
        if (level == level1) return level2;
        if (level == level2) return level3;
        if (level == level3) return "Done";
        return "Done";
    }
}

