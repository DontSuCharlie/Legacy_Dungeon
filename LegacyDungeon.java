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
	private static long window;//the reference to the actual window
	private static void init()
	{//initialize everything
		//Creating window with GLFW
		if(glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW! Aborting!");
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);//makes Window invisible for now
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);//no resizing
		//user profile
		int windowWidth = 500;
		int windowHeight = 500;
		window = glfwCreateWindow(windowWidth, windowHeight, "Legacy Dungeon", NULL, NULL);//this creates the Window
		if(window == NULL)
			throw new RuntimeException("Failed to create window..Aborting!");
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window,(GLFWvidmode.width(vidmode)-windowWidth)/2,(GLFWvidmode.height(vidmode)-windowHeight)/2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);//waits for 1 frame to wait before swapping buffers. 1 is recommended by GLFW bc it prevent screen tearing (=0) and doesn't lead to input latency (>1)
		glfwSetWindowCloseCallback(window,new GLFWWindowCloseCallback(){
			public void invoke(long callBack)
			{
				term();
			}
		});
		glfwShowWindow(window);
		KeyboardInput key = new KeyboardInput();
		glfwSetKeyCallback(window, key);
		
	}
	/*
	These two methods (createWindow and loadSaveFile) will be called concurrently from init().
	createWindow will create the window.
	loadSaveFile will read the save file.
	The introAnimation is called by createWindow in the scenario 
	*/
	private static void createWindow()
	{

	}
	private static void introAnimation()
	{

	}
	private static void loadSaveFile()
	{

	}
	private static void loop()
	{
		/*
		In the game loop, there will be 2-3 primary threads that we'll be in charge of (GLFW will be managing I/O). 
		Thread 1 is the normal thread where everything in the game happens
		Thread 2 is the save thread where it constantly updates the save file whenever something significant happens
		Thread 3 is the AI thread for monsters, bosses, etc.
		*/
		GLContext.createFromCurrent();
		glClearColor(1,1,1,1);
		while(true)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glfwSwapBuffers(window);
			glfwPollEvents();//polls events continuously
		}
	}
	private static void term()
	{//terminate everything

		glfwTerminate();
	}

	//Creating key input with GLFW. This is the GLOBAL key input. Do we need a global key input?
	private static void invoke(long window, int key, int scancode, int action, int mods)
	{

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