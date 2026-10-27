import greenfoot.*;
import java.util.*;

public class ChessWorld extends World {
    private Piece selectedPiece = null;
    private Piece[][] Board = new Piece[8][8];
    private String currentTurn = "W";
    ArrayList<Piece> checkers = new ArrayList<>();
    private Piece lastMovedPiece;
    private int lastMovedRow, lastMovedCol;
    private Player WhiteP = null;
    private Player BlackP = null;
    private int time;
    private boolean simulating = false;
    private boolean drawOffered = false;

    public ChessWorld(int time) {
        super(1100, 640, 1);
        setPaintOrder(PromoteUI.class, Marker.class, Piece.class, Board.class);
        addObject(new Board(), 550, 320);
        createObjects();
        this.time = time;
        WhiteP = new Player("W", time);
        BlackP = new Player("B", time);
        addObject(WhiteP, 0,0);
        addObject(BlackP, 0, 0);
        WhiteP.startTurn();
    }
    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            Actor clicked = Greenfoot.getMouseInfo().getActor();
            if (!(clicked instanceof Marker) && !(clicked instanceof Piece)) {
                setSelectedPiece(null);
                clearMarkers();
            } else if (clicked instanceof Marker) {
                Marker m = (Marker) clicked;
                if (selectedPiece != null) {
                    selectedPiece.moveTo(m.getRow(), m.getCol());
                    clearMarkers();
                    setSelectedPiece(null);
                    switchTurn();
                }
            }
        }
        if (isInCheck()) {
            showText("Check!", 900, 50);
        if (isInCheckmate()) {
            showText("Checkmate!", 900, 50);
            Greenfoot.stop();
            }
        } else {
        if (isStalemate()) {
            showText("Stalemate!", 900, 50);
            Greenfoot.stop();
        } 
        else if (isInsufficientMaterial()) {
            showText("Draw by Insufficient Material!", 900, 50);
            Greenfoot.stop();
        } 
        else if (timeRunOut()) {
            if (WhiteP.getPassedTime() == WhiteP.getTimer()) {
                showText("White has run out of time! Black wins.", getWidth()/2, getHeight()/2);
            } else {
                showText("Black has run out of time! White wins.", getWidth()/2, getHeight()/2);
            }
            Greenfoot.stop();
        } else {
            showText("", 900, 50);
            }
        }
    }

    public boolean isInCheck() {
        checkers.clear();
        int kingRow = -1, kingCol = -1;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null && p.getType().equals("King") && p.getColor().equals(currentTurn)) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }
        startSimulation();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null && !p.getColor().equals(currentTurn)) {
                    for (int[] move : p.getMoves()) {
                        if (move[0] == kingRow && move[1] == kingCol) {
                            checkers.add(p);
                        }
                    }
                }
            }
        }
        stopSimulation();
        return !checkers.isEmpty();
    }
    public boolean isInCheck(String color) {
        int kingX = -1, kingY = -1;
        for (int row = 0; row < Board.length; row++) {
            for (int col = 0; col < Board[row].length; col++) {
                Piece piece = Board[row][col];
                if (piece != null && piece.getColor().equals(color) && piece instanceof King) {
                    kingX = col;
                    kingY = row;
                }
            }
        }
        startSimulation();
        for (int row = 0; row < Board.length; row++) {
            for (int col = 0; col < Board[row].length; col++) {
                Piece piece = Board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    ArrayList<int[]> moves = piece.getMoves();
                    for (int[] move : moves) {
                        if (move[0] == kingY && move[1] == kingX) {
                        return true;
                        }
                    }
                }
            }
        }
        stopSimulation();
        return false;
    }
    
    public boolean isInCheckmate() {
        if (!isInCheck(currentTurn)) return false;
        List<Piece> allPieces = getObjects(Piece.class);
        startSimulation();
        for (Piece p : allPieces) {
            if (p.getColor().equals(currentTurn)) {
                ArrayList<int[]> moves = p.getMoves();
                int oldRow = p.getRow();
                int oldCol = p.getCol();
                for (int[] move : moves) {
                    Piece captured = getPiece(move[0], move[1]);
                    p.moveTo(move[0], move[1]);
                    if (!isInCheck(currentTurn)) {
                        p.moveTo(oldRow, oldCol);
                        if (captured != null) {
                            setPiece(captured, move[0], move[1]);
                            addObject(captured, 0, 0);
                            captured.position();
                        }
                        return false;
                    }
                    p.moveTo(oldRow, oldCol);
                    if (captured != null) {
                        setPiece(captured, move[0], move[1]);
                        addObject(captured, 0, 0);
                        captured.position();
                    }
                }
            }
        }
        stopSimulation();
        return true;
    }
    public boolean isStalemate() {
    if (isInCheck()) return false;
    List<Piece> allPieces = getObjects(Piece.class);
    for (Piece p : allPieces) {
        if (p.getColor().equals(currentTurn)) {
           
            if (!p.getAllMoves().isEmpty()) {
                return false;
            }
        }
    }
    return true;
    }
    public boolean isInsufficientMaterial() {
        List<Piece> pieces = getObjects(Piece.class);
        int whiteCount = 0, blackCount = 0;
        boolean whiteHasMinor = false, blackHasMinor = false;
        for (Piece p : pieces) {
            if (p.getType().equals("King")) continue;
            if (p.getColor().equals("W")) {
                whiteCount++;
                if (p.getType().equals("Bishop") || p.getType().equals("Knight"))
                    whiteHasMinor = true;
            } else {
                blackCount++;
                if (p.getType().equals("Bishop") || p.getType().equals("Knight"))
                    blackHasMinor = true;
            }
        }
        if (whiteCount == 0 && blackCount == 0) return true;
        if ((whiteCount == 1 && whiteHasMinor && blackCount == 0) ||
            (blackCount == 1 && blackHasMinor && whiteCount == 0))
            return true;
        return false;
    }
    public void createObjects() {
        addObject(new Rook(0, 0, "W"), 0, 0);
        addObject(new Knight(0, 1, "W"), 0, 0);
        addObject(new Bishop(0, 2, "W"), 0, 0);
        addObject(new Queen(0, 3, "W"), 0, 0);
        addObject(new King(0, 4, "W"), 0, 0);
        addObject(new Bishop(0, 5, "W"), 0, 0);
        addObject(new Knight(0, 6, "W"), 0, 0);
        addObject(new Rook(0, 7, "W"), 0, 0);
        for (int col = 0; col < 8; col++) {
           addObject(new Pawn(1, col, "W"), 0, 0);
        }
        addObject(new Rook(7, 0, "B"), 0, 0);
        addObject(new Knight(7, 1, "B"), 0, 0);
        addObject(new Bishop(7, 2, "B"), 0, 0);
        addObject(new Queen(7, 3, "B"), 0, 0);
        addObject(new King(7, 4, "B"), 0, 0);
        addObject(new Bishop(7, 5, "B"), 0, 0);
        addObject(new Knight(7, 6, "B"), 0, 0);
        addObject(new Rook(7, 7, "B"), 0, 0);
        for (int col = 0; col < 8; col++) {
            addObject(new Pawn(6, col, "B"), 0, 0);
        }
        reorderPieces();
    }
    public void reorderPieces() {
        ArrayList<Piece> allPieces = new ArrayList<>(getObjects(Piece.class));
        Collections.sort(allPieces, (a, b) -> Integer.compare(b.getRow(), a.getRow()));
        for (Piece p : allPieces) {
            int x = p.getX();
            int y = p.getY();
            removeObject(p);
            addObject(p, x, y);
        }
    }
    public void showMarkers(ArrayList<int[]> moves) {
        clearMarkers();
        for (int[] move : moves) {
            Marker m = new Marker(move[0], move[1]);
            addObject(m, 0, 0);
            m.position();
        }
    }
    public void clearMarkers() {
        removeObjects(getObjects(Marker.class));
    }
    public Piece getPiece(int row, int col) {
        return Board[row][col];
    }
    public void setPiece(Piece p, int row, int col) {
        Board[row][col] = p;
    }
    public void removePiece(int row, int col) {
        Piece p = getPiece(row, col);
        if (p != null) {
            removeObject(p);
            setPiece(null, row, col);
        }
    }
    public void setSelectedPiece(Piece p) {
        selectedPiece = p;
    }
    public Piece getSelectedPiece() {
        return selectedPiece;
    }
    public String getCurrentTurn() {
        return currentTurn;
    }
    public ArrayList<Piece> getCheckers() {
        startSimulation();
        stopSimulation();
        return checkers;
    }
    public void switchTurn() {
        if(currentTurn.equals("W")) {
            currentTurn = "B";
            WhiteP.endTurn();
            BlackP.startTurn();
        } else {
            currentTurn = "W";
            BlackP.endTurn();
            WhiteP.startTurn();
        }
    }
    public boolean inBounds(int row, int col) {
    return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }
    public void setLastMovedPiece(Piece p) {
        lastMovedPiece = p;
    }
     public boolean timeRunOut() {
        return WhiteP.getPassedTime() >= WhiteP.getTimer() || BlackP.getPassedTime() >= BlackP.getTimer();
    }
    public int getLastRowMoved() {
        return lastMovedRow;
    }
    public int getLastColMoved() {
        return lastMovedCol;
    }
    public void setLastRowMoved(int newRow) {
        lastMovedRow = newRow;
    }
    public void setLastColMoved(int newCol) {
        lastMovedCol = newCol;
    }
    public Piece[][] getBoard() {
        return Board;
    }
    public void startSimulation() {
        simulating = true;
    }
    
    public void stopSimulation() {
        simulating = false;
    }

    public boolean isSimulating() {
        return simulating;
    }
    public Player getPlayer(String color) {
        return color.equals("W") ? WhiteP : BlackP;
    }
    public void checkResign(Resign r) {
        if(r instanceof WhiteResign) {
            showText("White has resigned! Black wins.", getWidth()/2, getHeight()/2);
            Greenfoot.stop();
        } else {
            showText("Black has resigned! White wins.", getWidth()/2, getHeight()/2);
            Greenfoot.stop();
        }
    }
    public void checkDraw(Draw d) {
        if (d.getVal() == 0) {
            drawOffered = true;
        }
        if (drawOffered = true) {
            if (d.getVal() == 1) {
                showText("Both players have agreed to a draw. Game tied.", getWidth()/2, getHeight()/2);
                Greenfoot.stop();
            } else {
                drawOffered = false;
            }
        }
    }
    public void spawnDraws() {
        if (!drawOffered) {
            addObject(new Draw(0), 50, 620);
        }
        if (drawOffered) {
            removeObjects(getObjects(Draw.class));
            addObject(new Accept(), 30, 620);
            addObject(new Deny(), 100, 620);
        }
    }
    public void addResign() {
        removeObjects(getObjects(Resign.class));
        if(currentTurn.equals("W")) {
            addObject(new WhiteResign(), 1000, 620);
        } else {
            addObject(new BlackResign(), 1000, 620);
        }
    }
}
