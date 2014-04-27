import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

//Used to get keyboard input, similar in use to imageLoader
public class KeyboardInput extends JPanel
{

    public static int key;
    //Note: remove this main when done testing.
    
    public static void main(String[] args)
    {
       Config config = new Config();
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
            if (key == 0)
            {
               System.out.println("AK");
               
               
            }
        }
    
        public void keyReleased(KeyEvent e)
        {
            System.out.println("keyReleased: " + KeyEvent.getKeyText(e.getKeyCode()));
            key = 0;
           
        }
        

        
    
    }



}
