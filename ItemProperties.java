//Player and Enemies have these properties
public class GeneralCharacterProperties extends JFrame
{
   BufferedImage image;  
   int identity;   //Tells whether friend or foe, what type of creature
   int HEIGHT;  // Height of Screen
   int WIDTH;   //Width of Screen
   public int XPos; //X Coordinate
   public int YPos; //Y Coordinate
   int XSpriteSize;
   int YSpriteSize;
   int sellValue;
   boolean singular;//if the item or character cannot be crossed at all
   
   public void draw(Graphics g)
   {
      g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
   }
   
   public void paint(Graphics g)
   {
      ObjectProperites.super.paint(g);
   }
   
}
