import java.util.ArrayList;

import javax.swing.JPanel;
/*
Progress Bar: [||        ]
DungeonRunner.java is the file that generates the dungeon based on the information included in NodeWorld.java
DungeonRunner.java will tell LegacyDungeon.java (the main runner) what to display on the screen, so yeah.
*/
//RANDOM NOTES
//difficulty should also increase with skill level
//skills level can increase by # of monsters
//Remove extends JPanel?
public class DungeonRunner extends JPanel
{
    //Fields
    int numMonsters; //# of monsters
    int currentFloor; //keeps track of floor number
    int numFloor; //how many floors are there?
    int theme;//theme of the dungeon
    int skillID;//skill reward at bottom of dungeon
    int difficulty;//difficulty of dungeon
    
    static int xLength;
    static int yLength;
    //These four are designed to make picking a random tile more efficient by constraining the random area to a rectangle defined by these.
    static int minX;
    static int maxX;
    static int minY;
    static int maxY;
    //End
    static int numTiles;
    public static Player playerCharacter;
    //Stores all tiles.    
    public static DungeonTile[][] tileList;
    //Used for tile generation
    private ArrayList<DungeonTile> connectorList;
    //Used to pick a tile for enemy generation, player generation, item generation, etc.
    private ArrayList<DungeonTile> checkList;
    
   //Constructor
   public DungeonRunner(int theme, int skillID, int difficulty, int xLengthInput, int yLengthInput)//Takes in the following parameters from NodeWorld.java
   {
       this.theme = theme;
       this.skillID = skillID;
       this.difficulty = difficulty;//insert random factor that will adjust difficulty
       currentFloor = 1;
       numFloor = (int) (Math.random()*difficulty) + (int)(difficulty/10) + 1;
       //Remove this stuff later. Just for testing.
       xLength = xLengthInput;
       yLength = yLengthInput;
       numTiles = 1000;
       playerCharacter = new Player();
       tileList = new DungeonTile[xLength][yLength];
       //This list is used for generating tiles
       connectorList = new ArrayList<DungeonTile>();
       checkList = new ArrayList<DungeonTile>();
       this.assignTilePos(numTiles);
       this.spawnPlayer();       
      
       //Number of floors is based on the difficulty level
   }
//////////////////////////////////METHODS HERE///////////////////////////////////////
/* List of Methods:
Method 0: .checkSpace() runs every time the character moves. It makes sure the player doesn't sit on the same tile as a monster.
Method 1: .checkStairs() runs every time the character interacts with an Item. It starts off a chain of functions that generates a new map.
Method 2: .checkLastFloor() runs to make sure it's not the last floor
Method 3: .loadFloor() generates the walkable map itself.
Method 4: .monsterGenerator() uses data from NodeWorld.java to generate monsters that fill the new map.
Method 5: .spawnPlayer() spawns the player at a random coordinate point
Method 6: .spawnStairs() spawns the stairs. It will be located as far from the player as possible
Method 7: .loadLastFloor() creates the last floor
Method 8: .checkAtBorder() runs every time the character moves. It makes sure the character doesn't run off map.
*/

    public static void main(String[] args)
    {
        //Remove when done testing.
        DungeonRunner dungeon = new DungeonRunner(1,1,1,100,100);
        //generateItems();
    
    
    }



