package tictactoe;

/**
 *
 * @author ngazheva
 */
public enum Player {
    X_PLAYER,
    O_PLAYER,
    NONE;
    
    public static char getX(){
        return 'X';
    }
    
    public static char getO(){
        return 'O';
    }
    
    public static char getNone(){
        return '_';
    }
}
