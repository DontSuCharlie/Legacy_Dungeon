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
    double xPos; //current x-coordinate position
    double yPos; //current y-coordinate position
    ArrayList<Integer> inventory = new ArrayList<Integer>();//this is the inventory
    //Skill[] skill = new Skill[20];//maximum number of skills you can hold is 20.
    public int spriteHeight;//height of image, so rescaling is possible
    public int spriteWidth;//width of image, so rescaling is possible
    public int attemptedX;// These are the coordinates of a tile the character tries to go to. 
    public int attemptedY;
    public int characterID;// Used to check between player or enemy.
    public DungeonTile currentTile;
    public int direction; // Used for direction of sprite and attacks

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
        for (int i = 0; i < DungeonRunner.tileList.size(); i++)
        {
            //Will use the static ArrayList<DungeonTile> tileList from DungeonRunner.java
            if ((DungeonRunner.tileList.get(i).x == (occupiedTile.x + deltaX) ) && (DungeonRunner.tileList.get(i).y == occupiedTile.y + deltaY))
            {
                // If another character is there, return the ID for a wall.
                if (DungeonRunner.tileList.get(i).characterID != 0)
                {
                    return 0;               
                }
                
                return DungeonRunner.tileList.get(i).tileID;
            }
        } 
        //If it gets here, then the tile is not found.
        System.out.println("Missing tile for movement, returning unmovable.");
        return 0;
        
    }
    */
    public boolean charMove(int deltaX, int deltaY)
    {    
           if (DungeonRunner.tileList[this.currentTile.x + deltaX] [this.currentTile.y + deltaY] instanceof DungeonTile)
           {
               DungeonTile potentialTile = DungeonRunner.tileList[this.currentTile.x + deltaX] [this.currentTile.y + deltaY];
               if (potentialTile.tileID == 0 || potentialTile.characterID != 0)
               {
                   //Play a sound
                   return false;
               }
               else 
               {
                   //Not sure if this is allowed. The theory is that currentTile and tileList.get(potentialTileNum) refer to the same object, so changing one changes both.
                   //Need to change charID for new and old tile for 
                   DungeonRunner.tileList[this.currentTile.x] [this.currentTile.y].characterID = 0;
                   this.currentTile = potentialTile;
                   DungeonRunner.tileList[this.currentTile.x + deltaX] [this.currentTile.y + deltaY].characterID = this.characterID;
                   return true;
               }
           } 
           
           System.out.println("Error: Missing movement tile or is a wall");
           return false;
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