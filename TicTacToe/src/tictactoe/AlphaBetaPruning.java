package tictactoe;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author ngadzheva
 */
public class AlphaBetaPruning {
    private final int NEGATIVE_INFINITY;
    private final int POSITIVE_INFINITY;
    private int currentDepth;

    public AlphaBetaPruning() {
        NEGATIVE_INFINITY = Integer.MIN_VALUE;
        POSITIVE_INFINITY = Integer.MAX_VALUE;
        currentDepth = 0;
    }

    /**
     * Alpha beta pruning algorithm implementation
     * 
     * @param board - board field
     * @param player - player's turn
     * @param currentDepth - current depth of tree search
     * @return the score of the winner
     */
    public int start(Board board, Player player, int currentDepth){   
        currentDepth++;
        if(board.isGameOver()){
            return score(board, player, currentDepth);
        }
        
        if(player == board.getPlayersTurn()){
            return getMax(board, player, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        } else {
            return getMin(board, player, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        }
    }
    
    /**
     * 
     * @param board - board field
     * @param player - player's turn
     * @param alpha - maximizing value
     * @param beta - minimizing value
     * @param currentDepth - current depth of the search tree
     * @return the the value of alpha
     */
    private int getMax(Board board, Player player, int alpha, int beta, int currentDepth){        
        int indexOfBestMove = -1;
        
        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.move(move);
            int score = start(modifiedBoard, player, currentDepth);
            
            if(score > alpha){
                alpha = score;
                indexOfBestMove = move;
            }
            
            if(alpha >= beta){
                break;
            }
            
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return alpha;
    }
    
    /**
     * 
     * @param board - board field
     * @param player - player's turn
     * @param alpha - maximizing value
     * @param beta - minimizing value
     * @param currentDepth - current depth of the search tree
     * @return beta value
     */
    private int getMin(Board board, Player player, int alpha, int beta, int currentDepth){
        int indexOfBestMove = -1;
        
        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.move(move);
            int score = start(modifiedBoard, player, currentDepth);
            
            if(score < beta){
                beta = score;
                indexOfBestMove = move;
            }
            
            if(alpha >= beta){
                break;
            }
        }
        
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return beta;
    }
    
    /**
     * Get the score of the winner
     * 
     * @param board - board field
     * @param player - player's turn
     * @param currentDepth - current depth of the search tree
     * @return the score of the winner
     */
    private int score(Board board, Player player, int currentDepth){
        Player opponent = (player == Player.X_PLAYER) ? Player.O_PLAYER : Player.X_PLAYER;
        
        if(board.getWinner() == player){
            return 10 - currentDepth;
        } else if(board.getWinner() == opponent){
            return -10 + currentDepth;
        } else{
            return 0;
        }
    }
}
