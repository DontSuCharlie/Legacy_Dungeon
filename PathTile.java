import java.util.ArrayList;

/**
 * This shell class is used for pathfinding.
 * 
 *
 */
        
public class PathTile implements Comparable<PathTile>{
    DungeonTile thisTile = null;
    int pathFindingCost = 0;
    int pathFindingHeuristic = 0;
    PathTile previousTile = null; //A pointer to the previous tile in a series. Used for pathFinding.
    
    public PathTile(DungeonTile tile)
    {
        thisTile = tile;
        // TODO Auto-generated constructor stub
    }
    
    public int compareTo(PathTile other)
    {
        //We want the lowest score, so if the other is bigger then then this one should be placed lower. (Negative return values place this higher and positive ones place other higher.
        int temp = this.pathFindingHeuristic + this.pathFindingCost - other.pathFindingHeuristic - other.pathFindingCost;
        
        if (temp != 0)
        {
            return (temp);
        }
        
        //In the case of a tie, we want the larger cost, because that'll be closer to the target.
        else
        {
            return (other.pathFindingCost - this.pathFindingCost);
        }
    }
    
    /**
     * Return the tiles adjacent to the given one. Used in PathFinder(). The isFine adds the additional property of not having characters on it. Need to disable it for if the the target is a character though.
     * Note: Returns diagonals. Undecided about what to do about them. Could add penalty for diags.
     * 
     */
    public ArrayList<PathTile> getAdjacentTilesAndSetValues(DungeonMain lDungeon, PathTile sourceTile, DungeonTile targetTile, boolean isFine)
    {
        ArrayList<PathTile> returnList = new ArrayList<PathTile>();
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x+1,  sourceTile.thisTile.y, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x + 1][sourceTile.thisTile.y]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x-1,  sourceTile.thisTile.y, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x - 1][sourceTile.thisTile.y]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x,  sourceTile.thisTile.y+1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x][sourceTile.thisTile.y + 1]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x,  sourceTile.thisTile.y-1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x][sourceTile.thisTile.y - 1]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x+1,  sourceTile.thisTile.y+1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x+1][sourceTile.thisTile.y+1]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x+1,  sourceTile.thisTile.y-1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x+1][sourceTile.thisTile.y - 1]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x-1,  sourceTile.thisTile.y+1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x-1][sourceTile.thisTile.y + 1]));
        }
        
        if (lDungeon.dungeon.tileChecker(sourceTile.thisTile.x-1,  sourceTile.thisTile.y-1, isFine))
        {
            returnList.add(new PathTile(lDungeon.dungeon.tileList[sourceTile.thisTile.x-1][sourceTile.thisTile.y - 1]));
        }
        
        //:< Inefficient check if we are next to the target. 
        if (Math.abs(sourceTile.thisTile.x - targetTile.x) <= 1 && Math.abs(sourceTile.thisTile.y - targetTile.y) <= 1)
        {
            returnList.add(new PathTile(targetTile));
        }
        
        setPathFindingValues(returnList, sourceTile, targetTile);
        
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
    private void setPathFindingValues(ArrayList<PathTile> tileList, PathTile sourceTile, DungeonTile targetTile)
    {
        for (PathTile tile : tileList)
        {
            //Using Chebychev distance to account for diagonal movement. If no diag, then better to use Manhattan distance.
            tile.pathFindingHeuristic = Math.max(Math.abs(targetTile.x - tile.thisTile.x), Math.abs(targetTile.y - tile.thisTile.y));
            
            //Manhattan distance, better if no diagonals are needed.
            //tile.pathFindingHeuristic = Math.abs(targetTile.x - tile.x) + Math.abs(targetTile.y - tile.y);
            
            /*
            * In each pass, we need to find out the costs of the new tiles to be checked
            * This involves the type of tile it is and the cost it took to get the tile just before this one.
            */
            
            //If the cost is not 0, then this tile has been explored previously. 
            if (tile.pathFindingCost != 0)
            {
                //If the old path to this tile is longer than the path from this new tile, then we switch the path to follow this new path.
                //If not, then we do nothing to it. What is already there is better.
                if((tile.pathFindingCost - getTileIDCosts(thisTile))  > sourceTile.pathFindingCost)
                {
                    tile.pathFindingCost = sourceTile.pathFindingCost;
                    tile.previousTile = sourceTile;
                    
                    tile.pathFindingCost += getTileIDCosts(thisTile);

                }
                tileList.remove(this);
            }
            
            //Add cost of getting to the tile previous this one.
            else
            {
                tile.pathFindingCost += sourceTile.pathFindingCost;
                tile.previousTile = sourceTile;
                
                tile.pathFindingCost += getTileIDCosts(thisTile);

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
    }
}
