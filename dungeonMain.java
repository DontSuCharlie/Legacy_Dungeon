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

public class dungeonMain extends JPanel implements Runnable
{
    static Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
    ArrayList<Node> nodeList;
    ArrayList<Character> visibleCharacters = new ArrayList<Character>();
    static ArrayList<DeadCharacter> recentDeadCharList = new ArrayList<DeadCharacter>();
    musicPlayer musicPlayer = new musicPlayer();
    DungeonTile[][] tileArray;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;
    DungeonRunner dungeon;    
    //private Timer timer;
    final static int DELAY = 25;
    private Thread animator;
    //PlayerLegacyDungeon superPlayer;
    ImageLoader imageLoader = new ImageLoader();
    BufferedImage tileImage0 = imageLoader.loadImage("Wall.png");
    BufferedImage tileImage1 = imageLoader.loadImage("DungeonTile1.png");
    BufferedImage tileImage2 = imageLoader.loadImage("DungeonTile2.png");
    BufferedImage money = imageLoader.loadImage("coinGold.png");
    BufferedImage playerImageEast = imageLoader.loadImage("playerEast.png");
    BufferedImage playerImageWest = imageLoader.loadImage("playerWest.png");
    BufferedImage playerImageNorth = imageLoader.loadImage("playerNorth.png");
    BufferedImage playerImageSouth = imageLoader.loadImage("playerSouth.png");
    BufferedImage slimeImageEast = imageLoader.loadImage("slimeEast.png");
    BufferedImage slimeImageWest = imageLoader.loadImage("slimeWest.png");
    BufferedImage slimeImageNorth = imageLoader.loadImage("slimeNorth.png");
    BufferedImage slimeImageSouth = imageLoader.loadImage("slimeSouth.png");
    BufferedImage slimeImageEastWalk = imageLoader.loadImage("slimeEastWalk.png");
    BufferedImage slimeImageWestWalk = imageLoader.loadImage("slimeWestWalk.png");
    BufferedImage slimeImageNorthWalk = imageLoader.loadImage("slimeNorthWalk.png");
    BufferedImage slimeImageSouthWalk = imageLoader.loadImage("slimeSouthWalk.png");
    BufferedImage slimeImageEastDead = imageLoader.loadImage("slimeEastDead.png");
    BufferedImage slimeImageWestDead = imageLoader.loadImage("slimeWestDead.png");
    BufferedImage slimeImageNorthDead = imageLoader.loadImage("slimeNorthDead.png");
    BufferedImage slimeImageSouthDead = imageLoader.loadImage("slimeSouthDead.png");
    BufferedImage slimeImageEastHit = imageLoader.loadImage("slimeEastHit.png");
    BufferedImage slimeImageWestHit = imageLoader.loadImage("slimeWestHit.png");
    BufferedImage slimeImageNorthHit = imageLoader.loadImage("slimeNorthHit.png");
    BufferedImage slimeImageSouthHit = imageLoader.loadImage("slimeSouthHit.png");
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
        
