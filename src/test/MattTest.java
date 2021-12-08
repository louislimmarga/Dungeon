package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;

public class MattTest {
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }
    
    @Test
    public void WallTest(){
        Dungeon d = new Dungeon(1, 2);
        assertEquals(d.getWidth(), 1);
        Player p = new Player(d, 1, 1);
        Wall w = new Wall(1, 2);
        d.setPlayer(p);
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }

    @Test
    public void ExitTest(){
        Dungeon d = new Dungeon(1, 2);
        assertEquals(d.getWidth(), 1);
        Player p = new Player(d, 1, 1);
        d.setPlayer(p);
        Exit e = new Exit(d, 1, 2);
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
        
    }

}

