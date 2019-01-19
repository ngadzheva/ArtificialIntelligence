package tictactoe;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author ngadzheva
 */
public class Board {
    
    private Player[][] board;
    private final int BOARD_SIZE;
    private Player playersTurn;
    private Player winner;
    private int movesCount;
    private boolean isGameOver;
    private HashSet<Integer> movesAvailable;

    public Board() {
        BOARD_SIZE = 3;
        playersTurn = Player.NONE;
        winner = Player.NONE;
        movesCount = 0;
        isGameOver = false;
        movesAvailable = new HashSet<>();
        initializeBoard();
    }

    /**
     * Get player's turn 
     * 
     * @return playersTurn
     */
    public Player getPlayersTurn() {
        return playersTurn;
    }

    /**
     * Set player's turn
     * 
     * @param playersTurn 
     */
    public void setPlayersTurn(Player playersTurn) {
        this.playersTurn = playersTurn;
    }

    /**
     * Get available moves
     * 
     * @return set with available moves
     */
    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    /**
     * Get the winner player
     * 
     * @return winner 
     */
    public Player getWinner() {
        return winner;
    }
    
    /**
     * Make a copy of the board 
     * 
     * @return the copy of the board
     */
    public Board copy(){
        Board board = new Board();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board.board[i][j] = this.board[i][j];
            }
        }

        board.playersTurn = this.playersTurn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.movesCount = this.movesCount;
        board.isGameOver = this.isGameOver;
        
        return board;
    }

    /**
     * Initialize the board with empty spaces
     */
    private void initializeBoard(){
        board = new Player[BOARD_SIZE][BOARD_SIZE];
        
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                board[i][j] = Player.NONE;
               
            }
        }
        
        movesAvailable.clear();

        int size = BOARD_SIZE*BOARD_SIZE;
        
        for (int i = 0; i < size; i++) {
            movesAvailable.add(i);
        }
    }
    
    /**
     * Map move
     * 
     * @param index - position on the board [0-8]
     * @return the mapped row and column on the board
     */
    public boolean move (int index) {
        return makeMove(index / BOARD_SIZE, index % BOARD_SIZE);
    }
    
    /**
     * Make player's move
     * 
     * @param row - the index of the row to make a move
     * @param col - the index of the column to make a move
     * @return true if a move is made
     * else return false
     */
    public boolean makeMove(int row, int col){
        if(board[row][col] != Player.NONE){
            System.out.println("This place is not empty!");
            
            return false;
        }
        
        board[row][col] = playersTurn;
        ++movesCount;
        movesAvailable.remove(row * BOARD_SIZE + col);

        isGameOver = isWinner();
        
        playersTurn = Player.X_PLAYER == playersTurn ? Player.O_PLAYER : Player.X_PLAYER;
        
        return true;
    } 
    
    /**
     * Check whether the game is over
     * 
     * @return true if there is a winner
     * else return false
     */
    public boolean isGameOver(){        
        return isGameOver;
    }
    
    /**
     * Find whether there is a winner
     * 
     * @return true if there is a winner
     * else return false
     */
    private boolean isWinner(){
        for (int i = 0; i < BOARD_SIZE; ++i) {
            /**
             * Column winner
             */
            if(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != Player.NONE){
                winner = board[0][i];
                return true;
            } 
            
            /**
             * Row winner
             */
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != Player.NONE){
                winner = board[i][0];
                return true;
            } 
        }
        
        /**
         * Main diagonal winner
         */
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != Player.NONE){
            winner = board[0][0];
            return true;
        }
        
        /**
         * Second diagonal winner
         */
        if(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != Player.NONE){
            winner = board[0][2];
            return true;
        }
        
        /**
         * Draw
         */
        if(movesCount == BOARD_SIZE * BOARD_SIZE && winner == Player.NONE){
            return true;
        }
        
        /**
         * None
         */
        return false;
    }
    
    /**
     * Get the symbol of the winner player
     * 
     * @param player - the player, who is the winner
     * @return the symbol of the winner player
     */
    public char getPlayerSymbol(Player player){
        if(player == Player.NONE){
            return Player.getNone();
        }
        
        return player == Player.X_PLAYER ? Player.getX() : Player.getO();
    }
    
    /**
     * Print the board
     */
    public void printBoard(){
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(getPlayerSymbol(board[i][j]) + " ");
            }
            
            System.out.println("");
        }
        
        System.out.println("");
    }
}
