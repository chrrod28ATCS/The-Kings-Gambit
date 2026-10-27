import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TimerUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class TimerUI extends Actor
{
    private int timer;
    public TimerUI(int timer) {
        this.timer = timer;
    }
    /**
     * Act - do whatever the TimerUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        handleClick();
    }
    public int handleClick() {
        if (Greenfoot.mouseClicked(this)) {
            if (this instanceof CustomMin) {
                return ((CustomMin)(this)).getCustomTime();
            }
            return timer;
        }
        return 0;
    }
}