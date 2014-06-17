//Stirling's Approximation = n! = sqrt(2*PI*n)*(n/e)^n = Big O better than linear
//Notes: If we ever have a loading screen, I want to use the blue screen of death!
//pack() = resize to smallest area for Window based on components' sizes
import java.util.ArrayList;
import java.awt.*;//This is the java toolkit, includes Color, Dimension, Graphics, etc.
import java.awt.event.*;//Event Listeners
import java.awt.image.*;//Image classes allow for storage/usage of image files
import javax.swing.*;//Part of UI, includes JPanel
import java.awt.Polygon;

public class LegacyDungeon extends JPanel
{
	/*variables that'll be used EVERYWHERE*/
	static Window window;//creates Window
	static Menu menu;//runs menu
	static WorldMap world;//runs world map
	static DungeonMain dungeon;//runs dungeon
	static MusicPlayer musicPlayer = new MusicPlayer();//musicPlayer class plays music
	static int turnCounter;//counts turns
	static boolean running;
	static boolean inMenu;
	static boolean inGame;
	static boolean inWorldMap;
	static boolean inDungeon;	
	static LegacyDungeon game;

	/*menu related variables*/
	static Button button;

	/*world map related variables*/
	static Button OKButton;
	static boolean message1;
	MaxAreaPolygon maxAreaPolygon;
	static ArrayList<Node> validList;
	static int validListSize;	
	int xPosition = Window.windowX - 150;//position of the moon in
	int yPosition = 0;
	static float XFactor;//opacity of force field
	Polygon thePolygon;

	/*in dungeon related variables*/
	
	public LegacyDungeon()
	{
		window = new Window();//creates a window object, which generates the window
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
/*////////////////////////////////////////////////////////Method 4: paintComponent does the painting. According to Java, everything we want to paint should be placed in this method. Making something alternate between visibility requires the use of ArrayLists, if statements, etc. For example, if we want to spawn enemies, we'll have paintComponent paint images of an ArrayList. When a new enemy is spawned, our ArrayList grows, which results in the new enemy being painted every time we call repaint().*/
    @Override
    public void paintComponent(Graphics g)
    {	
        super.paintComponent(g);//we have to do super because magic happens in the original method
			//Font gameFont = new Font("8 BITWONDER", Font.PLAIN, 52);
			//setFont(gameFont);
			//g.setColor(Color.WHITE);
			//^WHAT THE FUCK DOES THIS HAVE TO DO WITH FONT THE FUCK GO FUCK YOURSELF FUCK FUCK FUCK FUCK YOU
		//Define the translations
		/*menu related painting*/
		if(inMenu)
		{
			//Most back layer first
			//1: Background
			game.setBackground(Color.BLACK);
			g.drawImage(menu.background, 0, 0, Window.windowX, Window.windowY, null);
			//2: Title
			g.drawString("I NEED A BETTER TITLE QQ", (Window.windowX/2-100), (100));
			//3: New Game Button //will add String later
			g.drawImage(menu.newGameButton.currentImage, menu.newGameButton.x, menu.newGameButton.y, menu.newGameButton.length, menu.newGameButton.height, null);
			//4: Load Game Button
			g.drawImage(menu.loadGameButton.currentImage, menu.loadGameButton.x, menu.loadGameButton.y, menu.loadGameButton.length, menu.loadGameButton.height, null);
			//5: Settings Button
			g.drawImage(menu.settingsButton.currentImage, menu.settingsButton.x, menu.settingsButton.y, menu.settingsButton.length, menu.settingsButton.height, null);
			//6: Credits Button
			g.drawImage(menu.creditsButton.currentImage, menu.creditsButton.x, menu.creditsButton.y, menu.creditsButton.length, menu.creditsButton.height, null);
			//7: Version
			g.drawString("Pre-Alpha Stage", Window.windowX-100, Window.windowY-100);
		}
		/*world map related painting*/
		else
		{
			if(inWorldMap)
			{
				//Basically, to make it so that we can scroll (as opposed to remaining stuck in an unmoving screen), I have to move the background as opposed to the character itself. The character itself will always remain at the center of the screen. originPoint is the point we'll be subtracting from the position of the current node to get the AMOUNT MOVED. We then apply the AMOUNT MOVED to the background image instead. It is windowX/2 - char.width/2 because we want to draw the character in the middle, and the window is ifuck I can't explain clearly
				int originPointX = Window.windowX/2 - world.character.width/2;
				int originPointY = Window.windowY/2 - world.character.height/2;			
				//Most back layer first
				//1: Background						
				int elseX = Window.windowX - 2*world.playerX;
				int elseY = Window.windowY - 2*world.playerY;
				//Most back layer first
				//1
				g.drawImage(world.map, 0, 0, (int)(Window.windowX), (int)(Window.windowY), null);
				//2
				for(int i = 0; i< world.nodeList.size(); i++)
				{
					g.drawImage(world.nodeList.get(i).image, world.nodeList.get(i).x-25, world.nodeList.get(i).y-25, 30, 30, null);
				}
				//3
				if(yPosition <50)
					yPosition++;
				if(yPosition > 50)
					yPosition--;							
				g.setColor(Color.WHITE);
				g.fillOval(0, 0 , 100, 100);

				//4
				for(int i = 0; i < world.nodeList.size(); i++)
				{
					if(world.nodeList.get(i).status == 2)
						validList.add(world.nodeList.get(i));
				}
				int currentSize = validList.size();
				float red = 1, green = 1, blue = 1, alpha = 0.8f;
				if(currentSize != maxAreaPolygon.refVar && currentSize >= 3)
				{
					thePolygon = maxAreaPolygon.makePolygon(validList);
					System.out.println(XFactor);
					System.out.println(red + " " + blue + " " + green + " " + alpha);
				}	
				XFactor = maxAreaPolygon.refVar;
				//0.3, 0.6, 1.0, 0.8 are original rgba
				red = 0.15f*(float)XFactor;
				green = 0.3f*(float)XFactor;
				blue = 0.5f*(float)XFactor;
				alpha = 0.2f*(float)XFactor;
				if(red>1)
					red = 1;
				if(green>1)
					green = 1;
				if(blue>1)
					blue = 1;
				if(alpha>0.8)
					alpha = 0.8f;
				Color forceField = new Color(red, green, blue, alpha);
				g.setColor(forceField);
				if(thePolygon != null)
					g.fillPolygon(thePolygon);
				validList.clear();
				if(message1)
				{
					g.drawImage(menu.message1, (int)(Window.windowX/4), (int)(Window.windowY/4), (int)(Window.windowX/1.5), (int)(Window.windowY/3), null);
					g.drawImage(OKButton.currentImage, (int)(Window.windowX/2 + 300) , (int)(Window.windowY/2 - 10), 90, 50, null);
				}
				
			}
			else
			{
				if(inDungeon)//Only paint if within dungeon
				{
				}
			}
		}
	}
}
