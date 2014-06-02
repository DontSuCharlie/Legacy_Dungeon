import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener
{
	boolean isClicked;
	boolean isEntered;
	boolean isExited;
	boolean isReleased;
	boolean isPressed;
	public Mouse()
	{
		isClicked = false;
		isEntered = false;
		isExited = false;
		isReleased = false;
	}
	public boolean clicked()
	{
		return isClicked;
	}
	public boolean entered()
	{
		return isEntered;
	}
	public boolean exited()
	{
		return isExited;
	}
	public boolean pressed()
	{
		return isPressed;
	}
	public boolean released()
	{
		return isReleased;
	}
	public void mouseClicked(MouseEvent e)
	{
		isClicked = true;
		System.out.println("I is clicked!");
	}
	public void mouseEntered(MouseEvent e)
	{
		System.out.println("I is Enter!");
		isEntered = true;
		isExited = false;
	}
	public void mouseExited(MouseEvent e)
	{
		isExited = true;
		isEntered = false;
	}
	public void mousePressed(MouseEvent e)
	{
		isPressed = true;
	}
	public void mouseReleased(MouseEvent e)
	{
		isReleased = true;
		isClicked = false;
		isPressed = false;
	}
}