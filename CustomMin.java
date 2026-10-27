import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CustomMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CustomMin extends TimerUI
{
    public CustomMin() {
        super(1);
    }
    /**
     * Act - do whatever the CustomMin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public int getCustomTime() {
        boolean selected = false;
        int time = 0;
        while (!selected) {
            String inputtedTime = Greenfoot.ask("Input amount of time for each player in this format: \"Minutes: Seconds\" using integers");
            try {
                String[] splitTime = inputtedTime.split(":");
                int minutes = Integer.parseInt(splitTime[0].trim());
                int seconds = Integer.parseInt(splitTime[1].trim());
                time = minutes * 60 + seconds;
                selected = true;
            } catch (ArrayIndexOutOfBoundsException a) {
                System.out.println("The required format is: \"Minutes: Seconds\"");
            } catch (NumberFormatException n) {
                System.out.println("You must input integers");
            }
        }
        return time;
    }
}
