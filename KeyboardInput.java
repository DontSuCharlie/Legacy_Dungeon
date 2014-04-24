import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

//Used to get keyboard input, similar in use to imageLoader
public class KeyboardInput extends JPanel
{
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
        //@Override
        public void keyTyped(KeyEvent e)
        
        {
        }
        
        //@Override
        public void keyPressed(KeyEvent e)
        {
            System.out.println("keyPressed: " + KeyEvent.getKeyText(e.getKeyCode()));
        }
    
        //@override
        public void keyReleased(KeyEvent e)
        {
            System.out.println("keyReleased: " + KeyEvent.getKeyText(e.getKeyCode()));
        }
    
    }




}
