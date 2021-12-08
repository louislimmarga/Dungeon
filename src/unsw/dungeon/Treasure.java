package unsw.dungeon;

public class Treasure extends Item{
    private TreasureGoal goal;

    public Treasure (Dungeon dungeon, int x, int y) {
        super(dungeon,x,y);
        System.out.println("Treasure added");
    }

    public void setGoal(TreasureGoal goal) {
        this.goal = goal;
        goal.addTreasure();
    }
    @Override
    public void pickup() {
        dungeon.removeFromMap(getX(), getY());
        // Remove icon from dungeon
        System.out.println("You picked up a treasure");
        use();
    } 

    @Override
    public void use() {
        goal.updateGoal();
    }
}