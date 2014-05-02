import java.awt.image.*;

public class DungeonTile
{
    int x;
    int y;
    int tileID; //0 = wall, 1 = regular floor, 2 = trap 3 = stairs
    int connectionCap;
    int numConnections;
    int itemID; //0 = nothing, 1 = money, 2 = ?
    int goldAmount;
    int characterID; //0 = nothing, 1 = player, 2 = enemy
    BufferedImage tileImage;
    ImageLoader imageLoader;
    
    //Test for improvement for generation
    boolean tileToLeft;
    boolean tileToRight;
    boolean tileToUp;
    boolean tileToDown;    
    
    public DungeonTile(int xPos, int yPos, int tileID)
    {
        x = xPos;
        y = yPos;
        tileID = 0;
        connectionCap = 2;
        itemID = 0;
        characterID = 0;
    }

    // main class for testing only.
    public static void main(String[] args)
    {    
    }
    
    
    
      public BufferedImage loadTileImage(int tileID)
   {
        imageLoader = new ImageLoader();
        return imageLoader.loadImage("DungeonTile" + tileID +".png");
   }
}