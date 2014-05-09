package LegacyDungeon
import java.util.ArrayList; //Needed for arraylist
import java.awt.*;//Java toolkit. Includes Color, Dimension, Graphics, etc.
import java.awt.event.*;//event listeners
import java.awt.image.*;//image class allows for storage/usage of image files
import javax.swing.*;//part of UI, includes JPanel
//Note to self: If we ever have a loading screen, I want to do the blue screen of death!

public class LegacyDungeon extends JPanel
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
	public static void main(String[] args)
	{
		boolean running = true;
		LegacyDungeon game = new LegacyDungeon();
		createWindow();
		window.add(game);
		loadMenu();
		game.repaint();
		while(running)
		{
			boolean inGame = true;
			while(inGame)
			{
				loadDungeon(createWorld(turnCounter));
			}
		}
		//if running is false, rewrite save file, close all threads, and end program
		//NOTE: might need to change parameters based on save file
/*///////////////////////////////////ORIGINAL NOTES//////////////////////////////////
   //If player enters a dungeon, load dungeon
      //Use node information to generate dungeon floor
      //Takes input.
      //Displays UI that reflects HP, Skill EXP (skill EXP is dependent on use), Cooldown, etc.
      //Displays Timer
      //Every 5 minutes, the enemy forces will have moved by 1 turn (will notify player)
      //Check to see if the player has reached another floor
         //If the player is about to approach the final floor, a new type of floor will be generated - the final floor
      //Generates new map
      //repeat until player reaches final floor
      //If player reaches end of dungeon, dies, or uses Escape Crystal, return to World Map
      //Update World Map with movement of Enemy Forces
   //Checks to see if Final Boss is at the Heart. If so, starts counting down on turns left before game ends
   //If Final Boss succeeds, game ends
   //If player defeats Final Boss, the player wins!
   //Creates a drawing based from a picture file
   */

////////////////////////////////////LIST OF METHODS//////////////////////////////////
/*
Method 0: createWindow() creates the JFrame
Method 1: ???() creates a new thread for playing background music.
Method 2: loadMenu() runs through the start-up screen and then loads the menu. It is in a while loop and won't exit until you press buttons
Method 3: loadWorldMap() is the part that loads the World Map. It is in a while loop and won't exit until you return to menu (which runs the menu) or until you enter a dungeon

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
/*
//Testing if
<<<<<<< HEAD
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
/*
=======
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
   
    //Reused in character, inefficient. :<
    public DungeonTile findTile(int x, int y)
    {
        for (int i = 0; i < DungeonRunner.tileList.size(); i++)
        {
            if ((DungeonRunner.tileList.get(i).x == x) && (DungeonRunner.tileList.get(i).y == y))
            {
                return DungeonRunner.tileList.get(i);
            
            }
        
        }
    
    
    
    }
    
    // Performed after player movement.
    public boolean drawDungeon(turnCounter)
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
                dungeonTile drawnTile = (findTile((playerX-numTilesX + i), (playerY-numTilesY + j)));
                // Draws a row of tiles.
                g.drawImage(drawnTile.tileImage, drawnTile.x, drawnTile.y, i * tileLengthX, j * tileLengthY, null);
        
            }
        
        
        }
        

    }
*/
//Method 4: Entering dungeon;; not yet...
/*
   public static void createDungeon(boolean enterDungeon)
   {
      while(enterDungeon)
      {
      }
   }
*/
    
    
    
    
    
    
}
