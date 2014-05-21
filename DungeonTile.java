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
    public BufferedImage tileImage;
    ImageLoader imageLoader;
    
    //Test for improvement for generation
    boolean tileToLeft;
    boolean tileToRight;
    boolean tileToUp;
    boolean tileToDown;        

    @Override
    public String toString()
    {
        return "DungeonTile [x=" + x + ", y=" + y + ", tileID=" + tileID
                + ", connectionCap=" + connectionCap + ", numConnections=";
               // + numConnections + ", itemID=" + itemID + ", goldAmount="
               // + goldAmount + ", characterID=" + characterID + ", tileImage="
               // + tileImage + ", imageLoader=" + imageLoader + ", tileToLeft="
               // + tileToLeft + ", tileToRight=" + tileToRight + ", tileToUp="
               // + tileToUp + ", tileToDown=" + tileToDown + "]";
    }  
    
    public DungeonTile(int xPos, int yPos, int inputTileID)
    {
        x = xPos;
        y = yPos;
        tileID = inputTileID;
        connectionCap = 2;
        itemID = 0;
        characterID = 0;
        //Not enough memory to do this for 1000 tiles. 
        //tileImage = loadTileImage(tileID);
    }

    // main class for testing only.
    public static void main(String[] args)
    {    
        DungeonTile tile = new DungeonTile(1,1,1);
        BufferedImage testImage = tile.loadTileImage();
    }
    
    
    
      public BufferedImage loadTileImage()
   {
        imageLoader = new ImageLoader();
        return imageLoader.loadImage("DungeonTile" + this.tileID +".png");
   }
}