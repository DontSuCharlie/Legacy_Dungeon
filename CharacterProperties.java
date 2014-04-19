//Player and Enemies have these properties
public class GeneralCharacterProperties extends JFrame
{
   BufferedImage image;  
   int identity;   //Tells whether friend or foe, what type of creature
   int HEIGHT;
   int WIDTH;
   public int XPos;
   public int YPos;
   int XSpriteSize;
   int YSpriteSize;
   ArrayList Inventory;   //Enemies will also be able to use items to attack.
   Array Skills;  //There will be a cap on skills. 6 maybe?
   
   public void draw(Graphics g)
   {
      g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
   }
   
   public void paint(Graphics g)
   {
      ObjectProperites.super.paint(g);
   }
   
}
