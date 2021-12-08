package unsw.dungeon;

import java.util.List;

public class RunAwayState implements EnemyState{
    private Enemy enemy;
    private Dungeon dungeon;

    public RunAwayState (Dungeon dungeon, Enemy enemy) {
        this.enemy = enemy;
        this.dungeon = dungeon;
    }
    @Override
    public void runAway(Player player) {
        // moving 1 tile away from the player
        AStar as = new AStar(dungeon, enemy.getX(), enemy.getY());;
        List<AStar.Node> path = as.findPathTo(player.getX(), player.getY());
        
        /*System.out.println("dungeon: [" + dungeon.getWidth() + ", " + dungeon.getHeight() + "]");
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] ");
            });
            System.out.printf("\nTotal cost: %.02f\n", path.get(path.size() - 1).g);
        }*/
         
        AStar.Node next = path.get(1);
        //System.out.println("player: [" + player.getX() + ", " + player.getY() + "]");
        //System.out.println("enemy: [" + enemy.getX() + ", " +enemy.getY() + "]");
        //System.out.println("next: [" + next.x + ", " + next.y + "]");
        if (enemy.getX() + 1 == next.x && enemy.checkConditions(next.x, enemy.getY())) {
            enemy.moveLeft();
        } else if (enemy.getX() - 1 == next.x && enemy.checkConditions(next.x, enemy.getY())) {
            enemy.moveRight();
        } else if (enemy.getY() - 1 == next.y && enemy.checkConditions(enemy.getX(), next.y)) {
            enemy.moveDown();
        } else if (enemy.getY() + 1 == next.y && enemy.checkConditions(enemy.getX(), next.y)) {
            enemy.moveUp();
        }
    }
    @Override
    public void chase(Player player) {
        // moving 1 tile closer to player
        enemy.setState(new AttackState(dungeon,enemy));
        enemy.chase(player);
    }
}