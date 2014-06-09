
public class DeadCharacter{

    Character prevCharacter;
    boolean justDied;
    int deathTimer = 250/DungeonMain.DELAY;
    
    public DeadCharacter(Character character)
    {
        prevCharacter = character;
        justDied = true;
    }
    
    
}
