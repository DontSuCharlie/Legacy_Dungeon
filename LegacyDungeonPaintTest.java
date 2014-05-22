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
public class LegacyDungeonPaintTest extends JPanel
{
    static Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
    ArrayList<NodeWorld> nodeList;
    DungeonTile[][] tileArray;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;
    DungeonRunnerV2 dungeon;    
    ImageLoader imageLoader = new ImageLoader();
    BufferedImage tileImage0 = imageLoader.loadImage("Wall.png");
    BufferedImage tileImage1 = imageLoader.loadImage("DungeonTile1.png");
    BufferedImage playerImageEast = imageLoader.loadImage("playerEast.png");
    BufferedImage playerImageWest = imageLoader.loadImage("playerWest.png");
    BufferedImage playerImageNorth = imageLoader.loadImage("playerNorth.png");
    BufferedImage playerImageSouth = imageLoader.loadImage("playerSouth.png");
        
    public LegacyDungeonPaintTest()
    {
        window = new JFrame("Hazardous Laboratory");
        world = new WorldMap();
        nodeList = world.getNodeList();
        dungeon = new DungeonRunnerV2(1,1,1,100,100);
        tileArray = DungeonRunnerV2.tileList;

    }
    public static void main(String[] args)
    {
        LegacyDungeonPaintTest game = new LegacyDungeonPaintTest();
        createWindow();
        window.add(game);
        
        //Changes to true when something needs to be repainted.
        boolean isChange = true;
        boolean inGame = true;
        game.repaint();
        
        while (inGame)
        {
            if (KeyboardInput.boolIsMoving == true)
            {
                game.dungeon.playerCharacter.charMove(game.dungeon.playerCharacter.playerMoveX(), game.dungeon.playerCharacter.playerMoveY(), game.dungeon.playerCharacter);
                isChange = true;
                System.out.println(game.dungeon.playerCharacter.currentTile);
                KeyboardInput.boolIsMoving = false;
            }
            
            while(isChange)
            {
                //game.revalidate();
                game.repaint();
                isChange = false;
            }          
        }
        //Insert what you need to test here
    }
//Insert test methods below
//Insert test methods above

//Methods that already work
/////////////////////////////////////////////////////////////////Method 0: Creating the Window
   public static void createWindow()
   {
       window.setSize((int)(100),(int)(100));//Sets size in pixels based on player's screen
       //window.setSize((int)(screenRes.getWidth()), (int)(screenRes.getHeight()) );
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
       super.paintComponent(g);//we have to do super because magic
       //Graphics2D g2 = (Graphics2D) g;
       //Border lineborder = 
       //g.setStroke(new BasicStroke(10));
       g.setColor(Color.red);
       final int numTilesX = 16;
       final int numTilesY = 9;
       
       //Needed length and height of tiles in pixels
       int tileLengthX = (int) screenRes.getWidth() / numTilesX;
       int tileLengthY = (int) screenRes.getHeight() / numTilesY;
       
       for (int i = 0; i < numTilesX; i++)
       {
           for (int j = 0; j < numTilesY; j++)
           {
               //System.out.print(dungeon.playerCharacter.currentTile.x - numTilesX/2 + i + " ");
               //System.out.println(dungeon.playerCharacter.currentTile.y - numTilesY/2 + j);
               
               
               DungeonTile drawnTile = tileArray[dungeon.playerCharacter.currentTile.x - numTilesX/2 + i][dungeon.playerCharacter.currentTile.y - numTilesY/2 + j];
               // Draws a row of tiles.
               if (drawnTile instanceof DungeonTile)
               {
                   if (drawnTile.tileID == 1)
                   {
                       image = tileImage1;
                   }
                   
                   //More variations of tiles here.
               }       
               
               else
               {           
                       image = tileImage0;
               }
               
               g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);
           }      
       }
       Image playerImage;
       switch(dungeon.playerCharacter.direction)
       {
           case 0: playerImage = playerImageEast;
               break;
           case 1: playerImage = playerImageNorth;
               break;
           case 2: playerImage = playerImageWest;
               break;
           case 3: playerImage = playerImageSouth;
               break;
           default: playerImage = playerImageEast;
       }
       g.drawImage(playerImage, numTilesX/2 * tileLengthX, numTilesY/2 * tileLengthY, (numTilesX/2 + 1) * tileLengthX, (numTilesY/2 + 1) * tileLengthY, 0, 0, playerImage.getWidth(null), playerImage.getHeight(null), null);
       //g.drawImage(tileImage0, numTilesX/2 * tileLengthX, numTilesY/2 * tileLengthY, (numTilesX+1)/2 * tileLengthX, (numTilesY+1)/2 * tileLengthY, 0, 0, tileImage0.getWidth(null), tileImage0.getHeight(null), null);
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
