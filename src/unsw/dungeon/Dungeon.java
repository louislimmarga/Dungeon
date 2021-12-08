/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private List<Goal> goals;
    private List<Goal> allGoals;
    private Player player;
    private Entity[][] map;
    private Entity[][] layer;
    private IntegerProperty done;



    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.allGoals = new ArrayList<>();
        this.player = null;
        this.map = new Entity[width][height];
        this.layer = new Entity[width][height];
        this.done = new SimpleIntegerProperty(0);
    }

    public IntegerProperty done() {
        return done;
    }

    public int getDone() {
        return done().get();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity != null)
            addToMap(entity, entity.getX(), entity.getY());
            if (entity instanceof Enemy) {
                Enemy e = (Enemy) entity;
                player.attach(e);
                e.addPlayer(player);
            }
    }

    public List<Entity> getEntity() {
        return this.entities;
    }

    public void addToMap(Entity entity, int x, int y) {
        map[x][y] = entity;
        if (entity instanceof FloorSwitch || entity instanceof Portal)
            addToLayer(entity, x, y);
    }

    public void removeFromMap(int x, int y) {
        map[x][y] = null;
    }

    public void removeFromLayer(int x, int y) {
        layer[x][y] = null;
    }

    public void addToLayer(Entity entity, int x, int y) {
        layer[x][y] = entity;
    }

    public Entity checkMap(int x, int y) {
        if (map[x][y] == null) return null;
        return map[x][y];
    }

    public Entity checkLayer(int x, int y) {
        if (layer[x][y] == null) return null;
        return layer[x][y];
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
        if (! (goal instanceof ANDGoal) && ! (goal instanceof ORGoal)) addAllgoal(goal);
    }

    public void update(Goal goal) {
        checkGoal();
    }

    public void addAllgoal(Goal goal) {
        allGoals.add(goal);
    }

    public List<Goal> getAllgoals() {
        return allGoals;
    }

    public boolean checkGoal() {
        //System.out.println("You cleared the level.");
        for (Goal g : goals) {
            if (g == null) continue;
            if (! g.isDone()) return false;
        }
        //System.out.println("All goals are done!");
        for (Goal g : allGoals) {
            if (g instanceof ExitGoal) {
                ExitGoal exit = (ExitGoal) g;
                if (! exit.getDone()) {
                    System.out.println("All goals are done!\nGet to the exit!");
                    return true;
                }
                else {
                    done.set(1);
                    System.out.println("You cleared the level.");
                    return true;
                }
            }
        }
        done.set(1);
        System.out.println("You cleared the level.");
        return true;
    }

    public List<Goal> getGoals() {
        return goals;
    }
}