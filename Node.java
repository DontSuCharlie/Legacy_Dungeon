//package WorldMap;
import java.awt.image.*;//image class allows for storage of BufferedImage files
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class Node extends JComponent implements MouseListener
{	
	MusicPlayer musicPlayer = new MusicPlayer();
	boolean isClicked;
	Mouse mouse;
	int x;
	int y;
	int skillID;
	int status;//0 = Uncleared, 1 = Skill Obtained; 2 = Protective; 3 = BattleField; 4 = Enemy; 5 = Heart
	int theme;
	static int difficulty;//Static because difficulty increases as game progresses
	BufferedImage image;
	ImageLoader imageLoader;
	static int yLength = 10;//length of images of nodes
	static int xLength = 10;//^
	boolean inRange;
    public Node(int x, int y, int inputSkill, int inputTheme)
	{
		super();
		this.x = x;
		this.y = y;
		skillID = inputSkill;
		theme = inputTheme;
		difficulty = 1;//defaults to 1, the starting difficulty
		status = 0;//(int)(Math.random()*5);//defaults to 0 bc all nodes start out as dungeons
		imageLoader = new ImageLoader();
		image = loadNodeImage(status);
		inRange = true;
		mouse = new Mouse();
		//this.setSize(100, 100);
		//this.setLocation(x, y);
		setBounds(x-30, y-30, 30, 30);
		addMouseListener(this);
	}
/*
Method 0: .nodeImage loads BufferedImage of Node, depending on nodeStatus
*/
	public BufferedImage loadNodeImage(int nodeStatus)
	{
		BufferedImage node = imageLoader.loadImage("images/Node" + nodeStatus + ".png");
		return node;
	}
/*
Method 1: Comparing nodes
*/
	public boolean equals(Node comparedNode)
	{
		if (this.x == comparedNode.x && this.y == comparedNode.y)
			return true;
		return false;
	}
/*
Making buttons for Mouse Listener
*/
	
/*private boolean withinRange(int x1, int y1)
	{
		//System.out.println(x+xLength/2);
		//System.out.println(x-xLength/2);
		//System.out.println(x1);
		if(x1 <= x+(100/2) && x1 >= x-(100/2))
		{
			if(y1 <= y+(100/2) && y1 >= y-(100/2))
				return true;
		}
		return false;
		//int[] xArray = {x-(xLength/2), x-(xLength/2), x+(xLength/2), x+(xLength/2)};
		//int[] yArray = {y-(yLength/2), y+(yLength/2), y+(yLength/2), y-(yLength/2)};
		//Polygon button = new Polygon(xArray, yArray, 4);
		//if(button.contains(x1,y1))
//			return true;
	//	return false;
	}
*/
/*
Implementing Mouse Listener
*/
	public void mouseClicked(MouseEvent e)
	{
			status = 2;
			image = loadNodeImage(status);
			isClicked = true;
			musicPlayer.playSound("sounds/button.wav");
			LegacyDungeon.turnCounter++;
			LegacyDungeon.inDungeon = true;
			repaint();
			
	}
	public void mouseEntered(MouseEvent e)
	{
		if(!isClicked)
		{
			status = 1;
			image = loadNodeImage(status);
			repaint();
		}
	}
	public void mouseExited(MouseEvent e)
	{
		if(!isClicked)
		{
			status = 0;
			image = loadNodeImage(status);
			repaint();
		}
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}

}