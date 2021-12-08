package unsw.dungeon;

public class HealthPotion extends Item{

    public HealthPotion(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public void pickup() {
        dungeon.removeFromMap(getX(), getY());
        System.out.println("You picked up a Health potion");
        //use();
    }

    @Override
    public void use() {
        System.out.println("Gained an extra life");
    }
    
}