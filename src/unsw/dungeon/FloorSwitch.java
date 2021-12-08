package unsw.dungeon;

public class FloorSwitch extends Entity {
    BoulderGoal goal;
    
    public FloorSwitch(int x, int y) {
        super(x, y);
    }

    public void setGoal(Goal goal) {
        this.goal = (BoulderGoal) goal;
        this.goal.addSwitch(this);
    }

    public void steppedOn() {
        if (goal != null) goal.addCounter();
    }

    public void steppedOff() {
        if (goal != null) goal.decreaseCounter();
    }
}