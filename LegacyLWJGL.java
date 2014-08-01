/*
Current updates:
1) Window has been established. Adding icon soon.
2) Adding mouse/keyboard input soon.
3) Looking at OpenGL soon.
*/
//Why court confessions as absolute truth is flawed - the Big 5 NY
//package LegacyDungeon; http://stackoverflow.com/questions/10971838/making-a-java-package-in-the-command-line
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;
import java.awt.*;//includes Dimension and .getWidth()/.getHeight() which allows us to define Window size based on user's computer
import java.awt.image.*;
import java.nio.*;//ByteBuffer for setIcon()

public class LegacyLWJGL
{
	public static void main(String[] args)
	{
		/*read save file here*/
		//LWJGL requires a try/catch loop
		Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		ImageLoader imageLoader = new ImageLoader();
		try
		{
			Display.setDisplayMode(new DisplayMode((int)(screenRes.getWidth()/4), (int)(screenRes.getWidth()/4.2)));
			BufferedImage icon = imageLoader.loadImage("images/tempIcon.png");
			ImageToBytes bytes = new ImageToBytes();
			ByteBuffer[] iconArray = {bytes.returnBytes(icon)};
			//set icon - Windows = 1 16x16; Linux = 1 32 x 32; Mac OSX = 1 128x128
			//ByteBuffer[] iconArray = {createBuffer(imageLoader.loadImage("images/Wall"))};
			Display.setIcon(iconArray);
			Display.setTitle("Legacy Dungeon");
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		while(!Display.isCloseRequested())
		{
			//loadMenu();
			/*
			while(inGame)
			{
				loadDungeon(createWorld(turnCounter));
			}
			*/
			//Display.update();
		}
		Display.destroy();
	}
}