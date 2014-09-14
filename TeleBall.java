import java.awt.image.BufferedImage;

/*
 * The teleball works like a regular projectile except for on contact it teleports its target randomly.
 */
public class TeleBall extends Projectile
{
    int teleRange; //How far something will be teleported
    public TeleBall(int inputDamage, int inputDirection, int speed,
            DungeonTile tile, Character sourceCharacter, int range)
    {
        super(inputDamage, inputDirection, speed, tile, sourceCharacter);
        //There are 8 eight images, this randomizes which this fireball starts at.
        animationCounter = (int) (8 * Math.random());
        teleRange = range;
    }
    
    /*
     * This returns the image for animation based on the time. 
     * Because direction doesn't matter, the images are placed in the order in which they are needed.
     */
    public BufferedImage getImage(DungeonMain lDungeon)
    {
        int direction = animationCounter % 8;
        return lDungeon.teleballImages[animationCounter % 8];
    }
    
    /*
     * Bleh, copy pasting code from Projectile. Improve if possible, return the hit Character?
     */
    public boolean act(DungeonMain lDungeon)
    {
        isMoving = true;
        prevTile = currentTile;
        //lDungeon.dungeon.tileList[prevTile.x][prevTile.y].projectileList.remove(this);
        while (movesLeft > 0)
        {
            System.out.println("Projectile moving.");
            
            if(lDungeon.dungeon.tileChecker(currentTile.x, currentTile.y, false))
            {
                //It hit some character, dealing damage and then teleporting them. The teleball allows friendly fire, so be careful aiming it. Could add a version with it. 
                if(lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character != null && lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character.isFriendly != sourceCharacter.isFriendly)
                {
                    lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character.dealDamage(damage, currentTile.x, currentTile.y, sourceCharacter, lDungeon);
                    //Make sure character is still alive.
                    if (lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character instanceof Character)
                    {
                        RandomTeleportCommand teleCommand = new RandomTeleportCommand(teleRange);
                        teleCommand.use(lDungeon, lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character);
                    }
                    System.out.println("Shot");
                   
                    //Destroy this projectile.
                    //lDungeon.ProjectileList.remove(this);
                    this.isDestroyed = true;
                    //lDungeon.dungeon.tileList[currentTile.x][currentTile.y].projectileList.add(this);
                    
                    //return true;
                    return false;
                }
            
                if(this.direction == 6)
                {
                    this.currentTile.x += 1; 
                }
                
                //North
                else if(this.direction == 8)
                {
                    this.currentTile.y -= 1;
                }
                
                //West
                else if(this.direction == 4)
                {
                    this.currentTile.x -= 1;
                }
                
                //South
                else if(this.direction == 2)
                {
                    this.currentTile.y += 1;
                }
                
                else if(this.direction == 9)
                {
                    this.currentTile.y -= 1;
                    this.currentTile.x += 1;
                }
                
                else if(this.direction == 7)
                {
                    this.currentTile.y -= 1;
                    this.currentTile.x -= 1;
                }
                
                else if(this.direction == 1)
                {
                    this.currentTile.y += 1;
                    this.currentTile.x -= 1;
                }
                
                else if(this.direction == 3)
                {
                    this.currentTile.y += 1;
                    this.currentTile.x += 1;
                }
                movesLeft--;
            }
            
            //It hit a wall.
            else
            {
                //lDungeon.ProjectileList.remove(this);
                //return true;
                this.isDestroyed = true;
                return false;
            }
        }
        movesLeft = speed;
        return false;
    }

}
