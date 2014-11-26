/*Word filter, remove only 
Mass
MAss 
mAss <=
mASS <=
maSS
MYAss
myAss
MyAss
MYASS
MYAS

GOFUCKYOU
myass*/
//NOTE: The current plan is to make ONLY camera movement run in the main runner. Everything else is dependent on AnimatedImages.java. If it turns out AnimatedImages is less efficient, then we shall either make AnimatedImages a single thread (and test that) or return to main.

//WHAT IT DOES: It creates a component that runs an animation. This animation is very light in terms of CPU usage, and does not interact with the player. It's useful in that it makes the screen look alive and nice, even when the main thread is asleep (which will be often).

//HOW TO USE: Simply do the following:
/*
	1. Create a new AnimatedImages object.
		EXAMPLE: AnimatedImages variableName = new AnimatedObjects("File Directory of Sprite Sheet", int numberOfFrames);
	2. Sprite sheets are a sheet of sprites that contain all frames on a specific animation all in 1 neat image file. Of course, that means different sprite sheets can have different sized frames.
		The method .setFrameSize(int horizontalLength, int verticalLength) allows the file to read a specific frame size.
		The method .setAllFrameSize(int horizontalLength, int verticalLength) allows this class to have a DEFAULT frame size to read (which means if you don't call .setFrameSize on a specific object, it will DEFAULT to this size you have declared).
		What I would recommend is (assuming a majority of the sprites you have have a common frame) is to call .setAllFrameSize() first, then on specific objects that require a unique frame size, call .setFrameSize() on the specific object.
		EXAMPLE: variableName.setAllFrameSize(10, 10);
		variableName.setFrameSize(20, 20);
	3. Add the AnimatedObjects to your window.
		EXAMPLE: yourGameJFrame.add(variableName);
	4. Call the method .animate() to loop your animation
		EXAMPLE: variableName.animate(int xCoord, int yCoord);
	5. Call the method .animate(int numTimes) to play your animation a specific # of times.
		EXAMPLE: vairableName.animate(int xCoord, int yCoord, int numTimes);
	6.
*/
//HOW IT WORKS: Please read my comments :D
import java.awt.image.*;
import javax.swing.JComponent;
public class AnimatedImages extends JComponent implements Runnable
{
	private Thread t;
	static int defaultFrameX = 1000;//default frame width. If you don't set a default value, then this 1000 x 1000 frame size is saying "Fuck you for not setting a frame size".
	static int defaultFrameY = 1000;//default frame height
	int frameX;//object specific frame width
	int frameY;//object specific frame height
	BufferedImage spriteSheet;
	int numFrames;
	public AnimatedImages(String directory, int numFrames)
	{
		spriteSheet = ImageLoader.loadImage(directory);//loads sprite sheet
		this.numFrames = numFrames;
		frameX = defaultFrameX; //sets frameX to default
		frameY = defaultFrameY; //sets frameY to default
	}
	public void setFrameSize(int x, int y)
	{
		frameX = x;
		frameY = y;
	}
	public void setOrigFrameSize(int x, int y)
	{
		defaultFrameX = x;
		defaultFrameY = y;
		this.setFrameSize(x, y);
	}
	public void animate(int x, int y)
	{
		setBounds(x, y, frameX, frameY);
		while(true)//will come up with better, more elegant solution later
		{
			int locationX = 0;//sets sprite sheet location to 0
			int locationY = 0;
			for(int i = 0; i < numFrames; i++)
			{
				spriteSheet.getSubimage(locationX, locationY, frameX, frameY);//parameter of .getSubImage is int x, int y, int w, and int l
				locationX+=frameX;
				//locationY+=frameY;//i am dumbsass
				//frame per second
				repaint();
			}
		}
	}
	public void animate(int x, int y, int numTimes)
	{
		//run();
		setBounds(x, y, frameX, frameY);
		int locationX = 0;
		int locationY = 0;
		for(int i = 0; i < numTimes; i++)
		{
			for(int j = 0; j <numFrames; j++)
			{
				spriteSheet.getSubimage(locationX, locationY, frameX, frameY);
				locationX+=frameX;
				locationY+=frameY;
				//frames per second
				repaint();
			}
		}
	}
	public void run()
	{
		try
		{
			System.out.println("I'm runnin baby");
			if( t == null)
			{
				t = new Thread(this, "ThreadName");
				t.start();
			}
		}
		catch(InterruptedException e)
		{
			System.out.println("Fuck you I got interrupted");
		}
	}
}