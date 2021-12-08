package unsw.dungeon;

public class TreasureGoal implements Goal{
    private int countTreasure;
    private boolean done;
    private Dungeon dungeon; 

    public TreasureGoal(Dungeon dungeon) {
        countTreasure = 0;
        done = false;
        this.dungeon = dungeon;
        System.out.println("Treasure Goal added");
    }

    public void addTreasure() {
        countTreasure++;
        System.out.println("New treasure added to dungeon");
    }
    public void updateGoal() {
        countTreasure--;
        System.out.println("You found a treasure");
        if(isDone()) setDone(true);
    }
    @Override
    public boolean isDone() {
        if (countTreasure == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void setDone(boolean done) {
        this.done = done;
        System.out.println("All treasure has been found!");
        dungeon.update(this);
    }
    
}