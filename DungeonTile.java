/*This is the class that's in charge of drawing tiles?*/
import java.awt.image.*;
public class DungeonTile
{
	int x;
	int y;
	int tileID; //0 = wall, 1 = regular floor, 2 = trap 3 = stairs
	int connectionCap;
	int numConnections;
	int itemID; //0 = nothing, 1 = money, 2 = ?
	int goldAmount;
	//int characterID; //0 = nothing, 1 = player, 2 = enemy
	Character character;
	DeadCharacter deadCharacter;
	Projectile projectile;
	public BufferedImage tileImage;
	public BufferedImage itemImage;
	public BufferedImage charImage;
	ImageLoader imageLoader = new ImageLoader();
	Number number = null;

	//Test for improvement for generation
	boolean tileToLeft;
	boolean tileToRight;
	boolean tileToUp;
	boolean tileToDown;

	public DungeonTile(int xPos, int yPos, int inputTileID)
	{
		x = xPos;
		y = yPos;
		tileID = inputTileID;
		if(inputTileID == 1 || inputTileID == 2)
			tileImage = imageLoader.loadImage("images/DungeonTile" + inputTileID + ".png");
		connectionCap = 2;
		itemID = 0;
		//characterID = 0;
	}
	
	// main and toString for testing/debugging purposes.
	@Override
	public String toString()
	{
		return "DungeonTile [x=" + x + ", y=" + y + ", tileID=" + tileID
		+ ", connectionCap=" + connectionCap;
		// + numConnections + ", itemID=" + itemID + ", goldAmount="
		// + goldAmount + ", characterID=" + characterID + ", tileImage="
		// + tileImage + ", imageLoader=" + imageLoader + ", tileToLeft="
		// + tileToLeft + ", tileToRight=" + tileToRight + ", tileToUp="
		// + tileToUp + ", tileToDown=" + tileToDown + "]";
	}
}