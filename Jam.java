import java.awt.Image;
import java.awt.image.BufferedImage;

//Mostly moved to RandomJam. This is used for Jam specific effects, eg. jam-eater.
public abstract class Jam extends Enemy
{
    //lol gridworld's revenge
    public Jam()
    {
        description = "This genus seems to have evolved after the world collapsed. They are quite resiliant";
    }
    public BufferedImage getImage()
    {
        return null;
    }
    
    public void act(DungeonMain lDungeon)
    {
        
    }
}
