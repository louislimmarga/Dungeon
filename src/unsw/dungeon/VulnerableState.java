package unsw.dungeon;

public class VulnerableState implements PlayerState{
    Player player;

    public VulnerableState (Player player) {
        this.player = player;
    }

    @Override
    public void drink() {
        player.setState(new InvincibleState(player));
    }

    @Override
    public void kill(Enemy e) {
        player.die();
        System.out.println("You are dead");
    }

    @Override
    public void move() {
        return; 
    }
    
}