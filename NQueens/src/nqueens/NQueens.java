package nqueens;

import java.util.Scanner;

/**
 * NQueens problem solution
 * @author ngadzheva
 */
public class NQueens {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of queens: ");
        int numOfQueens = input.nextInt();
        input.close();
        
        Board board = new Board(numOfQueens);
        board.minConflicts();  
    }
}
