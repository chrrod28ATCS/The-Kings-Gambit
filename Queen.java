public class Queen extends Piece {
    private int value = 9;
    
    public int getValue() {
        return value;
    }
    
    public Queen(int row, int col, String color) {
        super(row, col, color, "Queen");
    }

    public void calculateMoves() {
         for (int r = row + 1; r < 8; r++) {
            if (!tryAdd(r, col)) break;
        }
        for (int r = row - 1; r >= 0; r--) {
            if (!tryAdd(r, col)) break;
        }
        for (int c = col + 1; c < 8; c++) {
            if (!tryAdd(row, c)) break;
        }
        for (int c = col - 1; c >= 0; c--) {
            if (!tryAdd(row, c)) break;
        }
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