    //This generates the floor tiles of the dungeon.
    public void assignTilePos(int numTiles)
    {
        //This generates a list of all the tiles. If the tile is not picked, it will later be added to the tileList as a wall.
        //MAYBE UNNEEDED? POSSIBLY JUST CHECK FOR NULL REQUIRED. Maybe can be used when edge walls need to be different from normal walls
        
        /*
        for (int i = 0; i < xLength; i++)
        {
            for (int j = 0; j < yLength; j++)
            {
                unusedTileList[i][j] = new DungeonTile(i,j,0);
            }
        
        }
        */
        
        //Maybe have multiple seeds? Need to connect them.
        //The first seed tile. Currently uses the middle tile. Perhaps have a random seed tile?
        tileList[xLength/2][yLength/2] = new DungeonTile(xLength/2, yLength/2, 1);
        connectorList.add(new DungeonTile(xLength/2, yLength/2, 1));
        //Starting at 1 because seed is 0.
        for (int i = 1; i < numTiles; i++)
        {
            //Methods needed: get adjacent tile, check if good tile, add to tileList and connectorList, remove tiles from connector list with more than connectionCap connections
            boolean boolBadTile = true;
            int connectionCap = 2;
            int actualX = 0;
            int actualY = 0;
            int connectorNumber = 0;
            connectorNumber = (int) (connectorList.size() * Math.random());
            
            while (boolBadTile)
            {   
                //Gets the index of a random connector from the connectorList.
                //Gets a random tile adjacent to picked connector.
                    //Returns a valid adjacent tile. Note, picks one tile adjacent to this and starts entire loop if bad. Theoretically, this promotes branches and unconnected parts to grow (ex. 3 open tiles instead of fewer).
                    //boolBadTile = checkValidAdjacentTile(randX, randY);
                    
                    //I don't like doing this this way but oh well. 50% chance of x change, 50% chance of y change.
				
                actualY = connectorList.get(connectorNumber).y;
                actualX = checkValidAdjacentTileX(connectorList.get(connectorNumber).x);
                if (connectorList.get(connectorNumber).x == actualX)
                {
                    actualY = checkValidAdjacentTileY(connectorList.get(connectorNumber).y);
                      
                }
                //Need to protect against OOB exception. :/
                if (tileList[actualX][actualY] instanceof DungeonTile)
                {
                    boolBadTile = true;  
                    connectorNumber = (int) (connectorList.size() * Math.random());
                }
                    
                else boolBadTile = false;
                    
                    //If picked tile is invalid, then restart with another connector.          
            }         
            //Add a connection to the picked tile.
            connectorList.get(connectorNumber).numConnections += 1;
            tileList[actualX][actualY] = new DungeonTile(actualX,actualY,1);
            connectorList.add(new DungeonTile(actualX,actualY,1));
            //Testing this for generation.
            checkList.add(new DungeonTile(actualX,actualY,1));                                                
            //If a tile is used then remove it from the unused list. Not very efficient though.
            
            /* Somewhat unneeded, just use instanceof instead.
             * 
            for (int k = 0; k < unusedTileList.size(); k++)
            {
                for (int j = 0; j < tileList.size(); j++)
                {
                    if ((tileList.get(j).x == unusedTileList.get(k).x) && (tileList.get(j).y == unusedTileList.get(k).y))
                    {
                        unusedTileList.remove(k);                    
                    }
                }            
            }
            */
            //Checks if the latest connector reaches the max number of connections and deletes it if it has.
            if (connectorList.get(connectorNumber).numConnections >= connectionCap)
            {
                connectorList.remove(connectorNumber);
            }
            
            //FOR TESTING
            System.out.println(actualX + " " + actualY);
        }    
    
        //Must fill remaining area with walls. Same unneeded note as above.
        //tileList.addAll(unusedTileList);
        
        System.out.println("Done :>");
    }
  
    private boolean pickTileCoordinate(DungeonTile[][] choiceList)
    {
        //Pick a random tile from the list. 
        int randX = pickTileCoordinateX();
        int randY = pickTileCoordinateY();
        if (choiceList[randX][randY] instanceof DungeonTile)
        {
            return true;
        }
        
        else return false;
    }
    
//Perhaps inefficient?
    private int pickTileCoordinateX()
    {
        //Pick a random int from the range (minX - maxX)
        int x = minX + (int) ((maxX-minX + 1)* Math.random());
        return x;
    }
    
