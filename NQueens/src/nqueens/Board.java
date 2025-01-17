package nqueens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * Construction of the board and implementation of Min Conflicts algorithm
 * @author ngadzheva
 */
public class Board {
    private int size;
    private int[] queens;
    private int[] colConfl;
    private int[] rowConfl;
    private int[] diagConfl;
    private ArrayList<Integer> colWithMaxConfl;
    private ArrayList<Integer> rowWithMinConfl;
    private int maxConflicts;
    private Random random;
    private final int MAX_ITER;

    public Board(int size) {
        this.size = size;
        maxConflicts = 0;
        random = new Random();
        colWithMaxConfl = new ArrayList<>();
        rowWithMinConfl = new ArrayList<>();
        /**
         * Maximum number of iterations
         */
        MAX_ITER = 2 * size;
        
        initializeBoard();
    }

    /**
     * Get the value of rows
     *
     * @return the value of rows
     */
    public int[] getRows() {
        return queens;
    }

    /**
     * Set the value of rows
     *
     * @param rows new value of rows
     */
    public void setRows(int[] rows) {
        this.queens = rows;
    }


    /**
     * Get the value of size
     *
     * @return the value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the value of size
     *
     * @param size new value of size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Make initial state with randomly placed queens to the rows
     * The index i is the number of the column.
     * The value of the rows[i] is the number of the column
     * 
     * @param size - the size of the board (the number of rows/ columns)
     */
    private void initializeBoard(){
        queens = new int[size];
        colConfl = new int[size];
        rowConfl = new int[size];
        diagConfl = new int[2 * size];
        
        for (int i = 0; i < size; ++i) {
            colConfl[i] = -1;
            rowConfl[i] = -1;
            diagConfl[i] = -1;
        }
        
        for (int col = 0; col < size; ++col) {
            int row = random.nextInt(size);
            queens[col] = row;
            ++colConfl[col];
            ++rowConfl[row];
            
            if(col == row && diagConfl[col] == -1){
                for (int i = 0; i < size; ++i) {
                    ++diagConfl[i];
                }
            }
            
            if(col + row == size - 1 && diagConfl[col + size] == -1){
                for (int i = size; i < 2 * size; ++i) {
                    ++diagConfl[i];
                }
            }
        }
    }
    
    /**
     * Implementation of Min Conflicts algorithm
     */
    public void minConflicts(){
        int i = 0;
        
        /**
         * While current iteration is less than the max iteration
         * Get the queen with max conflicts (the col with max conflicts)
         * Find the row with min conflicts and move the queen there
         * If the max conflicts queen is with 0 conflicts, print the result
         * If the current iteration is greater than the max iteration
         * and there is still no solution, restart
         */
        while(i++ < MAX_ITER){ 
            conflicts();
            int col = colWithMaxConfl.get(random.nextInt(colWithMaxConfl.size()));//getColWithMaxConflicts();
            
            if(maxConflicts == 0){
                print();
                return;
            }
            
            int row = rowWithMinConfl.get(random.nextInt(rowWithMinConfl.size()));
            queens[col] = row;//getRowWithMinConflicts(col);
            
            --colConfl[col];
            ++rowConfl[row];
            
            if(col == row || col + row == size - 1){
                
            }
        }
        
        if(maxConflicts != 0){
            maxConflicts = 0;
            initializeBoard();
            minConflicts();
        }
    }
    
    private void conflicts(){
        int minConfl = (queens[0] != 0) ? (colConfl[0] + rowConfl[0] + diagConfl[0] - 1) : (colConfl[0] + rowConfl[0] - 1);
        int maxConfl = minConfl;
        
        rowWithMinConfl.add(0);
        colWithMaxConfl.add(0);
        
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; col++) {
                if(row == 0 && row == col){
                    continue;
                }
                
                int confl = colConfl[col] + rowConfl[row];
                
                if(queens[col] != row){
                    if(col == row){
                        confl += diagConfl[col] - 1;
                    }
                    
                    if(col + row == size - 1){
                        confl += diagConfl[col + size] - 1;
                    }     
                } else {
                    confl -= 1;    
                }
                
                
                if(confl < minConfl){
                    minConfl = confl;
                    rowWithMinConfl.clear();
                    rowWithMinConfl.add(row);
                }
                
                if(maxConfl < confl){
                    colWithMaxConfl.clear();
                    maxConfl = confl;
                    colWithMaxConfl.add(col);
                }
                
                if(minConfl == confl){
                    rowWithMinConfl.add(row);
                }
                
                if(maxConfl == confl){
                    colWithMaxConfl.add(col);
                }
            }
        }
    }
    
    /**
     * Return the number of queens that are in conflict with the queen on the
     * current position (row, col)
     * 
     * @param queenRow - the row index of the current queen
     * @param queenCol - the col index of the current queen
     * @return the number of conflicts
     */
    private int conflicts(int queenRow, int queenCol){
        int conflictsCount = 0;
        
        for (int col = 0; col < size; ++col) {
            if(col == queenCol){
                continue;
            }
            
            int row = queens[col];
            
            if(row == queenRow ||
                    Math.abs(row - queenRow) == Math.abs(col - queenCol)){
                ++conflictsCount;
            }
        }
        
        return conflictsCount;
    }
    
    /**
     * Get the index of the column with max conflicts 
     * 
     * @return maxConflictQueenIndex
     */
    private int getColWithMaxConflicts(){
        ArrayList<Integer> maxConflictsQueenIndex = new ArrayList<>();
        maxConflicts = 0;
        
        for (int col = 0; col < size; ++col) {
            int conflictsCount = conflicts(queens[col], col);
            
            if(conflictsCount == maxConflicts){
                maxConflictsQueenIndex.add(col);
            } else if(conflictsCount > maxConflicts){
                maxConflictsQueenIndex.clear();
                maxConflictsQueenIndex.add(col);
                maxConflicts = conflictsCount;
            }
        }
        
        return maxConflictsQueenIndex.get(random.nextInt(maxConflictsQueenIndex.size()));
    }
    
    /**
     * Get the row, in which the queen is in min conflicts with other queens 
     * 
     * @param queenCol - the column index of the current queen
     * @return the index of the row with min conflicts
     */
    private int getRowWithMinConflicts(int queenCol){
        ArrayList<Integer> minConflictsQueenRow = new ArrayList<>();
        int minConflicts = size;
        
        for (int row = 0; row < size; ++row) {
            int conflictsCount = conflicts(row, queenCol);
            if(conflictsCount == minConflicts){
                minConflictsQueenRow.add(row);
            } else if(conflictsCount < minConflicts){
                minConflicts = conflictsCount;
                minConflictsQueenRow.clear();
                minConflictsQueenRow.add(row);
            }
        }
        
        if(!minConflictsQueenRow.isEmpty()){
            return minConflictsQueenRow.get(random.nextInt(minConflictsQueenRow.size()));
        }
        
        return 0;
    }
    
    /**
     * Print the result
     */
    private void print(){
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                if(queens[col] == row){
                    System.out.print("* ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
}
