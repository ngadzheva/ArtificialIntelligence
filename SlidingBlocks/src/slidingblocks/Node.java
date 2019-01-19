package slidingblocks;

import java.util.Arrays;
import java.util.Objects;

/**
 * Description of possible state
 * @author ngadzheva
 */
public class Node {
    private int[][] state; // the current state
    private Node parent; // the previous state
    private int cost; // the total value (priority) of the current state
    private String direction; // the direction where the empty cell was moved
    private int path; // the path cost of reaching this node so far node

    public Node(int[][] state, Node parent, int cost, String direction, int path) {
        setState(state);
        setParent(parent);
        setCost(cost);
        setDirection(direction);
        setPath(path);
    }

    /**
     * Set the current state
     * @param state 
     */
    public void setState(int[][] state) {     
       this.state = state;
    }

    /**
     * Set the parent state of the current state
     * @param parent 
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Set the total cost of the current state
     * @param cost 
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Set the made step to reach the current state
     * @param direction 
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Set the cost of the path to the current state
     * @param path 
     */
    public void setPath(int path) {
        this.path = path;
    }

    /**
     * Get the current state
     * @return state
     */
    public int[][] getState() {
        return state;
    }

    /**
     * Get the parent state of the current state
     * @return parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Get the full cost of the current state
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Get the made step to reach the current state
     * @return direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Get the path cost to the current state
     * @return path
     */
    public int getPath() {
        return path;
    }

    /**
     * Generate a node's hash code
     * @return the node's hash code
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    /**
     * Check whether two Node objects are equal (i.e.their states are equal)
     * @param obj - node object to compare to the current node
     * @return true if the two nodes are equal
     * else return false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        
        if (!Arrays.deepEquals(this.state, other.state)) {
            return false;
        }
        
        return true;
    }
    
    
}
