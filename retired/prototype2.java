import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;
public class prototype2
{
	public prototype2()
	{
	}
	public void run
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		}
		catch(LWJGLException e)
		{
			System.out.println("Can't create window for unknown reason.");
		}
	}
}