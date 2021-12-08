package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    @FXML
    private Button continueBtn;

    private List<ImageView> initialEntities;

    private Player player;
    private Dungeon dungeon;

    private Stage stage;

    private GameOverController gameOverController;
    private InventoryController inventory;
    private GoalController goal;

    private InfoController info;

    private String name;

    private int treasureCount = 0;
    private int swordCount = 0;
    private int keyCount = 0;
    private int ipotionCount = 0;
    private int hpotionCount = 0;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Stage stage) throws IOException {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.stage = stage;
        this.info = new InfoController(stage);
        this.inventory = new InventoryController(stage);
        this.goal = new GoalController(stage);
        goal.setDungeonController(this);
        inventory.setDungeonController(this);
    }

    public void setGameOverController(GameOverController gameOverController) {
        this.gameOverController = gameOverController;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @FXML
    public void initialize() throws IOException {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
        }

        player.exist().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    gameOverController.activate();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        player.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (ipotionCount != 0) ipotionCount -= 1;
            }
        });
        player.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (ipotionCount != 0) ipotionCount -= 1;
            }
        });

        dungeon.done().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("AA");
                try {
                    gameOverController.activate(1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        for (Entity e : dungeon.getEntity()) {
            if (e instanceof Item) {
                e.exist().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        updateItem((Item)e);
                    }
                });

                if ((Item)e instanceof Sword) {
                    Sword sword = (Sword) ((Item)e);
                    sword.charge().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            updateItem((Item)sword);
                        }
                    });
                }
            }
        }


    }

    public void updateItem(Item item) {
        if (item instanceof Treasure) treasureCount += 1;
        else if (item instanceof Sword) swordCount = ((Sword)item).getcharge();
        else if (item instanceof Key) keyCount += 1;
        else if (item instanceof InvinciblePotion) ipotionCount += 31;
        else if (item instanceof HealthPotion) hpotionCount += 1;
        System.out.println(treasureCount + " " + swordCount + " " + keyCount + " " + ipotionCount + " " + hpotionCount);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case SPACE:
            player.attack();
            break;
        case P:
            info.activate();
            break;
        case I:
            inventory.activate();
            break;
        case G:
            goal.activate();
            break;
        default:
            break;
        }
    }

	public int getTreasureCount() {
		return treasureCount;
	}

	public void setTreasureCount(int treasureCount) {
		this.treasureCount = treasureCount;
	}

	public int getSwordCount() {
		return swordCount;
	}

	public void setSwordCount(int swordCount) {
		this.swordCount = swordCount;
	}

	public int getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(int keyCount) {
		this.keyCount = keyCount;
	}

	public int getIpotionCount() {
		return ipotionCount;
	}

	public void setIpotionCount(int ipotionCount) {
		this.ipotionCount = ipotionCount;
	}

	public int getHpotionCount() {
		return hpotionCount;
	}

	public void setHpotionCount(int hpotionCount) {
		this.hpotionCount = hpotionCount;
	}

}

