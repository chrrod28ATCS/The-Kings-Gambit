import greenfoot.*;
import java.util.*;

public abstract class Piece extends Actor {
    protected int row, col;
    protected String color, type;
    protected ArrayList<int[]> possibleMoves = new ArrayList<>();
    protected ChessWorld world;
    protected boolean isMoved = false;
    private static int WcapturedCount = 0;
    private static int BcapturedCount = 0;

    public void moveTo(int newRow, int newCol) {
        if (world == null) return;
        Piece target = world.getPiece(newRow, newCol);
        if (target != null && target.getColor() != null && target.getColor().equals(this.color)) {
            return;
        }
        if (target != null && !target.getColor().equals(this.color)) {
           if (world.isSimulating() == false) {
               Player capturer = world.getPlayer(this.color);
               capturer.addCapturedPiece(target);
                world.setPiece(null, newRow, newCol);
               world.removeObject(target);
           }
        }
        if (this instanceof Pawn && world.getLastMovedPiece() instanceof Pawn) {
        int dir = color.equals("W") ? 1 : -1;
        // Only attempt en passant if moving diagonally into an empty square
        if (newCol != col && target == null && Math.abs(newCol - col) == 1 && newRow - row == dir) {
            int capturedRow = newRow - dir;
            Piece captured = world.getPiece(capturedRow, newCol);
            if (captured instanceof Pawn && ((Pawn)captured).getLastFirstMove() && !world.isSimulating()) {
                Player capturer = world.getPlayer(this.color);
                capturer.addCapturedPiece(captured);
                world.setPiece(null, capturedRow, newCol);
                world.removeObject(captured);
                }
            }
        }   
        isCastling(newCol);
        world.setPiece(null, row, col);
        world.setLastRowMoved(row);
        world.setLastColMoved(col);
        row = newRow;
        col = newCol;
        world.setPiece(this, row, col);
        position();
        isMoved = true;
        world.setLastMovedPiece(this);
    }

    public Piece() {}

