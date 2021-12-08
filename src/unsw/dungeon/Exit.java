package unsw.dungeon;

public class Exit extends Entity {
    private ExitGoal goal;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
    }

    public void setGoal(Goal goal) {
        this.goal = (ExitGoal) goal;
    }

    public boolean checkGoal() {
        return goal.isDone();
    }

    public ExitGoal getGoal() {
        return goal;
    }
}