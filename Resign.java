import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Resign here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Resign extends MenuButton
{
    public Resign() {
        super("Resign");
    }
    /**
     * Act - do whatever the Resign wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            ((ChessWorld)(getWorld())).checkResign(this);
        }
    }
}
