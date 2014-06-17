import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

/*
Character.java is the superclass of NPC.java, Player.java, and Enemy.java. All characters will have the following attributes in the file.
1] Loading Sprite (not sure about animation)
2] HP
3] Max HP
4] X and Y coordinate positions (in the map)
5] Inventory
6] Skills
*/
//lol probably should not extend skills
public abstract class Character extends Skills
{
//Note to self: Remember to update public or not based on how you want other classes to access this file
    //Field
    BufferedImage image; //Creates an image and loads it. 
    int maxHealth;//maximum HP
    int currentHealth; //current HP
    //double xPos; //current x-coordinate position
    //double yPos; //current y-coordinate position
    ArrayList<Integer> inventory = new ArrayList<Integer>();//this is the inventory
    //Skill[] skill = new Skill[20];//maximum number of skills you can hold is 20.
    public int spriteHeight;//height of image, so rescaling is possible
    public int spriteWidth;//width of image, so rescaling is possible
    public int attemptedX;// These are the coordinates of a tile the character tries to go to. 
    public int attemptedY;
    //public int characterID;// Used to check between player or enemy.
    public String description;
    public DungeonTile currentTile;
    public int altTimer = (int)((800/DungeonMain.DELAY - 500/DungeonMain.DELAY)*Math.random() + 500/DungeonMain.DELAY); //Ranges from .8 to .5 seconds for alt. image. Counter occurs every 25 ms.
    public int imageID; //0 is first pose, 1 is alt., 2 is hit.
    public int direction; // Used for direction of sprite and attacks 6=east, 8=north, 4=west, 2=south, 9=NE, 7=NW, 3=SE, 1=SW
    public boolean isHit = false;
    //Used for length of hit animation. Currently set to 250 ms delay.
    public int hitTimer = (int) (100/DungeonMain.DELAY);
    public int currentHitTime = hitTimer;
    public boolean isFriendly;
    
   //Constructor
    
/*
   public Character(int maxHealth)
   {
      this.maxHealth = maxHealth;
      currentTile = new DungeonTile(0,0,0);
   }
   */
   //Superclass's Methods
    
 /*   
    public int CopiedCheckTile(int deltaX, int deltaY, DungeonTile occupiedTile)
    {
        for (int i = 0; i < DungeonBuilderV2.tileList.size(); i++)
        {
            //Will use the static ArrayList<DungeonTile> tileList from DungeonBuilderV2.java
            if ((DungeonBuilderV2.tileList.get(i).x == (occupiedTile.x + deltaX) ) && (DungeonBuilderV2.tileList.get(i).y == occupiedTile.y + deltaY))
            {
                // If another character is there, return the ID for a wall.
                if (DungeonBuilderV2.tileList.get(i).characterID != 0)
                {
                    return 0;               
                }
                
                return DungeonBuilderV2.tileList.get(i).tileID;
            }
        } 
        //If it gets here, then the tile is not found.
        System.out.println("Missing tile for movement, returning unmovable.");
        return 0;
        
    }
    */
    public DungeonTile charMove(int deltaX, int deltaY, Character character, DungeonBuilder dungeonChar)
    {    
        if(deltaX > 0)
        {
            direction = 6;
        }
        else if(deltaX < 0)
        {
            direction = 4;
        }
        else if(deltaY > 0)
        {
            direction = 2;
        }
        else if(deltaY < 0)
        {
            direction = 8;
        }
        else
        {
            direction = -1;
        }
           if (character.currentTile.x + deltaX >= 0 && character.currentTile.x + deltaX < dungeonChar.xLength && character.currentTile.y + deltaY >= 0 && character.currentTile.y + deltaY < dungeonChar.yLength && dungeonChar.tileList[character.currentTile.x + deltaX] [character.currentTile.y + deltaY] instanceof DungeonTile)
               
           {
               
               DungeonTile potentialTile = dungeonChar.tileList[character.currentTile.x + deltaX] [character.currentTile.y + deltaY];
               if (potentialTile.tileID == 0 || potentialTile.character instanceof Character)
               {
                   //Play a sound
                   System.out.println("Oh no a wall");
                   return character.currentTile;
               }
               else 
               {
                   //dungeonChar.tileList[character.currentTile.x] [character.currentTile.y].characterID = 0;
                   dungeonChar.tileList[character.currentTile.x] [character.currentTile.y].character = null;
                   //dungeonChar.tileList[character.currentTile.x + deltaX] [character.currentTile.y + deltaY].characterID = character.characterID;
                   dungeonChar.tileList[character.currentTile.x + deltaX] [character.currentTile.y + deltaY].character = character;
                   character.currentTile = dungeonChar.tileList[character.currentTile.x + deltaX] [character.currentTile.y + deltaY];
           
                   //HMMMM. IS THIS CAUSING THE ERROR?  nop
                   System.out.println(character.getClass().getName()+ " moved to " + character.currentTile);
                   //return new DungeonTile(currentTile.x + deltaX, currentTile.y + deltaY, 1);
               }
           } 
           
           System.out.println("Oh no a wall");
           return character.currentTile;
    }
    
    public void onDeath()
    {
        System.out.println(":<");
        DungeonBuilder.tileList[this.currentTile.x][this.currentTile.y].deadCharacter = new DeadCharacter(this);
        DungeonBuilder.tileList[this.currentTile.x][this.currentTile.y].character = null;
        DungeonMain.recentDeadCharList.add(new DeadCharacter(this));
        //Sound
        //Animation
        //Remnant on Tile
    }
    
