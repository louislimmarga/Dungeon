package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ANDGoal implements Goal {
    private Dungeon dungeon;
    private List<Goal> goals;
    private boolean done;

    public ANDGoal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.goals = new ArrayList<>();
    }

    @Override
    public boolean isDone() {
        if (this.done) return true;
        int counter = 0;
        for (Goal g : goals) {
            if (g == null) continue;
            if (g instanceof ExitGoal) {
                counter += 1;
                continue;
            }
            if (g.isDone()) {
                counter += 1;
            }
        }

        if (counter == goals.size()) return true;
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