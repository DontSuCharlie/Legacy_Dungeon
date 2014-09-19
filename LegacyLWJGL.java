//Why court confessions as absolute truth is flawed - the Big 5 NY
//package LegacyDungeon; http://stackoverflow.com/questions/10971838/making-a-java-package-in-the-command-line
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.opengl.ImageIOImageData;//ByteBuffer converter
import java.awt.*;//includes Dimension and .getWidth()/.getHeight() which allows us to define Window size based on user's computer
import java.awt.image.*;
import java.nio.*;//ByteBuffer for setIcon()

public class LegacyLWJGL
{
	//Global variables here
	static boolean inGame;
	public static void main(String[] args)
	{
		/*read save file here*/
		Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
		ImageLoader imageLoader = new ImageLoader();
		ImageIOImageData converter = new ImageIOImageData();
		
		/*creating Window with mouse and keyboard input*/
		//LWJGL requires a try/catch loop when creating the Display
			DisplayMode display = new DisplayMode((int)(screenRes.getWidth()/3), (int)(screenRes.getWidth()/3.2));

		try
		{
			Display.setDisplayMode(display);
			BufferedImage icon = imageLoader.loadImage("images/IconV0.png");
			ByteBuffer[] iconArray = {converter.imageToByteBuffer(icon, false, true, null)};
			//Note: for set icon - Windows = 1 16x16; Linux = 1 32 x 32; Mac OSX = 1 128x128. If not satisfied icon will not load
			Display.setIcon(iconArray);
			Display.setTitle("Legacy Dungeon");
			Display.create();
			Mouse.create();
			Keyboard.create();

		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		/*game loop*/
		while(!Display.isCloseRequested())
		{
			inGame = true;
			//loadMenu();
			while(inGame)
			{
				//loadDungeon(createWorld(turnCounter));
			}
		}
		Display.destroy();
	}
	public static void loadMenu()
	{
		Display.update();
	}
	public static boolean loadWorldMap()
	{
		Display.update();
		return true;
	}
	public static void loadDungeon()
	{
		Display.update();
		inGame = false;
	}
}

/*
	"Ideal" game loop: http://www.koonsolo.com/news/dewitters-gameloop/
	FPS is dependent of Game Update Speed. Game Update Speed is set constant. FPS might get in between game updates, so there will be a visual prediction of what'll happen.
	final int 
 const int TICKS_PER_SECOND = 25;
    const int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    const int MAX_FRAMESKIP = 5;

    DWORD next_game_tick = GetTickCount();
    int loops;
    float interpolation;

    bool game_is_running = true;
    while( game_is_running ) {

        loops = 0;
        while( GetTickCount() > next_game_tick && loops < MAX_FRAMESKIP) {
            update_game();

            next_game_tick += SKIP_TICKS;
            loops++;
        }

        interpolation = float( GetTickCount() + SKIP_TICKS - next_game_tick )
                        / float( SKIP_TICKS );
        display_game( interpolation );
    }
	
*/