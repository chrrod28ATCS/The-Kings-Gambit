import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RulesScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RulesScreen extends World
{
    private int timer = 0;
    private Actor text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, text13, text14, text15;
    private boolean backButtonAdded = false;
    private boolean skipRules = false;
    /**
     * Constructor for objects of class RulesScreen.
     * 
     */
    public RulesScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1100, 640, 1);
        prepareTexts();
    }
    public void act() {
        showRules();
        timer++;
    }
    private Actor makeText(String content) {
        GreenfootImage img = new GreenfootImage(content, 30, Color.WHITE, new Color(0,0,0,0));
        Actor text = new Actor() {};
        text.setImage(img);
        return text;
    }
    public void prepareTexts() {
        text1 = makeText("Chess is a two-player strategy game where each player commands an army of 16 pieces:");
        text2 = makeText("pawns, rooks, knights, bishops, a queen, and a king.");
        text3 = makeText("The objective is to checkmate the opponent's king.");
        text4 = makeText("Each type of piece moves uniquely.");
        text5 = makeText("The king moves one square in any direction.");
        text6 = makeText("A rook can move any number of squares along a row or column.");
        text7 = makeText("A bishop can move any number of squares diagonally");
        text8 = makeText("The queen can move any number of squares horizontally, vertically, or diagonally");
        text9 = makeText("A knight can move to any of the closest squares that are not on the same row, column, or diagonal.");
        text10 = makeText("The knight can leap over other pieces.");
        text11 = makeText("A pawn can move one empty square forward, and on its first turn, it can");
        text12 = makeText(" optionally move two empty squares forward. They can only capture diagonally.");
        text13 = makeText("If they reach the opposite side of the board, they can promote to any other piece.");
        text14 = makeText("The game ends when a king is checkmated, a player resigns, or a stalemate occurs.");
        text15 = makeText("Good luck!");
    }
    public void showRules() {
        if (timer>=0 && timer<200) {
            clearTexts();
            addObject(text1, getWidth()/2, getHeight()/2);
            addObject(text2, getWidth()/2, getHeight()/2+20);
        }
        if (timer>=200 && timer<400) {
            clearTexts();
            addObject(text3, getWidth()/2, getHeight()/2);
            addObject(text4, getWidth()/2, getHeight()/2+20);
        }
        if (timer>=400 && timer<600) {
            clearTexts();
            addObject(text5, getWidth()/2, getHeight()/2);
            addObject(text6, getWidth()/2, getHeight()/2+20);
        }
        if (timer>=600 && timer<800) {
            clearTexts();
            addObject(text7, getWidth()/2, getHeight()/2);
            addObject(text8, getWidth()/2, getHeight()/2+20);
        }
        if (timer>=800 && timer<1000) {
            clearTexts();
            addObject(text9, getWidth()/2, getHeight()/2);
            addObject(text10, getWidth()/2, getHeight()/2+20);
        }
        if (timer>=1000 && timer<1200) {
            clearTexts();
            addObject(text11, getWidth()/2, getHeight()/2);
            addObject(text12, getWidth()/2, getHeight()/2+20);
            addObject(text13, getWidth()/2, getHeight()/2+40);
        }
        if (timer>=1200 && timer<1600) {
            clearTexts();
            addObject(text14, getWidth()/2, getHeight()/2);
        }
        if (timer>=1600 && timer<1800) {
            clearTexts();
            addObject(text15, getWidth()/2, getHeight()/2);
        } else if (timer>=1800 && !backButtonAdded) {
            clearTexts();
            addObject(new MenuButton("Back"), getWidth()/2, getHeight()/2);
            backButtonAdded = true;
        }
        enter();
        skipRules();
    }
    private void clearTexts() {
        removeObject(text1);
        removeObject(text2);
        removeObject(text3);
        removeObject(text4);
        removeObject(text5);
        removeObject(text6);
        removeObject(text7);
        removeObject(text8);
        removeObject(text9);
        removeObject(text10);
        removeObject(text11);
        removeObject(text12);
        removeObject(text13);
        removeObject(text14);
        removeObject(text15);
    }
    public void enter() {
        if (!skipRules) {
            showText("Press Enter to skip rules.", getWidth()/2, getHeight()-getHeight()/4);
            if (Greenfoot.isKeyDown("Enter")) {
                skipRules = true;
            }
        }
    }
    public void skipRules() {
        if (skipRules) {
            showText("", getWidth()/2, getHeight()-getHeight()/4);
            Greenfoot.setWorld(new MenuScreen());
        }
    }
}
