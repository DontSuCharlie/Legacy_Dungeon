/*
PURPOSE/USE: In essence this class will be used to test new methods. This class will ONLY have fully completed functions! Also, this class doesn't have a game loop implemented.
ANY VARIABLES/METHODS/ETC. WILL HAVE THE COMMENT //test RIGHT NEXT TO IT
////////////////////////////////////LIST OF METHODS////////////////////////////////////////////
List of useable methods:
createWindow() - creates the window
paint() - needed to actually draw graphics
Current tested method:
MaxAreaPolygon.java's makePolygon()
*/
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
public class TestGame extends JPanel
{
	ArrayList<NodeWorld> nodeList;
	static JFrame window;
	WorldMap world;
	static int turnCounter = 0;
	MaxAreaPolygon maxAreaPolygon;//test
	public TestGame()
	{
		window = new JFrame("Hazardous Laboratory");
		world = new WorldMap();
		nodeList = world.getNodeList();
		maxAreaPolygon = new MaxAreaPolygon();//test
		Config config = new Config();
	}
	public static void main(String[] args)
	{
		TestGame game = new TestGame();
		createWindow();
		window.add(game);
		game.repaint();
		//Insert what you need to test here
	}
//Insert test methods below

//Insert test methods above

//Methods that already work
/////////////////////////////////////////////////////////////////Method 0: Creating the Window
   public static void createWindow()
   {
      Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
      window.setSize((int)(screenRes.getWidth()/2),(int)(screenRes.getWidth()/2.2));//Sets size in pixels based on player's screen
      int windowX = (int) (window.getWidth());//grabs the width of window made
      int windowY = (int) (window.getHeight());//grabs the height of window made
      int windowPosX = (int) (screenRes.getWidth() - windowX)/2;//obtains width of user screen, divides by two, then subtracts by size of window
      int windowPosY = (int) (screenRes.getHeight() - windowY)/2;//same as above except with height
      //Creating the frame
      JFrame.setDefaultLookAndFeelDecorated(true);//allows for customization of icons/windows/etc.
      Toolkit toolkit = Toolkit.getDefaultToolkit();//uses the toolkit, which allows for reading of image file
      Image icon = toolkit.getImage("icon.png");
      window.setResizable(true);
      window.setIconImage(icon); //Sets icon image
        KeyboardInput input = new KeyboardInput(); //Used for keyboard input
        window.add(input);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
      window.setLocation(windowPosX, windowPosY);//sets location to center
      window.setVisible(true);//Makes it visible...
    }
//Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
//Calling game.repaint() will update what's in here.
	@Override
	public void paintComponent(Graphics g)
	{
		Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(g);//we have to do super because magic
		//Graphics2D g2 = (Graphics2D) g;
		//Border lineborder = 
		//g.setStroke(new BasicStroke(10));
		g.setColor(Color.red);
		if(true)
		{
			g.drawImage(world.map, 0, 0, (int)(screenRes.getWidth()/2),(int)(screenRes.getWidth()/2.2),null);
		}
		for(int i = 0; i < nodeList.size(); i++)
		{
			g.drawImage(nodeList.get(i).nodeImage,nodeList.get(i).x,nodeList.get(i).y,20,20,null);
		}
		//testSTART
		ArrayList<NodeWorld> validList = new ArrayList<NodeWorld>();
		for(int i = 0; i < nodeList.size(); i++)
		{
			if(nodeList.get(i).nodeStatus == 2)
			{
				validList.add(nodeList.get(i));
			}
		}
		if(validList.size() >= 3)
		{
			g.drawPolygon(maxAreaPolygon.makePolygon(validList));
		}
		//testEND
	}
}