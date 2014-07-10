import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import java.awt.image.BufferedImage;

import javax.swing.*;//part of UI, includes JPanel
// Used for input and actions
public class Player extends Character{
    int xMovement;
    int yMovement;
    int goldAmount;
    boolean goingToNewFloor = false; //Turned true to start process of going to next floor.
    public int altTimer = (int)((800/DungeonMain.DELAY - 500/DungeonMain.DELAY)*Math.random() + 500/DungeonMain.DELAY); //Ranges from .8 to .5 seconds for alt. image. Counter occurs every 25 ms.

    public Player(int inputRiches)
    {
        xMovement = 0;
        yMovement = 0;
        //characterID = 1;
        goldAmount = inputRiches;
        String description = "You";
        isFriendly = true;
        maxHealth = 100;
        currentHealth = maxHealth;
        direction = 2;
        isActive = true;
    }
        
    public void act(DungeonMain lDungeon) throws InstantiationException, IllegalAccessException
    {
        boolean waitingForPlayer = true;
        //Loop while the player needs to act
        while (waitingForPlayer)
        {
            //Uncomment this System.out for things to work, albeit laggily and broken :< Maybe problem with KeyboardInput?
            System.out.println("Player");
            //Player actions
            //In move we use char move and then 
            if (KeyboardInput.boolIsMoving)
            {
                lDungeon.dungeon.playerCharacter.move(lDungeon);
                waitingForPlayer = false;
            }
            else if (KeyboardInput.boolIsAttack)
            {
                lDungeon.dungeon.playerCharacter.attack(lDungeon);
                waitingForPlayer = false;
            }
            else if(KeyboardInput.boolIsInteracting)
            {
                lDungeon.dungeon.playerCharacter.interact(lDungeon);
                waitingForPlayer = false;
            }
            else if(KeyboardInput.boolIs1)
            {
                lDungeon.dungeon.playerCharacter.useSkill1(lDungeon);
                KeyboardInput.boolIs1 = false;
                waitingForPlayer = false;
            }
            
            else if(KeyboardInput.boolIs2)
            {
                lDungeon.dungeon.playerCharacter.useSkill2(lDungeon);
                KeyboardInput.boolIs2 = false;
                waitingForPlayer = false;
            }
            else if(KeyboardInput.boolIs3)
            {
                lDungeon.dungeon.playerCharacter.useSkill3(lDungeon);
                KeyboardInput.boolIs3 = false;
                waitingForPlayer = false;
            }
            else if(KeyboardInput.diagnostic)
            {
                System.out.println("Diagnostic " +lDungeon.dungeon.playerCharacter.currentTile.x + " " + lDungeon.dungeon.playerCharacter.currentTile.y);
                waitingForPlayer = false;
            }
        }
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
            Image slimeImage = null;
            if (this.imageID == 0 && this.isHit == false)
            {
                return DungeonMain.playerImages[direction];

                /*
                switch(this.direction)
                {
                case 6: return DungeonMain.playerImageEast;
                case 8: return DungeonMain.playerImageNorth;
                case 4: return DungeonMain.playerImageWest;
                case 2: return DungeonMain.playerImageSouth;
                default: return DungeonMain.playerImageEast;
                }*/
            }
            //Jam alt. image
            else if(this.imageID == 1 && this.isHit == false)
            {
                return DungeonMain.playerImagesAlt[direction];

            }
            
            else if(this.isHit == true)
            {
                return DungeonMain.playerImagesHit[direction];
            }
            else 
            {
                System.out.println("Error, player image not found");
                return DungeonMain.playerImages[6];
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
            case 6: targetTileX += 1;
                break;
            case 8: targetTileY -= 1;
                break;
            case 4: targetTileX -= 1;
                break;
            case 2: targetTileY += 1;
                break;
            default: targetTileX += 1;
        }
        if (targetTileX  >= 0 && targetTileX < DungeonBuilder.xLength && targetTileY >= 0 && targetTileY < DungeonBuilder.yLength && lDungeon.dungeon.tileList[targetTileX][targetTileY] instanceof DungeonTile && lDungeon.dungeon.tileList[targetTileX][targetTileY].character instanceof Character)
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
                //lDungeon.tileArray = DungeonBuilder.tileList;
        }
        
        if(lDungeon.dungeon.playerCharacter.currentTile.tileID == 2)
        {
            goingToNewFloor = true;
            System.out.println("NEXT LEVEL");
            //Will then run stuff in DungeonMain.   
        }
        System.out.println("Interacting woah");
        KeyboardInput.boolIsInteracting = false; 
    }
    
    public void move(DungeonMain lDungeon)
    {
        System.out.println("Moving");
        lDungeon.dungeon.playerCharacter.charMove(lDungeon.dungeon.playerCharacter.playerMoveX(), lDungeon.dungeon.playerCharacter.playerMoveY(), lDungeon.dungeon);
        
      //Need to activate nearby enemies. Trying to do it in paintComponent breaks stuff :<. Not entirely sure about the incrementation.
        for (int i = lDungeon.dungeon.playerCharacter.currentTile.x - lDungeon.numTilesX; i < lDungeon.dungeon.playerCharacter.currentTile.x + lDungeon.numTilesX; i++)
        {
            for (int j = lDungeon.dungeon.playerCharacter.currentTile.y - lDungeon.numTilesY; j < lDungeon.dungeon.playerCharacter.currentTile.y + lDungeon.numTilesY; j++)
            {
                if (i > 0 && i < DungeonBuilder.xLength && j > 0 && j < DungeonBuilder.yLength && lDungeon.dungeon.tileList[i][j] instanceof DungeonTile && lDungeon.dungeon.tileList[i][j].character instanceof Character && !lDungeon.dungeon.tileList[i][j].character.isActive)
                {
                    lDungeon.dungeon.tileList[i][j].character.isActive = true;
                    //Start aim mode, preventing movement here.
                }
            }
        }
        //lDungeon.activeCharacterList.addAll(bufferList);
        System.out.println(lDungeon.dungeon.playerCharacter.currentTile);
        KeyboardInput.boolIsMoving = false;
        lDungeon.isEnemyTurn = true;
    }
    
    public void useSkill1(DungeonMain lDungeon)
    {
        Fireball(lDungeon, this);
    }

    
    public void useSkill2(DungeonMain lDungeon)
    {
        Heal(lDungeon, this);
    }
    
    public void useSkill3(DungeonMain lDungeon)
    {
        revive(lDungeon, this);
    }
        
    
    
    /*
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
    } */  
}
