/*
To improve it, make it so it does this:
1) check slope
2) 

Also import java.math.BigDecimal

Next Steps:
1] Fix the 3 point problem
2] Fix the vertical line problem
3] Alter it so makePolygon is only called every time the NodeList has a new defensive node
4] Find a way to draw a thicker line. Change opacity of polygon. (see if it's possible to load image, I can make an animation within the safe area)
5] Draw new images and animations
6] 

4] Load character sprite
5] Add mouse input and keyboard input
	Set it so all nodes have an area where it can be clickable. However, clickable is only true for nodes near the character. 
	Assign clicked node as target node (for character movement)
	When/if clicked, activate Method 6]
	
6] Movement of character
	Generate slope with the initial node and target node. Increase x value continously. Change y value according to equation. x value should be that of the map not the character (To look like scrolling)
7] Add scrolling
7] Add music
8] Add sounds
9] Add a temporary "manual" node type changer (CHANGE TO NORMAL NODE CHANGER AFTER MAIN DUNGEON IS DONE)
10] Add animation
11] Change World Map based on current stage
12] Add turn counter
13] Add enemy movement
	Create a polygon, set opacity/lightness to 0. Set the normal to darker (so it looks like enemy lines are destroying the world)
14] Draw enemy sprites
15] Add AI (different modes of AI for different levels of difficulty, diff war strategies?)
15] Add animation for attacked nodes (later)
16] Add scrolling
17] Add clouds (misty, yay!)
18] Draw custom map
19] Make GUI AS APPEALING AS POSSIBLE (fonts, colors, quality of images, etc.)

IN DUNGEON, MULTIPLE BOSSES CAN BE MOVING. 1 WILL BE ATTACKING YOU, OTHER WILL BE TRYING TO DESTROY BASE.
*/
import java.awt.Polygon;
import java.util.ArrayList;
public class MaxAreaPolygon
{
	//List of global variables
	ArrayList<NodeWorld> potentialList;
	ArrayList<NodeWorld> vertexList;
	Polygon safeZone;
	double slope;
	double translation;
	int firstPointIndex;
	int secondPointIndex;
	int thirdPointIndex;
	int fourthPointIndex;
//Main method
//Pre-condition: There is at least 3 points that are satisfactory
	public Polygon makePolygon(ArrayList<NodeWorld> potentialList)
	{
		this.potentialList = potentialList;
		vertexList = new ArrayList<NodeWorld>();
		//If there are 3, draw a triangle. Don't even bother trying to maximize area
		if(potentialList.size() == 3)
		{
			return drawPolygon(potentialList);
		}
		//Otherwise continue
		//Grab the 4 farthest points
		NodeWorld largestX = new NodeWorld(0,0,0,0);
		NodeWorld largestY = new NodeWorld(0,0,0,0);
		NodeWorld smallestX = new NodeWorld(10000,10000,0,0);//need to find better numbers in the future for more elegance.
		NodeWorld smallestY = new NodeWorld(10000,10000,0,0);
		for(int i = 0; i < potentialList.size(); i++)
		{
			if(potentialList.get(i).x < smallestX.x)
				smallestX = potentialList.get(i);
			if(potentialList.get(i).y < smallestY.y)
				smallestY = potentialList.get(i);
			if(potentialList.get(i).x > largestX.x)
				largestX = potentialList.get(i);
			if(potentialList.get(i).y > largestY.y)
				largestY = potentialList.get(i);
		}
		if(smallestY.equals(smallestX))
		firstPointIndex = 0;
		secondPointIndex = 1;
		thirdPointIndex = 2;
		fourthPointIndex = 3;
		//Add them to the vertexList
		vertexList.add(smallestX);
		vertexList.add(smallestY);
		vertexList.add(largestX);
		vertexList.add(largestY);
		safeZone = drawPolygon(potentialList);
		//This is the part that repeats until it finishes EVERYTHING
		//fuck this part
		//fuck it so much
		
		if(!smallestX.equals(smallestY))//slope 1 (Topleft) does not exist
		{
			addVertex(smallestY.x, smallestY.y, smallestX.x, smallestX.y, "TopLeft");
			System.out.println("Top Left is done.");
			System.out.println("=============================================");
		}
		if(!smallestY.equals(largestX))//slope 2 (Topright) does not exist
		{
			addVertex(largestX.x, largestX.y, smallestY.x, smallestY.y, "TopRight");
			System.out.println("Top Right is done.");
			System.out.println("=============================================");
		}
		if(!largestX.equals(largestY))//slope 3 (Bottomriht) does not exist
		{
			addVertex(largestY.x, largestY.y, largestX.x, largestX.y, "BottomRight");
			System.out.println("Bottom Right is done.");
			System.out.println("=============================================");
		}
		if(!smallestX.equals(largestY))//slope 4 (bottom left) does not exist
		{
			addVertex(smallestX.x, smallestX.y, largestY.x, largestY.y, "BottomLeft");
			System.out.println("Bottom Left is done.");
			System.out.println("=============================================");
		}
		return drawPolygon(vertexList);
	}
//Sub-main method
//This method is called 4 times. It is responsible for adding vertices to the polygon in the right place.
//fuck this method
	public void addVertex(int x1, int y1, int x2, int y2, String ref)
	{
		//Creates a new arraylist. This arraylist checks for points on 1 specific side of the polygon
		ArrayList<NodeWorld> viableList = new ArrayList<NodeWorld>();
		for(int i = 0; i < potentialList.size(); i++)
		{
			System.out.print("Checking point: " + i);
			int reference = drawLine(x1, y1, x2, y2, potentialList.get(i).x, potentialList.get(i).y);
			if(reference == 0)
			{
				System.out.println("Found a vertical line. Skip!");
			}
			if(ref.equals("TopLeft") || ref.equals("TopRight"))
			{
				//x>0 means, visually, above it
				if(reference > 0)
				{
					System.out.println("FOUND ONE!!!!!!! Point is visually above: X = " + potentialList.get(i).x + " Y = " + potentialList.get(i).y);
					viableList.add(potentialList.get(i));
				}
			}
			if(ref.equals("BottomLeft") || ref.equals("BottomRight"))
			{
				//x<0 means, visually below it
				if(reference < 0)
				{
					System.out.println("Point is visually below: X = " + potentialList.get(i).x + " Y = " + potentialList.get(i).y);
					viableList.add(potentialList.get(i));
				}
			}
		}
		int i = 1;//for bug testing
		while(viableList.size() > 0)
		{
			addFarthestPoint(ref, viableList);
			safeZone = drawPolygon(viableList);
			i++;
			if(i > 10)
			{
				System.out.println(ref + "has failed.");
				break;
			}
		}
	}
//Core method (not checked yet :/)
//This guy draws a line based on the input. This line is the boundary
	public int drawLine(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		if(x2-x1 == 0)//if the slope is a 100% vertical line
		{
			return 0;
			//if it is left of the boundary, return 1
			//if(x3 < x2)
				//return 1;
			//if it is right of the boundary, return -1
			//return -1;
		}
		slope = (double)(y2-y1)/(double)(x2-x1);
		translation = y1 - (slope*x1);
		System.out.println("Equation = " + slope + "x + " + translation + " Input X Value: " + x3 + " Input Y Value: " + y3);
		System.out.println("Resultant y value = " + ((slope*x3) + translation));
		if((slope*x3) + translation < y3)//above it in Cartesian plane; below in java
			return -1;
		if((slope*x3) + translation > y3)//below in Cartesian plane; above in java
			return 1;
		return 0;
	}
//Updator method
//Because Java doesn't use fucking ArrayLists for Polygons, we have to redraw the polygon every single time. .add() doesn't work, because you can't choose the specific index you want it to add it into.
	public Polygon drawPolygon(ArrayList<NodeWorld> inputList)//entering an ArrayList as a parameter does indeed change the value of the parameter, which is nice!
	{
		//Convert our ArrayList's coordinates to arrays. Polygon takes in arrays as parameters
		int[] xCoord = new int[vertexList.size()];
		for(int i = 0; i < xCoord.length; i++)
		{
			xCoord[i] = vertexList.get(i).x;
		}
		int[] yCoord = new int[vertexList.size()];
		for(int i = 0; i < yCoord.length; i++)
		{
			yCoord[i] = vertexList.get(i).y;
		}
		Polygon polygon = new Polygon(xCoord,yCoord,vertexList.size());
		//This parts updates the potentialList and removes all points that are within the new polygon. I did it here because everytime we need to update the polygon, we need to check for remaining points, so why not do it in the same method? :D
		for(int i = 0; i < inputList.size(); i++)
		{
			if(polygon.contains(inputList.get(i).x, inputList.get(i).y))
			{
				inputList.remove(i);//NOTE: I think contains does not include vertex point
				i--;//don't want to skip the actual next index
			}
		}
		return polygon;
	}
//Core method
//Goes through the arraylist of viable points and looks for the one that is farthest away
//fuck you
	public void addFarthestPoint(String slopeRef, ArrayList<NodeWorld> viableList)
	{
		int farthestIndex = 0;
		double farthestDistance = 0;
		for(int i = 0; i < viableList.size(); i++)
		{
			if(minDistance(viableList.get(i).x, viableList.get(i).y) > farthestDistance)
				farthestIndex = i;
		}
		vertexList.add(getVertexIndex(slopeRef, viableList.get(farthestIndex)), viableList.get(farthestIndex));
		viableList.remove(farthestIndex);
		//TESTING ZONE
		System.out.println("# of elements = " + viableList.size());
		if(viableList.size() < 3)
		{
			for(int i = 0; i < viableList.size(); i++)
			{
				System.out.println("Viable List Index #: " + i);
				System.out.println("x: " + viableList.get(i).x + " y: " + viableList.get(i).y);
				//System.out.println("New points vertexList index: " + getVertexIndex(slopeRef, viableList.get(farthestIndex)));
			}
		}
	}
//Most annoying of them all
//Core method
//Keeps track of the original 4's vertex indices.
	public int getVertexIndex(String slopeRef, NodeWorld farthestNode)
	{
		//if translation is bigger, then it is more to the right
		//for the top left, and bottom left, to the right = add index after
		//for the top right and bottom right, to the right = add index before
		switch(slopeRef)
		{
			case "TopLeft": 
				//find the vertices between the two original vertices
				//check to see which one has the smallest y coordinate, bc if it's higher then it is to the right.
				for(int i = firstPointIndex; i <= secondPointIndex; i++)
				{
					if(vertexList.get(i).y < farthestNode.y)
					{
						secondPointIndex++;//now that secondPoint is at a new index, update it
						thirdPointIndex++;
						fourthPointIndex++;
						return i;// i - 1 = the index that is before it. Therefore, you should move the current i to i + 1, and make this one the i.
					}
				}
				//OMG I FOUND THE ERROR. I TRY TO SECONDPOINTINDEX++ AFTER IT RETURNS, BUT IT WILL NEVER REACH THERE. HOLY FUCK
			case "TopRight":
				for(int i = secondPointIndex; i <= thirdPointIndex; i++)
				{
					if(vertexList.get(i).y > farthestNode.y)//opposite here, since slope is neg.
					{
						thirdPointIndex++;
						fourthPointIndex++;
						return i;
					}
				}
			case "BottomRight":
				for(int i = thirdPointIndex; i <= fourthPointIndex; i++)
				{
					if(vertexList.get(i).y > farthestNode.y)//opposite here, since we're going from a higher point to a lower point
					{
						fourthPointIndex++;
						return i;
					}
				}
			case "BottomLeft":
				//since bottom left has the last index point, we just add on. But we still want to keep track of any new added points.
				for(int i = fourthPointIndex; i < vertexList.size(); i++)
				{
					if(vertexList.get(i).y < farthestNode.y)//back to normal
					{
						return i;
					}
				}
		}
		return 0;//if none of them work then return 0?
	}
	public double minDistance(int x0, int y0)
	{
		//formula found on internet
		//proof can be done with very basic algebra
		double minDistance = Math.abs(y0 - (slope*x0) - translation)/Math.sqrt(slope*slope + 1);
		return minDistance;
	}
}