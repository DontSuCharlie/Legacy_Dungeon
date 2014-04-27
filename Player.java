import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import javax.swing.*;//part of UI, includes JPanel
// Used for input and actions
public class Player extends JFrame{

    public Player()
    {
        DungeonTile currentTile = new DungeonTile(0,0,0,0);
    
    


    }
    
    public void movement()
    {
    
    
    }
    

    public void getMovementKey(int keyReceived, Config config)
    {
        System.out.println(keyReceived);
        //Test only
        if (keyReceived == config.up)
        {
        System.out.println("Up");
        }
        
        else if (keyReceived == config.down)
        {
        System.out.println("Down");
        }
    }
    
    public void aimMode()
    {
        
    
    }
        
   static public void main(String[] args)
    {
     int keyReceived;
        //This frame is for testing only. KeyListener will be in LegacyDungeon.
        JFrame frame = new JFrame("Test");
        KeyboardInput input = new KeyboardInput();
        Config config = new Config();
        Player player = new Player();
        frame.add(input);
      frame.setSize(200, 200);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true)
        {
        keyReceived = KeyboardInput.key;       
        player.getMovementKey(keyReceived, config);
//        KeyboardInput.key = 0;
        }
    }   
}
