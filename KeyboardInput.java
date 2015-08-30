package LegacyDungeon;

import org.lwjgl.glfw.*;

import java.util.concurrent.ConcurrentHashMap;
//java.lang.reflect 
//make a hashtable
//we'll have to create a new class for different contexts
//e.g. talking, in-dungeon, in world map

/*Class  aClass = ...//obtain class object
Method method =
    aClass.getMethod("doSomething", new Class[]{String.class});
*/
public class KeyboardInput extends GLFWKeyCallback
{
	private ConcurrentHashMap keyBindings = new ConcurrentHashMap<Integer,Object>();

	public KeyboardInput()
	{
		System.out.println("Created Key Listener");
		//initializes all the keys..haha, literal keys to the Keys in the Hashmap
		//Create method object
		//save as value
	}

	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		System.out.println(key);
		//keyBindings.get(key).invoke();
	}
}