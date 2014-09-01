import java.awt.image.BufferedImage;

/**
 * 
 */

/**
 * @author Anikan
 * Having separate classes for each projectile type so that different things interact differently. Ex. fireballs would be stopped by water.
 */
public class Fireball extends Projectile
{
            
    public Fireball(int inputDamage, int inputDirection, int speed, DungeonTile tile, Character sourceCharacter)
    {
        super(inputDamage, inputDirection, speed, tile, sourceCharacter);
        //There are 8 eight images, this randomizes which this fireball starts at.
        animationCounter = (int) (8 * Math.random());

    }
    
    /*
     * This returns the image for animation based on the time. 
     * Because direction doesn't matter, the images are placed in the order in which they are needed.
     */
    public BufferedImage getImage(DungeonMain lDungeon)
    {
        int direction = animationCounter % 8;
        return lDungeon.fireballImages[animationCounter % 8];
    }
    
    public boolean act(DungeonMain lDungeon)
    {
        return super.act(lDungeon);
    }
}