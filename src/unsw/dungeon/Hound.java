package unsw.dungeon;

public class Hound extends Enemy {

    public Hound(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    @Override
    public void update(Player player) {
        if (player.getState() instanceof VulnerableState) {
            // System.out.println("##################");
            super.chase(player);
            super.chase(player);
        } else if (player.getState() instanceof InvincibleState) {
            super.runAway(player);
            super.chase(player);
        }
    }
    
}