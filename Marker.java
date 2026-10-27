import greenfoot.*;

public class Marker extends Actor {
    private int row, col;

    public Marker(int row, int col) {
        this.row = row;
        this.col = col;
        GreenfootImage image = getImage();
        image.scale(image.getWidth() * 3 / 2, image.getHeight() * 3 / 2);
        setImage(image);
    }

    public void position() {
        int x = 309 + 69 * col;
        int y = 490 - 52 * row;
        setLocation(x, y);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col; 
    }
}
