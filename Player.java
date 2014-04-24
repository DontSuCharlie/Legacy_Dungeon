import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// Used for input and actions
public class Player extends GeneralCharacterProperties{

    public Player()
    {
        
        
    }

    public int move()
    {
        
    
    }
    
    public void aimMode()
    {
        
    
    }
        
   static public void main
    {
        //This frame is for testing only. KeyListener will be in LegacyDungeon.
        JFrame frame = new JFrame("Test");
        KeyboardInput input = new KeyboardInput();
        frame.add(input);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
}
