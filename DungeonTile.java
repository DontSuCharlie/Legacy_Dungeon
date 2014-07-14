/*This is the class that's in charge of drawing tiles?*/
import java.awt.image.*;
import java.util.ArrayList;
public class DungeonTile implements Comparable<DungeonTile>
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
        result = prime * result
                + ((character == null) ? 0 : character.hashCode());
        result = prime * result
                + ((deadCharacter == null) ? 0 : deadCharacter.hashCode());
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
        if (character == null)
        {
            if (other.character != null)
            {
                return false;
            }
        }
        else if (!character.equals(other.character))
        {
            return false;
        }
        if (deadCharacter == null)
        {
            if (other.deadCharacter != null)
            {
                return false;
            }
        }
        else if (!deadCharacter.equals(other.deadCharacter))
        {
            return false;
        }
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
    
    /**
     * Return the tiles adjacent to the given one. Used in PathFinder(). The isFine adds the additional property of not having characters on it. Need to disable it for if the the target is a character though.
     * Note: Returns diagonals. Undecided about what to do about them. Could add penalty for diags.
     * 
     */
	/*
    public ArrayList<DungeonTile> getAdjacentTilesAndSetValues(DungeonMain lDungeon, DungeonTile tile, DungeonTile targetTile, boolean isFine)
    {
        ArrayList<DungeonTile> returnList = new ArrayList<DungeonTile>();
        if (lDungeon.dungeon.tileChecker(tile.x+1,  tile.y, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x + 1][tile.y]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x-1,  tile.y, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x - 1][tile.y]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x,  tile.y+1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x][tile.y + 1]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x,  tile.y-1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x][tile.y - 1]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x+1,  tile.y+1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x+1][tile.y+1]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x+1,  tile.y-1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x+1][tile.y - 1]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x-1,  tile.y+1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x-1][tile.y + 1]);
        }
        
        if (lDungeon.dungeon.tileChecker(tile.x-1,  tile.y-1, isFine))
        {
            returnList.add(lDungeon.dungeon.tileList[tile.x-1][tile.y - 1]);
        }
        
        //:< Inefficient check if we are next to the target. 
        if (Math.abs(tile.x - targetTile.x) <= 1 && Math.abs(tile.y - targetTile.y) <= 1)
        {
            returnList.add(targetTile);
        }
        
        setPathFindingValues(returnList, targetTile, tile);
        
        return returnList;
        
    }
    /**
     * The bread and butter of A* pathing.
     * This heuristic is basically an estimate of how far away we are from the goal. We use that to pick which tiles to use.
     * If we're far from the goal then the position of characters isn't too relevant- they'll be different by the time we get there. That isn't too important.
     * I currently use Manhattan distance which adds distance on the x axis and the y axis. I am avoiding diagonal movement but that shouldn't be too hard to add if desired.
     * 
     * In each pass, we need to find out the costs of the new tiles to be checked
     * This involves the type of tile it is and the cost it took to get the tile just before this one.
     * 
     */
    /*
    private void setPathFindingValues(ArrayList<DungeonTile> tileList, DungeonTile targetTile, DungeonTile sourceTile)
    {
        for (DungeonTile tile : tileList)
        {
            //Using Chebychev distance to account for diagonal movement. If no diag, then better to use Manhattan distance.
            tile.pathFindingHeuristic = Math.max(Math.abs(targetTile.x - tile.x), Math.abs(targetTile.y - tile.y));
            
            //Manhattan distance, better if no diagonals are needed.
            //tile.pathFindingHeuristic = Math.abs(targetTile.x - tile.x) + Math.abs(targetTile.y - tile.y);
            
            /*
            * In each pass, we need to find out the costs of the new tiles to be checked
            * This involves the type of tile it is and the cost it took to get the tile just before this one.
            */
            /*
            //If the cost is not 0, then this tile has been explored previously. 
            if (tile.pathFindingCost != 0)
            {
                //If the old path to this tile is longer than the path from this new tile, then we switch the path to follow this new path.
                //If not, then we do nothing to it.
                if((tile.pathFindingCost - getTileIDCosts(tile))  > sourceTile.pathFindingCost)
                {
                    tile.pathFindingCost = sourceTile.pathFindingCost;
                    tile.previousTile = sourceTile;
                    
                    tile.pathFindingCost += getTileIDCosts(tile);

                }
                tileList.remove(this);
            }
            
            //Add cost of getting to the tile previous this one.
            else
            {
                tile.pathFindingCost += sourceTile.pathFindingCost;
                tile.previousTile = sourceTile;
                
                tile.pathFindingCost += getTileIDCosts(tile);

            }
            
        }
    }
    
    private int getTileIDCosts(DungeonTile tile)
    {
        //Ugly adding costs due to tile movement.
        //Standard tile
        if (tile.tileID == 1)
        {
            return 1;
        }
         
        //Trap tile
        else if(tile.tileID == 3)
        {
            return 3;
        }
            
        //Stair tile
        else if(tile.tileID == 2)
        {
            return 1;
        }
        
        //Should not run.
        else       
        {
            return 9999;
        }
    }*/
}