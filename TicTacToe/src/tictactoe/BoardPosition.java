package tictactoe;

/**
 *
 * @author ngadzheva
 */
public class BoardPosition {
    
    private int row;
    private int col;

    public BoardPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the value of col
     *
     * @return the value of col
     */
    public int getCol() {
        return col;
    }

    /**
     * Set the value of col
     *
     * @param col new value of col
     */
    public void setCol(int col) {
        this.col = col;
    }


    /**
     * Get the value of row
     *
     * @return the value of row
     */
    public int getRow() {
        return row;
    }

    /**
     * Set the value of row
     *
     * @param row new value of row
     */
    public void setRow(int row) {
        this.row = row;
    }

}
