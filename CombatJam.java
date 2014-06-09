import java.awt.Image;
import java.awt.image.BufferedImage;


public class CombatJam extends Jam{
    
    public CombatJam()
    {
        super();
        description = "An evolved form of the normal Jam, these creatures attempt to defend their home.";
        spawnRate = .1;
        //characterID = 2;
        maxHealth = 5;
        currentHealth = 5;
        isFriendly = false;
        
    }
    public BufferedImage getImage()
    {
        Image combatSlimeImage = null;
        if (this.imageID == 0 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 0: return DungeonMain.combatSlimeImageEast;
            case 1: return DungeonMain.combatSlimeImageNorth;
            case 2: return DungeonMain.combatSlimeImageWest;
            case 3: return DungeonMain.combatSlimeImageSouth;
            default: return DungeonMain.combatSlimeImageEast;
            }
        }
        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 0: return DungeonMain.combatSlimeImageEastWalk;
            case 1: return DungeonMain.combatSlimeImageNorthWalk;
            case 2: return DungeonMain.combatSlimeImageWestWalk;
            case 3: return DungeonMain.combatSlimeImageSouthWalk;
            default: return DungeonMain.combatSlimeImageEastWalk;
            }
        }
        
        else if(this.isHit == true)
        {
            switch(this.direction)
            {
            case 0: return DungeonMain.slimeImageEastHit;
            case 1: return DungeonMain.slimeImageNorthHit;
            case 2: return DungeonMain.slimeImageWestHit;
            case 3: return DungeonMain.slimeImageSouthHit;
            default: return DungeonMain.slimeImageEastHit;
            }
        }
        
        else 
        {
            //Should not run.
            return DungeonMain.combatSlimeImageEast;
        }
    }
    
    public void act(DungeonMain lDungeon)
    {
        AIAggressiveSemiRandom(lDungeon);
    }

}
