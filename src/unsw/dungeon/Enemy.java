package unsw.dungeon;

public class Enemy extends Entity implements Movable, Observer {
    private Dungeon dungeon;
    private EnemyState state;
    private Player player;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        state =  new AttackState(dungeon, this);
    }
    
    public void addPlayer(Player player) {
        this.player = player;
    }

    public EnemyState getState() {
        return state;
    }
    public void setState(EnemyState state) {
        this.state = state;
    }

    @Override
    public void update(Player player) {
        if (player.getState() instanceof VulnerableState) {
            //System.out.println("##################");
            chase(player);
        } else if (player.getState() instanceof InvincibleState) {
            runAway(player);
        }
    }
    public void chase(Player player) {
        state.chase(player);
    }
    public void runAway(Player player) {
        state.runAway(player);
    }
    public void die() {
        //System.out.println("AA");
        dungeon.removeFromMap(getX(), getY());
        
        this.delete();
    }
    @Override
    public void moveUp() {
        if (getY() > 0) {
            if (checkConditions(getX(), getY() - 1)) {
                dungeon.removeFromMap(getX(), getY());
                y().set(getY() - 1);
                dungeon.addToMap(this, getX(), getY());
            }
        }
        //System.out.println("AA");
    }

    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            if (checkConditions(getX(), getY() + 1)) {
                dungeon.removeFromMap(getX(), getY());
                y().set(getY() + 1);
                dungeon.addToMap(this, getX(), getY());
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
            }
        }
    }

    @Override
    public boolean checkConditions(int x, int y) {
        Entity entity = dungeon.checkMap(x, y);

        if (entity != null) {
            if (entity instanceof Wall)
                return false;
            if (entity instanceof Exit)
                return false;
            if (entity instanceof Boulder)
                return false;
            if (entity instanceof Door) {
                Door door = (Door) entity;
                if (door.getLock()) return false;
                else return true;
            }
        }
        return true;
    }

}