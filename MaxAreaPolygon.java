import java.awt.Polygon;
import java.util.ArrayList;
public class MaxAreaPolygon
{
	ArrayList<NodeWorld> potentialList;
	ArrayList<NodeWorld> vertexList;
	Polygon safeZone;
	double slope;
	double translation;
	int firstPointIndex;
	int secondPointIndex;
	int thirdPointIndex;
	int fourthPointIndex;
	public Polygon makePolygon(ArrayList<NodeWorld> nodeList)//draws the actual polygon
	{
		potentialList = new ArrayList<NodeWorld>();
		vertexList = new ArrayList<NodeWorld>();
		safeZone = new Polygon();
		//Initialize the variables that will need to be used
		NodeWorld largestX = new NodeWorld(0,0,0,0);
		NodeWorld largestY = new NodeWorld(0,0,0,0);
		NodeWorld smallestX = new NodeWorld(10000,10000,0,0);//need to find better numbers in the future for more elegance.
		NodeWorld smallestY = new NodeWorld(10000,10000,0,0);
		//Creates an arraylist of nodes that are viable (those that have a defense status)
		for(int i = 0; i < nodeList.size(); i++)
		{
			if(nodeList.get(i).nodeStatus == 2)
			{
				potentialList.add(nodeList.get(i));
			}
		}
		//if there are 3, make a triangle
		if(potentialList.size() == 3)//if there's only 3, don't even try to maximize area
		{
			int[] xCoord = new int[potentialList.size()];
			for(int i = 0; i < xCoord.length; i++)
			{
				xCoord[i] = potentialList.get(i).x;
			}
			int[] yCoord = new int[potentialList.size()];
			for(int i = 0; i < yCoord.length; i++)
			{
				yCoord[i] = potentialList.get(i).y;
			}
			safeZone = new Polygon(xCoord,yCoord,potentialList.size());
		}
		//if there are more than 3, fuck life
		if(potentialList.size() > 3)//if greater than 3, try to maximize area
		{
			for(int i = 0; i < potentialList.size(); i++)
			{
				//Might implement binary search here, but not skilled enough yet :(
				if(potentialList.get(i).x < smallestX.x)
				{
					smallestX = potentialList.get(i);
					firstPointIndex = 0;
				}
				if(potentialList.get(i).y < smallestY.y)
				{
					smallestY = potentialList.get(i);
					secondPointIndex = 1;
				}	
				if(potentialList.get(i).x > largestX.x)
				{
					largestX = potentialList.get(i);
					thirdPointIndex = 2;
				}
				if(potentialList.get(i).y > largestY.y)
				{
					largestY = potentialList.get(i);
					fourthPointIndex = 3;
				}			
			}
			//remove from search list
			potentialList.remove(smallestY);
			potentialList.remove(largestY);
			potentialList.remove(smallestX);
			potentialList.remove(largestX);
			//add to list of actual vertices, in order of how you would actually connect the dots bc thats how Polygon does it
			safeZone.addPoint(smallestX.x, smallestX.y);
			safeZone.addPoint(smallestY.x, smallestY.y);
			safeZone.addPoint(largestX.x, largestX.y);
			safeZone.addPoint(largestY.x, largestY.y);
			vertexList.add(smallestX);
			vertexList.add(smallestY);
			vertexList.add(largestX);
			vertexList.add(largestY);
			while(pointsRemaining())//repeats this step until no more points exists outside of polygonal area, mwahahahha. Shouldn't be necessary bc each while loop inside does so already
			{
				//checking the top left slope
				ArrayList<NodeWorld> viableList = new ArrayList<NodeWorld>();
				boolean firstRun = true;
				for(int i = 0; i < potentialList.size(); i++)
				{
					if(lineMaker(smallestY.x, smallestY.y, smallestX.x, smallestX.y, potentialList.get(i).x, potentialList.get(i).y) > 0)
					{
						viableList.add(potentialList.get(i));
					}
				}
				while(viableList.size() > 0 || firstRun)
				{
					firstRun = false;
					addFarthestPoint("TopLeft", viableList);
					safeZone = redrawPolygon();
					for(int i = 0; i < viableList.size(); i++)
					{
						if(safeZone.contains(viableList.get(i).x, viableList.get(i).y))
						{
							viableList.remove(i);
							i--;
						}
					}
					System.out.println("TOPLEFT REPORTING FOR DUTY. SIZE: " + viableList.size());
				}
				//checking the top right slope
				firstRun = true;
				for(int i = 0; i < potentialList.size(); i++)
				{
					if(lineMaker(largestX.x, largestX.y, smallestY.x, smallestY.y, potentialList.get(i).x, potentialList.get(i).y) > 0)
					{
						viableList.add(potentialList.get(i));
					}
				}
				while(viableList.size() > 0 || firstRun)
				{
					addFarthestPoint("TopRight", viableList);
					safeZone = redrawPolygon();
					for(int i = 0; i < viableList.size(); i++)
					{
						if(safeZone.contains(viableList.get(i).x, viableList.get(i).y))
						{
							viableList.remove(i);
							i--;
						}
					}
					firstRun = false;
					System.out.println("TOPRIGHT REPORTING FOR DUTY. SIZE:" + viableList.size());
				}
				//checking the bottom right slope
				firstRun = true;
				for(int i = 0; i < potentialList.size(); i++)
				{
					if(lineMaker(largestY.x, largestY.y, largestX.x, largestX.y, potentialList.get(i).x, potentialList.get(i).y) < 0)
					{
						viableList.add(potentialList.get(i));
					}
				}
				while(viableList.size() > 0 || firstRun)
				{
					addFarthestPoint("BottomRight", viableList);
					safeZone = redrawPolygon();
					for(int i = 0; i < viableList.size(); i++)
					{
						if(safeZone.contains(viableList.get(i).x, viableList.get(i).y))
						{
							viableList.remove(i);
							i--;
						}
					}
					firstRun = false;
					System.out.println("BOTTOMRIGHT REPORTING FOR DUTY");
				}
				//checking the bottom left slope
				firstRun = true;
				for(int i = 0; i < potentialList.size(); i++)
				{
					if(lineMaker(smallestX.x, smallestX.y, largestY.x, largestY.y, potentialList.get(i).x, potentialList.get(i).y) < 0)
					{
						viableList.add(potentialList.get(i));
					}
				}
				while(viableList.size() > 0 || firstRun)
				{
					addFarthestPoint("BottomLeft", viableList);
					safeZone = redrawPolygon();
					for(int i = 0; i < viableList.size(); i++)
					{
						if(safeZone.contains(viableList.get(i).x, viableList.get(i).y))
						{
							viableList.remove(i);
							i--;
						}
					}
					firstRun = false;
					System.out.println("BOTTOMLEFT REPORTING FOR DUTY");
				}
			}
		}
		return safeZone;
	}
	public boolean pointsRemaining()//checks to see if there are points remaining
	{
		for(int i = 0; i < potentialList.size(); i++)
		{
			if(safeZone.contains(potentialList.get(i).x, potentialList.get(i).y))
			{
				potentialList.remove(i);
				i--;
			}
		}
		System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII AM NOT WORKING!!!!!!!!!!!!!!");
		if(potentialList.size() > 0)
		{
			return true;
		}
		return false;
	}

