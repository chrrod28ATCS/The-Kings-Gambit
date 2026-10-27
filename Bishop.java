
public class Bishop extends Piece {
    private int value = 3;
    
    public int getValue() {
        return value;
    }
    
    public Bishop(int row, int col, String color) {
        super(row, col, color, "Bishop");
    }

    public void calculateMoves() {
        for (int i = 1; i < 8; i++) {
            if (!tryAdd(row + i, col + i)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (!tryAdd(row + i, col - i)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (!tryAdd(row - i, col + i)) break;
        }
        for (int i = 1; i < 8; i++) {
            if (!tryAdd(row - i, col - i)) break;
        }
    }
}