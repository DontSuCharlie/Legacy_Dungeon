/*
PURPOSE/USE: In essence this class will be used to test new methods. This class will ONLY have fully completed functions! Also, this class doesn't have a game loop implemented.
////////////////////////////////////LIST OF METHODS////////////////////////////////////////////
List of useable methods:
createWindow() - creates the window
paint() - needed to actually draw graphics
*/
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
public class LegacyDungeonCopy extends JPanel
{
    ArrayList<NodeWorld> nodeList;
    DungeonTile[][] tileArray;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;
    DungeonRunner dungeon;    
    public LegacyDungeonCopy()
    {
        window = new JFrame("Hazardous Laboratory");
        world = new WorldMap();
        nodeList = world.getNodeList();
        dungeon = new DungeonRunner(1,1,1,100,100);
        tileArray = DungeonRunner.tileList;
    }
    public static void main(String[] args)
    {
        LegacyDungeonCopy game = new LegacyDungeonCopy();
        createWindow();
        window.add(game);
        game.repaint();
        //Insert what you need to test here
    }
//Insert test methods below
//Insert test methods above

//Methods that already work
/////////////////////////////////////////////////////////////////Method 0: Creating the Window
   public static void createWindow()
   {
      Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
      window.setSize((int)(100),(int)(100));//Sets size in pixels based on player's screen
      int windowX = (int) (window.getWidth());//grabs the width of window made
      int windowY = (int) (window.getHeight());//grabs the height of window made
      int windowPosX = (int) (screenRes.getWidth() - windowX)/2;//obtains width of user screen, divides by two, then subtracts by size of window
      int windowPosY = (int) (screenRes.getHeight() - windowY)/2;//same as above except with height
      //Creating the frame
      JFrame.setDefaultLookAndFeelDecorated(true);//allows for customization of icons/windows/etc.
      Toolkit toolkit = Toolkit.getDefaultToolkit();//uses the toolkit, which allows for reading of image file
      Image icon = toolkit.getImage("icon.png");
      window.setResizable(true);
      window.setIconImage(icon); //Sets icon image
        KeyboardInput input = new KeyboardInput(); //Used for keyboard input
        window.add(input);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
      window.setLocation(windowPosX, windowPosY);//sets location to center
      window.setVisible(true);//Makes it visible...
    }
//Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
//Calling game.repaint() will update what's in here.
   @Override
   public void paint(Graphics g)
   {
       BufferedImage image = null;
       Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
       super.paintComponent(g);//we have to do super because magic
       //Graphics2D g2 = (Graphics2D) g;
       //Border lineborder = 
       //g.setStroke(new BasicStroke(10));
       g.setColor(Color.red);
       final int numTilesX = 11;
       final int numTilesY = 11;
       
       //Needed length and height of tiles in pixels
       int tileLengthX = (int) screenRes.getWidth() / numTilesX;
       int tileLengthY = (int) screenRes.getHeight() / numTilesY;
       int previousImageID = -1;
       
       
       for (int i = 0; i < numTilesX; i++)
       {
           for (int j = 0; j < numTilesY; j++)
           {
               System.out.print(Player.currentTile.x - numTilesX/2 + i + " ");
               System.out.println(Player.currentTile.y - numTilesY/2 + j);
               
               
               DungeonTile drawnTile = tileArray[Player.currentTile.x - numTilesX/2 + i][Player.currentTile.y - numTilesY/2 + j];
               // Draws a row of tiles.
               if (drawnTile instanceof DungeonTile)
               {
                   if (previousImageID != 1)
                   {
                       image = drawnTile.loadTileImage();
                       previousImageID = 1;
                   }
               }               
               else
               {
                   if (previousImageID != 0)
                   {
                       ImageLoader imageLoader = new ImageLoader();                  
                       image = imageLoader.loadImage("DungeonTile0.png");                  
                       previousImageID = 0;
                   }
               }
               g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);
           }
       
       
       }
      /*
      if(true)
      {
         g.drawImage(world.map, 0, 0, (int)(screenRes.getWidth()/2),(int)(screenRes.getWidth()/2.2),null);
      }
      for(int i = 0; i < nodeList.size(); i++)
      {
         g.drawImage(nodeList.get(i).nodeImage,nodeList.get(i).x,nodeList.get(i).y,20,20,null);
      }
      int vertexCounter = 0;
      for(int i = 0; i < nodeList.size(); i++)
      {
         if(nodeList.get(i).nodeStatus == 2)
         {
            vertexCounter++;
         }
      }
      if(vertexCounter >= 3)
      {
         g.drawPolygon(world.polygonDetector());
      }
      */
   }
}
