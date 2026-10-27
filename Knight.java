public class Knight extends Piece {
    private int value = 3;
    
    public int getValue() {
        return value;
    }
    
    public Knight(int row, int col, String color) {
        super(row, col, color, "Knight");
    }

    public void calculateMoves() {
        int[][] moves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] m : moves) {
            addMove(row + m[0], col + m[1]);
            addCapture(row + m[0], col + m[1]);
        }
    }
}
