package unsw.dungeon;

public interface PlayerState {
    public void drink();
    public void move();
    public void kill(Enemy enemy);
}