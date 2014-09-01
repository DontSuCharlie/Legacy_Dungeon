import java.awt.image.BufferedImage;


public abstract class Projectile {

    int direction;
    int damage;
    int speed; //0 for stationary, 1 for 1 tile/turn, higher for faster objects.
    int movesLeft;
    
    //BE CAREFUL WITH THIS TILE. ONLY USE THE X AND Y VALUES FROM THIS.
    DungeonTile currentTile;
    Character sourceCharacter;
    public boolean isMoving = false; //Used for animating movement between tiles.
    final int moveTimer = (int) (250/DungeonMain.DELAY); //Used for length of move animation. Currently set to 100 ms delay.
    int currentMoveTimer = 0;
    int altTimer = (int)(50/DungeonMain.DELAY); //Alternates every .05 second. Counter occurs every 25 ms.
    int animationCounter; //This keeps track of which image should be shown.
    public DungeonTile prevTile; //Stored for animation from one tile to another.
    
    //Testing
    public boolean isDestroyed;
    
    public Projectile(int inputDamage, int inputDirection, int speed, DungeonTile tile, Character sourceCharacter)
    {
        this.speed = speed;
        damage = inputDamage;
        direction = inputDirection;
        movesLeft = speed;
        //BE CAREFUL WITH THIS TILE. ONLY USE THE X AND Y VALUES FROM THIS.
        currentTile = new DungeonTile(tile.x, tile.y, 1);
        this.sourceCharacter = sourceCharacter;
    }
    
    /*
     * Allows the projectile to move based on its speed. If the projectile is destroyed (hits a wall or character), this returns true.
     * Otherwise, the it returns false. This allows for proper iteration of the projectileList.
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
            	//It hit some character. Currently allows no friendly fire. Will just pass through. Could add a version with it. 
                if(lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character != null && lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character.isFriendly != sourceCharacter.isFriendly)
                {
                	lDungeon.dungeon.tileList[currentTile.x][currentTile.y].character.dealDamage(damage, currentTile.x, currentTile.y, sourceCharacter, lDungeon);
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
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((currentTile == null) ? 0 : currentTile.hashCode());
        result = prime * result + damage;
        result = prime * result + direction;
        result = prime * result + (isDestroyed ? 1231 : 1237);
        result = prime * result + (isMoving ? 1231 : 1237);
        result = prime * result
                + ((sourceCharacter == null) ? 0 : sourceCharacter.hashCode());
        result = prime * result + speed;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Projectile other = (Projectile) obj;
        if (currentTile == null)
        {
            if (other.currentTile != null)
                return false;
        } else if (!currentTile.equals(other.currentTile))
            return false;
        if (damage != other.damage)
            return false;
        if (direction != other.direction)
            return false;
        if (isDestroyed != other.isDestroyed)
            return false;
        if (isMoving != other.isMoving)
            return false;
        if (sourceCharacter == null)
        {
            if (other.sourceCharacter != null)
                return false;
        } else if (!sourceCharacter.equals(other.sourceCharacter))
            return false;
        if (speed != other.speed)
            return false;
        return true;
    }

    public BufferedImage getImage(DungeonMain lDungeon)
    {
        System.out.println("Error");
        return null;
    }
}
