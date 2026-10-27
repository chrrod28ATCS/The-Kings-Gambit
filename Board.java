import greenfoot.*;

public class Board extends Actor {
    public Board() {
        GreenfootImage image = getImage(); 
        image.scale(image.getWidth() *  86/20, image.getHeight() * 87/20); 
        setImage(image);  
    }

} 