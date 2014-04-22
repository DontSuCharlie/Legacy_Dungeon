import java.awt.*;//not sure
import java.awt.event.*;//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
import java.awt.image.*;//image class allows for storage of image files
import javax.swing.*;//part of UI
import javax.imageio.*;//handles the input/output of IMAGE files
import java.io.*;//handles input/output of files (ie: reads files)
import java.util.*;
public class NodeWorld{
    //Fields
	int x; //x coordinate on World Map
	int y; //y coordinate on World MAp
	int skillID; //Skill associated with Node
	int nodeStatus; //0 = Dungeon; 1 = Skill Obtained; 2 = Skill Kept; 3 = Battlefield; 4 = Enemy's
	int theme;//0 = ice; 1 = cave; 2 = castle?;3 = jungle; 4 = ocean
	//don't know, think we could use more _new_ themes
	static int difficulty; //linear increase in dungeon. Is static bc difficulty increases as game progresses.
	//Constructor
    public NodeWorld(int xPos, int yPos, int skill, int theme)
	{
		x = xPos;
		y = yPos;
		skillID = skill;
		this.theme = theme;
		difficulty = 1;//defaults to 1, the starting difficulty
		nodeStatus = 0;//defaults to 0 bc all nodes start out as dungeons
	}
//////////////////////////////////////Methods Galore Here////////////////////////////
/*
Method 0: .nodeImage loads image of Node, depending on nodeStatus
Method 1: 
*/
	public Image nodeImage(int nodeStatus)
	{
		if(nodeStatus == 0)//Uncleared dungeon. This is the default
		{
			loadDungeonImage();
		}
		if(nodeStatus == 1)//Dungeon where you took its skill. It is now empty.
		{
			loadClearedImage();
		}
		if(nodeStatus == 2)//Dungeon where you left its skill. It becomes a protective node
		{
			loadProtectiveNodeImage();
		}
		if(nodeStatus == 3)//Battlefield. Because there's a countdown, loads 2 images
		{
			loadBattlefieldImage();
		}
		if(nodeStatus == 4)//Enemy's territory now
		{
			loadEnemyNodeImage();
		}
	}
	public Image loadDungeonImage()
	{
		BufferedImage dungeon = null;
		try
		{
			dungeon = ImageIO.read(new File("/Images/Node_Concept.jpg"));
		}
		catch(IOException e)
		{
		}
		return dungeon;
	}
	public Image loadClearedImage()
	{
		BufferedImage clearedDungeon = null;
		try
		{
            // Note: add correct sprite later
			clearedDungeon = ImageIO.read(new File("/Images/Captured_Node_Concept.png"));
		}
		catch(IOException e)
		{
		}
		return clearedDungeon;
	}
	public Image loadProtectiveNodeImage()
	{
		BufferedImage sanctuary = null;
		try
		{
			sanctuary = ImageIO.read(new File("/Images/Captured_Node_Concept.jpg"));
		}
		catch(IOException e)
		{
		}
		return sanctuary;
	}
	public Image loadBattlefieldImage()
	{
		BufferedImage battlefield = null;
		try
		{
			battlefield = ImageIO.read(new File("/Images/Node_Under_Attack_Concept.jpg"));
		}
		catch(IOException e)
		{
		}
		return battlefield;
	}
	public Image loadEnemyNodeImage()
	{
		BufferedImage enemyNode = null;
		try
		{
			enemyNode = ImageIO.read(new File("/Images/Lost_Node_Concept.jpg"));
		}
		catch(IOException e)
		{
		}
		return enemyNode;
	}
}