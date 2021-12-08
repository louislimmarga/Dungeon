package unsw.dungeon;

public class InvinciblePotion extends Item{
    
    public InvinciblePotion(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

	@Override
	public void pickup() {
        dungeon.removeFromMap(getX(), getY());
        System.out.println("You picked up an invincible potion");
	}

    @Override
    public void use() {
        System.out.println("Invinsible active");
    }
}