    public Piece(int row, int col, String color, String type) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.type = type;
        WcapturedCount = 0;
        BcapturedCount = 0;
        setImage(new GreenfootImage(color + "_" + type + ".png"));
        getImage().scale(getImage().getWidth() * 39 / 10, getImage().getHeight() * 39 / 10);
    }

    public void act() {
        if (!Greenfoot.mouseClicked(this)) return;
        if (!world.getCurrentTurn().equals(color)) return;
        possibleMoves.clear();
        calculateMoves();
        removeMoves(); 
        world.setSelectedPiece(this);
        world.showMarkers(possibleMoves);
        world.reorderPieces();
    }

    protected void addedToWorld(World w) {
        if (w instanceof ChessWorld) {
            world = (ChessWorld) w;
            world.setPiece(this, row, col);
            position();
        }
    }

    public void calculateMoves() {
       
    }

    public ArrayList<int[]> getMoves() {
        possibleMoves.clear();
        calculateMoves();
        return new ArrayList<>(possibleMoves); 
    }
    
    public void removeMoves() {
        if (type.equals("King")) {
            world.startSimulation();
            ArrayList<int[]> toRemove = new ArrayList<>();
            List<int[]> originalMoves = new ArrayList<>(possibleMoves);
            for (int[] move : originalMoves) {
                int originalRow = row, originalCol = col;
                Piece captured = world.getPiece(move[0], move[1]);
                world.setPiece(null, originalRow, originalCol);
                world.setPiece(this, move[0], move[1]);
                row = move[0];
                col = move[1];
                boolean kingInCheck = world.isInCheck();
                world.setPiece(this, originalRow, originalCol);
                world.setPiece(captured, move[0], move[1]);
                row = originalRow;
                col = originalCol;
                if (kingInCheck) toRemove.add(move);
            }
            possibleMoves.removeAll(toRemove);
            world.stopSimulation();
            return;
        }
        ArrayList<Piece> checkers = world.getCheckers();
        if (checkers.isEmpty()) {
        if (!type.equals("King")) {
            world.startSimulation();
            if (isPinned()) {
                    filterMovesAlongPinRay();
            }
            world.stopSimulation();
        }
        return;
        }
        if (checkers.size() > 1) {
            possibleMoves.clear();
            return;
        }
        Piece checker = checkers.get(0);
        int kingRow = -1;
        int kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = world.getPiece(r, c);
                if (p != null && p.getType().equals("King") && p.getColor().equals(color)) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }
        ArrayList<int[]> legalMoves = new ArrayList<>();
        for (int[] move : possibleMoves) {
            if (move[0] == checker.getRow() && move[1] == checker.getCol()) {
                legalMoves.add(move);
            }
        }
        if (checker.getType().equals("Queen") || checker.getType().equals("Rook") || checker.getType().equals("Bishop")) {
            int[] dir = getRay(checker.getRow(), checker.getCol(), kingRow, kingCol);
            int r = checker.getRow() + dir[0];
            int c = checker.getCol() + dir[1];
    
            while (r != kingRow || c != kingCol) {
                for (int[] move : possibleMoves) {
                    if (move[0] == r && move[1] == c) {
                        legalMoves.add(move);
                    }
                }
                r += dir[0];
                c += dir[1];
            }
        }
    
        possibleMoves = legalMoves;
    }
    
    protected boolean isPinned() {
        int kingRow = -1, kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = world.getPiece(r, c);
                if (p != null && p.getType().equals("King") && p.getColor().equals(color)) {
                    kingRow = r;
                    kingCol = c;
                }
            }
        }
        if (kingRow == -1 || kingCol == -1) return false;
        int[] dir = getRay(row, col, kingRow, kingCol);
        if (dir[0] == 0 && dir[1] == 0) return false;
        int r = row + dir[0], c = col + dir[1];
        while (isInBounds(r, c)) {
            Piece p = world.getPiece(r, c);
            if (r == kingRow && c == kingCol) break;
            if (p != null) return false;
            r += dir[0];
            c += dir[1];
        }
        r = row - dir[0];
        c = col - dir[1];
        while (isInBounds(r, c)) {
            Piece p = world.getPiece(r, c);
            if (p != null) {
                if (!p.getColor().equals(color)) {
                    String type = p.getType();
                    if ((Math.abs(dir[0]) + Math.abs(dir[1]) == 2 && (type.equals("Bishop") || type.equals("Queen"))) ||
                        (Math.abs(dir[0]) + Math.abs(dir[1]) == 1 && (type.equals("Rook") || type.equals("Queen")))) {
                        return true;
                    }
                }
                break;
            }
            r -= dir[0];
            c -= dir[1];
        }
        return false;
    }
    
    protected void filterMovesAlongPinRay() {
        int kingRow = -1, kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = world.getPiece(r, c);
                if (p != null && p.getType().equals("King") && p.getColor().equals(color)) {
                    kingRow = r;
                    kingCol = c;
                }
            }
        }
        int[] pinRay = getRay(kingRow, kingCol, row, col);
        if (pinRay[0] == 0 && pinRay[1] == 0) return;
        ArrayList<int[]> legalMoves = new ArrayList<>();
        for (int[] move : possibleMoves) {
            int dRow = move[0] - row;
            int dCol = move[1] - col;
            if (Integer.compare(dRow, 0) == pinRay[0] && Integer.compare(dCol, 0) == pinRay[1]) {
                legalMoves.add(move);
            }
        }
        possibleMoves = legalMoves;
    }
    
    protected int[] getRay(int fromRow, int fromCol, int toRow, int toCol) {
        int rowChange = Integer.compare(toRow - fromRow, 0);
        int colChange = Integer.compare(toCol - fromCol, 0);
        return new int[]{rowChange, colChange};
    } 
    
    public void position() {
        int x = 309 + 69 * col;
        int y = 490 - 53 * row;
        setLocation(x, y - 15);
    }
    
    protected void addMove(int r, int c) {
        if (isInBounds(r, c) && world.getPiece(r, c) == null) {
            possibleMoves.add(new int[]{r, c});
        }
    }
    
    protected void addCapture(int r, int c) {
        if (isInBounds(r, c)) {
            Piece target = world.getPiece(r, c);
            if (target != null && !target.getColor().equals(this.color)) {
                possibleMoves.add(new int[]{r, c});
            }
        }
    }
    
    protected boolean tryAdd(int r, int c) {
        if (!isInBounds(r, c)) return false;
        Piece p = world.getPiece(r, c);
        if (p == null) {
            addMove(r, c);
            return true;
        } else if (!p.getColor().equals(this.color)) {
            addCapture(r, c);
        }
        return false;
    }
    
    protected boolean isInBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    protected String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getType() {
        return type;
    }

    public boolean moved() {
        return isMoved;
    }

    public void isCastling(int newCol) {
        if (type.equals("King") && Math.abs(newCol - col) == 2) {
            if (newCol > col) {
                Piece rook = world.getPiece(row, col + 3);
                rook.moveTo(row, newCol - 1);
            } else {
                Piece rook = world.getPiece(row, col - 4);
                rook.moveTo(row, newCol + 1);
            }
        }
    }
    public ArrayList<int[]> getAllMoves() {
        possibleMoves.clear();
        calculateMoves();
        removeMoves();
        return new ArrayList<>(possibleMoves);
    }
}