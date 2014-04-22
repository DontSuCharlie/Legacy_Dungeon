import java.awt.*;//not sure
import java.awt.event.*;//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
import java.awt.image.*;//image class allows for storage of BufferedImage files
import javax.swing.*;//part of UI
import javax.imageio.*;//handles the input/output of BufferedImage files
import java.io.*;//handles input/output of files (ie: reads files)
import java.util.*;
public class NodeWorld
{
	int x;//x coordinate of node on world map
	int y;//y coordinate of node on world map
	int skillID;//skill rewarded at node. Might need to change this so people won't find a hack that detects all the skills beforehand
	int nodeStatus;//0 = uncleared; 1 = skill obtained; 2 = protective; 3 = battlefield; 4 = enemy's
	int theme;
	static int difficulty; //linear increase in dungeon. Is static bc difficulty increases as game progresses
	BufferedImage nodeImage; //image of node
	//Constructor
    public NodeWorld(int xPos, int yPos, int skill, int theme)
	{
		x = xPos;
		y = yPos;
		skillID = skill;
		this.theme = theme;
		difficulty = 1;//defaults to 1, the starting difficulty
		nodeStatus = (int)(Math.random()*5);//defaults to 0 bc all nodes start out as dungeons
	}
//////////////////////////////////////Methods Galore Here////////////////////////////
/*
Method 0: .nodeImage loads BufferedImage of Node, depending on nodeStatus
*/
	public BufferedImage nodeImage(int nodeStatus)
	{
		BufferedImage node = null;
		if(nodeStatus == 0)//Uncleared dungeon. This is the default
		{
			node = loadDungeonImage();
		}
		if(nodeStatus == 1)//Dungeon where you took its skill. It is now empty.
		{
			node = loadClearedImage();
		}
		if(nodeStatus == 2)//Dungeon where you left its skill. It becomes a protective node
		{
			node = loadProtectiveNodeImage();
		}
		if(nodeStatus == 3)//Battlefield. Because there's a countdown, loads 2 images
		{
			node = loadBattlefieldImage();
		}
		if(nodeStatus == 4)//Enemy's territory now
		{
			node = loadEnemyNodeImage();
		}
		return node;
	}
	public BufferedImage loadDungeonImage()
	{
		BufferedImage dungeon = null;
		try
		{
			dungeon = ImageIO.read(new File("Node.jpg"));
		}
		catch(IOException e)
		{
		}
		return dungeon;
	}
	public BufferedImage loadClearedImage()
	{
		BufferedImage clearedDungeon = null;
		try
		{
			clearedDungeon = ImageIO.read(new File("CapturedNode.jpg"));
		}
		catch(IOException e)
		{
		}
		return clearedDungeon;
	}
	public BufferedImage loadProtectiveNodeImage()
	{
		BufferedImage sanctuary = null;
		try
		{
			sanctuary = ImageIO.read(new File("icon.png"));
		}
		catch(IOException e)
		{
		}
		return sanctuary;
	}
	public BufferedImage loadBattlefieldImage()
	{
		BufferedImage battlefield = null;
		try
		{
			battlefield = ImageIO.read(new File("BattlefieldNode.jpg"));
		}
		catch(IOException e)
		{
		}
		return battlefield;
	}
	public BufferedImage loadEnemyNodeImage()
	{
		BufferedImage  enemyNode = null;
		try
		{
			enemyNode = ImageIO.read(new File("LostNode.jpg"));
		}
		catch(IOException e)
		{
		}
		return enemyNode;
	}
}