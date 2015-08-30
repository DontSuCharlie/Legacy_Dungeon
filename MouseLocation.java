package LegacyDungeon;

import org.lwjgl.glfw.*;

//mouse location is going to be FUCKING important. 
//The position of your mouse relative to character origin (not actual window origin) determines which way you're facing (and therefore walking)
//While holding down the left mouse button, CHANGE in your position will determine how you hack and slash and how much damage/what skills you might activate


public class MouseLocation extends GLFWCursorPosCallback
{
	public void invoke(long window, double xpos, double ypos)
	{
		System.out.printf("xpos=%f\typos=%f\t\n", xpos, ypos);
	}
}