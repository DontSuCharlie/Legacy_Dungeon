
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
        currentTile = tile;
        this.sourceCharacter = sourceCharacter;
    }
    
    public void act(DungeonMain lDungeon)
    {
        while (movesLeft > 0)
        {
            System.out.println("Projectile moving.");
            
            if(currentTile instanceof DungeonTile)
            {
            
                if(currentTile.character != null)
                {
                    currentTile.character.dealDamage(damage, currentTile.x, currentTile.y, sourceCharacter, lDungeon);
                    //Use deal damage method?
                    System.out.println("Shot");
                   
                    //Destroy this projectile.
                    lDungeon.ProjectileList.remove(this);
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
            
            else
            {
                lDungeon.ProjectileList.remove(this);
            }
            
        }
    }
}
