
public class DungeonTile{
    int x;
    int y;
    int tileID; //0 = wall, 1 = regular floor, 2 = trap 3 = stairs
    int connectionCap;
    int numConnections;
    int itemID; //0 = nothing, 1 = money, 2 = ?
    int goldAmount;
    int characterID;
    
    public DungeonTile(int xPos, int yPos)
    {
        x = xPos;
        y = yPos;
        tileID = 0;
        connectionCap = 2;
        numConnections = 0;
        itemID = 0;
        characterId = 0;
        
    }

    public void draw(Graphics g)
    {
        g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
    }
   
    public void paint(Graphics g)
    {
       ObjectProperites.super.paint(g);
    }







}