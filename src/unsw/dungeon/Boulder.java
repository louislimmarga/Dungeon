package unsw.dungeon;

public class Boulder extends Entity implements Movable{
    Dungeon dungeon;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        checkSwitch(x, y, x, y);
    }

    @Override
    public void moveUp() {
        if (getY() > 0) {
            if (checkConditions(getX(), getY() - 1)) {
                dungeon.removeFromMap(getX(), getY());
                y().set(getY() - 1); 
                dungeon.addToMap(this, getX(), getY());
                checkSwitch(getX(), getY(), getX(), getY() + 1);
            }
        }
    }

    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            if (checkConditions(getX(), getY() + 1)) {
                dungeon.removeFromMap(getX(), getY());
                y().set(getY() + 1);
                dungeon.addToMap(this, getX(), getY());
                checkSwitch(getX(), getY(), getX(), getY() - 1);
            }
        }
            
    }

    @Override
    public void moveLeft() {
        if (getX() > 0) {
            if (checkConditions(getX() - 1, getY())) {
                dungeon.removeFromMap(getX(), getY());
                x().set(getX() - 1);
                dungeon.addToMap(this, getX(), getY());
                checkSwitch(getX(), getY(), getX() + 1, getY());;
            }
        }
           
    }

    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            if (checkConditions(getX() + 1, getY())) {
                dungeon.removeFromMap(getX(), getY());
                x().set(getX() + 1);
                dungeon.addToMap(this, getX(), getY());
                checkSwitch(getX(), getY(), getX() - 1, getY());
            }
        }
    }
    @Override
    public boolean checkConditions(int x, int y) {
        Entity entity = dungeon.checkMap(x, y);
        
        if (entity != null) {
            if (entity instanceof Wall) return false;
            if (entity instanceof Exit) return false;
            if (entity instanceof Boulder) return false;
            if (entity instanceof Door) return false;
        }

        entity = dungeon.checkLayer(x, y);
        if (entity instanceof Portal) return false;
        
        return true;
    }

    public void checkDirection() {
        if (dungeon.checkMap(getX() - 1, getY()) instanceof Player) moveRight();
        else if (dungeon.checkMap(getX() + 1, getY()) instanceof Player) moveLeft();
        else if (dungeon.checkMap(getX(), getY()- 1) instanceof Player) moveDown();
        else if (dungeon.checkMap(getX(), getY() + 1) instanceof Player) moveUp();
    }

    public void checkSwitch(int x, int y, int xbefore, int ybefore) {
        Entity entity = dungeon.checkLayer(x, y);
        Entity entitybefore = dungeon.checkLayer(xbefore, ybefore);
        FloorSwitch plate = null;
        FloorSwitch platebefore = null;
        if ( entity instanceof FloorSwitch ) plate = (FloorSwitch) entity;
        if ( entitybefore instanceof FloorSwitch ) platebefore = (FloorSwitch) entitybefore;
        if (plate != null) {
            plate.steppedOn();
        }
        else if (platebefore != null) {
            platebefore.steppedOff();
        }
    }
    
}