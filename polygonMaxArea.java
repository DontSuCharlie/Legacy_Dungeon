import java.awt.Polygon;
public class polygonMaxArea
{
	public static void main(String[] args)
	{
		ArrayList<NodeWorld> coordinatePoints = new ArrayList<NodeWorld>();
		for(int i = 0; i < 10; i++)
		{
			coordinatePoints.add(new NodeWorld((int)(Math.random()*30),(int)(Math.random()*30), 0, 0));
		}
		for(int i = 0; i < coordinatePoints.size(); i++)
		{
			NodeWorld largestX = new NodeWorld(0,0,0,0);//will change to coordinate
			NodeWorld smallestX = new NodeWorld(30,0,0,0);//will change to coordinate
			NodeWorld largestY = new NodeWorld(0,0,0,0);//
			NodeWorld smallestY = new NodeWorld(0,30,0,0);//
			//might implement binary search here, but not yet
			if(coordinatePoints.get(i).x > largestX.x)
			{
				largestX = coordinatesPoints.get(i);
			}
			if(coordinatePoints.get(i).x < smallestX.x)
			{
				smallestX = coordinatePoints.get(i);
			}
			if(coordinatePoints.get(i).y > largestY.y)
			{
				largestY = coordinatePoints.get(i);
			}
			if(coordinatePoints.get(i).y < smallestY.y)
			{
				smallestY = coordinatePoints.get(i);
			}
		}
		//Generate quadrilateral from these 4 points
		int[] listofXCoord = {smallestX.x, largestX.x};
		int[] listofYCoord = {smallestY.y, largestY.y};
		Polygon maxAreaPolygon = new Polygon(listofXCoord, listofYCoord, 4);
		//Create 4 slopes.
		//Doesn't matter which slope is referred to AS LONG AS LARGEST(X/Y) DOES NOT COME IN CONTACT WITH SMALLEST(X/Y) AND IT DOESN'T REPEAT
		double upperSlope = (largestY.y-largestX.y)/(largestY.x-largestX.x);
		double leftSlope = (smallestX.y-smallestY.y)/(smallestX.x -smallestY.x);
		double rightSlope = (largestX.y-smallestX.y)/(largestX.x - smallestX.x);
		double lowerSlope = (smallestY.y-largestY.y)/(smallestY.x -largestY.x);
		//Plugs in values. If values are outside of the boundary, append them to the polygon
		for(int i = 0; i < coordinatePoints.size(); i++)
		{
			
		}
	}
}