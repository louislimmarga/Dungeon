package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class EnemiesGoal implements Goal {
    private boolean done;
    private Dungeon dungeon;

    public EnemiesGoal(Dungeon dungeon) {
        this.done = false;
        this.dungeon = dungeon;
    }

    @Override
    public boolean isDone() {
        if (dungeon.getPlayer().getEnemies().isEmpty()) {
            return true;
        } else return false;
    }

    @Override
    public void setDone(boolean done) {
        if (done) System.out.println("All Enemies are killed!");
        dungeon.update(this);
        this.done = done;
    }
}