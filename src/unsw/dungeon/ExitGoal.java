package unsw.dungeon;

public class ExitGoal implements Goal {
    private boolean done;
    Dungeon dungeon;

    public ExitGoal(Dungeon dungeon) {
        this.done = false;
        this.dungeon = dungeon;
    }

    @Override
    public boolean isDone() {
        if (this.done) return true;
        //System.out.println(this.done);
        for (Goal g : dungeon.getGoals()) {
            if (g == null) continue;
            if (g instanceof ExitGoal) continue;
            if (! g.isDone()) return false;
            
        }
        
        //if (! this.done) setDone(true);
        //setDone(true);
        //System.out.println("Get to the exit!");
        setDone(true);
        return false;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
        dungeon.update(this);
        
    }

    public boolean getDone() {
        return done;
    }

}