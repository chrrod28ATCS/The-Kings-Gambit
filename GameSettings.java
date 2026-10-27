import greenfoot.*;
public class GameSettings {
    public static boolean timerOn = false;
    public static boolean musicOn = false;
    public static GreenfootSound bgm = new GreenfootSound("music.mp3");

    public static void toggleTimer() {
        timerOn = !timerOn;
    }

    public static void toggleMusic() {
        musicOn = !musicOn;
    }
}