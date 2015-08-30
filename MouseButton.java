package LegacyDungeon;

import org.lwjgl.glfw.*;

//holding down the left button, in the context of the dungeon, signals that you are going to be attacking
//holding down the right button...does something

public class MouseButton extends GLFWMouseButtonCallback
{
	public void invoke(long window, int button, int action, int mods)
	{
		System.out.println(button);
	}

}