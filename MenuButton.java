
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MenuButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuButton extends Actor {
    private String buttonType;
    private MenuScreen menu;
    public MenuButton(String type) {
        this.buttonType = type;
        GreenfootImage img = new GreenfootImage(type, 24, Color.WHITE, new Color(0,0,0,0));
        setImage(img);
    }
    
    public void addedToWorld(World w) {
        if (w instanceof MenuScreen) {
            menu = (MenuScreen)w;
        }
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (buttonType.equals("Play Game")) {
                menu.setSelectedPlay(true);
                if (menu.getTimeSet() == true) {
                    Greenfoot.setWorld(new ChessWorld(menu.getTime()));
                }
            } else if (buttonType.equals("Rules")) {
                Greenfoot.setWorld(new RulesScreen());
            } else if (buttonType.equals("Settings")) {
                Greenfoot.setWorld(new SettingsScreen());
            } else if (buttonType.equals("Back")) {
                Greenfoot.setWorld(new MenuScreen());
            } else if (buttonType.equals("Timer")) {
                GameSettings.timerOn = !GameSettings.timerOn;
                System.out.println("Timer setting: " + (GameSettings.timerOn ? "ON" : "OFF"));
            } else if (buttonType.equals("Music")) {
                GameSettings.toggleMusic();
                if (GameSettings.musicOn) {
                    GameSettings.bgm.playLoop();
                } else {
                    GameSettings.bgm.stop();
                }
                System.out.println("Music setting: " + (GameSettings.musicOn ? "ON" : "OFF"));
            }
        }
    }
}