	public void addFarthestPoint(String slopeRef, ArrayList<NodeWorld> viableList)//looks for farthest point to append to polygon
	{
		int farthestNodeIndex = 0;
		double farthestNodeDistance = 0;
		for(int i = 0; i < viableList.size(); i++)
		{
			if(minDistance(viableList.get(i).x, viableList.get(i).y) > farthestNodeDistance)
			{
				farthestNodeIndex = i;
			}
		}
		if(viableList.size() > 0)
		{
			vertexList.add(getVertexIndex(slopeRef, viableList.get(farthestNodeIndex)),viableList.get(farthestNodeIndex));
		}
	}
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
					if(vertexList.get(i).y > farthestNode.y)
					{
						return i;// i - 1 = the index that is before it. Therefore, you should move the current i to i + 1, and make this one the i.
					}
				}
				secondPointIndex++;//now that secondPoint is at a new index, update it
			case "TopRight":
				for(int i = secondPointIndex; i <= thirdPointIndex; i++)
				{
					if(vertexList.get(i).y < farthestNode.y)//opposite here, since slope is neg.
					{
						return i;// i - 1 = the index that is before it. Therefore, you should move the current i to i + 1, and make this one the i.
					}
				}
				thirdPointIndex++;
			case "BottomRight":
				for(int i = thirdPointIndex; i <= fourthPointIndex; i++)
				{
					if(vertexList.get(i).y < farthestNode.y)//opposite here, since we're going from a higher point to a lower point
					{
						return i;// i - 1 = the index that is before it. Therefore, you should move the current i to i + 1, and make this one the i.
					}
				}
				fourthPointIndex++;
			case "BottomLeft":
				//since bottom left has the last index point, we just add on. But we still want to keep track of any new added points.
				for(int i = fourthPointIndex; i < vertexList.size(); i++)
				{
					if(vertexList.get(i).y > farthestNode.y)//back to normal
					{
						return i;// i - 1 = the index that is before it. Therefore, you should move the current i to i + 1, and make this one the i.
					}
				}
		}
		return 0;
	}
	public double minDistance(int x0, int y0)
	{
		//formula
		double minDistance = Math.abs(y0 - (slope*x0) - translation)/Math.sqrt(slope*slope + 1);
		return minDistance;
	}
	/*
		//find slope
		double inverseSlope = -1/slope;
		//find translation of line
		double translation = y0 - inverseSlope*x0;
		//solve the two equations to find the point that intersects the perpendicular
		x1 = 
		y1 = 
		//find distance from point to the point that intersects the perpendicular
		derivation on here
	*/
	public double lineMaker(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		//Finding the slope
		slope = (double)(y2-y1)/(double)(x2-x1);
		System.out.println((y2-y1) + "/" + (x2-x1) + " = " + slope);
		//Finding the translation
		translation = y1 - (slope*x1);
		//System.out.println(y1 + "-" + slope + "*" + x1 + " = " + translation);
		//compare
		int returnValue = 0;
		if((slope*x3) + translation < y3)//above in terms of java's coord persp
		{
			returnValue =  1;
		}
		if((slope*x3) + translation > y3)//below
		{
			returnValue = -1;
		}
		if(slope < 0)//if slope is opposite then it will change perspective
		{
			returnValue = - returnValue;
		}
		return returnValue;
	}
	public Polygon redrawPolygon()
	{
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
		safeZone = new Polygon(xCoord,yCoord,vertexList.size());
		return safeZone;
	}
	
}