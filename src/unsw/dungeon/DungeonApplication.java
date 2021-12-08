package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");
        
        MenuController controller = new MenuController(primaryStage);
        GameOverController gameOverController = new GameOverController(primaryStage);
        gameOverController.setMenu(controller);
        controller.activate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
