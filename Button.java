//package GUI
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class Button extends JComponent implements MouseListener
{
	MusicPlayer musicPlayer = new MusicPlayer();
	BufferedImage notPressed;
	BufferedImage mouseOver;
	BufferedImage isPressed;
	BufferedImage currentImage;
	boolean pressed;
	boolean clicked;
	boolean entered;
	boolean exited;
	int x;
	int y;
	int height;
	int length;
	String text;
	int fontX;
	int fontY;

	public Button(String notPressedImage, String mouseOverImage, String pressedImage, int x, int y, int length, int height)
	{
		notPressed = ImageLoader.loadImage(notPressedImage);
		mouseOver = ImageLoader.loadImage(mouseOverImage);
		isPressed = ImageLoader.loadImage(pressedImage);
		currentImage = notPressed;
		//this.text = text;
		this.x = x;
		this.y = y;
		this.height = height;
		this.length = length;
		setBounds(x, y, length, height);
		addMouseListener(this);	
	}
	
	public void mouseClicked(MouseEvent e)
	{
		currentImage = mouseOver;
		clicked = true;
		pressed = false;
		repaint();
	}
	public void mouseEntered(MouseEvent e)
	{
		currentImage = mouseOver;
		entered = true;
		exited = false;
		repaint();
	}
	public void mouseExited(MouseEvent e)
	{
		currentImage = notPressed;
		entered = false;
		exited = true;
		repaint();
	}
	public void mousePressed(MouseEvent e)
	{
		currentImage = isPressed;
		musicPlayer.playSound("sounds/button2.wav");
		pressed = true;
		clicked = false;
		repaint();
	}
	public void mouseReleased(MouseEvent e)
	{
		currentImage = notPressed;
		repaint();
	}
}