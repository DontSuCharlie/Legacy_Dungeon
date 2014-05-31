//package WorldMap
import java.util.*;
import java.awt.image.*;
import java.awt.Polygon;
import javax.swing.*;
import java.awt.*;

public class WorldMap extends JPanel
{
	Character0 character;
	ImageLoader imageLoader = new ImageLoader();
	BufferedImage map = imageLoader.loadImage("TempWorldMap.jpg");
	BufferedImage[] numImage = new BufferedImage[10];
	String[] imageDirectory = {"0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"}; 
	static ArrayList<Node> nodeList = new ArrayList<Node>();//array list of nodes
	int numNodes, innerNodes, midNodes, outerNodes, nodesLeft;//number of nodes to be made
	boolean concentrateNodes;
	int playerX;
	int playerY;
	
	public WorldMap()
	{
		System.out.println(playerX);
		character = new Character0();
		numNodes = (int) (Math.random() * 20) + 20;//generates a random # of nodes (20-40)
		//imageLoader = new ImageLoader();
		//map = imageLoader.loadImage("TempWorldMap.jpg");
		for(int i = 0; i < 10; i++)
		{
			numImage[i] = imageLoader.loadImage(imageDirectory[i]);
		}
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
	}
//////////////////////////////////Methods here///////////////////////////////////////
/*
Method 0: .assignNodePos() creates a random amount of nodes and assigns them positions
Method 1: .playerMove() loads player sprite, positions the player based on previous dungeon (if no previous then starts at Heart), makes sure the player CAN ONLY BE ON NODES. Will also make sure turn++;
Method 2: .mouseListener() - obtains keyboard input from LegacyDungeon. Then translates input to allow for player movement based on lowest distance in terms of x/y based on position (will add keyListener later)
Method 5: .enemyMovement() - will implement later, AI so scarrr
*/

/* WILL WORK ON DISTRIBUTION OF NODES _AFTER_ NORMAL DISTRIBUTION OF NODES WORKS
	//Method 0: Creates nodes and assigns different positions for inner nodes
	public static void assignInnerNodes()
	{
		for(int i = 0; i < innerNodes; i++)
		{
			nodeList.add(i, new Node(Math.random()*screenRes1X1, Math.random() * screenRes1Y1));
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
<<<<<<< HEAD
	public void assignNodePos()
	{
		//Generate Heart Node
		nodeList.add(new Node(Window.windowX/2, Window.windowY/2,0,0));
		nodeList.get(0).status = 5;//Heart Node Status
		for(int i = 1; i < numNodes; i++)
		{
			nodeList.add(i, new Node((int)(Math.random()*Window.windowX*2), (int)(Math.random()*Window.windowY*2), 0, 0));//will change the skillID/theme parameter inputs later
			boolean check = true; //to check whether or not any nodes are on top of another
			for(int j = 0; j < i; j++)
			{
				int counter = 0;
				while(check)
				{
					int dontWantX = Math.abs(nodeList.get(i).x - nodeList.get(j).x);
					int dontWantY = Math.abs(nodeList.get(i).y - nodeList.get(j).y);
					// Checking a range if far enough from other nodes. Basically, the range from (x - 20) to (x + 20) must be both less or both greater than the other nodes available.
					if(dontWantX <= 50 && dontWantY <= 50)
=======
	public void assignNodePos(){
		for(int i = 0; i < numNodes; i++)
		{
			nodeList.add(i, new NodeWorld((int)(Math.random()*screenRes1X1), (int)(Math.random()*screenRes1Y1), 0, 0));//will change the skillID/theme parameter inputs later
			boolean check = true; //for checking purposes
			for(int j = 0; j < i; j++)
			{
				int counter = 0;
				while(check)//if check is true, retry because that means the nodes are on top of another
				{
					int dontWantX = Math.abs(nodeList.get(i).x - nodeList.get(j).x);
					int dontWantY = Math.abs(nodeList.get(i).y - nodeList.get(j).y);
					// Checking a range if far enough from other nodes. Basically, the range from (xPos - 20) to (xPos + 20) must be both less or both greater than the other nodes available.
					if(dontWantX <= 10 && dontWantY <= 10)
>>>>>>> 9bf573ad7a352036b1cda76e901933589d760242
					{
						nodeList.get(i).x = (int)(Math.random()*Window.windowX*2);
						nodeList.get(i).y = (int)(Math.random()*Window.windowY*2);
						counter++;
					}
					if(counter > 0)//should check if need brackets here......
						check = true;
					else
						check = false;
<<<<<<< HEAD
				System.out.println("Node " + i + ": " + nodeList.get(i).x + ", " + nodeList.get(i).y + "Status" + nodeList.get(i).status);
=======
				System.out.println("Node " + i + ": " + nodeList.get(i).x + ", " + nodeList.get(i).y);
>>>>>>> 9bf573ad7a352036b1cda76e901933589d760242
				}
			}
		}
	}
		//Scan each node and identify the one with lowest distance from each other starting from arraylist.get(0)
		//With lowest distance, use java.paint to draw a line using the two points as vertices
		//change status of node to taken. If it is taken, then it cannot be used to form new line
		//repeat until polygon is formed
		//NOTE MIGHT WANT TO CHANGE TO FIND POLYGON WITH MAXIMUM AREA
        
				//If input = Enter, then check availability of dungeon
				//If available, enter dungeon
				//Turn screen to black

	public void playerMove(int x, int y)
	{
		playerX = x;
		playerY = y;
	}
		//if(mouseClick)
		//{
			//inRangeList = new ArrayList<Node>();
			
			/*
			int everythingElse.X++;
			int everythingElse.Y = copy pasta of slope formula
			turn screen to black
			clears ArrayList of potential nodes (to prevent glitch)
			return true;
			*/
		//}
		//return false;
	//Method 3: Detects closest nodes. Will be called by .playerMove() so dwai
	public void nodeDetector()
	{
		int searchRadius = 1;
		boolean pointsNotFound = true;
		for(int i = 0; i < nodeList.size(); i++)
		{
			if(Math.abs(nodeList.get(i).x - playerX) <= searchRadius && Math.abs(nodeList.get(i).y - playerY) <= searchRadius)
			{
				nodeList.get(i).inRange = true;
				pointsNotFound = false;
				searchRadius+=5;
			}
		}
		do
		{
			for(int i = 0; i < nodeList.size(); i++)
			{
				if(Math.abs(nodeList.get(i).x - playerX) <= searchRadius && Math.abs(nodeList.get(i).y - playerY) <= searchRadius)
				{
					nodeList.get(i).inRange = true;
					pointsNotFound = false;
				}
				else
				{
					searchRadius+=5;
				}
			}
		} while(pointsNotFound);
		//Find nodes nearest to it, USE THE MOST FUCKING EFFICIENT SORT MOFO MWAHAHAH
		//add keyboard movement function later. Too complicated for now
			//Look for the most predomininat x/y node, assign node value??
			//actually, we could just assign each point a "turn". If within x proximity, then movement equals 1 turn. You can just choose whichever one you want. idk, we'll see
	}
<<<<<<< HEAD
}
=======
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
>>>>>>> 9bf573ad7a352036b1cda76e901933589d760242
