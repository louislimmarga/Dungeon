package unsw.dungeon;

import java.util.List;

public class Portal extends Entity {
    private int id;
    Dungeon dungeon;
    private Portal portal;

    public Portal(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.id = id;
        this.dungeon = dungeon;
        findPortal();
    }

    public int getId() {
        return this.id;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public void findPortal() {
        for (Entity e : dungeon.getEntity()) {
            if ( !(e instanceof Portal)) continue;
            Portal portal = (Portal) e;
            if (portal.getId() == this.id) {
                setPortal(portal);
                portal.setPortal(this);
            }
        }
    }

    public void teleport(Entity entity) {
        dungeon.removeFromMap(entity.getX(), entity.getY());
        entity.x().set(portal.getX());
        entity.y().set(portal.getY());
        dungeon.addToMap(entity, portal.getX(), portal.getY());
    }

}