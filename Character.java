import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

/*
Character.java is the superclass of NPC.java, Player.java, and Enemy.java. All characters will have the following attributes in the file.
1] Loading Sprite (not sure about animation)
2] HP
3] Max HP
4] X and Y coordinate positions (in the map)
5] Inventory
6] Skills
*/
//probably should not extend skills. Sloppy
public abstract class Character extends Skills
{
//Note to self: Remember to update public or not based on how you want other classes to access this file
    //Field
    //BufferedImage image; //Creates an image and loads it. 
    int maxHealth;//maximum HP
    int currentHealth; //current HP
    ArrayList<Integer> inventory = new ArrayList<Integer>();//this is the inventory
    //Skill[] skill = new Skill[20];//maximum number of skills you can hold is 20.
    //public int spriteHeight;//height of image, so rescaling is possible
    //public int spriteWidth;//width of image, so rescaling is possible
    //public int characterID;// Used to check between player or enemy.
    public String description;
    public DungeonTile currentTile;
    public Character storedTargetCharacter;// The character this character is basing it's movement off of. Running from it or going to it are just a few examples.
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
    int status = 0; //Keeps track of necessary effects, such as damaging this from poison. 0 is normal.
    Stack<DungeonTile> currentPath = new Stack<DungeonTile>();
    boolean closeToTarget = false;
    private int pathFindingCooldown = 0;
    private int maxPathFindingCooldown = 5;
    
   //Constructor
    
/*
   public Character(int maxHealth)

   {
      this.maxHealth = maxHealth;
      currentTile = new DungeonTile(0,0,0);
   }
   */
    
    private void overHealBurn(DungeonMain lDungeon)
    {
        //If the overheal amount is less than the threshold percentage, restore currentHealth to the normal MaxHealth
        //Right now, OVERHEAL_DECAY_PERCENT is shared between all characters. If individual characters require a different one, please add it specifically to that Enemy's class and manually set the act() properly.
        if (this.currentHealth < (1+lDungeon.OVERHEAL_DECAY_PERCENT) * this.maxHealth)
        {
            this.currentHealth = this.maxHealth;
            System.out.println("Reverted to normal max");
        }
            
        //Reduce the overheal percent in other case.
        else 
        {
            System.out.println("Overheal reduced");
            this.currentHealth -= (int)(lDungeon.OVERHEAL_DECAY_PERCENT * this.maxHealth);
            System.out.println(this.currentHealth);
        }
    }
    /**
     * In future, will add status effects
     * First basic ones, poison, burn, freeze, sleep.
     * Then more interesting ones, invisible, 
     * @param lDungeon
     */
    private void statusEffects(DungeonMain lDungeon)
    {
        
    }
    
    /* charMove: moves this character some given distance and sets their direction appropriately.
     * Mostly used for simple movement, but can be used as part of creature skills as well.
     * NOTE: For pushing abilities, call charMove several times. This only checks if the final tile is occupied. Useful for short range teleports, jumps, etc.
     * The returned boolean is for whether the movement succeeds or fails.
     */
    public boolean charMove(int deltaX, int deltaY, DungeonBuilder dungeonChar)
    {    
        
        setDirection(this.currentTile.x+ deltaX, this.currentTile.y+deltaY);
        if (dungeonChar.tileChecker(this.currentTile.x + deltaX, this.currentTile.y + deltaY, true))       
        {
            dungeonChar.tileList[this.currentTile.x] [this.currentTile.y].character = null;
            dungeonChar.tileList[this.currentTile.x + deltaX] [this.currentTile.y + deltaY].character = this;
            this.currentTile = dungeonChar.tileList[this.currentTile.x + deltaX] [this.currentTile.y + deltaY];
           
            //HMMMM. IS THIS CAUSING THE ERROR?  nop
            //System.out.println(character.getClass().getName()+ " moved to " + character.currentTile);
            //return new DungeonTile(currentTile.x + deltaX, currentTile.y + deltaY, 1);
        }
           
           //System.out.println("Oh no a wall");
           return false;
    }
    
