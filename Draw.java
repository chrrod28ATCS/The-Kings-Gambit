import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Draw here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Draw extends MenuButton
{
    private int value;
    public Draw(int v) {
        super("Offer Draw");
        value = v;
    }
    public Draw(int v, String t) {
        super(t);
        value = v;
    }
    /**
     * Act - do whatever the Draw wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            ((ChessWorld)(getWorld())).checkDraw(this);
        }
    }
    public int getVal() {
        return value;
    }
}
