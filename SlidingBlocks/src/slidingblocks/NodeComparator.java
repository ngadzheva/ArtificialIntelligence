package slidingblocks;

import java.util.Comparator;

/**
 * Make comparator for two states
 * @author ngadzheva
 */
public class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node node1, Node node2) {
        return node1.getCost() - node2.getCost();
    }
    
}
