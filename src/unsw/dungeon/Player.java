package unsw.dungeon;

import java.util.ArrayList;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Movable, Subject{

    private Dungeon dungeon;
    private boolean alive;
    private ArrayList<Item> inventory;
    private ArrayList<Enemy> enemies;
    private PlayerState state;
    private int health;

    /**
     * Create a player positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.alive = true;
        this.setInventory(new ArrayList<>());
        this.enemies = new ArrayList<Enemy>();
        this.state = new VulnerableState(this);
        this.health = 1;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void attach(Enemy enemy) {
        if(! enemies.contains(enemy)) {
            enemies.add(enemy);
        }
    }
    
    @Override
    public void notifyObserver() {
        for (Enemy e : enemies) {
            e.update(this);
        }
    }

    @Override
    public void detach(Enemy enemy) {
        enemies.remove(enemy);
    }
    public void setState(PlayerState state) {
        this.state = state;
    }
    public PlayerState getState() {
        return state;
    }
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    public void addItem(Item item) {
        inventory.add(item);
    }

    public Item getSword() {
        for (Item i : inventory) {
            if (i instanceof Sword) {
                return i;
            }
        }
        return null;
    }

    public void die() {
        health--;
        if (health < 1) {
            dungeon.removeFromMap(getX(), getY());
            this.delete();
            System.out.println("You are dead");
        } else {
            dungeon.removeFromMap(getX(), getY());
            y().set(1);
            x().set(1);
            dungeon.addToMap(this, getX(), getY());
        }
        // Remove one health from the display
        
    }

    @Override
    public void moveUp() {
        if (getY() > 0) {
            if (checkConditions(getX(), getY() - 1)) {
                dungeon.removeFromMap(getX(), getY());
                y().set(getY() - 1); 
                dungeon.addToMap(this, getX(), getY());
                notifyObserver();
                state.move();
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
                notifyObserver();
                state.move();
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
                notifyObserver();
                state.move();
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
                notifyObserver();
                state.move();
            }
        }
    }

    public void attack() {
        Sword s = (Sword) getSword();
            if (s != null) {
               Enemy e = checkEnemy();                
                s.use();
                if (e != null) {
                    detach(e);
                    e.die();
                    System.out.println("You killed an enemy");
                }
            }
    }

    private Enemy checkEnemy() {
        Entity left = dungeon.checkMap(getX() - 1, getY());
        Entity right = dungeon.checkMap(getX() + 1, getY());
        Entity up = dungeon.checkMap(getX(), getY() + 1);
        Entity down = dungeon.checkMap(getX(), getY() - 1);
        Entity leftup = dungeon.checkMap(getX() - 1, getY() + 1);
        Entity leftdown = dungeon.checkMap(getX() - 1, getY() - 1);
        Entity rightup = dungeon.checkMap(getX() + 1, getY() + 1);
        Entity rightdown = dungeon.checkMap(getX() + 1, getY() - 1);

        Enemy e = null;
        if (left instanceof Enemy)
            e = (Enemy) left;
        else if (right instanceof Enemy)
            e = (Enemy) right;
        else if (up instanceof Enemy)
            e = (Enemy) up;
        else if (down instanceof Enemy)
            e = (Enemy) down;
        else if (leftup instanceof Enemy)
            e = (Enemy) leftup;
        else if (leftdown instanceof Enemy)
            e = (Enemy) leftdown;
        else if (rightup instanceof Enemy)
            e = (Enemy) rightup;
        else if (rightdown instanceof Enemy)
            e = (Enemy) rightdown;
        
        return e;
    }

    @Override
    public boolean checkConditions(int x, int y) {
        Entity entity = dungeon.checkMap(x, y);
        if (entity != null) {
            if (entity instanceof Wall) return false;
            if (entity instanceof Exit) {
                ExitGoal exit = ((Exit)entity).getGoal();
                if (dungeon.checkGoal()) {
                    System.out.println("You have found the exit!");
                    exit.setDone(true);
                    return true;
                }
                else {
                    return false;
                }
            }
            if (entity instanceof Boulder) {
                Boulder boulder = (Boulder) entity;
                boulder.checkDirection();
                if (dungeon.checkMap(x, y) instanceof Boulder) return false;
            }
            if (entity instanceof Item) {
                Item item = (Item) entity;
                item.pickup();
                item.delete();
                if (item instanceof InvinciblePotion) {
                    item.use();
                    state.drink();
                } else if (item instanceof HealthPotion) {
                    item.use();
                    health++;
                }
                // add sword, key and treasure to inventory
                addItem(item);  
            }
            if (entity instanceof Enemy) {
                //System.out.println("ASDASD");
                Enemy e = (Enemy) entity;
                state.kill(e);
            }
            if (entity instanceof Door) {
                Door door = (Door) entity;
                if (door.getLock()) {
                    for (Item i : inventory) {
                        if (i instanceof Key) {
                            Key key = (Key) i;
                            if (door.unlock(key)) {
                                //openDoor();
                                return true;
                            };
                        }
                    }
                }
                return false;
            }
        }

        entity = dungeon.checkLayer(x, y);
        if (entity instanceof Portal) {
            Portal portal = (Portal) dungeon.checkLayer(x, y);
            portal.teleport(this);
            return false;
        }

        return true;
    }
}
