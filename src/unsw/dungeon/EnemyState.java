package unsw.dungeon;

public interface EnemyState {
    public void chase(Player player);
    public void runAway(Player player);
}