package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        loadGoals(dungeon, jsonGoals);

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        return dungeon;
    }

    private void loadGoals(Dungeon dungeon, JSONObject json) {
        String type = json.getString("goal");
        Goal goal = null;
        switch (type) {
            case "exit":
                goal = new ExitGoal(dungeon);
                break;
            case "boulders":
                goal = new BoulderGoal(dungeon);
                break;
            case "treasure":
                goal = new TreasureGoal(dungeon);
                break;
            case "enemies":
                goal = new EnemiesGoal(dungeon);
                break;
            case "AND":
                JSONArray jsonSubgoals = json.getJSONArray("subgoals");
                goal = new ANDGoal (dungeon);
                for (int i = 0; i < jsonSubgoals.length(); i++) {
                    loadANDGoals(dungeon, jsonSubgoals.getJSONObject(i), goal);
                }
                break;
            case "OR":
                JSONArray jsonSubgoals2 = json.getJSONArray("subgoals");
                goal = new ORGoal (dungeon);
                for (int i = 0; i < jsonSubgoals2.length(); i++) {
                    loadORGoals(dungeon, jsonSubgoals2.getJSONObject(i), goal);
                }
        }
        dungeon.addGoal(goal);
    }
    private void loadANDGoals(Dungeon dungeon, JSONObject json, Goal dest) {
        String type = json.getString("goal");
        ANDGoal andgoal = (ANDGoal) dest;
        Goal goal = null;
        switch (type) {
            case "exit":
                goal = new ExitGoal(dungeon);
                break;
            case "boulders":
                goal = new BoulderGoal(dungeon);
                break;
            case "treasure":
                goal = new TreasureGoal(dungeon);
                break;
            case "enemies":
                goal = new EnemiesGoal(dungeon);
                break;
            case "AND":
                JSONArray jsonSubgoals = json.getJSONArray("subgoals");
                goal = new ANDGoal (dungeon);
                for (int i = 0; i < jsonSubgoals.length(); i++) {
                    loadANDGoals(dungeon, jsonSubgoals.getJSONObject(i), goal);
                }
                break;
            case "OR":
                JSONArray jsonSubgoals2 = json.getJSONArray("subgoals");
                goal = new ORGoal (dungeon);
                for (int i = 0; i < jsonSubgoals2.length(); i++) {
                    loadORGoals(dungeon, jsonSubgoals2.getJSONObject(i), goal);
                }
        }
        andgoal.addGoal(goal);
        dungeon.addAllgoal(goal);
    }

    private void loadORGoals(Dungeon dungeon, JSONObject json, Goal dest) {
        String type = json.getString("goal");
        ORGoal orgoal = (ORGoal) dest;
        Goal goal = null;
        switch (type) {
            case "exit":
                goal = new ExitGoal(dungeon);
                break;
            case "boulders":
                goal = new BoulderGoal(dungeon);
                break;
            case "treasure":
                goal = new TreasureGoal(dungeon);
                break;
            case "enemies":
                goal = new EnemiesGoal(dungeon);
                break;
            case "AND":
                JSONArray jsonSubgoals = json.getJSONArray("subgoals");
                goal = new ANDGoal (dungeon);
                for (int i = 0; i < jsonSubgoals.length(); i++) {
                    loadANDGoals(dungeon, jsonSubgoals.getJSONObject(i), goal);
                }
                break;
            case "OR":
                JSONArray jsonSubgoals2 = json.getJSONArray("subgoals");
                goal = new ORGoal (dungeon);
                for (int i = 0; i < jsonSubgoals2.length(); i++) {
                    loadORGoals(dungeon, jsonSubgoals2.getJSONObject(i), goal);
                }
        }
        orgoal.addGoal(goal);
        dungeon.addAllgoal(goal);
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
            case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            for (Goal g : dungeon.getAllgoals()) {
                if (g instanceof ExitGoal) exit.setGoal(g);
            }
            onLoad(exit);
            entity = exit;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            FloorSwitch plate = new FloorSwitch(x, y);
            for (Goal g : dungeon.getAllgoals()) {
                if (g instanceof BoulderGoal) {
                    
                    plate.setGoal(g);
                }
            }
            onLoad(plate);
            entity = plate;
            break;
        case "portal":
            int portalid = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, portalid);
            onLoad(portal);
            entity = portal;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            for (Goal g : dungeon.getAllgoals()) {
                if (g instanceof TreasureGoal) {
                    
                    TreasureGoal t = (TreasureGoal) g;
                    treasure.setGoal(t);
                }
            }
            onLoad(treasure);
            entity = treasure;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "door":
            int doorid = json.getInt("id");
            Door door = new Door(x, y, doorid);
            onLoad(door);
            entity = door;
            break;
        case "key":
            int keyid = json.getInt("id");
            Key key = new Key(dungeon, x, y, keyid);
            onLoad(key);
            entity = key;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "invincibility":
            InvinciblePotion potion = new InvinciblePotion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "health":
            HealthPotion healthPotion = new HealthPotion(dungeon, x, y);
            onLoad(healthPotion);
            entity = healthPotion;
            break;
        case "hound":
            Hound hound = new Hound(dungeon, x, y);
            onLoad(hound);
            entity = hound;
            break;
        case "gnome":
            Gnome gnome = new Gnome(dungeon, x, y);
            onLoad(gnome);
            entity = gnome;
            break;
            // TODO Handle other possible entities
        }
        dungeon.addEntity(entity);
    }

    /*public Door openDoor(int x, int y) {
        //Door open = new Door(dungeon);
    }*/
    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(FloorSwitch plate);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Enemy enemy);

    public abstract void onLoad(InvinciblePotion potion);

    public abstract void onLoad(HealthPotion potion);

    public abstract void onLoad(Hound hound);

    public abstract void onLoad(Gnome gnome);

    // TODO Create additional abstract methods for the other entities

}
