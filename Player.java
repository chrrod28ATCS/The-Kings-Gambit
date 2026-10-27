import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private int timer;
    private long startTime;
    private int passedTime = 0;
    private String color;
    private boolean running = false;
    private ArrayList<Piece> captureds = new ArrayList<>();
    private ArrayList<SidePiece> displayedCaptureds = new ArrayList<>();
    int amtOfCaptureds;
    public Player(String color, int time) {
        this.color = color;
        timer = time;
        startTime = System.currentTimeMillis();
        setImage(new GreenfootImage(1, 1));  // 1x1 transparent pixel
        getImage().setTransparency(0);      // 0 = fully transparent
    }
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        displayTime(getRemainingTime());
        displayCaptures();
    }
    public int getTimer() {
        return timer;
    }
    public void startTurn() {
        ((ChessWorld)(getWorld())).addResign();
        running = true;
        startTime = System.currentTimeMillis();
    }
    public void endTurn() {
        passedTime += (int)((System.currentTimeMillis() - startTime) / 1000);
        running = false;
    }
    public int[] getRemainingTime() {
        String turn = ((ChessWorld)(getWorld())).getCurrentTurn();
        int used = passedTime;
        if (running) {
            used+=(int)((System.currentTimeMillis()-startTime)/1000);
        }
        int time = Math.max(0, timer-used);
        return new int[]{time/60, time % 60};
    }
    public void displayTime(int[] time) {
        int x = 200;
        int y = 0;
        if (color.equals("W")) y = 580; else y = 60;
        String format = String.format("%02d:%02d", time[0], time[1]);
        getWorld().showText(format, x, y);
    }
    public int getPassedTime() {
          int used = passedTime;
        if (running) {
            used += (int)((System.currentTimeMillis() - startTime) / 1000);
        }
        return used;
    }
    public void addCapturedPiece(Piece p) {
        captureds.add(p);
        SidePiece sp = new SidePiece(p.getImage(), p.getColor());
        addDisplay(sp);
    }
    public String getColor() {
        return color;
    }
    public void addDisplay(SidePiece sp) {
        displayedCaptureds.add(sp);
    }
    public void displayCaptures() {
        int row = displayedCaptureds.size() % 5;
        int col = displayedCaptureds.size() / 5;
        for (SidePiece current : displayedCaptureds) {
            if (!current.getDisplayed()) {
                if (current.getColor().equals("W")) {
                    int x = 200 - col * 60;
                    int y = 60 + row * 70;
                    getWorld().addObject(current, x, y);
                    current.setDisplayed(true);
                } else {
                    int x = 900 + col * 60;
                    int y = 60 + row * 70;
                    getWorld().addObject(current, x, y);
                    current.setDisplayed(true);
                }
            }
        }
    }
}