    private int pickTileCoordinateY()
    {
        //Pick a random int from the range (minY - maxY)
        int y = minY + (int) ((maxY-minY + 1)* Math.random());
        return y;
    }
    
/*    private boolean checkValidAdjacentTile(int xInput, int yInput)
    {
        int x = xInput;
        int y = yInput;       
        
        while (true)
        {
            double randomNumber = Math.random();
            if (randomNumber <= .25)
            {
                x++;
            }
        
            else if (randomNumber <= .5)
            {
                x--;
            }
        
            else if (randomNumber <= .75)
            {
                y++;
            }
        
            else if (randomNumber < 1)
            {
                y--;
            }
        
            if (tileList[x][y] instanceof DungeonTile)
            {
                //Will restart loop if this tile is occupied
                return true;
            }
                //Breaks loop.
                return false;
            
        }
    }*/

    private int returnAdjacentTile (int x, int y)
    {
		double randomNumber = Math.random()
		if(randomNumber <= 0.25)
			x++;
		else if(randomNumber <= 0.5)
			x--;
		else if(randomNumber <= 0.75)
			y++;
		else if(randomNumber <= 1)
			y--;
	}

/*
    //Pick a tile adjacent to given tile. Either x or y is modified by one.
    private DungeonTile getAdjacentTile(int tileChooser)
    {
        int x = 0;
        int y = 0;       
        
        double randomNumber = Math.random();
        if (randomNumber <= .25)
        {
            x = (connectorList.get(tileChooser).x + 1);
            y = (connectorList.get(tileChooser).y);
        }
        
        else if (randomNumber <= .5)
        {
            x = (connectorList.get(tileChooser).x - 1);
            y = (connectorList.get(tileChooser).y);
        }
        
        else if (randomNumber <= .75)
        {
            x = (connectorList.get(tileChooser).x);
            y = (connectorList.get(tileChooser).y + 1);
        }
        
        else if (randomNumber < 1)
        {
            x = (connectorList.get(tileChooser).x);
            y = (connectorList.get(tileChooser).y - 1);
        }
        
        DungeonTile newTile = new DungeonTile(x, y, 1);
        return newTile;
    }
  */  
    /*
    private boolean checkTileSpace(DungeonTile tile)
    {
    //If on same space as another tile or in contact with the edges, then return false.
    //Note that if the only available spaces are out of bounds, this program will crash.
        for (int i = 0; i < tileList.size(); i++)
            {
            
                int DiffX = Math.abs(tile.x - tileList.get(i).x);
                int DiffY = Math.abs(tile.y - tileList.get(i).y);
                
                if (DiffX == 0 && DiffY == 0)
                {
                    return true;                
                }                            
            }            
        
        if (tile.x == 0 || tile.x == xLength || tile.y == 0 || tile.y == yLength)
        {
            return true;
        }
        else
        {
            return false;  
        }
    }
    */
    //Player and enemy locations will be defined by two ways- a dungeonTile for each character and a different characterID on the tileList. Maybe not efficient enough.
    private void spawnPlayer()
    {
        boolean isBadTile = true;
        int tileNumber = 0;
        
        while (isBadTile)
        {
            tileNumber = (int) (Math.random() * checkList.size());
            if (checkList.get(tileNumber).characterID == 0)
            {
                //Breaks loop on valid tile.
                isBadTile = false;
            }

            else 
                isBadTile = true;    
        
        }
        
        tileList[checkList.get(tileNumber).x][checkList.get(tileNumber).y].characterID = 1;
        Player.currentTile = checkList.get(tileNumber);
        System.out.println("I am at: " + Player.currentTile.x + ", " + Player.currentTile.y);
        
    }
    
    
    /* FIX WHEN ITEM SYSTEM IS SOLIDIFIED
    private void generateItems()
    {
        for (int i = 0; i < tileList.size(); i++)
        {
            //If the random double is less than itemChance, randomly pick the item that will be there.
            double itemChance = .15;
            if (Math.random() < .15)
            {
                //Note: these are placeholders for the actual way we do the item lists. (Maybe arraylists?)
                int lowCommon;
                int highCommon;
                int lowUncommon;
                int highUncommon;
                int lowRare;
                int highRare;
                int lowSuper;
                int highSuper;
                
                double chooser = Math.random();
                double goldChance = .4;
                double commonChance = .3;             
                double uncommonChance = .19;
                double rareChance = .1;
                double superChance = .01;
                
                if (chooser < goldChance)
                {
                    tileList.get(i).itemID = 1;
                    //Need to decide how to do this.
                    //tileList.get(i).goldAmount =                 
                }
                
                else if (chooser < commonChance)
                {
                    tileList.get(i).itemID = (int)((Math.random() * (highCommon - lowCommon)) + lowCommon);                                
                }
            
                else if (chooser < uncommonChance)
                {
                    tileList.get(i).itemID = (int)((Math.random() * (highUncommon - lowUncommon)) + lowUncommon);
                }
            
                else if (chooser < rareChance)
                {
                    tileList.get(i).itemID = (int)((Math.random() * (highRare - lowRare)) + lowRare);
                }
            
                else if (chooser < superChance)
                {
                    tileList.get(i).itemID = (int)((Math.random() * (highSuper - lowSuper)) + lowSuper);
                }
            
            }
        }
    }
    */
    
    
    
/*
   //Method 1: .checkStairs() checks whether or not the player reached the stairs. Note that on LegacyDungeon.java, it will run this method first, and if it returns true, it will run every other method.
   public static boolean checkStairs(boolean reachStairs)
   {
      if(reachStairs)
      {
         currentFloor++;
         return true;
      }
      return false;
   }
   //Method 2: .checkLastFloor() checks whether or not the player will is at the last floor
   public static boolean checkLastFloor(int currentFloor)
   {
      currentFloor = this.currentFloor;
      if(currentFloor == numFloor)
      {
         return true;
      }
      return false;
   }
   //Method 4: .monsterGenerator() generates monsters and gives them a position
   public static void monsterGenerator()
   {
      numMonsters = (int)(Math.random()*difficulty);//generates a random number of monsters based on difficulty
      Monster[] monsters = new Monster[numMonsters];//creates an array of monsters, and initializes each monster.
      for(int i = numMonsters; i > 0; i--)
      {
         monsters[i] = new Monster();//will insert Monster parameter in here later
         monsters[i].xPos =;
         monsters[i].yPos =;
      }
   }
   //Method 3a: .checkSpace() checks if Item and Character are in the same box
   public static void onSameTile(Object firstObject, Object secondObject)
   {
      if(firstObject instanceof Character && secondObject instanceof Item)
      {
         if(firstObject.singularity || secondObject.singularity)
         {
            return false;
         }
         return true;
      }
      return false;
   }
   //Method 3b: .loadFloor() chooses the picture tile to load and draws out the actual map
   public static void loadFloor()
   {
      //check if last floor, if not, continue
      if(currentFloor == numFloor)
      {
         return;
      }
      //determine 
   }
   //Method 4: .spawnPlayer() spawns the player at a random position
   public static void spawnPlayer()
   {
      //load sprite
      //generate random coordinates
      //check if random coordinates are valid (within bounds of loadFloor)
      //if not regenerate
      //if so generate
   }
   //Method 5: .spawnStairs() spawns stairs at a random position significantly far away from the player
   public static void spawnStairs()
   {
      //check if last floor. If last floor, don't do anything
      //load sprite
      //generate random coordinates
      //check if within bounds and not near player
      //generate
   }
   //Method 6: .loadLastFloor() loads the last floor pre-determined map and an Item
   public static void loadLastFloor()
   {
      //check if last floor. IF last floor
      //load sprites for tile
      //load 
   }  */
}