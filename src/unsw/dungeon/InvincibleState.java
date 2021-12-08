package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InvincibleState implements PlayerState{
    private Player player;
    private IntegerProperty duration;

    public InvincibleState (Player player) {
        this.player = player;
        this.duration = new SimpleIntegerProperty(30);
    }

    @Override
    public void drink() {
        System.out.println("Invincible in duration");

    }

    @Override
    public void kill(Enemy enemy) {
        System.out.println("You killed an enemy!!!");
        //System.out.println("CC");
        player.detach(enemy);
        enemy.die();
    }
    
    @Override
    public void move() {
        duration.set(getDuration() - 1);
        if (getDuration() < 1) {
            player.setState(new VulnerableState(player));
            System.out.println("You're no longer invincible");
        }
    } 

    public IntegerProperty duration() {
        return duration;
    }

    public int getDuration() {
        return duration().get();
    }
    
}