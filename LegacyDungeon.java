package LegacyDungeon;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;//contains some core functions we need
import static org.lwjgl.opengl.GL15.*;//contains glgen() and glbind() functions, which is needed to allow OpenGL to manipulate objects from the GPU
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import static org.lwjgl.system.MemoryUtil.*;

public class LegacyDungeon
{
	private static boolean isRunning = true;
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
				isRunning = false;
				term();
			}
		});
		KeyboardInput key = new KeyboardInput();
		MouseLocation pos = new MouseLocation();
		MouseScroll mouseScroll = new MouseScroll();
		MouseButton mouseClick = new MouseButton();
		glfwSetKeyCallback(window, key);
		glfwSetCursorPosCallback(window, pos);
		glfwSetMouseButtonCallback(window, mouseClick);
		glfwSetScrollCallback(window, mouseScroll);
		glfwShowWindow(window);
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

		//Step 1: defining vertices
		float[] vertexBufferData = {
			-1.0f, -1.0f,
			1.0f, -1.0f,
			-1.0f, 1.0f,
			1.0f, 1.0f
		}

		//Drawing
		float[] bullshit = {
			-0.5f, -0.5f,
			-0.5f, 0.5f,
			0.5f, -0.5f,
			0.5f, 0.5f,
			-0.5f, 0.5f,
			0.5f, -0.5f,
		};//it looks like a 2x6 Matrix. I'm not sure whether we're going to go by columns or rows
		//Also I have no idea what this 2x6 Matrix is supposed to draw. I THINK it's 6 2D points...maybe a hexagon?
		//Also, should we use a math library? It might be more efficient?
		//Also why are we using Java

		//An explanation of OpenGL's coordinate system according to static void games

		//The origin (0,0) is at the center of the window
		//y is vertical, with +y = up; -y = down
		//x is horizontal, with +x = right; -x = left
		//the possible values of x include -1<=x<=1, with -1 being the left edge, 1 being the right edge
		//the possible values of y are the same (with -1 being the bottom edge, 1 being the top edge)
		//it looks like, we are indeed drawing 6 points. We're drawing 2 triangles that touch each other, creating a square
		//I'm THINKING that the left is x, right is y, and it draws lines from coordinate point to coordinate point
		//How does OpenGL determine that a set of vertices is indeed a closed polygon?

		//In OpenGL 3+, rendering is done through SHADERS
		//There are 2 types of primary shaders: the vertex shader () and the fragment shader (???)
		//Shaders are programs
		//The GLSL standard says that GPU's have compilers that accept String input, meaning to make a shader program, we'll have to create String variables
		//What is the purpose of shaders? What exactly do they manipulate and how do they mnaipulate it?


		String vertexShader = "#version 330\n" + 
			"in vec2 position;\n" +
			"void main(){\n"+
			"	gl_Position=vec4(position,0,1);\n"+
			"}\n";
		String fragmentShader = "#version 330\n"+
			"out vec4 out_color;\n"+
			"void main(){\n"+
			"	out_color=vec4(0f,1f,1f,1f);\n"+
			"}\n";
		//memAllocFloat();//malloc float


		while(isRunning)
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
	private static boolean inGame;
	public static void main(String[] args)
	{
		/*read save file here*/
		init();
		loop();
		term();
	}
}