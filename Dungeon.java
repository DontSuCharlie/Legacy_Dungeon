import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Dungeon
{
    ImageLoader imageLoader = new ImageLoader();
	BufferedImage[] numArray = new BufferedImage[10];
	BufferedImage[] tileImage = new BufferedImage[3];
	String[] numDirectory = {"0.png", "1.png", "2.png", "3.png", "4.png",  "5.png", "6.png", "7.png", "8.png", "9.png"};//to declare BufferedImages
	String[] tileDirectory = {"Wall.png", "DungeonTile1.png", "DungeonTile2.png"};
    BufferedImage money = imageLoader.loadImage("coinGold.png");
    BufferedImage playerImageEast = imageLoader.loadImage("playerEast.png");
    BufferedImage playerImageWest = imageLoader.loadImage("playerWest.png");
    BufferedImage playerImageNorth = imageLoader.loadImage("playerNorth.png");
    BufferedImage playerImageSouth = imageLoader.loadImage("playerSouth.png");
    BufferedImage slimeImageEast = imageLoader.loadImage("slimeEast.png");
    BufferedImage slimeImageWest = imageLoader.loadImage("slimeWest.png");
    BufferedImage slimeImageNorth = imageLoader.loadImage("slimeNorth.png");
    BufferedImage slimeImageSouth = imageLoader.loadImage("slimeSouth.png");
    BufferedImage slimeImageEastWalk = imageLoader.loadImage("slimeEastWalk.png");
    BufferedImage slimeImageWestWalk = imageLoader.loadImage("slimeWestWalk.png");
    BufferedImage slimeImageNorthWalk = imageLoader.loadImage("slimeNorthWalk.png");
    BufferedImage slimeImageSouthWalk = imageLoader.loadImage("slimeSouthWalk.png");
    BufferedImage slimeImageEastDead = imageLoader.loadImage("slimeEastDead.png");
    BufferedImage slimeImageWestDead = imageLoader.loadImage("slimeWestDead.png");
    BufferedImage slimeImageNorthDead = imageLoader.loadImage("slimeNorthDead.png");
    BufferedImage slimeImageSouthDead = imageLoader.loadImage("slimeSouthDead.png");
    BufferedImage slimeImageEastHit = imageLoader.loadImage("slimeEastHit.png");
    BufferedImage slimeImageWestHit = imageLoader.loadImage("slimeWestHit.png");
    BufferedImage slimeImageNorthHit = imageLoader.loadImage("slimeNorthHit.png");
    BufferedImage slimeImageSouthHit = imageLoader.loadImage("slimeSouthHit.png");
	public Dungeon()
	{
		for(int i = 0; i < numArray.length; i++)
		{
			numArray[i] = imageLoader.loadImage(numDirectory);
		}
		for(int i = 0; i < tileImage.length; i++)
		{
			tileImage[i] = imageLoader.loadImage(tileDirectory);
		}
	}
}