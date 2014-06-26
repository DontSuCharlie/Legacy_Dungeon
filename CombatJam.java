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
        direction = 6;
    }
    public final BufferedImage getImage()
    {
        Image combatSlimeImage = null;
        if (this.imageID == 0 && this.isHit == false)
        {
            return DungeonMain.combatSlimeImages[direction];
        }
        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            return DungeonMain.combatSlimeImagesAlt[direction];

        }
        
        else if(this.isHit == true)
        {
            return DungeonMain.slimeImagesHit[direction];

        }
        
        else 
        {
            //Should not run.
            System.out.println("Error combatSlime image not found");
            return DungeonMain.combatSlimeImages[6];
        }
    }
    
    public final void act(final DungeonMain lDungeon)
    {
        AIAggressiveSemiRandom(lDungeon);
    }

}
