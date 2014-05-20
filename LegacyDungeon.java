import java.util.ArrayList; //Needed for arraylist
import java.awt.*;//Java toolkit. Includes Color, Dimension, Graphics, etc.
import java.awt.event.*;//event listeners
import java.awt.image.*;//image class allows for storage/usage of image files
import javax.swing.*;//part of UI, includes JPanel
//Note to self: If we ever have a loading screen, I want to do the blue screen of death!

//pack() = resize to smallest area for Window;
//after drawing a shape it is merely pixels, so no, you cannot fill a rectangle after it's been drawn
public class LegacyDungeon extends JPanel
<<<<<<< HEAD
{ 
    ArrayList<NodeWorld> nodeList;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;//will read save file. Turn counter will be 0 if save file is not found or is corrupt.
    //Constructor
    public LegacyDungeon()
    {
        window = new JFrame("Legacy Dungeon");
        world = new WorldMap();
        nodeList = world.getNodeList();
        Config config = new Config();//What is this for?
    }
    //Main method
    public static void main(String[] args)
    {
        boolean running = true;
        LegacyDungeon game = new LegacyDungeon();
        createWindow();
        window.add(game);
        game.repaint();
        while(running)
        {
            //loadMenu();//loads menu screen
            boolean inGame = true;//should be global
            while(inGame)
            {
                loadDungeon(createWorld(turnCounter));
            }
        }
        //if running is false, rewrite save file, close all threads, and end program
        //saveUpdate();//note, make sure saveUpdate runs periodically OR will be executed EVERYTIME IT CLOSES
        //thread.close();
        //end();
        //NOTE: might need to change parameters based on save file
    }
=======
{
	ArrayList<NodeWorld> nodeList;
	static JFrame window;
	WorldMap world;
	static int turnCounter = 0;//will read save file. Turn counter will be 0 if save file is not found or is corrupt.
	//Constructor
	public LegacyDungeon()
	{
		window = new JFrame("Legacy Dungeon");
		world = new WorldMap();
		nodeList = world.getNodeList();
		Config config = new Config();//What is this for?
	}
	//Main method
	public static void main(String[] args)
	{
		boolean running = true;
		LegacyDungeon game = new LegacyDungeon();
		createWindow();
		window.add(game);
		game.repaint();
		while(running)
		{
			//loadMenu();//loads menu screen
			boolean inGame = true;//should be global
			while(inGame)
			{
				loadDungeon(createWorld(turnCounter));
			}
		}
		//if running is false, rewrite save file, close all threads, and end program
		//saveUpdate();//note, make sure saveUpdate runs periodically OR will be executed EVERYTIME IT CLOSES
		//thread.close();
		//end();
		//NOTE: might need to change parameters based on save file
	}
>>>>>>> 4571668650b3e1ba7a32f5689b5e0793545494e6
////////////////////////////////////LIST OF METHODS///////////////////////////////////////////
/*
createWindow() creates the JFrame - need to make Window size dependent on options
paint(Graphics g) updates the screen - need to update
loadMenu() loads the menu screen - need to make
loadDungeon(boolean b), if true, load dungeon map. If false, do nothing - ask Anish to finish
createWorld(int turn), takes the current turn #, adjusts difficulty of newly generated dungeons, and creates/updates the world map. - need to finish
???() plays background music - need to make
????() animation? - need to make
*/
////////////////////////////////////////////////////////Method 0: Creating the Window
   public static void createWindow()
   {
      Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
      window.setSize((int)(screenRes.getWidth()/2),(int)(screenRes.getWidth()/2.2));//Sets size in pixels based on player's screen
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
    public void paintComponent(Graphics g)
    {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        super.paintComponent(g);//we have to do super because magic
        //Graphics2D g2 = (Graphics2D) g;
        //Border lineborder = 
        //g.setStroke(new BasicStroke(10));
        g.setColor(Color.red);
        if(true)
        {
            g.drawImage(world.map, 0, 0, (int)(screenRes.getWidth()/2),(int)(screenRes.getWidth()/2.2),null);
        }
        for(int i = 0; i < nodeList.size(); i++)
        {
            g.drawImage(nodeList.get(i).nodeImage,nodeList.get(i).x,nodeList.get(i).y,20,20,null);
        }
        ArrayList<NodeWorld> validList = new ArrayList<NodeWorld>();
        for(int i = 0; i < nodeList.size(); i++)
        {
            if(nodeList.get(i).nodeStatus == 2)
            {
                validList.add(nodeList.get(i));
            }
        }
        if(validList.size() >= 3)
        {
            g.drawPolygon(maxAreaPolygon.makePolygon(validList));
        }
    }
/////////////////////////////////////////Method 2: Loads menu screen
/////////////////////////////////////////Method 3: Creating and loading the World Map
    public boolean createWorld(int turnCounter)
    {
        boolean inWorldMap = true;//when this turns false, createWorld stops running
        //Always loads map image
        //Since paintComponent requires everything to be in it, this might just turn world.map's boolean to true
        //but i need to test whether or not if functions work in here
        if(turnCounter == 0)//if this is the beginning, then we have to generate nodes and set character to heart node
        {
            world.assignNodePos();
            nodeList = world.getNodeList();
            //Polygon polygon = world.polygonMaker();
            //Polygon polygon = polygonMaker.makePolygon(nodeList, 
            //loads character sprite and sets them to the Heart Node as a starting position
            //world.startCharacter(); NOT YET
        }
        //Continue on normally
        /*
        while(inWorldMap)//continues this loop until player chooses to enter dungeon OR exit map
        {
            //Takes input, updates character sprite/movement depending on what they player pressed. Will confirm player movement and then run enemyMove() to update. If the player chooses to enter the dungeon, the method returns true, else it would just keep running. If the player chooses to leave the game, the method would return false
            if(world.playerMove())
            {
                turnCounter++;
                return true;
            }
            inWorldMap = false;
            //SOMETHING HERE. I MIGHT BE MISSING SOMETHING. I FEEL LIKE I'M MISSING SOMETHING. THIS NOTE SHOULD BE KEPT UNTIL WE FIND THE MISSING COMPONENT.
        }
        */
        this.turnCounter++;
        return false;
    }
/////////////////////////////////////////Method 4: Creating, loading, and updating Dungeon
//TO ANISH: I've moved every dungeon related method under this category. Please turn it into 1 method
    //Reused in character, inefficient. :<

    /*Obsolete with 2D array.
    public DungeonTile findTile(int x, int y)
    {
        for (int i = 0; i < DungeonRunner.tileList.length; i++)
        {
            if ((DungeonRunner.tileList.get(i).x == x) && (DungeonRunner.tileList.get(i).y == y))
            {
                return DungeonRunner.tileList.get(i);
            
            }
        }
    }
*/

   @Override
   public void paintComponent(Graphics g)
   {
      Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
      super.paintComponent(g);//we have to do super because magic
      //Graphics2D g2 = (Graphics2D) g;
      //Border lineborder = 
      //g.setStroke(new BasicStroke(10));
      g.setColor(Color.red);
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
   }
//Method 2:
/////////////////////////////////////////Method 3: Creating and loading the World Map
   public boolean createWorld(int turnCounter)
   {
      boolean inWorldMap = true;//when this turns false, createWorld stops running
      //Always loads map image
      //Since paintComponent requires everything to be in it, this might just turn world.map's boolean to true
      //but i need to test whether or not if functions work in here
      if(turnCounter == 0)//if this is the beginning, then we have to generate nodes and set character to heart node
      {
         world.assignNodePos();
         nodeList = world.getNodeList();
         Polygon polygon = world.polygonMaker();
         //Polygon polygon = polygonMaker.makePolygon(nodeList, 
         //loads character sprite and sets them to the Heart Node as a starting position
         //world.startCharacter(); NOT YET
      }
      //Continue on normally
      /*
      while(inWorldMap)//continues this loop until player chooses to enter dungeon OR exit map
      {
         //Takes input, updates character sprite/movement depending on what they player pressed. Will confirm player movement and then run enemyMove() to update. If the player chooses to enter the dungeon, the method returns true, else it would just keep running. If the player chooses to leave the game, the method would return false
         if(world.playerMove())
         {
            turnCounter++;
            return true;
         }
         inWorldMap = false;
         //SOMETHING HERE. I MIGHT BE MISSING SOMETHING. I FEEL LIKE I'M MISSING SOMETHING. THIS NOTE SHOULD BE KEPT UNTIL WE FIND THE MISSING COMPONENT.
      }
      */
      this.turnCounter++;
      return false;
   }
    
    // Performed after player movement.
    public boolean drawDungeon(int turnCounter)
    {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
        //How many tiles are drawn, including that of the player.
        final int numTilesX = 11;
        final int numTilesY = 11;
        //Needed length and height of tiles in pixels
        int tileLengthX = (int) screenRes.getWidth() / numTilesX;
        int tileLengthY = (int) screenRes.getHeight() / numTilesY;
        int currentX;
        int currentY;
        //Maybe remove?

        int playerX = player.currentTile.x;
        int playerY = player.currentTile.y;

        for (int i = 0; i < numTilesX; i++)
        {
            for (int j = 0; j < numTilesY; j++)
            {
                DungeonTile drawnTile = DungeonRunner.tileList[i][j];
                // Draws a row of tiles.
                g.drawImage(drawnTile.tileImage, drawnTile.x, drawnTile.y, i * tileLengthX, j * tileLengthY, null);
            }
        }
    }
}
