package LegacyDungeon;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class LegacyDungeon
{
	private static long window;
	private static void init()
	{//initialize everything
		//Creating window with GLFW
		if(glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW! Aborting!");
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);//makes Window invisible for now
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);//no resizing
		//user profile
		int width = 500;
		int height = 500;
		window = glfwCreateWindow(width, height, "Legacy Dungeon", NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Failed to create window..Aborting!");
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window,(GLFWvidmode.width(vidmode)-width)/2,(GLFWvidmode.height(vidmode)-height)/2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwSetWindowCloseCallback(window,new GLFWWindowCloseCallback(){
			public void invoke(long callBack)
			{
				term();
			}
		});
		glfwShowWindow(window);
	}
	private static void loop()
	{
		GLContext.createFromCurrent();
		glClearColor(1,1,1,1);
		while(true)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(window);
			glfwPollEvents();//polls events
		}
	}
	private static void term()
	{//terminate everything
		glfwTerminate();
	}
	//Global variables here
	private boolean inGame;
	public static void main(String[] args)
	{
		/*read save file here*/
		init();
		loop();
		term();
	}
}