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
    int characterID;
    BufferedImage tileImage;
    ImageLoader imageLoader;
    
    public DungeonTile(int xPos, int yPos, int Connections)
    {
        x = xPos;
        y = yPos;
        tileID = 0;
        connectionCap = 2;
        numConnections = Connections;
        itemID = 0;
        characterID = 0;
    }
    
    	public BufferedImage loadTileImage(int tileID)
	{
        
        return imageLoader.loadImage("DungeonTile" + tileID +".png");
	}
/*
    public void draw(Graphics g)
    {
        g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
    }
   
    public void paint(Graphics g)
    {
       ObjectProperites.super.paint(g);
    }

*/





}