import java.awt.image.*;//image class allows for storage of BufferedImage files
public class NodeWorld
{
	int x;//x coordinate of node on world map
	int y;//y coordinate of node on world map
	int skillID;//skill rewarded at node. Might need to change this so people won't find a hack that detects all the skills beforehand
	int nodeStatus;//0 = uncleared; 1 = skill obtained; 2 = protective; 3 = battlefield; 4 = enemy's
	int theme;
	static int difficulty; //linear increase in dungeon. Is static bc difficulty increases as game progresses
	BufferedImage nodeImage; //image of node
	ImageLoader imageLoader;//need to initiate ImageLoader
	//Constructor
    public NodeWorld(int xPos, int yPos, int inputSkill, int inputTheme)
	{
		x = xPos;
		y = yPos;
		skillID = inputSkill;
		theme = inputTheme;
		difficulty = 1;//defaults to 1, the starting difficulty
		nodeStatus = (int)(Math.random()*5);//defaults to 0 bc all nodes start out as dungeons
		imageLoader = new ImageLoader();
		nodeImage = loadNodeImage(nodeStatus);
	}
/*
Method 0: .nodeImage loads BufferedImage of Node, depending on nodeStatus
*/
	public BufferedImage loadNodeImage(int nodeStatus)
	{
		BufferedImage node = imageLoader.loadImage("Node" + nodeStatus + ".png");
		return node;
	}
/*
Method 1: Comparing nodes
*/
	public boolean equals(NodeWorld comparedNode)
	{
		if (this.x == comparedNode.x && this.y == comparedNode.y)
			return true;
		return false;
	}
}