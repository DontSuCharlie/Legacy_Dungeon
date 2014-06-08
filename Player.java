import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import javax.swing.*;//part of UI, includes JPanel
// Used for input and actions
public class Player extends Character{
    int xMovement;
    int yMovement;
    int goldAmount;
    
    public Player(int inputRiches)
    {
        xMovement = 0;
        yMovement = 0;
        //characterID = 1;
        goldAmount = inputRiches;
        description = "You";

    }
    
    //Basic punch
    public void punch()
    {

    }
    
    
    public int playerMoveY()
    {
       
       if (KeyboardInput.boolIsUp == true && KeyboardInput.boolIsDown == true)
       {
           KeyboardInput.boolIsUp = false;
           KeyboardInput.boolIsDown = false;
          return 0;
       }
       
       //PLEASE NOTE THAT UP HAS LOWER Y VALUE. DOWN HAS GREATER Y VALUE.
       else if (KeyboardInput.boolIsUp == true)
       {
           System.out.println("Going up?");
           KeyboardInput.boolIsUp = false;
           return -1;        
       }
       
       else if (KeyboardInput.boolIsDown == true)
       {
           KeyboardInput.boolIsDown = false;
           return 1;         
       }
       
       else 
       {
           return 0;
       }
       
    }
    
    public int playerMoveX(){
       
       if (KeyboardInput.boolIsLeft == true && KeyboardInput.boolIsRight == true)
       {
           KeyboardInput.boolIsLeft = false;
           KeyboardInput.boolIsRight = false;
          return 0;         
       }
       
       else if (KeyboardInput.boolIsLeft == true)
       {
           KeyboardInput.boolIsLeft = false;
           return -1;
          
       }
       
       else if (KeyboardInput.boolIsRight == true)
       {
           KeyboardInput.boolIsRight = false;
           return 1;        
       }
       else 
       {
          return 0;
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
            Player player = new Player(1);
            frame.addKeyListener(input);
            frame.setSize(200, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            while (true)
            {      
                player.xMovement = player.playerMoveX();
                player.yMovement = player.playerMoveY();
                //player.charMove(player.xMovement, player.yMovement);
        }
    }   
}
