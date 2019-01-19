package tictactoe;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ngadzheva
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        AlphaBetaPruning play = new AlphaBetaPruning();
        Scanner input = new Scanner(System.in);
        
        System.out.println("Select player (X or O): ");
        String player = input.nextLine();
        Player human = player.equals("X") ? Player.X_PLAYER : Player.O_PLAYER;
        board.setPlayersTurn(human);
        System.out.println("");
        
        System.out.println("Select who is first (X or O): ");
        String first = input.nextLine();
        Random random = new Random();
        if(!first.equals(player)){
            board.setPlayersTurn(human == Player.X_PLAYER ? Player.O_PLAYER : Player.X_PLAYER);
            board.makeMove(random.nextInt(3), random.nextInt(3));
        }
        
        do{
            board.printBoard();
            
            System.out.println("Your turn: ");
            String[] userTurn = input.nextLine().split(" ");
            boolean moveMade = board.makeMove(Integer.parseInt(userTurn[0]), Integer.parseInt(userTurn[1]));
            board.printBoard();
            
            while(!moveMade){
                System.out.println("Your turn: ");
                userTurn = input.nextLine().split(" ");
                moveMade = board.makeMove(Integer.parseInt(userTurn[0]), Integer.parseInt(userTurn[1]));
                board.printBoard();
            }
            
            System.out.println("Opponent turn: ");
            play.start(board, board.getPlayersTurn(), 0);

        }while(!board.isGameOver());
        
        board.printBoard();
        
        if(board.getPlayerSymbol(board.getWinner()) == '_'){
            System.out.println("It's a draw!");
        } else {
            System.out.printf("%s is the winner! %n", board.getPlayerSymbol(board.getWinner()));
        }
    }
    
}
