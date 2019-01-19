package slidingblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of A* algorithm for finding a solution
 * @author ngadzheva
 */
public class AStar {
    private Node currentState; // stores the current state node
    private final Node finalState; // stores the goal state node
    private final int size; // stores the size of the board
    private int level; // stores the path to the current state

    public AStar(int[][] startState, int[][] finalState, int size) {
        this.level = 0;
        this.currentState = new Node(startState, null, manhattanSum(startState), "", level);
        this.finalState = new Node(finalState, null, -1, "", -1);
        this.size = size;
    }
    
    /**
     * Implementation of A* algorithm for finding a solution
     */
    public void findSolution(){
        /**
         * Set with visited states
         */
        Set<Node> visited = new HashSet<Node>();
        
        /**
         * Priority queue with possible moves
         */
        PriorityQueue<Node> moves = new PriorityQueue<Node>(new NodeComparator());
        
        moves.add(currentState);
        
        /**
         * While the priority queue is not empty
         * get the state with highest priority
         * check whether it is the goal state
         * If it's not
         * add it to the visited states
         * For every child of the current state
         * check whether it has been already visited
         * If not
         * add it to the priority queue with all possible states
         */
        while(!moves.isEmpty()){
            currentState = moves.remove();
            level = currentState.getPath();
            
            if(isGoal()){
                break;
            } 
            
            visited.add(currentState);
            
            for (Node state : findStates()) {
                if(visited.contains(state)){
                    continue;
                } 
               
                if (!moves.contains(state)) {
                    moves.add(state);
                }
            }  
        }
    }
    
    /**
     * Make next possible states
     */
    private Node[] findStates(){
        int[] zeroPos = getZeroPosition();
        int row = zeroPos[0];
        int col = zeroPos[1];
        int[][] current = currentState.getState();
        
        ++level;
        
        Node up = moveUp(makeCopyState(current), row, col);
        Node down = moveDown(makeCopyState(current), row, col);
        Node left = moveLeft(makeCopyState(current), row, col);
        Node right = moveRight(makeCopyState(current), row, col);
        
        Node[] childStates = {up, down, left, right};
        
        return childStates;
    }
    
    /**
     * Move zero position down
     * @param current - current state
     * @param row - row of the zero position
     * @param col - col of the zero position
     */
    private Node moveUp(int[][] current, int row, int col){  
        if(row < size - 1){
            current[row][col] = current[row + 1][col];
            current[row + 1][col] = 0;
        }
        
        Node up = new Node(current, currentState, calculateFullCost(current), "up", level);
        
        return up;
    }
    
    /**
     * Move zero position up
     * @param current - current state
     * @param row - row of the zero position
     * @param col - col of the zero position
     */
    private Node moveDown(int[][] current, int row, int col){
        if (row > 0) {
            current[row][col] = current[row - 1][col];
            current[row - 1][col] = 0;
        }
        
        Node down = new Node(current, currentState, calculateFullCost(current), "down", level);
        
        return down;
    }
    
    /**
     * Move zero position to the right
     * @param current - current state
     * @param row - row of the zero position
     * @param col - col of the zero position
     */
    private Node moveLeft(int[][] current, int row, int col){
        if (col < size - 1) {
            current[row][col] = current[row][col + 1];
            current[row][col + 1] = 0;
        }
        
        Node left = new Node(current, currentState, calculateFullCost(current), "left", level);
        
        return left;
    }
    
    /**
     * Move zero position to the left
     * @param current - current position
     * @param row - row of the current position
     * @param col - col of the current position
     */
    private Node moveRight(int[][] current, int row, int col){
        if (col > 0) {
            current[row][col] = current[row][col - 1];
            current[row][col - 1] = 0;
        }
        
        Node right = new Node(current, currentState, calculateFullCost(current), "right", level);
        
        return right;
    }
    
    /**
     * Make copy of the current state
     * @param current - the current state
     */
    private int[][] makeCopyState(int[][] current){
        int[][] copyState = new int[size][size];
        
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                copyState[i][j] = current[i][j];
            }
        }
        
        return copyState;
    }

    /**
     * Find the coordinates of the empty position
     * @return the coordinates of the empty position
     */
    private int[] getZeroPosition(){
        int[] position = new int[2];
        
        int[][] current = currentState.getState();
        
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(current[i][j] == 0){
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        
        return position;
    }
    
    /**
     * Heuristic function evaluating the path to the current state
     * Implementation of Manhattan Distance
     * For every node of the current state calculate |Xg - Xc| + |Yg - Yc|
     * and sum the results, where 
     * Xg is the row of the current node's i value in the final node
     * Xc is the row of the current node's i value
     * Yg is the column of the current node's i value in the final node
     * Yc is the column of the current node's i value
     * 
     * @return the heuristic value
     */
    private int manhattanSum(int[][] state){
        int sum = 0;
        
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column) {
                int value = state[row][column];
                
                if(value == 0){
                    sum += Math.abs(size - 1 - row); // row difference
                    sum += Math.abs(size - 1 - column); // column difference
                } else {
                    sum += Math.abs(((value - 1) / size)  - row); // row difference
                    sum += Math.abs(((value - 1) % size) - column); // column difference
                }
            }
        }
        
        return sum;
    }
    
    /**
     * Evaluation function
     * Calculates the full cost of the path to the current state
     * @return the full cost of the path to the current state
     */
    private int calculateFullCost(int[][] state){
        int cost = level + manhattanSum(state);
        
        return cost;
    }
    
    /**
     * Check whether the current state is the goal state
     * @return true if the current state is the goal state
     * else return false
     */
    private boolean isGoal(){
        int[][] current = currentState.getState();
        int[][] goal = finalState.getState();
        
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(current[i][j] != goal[i][j]){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Print the number of states and the steps to the final state 
     */
    public void printResult(){
        System.out.println(currentState.getPath());

        Stack<String> result = new Stack<>();
        
        for (int i = 0; i < level; i++) {
            result.add(currentState.getDirection());
            currentState = currentState.getParent();
        }
        
        while(!result.isEmpty()){
            System.out.println(result.pop());
        }
    }
}
