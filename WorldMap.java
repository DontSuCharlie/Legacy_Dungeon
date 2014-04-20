/* WorldMap.java tells LegacyDungeon.java exactly what to load, it will have the following attributes:
TO-DO LIST:
1] MIGHT NEED TO CONSIDER POINTS NOT TO BE TOO RANDOM
2] 
1] Background (image file) CHECK (just need LegacyDungeon to load this mofo)
2] Creates nodes (uses NodeWorld.java) CHECK (got this down! fuck yeah! *note it is not very efficient)
3] Does weird math to create polygons
4] Has methods that updates the nodes (including enemy movement and player movement)
5] Only allows player to move via the weird-math-polygon system
6] Takes input (has a limited set of inputs)
7] Loads game piece icon (statuses of the stuff)
8] Music
9] Movement sound
*/
import java.util.*;
import java.awt.image.*;
import java.imageio.*;
import java.io.*;
public class WorldMap
{
	//Fields
	Image map;//background image of map
	int turn;//current turn
	public static ArrayList<NodeWorld> nodeList = new ArrayList<NodeWorld>();
	int numNodes;
	private Dimension screenRes;
	int screenResX;
	int screenResY;
	//Constructor
	public WorldMap()
	{
		this.numNodes = (int) (Math.random() * 20) + 20;
		this.map = map();
		this.screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
		this.screenResX = (int) (screenRes.getWidth());
		this.screenResY = (int) (screenRes.getHeight());
	}
//////////////////////////////////Methods here///////////////////////////////////////
/*
Method 0: .map() loads an image used as the World Background (world map animation will be added later!)
Method 1: .assignNodePos() creates a random amount of nodes and assigns them positions
//You've got to load the images (will be part of NodeWorld.java)
//
Method 2: .playerPiece() loads player sprite, positions the player based on previous dungeon (if no previous then starts at Heart), makes sure the player CAN ONLY BE ON NODES. Will also make sure turn++;
Method 3: .keyListener() - obtains keyboard input. Allows for player movement based on lowest distance in terms of x/y based on position
Method 4: .lineMaker() - makes it so your character sprite moves towards the node in a linear line
Method 5: .polygonDetector() creates polygons based on Safe Nodes
Method 6: .enemyMovement() - will implement later, AI so scarrr
*/
	public static Image map()//This method is used to prevent the game from crashing if it can't locate the image
	{
		BufferedImage map = null;
		try
		{
			map = ImageIO.read(new File("TempWorldMap.jpg"));
		}
		catch (IOException e)
		{
		}
		return map;
	}
	//Method 1: Creates nodes and assigns different positions
	public static void assignNodePos(){
		for(int i = 0; i < numNodes; i++){
			nodeList.add(i, new NodeWorld(Math.random()*screenResX, Math.random()*screenResY));
			boolean check = true; //for checking purposes
			while(check){
				for(int j = 0; j < i; j++){
					int dontWantX = Math.abs(nodeList.get(i).x - nodeList.get(j).x);
					int dontWantY = Math.abs(nodeList.get(i).y - nodeList.get(j).y);
					int counter = 0;
					// Checking a range if far enough from other nodes. Basically, the range from (xPos - 20) to (xPos + 20) must be both less or both greater than the other nodes available.
					if(dontWantX <= 20 && dontWantY <= 20)
					{
						nodeList.get(i).x = Math.random()*screenResX;
						nodeList.get(i).y = Math.random()*screenResY;
						counter++;
					}
					if(counter > 0)//should check if need brackets here......
						check = true;
					else
						check = false
				}
			}
		}
	}
	//Method 5: PolygonDetector
	public static void polygonDetector()//This method creates polygons
	{
		//
	}
	//Method 3:
}