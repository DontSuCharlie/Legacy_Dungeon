import java.awt.event.KeyEvent;
// Note, will be included in the save file.
public class Config{

public int up;
public int down;
public int left;
public int right;
public int aimMode;
public int hotKeyHeal;
public int inventory;
public int hotKeyOne;
public int hotKeyTwo;
public int hotKeyThree;
public int hotKeyFour;
public int hotKeyFive;
public int hotKeySix;
public int hotKeySeven;
public int hotKeyEight;
public int hotKeyNine;
public int hotKeyTen;


    public Config()
    {
        up = KeyEvent.VK_W;
        down = KeyEvent.VK_S;
        left = KeyEvent.VK_A;
        right = KeyEvent.VK_D;
        aimMode = KeyEvent.VK_SPACE;
        hotKeyHeal  = KeyEvent.VK_H;
        inventory = KeyEvent.VK_I;
        hotKeyOne = KeyEvent.VK_1;
        hotKeyTwo = KeyEvent.VK_2;
        hotKeyThree = KeyEvent.VK_3;
        hotKeyFour = KeyEvent.VK_4;
        hotKeyFive = KeyEvent.VK_5;
        hotKeySix = KeyEvent.VK_6;
        hotKeySeven = KeyEvent.VK_7;
        hotKeyEight = KeyEvent.VK_8;
        hotKeyNine = KeyEvent.VK_9;
        hotKeyTen = KeyEvent.VK_0;
    
    }



    public void rebindKeys()
    {
        
    
    
    }




}