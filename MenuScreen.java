import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class MenuScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class MenuScreen extends World
{
    private ChessWorld w;
    private int time;
    private boolean timeSet;
    private boolean selectedPlay;

    public MenuScreen()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1100, 640, 1);
        timeSet = false;
        selectedPlay = false;
        showMainMenu();
    }

    public void act()
    {
        if (selectedPlay) checkTimer();
    }

    public void showMainMenu()
    {
        GreenfootImage textImage = new GreenfootImage("The King's Gambit", 45, Color.WHITE, new Color(0, 0, 0, 0));
        Actor title = new Actor() {};
        title.setImage(textImage);
        addObject(title, 1100 / 2, 75);

        addObject(new MenuButton("Play Game"), getWidth() / 2, 200);
        addObject(new MenuButton("Rules"), getWidth() / 2, 275);
        addObject(new MenuButton("Settings"), getWidth() / 2, 350);
    }

    public void setPlayerTimer()
    {
        List<TimerUI> timers = getObjects(TimerUI.class);
        if (!timeSet)
        {
            for (TimerUI current : timers)
            {
                int pleaseWork = current.handleClick();
                if (pleaseWork > 0)
                {
                    time = pleaseWork;
                    timeSet = true;
                }
            }
        }
    }

    public void displayTimerUI()
    {
        OneMin one = new OneMin();
        ThreeMin three = new ThreeMin();
        FiveMin five = new FiveMin();
        TenMin ten = new TenMin();
        CustomMin custom = new CustomMin();
        addObject(one, 110, 590);
        addObject(three, 330, 590);
        addObject(five, 550, 590);
        addObject(ten, 770, 590);
        addObject(custom, 990, 590);
        showText("One minute.", 110, 530);
        showText("Three minutes.", 330, 530);
        showText("Five minutes.", 550, 530);
        showText("Ten minutes.", 770, 530);
        showText("Custom minutes.", 990, 530);
    }

    public void checkTimer()
    {
        if (!timeSet)
        {
            displayTimerUI();
            setPlayerTimer();
        } else
        {
            removeObjects(getObjects(TimerUI.class));
            showText("", 110, 530);
            showText("", 330, 530);
            showText("", 550, 530);
            showText("", 770, 530);
            showText("", 990, 530);
        }
    }

    public int getTime()
    {
        return time;
    }
    
    public boolean getTimeSet() {
        return timeSet;
    }

    public void setTime(int t)
    {
        time = t;
    }

    public void setSelectedPlay(boolean play)
    {
        selectedPlay = play;
    }

    public void setChessWorld()
    {
        if (timeSet)
        {
            w = new ChessWorld(time);
            Greenfoot.setWorld(w);
        }
    }
}