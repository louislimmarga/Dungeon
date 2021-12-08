package unsw.dungeon;

//import java.io.File;

//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

public class Door extends Entity {
    private boolean lock;
    private int id;

    public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;
        this.lock = true;
        // TODO Auto-generated constructor stub
    }
    
    public boolean getLock() {
        return this.lock;
    }

    public int getId() {
        return this.id;
    }

    public boolean unlock(Key key) {
        if (key.getId() == this.id) {
            //Image doorOpenImage = new Image((new File("images/open_door.png")).toURI().toString());
            //ImageView view = new ImageView(doorOpenImage);
            this.lock = false;
            this.delete();
            return true;
        }
        return false;
    }

}