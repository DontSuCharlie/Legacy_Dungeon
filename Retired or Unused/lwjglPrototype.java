import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;
public class lwjglPrototype
{
	public static void main(String[] args)
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
		while(!Display.isCloseRequested())
		{
			Display.update();
		}
		Display.destroy();
		//System.setProperty("java.library.path","./native");
		//System.load("C:/Users/Charlie/Desktop/Github/LegacyDungeon/native/jinput-dx8_64.dll");
		//System.load("C:/Users/Charlie/Desktop/Github/LegacyDungeon/native/jinput-raw_64.dll");
		//System.load("C:/Users/Charlie/Desktop/Github/LegacyDungeon/native/lwjgl64.dll");			
		//System.load("C:/Users/Charlie/Desktop/Github/LegacyDungeon/native/OpenAL64.dll");
		//prototype2 proto = new prototype2();
		//proto.run();
		//to make it multi-platform, we need all 3 natives and 3 if statements
		/*
		*/
		System.out.println("hello world");
	}
}