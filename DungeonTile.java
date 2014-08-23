/*This is the class that's in charge of drawing tiles?*/
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Collections;
public class DungeonTile implements Comparable<DungeonTile>
{
	int x;
	int y;
	int tileID; //0 = wall, 1 = regular floor, 2 = trap 3 = stairs
	int connectionCap;
	int numConnections;
	int itemID; //0 = nothing, 1 = money, 2 = Show item icon. 3= Show multiple item icon.
	ArrayList<GameItem> items;
	int goldAmount;
	//int characterID; //0 = nothing, 1 = player, 2 = enemy
	Character character;
	//DeadCharacter deadCharacter;
	ArrayList<DeadCharacter> deadCharTileList = new ArrayList<DeadCharacter>();
	Projectile projectile;
	public BufferedImage tileImage;
	public BufferedImage itemImage;
	public BufferedImage charImage;
	ImageLoader imageLoader = new ImageLoader();
	Number number = null;

	public DungeonTile(int xPos, int yPos, int inputTileID)
	{
		x = xPos;
		y = yPos;
		tileID = inputTileID;
		//if(inputTileID == 1 || inputTileID == 2)
		//	tileImage = imageLoader.loadImage("images/DungeonTile" + inputTileID + ".png");
		connectionCap = 2;
		itemID = 0;
		//characterID = 0;
	}
	
	/**
	 * Because multiple items can be on a tile, I have to pick which one to use. I just get the first one, because items are sorted when they are placed on a tile.
	 * @return Image of highest priority item.
	 */
	public BufferedImage getItemImage(DungeonMain lDungeon)
	{
	    assert (itemID != 0);
	    //Sorted when items are placed on this tile.
	    //Collections.sort(items);
	    return items.get(0).getImage(lDungeon);
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
    
	/**
	 * This compare method is used for A* pathFinding. (non-Javadoc)
	 * Different tile types will have different costs. Spikes may be weighted +3 vs. regular tiles for example.
	 */ 
	
	
    @Override
    public int compareTo(DungeonTile other)
    {
        //We want the lowest score, so if the other is bigger then then this one should be placed lower. (Negative return values place this higher and positive ones place other higher.
        int temp = this.y - other.y;
        
        if (temp != 0)
        {
            return (temp);
        }
        
        //In the case of a tie, we want the larger cost, because that'll be closer to the target.
        else
        {
            return (this.x - other.x);
        }
    }
    /*
    public boolean equals(Object obj)
    {
        //We want the lowest score, so if the other is bigger then then this one should be placed lower. (Negative return values place this higher and positive ones place other higher.
        return (this.pathFindingHeuristic + this.pathFindingCost - other.pathFindingHeuristic - other.pathFindingCost);
    }*/

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + goldAmount;
        result = prime * result + itemID;
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result
                + ((projectile == null) ? 0 : projectile.hashCode());
        result = prime * result + tileID;
        result = prime * result
                + ((tileImage == null) ? 0 : tileImage.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof DungeonTile))
        {
            return false;
        }
        DungeonTile other = (DungeonTile) obj;
        if (goldAmount != other.goldAmount)
        {
            return false;
        }
        if (itemID != other.itemID)
        {
            return false;
        }
        if (number == null)
        {
            if (other.number != null)
            {
                return false;
            }
        }
        else if (!number.equals(other.number))
        {
            return false;
        }
        if (projectile == null)
        {
            if (other.projectile != null)
            {
                return false;
            }
        }
        else if (!projectile.equals(other.projectile))
        {
            return false;
        }
        if (tileID != other.tileID)
        {
            return false;
        }
        if (tileImage == null)
        {
            if (other.tileImage != null)
            {
                return false;
            }
        }
        else if (!tileImage.equals(other.tileImage))
        {
            return false;
        }
        if (x != other.x)
        {
            return false;
        }
        if (y != other.y)
        {
            return false;
        }
        return true;
    }
}