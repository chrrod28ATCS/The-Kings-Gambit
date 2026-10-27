import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PromoteUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class PromoteUI extends Actor
{
    private int num;
    /**
     * Act - do whatever the PromoteUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public PromoteUI(int num) {
        this.num = num;
    }
    public void act()
    {
        
    }
    public int handleClick() {
        if (Greenfoot.getMouseInfo() != null) {
            if (Greenfoot.mouseClicked(this)) {
                return num;
            }
        }
        return 0;
    }
}
