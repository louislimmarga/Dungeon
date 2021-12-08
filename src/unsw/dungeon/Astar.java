package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 
//  This class code snippet from https://rosettacode.org/wiki/A*_search_algorithm#Java with tweak with dungeon.
class AStar {
    private final List<Node> open;
    private final List<Node> closed;
    private final Dungeon dungeon;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
 
    // Node class for convienience
    static class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;
        Node(Node parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
       }
       // Compare by f value (g + h)
       @Override
       public int compareTo(Object o) {
           Node that = (Node) o;
           return (int)((this.g + this.h) - (that.g + that.h));
       }
   }
 
    AStar(Dungeon dungeon, int xstart, int ystart) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.dungeon = dungeon;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
    }
    /*
    ** Finds path to xend/yend or returns null
    **
    ** @param (int) xend coordinates of the target position
    ** @param (int) yend
    ** @return (List<Node> | null) the path
    */
    public List<Node> findPathTo(int xend, int yend) {
        List<Node> path = new ArrayList<>();
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
        while (this.now.x != this.xend || this.now.y != this.yend) {
            if (this.open.isEmpty()) { // Nothing to examine
                return null;
            }
            this.now = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.now); // and add to the closed
            addNeigborsToOpenList();
        }
        path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            path.add(0, this.now);
        }
        return path;
    }
    /*
    ** Looks in a given List<> for a node
    **
    ** @return (bool) NeightborInListFound
    */
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    /*
    ** Calulate distance between this.now and xend/yend
    **
    ** @return (int) distance
    */
    private double distance(int dx, int dy) {
        // Apply "Manhattan distance"
        return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); 
    }
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 && y != 0) {
                    //System.out.print("[" + x + ", " + y + "] ");
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) // not this.now
                    && this.now.x + x >= 0 && this.now.x + x < this.dungeon.getWidth() // check maze boundaries
                    && this.now.y + y >= 0 && this.now.y + y< this.dungeon.getHeight() 
                    && checkConditions((this.now.x + x),(this.now.y + y)) // check if square is walkable
                    && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // if not already done
                        node.g = node.parent.g + 1; // Horizontal/vertical cost = 1.0
 
                        this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }
    
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
    /*public static void main(String[] args) {
        // -1 = blocked
        // 0+ = additional movement cost
        int[][] maze = {
            {  0,  0,  0,  0,  0,  0,  0,  0},
            {  0,  0,  0,  0,  0,  0,  0,  0},
            {  0,  0,  0,100,100,100,  0,  0},
            {  0,  0,  0,  0,  0,100,  0,  0},
            {  0,  0,100,  0,  0,100,  0,  0},
            {  0,  0,100,  0,  0,100,  0,  0},
            {  0,  0,100,100,100,100,  0,  0},
            {  0,  0,  0,  0,  0,  0,  0,  0},
        };
        AStar as = new AStar(maze, 0, 0, false);
        List<Node> path = as.findPathTo(7, 7);
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] ");
                maze[n.y][n.x] = -1;
            });
            System.out.printf("\nTotal cost: %.02f\n", path.get(path.size() - 1).g);
 
            for (int[] maze_row : maze) {
                for (int maze_entry : maze_row) {
                    switch (maze_entry) {
                        case 0:
                            System.out.print("_");
                            break;
                        case -1:
                            System.out.print("*");
                            break;
                        default:
                            System.out.print("#");
                    }
                }
                System.out.println();
            }
        }
    }*/
}