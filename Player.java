import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import javax.swing.*;//part of UI, includes JPanel
// Used for input and actions
public class Player extends JFrame{

    public Player()
    {
    
    
    


    }
    
    public void movement()
    {
    
    
    }
    

    public void getMovementKey()
    {
        //Test only
        KeyboardInput input = new KeyboardInput();
        Config config = new Config();
        if (input.key == config.up)
        {
        System.out.println("Kappa");
        }
    
    }
    
    public void aimMode()
    {
        
    
    }
        
   static public void main(String[] args)
    {
        //This frame is for testing only. KeyListener will be in LegacyDungeon.
        JFrame frame = new JFrame("Test");
        KeyboardInput input = new KeyboardInput();
        Config config = new Config();
        Player player = new Player();
        frame.add(input);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        player.getMovementKey();
    }
   
}
