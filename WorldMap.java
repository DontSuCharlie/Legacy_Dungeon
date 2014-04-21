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
//Might need to use threads for music...
*/
import java.util.*;
import java.awt.image.*;
import java.imageio.*;
import java.io.*;
import java.awt.Polygon;//for polygon construction
public class WorldMap
{
	//Fields
	Image map;//background image of map
	int turn;//current turn
	public static ArrayList<NodeWorld> nodeList = new ArrayList<NodeWorld>();//array list of nodes
	int numNodes;//number of nodes to be made
	int innerNodes;//splitting the number of nodes into 3 zones
	int midNodes;
	int outerNodes;//to the heart, and a lower node concentration outside
	int nodesLeft;
	private Dimension screenRes;//dimension of screen
	int screenResX;//width of screen
	int screenResY;//height of screen
	
	//Constructor
	public WorldMap()
	{
		numNodes = (int) (Math.random() * 20) + 20;//generates a random number of nodes
		/*
		nodesLeft = numNodes;
		for(int i = 2; i < numNodes; i++)
		{
			if(numNodes%i == 0)
			{
				innerNodes = numNodes/i;
				nodesLeft = numNodes - innerNodes;
			}
		}
		for(int i = 2; i < nodesLeft; i++)
		{
			if(numNodes%i == 0 && numNodes/i < nodesLeft)
			{
				midNodes = numNodes/i;
				outerNodes = nodesLeft - midNodes;
			}
		}
		*/
		map = map();
		screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
		screenResX = (int) (screenRes.getWidth());
		screenResY = (int) (screenRes.getHeight());
	}
//////////////////////////////////Methods here///////////////////////////////////////
/*
Method 0: .map() loads an image used as the World Background (world map animation will be added later!)
Method 1: .assignNodePos() creates a random amount of nodes and assigns them positions
//You've got to load the images (will be part of NodeWorld.java)
//
Method 2: .playerMove() loads player sprite, positions the player based on previous dungeon (if no previous then starts at Heart), makes sure the player CAN ONLY BE ON NODES. Will also make sure turn++;
Method 3: .keyListener() - obtains keyboard input. Allows for player movement based on lowest distance in terms of x/y based on position
Method 4: .lineMaker() - makes it so your character sprite moves towards the node in a linear line
Method 5: .polygonDetector() creates polygons based on Safe Nodes
Method 6: .enemyMovement() - will implement later, AI so scarrr
*/

