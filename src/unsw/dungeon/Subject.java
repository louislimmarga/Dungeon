package unsw.dungeon;

public interface Subject {
    public void notifyObserver();
    public void attach(Enemy enemy);
    public void detach(Enemy enemy);
}