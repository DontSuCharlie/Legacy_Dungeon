/*

The gravity of time: Om tje 4th dimension, there exists a time's version of gravity, where up is the past and down is the future. E
Window.java is in charge of everything related to Windows, window resolution, window sizes, etc.
*/
//package LegacyDungeon;
import java.awt.*;//includes Color, Dimension, Graphics, etc.
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

/*
	1. Loading Window Opens - SwingWorker in background initializes every single variable along with checking for save file
	2. Menu opens
		a. Play game
	3. Make window invisible, close threads, update save file
		a. If an error appears - create a new JFrame that tells the user about the issue
*/

public class Window extends JPanel
{
	static JFrame window;
	static Dimension screenRes;
	static int windowX;
	static int windowY;
	static int windowXPos;
	static int windowYPos;
	/*
	public Window()
	{
	}*/
	public static void main(String[] args)
	{
		Window.createWindow();
		//window.addMouseListener();
	}
	public static void coord(int x, int y)
	{
		//Set middle of screen as origin
		//int x and int y are where we WANT it to be
		//output should be where it ACTUALLY is
		x = x+windowX;
		y = y+windowY;
		
	}
////////////////////////////////////////////////////////Method 0: Creates window
	public static void createFullScreen()
	{
	}
	public static void createWindow(boolean decorated)
	{
	    window = new JFrame("Legacy Dungeon");
		screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		//Reads save file to see current resolution; otherwise goes to default size
		//windowX = (int)(screenRes.getWidth()/2);
		//windowY = (int)(screenRes.getWidth()/2.2);//set Y to width because I wanted a square
		
	    windowX = (int)(screenRes.getWidth());
	    windowY = (int)(screenRes.getHeight());//No, I like it fitting the screen.
		window.setSize(windowX, windowY);
		
		//Set default to center
		windowXPos = (int)(screenRes.getWidth() - windowX)/2;
		windowYPos = (int)(screenRes.getHeight() - windowY)/2;
		
		//gets window operational
		if(decorated)
		{
			JFrame.setDefaultLookAndFeelDecorated(true);//wtf, why would Java have this? This makes it so we can customize stuff. Why would you make it false at any time????
			KeyboardInput input = new KeyboardInput(); //Used for keyboard input
			window.addKeyListener(input);
		}		
		else
			window.setUndecorated(true);
		window.setResizable(false);//too messy for now, will change to true later
		window.setIconImage(ImageLoader.loadImage("/images/icon.png"));//sets icon image
		window.setLocation(windowXPos, windowYPos);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes program when "X" button is pressed
		window.setVisible(true);
    }
}