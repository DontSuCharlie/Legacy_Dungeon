
public class QuadFireballs extends Skills
{
    
    
    public QuadFireballs(DungeonMain lDungeon, Character sourceCharacter)
    {
        lDungeon.ProjectileList.add(new Projectile (5, sourceCharacter.direction, sourceCharacter.currentTile));
        
    }
    
    
    
    
    
    
}
