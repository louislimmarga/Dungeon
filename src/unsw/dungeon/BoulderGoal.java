package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class BoulderGoal implements Goal {
    private boolean done;
    private List<FloorSwitch> switchlist;
    private int counter;
    private Dungeon dungeon;

    public BoulderGoal(Dungeon dungeon) {
        this.done = false;
        this.switchlist = new ArrayList<>();
        this.counter = 0;
        this.dungeon = dungeon;
    }

    @Override
    public boolean isDone() {
        if (counter == switchlist.size()) {
            return true;
        } else return false;
    }

    @Override
    public void setDone(boolean done) {
        if (done) System.out.println("All Floor Switch are triggered!");
        dungeon.update(this);
        this.done = done;
    }

    public void addSwitch(FloorSwitch plate) {
        switchlist.add(plate);
    }

    public void addCounter() {
        counter += 1;
        if (isDone()) setDone(true);
    }

    public void decreaseCounter() {
        counter -= 1;
        if (! isDone()) setDone(false);
    }
}