
public class Jam extends Enemy
{
    //lol gridworld's revenge
    public Jam()
    {
        description = "More annoying than dangerous, this gelatinous creature simply gets in the way.";
        spawnRate = 1;
        //characterID = 2;
        maxHealth = 5;
        currentHealth = 5;
        
    }
    
    public void act(DungeonRunner dungeon)
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
        
        charMove(deltaX, deltaY, this, dungeon);
        
    }
}
