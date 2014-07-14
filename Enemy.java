
public class Enemy extends Character{
    
    int AIType;
    double spawnRate;
    //When on screen, they are permanently active. Initially at rest.
    public Enemy()
    {
        
    }
    
    public void act(DungeonMain lDungeon)
    {
        System.out.println("lol");
    }
    
    public void onDeath(DungeonMain lDungeon)
    {
        super.onDeath(lDungeon);
        if (!this.isFriendly)
        {
            DungeonBuilder.enemyList.remove(this);

        }
    }

}
