
public class DungeonTile{
    int x
    int y
    int tileID //0 = wall, 1 = regular floor, 2 = trap 3 = stairs
    
    public DungeonTile(int xPos, int yPos
    {
        x = xPos;
        y = yPos;
        tileID = 0;
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