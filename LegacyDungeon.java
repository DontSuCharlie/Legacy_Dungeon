import java.util.ArrayList;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import java.awt.event.*;
import java.awt.image.*;//image class allows for storage of image files
import javax.swing.*;//part of UI, includes JPanel

public class LegacyDungeon extends JPanel
{
	//Global Variables here
	ArrayList<NodeWorld> nodeList;
	static JFrame window;
	WorldMap world;
	static int turnCounter = 0;//will read safe file. turn should be 0 if save file not found or corrupt
	//what happens when we load LegacyDungeon
	public LegacyDungeon()
	{
		window = new JFrame("Legacy Dungeon");
		world = new WorldMap();
		nodeList = world.getNodeList();
	}
	//Main Method
	public static void main(String[] args)
	{
		boolean running = true;
		LegacyDungeon game = new LegacyDungeon();
		createWindow();//creates window
		window.add(game);//adds JPanel to window
		game.createWorld(turnCounter);
		while(running)//new problem: nodes don't appear unless you repaint, might need to force repaint
		{
			//loadMenu();
			boolean inGame = true;
			while(inGame)
			{
				game.createWorld(turnCounter);
				//loadDungeon(createWorld(turnCounter));
			}
		}
		//if running is false, rewrite save file, close all threads, and end program
		//NOTE: might need to change parameters based on save file
	}
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
		window.setSize(1000,1000);//Sets size in pixels
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
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
		window.setLocation(windowPosX, windowPosY);//sets location to center
		window.setVisible(true);//Makes it visible...
}
//Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
//Calling game.repaint() will update what's in here.
//Testing if
	@Override
	public void paintComponent(Graphics g)
	{
			super.paintComponent(g);//we have to do super because magic
			if(true)
			{
				g.drawImage(world.map, 0, 0, null);
			}
			for(int i = 0; i < nodeList.size(); i++)
			{
				g.drawImage(nodeList.get(i).nodeImage,nodeList.get(i).x,nodeList.get(i).y,null);
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