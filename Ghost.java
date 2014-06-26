import java.awt.Image;
import java.awt.image.BufferedImage;


public class Ghost extends Enemy{

    public Ghost()
    {
        description = "This is a lost spirit seeking revenge for long forgotten"
                + "crimes. It was likely awakened when the ship crash landed."
                + "They are able to revive other dead, a bit sad considering"
                + "their position. You can hit them to make them run away.";
        spawnRate = .1;
        //characterID = 2;
        maxHealth = 10;
        currentHealth = 10;
        
    }
    
    public BufferedImage getImage()
    {
        Image ghostImage = null;
        if (this.imageID == 0 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.ghostImageEast;
            case 8: return DungeonMain.ghostImageNorth;
            case 4: return DungeonMain.ghostImageWest;
            case 2: return DungeonMain.ghostImageSouth;
            default: return DungeonMain.ghostImageEast;
            }
        }
        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.ghostImageEastWalk;
            case 8: return DungeonMain.ghostImageNorthWalk;
            case 4: return DungeonMain.ghostImageWestWalk;
            case 2: return DungeonMain.ghostImageSouthWalk;
            default: return DungeonMain.ghostImageEastWalk;
            }
        }
        
        else if(this.isHit == true)
        {
            switch(this.direction)
            {
            case 6: return DungeonMain.ghostImageEastHit;
            case 8: return DungeonMain.ghostImageNorthHit;
            case 4: return DungeonMain.ghostImageWestHit;
            case 2: return DungeonMain.ghostImageSouthHit;
            default: return DungeonMain.ghostImageEastHit;
            }
        }
        
        else 
        {
            //Should not run.
            System.out.println("Error missing ghost image");
            return DungeonMain.ghostImageEast;
        }
        
        public void act(DungeonMain lDungeon)
        {
            //Picks random spot to go to. Including walls.
            AIRandom(lDungeon);
        }
    
}
