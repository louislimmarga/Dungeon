package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Sword extends Item{
    private IntegerProperty charge;

    public Sword(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.charge = new SimpleIntegerProperty(5);
    }
    @Override
    public void pickup() {
        dungeon.removeFromMap(getX(), getY());
        System.out.println("You picked up a sword");
    }

    @Override
    public void use() {
        if (getcharge() < 1) {
            System.out.println("Sword is out of charge");
        } else {
            charge.set(getcharge()-1);
        }
    }    

    public IntegerProperty charge() {
        return charge;
    }

    public int getcharge() {
        return charge().get();
    }
} 