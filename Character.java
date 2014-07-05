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
    public Character storedTargetCharacter;
    public int altTimer = (int)((800/DungeonMain.DELAY - 500/DungeonMain.DELAY)*Math.random() + 500/DungeonMain.DELAY); //Ranges from .8 to .5 seconds for alt. image. Counter occurs every 25 ms.
    public int imageID; //0 is first pose, 1 is alt., 2 is hit.
    public int direction; // Used for direction of sprite and attacks 6=east, 8=north, 4=west, 2=south, 9=NE, 7=NW, 3=SE, 1=SW
    public boolean isHit = false;
    final int hitTimer = (int) (100/DungeonMain.DELAY); //Used for length of hit animation. Currently set to 250 ms delay.
    private int cooldownTimer1Max = 10; //Time to charge abilities. Reduced by one each turn.
    public int cooldownTimer1 = cooldownTimer1Max;//Deals with cooldowns for the first ability of this creature. 
    public int currentHitTime = hitTimer;
    public int dangerLevel = 1; //This is used to determine how abilities work with this character, ex. revives should not work on bosses.
    public boolean isFriendly; //Used to check attacks and determine which animating number to be shown.
    boolean getNewTarget = true;
    boolean isActive = false;
    
   //Constructor
    
/*
   public Character(int maxHealth)
   {
      this.maxHealth = maxHealth;
      currentTile = new DungeonTile(0,0,0);
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
                //System.out.println("Oh no a wall");
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
             //      System.out.println(character.getClass().getName()+ " moved to " + character.currentTile);
                   //return new DungeonTile(currentTile.x + deltaX, currentTile.y + deltaY, 1);
            }
        } 
           
           //System.out.println("Oh no a wall");
           return character.currentTile;
    }
    
    public void onDeath(DungeonMain lDungeon)
    {
        System.out.println(":<");
        lDungeon.dungeon.tileList[this.currentTile.x][this.currentTile.y].deadCharacter = new DeadCharacter(this);
        lDungeon.dungeon.tileList[this.currentTile.x][this.currentTile.y].character = null;
        this.isActive = false;
        lDungeon.recentDeadCharList.add(new DeadCharacter(this));
        lDungeon.deadCharList.add(new DeadCharacter(this));

        //Sound
        //Animation
        //Remnant on Tile
    }
    
    public void dealDamage(int damage, int targetX, int targetY, DungeonMain lDungeon)
    {
        //Create a new damage number
        HitNumber temp = new HitNumber(damage, targetX, targetY, lDungeon.dungeon.tileList[targetX][targetY].character.isFriendly);
        DungeonMain.NumberList.add(temp);
        lDungeon.dungeon.tileList[targetX][targetY].number = temp;
        //Subtract health
        lDungeon.dungeon.tileList[targetX][targetY].character.currentHealth -= damage;
        
        //Used for stats and the heuristic for finetuning difficulty when building new floors.
        if(lDungeon.dungeon.tileList[targetX][targetY].character instanceof Player)
        {
            lDungeon.dungeon.playerFloorDamage+= damage;
        }
        
        //Show hit animation.
        lDungeon.dungeon.tileList[targetX][targetY].character.isHit = true;
        //If the character has no health, it dies. :<
        if(lDungeon.dungeon.tileList[targetX][targetY].character.currentHealth <= 0)
        {
            lDungeon.dungeon.tileList[targetX][targetY].character.onDeath(lDungeon); 
        }
    }
    
    public void heal(int healAmount, Character healedCharacter, DungeonMain lDungeon)
    {
        healedCharacter.currentHealth += healAmount;
        //lDungeon.dungeon.tileList[locationX][locationY].character.isHeal = true; This could be used to have different colors for enemy heals.
        //System.out.println(DungeonMain.dungeon.tileList[locationTileX][locationTileY].character.currentHealth);
        if (healedCharacter.currentHealth > (2 * healedCharacter.maxHealth))
        {
            //This business with the healAmount allows for the proper healNumber.
            healAmount -= (healedCharacter.currentHealth - 2 * healedCharacter.maxHealth);
            healedCharacter.currentHealth = (2 * healedCharacter.maxHealth);
        }
        HealNumber temp = new HealNumber(healAmount, healedCharacter.currentTile.x, healedCharacter.currentTile.y, healedCharacter.isFriendly);
        DungeonMain.NumberList.add(temp);
        lDungeon.dungeon.tileList[healedCharacter.currentTile.x][healedCharacter.currentTile.y].number = temp;
        
    }
    //healPercent determines the percent of health the revived health starts with. Will only be run if there is no other character there. Else the old character would disappear.(Perhaps humorous combat solution ~ telefragging?)
    public void revive(double healPercent, DeadCharacter character, DungeonMain lDungeon)
    {
        //Get the dead character and place it as a character again. (Note still 0 health)
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character = character.prevCharacter;
        //Replace health based on maxHealth
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character.currentHealth = (int)(lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character.maxHealth * healPercent);
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character.isFriendly = this.isFriendly;
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character.isActive = true;
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].deadCharacter = null;

        
        //Add a new heal number to show what happened.
        //Perhaps add another fancy image to show the revival.
        HealNumber temp = new HealNumber(lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].character.currentHealth, character.prevCharacter.currentTile.x, character.prevCharacter.currentTile.y, character.prevCharacter.isFriendly);
        DungeonMain.NumberList.add(temp);
        lDungeon.dungeon.tileList[character.prevCharacter.currentTile.x][character.prevCharacter.currentTile.y].number = temp;
        
    }
    
    //Get distance from this character to another tile
    public int getDistance(DungeonTile tile)
    {
        return (int)(Math.sqrt(((this.currentTile.x - tile.x)^2 + (this.currentTile.y - tile.y)^2)));  
    }
    
    public void setDirection(int targetTileX, int targetTileY)
    {
      //Set direction of this creature. Ugly please change.
        //East
        if(targetTileX - this.currentTile.x == 1 && targetTileY == this.currentTile.y )
        {
            this.direction = 6;
        }
        
        //North
        else if(targetTileX == this.currentTile.x && targetTileY - this.currentTile.y == -1)
        {
            this.direction = 8;
        }
        
        //West
        else if(targetTileX - this.currentTile.x == -1 && targetTileY == this.currentTile.y )
        {
            this.direction = 4;
        }
        
        //South
        else if(targetTileX == this.currentTile.x && targetTileY - this.currentTile.y == 1)
        {
            this.direction = 2;
        }
        
        if(targetTileX - this.currentTile.x == 1 && targetTileY - this.currentTile.y == 1)
        {
            this.direction = 3;
        }
        
        if(targetTileX - this.currentTile.x == -1 && targetTileY - this.currentTile.y == 1)
        {
            this.direction = 1;
        }
        
        if(targetTileX - this.currentTile.x == 1 && targetTileY - this.currentTile.y == -1)
        {
            this.direction = 9;
        }
        
        if(targetTileX - this.currentTile.x == -1 && targetTileY - this.currentTile.y == -1)
        {
            this.direction = 7;
        }
    }
    ////////////////////////////////////////////////AI Types/////////////////////////////////////////////
    public void AIRandom(DungeonMain lDungeon) //Jam
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
    public void AIAggressiveSemiRandom(DungeonMain lDungeon)//Combat Jam
    {
        //If no target is stored, get one.
        if (!(storedTargetCharacter instanceof Character))
        {
            storedTargetCharacter = getFriendlyCharacter(lDungeon);
        }
        //Attack player if in range. Only attacks if diagonal. (It's not a bug, it's a feature :>) Correct code commented.
        //Math.abs(this.currentTile.x - lDungeon.dungeon.playerCharacter.currentTile.x) == 1 || Math.abs(this.currentTile.y - lDungeon.dungeon.playerCharacter.currentTile.y) == 1 
        if (Math.abs(this.currentTile.x - storedTargetCharacter.currentTile.x) == 1 && Math.abs(this.currentTile.y - storedTargetCharacter.currentTile.y) == 1 )
        {
            System.out.println("punched!");
            int damage = (int) (2 * Math.random()) + 1;
            int storedTargetCharacterTileX = storedTargetCharacter.currentTile.x;
            int storedTargetCharacterTileY = storedTargetCharacter.currentTile.y;
            this.setDirection(storedTargetCharacterTileX, storedTargetCharacterTileY);
            
            dealDamage(damage, storedTargetCharacterTileX, storedTargetCharacterTileY, lDungeon);
        }

        
        //Pursue storedTargetCharacter
        else
        {
            double directionChoice = Math.random();
            int deltaX = 0;
            int deltaY = 0;
            //Get closer in x axis.
            if(directionChoice < .40)
            {
                int temp = storedTargetCharacter.currentTile.x - this.currentTile.x;
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
                    //If on same x level, then randomly decide to go left or right.
                    if(directionChoice < .2)
                    {
                        deltaX = 1;
                    }
                    
                    else
                    {
                        deltaX = -1;
                    }
                }
                
            }
            
            else if(directionChoice < .80)
            {
                int temp = storedTargetCharacter.currentTile.y - this.currentTile.y;
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
                    //If on same x level, then randomly decide to go up or down.
                    if(directionChoice < .6)
                    {
                        deltaY = 1;
                    }
                    
                    else
                    {
                        deltaY = -1;
                    }
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
    
    //Running from evil (or you). Hmm, this runs from only one scary creature, maybe directly into another :<. Need better AI.
    public void AIRun(DungeonMain lDungeon, Character scaryCharacter)
    {
        
        
        
    }
    
    //This should only be activated when the dead body is relatively nearby, because this hones in on the selected body.
    //Note, need to make balanced lol. OP right now, especially with two revivers reviving each other.
    //Somewhat inefficient, finds the closest dead char at each loop. Perhaps could rework to only refind when one dies.
    //Run getDeadCharacter in character.act() to get potential deadCharacter
    public void AIReviver(DungeonMain lDungeon, DeadCharacter chosenDead)//tba(Reviver)
    {
        //These control how much health the revived character has.
        double minReviveHealth = .25;
        double maxReviveHealth = .75;
        
        //Math.abs(this.currentTile.x - lDungeon.dungeon.playerCharacter.currentTile.x) == 1 || Math.abs(this.currentTile.y - lDungeon.dungeon.playerCharacter.currentTile.y) == 1 
        
        //Try to use Revive if off cooldown. Currently does not revive if another character is there (Including itself).
        if (cooldownTimer1 <= 0 && Math.abs(chosenDead.prevCharacter.currentTile.x - this.currentTile.x) <= 1 && Math.abs(chosenDead.prevCharacter.currentTile.y - this.currentTile.y) <= 1 && !(chosenDead.prevCharacter.currentTile.character instanceof Character))
        {
            System.out.println("revived!");
            //Pick amount of health the character respawns with.
            double healthPercent = ((maxReviveHealth - minReviveHealth) * Math.random()) + minReviveHealth;
            this.setDirection(chosenDead.prevCharacter.currentTile.x, chosenDead.prevCharacter.currentTile.y);
            
            revive(healthPercent, chosenDead, lDungeon);
            //Reset cooldown. Also prevents multiple respawns at a time.
            this.cooldownTimer1 = this.cooldownTimer1Max;
        }
        
        //Go to nearest body. Moves if not reviving. Waits if in range of target
        else if (Math.abs(chosenDead.prevCharacter.currentTile.x - this.currentTile.x) > 1 || Math.abs(chosenDead.prevCharacter.currentTile.y - this.currentTile.y) > 1)
        {
            double directionChoice = Math.random();
            int deltaX = 0;
            int deltaY = 0;
            //Get closer in x axis.
            if(directionChoice < .50)
            {
                int temp = chosenDead.prevCharacter.currentTile.x - this.currentTile.x;
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
                    //If on same x level, then randomly decide to go left or right.
                    deltaX = (int) (2 * Math.random() - 1);
                }
                
            }
            
            else
            {
                int temp = chosenDead.prevCharacter.currentTile.y - this.currentTile.y;
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
            
            charMove(deltaX, deltaY, this, lDungeon.dungeon);
        }
        cooldownTimer1--;

    }
    
    //Get closest friendly character. Currently only gives player.
    private Character getFriendlyCharacter(DungeonMain lDungeon)
    {
        return lDungeon.dungeon.playerCharacter;
    }
    
    //Just find the closest deadChar to revive
    private DeadCharacter getDeadCharacter(DungeonMain lDungeon)
    {
        //The target. If no deadcharacters, then return null
        DeadCharacter closestDeadChar = null;
        //Used to find closest character
        int minDistance = 999;
        
        //Get nearest deadCharacter to revive.
        for (DeadCharacter character : lDungeon.deadCharList)
        {
            int temp = this.getDistance(character.prevCharacter.currentTile);
            if(temp < minDistance)
            {
                minDistance = temp;
                closestDeadChar = character;
            }
        }
        return closestDeadChar;
    }
    
    //Try to revive stronger ones first (nasty)
    private DeadCharacter getDeadCharacterPrioritized(DungeonMain lDungeon)
    {
        //The target. If no deadcharacters, then 
        DeadCharacter closestDeadChar = null;
        //Used to find closest character. If no closest, then revert to old to prev AI
        int minDistance = 999;
        
        //Get nearest deadCharacter to revive.
        for (DeadCharacter character : lDungeon.deadCharList)
        {
            //Dividing distance by dangerLevel prioritizes higher ranking enemies, but if there is one lower rank nearby, then it can still be targeted.
            int temp = this.getDistance(character.prevCharacter.currentTile) / character.prevCharacter.dangerLevel;
            if(temp < minDistance)
            {
                minDistance = temp;
                closestDeadChar = character;
            }
        }
        return closestDeadChar;
    }

    public void act(DungeonMain lDungeon) throws InstantiationException, IllegalAccessException
    {
        System.out.println("Error");
        
        // TODO Auto-generated method stub
        
    }
    
    
    
    
}
