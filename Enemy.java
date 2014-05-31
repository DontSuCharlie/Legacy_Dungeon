
public class Enemy extends Character{
    
    int AIType;
    double spawnRate;
    //When on screen, they are permanently active. Initially at rest.
    boolean isActive = false;
    int enemyID;
    public Enemy()
    {
        
        
    }
    
    public void act()
    {
        System.out.println("lol");
    }
    
    public void onDeath()
    {
        super.onDeath();
        System.out.println(this.enemyID);
        DungeonRunner.enemyList.remove(this.enemyID);
        //Used to keep enemy IDs consistent with their new position.
        for (Enemy i : DungeonRunner.enemyList)
        {
            if (i.enemyID > this.enemyID)
            {
                i.enemyID--;
            }
        }
        System.out.println(this.enemyID);
    }

}