    public dungeonMain() throws InstantiationException, IllegalAccessException
    {
        window = new JFrame("Hazardous Laboratory");
        //world = new WorldMap();
        //nodeList = world.getNodeList();
        dungeon = new DungeonRunner(1,1,1,100,100,1,null);
        tileArray = DungeonRunner.tileList;
        //superPlayer = new PlayerLegacyDungeon();
    }
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException
    {
        dungeonMain game = new dungeonMain();
        createWindow();
        window.add(game); 
        //Changes to true when something needs to be repainted.
       // boolean isChange = true;
        boolean inGame = true;
        boolean isEnemyTurn = false;
        game.repaint();
        
        
        while (inGame)
        {
			game.repaint();
            if (KeyboardInput.boolIsMoving == true)
            {
                System.out.println("Moving");
                game.dungeon.playerCharacter.charMove(game.dungeon.playerCharacter.playerMoveX(), game.dungeon.playerCharacter.playerMoveY(), game.dungeon.playerCharacter, game.dungeon);
                //isChange = true;
                System.out.println(game.dungeon.playerCharacter.currentTile);
                KeyboardInput.boolIsMoving = false;
                isEnemyTurn = true;
                
                //game.tileArray = DungeonRunner.tileList;
            }
            //Probably a better way to do this but oh well
            if (KeyboardInput.boolIsAttack == true)
            {
                KeyboardInput.boolIsAttack = false;
                isEnemyTurn = true;
                //isChange = true;
                System.out.println("punch him!");
                int damage = (int) (2 * Math.random()) + 1;
                int targetTileX = game.dungeon.playerCharacter.currentTile.x;
                int targetTileY = game.dungeon.playerCharacter.currentTile.y;
            
                switch(game.dungeon.playerCharacter.direction)
                {
                    case 0: targetTileX += 1;
                        break;
                    case 1: targetTileY -= 1;
                        break;
                    case 2: targetTileX -= 1;
                        break;
                    case 3: targetTileY += 1;
                        break;
                    default: targetTileX += 1;
                }
            
                if (targetTileX  >= 0 && targetTileX < DungeonRunner.xLength && targetTileY >= 0 && targetTileY < DungeonRunner.yLength && game.dungeon.tileList[targetTileX][targetTileY] instanceof DungeonTile && game.dungeon.tileList[targetTileX][targetTileY].character instanceof Character)
                {
                    game.dungeon.tileList[targetTileX][targetTileY].character.currentHealth -= damage;
                    game.dungeon.tileList[targetTileX][targetTileY].character.isHit = true;
                    System.out.println(game.dungeon.tileList[targetTileX][targetTileY].character.currentHealth);
                    if(game.dungeon.tileList[targetTileX][targetTileY].character.currentHealth <= 0)
                    {
                        if(game.dungeon.tileList[targetTileX][targetTileY].character instanceof Jam)
                        {
                            ((Jam)(game.dungeon.tileList[targetTileX][targetTileY].character)).onDeath();        
                        }
                        
                        
                    }
                }
            }

            if(KeyboardInput.boolIsInteracting == true)
            {
                if (game.dungeon.playerCharacter.currentTile.itemID != 0)
                {
                    if (game.dungeon.playerCharacter.currentTile.itemID == 1)
                    {
                        System.out.println("MOENEY");
                        game.dungeon.playerCharacter.goldAmount += game.dungeon.playerCharacter.currentTile.goldAmount;
                        //game.superPlayer.goldCount += game.dungeon.playerCharacter.currentTile.goldAmount;
                        //THIS LINE MUST CHANGE THE VALUE IN TILEARRAY FROM LDungeon. ACCESSING SAME MEMORY?
                        game.dungeon.playerCharacter.currentTile.itemID = 0;
                        game.dungeon.tileList[game.dungeon.playerCharacter.currentTile.x][game.dungeon.playerCharacter.currentTile.y].itemID = 0;
                        //isChange = true;
                            
                    }
                    
                    if (game.dungeon.playerCharacter.currentTile.itemID == 2)
                    {
                        System.out.println("");
                    }
                        //game.tileArray = DungeonRunner.tileList;
                }
                
                if(game.dungeon.playerCharacter.currentTile.tileID == 2)
                {
                    System.out.println("NEXT LEVEL");
                    //game.superPlayer.goldCount = game.dungeon.playerCharacter.riches;
                    game.dungeon.currentFloor++;                    
                    game.dungeon = new DungeonRunner(1,1,1,100,100,game.dungeon.currentFloor, game.dungeon.playerCharacter);
                    game.tileArray = DungeonRunner.tileList;
                    //isChange = true;
                    
                    //If this is the last floor, we export stuff from player and set ingame to false;
                    /*
                    if game.dungeon.currentFloor > game.numFloors
                    {
                        game.playerData = game.dungeon.playerCharacter;
                        inGame = false;
                    }
*/
                }
                
                
                
                System.out.println("Interacting woah");
                KeyboardInput.boolIsInteracting = false; 
            }
            
            while(isEnemyTurn)
            {
                for (Enemy i: game.dungeon.enemyList)
                {
                    if(i instanceof Jam)
                    {
                        ((Jam)i).act(game.dungeon);  
                        //game.repaint();
                    }   
                    
                    //else if other stuff
                isEnemyTurn = false;
                }
            }
            /*
            while(isChange)
            {
                //game.revalidate();
                //game.repaint();
                isChange = false;
            } */         
        }
    }
        //Insert what you need to test here
//Insert test methods below
//Insert test methods above

//Methods that already work
/////////////////////////////////////////////////////////////////Method 0: Creating the Window
   public static void createWindow()
   {
       //window.setSize((int)(100),(int)(100));//Sets size in pixels based on player's screen
       window.setSize((int)(screenRes.getWidth()/2), (int)(screenRes.getWidth()/2.2) );
       int windowX = (int) (window.getWidth());//grabs the width of window made
       int windowY = (int) (window.getHeight());//grabs the height of window made
		int windowPosX = (int)(screenRes.getWidth() - windowX)/2;
		int windowPosY = (int)(screenRes.getHeight() - windowY)/2;
       //Creating the frame
       JFrame.setDefaultLookAndFeelDecorated(true);//allows for customization of icons/windows/etc.
       Toolkit toolkit = Toolkit.getDefaultToolkit();//uses the toolkit, which allows for reading of image file
       Image icon = toolkit.getImage("images/icon.png");
       window.setResizable(false);
       window.setIconImage(icon); //Sets icon image
       KeyboardInput input = new KeyboardInput(); //Used for keyboard input
       window.addKeyListener(input);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
       window.setLocation(windowPosX, windowPosY);//sets location to center
       //window.setLocationByPlatform(true);
       window.setVisible(true);//Makes it visible...
   }
   
