
public class Enemy extends Character{
    
    int AIType;
    double spawnRate;
    //When on screen, they are permanently active. Initially at rest.
    int enemyID;
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
        DungeonBuilder.enemyList.remove(this.enemyID);
        //Used to keep enemy IDs consistent with their new position.
        for (Enemy i : DungeonBuilder.enemyList)
        {
            if (i.enemyID > this.enemyID)
            {
                i.enemyID--;
            }
        }
        System.out.println(this.enemyID);
    }

}
