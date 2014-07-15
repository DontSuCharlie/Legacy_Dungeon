import java.awt.Image;
import java.awt.image.BufferedImage;


public class RandomJam extends Jam{
    //lol gridworld's revenge
    public RandomJam()
    {
        description = "More annoying than dangerous, this gelatinous creature simply gets in the way.";
        spawnRate = .9;
        //characterID = 2;
        maxHealth = 5;
        currentHealth = 5;
        direction = 2;
    }
    public BufferedImage getImage()
    {
        if (this.imageID == 0 && this.isHit == false)
        {
            return DungeonMain.slimeImages[direction];
        }
        
        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            return DungeonMain.slimeImagesAlt[direction];
        }
        
        else if(this.isHit == true)
        {
            return DungeonMain.slimeImagesHit[direction];
        }
        
        else 
        {
            //Should not run.
            System.out.println("Error missing slime image");
            return DungeonMain.slimeImages[direction];
        }
    }
    
    public void act(DungeonMain lDungeon)
    {
        storedTargetCharacter = this.getEnemyCharacter(lDungeon);
        System.out.println("RandomJam acting - found character");
        //Picks random spot to go to. Including walls.
        //AIRandom(lDungeon);
        goToCharacter(lDungeon);
        
        System.out.println("RandomJam Acted");
        endTurn(lDungeon);
    }
}