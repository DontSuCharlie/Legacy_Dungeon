import java.awt.Image;
import java.awt.image.BufferedImage;


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
    public BufferedImage getImage()
    {
        Image slimeImage = null;
        if (this.imageID == 0 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.slimeImageEast;
            case 8: return DungeonMain.slimeImageNorth;
            case 4: return DungeonMain.slimeImageWest;
            case 2: return DungeonMain.slimeImageSouth;
            default: return DungeonMain.slimeImageEast;
            }
        }
        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.slimeImageEastWalk;
            case 8: return DungeonMain.slimeImageNorthWalk;
            case 4: return DungeonMain.slimeImageWestWalk;
            case 2: return DungeonMain.slimeImageSouthWalk;
            default: return DungeonMain.slimeImageEastWalk;
            }
        }
        
        else if(this.isHit == true)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.slimeImageEastHit;
            case 8: return DungeonMain.slimeImageNorthHit;
            case 4: return DungeonMain.slimeImageWestHit;
            case 2: return DungeonMain.slimeImageSouthHit;
            default: return DungeonMain.slimeImageEastHit;
            }
        }
        
        else 
        {
            //Should not run.
            return DungeonMain.slimeImageEast;
        }
    }
    
    public void act(DungeonMain lDungeon)
    {
        //Picks random spot to go to. Including walls.
        AIRandom(lDungeon);
    }
}
