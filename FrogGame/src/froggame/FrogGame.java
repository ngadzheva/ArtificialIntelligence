package froggame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Frog game with StringBuilder
 * @author ngadzheva
 */
public class FrogGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        makeInitialState();
    }
    
    /**
     * Retrieve user input
     * Make the initial state
     * Start search
     */
    private static void makeInitialState(){
        System.out.print("Enter the number of frogs: ");
        Scanner input = new Scanner(System.in);
        int frogsCount = input.nextInt();
        input.close();
        
        int emptyPosition = frogsCount;
        int length = 2 * frogsCount + 1;
        /*StringBuilder initialState = new StringBuilder(String.join("", Collections.nCopies(frogsCount, ">")) + 
                "_" + String.join("", Collections.nCopies(frogsCount, "<")));*/
        char[] initialState = new char[length];
        for (int i = 0; i < frogsCount; ++i) {
            initialState[i] = '>';
        }
        
        initialState[frogsCount] = '_';
        
        for (int i = frogsCount + 1; i < length; ++i) {
            initialState[i] = '<';
        }
        
        dfs(initialState, emptyPosition, initialState.length);
    }
    
    /**
     * Implementation of DFS
     * @param frogs - the initial state
     * @param emptyPosition - the index of the empty position
     * @param length - the number of all frogs + empty position 
     */
    private static void dfs(char[] frogs, int emptyPosition, int length){
        /**
         * Stack which stores next possible moves
         */
        Stack<char[]> moves = new Stack<>();
        
        /**
         * List which stores the visited moves
         */
        List<char[]> visited = new ArrayList<>();
        
        /**
         * List which stores the correct moves
         */
        List<char[]> result = new ArrayList<>();
        
        /**
         * Add the initial state to the list with correct moves
         */
        result.add(frogs);
        
        /**
         * Make next possible moves
         */
        makeMoves(frogs, moves, emptyPosition, length);
        
        /**
         * While there are available moves in the stack
         * get the move on the top of the stack,
         * check whether it is the wanted move
         * If the current move is the wanted move,
         * add it to the list with the correct moves, break and print the result
         * Else make next possible moves from the current moves
         * If there are such moves,
         * add them to the stack with possible moves
         */
        while(!moves.isEmpty()){
            char[] currentMove = moves.peek();
            
            if(visited.contains(currentMove)){
                moves.pop();
                
                if(result.contains(currentMove)){
                    result.remove(currentMove);
                }
            } else {
                visited.add(currentMove);
                emptyPosition = getEmptyPosition(currentMove, length);

                if(emptyPosition == currentMove.length / 2 &&
                        isAllLeftRight(currentMove, emptyPosition)){
                    result.add(currentMove);
                    break;
                }

                int currentSize = moves.size();

                makeMoves(currentMove, moves, emptyPosition, length);

                if(moves.size() > currentSize){
                    result.add(currentMove);
                } else {
                    moves.pop();
                }
            }
            
        }
        
        printResult(result);
    }
    
    /**
     * Make next possible move
     * @param frogs - the current move
     * @param moves - the stack with the possible moves
     * @param emptyPosition - the index of the empty position
     * @param length - the number of all frogs + the empty position
     */
    private static void makeMoves(char[] frogs, Stack<char[]> moves, int emptyPosition, int length){
        /**
         * emptyPosition + 2
         * right frog jumps over one frog
         */
        if(emptyPosition + 2 < length && frogs[emptyPosition + 2] == '<'){
            char[] current = makeCopy(frogs, length);
            current[emptyPosition + 2] =  '_';
            current[emptyPosition] = '<';
            
            moves.add(current);
        }
        
        /**
         * emptyPosition + 1
         * right frog jumps on the empty position
         */
        if(emptyPosition + 1 < length && frogs[emptyPosition + 1] == '<'){
            char[] current = makeCopy(frogs, length);
            current[emptyPosition + 1] = '_';
            current[emptyPosition] = '<';
            
            moves.add(current);
        }

        /**
         * emptyPosition - 1
         * left frog jumps over one frog
         */
        if(emptyPosition - 1 >= 0 && frogs[emptyPosition - 1] == '>'){
            char[] current = makeCopy(frogs, length);
            current[emptyPosition - 1] = '_';
            current[emptyPosition] = '>';
            
            moves.add(current);
        }
        
        /**
         * emptyPosition - 2
         * left frog jumps on the empty position
         */
        if(emptyPosition - 2 >= 0 && frogs[emptyPosition - 2] == '>'){
            char[] current = makeCopy(frogs, length);
            current[emptyPosition - 2] = '_';
            current[emptyPosition] = '>';
            
            moves.add(current);
        }
        
    }
    
    /**
     * Make copy of the current move
     * @param move - the current move
     * @param length - the number of frogs and the empty position
     * @return copy of the current move
     */
    private static char[] makeCopy(char[] move, int length){
        char[] newMove = new char[length];
        
        for (int i = 0; i < length; ++i) {
            newMove[i] = move[i];
        }
        
        return newMove;
    }
    
    /**
     * Get the index of the empty position
     * @param move - current move
     * @return the index of the empty position
     */
    private static int getEmptyPosition(char[] move, int length){
        int index = 0;
        
        for (int i = 0; i < length; ++i) {
            if(move[i] == '_'){
                return i;
            }
        }
        return index;
    }
    
    /**
     * Print the correct moves
     * @param result - the list with the correct moves
     */
    private static void printResult(List<char[]> result){
        for (char[] res : result) {
            System.out.println(new String(res));
        }
    }
    
    /**
     * Check whether all left frogs are right frogs
     * @param move - current move
     * @param length - the number of all frogs + the empty position
     * @return true if the frogs to the right are ordered properly, else
     * return false
     */
    private static boolean isAllLeftRight(char[] move, int emptyPosition){        
        for (int i = 0; i < emptyPosition; ++i) {
            if(move[i] != '<'){
                return false;
            }
        }
        
        return true;
    }
}
