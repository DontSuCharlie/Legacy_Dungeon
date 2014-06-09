import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import java.awt.image.BufferedImage;

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
        isFriendly = true;
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
    
    public BufferedImage getImage()
    {
        switch(this.direction)
        {
            case 0: return DungeonMain.playerImageEast;
            case 1: return DungeonMain.playerImageNorth;
            case 2: return DungeonMain.playerImageWest;
            case 3: return DungeonMain.playerImageSouth;
            default: return DungeonMain.playerImageEast;
        }
    }
    
    public void attack(DungeonMain lDungeon)
    {
        KeyboardInput.boolIsAttack = false;
        lDungeon.isEnemyTurn = true;
        //isChange = true;
        System.out.println("punch him!");
        int damage = (int) (2 * Math.random()) + 1;
        int targetTileX = lDungeon.dungeon.playerCharacter.currentTile.x;
        int targetTileY = lDungeon.dungeon.playerCharacter.currentTile.y;
    
        switch(lDungeon.dungeon.playerCharacter.direction)
        {
            case 0: targetTileX += 1;
                break;
            case 1: targetTileY -= 1;
                break;
            case 2: targetTileX -= 1;
                break;
            case 3: targetTileY += 1;
                break;
            default: targetTileX += 1;
        }
    
        if (targetTileX  >= 0 && targetTileX < DungeonRunner.xLength && targetTileY >= 0 && targetTileY < DungeonRunner.yLength && lDungeon.dungeon.tileList[targetTileX][targetTileY] instanceof DungeonTile && lDungeon.dungeon.tileList[targetTileX][targetTileY].character instanceof Character)
        {
            dealDamage(damage, targetTileX, targetTileY, lDungeon);
        }
    }
    
    public void interact(DungeonMain lDungeon) throws InstantiationException, IllegalAccessException
    {
        if (lDungeon.dungeon.playerCharacter.currentTile.itemID != 0)
        {
            if (lDungeon.dungeon.playerCharacter.currentTile.itemID == 1)
            {
                System.out.println("MOENEY");
                lDungeon.dungeon.playerCharacter.goldAmount += lDungeon.dungeon.playerCharacter.currentTile.goldAmount;
                //lDungeon.superPlayer.goldCount += lDungeon.dungeon.playerCharacter.currentTile.goldAmount;
                //THIS LINE MUST CHANGE THE VALUE IN TILEARRAY FROM LDungeon. ACCESSING SAME MEMORY?
                GoldNumber temp = new GoldNumber(lDungeon.dungeon.playerCharacter.currentTile.goldAmount, lDungeon.dungeon.playerCharacter.currentTile.x, lDungeon.dungeon.playerCharacter.currentTile.y);
                lDungeon.NumberList.add(temp);
                lDungeon.dungeon.tileList[lDungeon.dungeon.playerCharacter.currentTile.x][lDungeon.dungeon.playerCharacter.currentTile.y].number = temp;
                lDungeon.dungeon.playerCharacter.currentTile.itemID = 0;
                lDungeon.dungeon.tileList[lDungeon.dungeon.playerCharacter.currentTile.x][lDungeon.dungeon.playerCharacter.currentTile.y].itemID = 0;
                //isChange = true;

                    
            }
            
            if (lDungeon.dungeon.playerCharacter.currentTile.itemID == 2)
            {
                System.out.println("");
            }
                //lDungeon.tileArray = DungeonRunner.tileList;
        }
        
        if(lDungeon.dungeon.playerCharacter.currentTile.tileID == 2)
        {
            System.out.println("NEXT LEVEL");
            //lDungeon.superPlayer.goldCount = lDungeon.dungeon.playerCharacter.riches;
            lDungeon.dungeon.currentFloor++;                    
            lDungeon.dungeon = new DungeonRunner(1,1,1,100,100,lDungeon.dungeon.currentFloor, lDungeon.dungeon.playerCharacter);
            lDungeon.tileArray = DungeonRunner.tileList;
            //isChange = true;
            
            //If this is the last floor, we export stuff from player and set ingame to false;
            /*
            if lDungeon.dungeon.currentFloor > lDungeon.numFloors
            {
                lDungeon.playerData = lDungeon.dungeon.playerCharacter;
                inGame = false;
            }
*/
        }
        
        
        
        System.out.println("Interacting woah");
        KeyboardInput.boolIsInteracting = false; 
    }
    
    public void move(DungeonMain lDungeon)
    {
        System.out.println("Moving");
        lDungeon.dungeon.playerCharacter.charMove(lDungeon.dungeon.playerCharacter.playerMoveX(), lDungeon.dungeon.playerCharacter.playerMoveY(), lDungeon.dungeon.playerCharacter, lDungeon.dungeon);
        System.out.println(lDungeon.dungeon.playerCharacter.currentTile);
        KeyboardInput.boolIsMoving = false;
        lDungeon.isEnemyTurn = true;
    }
    
        
   static public void main(String[] args)
    {
        //This frame is for testing only. KeyListener will be in LegacyDungeon.
            JFrame frame = new JFrame("Test");
            KeyboardInput input = new KeyboardInput();
            Player player = new Player(1);
            //frame.add(input);
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