   //Run when panel has started.
   public void addNotify()
   {
       super.addNotify();
       animator = new Thread(this);
       animator.start();       
   }
   
//Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
//Calling game.repaint() will update what's in here.
   @Override
   public void paintComponent(Graphics g)
   {
       visibleCharacters.clear();
       BufferedImage image = null;
       super.paintComponent(g);//we have to do super because magic
       //Graphics2D g2 = (Graphics2D) g;
       //Border lineborder = 
       //g.setStroke(new BasicStroke(10));
       g.setColor(Color.red);
       final int numTilesX = 16;
       final int numTilesY = 9;
       
       //Needed length and height of tiles in pixels
       int tileLengthX = (int) screenRes.getWidth()/2 / numTilesX;
       int tileLengthY = (int) (screenRes.getWidth()/2.2) / numTilesY;
       int enemyNumber = 0;
       int screenShakeX = 0;
       int screenShakeY = 0;
       
       if(recentDeadCharList.size() != 0)
       {
           screenShakeX = (int)(20 * Math.random() - 10);
           screenShakeY = (int)(20 * Math.random() - 10);
       }
       
       for (int i = 0; i < numTilesX; i++)
       {
           for (int j = 0; j < numTilesY; j++)
           {
               //System.out.print(dungeon.playerCharacter.currentTile.x - numTilesX/2 + i + " ");
               //System.out.println(dungeon.playerCharacter.currentTile.y - numTilesY/2 + j);
               //Draw tiles
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
               
               g.drawImage(image, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, image.getWidth(null), image.getHeight(null), null);
               
               //Draw bodies
               if (drawnTile instanceof DungeonTile && drawnTile.deadCharacter instanceof DeadCharacter)
               {
                   if (drawnTile.deadCharacter.prevCharacter instanceof Jam)
                   {
                       Image slimeImage = null;
                       switch(drawnTile.deadCharacter.prevCharacter.direction)
                       {
                       case 0: slimeImage = slimeImageEastDead;
                           break;
                       case 1: slimeImage = slimeImageNorthDead;
                           break;
                       case 2: slimeImage = slimeImageWestDead;
                           break;
                       case 3: slimeImage = slimeImageSouthDead;
                           break;
                       default: slimeImage = slimeImageEastDead;
                       }
                       
                       g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);

                   }
               }
               //Draw money
               if (drawnTile instanceof DungeonTile && drawnTile.itemID != 0)
               {
                   if (drawnTile.itemID == 1)
                   {
                       image = money;
                       g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (int)((.5+j) * tileLengthY), 0, 0, image.getWidth(null), image.getHeight(null), null);
                   }
               }
               
               //Draw player
               if (drawnTile instanceof DungeonTile && drawnTile.character instanceof Character)
               {
                   visibleCharacters.add(drawnTile.character);
                   if (drawnTile.character instanceof Player)
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
                   //Draw Jam
                   else if (drawnTile.character instanceof Jam)
                   {
                       Image slimeImage = null;
                       if (drawnTile.character.imageID == 0 && drawnTile.character.isHit == false)
                       {
                           switch(drawnTile.character.direction)
                           {
                           case 0: slimeImage = slimeImageEast;
                               break;
                           case 1: slimeImage = slimeImageNorth;
                               break;
                           case 2: slimeImage = slimeImageWest;
                               break;
                           case 3: slimeImage = slimeImageSouth;
                               break;
                           default: slimeImage = slimeImageEast;
                           }
                       }
                       //Jam alt. image
                       else if(drawnTile.character.imageID == 1 && drawnTile.character.isHit == false)
                       {
                           switch(drawnTile.character.direction)
                           {
                           case 0: slimeImage = slimeImageEastWalk;
                               break;
                           case 1: slimeImage = slimeImageNorthWalk;
                               break;
                           case 2: slimeImage = slimeImageWestWalk;
                               break;
                           case 3: slimeImage = slimeImageSouthWalk;
                               break;
                           default: slimeImage = slimeImageEastWalk;
                           }
                       }
                       
                       else if(drawnTile.character.isHit == true)
                       {
                           switch(drawnTile.character.direction)
                           {
                           case 0: slimeImage = slimeImageEastHit;
                               break;
                           case 1: slimeImage = slimeImageNorthHit;
                               break;
                           case 2: slimeImage = slimeImageWestHit;
                               break;
                           case 3: slimeImage = slimeImageSouthHit;
                               break;
                           default: slimeImage = slimeImageEastHit;
                           }
                       }
                       
                       
                       g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                   }
               }
           }      
       }
       
       //Death animations
       for (DeadCharacter i : recentDeadCharList)
       {
           if (i.prevCharacter instanceof Jam)
           {
               Image slimeImage = null;
               switch(i.prevCharacter.direction)
               {
               case 0: slimeImage = slimeImageEastHit;
                   break;
               case 1: slimeImage = slimeImageNorthHit;
                   break;
               case 2: slimeImage = slimeImageWestHit;
                   break;
               case 3: slimeImage = slimeImageSouthHit;
                   break;
               default: slimeImage = slimeImageEastHit;
               }
               
               if (i.deathTimer <= 0)
               {
                   recentDeadCharList.remove(i);
               }
               
               g.drawImage(slimeImage, i.prevCharacter.currentTile.x * tileLengthX + 25, i.prevCharacter.currentTile.y * tileLengthY + 25, (i.prevCharacter.currentTile.x+1) * tileLengthX, (i.prevCharacter.currentTile.y+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
               
               
           }
       }

       
       
       //UI is drawn last so it's on top of everything else.
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
       
       int goldNum = dungeon.playerCharacter.goldAmount;
       numCounter = 0;
       oneDigit = 0;
       
       do
       {
           if(goldNum > 9)
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
         g.drawImage(world.map, 0, 0, (int)(screenRes.getWidth()/2/2),(int)(screenRes.getWidth()/2/2.2),null);
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
@Override
    public void run()
    {
        long previousTime, sleepTime, timeDifference;
        int counter = 0;
        previousTime = System.currentTimeMillis();
        while (true)
        {
            repaint();
            timeDifference = System.currentTimeMillis() - previousTime;
            sleepTime = DELAY - timeDifference;
            if (sleepTime < 0)
            {
                sleepTime = 2;
            }
            
            try
            {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            previousTime = System.currentTimeMillis();
            counter++;
            // Note: change to visible enemyList.
            for (Character i : visibleCharacters)
            {
                if (counter%i.altTimer == 0)
                {
                    if (i.imageID == 0)
                    {
                        i.imageID = 1;
                    }
                    
                    else if(i.imageID == 1)
                    {
                        i.imageID = 0;
                    }
                }
                
                if (i.isHit == true)
                {
                    i.currentHitTime--;
                    
                    if(i.currentHitTime <= 0 )
                    {
                        i.isHit = false;
                        i.currentHitTime = i.hitTimer;
                    }   
                }
            }
            //Complains about multiple modifications. Need to fix.
            if(recentDeadCharList.size() != 0)
            {
                for(DeadCharacter j : recentDeadCharList)
                {
                    j.deathTimer--;
                }
            }
        }
    }
}
