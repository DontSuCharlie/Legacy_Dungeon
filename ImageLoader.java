//package LegacyDungeon;
//NOTE: Difference between ImageIO and Toolkit is that Toolkit uses a separate thread to run it, which means that the game will continue even if the image is not completely loaded. This means that we have to somehow stop it.
//
//7/31 Update: 
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class ImageLoader
{    
	public static BufferedImage loadImage(String fileDirectory)
	{
		try{
			ImageLoader imageLoader = new ImageLoader();
			return ImageIO.read(imageLoader.getClass().getResource(fileDirectory));
		}
		catch (IOException e)
		{
			e.getMessage();
		}
		return null;
	}

}
/*
HOW TO USE IN 1 EASY STEP!
Image myImage = ImageLoader.loadImage("imageName.jpg");
HOW TO USE IN 3 EASY STEPS!:
Create ImageLoader object
Create an Image or BufferedImage variable
Assign variable's value to ImageLoaderObject.loadImage(enter name of file you want)
*/