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
        if (this.imageID == 0 && this.isHit == false)
        {
            return DungeonMain.ghostImages[direction];
        }

        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            return DungeonMain.ghostImagesAlt[direction];
        }
            
        else if(this.isHit == true)
        {
            return DungeonMain.ghostImagesHit[direction];
        }

        else 
        {
            //Should not run.
            System.out.println("Error missing ghost image");
            return DungeonMain.ghostImages[direction];
        }
    }
        
        public void act(DungeonMain lDungeon)
        {
            //Picks random spot to go to. Including walls.
            AIRandom(lDungeon);
            endTurn(lDungeon);
        }
    
}
