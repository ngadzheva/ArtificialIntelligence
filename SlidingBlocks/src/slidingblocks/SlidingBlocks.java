package slidingblocks;

import java.util.Scanner;

/**
 * Solution of NxN puzzle
 * @author ngadzheva
 */
public class SlidingBlocks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        findSolution();
    }
    
    /**
     * Get user input and find solution of the inputted NxN puzzle
     */
    private static void findSolution(){
        Scanner input = new Scanner(System.in);
        
        int numberOfBlocks = getNumberOfBlocks(input);       
        int length = (int) Math.sqrt(numberOfBlocks + 1); 
        int[][] initialState = makeInitialState(input, length);    
        int[][] finalState = makeFinalState(length);
        
        AStar aStarSearch = new AStar(initialState, finalState, length);
        aStarSearch.findSolution();
        aStarSearch.printResult();
    }
    
    /**
     * Get user;s input for number of blocks
     * @param input - input stream
     * @return numberOfBlocks
     */
    private static int getNumberOfBlocks(Scanner input){
        System.out.println("Entenr number of blocks (3, 8, 15, 24, ...): ");
        
        int numberOfBlocks = input.nextInt();
        
        while ((numberOfBlocks < 3) || ((Math.sqrt(numberOfBlocks + 1) % 2 != 1.0) 
                && (Math.sqrt(numberOfBlocks + 1) % 2 != 0.0))) {
            System.out.println("Type proper number: ");
            numberOfBlocks = input.nextInt();
        }
        
        return numberOfBlocks;
    }
    
    /**
     * Get user's input for NxN puzzle => the initial state
     * @param input - input stream
     * @param length - the size of the puzzle board
     * @return initialState
     */
    private static int[][] makeInitialState(Scanner input, int length){
        int[][] initialState = new int[length][length];
        
        System.out.println("Enter the initial state: ");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.println("block[" + i + "][" + j + "]: ");
                initialState[i][j] = input.nextInt();   
            }
        }
        
        input.close();
        
        return initialState;
    }
    
    /**
     * Make the goal state
     * @param length - the size of the puzzle board
     * @return finalState
     */
    private static int[][] makeFinalState(int length){
        int[][] finalState = new int[length][length];
        int count = 1;
        
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                finalState[i][j] = count;
                
                ++count;
            }
        }
        
        finalState[length - 1][length - 1] = 0;
        
        return finalState;
    }
}
