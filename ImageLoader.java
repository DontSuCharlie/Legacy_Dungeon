//NOTE: Difference between ImageIO and Toolkit is that Toolkit uses a separate thread to run it, which means that the game will continue even if the image is not completely loaded. This means that we have to somehow stop it.
//
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader
{
	public BufferedImage loadImage(String fileDirectory)
	{
		try 
		{
			return ImageIO.read(getClass().getResource(fileDirectory));
		} 
		catch (IOException e) 
		{
			System.out.println("I cannot find the following Image file:\"" + fileDirectory + "\" :( ");
			e.printStackTrace();
		}
		return null;
	}
}
/*
HOW TO USE IN 3 EASY STEPS!:
Create ImageLoader object
Create an Image or BufferedImage variable
Assign variable's value to ImageLoaderObject.loadImage(enter name of file you want)

*/