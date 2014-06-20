import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

//Used to get keyboard input, similar in use to imageLoader
public class KeyboardInput implements KeyListener
{
	public static boolean boolIsUp, boolIsDown, boolIsLeft, boolIsRight, boolIsAttack, boolIsInteracting, boolIsMoving, boolIs1, boolIs2, boolIs3, boolIs4, boolIs5,boolIs6, boolIs7, boolIs8, boolIs9, boolIs0, diagnostic;
	static boolean[] actionArray = {boolIsUp, boolIsDown, boolIsLeft, boolIsRight, boolIsAttack, boolIsInteracting, boolIs1, boolIs2, boolIs3, boolIs4, boolIs5,boolIs6, boolIs7, boolIs8, boolIs9, boolIs0, diagnostic};
	int[] keyArray = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_SHIFT, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9,KeyEvent.VK_0, KeyEvent.VK_TAB};
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
	        boolIs2 = true;
	    if(actionArray[8])
	        boolIs3 = true;
	    if(actionArray[9])
	        boolIs4 = true;
	    if(actionArray[10])
	        boolIs5 = true;
	    if(actionArray[11])
	        boolIs6 = true;
	    if(actionArray[12])
	        boolIs7 = true;
	    if(actionArray[13])
	        boolIs8 = true;
	    if(actionArray[14])
	        boolIs9 = true;
	    if(actionArray[15])
	        boolIs0 = true;	      
		if(actionArray[16])
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