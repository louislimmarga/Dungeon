package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ORGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> goals;
    private boolean done;

    public ORGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.goals = new ArrayList<>();
        this.done = false;
    }

    @Override
    public boolean isDone() {
        if (this.done) return true;

        for (Goal g : goals) {
            if (g.isDone() || g instanceof ExitGoal) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }
    
}