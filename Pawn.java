import greenfoot.*;
import java.util.List;
public class Pawn extends Piece {
    private int value = 1;
    private boolean hasPromoted;
    private int promotedTo;
    public int getValue() {
        return value;
    }

    public Pawn(int row, int col, String color) {
        super(row, col, color, "Pawn");
    }
    public void act() {
        super.act();
        if (getColor().equals("W") && row == 7 || getColor().equals("B") && row == 0) {
            Piece p = promote();
            if (p != null) {
                world.removePiece(row, col);
                world.setPiece(p, p.row, p.col);
                world.addObject(p, 0, 0);
                world.removeObject(this);
                world.switchTurn();
                
            }
        }
        clearPromoteUI();
    }
    public void calculateMoves() {
        if (color.equals("W")) {
            if (isInBounds(row+1,col) && world.getPiece(row + 1, col) == null) {
                addMove(row + 1, col);
                if (row == 1 && world.getPiece(row + 2, col) == null) {
                    addMove(row + 2, col);
                }
            }
            addCapture(row + 1, col - 1);
            addCapture(row + 1, col + 1);
        } else {
            if (isInBounds(row-1, col) && world.getPiece(row - 1, col) == null) {
                addMove(row - 1, col);
                if (row == 6 && world.getPiece(row - 2, col) == null) {
                    addMove(row - 2, col);
                }
            }
            addCapture(row - 1, col - 1);
            addCapture(row - 1, col + 1);
        }
        enPassant();
    }
      public void moveTo(int newRow, int newCol) {
        int dir;
        if (color.equals("W"))
            dir = 1;
        else
            dir = -1;
        if (Math.abs(newCol - this.col) == 1 && newRow - this.row == dir) {
            Piece target = world.getPiece(newRow, newCol);
            if (target == null) {
                Piece last = world.getLastMovedPiece();
                if (last instanceof Pawn) {
                    int lastRow = last.getRow();
                    int lastCol = last.getCol();
                    if (lastRow == this.row && lastCol == newCol) {
                        Player capturer = world.getPlayer(this.color);
                        capturer.addCapturedPiece(last);
                        world.setPiece(null, lastRow, lastCol);
                        world.removeObject(last);
                    }
                }
            }
        }
        super.moveTo(newRow, newCol);
        if ((color.equals("W") && newRow == 7) || (color.equals("B") && newRow == 0)) {
            setPromoteUI();
        }
    }
    public Piece promote() {
        Piece promoted = null;
        if (!world.getCurrentTurn().equals(this.color)) {
            world.switchTurn();
        }
        List<PromoteUI> promoteStuff = world.getObjects(PromoteUI.class);
        for (PromoteUI current : promoteStuff) {
            promotedTo = current.handleClick();
            if (promotedTo > 0) {
                int currentRow = row;
                int currentCol = col;
                String currentColor = color;
                hasPromoted = true;
                if (promotedTo == 1) {
                    promoted = new Queen(currentRow, currentCol, currentColor);
                } else if (promotedTo == 2) {
                    promoted = new Rook(currentRow, currentCol, currentColor);
                } else if (promotedTo == 3) {
                    promoted = new Bishop(currentRow, currentCol, currentColor);
                } else {
                    promoted = new Knight(currentRow, currentCol, currentColor);
                }
            } 
        }
        return promoted;
    }
    public void setPromoteUI() {
        world.addObject(new QueenPromote(), 322, 40);
        world.addObject(new RookPromote(), 474, 40);
        world.addObject(new BishopPromote(), 626, 40);
        world.addObject(new KnightPromote(), 778, 40);
    }
    public void clearPromoteUI() {
        if (hasPromoted) {
            world.removeObjects(world.getObjects(PromoteUI.class));
        }
    }
     public void enPassant() {
         // The last pawn that moved
        Piece last = world.getLastMovedPiece();
        if (!(last instanceof Pawn)) {
            return;
        }
        Pawn sidePawn = (Pawn) last;
        int sideRow = sidePawn.row;   // its current row
        int sideCol = sidePawn.col;   // its current column
        // Must be sitting on the same rank and adjacent file
        if (sideRow == this.row && Math.abs(sideCol - this.col) == 1) {
            // And that pawn must have just made its two-square first move
            if (sidePawn.getLastFirstMove()) {
                // Compute the landing square: one step diagonally forward
                int dir = (this.color.equals("W") ? +1 : -1);
                addMove(this.row + dir, sideCol);
            }
        }
    }
    public boolean getLastFirstMove() {
        if (world.getLastMovedPiece() != null) { 
            if (this == world.getLastMovedPiece()) {
                if (this.color.equals("W")) {
                    if (this.row == world.getLastRowMoved() + 2) {
                        return true;
                    }
                } else {
                    if (this.row == world.getLastRowMoved() - 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

