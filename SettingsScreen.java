import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SettingsScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SettingsScreen extends World
{
    /**
     * Constructor for objects of class SettingsScreen.
     * 
     */
    public SettingsScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1100, 640, 1); 
        showMenu();
    }
    public void showMenu() {
        addObject(new MenuButton("Timer"), getWidth() / 2, getHeight() / 2 - 40);
        addObject(new MenuButton("Music"), getWidth() / 2, getHeight() / 2);
        addObject(new MenuButton("Back"), getWidth() / 2, getHeight() / 2 + 40);
    }
}
