package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    private Stage stage;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image boulderImage;
    private Image switchImage;
    private Image portalImage;
    private Image treasureImage;
    private Image keyImage;
    private Image doorCloseImage;
    private Image doorOpenImage;
    private Image enemyImage;
    private Image swordImage;
    private Image inviPotionImage;
    private Image healthPotionImage;
    private Image houndImage;
    private Image gnomeImage;

    public DungeonControllerLoader(String filename, Stage stage)
            throws FileNotFoundException {
        super(filename);
        this.stage = stage;
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        doorCloseImage = new Image((new File("images/closed_door.png")).toURI().toString());
        doorOpenImage = new Image((new File("images/open_door.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        inviPotionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        healthPotionImage = new Image((new File("images/bubbly.png")).toURI().toString());
        houndImage = new Image((new File("images/hound.png")).toURI().toString());
        gnomeImage = new Image((new File("images/gnome.png")).toURI().toString());
        
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(FloorSwitch plate) {
        ImageView view = new ImageView(switchImage);
        addEntity(plate, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorCloseImage);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(InvinciblePotion potion) {
        ImageView view = new ImageView(inviPotionImage);
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(HealthPotion potion) {
        ImageView view = new ImageView(healthPotionImage);
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(Enemy enemy){
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }
    @Override
    public void onLoad(Hound hound) {
        ImageView view = new ImageView(houndImage);
        addEntity(hound, view);
    }
    
    @Override
    public void onLoad(Gnome gnome) {
        ImageView view = new ImageView(gnomeImage);
        addEntity(gnome, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        //ImageView view = (ImageView) node;
        //System.out.println(view.getX());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        entity.exist().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                //GridPane.getColumnIndex(node);
                if (entity instanceof Sword) {
                    GridPane.setRowIndex(node, 0);
                    GridPane.setColumnIndex(node, 4);
                } else if (entity instanceof Treasure) {
                    GridPane.setRowIndex(node, 0);
                    GridPane.setColumnIndex(node, 1);
                } else if (entity instanceof InvinciblePotion) {
                    GridPane.setRowIndex(node, 0);
                    GridPane.setColumnIndex(node, 2);
                } else if (entity instanceof HealthPotion) {
                    GridPane.setRowIndex(node, 0);
                    GridPane.setColumnIndex(node, 5);
                } else if (entity instanceof Key) {
                    GridPane.setRowIndex(node, 0);
                    GridPane.setColumnIndex(node, 3);
                } else if (entity instanceof Enemy) {
                    node.setVisible(false);
                } else if (entity instanceof Door) {
                    ImageView open = (ImageView) node;
                    open.setImage(doorOpenImage);    
                } else if (entity instanceof Player) {
                    node.setVisible(false);
                    
                }
                
            }
        });
    }

    

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * 
     * @return
     * @throws IOException
     */
    public DungeonController loadController() throws IOException {
        return new DungeonController(load(), entities, this.stage);
    }
}
