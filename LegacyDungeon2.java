import java.util.ArrayList;
import java.awt.*;//This is the java toolkit, includes Color, Dimension, Graphics, etc.
import java.awt.event.*;//Event Listeners
import java.awt.image.*;//Image classes allow for storage/usage of image files
import javax.swing.*;//Part of UI, includes JPanel
import java.awt.Polygon;

public class LegacyDungeon
{
	public LegacyDungeon()
	{
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Legacy Dungeon");
			//Display.setIcon();
	}
	public static void main(String[] args)
	{
		while(!Display.isCloseRequested())
		{
			inGame = true;
			while(inGame)
			{
				loadDungeon(createWorld(turnCounter));
				Display.update();
			}
		}
		Display.destroy();
	}
	/*Method 1: Run Menu Screen*/
	/*Method 2: Run World Map*/
	/*Method 3: Run Dungeon*/
}

public class LegacyDungeon extends JPanel
{

	/*in dungeon related variables*/
	
	public LegacyDungeon()
	{
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("Legacy Dungeon");
		//Display.setIcon();
		world = new WorldMap();//creates a worldmap object, which generates the world map
		turnCounter = 0;//will read save file first. If empty/corrupt, defaults to 0. Implement after project is done
		maxAreaPolygon = new MaxAreaPolygon();//in charge of drawing the polygon in worldmap
		validList = new ArrayList<Node>();//list of nodes that will be fed into maxareapolygon to draw the polygon int he worldmap
	}
	public static void main(String[] args)
	{
		running = true;//means the game is running
		game = new LegacyDungeon();
		Window.createWindow();//creates window
		Window.window.add(game);//adds game file to the window
		while(running)
		{
			loadMenu();//loads menu screen
			inGame = true;//means in game (out of menu screen)
			while(inGame)
			{
				loadDungeon(createWorld(turnCounter));//createWorld loads world map. if turn is first turn, make new nodes, else don't. createWorld returns true if we're in the dungeon
			}
		}
		//rewrite save file here (might need to save periodically, would consider after project is done) + close threads
	}
////////////////////////////////////LIST OF METHODS///////////////////////////////////////////
/*
createWindow() creates the JFrame - need to make Window size dependent on options
paint(Graphics g) updates the screen - need to update
loadMenu() loads the menu screen - need to make
loadDungeon(boolean b), if true, load dungeon map. If false, do nothing
createWorld(int turn), takes the current turn #, adjusts difficulty of newly generated dungeons, and creates/updates the world map. - need to finish
???() plays background music - need to make
????() animation? - need to make
*/
//////////////////////////////////////////////////////////////////////////////////////////////
/*//////////////////////////////////////////////////////////Method 0: Creates Window*/

/*////////////////////////////////////////////////////////Method 1: Loads menu screen*/
	public static void loadMenu()
	{
		musicPlayer.playMusic("sounds/menu0.wav");
		menu = new Menu();
		Window.window.add(menu.newGameButton);
		Window.window.add(menu.loadGameButton);
		Window.window.add(menu.settingsButton);
		Window.window.add(menu.creditsButton);
		inMenu = true;
		game.repaint();
		while(!menu.newGameButton.clicked)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
		}
		Window.window.remove(menu.newGameButton);//have to remove it or stays there
		Window.window.remove(menu.loadGameButton);
		Window.window.remove(menu.settingsButton);
		Window.window.remove(menu.creditsButton);		
		inMenu = false;
	}
/*////////////////////////////////////////////////////////Method 2: Creating and loading the World Map*/
    public static boolean createWorld(int turnCounter)
    {
		musicPlayer.stop();
		//musicPlayer.musicThread.interrupt();
		musicPlayer.playMusic("sounds/Worldmap1.wav");
        inWorldMap = true;//when this turns false, createWorld stops running
        if(turnCounter == 0)//when game begins, we generate nodes and set character to heart node
        {
            world.assignNodePos();//creates nodes
			for(int i = 0; i < world.nodeList.size(); i++)
			{
				Window.window.add(world.nodeList.get(i));
			}
			OKButton = new Button("images/OKOrig.png", "images/OKMoused.png", "images/OKPressed.png", (int)(Window.windowX/2 + 300) , (int)(Window.windowY/2 - 10), 100, 50);
			Window.window.add(OKButton);
			message1 = true;
			if(OKButton.clicked)
			{
				message1 = false;
				Window.window.remove(OKButton);
			}
        }
		//Else, continue on normally
		while(inWorldMap)
		{
			if(OKButton.clicked)
				message1 = false;
			world.nodeDetector();
			game.repaint();
			try
			{
				Thread.sleep(1000);
				if(inDungeon)
					return true;
			}
			catch(InterruptedException e)
			{
				System.out.println(e);
			}
			//A pop-up appears that asks the user whether or not he wants to enter the dungeon
			//if the player selects yes, turnCounter++; inWorldMap = false; this method ends by returning true;
			//if the player selects no, move player back to original position, wait for response
            //SOMETHING HERE. I MIGHT BE MISSING SOMETHING. I FEEL LIKE I'M MISSING SOMETHING. THIS NOTE SHOULD BE KEPT UNTIL WE FIND THE MISSING COMPONENT.
        }
        return false;
    }

/*////////////////////////////////////////////////////////Method 3: Creating, loading, and updating Dungeon*/
	public static void loadDungeon(boolean inDungeon)
	{
		musicPlayer.stop();
		musicPlayer.playMusic("sounds/Worldmap2.wav");
		if(inDungeon)
		{
			System.out.println("You're in the dungeon!");
			try
			{
				DungeonMain runDungeon = new DungeonMain();
				//window.window.add(runDungeon);
				inWorldMap = false;
				runDungeon.dungeonLoop();

				//Window.window.remove(game);
				//runDungeon.runThisMofo();
				//System.out.println("yea");
				//Window.window.add(runDungeon);
				//runDungeon.repaint();
			}
			catch(InstantiationException e)
			{
				System.out.println("Did not instantiate");
			}
			catch(IllegalAccessException e)
			{
				System.out.println("Illegal Accesss");
			}
		}
	}
}
