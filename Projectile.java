
public class Projectile {

    int direction;
    int damage;
    int speed = 5; //0 for stationary, 1 for 1 tile/turn, higher for faster objects.
    int movesLeft;
    DungeonTile currentTile;
    Character sourceCharacter;
    
    public Projectile(int inputDamage, int inputDirection, DungeonTile tile, Character sourceCharacter)
    {
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
                    lDungeon.ProjectileList.remove(this);
                    return true;
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
                lDungeon.ProjectileList.remove(this);
                return true;
            }
        }
        return false;
    }
}
