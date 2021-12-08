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
public class GoalController {

    private Stage stage;

    private DungeonController dc;

    @FXML
    private Button infoBtn;

    public GoalController(Stage stage) throws IOException {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // System.out.println("BB");
    }

    public void activate() throws IOException {
        Stage info = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOfLifeView4.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene gameOver = new Scene(root);

        root.requestFocus();
        info.setScene(gameOver);
        info.show();
    }

    @FXML
    public void handleInfo(ActionEvent e) throws IOException {
        Stage stage = (Stage) infoBtn.getScene().getWindow();
        stage.close();
    }

    public void setDungeonController(DungeonController dc) {
        this.dc = dc;
    }
}
