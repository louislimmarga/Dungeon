package unsw.dungeon;

public abstract class Item extends Entity{
    protected Dungeon dungeon;
    public Item(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }
    public abstract void pickup();
    public abstract void use();    
}