    public void dealDamage(int damage, int targetX, int targetY, DungeonMain lDungeon)
    {
        HitNumber temp = new HitNumber(damage, targetX, targetY, lDungeon.dungeon.tileList[targetX][targetY].character.isFriendly);
        DungeonMain.NumberList.add(temp);
        lDungeon.dungeon.tileList[targetX][targetY].number = temp;
        lDungeon.dungeon.tileList[targetX][targetY].character.currentHealth -= damage;
        lDungeon.dungeon.tileList[targetX][targetY].character.isHit = true;
        //System.out.println(DungeonMain.dungeon.tileList[targetTileX][targetTileY].character.currentHealth);
        if(lDungeon.dungeon.tileList[targetX][targetY].character.currentHealth <= 0)
        {
            lDungeon.dungeon.tileList[targetX][targetY].character.onDeath();
            /*
            if(lDungeon.dungeon.tileList[targetX][targetY].character instanceof Jam)
            {
                ((Jam)(lDungeon.dungeon.tileList[targetX][targetY].character)).onDeath();        
            } */       
        }
    }
    ////////////////////////////////////////////////AI Types/////////////////////////////////////////////
    public void AIRandom(DungeonMain lDungeon)
    {
        //Picks random spot to go to. Including walls.
        double directionChoice = Math.random();
        int deltaX = 0;
        int deltaY = 0;
        if (directionChoice < .25)
        {
            deltaX = 1;
        }
        else if(directionChoice < .5)
        {
            deltaX = -1;
        }
        
        else if(directionChoice < .75)
        {
            deltaY = 1;
        }
        
        else
        {
            deltaY = -1;
        }
        
        charMove(deltaX, deltaY, this, lDungeon.dungeon);
    }
    
    //Player can be replaced by an input target if allies become viable.
    public void AIAggressiveSemiRandom(DungeonMain lDungeon)
    {
        //Attack player if in range. Only attacks if diagonal. (It's not a bug, it's a feature :>) Correct code commented.
        //Math.abs(this.currentTile.x - lDungeon.dungeon.playerCharacter.currentTile.x) == 1 || Math.abs(this.currentTile.y - lDungeon.dungeon.playerCharacter.currentTile.y) == 1 
        if (Math.abs(this.currentTile.x - lDungeon.dungeon.playerCharacter.currentTile.x) == 1 && Math.abs(this.currentTile.y - lDungeon.dungeon.playerCharacter.currentTile.y) == 1 )
        {
            System.out.println("punched!");
            int damage = (int) (2 * Math.random()) + 1;
            int targetTileX = lDungeon.dungeon.playerCharacter.currentTile.x;
            int targetTileY = lDungeon.dungeon.playerCharacter.currentTile.y;
            
            //Set direction of this creature
            //East
            if(targetTileX - this.currentTile.x == 1 && targetTileY == this.currentTile.y )
            {
                this.direction = 0;
            }
            
            //North
            else if(targetTileX == this.currentTile.x && targetTileY - this.currentTile.y == 1)
            {
                this.direction = 1;
            }
            
            //West
            else if(targetTileX - this.currentTile.x == -1 && targetTileY == this.currentTile.y )
            {
                this.direction = 2;
            }
            
            //South
            else if(targetTileX - this.currentTile.x == 1 && targetTileY == this.currentTile.y )
            {
                this.direction = 3;
            }
            
            if(targetTileX - this.currentTile.x == 1 && targetTileY == this.currentTile.y )
            {
                this.direction = 4;
            }
            
            if(targetTileX - this.currentTile.x == -1 && targetTileY - this.currentTile.y == 1)
            {
                this.direction = 5;
            }
            
            if(targetTileX - this.currentTile.x == 1 && targetTileY - this.currentTile.y == -1)
            {
                this.direction = 6;
            }
            
            if(targetTileX - this.currentTile.x == -1 && targetTileY - this.currentTile.y == -1)
            {
                this.direction = 7;
            }
            
            dealDamage(damage, targetTileX, targetTileY, lDungeon);
        }

        
        //Pursue target
        else
        {
            double directionChoice = Math.random();
            int deltaX = 0;
            int deltaY = 0;
            //Get closer in x axis.
            if(directionChoice < .40)
            {
                int temp = lDungeon.dungeon.playerCharacter.currentTile.x - this.currentTile.x;
                //If the player is further to the right, then go right.
                if (temp > 0)
                {
                deltaX = 1;
                }
                
                else if (temp < 0)
                {
                deltaX = -1;
                }
                
                else if (temp == 0)
                {
                    //If on same x level, then randomly decide to left or right.
                    deltaX = (int) (2* Math.random() - 1);
                }
                
            }
            
            else if(directionChoice < .80)
            {
                int temp = lDungeon.dungeon.playerCharacter.currentTile.y - this.currentTile.y;
                //If the player is further to the right, then go right.
                if (temp > 0)
                {
                    deltaY = 1;
                }
                
                else if (temp < 0)
                {
                    deltaY = -1;
                }
                
                else if (temp == 0)
                {
                    //If on same x level, then randomly decide to left or right.
                    deltaY = (int) (2 * Math.random() - 1);
                }
            }
            
            //20% chance of moving randomly
            else if(directionChoice < .85)
            {
                deltaX = -1;
            }
            else if(directionChoice < .90)
            {
                deltaX = 1;
            }
            else if(directionChoice < .95)
            {
                deltaX = -1;
            }
            else if(directionChoice < 1)
            {
                deltaX = 1;
            }
            
            charMove(deltaX, deltaY, this, lDungeon.dungeon);
        }
    }
    
/*    
    public void draw(Graphics g)
    {
        g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
    }
   
    public void paint(Graphics g)
    {
       ObjectProperties.super.paint(g);
    }
    //Methods unique to Character.java
*/
}