    /* OVERLOADED: Input of chosen DungeonTile instead of coordinates.
     *  charMove: moves a character some given distance and sets their direction appropriately.
     * This differs in that it would work better for pathfinding and teleportation.
     * NOTE: all directions currently not set. Need to do diagonals.
     * ASSUMES VALID TILE. Because if not then 
     * 
     * The boolean is for whether the movement succeeds or fails.
     */
    public void charMove(DungeonTile chosenTile, DungeonBuilder dungeonChar)
    {    
        setDirection(chosenTile.x, chosenTile.y);
        
        dungeonChar.tileList[this.currentTile.x] [this.currentTile.y].character = null;
        chosenTile.character = this;
        this.currentTile = chosenTile;
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
    public void revive(double healPercent, DeadCharacter deadChar, DungeonMain lDungeon)
    {
        //Get the dead deadChar and place it as a deadChar again. (Note still 0 health)
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character = deadChar.prevCharacter;
        //Replace health based on maxHealth
        /*
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character.currentHealth = (int)(lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character.maxHealth * healPercent);
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character.isFriendly = this.isFriendly;
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character.isActive = true;
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].deadCharacter = null;
*/
        deadChar.prevCharacter.currentHealth = (int)(deadChar.prevCharacter.maxHealth * healPercent);
        deadChar.prevCharacter.isFriendly = this.isFriendly;
        deadChar.prevCharacter.isActive = true;
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].deadCharacter = null;
        //If this revived character is an enemy, we add it to the enemyList so, it can be checked for other applications 
        if(!this.isFriendly)
        {
            lDungeon.dungeon.enemyList.add((Enemy) deadChar.prevCharacter);
        }
        
        //Add a new heal number to show what happened.
        //Perhaps add another fancy image to show the revival.
        HealNumber temp = new HealNumber(lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].character.currentHealth, deadChar.prevCharacter.currentTile.x, deadChar.prevCharacter.currentTile.y, deadChar.prevCharacter.isFriendly);
        DungeonMain.NumberList.add(temp);
        lDungeon.dungeon.tileList[deadChar.prevCharacter.currentTile.x][deadChar.prevCharacter.currentTile.y].number = temp;
        
    }
     
    /**
    * Using A* pathing, we get beautiful paths. Diagonals suck though. There is no reason not to use diagonal movement when x and y coordinates differ. I have currently set it so that paths are found diagonals
    * Start by calculating the 
    * This is insensitive to other creatures so it's easier to process. When within 10 tiles or so, use P. This will only be calculated every 5 turns or so.
    * The fine finder takes other characters in account but also reprocesses each turn.
    * 
    */
    public void PathFinder(DungeonMain lDungeon, DungeonTile targetTile)
    {
        System.out.println("Path Finding");

        //These limits determine what are close enough to the target to justify PathFinderFine
        final int FINE_LIMIT_X = lDungeon.numTilesX;
        final int FINE_LIMIT_Y = lDungeon.numTilesY;
        //If close enough we swap to a more accurate pathFinder that takes nearby enemies in account.
        if (Math.abs(this.currentTile.x - targetTile.x) < FINE_LIMIT_X && Math.abs(this.currentTile.y - targetTile.y) < FINE_LIMIT_Y)
        {
            this.closeToTarget = true;
        }

        boolean atTarget = false;
        //HashSets are great for checking if something is in it. Not good for recalling data. Useful for knowing if we already navigated this tile. If we have already went past this tile then we don't add it again. If our heuristic is good enough (consistent) then it should work.
        HashSet<PathTile> checkedTiles = new HashSet<PathTile>();
        
        //We only care about the minimum value so a priority queue is best for the job.
        PriorityQueue<PathTile> potentialTileQueue = new PriorityQueue<PathTile>();
        potentialTileQueue.addAll(new PathTile(this.currentTile).getAdjacentTilesAndSetValues(lDungeon, new PathTile(this.currentTile), targetTile, this.closeToTarget));
        //We store this so that the final path is stored as a linked list.

        //If the queue is empty, then there's no possible way to reach the target. :< I also cause this to short circuit if it attempts to place 1000 tiles on the potentialTileQueue. If it's that bad, then it's probably broken or impossible.
        while(!atTarget && !potentialTileQueue.isEmpty() && checkedTiles.size() < 25)
        {
            //Get the tile the minimum cost away. The sorting is handled by the priorityQueue based on the compareTo() method in DungeonTile
            PathTile possibleTile = potentialTileQueue.poll();
            
            
            //This goal is used when the character is far from the target. Characters in the way are ignored.
            //If this tile is near the target (distance on both x and y axis are within the FINE_LIMIT) then we backtrack, following the previous tile pointers.
            if (!this.closeToTarget && Math.abs(possibleTile.thisTile.x - targetTile.x) < FINE_LIMIT_X && Math.abs(possibleTile.thisTile.y - targetTile.y) < FINE_LIMIT_Y)
            {
                System.out.println("Coarse Target");
                atTarget = true;
                //Keep going back until we reach the source character again.
                while(possibleTile.thisTile != this.currentTile)
                {
                    currentPath.add(possibleTile.thisTile);
                    //Going back further.
                    possibleTile = possibleTile.previousTile;
                }
            }
            
            //If the possibleTile is the targetTile, then we backtrack to find the path.
            else if (this.closeToTarget && possibleTile.thisTile.equals(targetTile))
            {
                System.out.println("Fine Target");
                atTarget = true;
                //Keep going back until we reach the source character again.
                while(possibleTile.thisTile != this.currentTile)
                {
                    System.out.println(possibleTile.thisTile);
                    currentPath.add(possibleTile.thisTile);
                                        
                    //Going back further.
                    possibleTile = possibleTile.previousTile;
                }
            }
            
            if (!checkedTiles.contains(possibleTile))
            {
                System.out.println("Tile plucked" + possibleTile.thisTile);

                checkedTiles.add(possibleTile);
                //Even if there are dead ends, the priorityqueue just gets the next best value. 
                ArrayList<PathTile> temp = possibleTile.getAdjacentTilesAndSetValues(lDungeon, possibleTile, storedTargetCharacter.currentTile, this.closeToTarget);

                potentialTileQueue.addAll(temp);
            }
        }
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
        if(targetTileX - this.currentTile.x > 0 && targetTileY == this.currentTile.y )
        {
            this.direction = 6;
        }
        
        //North
        else if(targetTileX == this.currentTile.x && targetTileY - this.currentTile.y < 0)
        {
            this.direction = 8;
        }
        
        //West
        else if(targetTileX - this.currentTile.x < 0 && targetTileY == this.currentTile.y )
        {
            this.direction = 4;
        }
        
        //South
        else if(targetTileX == this.currentTile.x && targetTileY - this.currentTile.y > 0)
        {
            this.direction = 2;
        }
        
        if(targetTileX - this.currentTile.x > 0 && targetTileY - this.currentTile.y > 0)
        {
            this.direction = 3;
        }
        
        if(targetTileX - this.currentTile.x < 0 && targetTileY - this.currentTile.y > 0)
        {
            this.direction = 1;
        }
        
        if(targetTileX - this.currentTile.x > 0 && targetTileY - this.currentTile.y < 0)
        {
            this.direction = 9;
        }
        
        if(targetTileX - this.currentTile.x < 0 && targetTileY - this.currentTile.y < 0)
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
        
        charMove(deltaX, deltaY, lDungeon.dungeon);
    }
    
    /**
     * Player can be replaced by an input target if allies become viable.
     * @param lDungeon
     */
    public void AIAggressiveSemiRandom(DungeonMain lDungeon)//Combat Jam
    {
        //If no target is stored, get one.
        if (!(storedTargetCharacter instanceof Character))
        {
            storedTargetCharacter = getEnemyCharacter(lDungeon);
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

        
        //Pursue storedTargetCharacter. This is reflexive
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
            
            charMove(deltaX, deltaY, lDungeon.dungeon);
        }
    }
    
    /**
     * Running from evil (or you). Hmm, this runs from only one scary creature, maybe directly into another :<. Need better AI.
     */
    public void AIRun(DungeonMain lDungeon, Character scaryCharacter)
    {
        
        
        
    }
    
    /**
     * This should only be activated when the dead body is relatively nearby, because this hones in on the selected body.
     * Note, need to make balanced. OP right now, especially with two revivers reviving each other.
     * Somewhat inefficient, finds the closest dead char at each loop. Perhaps could rework to only refind when one dies.
     * Run getDeadCharacter in character.act() to get potential deadCharacter
     * @param lDungeon
     * @param chosenDead
     */

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
            
            charMove(deltaX, deltaY, lDungeon.dungeon);
        }
        cooldownTimer1--;

    }
    
    
    /**
     * Pathfinder test
     * 
     */
    public void goToCharacter(DungeonMain lDungeon)
    {
        if (pathFindingCooldown == 0 || currentPath.isEmpty())
        {
            PathFinder(lDungeon, storedTargetCharacter.currentTile);
            //If we are close to target, then we repeatedly find new paths. Maybe bad idea. Else there is a cooldown period.
            //This was so that the player doesn't often see AI acting strangely.
            if(!closeToTarget)
            {
                pathFindingCooldown = maxPathFindingCooldown;
            }
        }
        
        if (!currentPath.isEmpty())
        {
            DungeonTile nextTile = currentPath.pop();
            if(lDungeon.dungeon.tileChecker(nextTile.x, nextTile.y, true))
            {
                charMove(nextTile, lDungeon.dungeon);
                //Is even incremented when unneeded, when fine. Hopefully doesn't matter much.
                --pathFindingCooldown;
            }
        }
    }
    
    /**
     * Get closest enemy character. Currently only gives player.
     * @param lDungeon
     * @return closestEnemy
     */
    public Character getEnemyCharacter(DungeonMain lDungeon)
    {
        //If this is a friendly character
        if (this.isFriendly)
        {
            if (lDungeon.dungeon.enemyList.isEmpty())
            {
                //Change to simple follow AI
                return null;
            }
            Enemy closestEnemy = null;
            int minDistance = 999;
            //
            for(Enemy e : lDungeon.dungeon.enemyList)
            {
                if (this.getDistance(e.currentTile) < minDistance)
                {
                    closestEnemy = e;
                    minDistance = this.getDistance(e.currentTile);
                }
            }
            return closestEnemy;
        }
        
        //If this is an enemy character
        else
        {
            return lDungeon.dungeon.playerCharacter;
        }
    }
    
    /**
     * Just find the closest deadChar to revive
     * @param lDungeon
     * @return
     */
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
    
    /**
     * Try to revive stronger ones first (nasty)
     * @param lDungeon
     * @return
     */
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
    }
      
     /**
     * This is mostly end of turn stuff all characters undergo. overHealBurn and statusEffect.
     * @param lDungeon
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void endTurn(DungeonMain lDungeon)
    {
        //Enemy overheals occur just after their turn.
        if (this.currentHealth > this.maxHealth)
        {
            overHealBurn(lDungeon);
        }
    }
    
    
    
    
}
