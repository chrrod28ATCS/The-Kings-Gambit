public class King extends Piece {
    public King(int row, int col, String color) {
        super(row, col, color, "King");
    }

    public void calculateMoves() {
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };
        for (int[] d : directions) {
            int newRow = row + d[0];
            int newCol = col + d[1];
            if (world.inBounds(newRow, newCol)) {
                addMove(newRow, newCol);
                addCapture(newRow, newCol);
            }
        }
        castling();
    }

    public void castling() {
        if (!moved()) {
            // King-side castling
            if (world.inBounds(row, col + 3)) {
                Piece rook1 = world.getPiece(row, col + 3);
                if (rook1 != null && !rook1.moved() && "Rook".equals(rook1.getType())) {
                    if (world.getPiece(row, col + 1) == null &&
                        world.getPiece(row, col + 2) == null) {
                        addMove(row, col + 2);
                    }
                }
            }

            // Queen-side castling
            if (world.inBounds(row, col - 4)) {
                Piece rook2 = world.getPiece(row, col - 4);
                if (rook2 != null && !rook2.moved() && "Rook".equals(rook2.getType())) {
                    if (world.getPiece(row, col - 1) == null &&
                        world.getPiece(row, col - 2) == null &&
                        world.getPiece(row, col - 3) == null) {
                        addMove(row, col - 2);
                    }
                }
            }
        }
    }
}
