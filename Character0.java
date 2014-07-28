import java.awt.image.*;

public class Character0
{
	ImageLoader imageLoader;
	int x;
	int y;
	int width;
	int height;
	BufferedImage image;
	public Character0()
	{
		imageLoader = new ImageLoader();
		image = imageLoader.loadImage("images/player1.png");
		width = image.getWidth();
		height = image.getHeight();
		x = Window.windowX/2;
		y = Window.windowX/2;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}