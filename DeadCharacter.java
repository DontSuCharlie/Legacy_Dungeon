
public class DeadCharacter{

    Character prevCharacter;
    boolean justDied;
    int deathTimer = 250/LegacyDungeonPaintTest.DELAY;
    
    public DeadCharacter(Character character)
    {
        prevCharacter = character;
        justDied = true;
    }
    
    
}
