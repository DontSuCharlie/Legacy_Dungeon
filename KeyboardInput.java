import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

//Used to get keyboard input, similar in use to imageLoader
public class KeyboardInput implements KeyListener
{
	public static boolean boolIsUp, boolIsDown, boolIsLeft, boolIsRight, boolIsAttack, boolIsInteracting, boolIsMoving, boolIs1, diagnostic;
	static boolean[] actionArray = {boolIsUp, boolIsDown, boolIsLeft, boolIsRight, boolIsAttack, boolIsInteracting, boolIs1, diagnostic};
	int[] keyArray = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_SHIFT, KeyEvent.VK_1, KeyEvent.VK_TAB};
    public static int key;
	
	public static void update(boolean[] actionArray)
	{
		if(actionArray[0])
			boolIsUp = true;
		if(actionArray[1])
			boolIsDown = true;
		if(actionArray[2])
			boolIsLeft = true;
		if(actionArray[3])
			boolIsRight = true;
		if(actionArray[4])
			boolIsAttack = true;
		if(actionArray[5])
			boolIsInteracting = true;
		if(actionArray[6])
		    boolIs1 = true;
		if(actionArray[7])
		    diagnostic = true;
	}
	
	public static void main(String[] args)
	{
		Window window = new Window();
		Window.createWindow();
		Window.window.addKeyListener(new KeyboardInput());
	}
	
	public void keyTyped(KeyEvent e)
	{
		//useless, don't need
	}
	public void keyPressed(KeyEvent e)
	{
		key = e.getKeyCode();
		for(int i = 0; i < keyArray.length; i++)
		{
			if(keyArray[i] == key)
			{
			    System.out.println(i);
				actionArray[i] = true;
				update(actionArray);
				if(i <4)//all arrow keys have indices less than 4
					boolIsMoving = true;
				break;
			}
		}
       System.out.println("keyPressed: " + key);
	 }
	   
	public void keyReleased(KeyEvent e)
	{
		key = e.getKeyCode();
		for(int i = 0; i < keyArray.length; i++)
		{
			if(keyArray[i] == key)
			{
				actionArray[i] = false;
				//update();
				System.out.println(boolIsAttack);
				break;
			}
		}
		System.out.println("keyReleased: " + key);
	}
}