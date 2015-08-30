package LegacyDungeon;

import org.lwjgl.glfw.*;

//scrolling, in the context of the dungeon allows you to switch between your skills quickly

public class MouseScroll extends GLFWScrollCallback
{
	public void invoke(long window, double xoffset, double yoffset)
	{
		System.out.printf("xoffset=%f\tyoffset= %f\n", xoffset, yoffset);
	}

}