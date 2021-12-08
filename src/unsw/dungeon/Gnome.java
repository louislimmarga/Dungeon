package unsw.dungeon;

import java.util.Random;

public class Gnome extends Enemy{

    public Gnome(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public void update(Player player) {
        Random rand = new Random();
        int move = rand.nextInt(3);
        switch(move) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
    }
}