    public static void main(String[] args)
    {
        WorldMap gameMap = new WorldMap();
        //gameMap.map()
        gameMap.assignNodePos();
        
        
    }

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
/* WILL WORK ON DISTRIBUTION OF NODES _AFTER_ NORMAL DISTRIBUTION OF NODES WORKS
	//Method 0: Creates nodes and assigns different positions for inner nodes
	public static void assignInnerNodes()
	{
		for(int i = 0; i < innerNodes; i++)
		{
			nodeList.add(i, new NodeWorld(Math.random()*screenResX, Math.random() * screenResY));
			boolean check = true;
			while(check)
			{
				for (int j = 0; j < i; j++)
				{
					int dontWantX = Math.abs(nodeList.get(i).x - nodeList.get(j).x);
					int dontWantY = Math.abs(nodeList.get(i).y - nodeList.get(j).y);
					int counter = 0;
					if(dontWantX <= 20 && dontWantY <= 20)
					{
						nodeList.get(i).x = Math.random()*screenResX;
						nodeList.get(i).y = Math.random()*screenResY;
						counter++;
					}
				*/
	//Method 1: Creates nodes and assigns different positions
	public static void assignNodePos(){
		for(int i = 0; i < numNodes; i++){
			nodeList.add(i, new NodeWorld(Math.random()*screenResX, Math.random()*screenResY), 0, 0);//will change the skillID/theme parameter inputs later
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
						check = false;
				}
			}
		}
	}
	//Method 5: PolygonDetector
	public static void polygonDetector()//This method creates polygons
	{
		//Creates an arraylist of nodes with Sanctuary status
		ArrayList<NodeWorld> vertexList = new ArrayList<NodeWorld>();
		for(int i = 0; i < nodeList.size(); i++)
		{
			if (nodeList.get(i).nodeStatus == 2)
			{
				vertexList.add(nodeList.get(i));
			}
		}
		if(nodeList.size() > 2)
		{
			int[] xCoord = new int[nodeList.size()];
			for(int i = 0; i < xCoord.length; i++)
			{
				xCoord[i] = nodeList.get(i).x;
			}
			int[] yCoord = new int[nodeList.size()];
			for(int i = 0; i < yCoord.length; i++)
			{
				yCoord[i] = nodeList.get(i).y;
			}
			Polygon safeZone = new Polygon(xCoord,yCoord,nodeList.size());
			//will add maximum area later
		}
	}
		//Scan each node and identify the one with lowest distance from each other starting from arraylist.get(0)
		//With lowest distance, use java.paint to draw a line using the two points as vertices
		//change status of node to taken. If it is taken, then it cannot be used to form new line
		//repeat until polygon is formed
		//NOTE MIGHT WANT TO CHANGE TO FIND POLYGON WITH MAXIMUM AREA
	}
				//If input = Enter, then check availability of dungeon
				//If available, enter dungeon
				//Turn screen to black
	public static boolean playerMove()
	{
		//Load Image
		
		//Take input for movement
		
		//Based on nodeDetector, makes a list of available nodes to jump to
		nodeDetector(playerX, playerY);
		//Based on lineMaker, make sure it follows the lines
		lineMaker(playerX, playerY);
		//If press enter, jump out of World Map and into dungeon
		if()
		{
			//turn screen to black
			//clears arraylist so when we exit the dungeon we won't have access to previous not-available nodes. Little game playing mofos think they ould exploit this mofo glitch? hell nooooo
			//return true
			//returning true will make LegacyDungeon.java call on Dungeon.java
		}
		return false;
	}
	//Method 3: Detects closest nodes. Will be called by .playerMove() so dwai
	public static void nodeDetector(int playerX, int playerY)
	{
		//Grab current location of playerMove
		//Creates a circle around the character. Looks for nodes
		int searchRadius = 1;
		int originX = playerX;
		int originY = playerY;
		int numValidPoints = 0;
		boolean pointsNotFound = true;
		//Find nodes nearest to it, USE THE MOST FUCKING EFFICIENT SORT MOFO MWAHAHAH
			//start with a small circle. If no nodes detected, increase radius of circle
			//repeat until at least 1 node is detected
		while(pointsNotFound)
		{
			for(int i = 0; i < nodeList.size(); i++)
			{
				if(Math.abs(nodeList.get(i).x - originX) <= searchRadius && Math.abs(nodeList.get(i).y - originY) <= searchRadius)
				{
					//Adds to arraylist of available nodes
					numValidPoints+=5;//increases radius by 5 pixels
					pointsNotFound = false;
				}
			}
		}
		//add keyboard movement function later. Too complicated for now
			//Look for the most predomininat x/y node, assign node value??
			//actually, we could just assign each point a "turn". If within x proximity, then movement equals 1 turn. You can just choose whichever one you want. idk, we'll see
		//
	}
	public static void lineMaker()
	{
		//Grab node player is on
		//Grab arraylist of available nodes
		//Look for the one closest to the LEFT
		//Look for the one closest to the RIGHT
		//Look for the one closest to the TOP
		//Look for the one closest to the BOTTOM
		//WHAT IF THE ONE CLOSEST TO TOP AND RIGHT?
			//WELL, BC IT IS A CIRCULAR RADIUS, AND BC THE CIRCLE INCREASES BY 5 (LESS THAN 20), THEN IT IS UNLIKELY, IF AT ALL POSSIBLE, FOR TWO NODES TO HAVE EQUAL LEFT/RIGHT/TOP/BOTTOM
		//Detect input
		//Find difference in x and y
		//VECTORS?!
		//Move the character at a rate of y/x until it reaches the point. Then stop
	}
}