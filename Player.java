import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import javax.swing.*;//part of UI, includes JPanel
// Used for input and actions
public class Player extends Character{
    int xMovement;
    int yMovement;
    public static DungeonTile currentTile;

    public Player()
    {
        xMovement = 0;
        yMovement = 0;


    }
    
    //Issue, things that come first have priority.
    public int playerMoveY()
    {
       
       if (KeyboardInput.boolIsUp == true && KeyboardInput.boolIsDown == true)
       {
          return 0;
       }
       
       else if (KeyboardInput.boolIsUp == true)
       {
          return 1;        
       }
       
       else if (KeyboardInput.boolIsDown == true)
       {
          return -1;         
       }
       
       else 
       {
           return 0;
       }
       
    }
    
    public int playerMoveX(){
       
       if (KeyboardInput.boolIsLeft == true && KeyboardInput.boolIsRight == true)
       {
          return 0;         
       }
       
       else if (KeyboardInput.boolIsLeft == true)
       {
          return 1;
       }
       
       else if (KeyboardInput.boolIsRight == true)
       {
          return -1;        
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
        Player player = new Player();
        frame.add(input);
      frame.setSize(200, 200);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true)
        {      
        player.xMovement = player.playerMoveX();
        player.yMovement = player.playerMoveY();
        player.charMove(player.xMovement, player.yMovement);
        }
    }   
}
