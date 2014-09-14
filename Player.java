import java.awt.*;//includes Color, Dimension, Graphics, etc.
import java.awt.image.BufferedImage;
// Used for input and actions
public class Player extends Character{
    int xMovement;
    int yMovement;
    int goldAmount;
    boolean goingToNewFloor; //Turned true to start process of going to next floor.
    int xVision;
    int yVision;
    Skills hotbarList[] = new Skills[10];

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
        charInventory = new Inventory(64);
        xVision = 16;
        yVision = 9;
        hotbarList[0] = new FireballCommand();
        //Currently manually inputing amounts, should recalculate whenever a stat changes ex. gaining level, equipping stuff.
        hotbarList[1] = new HealCommand(30);
        hotbarList[2] = new ReviveCommand(.25);
        hotbarList[3] = new RandomTeleportCommand(5);
        hotbarList[4] = new TeleballCommand(5);
        

    }

    /**
     * All of the player's actions
     * Moving
     * Attacking
     * Interacting
     * Using Abilities
     * Checking Inventory
     */
    public void act(DungeonMain lDungeon) throws InstantiationException, IllegalAccessException
    {
        boolean waitingForPlayer = true;
        //Loop while the player needs to act
        while (waitingForPlayer)
        {
            //I don't know why but adding a sleep prevents the 2-step bug. Maybe problem with KeyboardInput?
            try
            {
                Thread.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
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
            else if(KeyboardInput.boolIs4)
            {
                lDungeon.dungeon.playerCharacter.useSkill4(lDungeon);
                KeyboardInput.boolIs4 = false;
                waitingForPlayer = false;
            }
            else if(KeyboardInput.boolIs5)
            {
                lDungeon.dungeon.playerCharacter.useSkill5(lDungeon);
                KeyboardInput.boolIs5 = false;
                waitingForPlayer = false;
            }
            else if(KeyboardInput.boolIsHeal)
            {
                System.out.println("ItemHeal");
                lDungeon.dungeon.playerCharacter.itemHeal(lDungeon);
                KeyboardInput.boolIsHeal = false;
                waitingForPlayer = false;
            }

            else if(KeyboardInput.diagnostic)
            {
                System.out.println("Diagnostic " +lDungeon.dungeon.playerCharacter.currentTile.x + " " + lDungeon.dungeon.playerCharacter.currentTile.y);
                waitingForPlayer = false;
            }
        }

        endTurn(lDungeon);
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
        //Alt. image
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
            dealDamage(damage, targetTileX, targetTileY, this, lDungeon);
        }
    }

    public void interact(DungeonMain lDungeon) throws InstantiationException, IllegalAccessException
    {
        if (lDungeon.dungeon.playerCharacter.currentTile.itemID != 0)
        {
            if (lDungeon.dungeon.playerCharacter.currentTile.itemID == 1)
            {
                System.out.println("MONEY");
                lDungeon.dungeon.playerCharacter.goldAmount += lDungeon.dungeon.playerCharacter.currentTile.goldAmount;
                //lDungeon.superPlayer.goldCount += lDungeon.dungeon.playerCharacter.currentTile.goldAmount;
                //THIS LINE MUST CHANGE THE VALUE IN TILEARRAY FROM LDungeon. ACCESSING SAME MEMORY?
                GoldNumber temp = new GoldNumber(lDungeon.dungeon.playerCharacter.currentTile.goldAmount, lDungeon.dungeon.playerCharacter.currentTile.x, lDungeon.dungeon.playerCharacter.currentTile.y);
                lDungeon.numberList.add(temp);
                lDungeon.dungeon.tileList[lDungeon.dungeon.playerCharacter.currentTile.x][lDungeon.dungeon.playerCharacter.currentTile.y].number = temp;
                lDungeon.dungeon.playerCharacter.currentTile.itemID = 0;
                lDungeon.dungeon.tileList[lDungeon.dungeon.playerCharacter.currentTile.x][lDungeon.dungeon.playerCharacter.currentTile.y].itemID = 0;
            }

            //Normal inventory item.
            if (lDungeon.dungeon.playerCharacter.currentTile.itemID == 2)
            {
                charInventory.add(this.currentTile);
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

    /**
     * Simple single tile movement(supports diagonals). First take in input and store the values (1,-1, or 0). 
     * Then perform charMove().
     * Then activate any potential enemies.
     * Note that teleportation must scan more to activate enemies in new area, so that must be separate.
     * @param lDungeon
     */
    public void move(DungeonMain lDungeon)
    {
        System.out.println("Moving");
        int x = lDungeon.dungeon.playerCharacter.playerMoveX();
        int y = lDungeon.dungeon.playerCharacter.playerMoveY();
        lDungeon.dungeon.playerCharacter.charMove(x, y, lDungeon.dungeon);

        //Activate enemies to column left or right depending on player motion.
        if (x != 0)
        {
            //This picks the correct column to scan. If moving right, x is 1.
            int xPos = this.currentTile.x + x * lDungeon.numTilesX;
            int yPos = this.currentTile.y - lDungeon.numTilesY;

            for (int j = 0; j < 2 * lDungeon.numTilesY; j++)
            {
                if (lDungeon.dungeon.tileChecker(xPos, yPos, false) && lDungeon.dungeon.tileList[xPos][yPos].character instanceof Character && !lDungeon.dungeon.tileList[xPos][yPos].character.isActive)
                {
                    lDungeon.dungeon.tileList[xPos][yPos].character.activate(lDungeon.dungeon);
                }
                ++yPos;
            }
        }

        if (y != 0)
        {
            //This picks the correct column to scan. If moving up, y is -1 and the above column is scanner.
            int xPos = this.currentTile.x - lDungeon.numTilesX;
            int yPos = this.currentTile.y + y * lDungeon.numTilesY;

            for (int i = 0; i < 2 * lDungeon.numTilesX; i++)
            {
                if (lDungeon.dungeon.tileChecker(xPos, yPos, false) && lDungeon.dungeon.tileList[xPos][yPos].character instanceof Character && !lDungeon.dungeon.tileList[xPos][yPos].character.isActive)
                {
                    lDungeon.dungeon.tileList[xPos][yPos].character.activate(lDungeon.dungeon);
                }
                ++xPos;
            }
        }
        /*
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
         */
        //lDungeon.activeCharacterList.addAll(bufferList);
        System.out.println(lDungeon.dungeon.playerCharacter.currentTile);
        KeyboardInput.boolIsMoving = false;
        lDungeon.isEnemyTurn = true;
    }

    public void useSkill1(DungeonMain lDungeon)
    {
        hotbarList[0].use(lDungeon, this);
        System.out.println("A");
    }


    public void useSkill2(DungeonMain lDungeon)
    {
        hotbarList[1].use(lDungeon, this);
    }

    public void useSkill3(DungeonMain lDungeon)
    {
        hotbarList[2].use(lDungeon, this);
    }
    
    public void useSkill4(DungeonMain lDungeon)
    {
        hotbarList[3].use(lDungeon, this);
    }
    
    public void useSkill5(DungeonMain lDungeon)
    {
        hotbarList[4].use(lDungeon, this);
    }

    public void itemHeal(DungeonMain lDungeon)
    {
        if(charInventory.itemList.size() > 0)
        {
            charInventory.itemList.get(0).use(this, lDungeon);
        }

        else
        {
            System.out.println("No heal items");
        }
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
