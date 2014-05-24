import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

//Used to get keyboard input, similar in use to imageLoader
public class KeyboardInput extends JPanel
{
    public static boolean boolIsUp;
    public static boolean boolIsDown;
    public static boolean boolIsLeft;
    public static boolean boolIsRight;
    public static boolean boolIsAim;
    public static boolean boolIsHeal;
    public static boolean boolIsInventory;
    public static boolean boolIsMoving;
    public static boolean boolIsInteracting;
    public static boolean boolIs1;
    public static boolean boolIs2;
    public static boolean boolIs3;
    public static boolean boolIs4;
    public static boolean boolIs5;
    public static boolean boolIs6;
    public static boolean boolIs7;
    public static boolean boolIs8;
    public static boolean boolIs9;
    public static boolean boolIs10;
    //condense to array might make it faster
    public static int key;
    //Note: remove this main when done testing.
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test");
        KeyboardInput input = new KeyboardInput();
        frame.add(input);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public KeyboardInput()
    {
        KeyListener listener = new KeyboardListener();
        addKeyListener(listener);
        setFocusable(true);
    }

    public class KeyboardListener implements KeyListener
    {
       Config config = new Config();
    
        public void keyTyped(KeyEvent e)   
        {
        }
        public void keyPressed(KeyEvent e)
        {
            key = e.getKeyCode();        
            System.out.println("keyPressed: " + key);
            if (key == config.up)
            {
                System.out.println("Up");
                boolIsMoving = true;
                boolIsUp = true;
            }
            else if (key == config.down)
            {
                boolIsMoving = true;
               boolIsDown = true;
            }
            else if (key == config.left)
            {
                boolIsMoving = true;
               boolIsLeft = true;
            }
            else if (key == config.right)
            {
                boolIsMoving = true;
               boolIsRight = true;
            }
            else if (key == config.aimMode)
            {
               boolIsAim = true;
            }
            else if (key == config.hotKeyHeal)
            {
               boolIsHeal = true;
            }
            else if (key == config.inventory)
            {
               boolIsInventory = true;
            }
            else if (key == config.interact)
            {
               boolIsInteracting = true;
            }
            else if (key == config.hotKey1)
            {
               boolIs1 = true;
            }
            else if (key == config.hotKey2)
            {
               boolIs2 = true;
            }
            else if (key == config.hotKey3)
            {
               boolIs3 = true;
            }
            else if (key == config.hotKey4)
            {
               boolIs4 = true;
            }
            
            else if (key == config.hotKey5)
            {
               boolIs5 = true;
            }
            else if (key == config.hotKey6)
            {
               boolIs6 = true;
            }
            else if (key == config.hotKey7)
            {
               boolIs7 = true;
            }
            else if (key == config.hotKey8)
            {
               boolIs8 = true;
            }
            else if (key == config.hotKey9)
            {
               boolIs9 = true;
            }
            else if (key == config.hotKey10)
            {
               boolIs10 = true;
            }
        }
    
        public void keyReleased(KeyEvent e)
        {
            
            key = e.getKeyCode();        
            System.out.println("keyPressed: " + key);
            /*
            if (key == config.up)
            {
               boolIsUp = false;
            }
            else if (key == config.down)
            {
               boolIsDown = false;
            }
            else if (key == config.left)
            {
               boolIsLeft = false;
            }
            else if (key == config.right)
            {
               boolIsRight = false;
            }
            */
            if (key == config.aimMode)
            {
               boolIsAim = false;
            }
            else if (key == config.hotKeyHeal)
            {
               boolIsHeal = false;
            }
            else if (key == config.inventory)
            {
               boolIsInventory = false;
            }
            /*else if (key == config.interact)
            {
               boolIsInteracting = false;
            } 
            */ 
            else if (key == config.hotKey1)
            {
               boolIs1 = false;
            }
            else if (key == config.hotKey2)
            {
               boolIs2 = false;
            }
            else if (key == config.hotKey3)
            {
               boolIs3 = false;
            }
            else if (key == config.hotKey4)
            {
               boolIs4 = false;
            }
            else if (key == config.hotKey5)
            {
               boolIs5 = false;
            }
            else if (key == config.hotKey6)
            {
               boolIs6 = false;
            }
            else if (key == config.hotKey7)
            {
               boolIs7 = false;
            }
            else if (key == config.hotKey8)
            {
               boolIs8 = false;
            }
            else if (key == config.hotKey9)
            {
               boolIs9 = false;
            }
            else if (key == config.hotKey10)
            {
               boolIs10 = false;
            }  
            
            
        }
    }
}
