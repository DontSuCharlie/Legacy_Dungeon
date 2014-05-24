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
    static Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
    ArrayList<NodeWorld> nodeList;
    DungeonTile[][] tileArray;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;
    DungeonRunner dungeon;    
    PlayerLegacyDungeon superPlayer;
    ImageLoader imageLoader = new ImageLoader();
    BufferedImage tileImage0 = imageLoader.loadImage("Wall.png");
    BufferedImage tileImage1 = imageLoader.loadImage("DungeonTile1.png");
    BufferedImage tileImage2 = imageLoader.loadImage("DungeonTile2.png");
    BufferedImage money = imageLoader.loadImage("coinGold.png");
    BufferedImage playerImageEast = imageLoader.loadImage("playerEast.png");
    BufferedImage playerImageWest = imageLoader.loadImage("playerWest.png");
    BufferedImage playerImageNorth = imageLoader.loadImage("playerNorth.png");
    BufferedImage playerImageSouth = imageLoader.loadImage("playerSouth.png");
    BufferedImage num0 = imageLoader.loadImage("0.png");
    BufferedImage num1 = imageLoader.loadImage("1.png");
    BufferedImage num2 = imageLoader.loadImage("2.png");
    BufferedImage num3 = imageLoader.loadImage("3.png");
    BufferedImage num4 = imageLoader.loadImage("4.png");
    BufferedImage num5 = imageLoader.loadImage("5.png");
    BufferedImage num6 = imageLoader.loadImage("6.png");
    BufferedImage num7 = imageLoader.loadImage("7.png");
    BufferedImage num8 = imageLoader.loadImage("8.png");
    BufferedImage num9 = imageLoader.loadImage("9.png");
        
    public LegacyDungeonCopy()
    {
        window = new JFrame("Hazardous Laboratory");
        world = new WorldMap();
        nodeList = world.getNodeList();
        dungeon = new DungeonRunner(1,1,1,100,100,1, 0);
        tileArray = DungeonRunner.tileList;
        superPlayer = new PlayerLegacyDungeon();

    }
    public static void main(String[] args)
    {
        LegacyDungeonCopy game = new LegacyDungeonCopy();
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
                System.out.println("Moving");
                game.dungeon.playerCharacter.charMove(game.dungeon.playerCharacter.playerMoveX(), game.dungeon.playerCharacter.playerMoveY(), game.dungeon.playerCharacter);
                
                isChange = true;
                System.out.println(game.dungeon.playerCharacter.currentTile);
                KeyboardInput.boolIsMoving = false;
                //game.tileArray = DungeonRunner.tileList;
            }
            //Probably a better way to do this but oh well
            if(KeyboardInput.boolIsInteracting == true)
            {
                if(game.dungeon.playerCharacter.currentTile.tileID == 2)
                {
                    System.out.println("NEXT LEVEL");
                    game.superPlayer.goldCount = game.dungeon.playerCharacter.riches;
                    game.dungeon.currentFloor++;                    
                    game.dungeon = new DungeonRunner(1,1,1,100,100,game.dungeon.currentFloor, game.superPlayer.goldCount);
                    game.tileArray = DungeonRunner.tileList;
                    isChange = true;

                }
                else if (game.dungeon.playerCharacter.currentTile.itemID != 0)
                {
                    if (game.dungeon.playerCharacter.currentTile.itemID == 1)
                    {
                        System.out.println("MOENEY");
                        game.dungeon.playerCharacter.riches += game.dungeon.playerCharacter.currentTile.goldAmount;
                        game.superPlayer.goldCount += game.dungeon.playerCharacter.currentTile.goldAmount;
                        //THIS LINE MUST CHANGE THE VALUE IN TILEARRAY FROM LDungeon. ACCESSING SAME MEMORY?
                        game.dungeon.playerCharacter.currentTile.itemID = 0;
                        game.dungeon.tileList[game.dungeon.playerCharacter.currentTile.x][game.dungeon.playerCharacter.currentTile.y].itemID = 0;
                        isChange = true;
                            
                    }
                    
                    if (game.dungeon.playerCharacter.currentTile.itemID == 2)
                    {
                        System.out.println("");
                    }
                        //game.tileArray = DungeonRunner.tileList;
                }
                
                
                System.out.println("Interacting woah");
                KeyboardInput.boolIsInteracting = false; 
            }
            
            while(isChange)
            {
                //game.revalidate();
                game.repaint();
                isChange = false;
            }          
        }
    }
        //Insert what you need to test here
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
               DungeonTile drawnTile = null;
               if (dungeon.playerCharacter.currentTile.x - numTilesX/2 + i  >= 0 && dungeon.playerCharacter.currentTile.x - numTilesX/2 + i < DungeonRunner.xLength && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j >= 0 && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j < DungeonRunner.yLength)
               {
                   drawnTile = tileArray[dungeon.playerCharacter.currentTile.x - numTilesX/2 + i][dungeon.playerCharacter.currentTile.y - numTilesY/2 + j];
               }
               // Draws a row of tiles.
               if (drawnTile instanceof DungeonTile)
               {
                   if (drawnTile.tileID == 1)
                   {
                       image = tileImage1;
                   }
                   else if (drawnTile.tileID == 2)
                   {
                       image = tileImage2;
                   }
                   
                   //More variations of tiles here.
               }       
               
               else
               {           
                       image = tileImage0;
               }
               
               g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);
               
               if (drawnTile instanceof DungeonTile && drawnTile.itemID != 0)
               {
                   if (drawnTile.itemID == 1)
                   {
                       image = money;
                       g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);
                   }
               }
               
               if (drawnTile instanceof DungeonTile && drawnTile.characterID != 0)
               {
                   if (drawnTile.characterID == 1)
                   {
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
                   }
               }
           }      
       }

       int floorNum = dungeon.currentFloor;
       int numCounter = 0;
       int oneDigit = 0;
       
       //Supports 3-digit numbers for floors.Theoretically could do more, but that makes GUI :<.
       do
       {
           if(floorNum > 9)
           {
               
               oneDigit = floorNum % 10;
               floorNum /= 10;
               numCounter++;
           }
           
           else
           {
               oneDigit = floorNum;
               floorNum = 0;
               numCounter++;
           }
           
       //Even more inefficient. More a test.
           
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
       g.drawImage(image, tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 5*tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
       
       }while(floorNum != 0);
       
       int goldNum = superPlayer.goldCount;
       numCounter = 0;
       oneDigit = 0;
       
       do
       {
           if(superPlayer.goldCount > 9)
           {
               
               oneDigit = goldNum % 10;
               goldNum /= 10;
               numCounter++;
           }
           
           else
           {
               oneDigit = goldNum;
               goldNum = 0;
               numCounter++;
           }
           
       //Even more inefficient. More a test.
           
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
       g.drawImage(image, 2 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 9*tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
       
       }while(goldNum != 0);
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
