//package GUI
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public class Button extends JComponent implements MouseListener
{
	musicPlayer musicPlayer = new musicPlayer();
	BufferedImage notPressed;
	BufferedImage mouseOver;
	BufferedImage isPressed;
	BufferedImage currentImage;
	boolean pressed;
	boolean clicked;
	boolean entered;
	boolean exited;
	ImageLoader imageLoader = new ImageLoader();
	int x;
	int y;
	int height;
	int length;
	String text;
	int fontX;
	int fontY;

	public Button(String notPressedImage, String mouseOverImage, String pressedImage, int x, int y, int length, int height)
	{
		notPressed = imageLoader.loadImage(notPressedImage);
		mouseOver = imageLoader.loadImage(mouseOverImage);
		isPressed = imageLoader.loadImage(pressedImage);
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
		musicPlayer.playSound("sounds/button.wav");
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