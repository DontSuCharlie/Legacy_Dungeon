import java.awt.Polygon;
import java.util.ArrayList;
public class polygonMaker
{
	double slope;
	double translation;
	//NodeWorld is an object with x and y coordinates
	public Polygon makePolygon(ArrayList<NodeWorld>)
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
			//add to list of actual vertices, in order of how you would actually connect the dots bc thats how Polygon does it
			vertexList.add(smallestX);
			vertexList.add(largestY);
			vertexList.add(largestX);
			vertexList.add(smallestY);
			//Find their slopes
			//Check all the points to see which one is out of polygon range
			//
			if(largestY.x-largestX.x == 0)
			{
				System.out.println("Horizontal line here. Cannot perform function QQ");
			}
			double rightBottomSlope = 0;
			double leftTopSlope = 0;
			double rightTopSlope = 0;
			double leftBottomSlope = 0;
			int indexOfMaxNode = 0;
			for(int i = 0; i < potentialList.size(); i++)
			{
				ArrayList <NodeWorld> viableList = new ArrayList<NodeWorld>();
				if(lineMaker(smallestX.x, smallestX.y, largestY.x, largestY.y, potentialList.get(i).x, potentialList.get(i).y) < 0)
				{
					viableList.add(potentialList.get(i));
					if(absoluteDistance(potentialList.get(i)))
					{
						indexOfMaxNode = i;
					}
				}
			}
			//check to see if the point lies at the left or right of the point
			polygonList.add(,potentialList.get(indexOfMaxNode));
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
		}
		return safeZone;
	}
	public static double lineMaker(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		//Finding the slope
		slope = (double)(y2-y1)/(double)(x2-x1);
		System.out.println((y2-y1) + "/" + (x2-x1) + " = " + slope);
		//Finding the translation
		translation = y1 - (slope*x1);
		//System.out.println(y1 + "-" + slope + "*" + x1 + " = " + translation);
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
	}
	public static double perpendicularMaker()
	{
	}
	public static NodeWorld farthestPoint()
	{
	}
}