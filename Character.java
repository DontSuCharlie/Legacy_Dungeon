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
public class Character extends JFrame
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
    public int altTimer = (int)((1200/LegacyDungeonPaintTest.DELAY - 800/LegacyDungeonPaintTest.DELAY)*Math.random() + 800/LegacyDungeonPaintTest.DELAY); //Ranges from .8 to 1.2 seconds for alt. image. Counter occurs every 25 ms.
    public int imageID; //0 is first pose, 1 is alt., 2 is hit.
    public int direction; // Used for direction of sprite and attacks 0=east, 1=north, 2=west, 3=south
    public boolean isHit;
    
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
        for (int i = 0; i < DungeonRunnerV2.tileList.size(); i++)
        {
            //Will use the static ArrayList<DungeonTile> tileList from DungeonRunnerV2.java
            if ((DungeonRunnerV2.tileList.get(i).x == (occupiedTile.x + deltaX) ) && (DungeonRunnerV2.tileList.get(i).y == occupiedTile.y + deltaY))
            {
                // If another character is there, return the ID for a wall.
                if (DungeonRunnerV2.tileList.get(i).characterID != 0)
                {
                    return 0;               
                }
                
                return DungeonRunnerV2.tileList.get(i).tileID;
            }
        } 
        //If it gets here, then the tile is not found.
        System.out.println("Missing tile for movement, returning unmovable.");
        return 0;
        
    }
    */
    public DungeonTile charMove(int deltaX, int deltaY, Character character, DungeonRunner dungeonChar)
    {    
        if(deltaX > 0)
        {
            direction = 0;
        }
        else if(deltaX < 0)
        {
            direction = 2;
        }
        else if(deltaY > 0)
        {
            direction = 3;
        }
        else if(deltaY < 0)
        {
            direction = 1;
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
           

                   System.out.println(character.getClass().getName()+ " moved to " + character.currentTile);
                   //return new DungeonTile(currentTile.x + deltaX, currentTile.y + deltaY, 1);
               }
           } 
           
           System.out.println("Oh no a wall");
           return character.currentTile;
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