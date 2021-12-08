package unsw.dungeon;

public class Key extends Item {
    private int id;

    public Key(Dungeon dungeon, int x, int y, int id) {
        super(dungeon, x, y);
        this.id = id;
    }

    @Override
    public void pickup() {
        dungeon.removeFromMap(getX(), getY());
        System.out.println("You picked up a Key");
    }

    @Override
    public void use() {
        // TODO Auto-generated method stub

    }

    public int getId() {
        return this.id;
    }
    
}