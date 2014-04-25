import java.util.*;
import java.awt.image.*;
import java.awt.Polygon;
import javax.swing.*;
import java.awt.*;
public class WorldMap extends JPanel
{
	//Fields
	BufferedImage map;//background image of map
	ImageLoader imageLoader;
	public static ArrayList<NodeWorld> nodeList = new ArrayList<NodeWorld>();//array list of nodes
	int numNodes;//number of nodes to be made
	int innerNodes;//splitting the number of nodes into 3 zones
	int midNodes;
	int outerNodes;//to the heart, and a lower node concentration outside
	int nodesLeft;
	boolean concentrateNodes;
	Dimension screenRes1;//dimension of screen
	int screenRes1X1;//width of screen
	int screenRes1Y1;//height of screen
	Polygon polygon;
	//Constructor
	public WorldMap()
	{
		numNodes = (int) (Math.random() * 20) + 20;//generates a random number of nodes
		imageLoader = new ImageLoader();
		map = imageLoader.loadImage("TempWorldMap.jpg");
		concentrateNodes = true;
		if(concentrateNodes)//I can turn concentration of nodes at center on and off for testing purposes. See which one's more balanced/fun
		{
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
		}
		//polygon = polygonDetector();
		screenRes1 = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
		screenRes1X1 = (int) (screenRes1.getWidth()/2) - 100;
		screenRes1Y1 = (int) (screenRes1.getHeight()/2.2) - 100;
	}
//////////////////////////////////Methods here///////////////////////////////////////
/*
Method 0: .assignNodePos() creates a random amount of nodes and assigns them positions
Method 1: .playerMove() loads player sprite, positions the player based on previous dungeon (if no previous then starts at Heart), makes sure the player CAN ONLY BE ON NODES. Will also make sure turn++;
Method 2: .mouseListener() - obtains keyboard input from LegacyDungeon. Then translates input to allow for player movement based on lowest distance in terms of x/y based on position (will add keyListener later)
Method 3: .lineMaker() - makes it so your character sprite moves towards the node in a linear line
Method 4: .polygonDetector() creates polygons based on Safe Nodes
Method 5: .enemyMovement() - will implement later, AI so scarrr
*/
/* WILL WORK ON DISTRIBUTION OF NODES _AFTER_ NORMAL DISTRIBUTION OF NODES WORKS
	//Method 0: Creates nodes and assigns different positions for inner nodes
	public static void assignInnerNodes()
	{
		for(int i = 0; i < innerNodes; i++)
		{
			nodeList.add(i, new NodeWorld(Math.random()*screenRes1X1, Math.random() * screenRes1Y1));
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
						nodeList.get(i).x = Math.random()*screenRes1X1;
						nodeList.get(i).y = Math.random()*screenRes1Y1;
						counter++;
					}
				*/
	//Method 1: Creates nodes and assigns different positions
	public void assignNodePos(){
		for(int i = 0; i < numNodes; i++){
			nodeList.add(i, new NodeWorld((int)(Math.random()*screenRes1X1), (int)(Math.random()*screenRes1Y1), 0, 0));//will change the skillID/theme parameter inputs later
			boolean check = true; //for checking purposes
			for(int j = 0; j < i; j++){
				int counter = 0;
				while(check){
					int dontWantX = Math.abs(nodeList.get(i).x - nodeList.get(j).x);
					int dontWantY = Math.abs(nodeList.get(i).y - nodeList.get(j).y);
					// Checking a range if far enough from other nodes. Basically, the range from (xPos - 20) to (xPos + 20) must be both less or both greater than the other nodes available.
					if(dontWantX <= 20 && dontWantY <= 20)
					{
						nodeList.get(i).x = (int)(Math.random()*screenRes1X1);
						nodeList.get(i).y = (int)(Math.random()*screenRes1Y1);
						counter++;
					}
					if(counter > 0)//should check if need brackets here......
					{
						check = true;
					}
					else
					{
						check = false;
					}
				System.out.println("Node " + i + ": " + nodeList.get(i).x + ", " + nodeList.get(i).y);
				}
			}
		}
	}
	public ArrayList<NodeWorld> getNodeList()
	{
		return nodeList;
	}
	//Method 5: PolygonDetector
	public static Polygon polygonMaker()
	{
		Polygon safeZone = null;
		ArrayList<NodeWorld> potentialList = new ArrayList<NodeWorld>();
		ArrayList<NodeWorld> vertexList = new ArrayList<NodeWorld>();
		NodeWorld largestX = new NodeWorld(0,0,0,0);
		NodeWorld largestY = new NodeWorld(0,0,0,0);
		NodeWorld smallestX = new NodeWorld(10000,10000,0,0);//need to find better numbers in the future for more elegance.
		NodeWorld smallestY = new NodeWorld(10000,10000,0,0);
		for(int i = 0; i < nodeList.size(); i++)
		{
			if(nodeList.get(i).nodeStatus == 2)
			{
				potentialList.add(nodeList.get(i));
			}
		}
		if(potentialList.size() == 3)//if there's only 3, don't even try to maximize area
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
			safeZone = new Polygon(xCoord,yCoord,nodeList.size());
		}
		if(potentialList.size() > 3)//if greater than 3, try to maximize area
		{
			for(int i = 0; i < potentialList.size(); i++)
			{
				//Might implement binary search here, but not skilled enough yet :(
				if(potentialList.get(i).x > largestX.x)
				{
					largestX = potentialList.get(i);
				}
				if(potentialList.get(i).x < smallestX.x)
				{
					smallestX = potentialList.get(i);
				}
				if(potentialList.get(i).y > largestY.y)
				{
					largestX = potentialList.get(i);
				}
				if(potentialList.get(i).y < smallestY.y)
				{
					smallestX = potentialList.get(i);
				}				
			}
			//remove from search list
			potentialList.remove(smallestY);
			potentialList.remove(largestY);
			potentialList.remove(smallestX);
			potentialList.remove(largestX);
			//add to list of actual vertices, in order of drawing
			polygonList.add(smallestX);
			polygonList.add(largestY);
			polygonList.add(largestX);
			polygonList.add(smallestY);
			if(largestY.x-largestX.x == 0)
			{
				System.out.println("Horizontal line here");
			}
			double rightBottomSlope = 0;
			double leftTopSlope = 0;
			double rightTopSlope = 0;
			double leftBottomSlope = 0;

			for(int i = 0; i < potentialList.size(); i++)
			{
				if(linearEq(smallestX.x, smallestX.y, largestY.x, largestY.y, potentialList.get(i).x, potentialList.get(i).y) < 0)
				{
					//check to see if it's maximum distance
					//absoluteDistance(potentialList.get(i).x, potentialList.get(i).y, rightBottomSlope, rightBottomTranslation);
					polygonList.add(1,potentialList.get(i));
					
				}
			}
			//create slope
			//
			//Now that all the vertices have been obtained, its time to add the points to the polygon object
			int[] listofXCoord = new int[vertexList.size()];
			int[] listofYCoord = new int[vertexList.size()];
			for(int i = 0; i < vertexList.size(); i++)
			{
				listofXCoord[i] = vertexList.get(i).x;
				listofYCoord[i] = vertexList.get(i).y;
			}
			safeZone = new Polygon(listofXCoord, listofYCoord, vertexList.size());
			while(true)
			{
				for(int i = 0; i <= 10000000; i++)
				{
					if(i == 10000000)
					{
						int[] listofX = new int[vertexList.size()];
						int[] listofY = new int[vertexList.size()];
						for(int i = 0; i < vertexList.size(); i++)
						{
							listofX[i] = (int)(Math.random() * 1000);
							listofY[i] = (int)(Math.random() * 1000);
						}
						safeZone.xpoints = listofX;
						safeZone.ypoints = listofY;
					}
				}
			}
		}
		return safeZone;
	}
	
		for(int i = 0; i < vertexList.size(); i++)
		{
			//Bottom Left Slope
			if(linearEquationMaker(smallestX.x, smallestX.y, largestY.x, largestY.y, vertexList.get(i).x, vertexList.get(i).y) < 0)
			{
				polygonList.add(1,vertexList.get(i));//need to set index
				System.out.println("Working on it.");
			}
		}
	public static int linearEquationMaker(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		//Finding the slope
		double slope = (double)(y2-y1)/(double)(x2-x1);
		System.out.println((y2-y1) + "/" + (x2-x1) + " = " + slope);
		//Finding the translation
		double translation = y1 - (slope*x1);
		System.out.println(y1 + "-" + slope + "*" + x1 + " = " + translation);
		//compare
		int returnValue = 0;
		if((slope*x3) + translation < y3)//below in terms of java's coord persp
		{
			returnValue =  -1;
		}
		if((slope*x3) + translation > y3)//above
		{
			returnValue = 1;
		}
		if(slope < 0)//if slope is opposite then it will change perspective
		{
			returnValue = - returnValue;
		}
		return returnValue;
	}
	public static double absoluteDistance(int x, int y, double slope, double translation)
	{
		//the absolute distance is the length of the line formed by the point you have and a point on the line that forms a perpendicular line to that line.
		//oh, hello cross product
		
		//generate a perpendicular line
		//
	}
		//Scan each node and identify the one with lowest distance from each other starting from arraylist.get(0)
		//With lowest distance, use java.paint to draw a line using the two points as vertices
		//change status of node to taken. If it is taken, then it cannot be used to form new line
		//repeat until polygon is formed
		//NOTE MIGHT WANT TO CHANGE TO FIND POLYGON WITH MAXIMUM AREA
        
				//If input = Enter, then check availability of dungeon
				//If available, enter dungeon
				//Turn screen to black

/*    public static boolean playerMove()
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
*/
/*
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
*/
}