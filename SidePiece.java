import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SidePiece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SidePiece extends Actor
{
    private String color;
    private boolean isDisplayed = false;
    public SidePiece(GreenfootImage img, String color) {
        setImage(img);
        this.color = color;
    }
    /**
     * Act - do whatever the SidePiece wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public String getColor() {
        return color;
    }
    public boolean getDisplayed() {
        return isDisplayed;
    }
    public void setDisplayed(boolean display) {
        isDisplayed = display;
    }
}
