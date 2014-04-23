import java.util.ArrayList;

/*
Progress Bar: [||        ]
DungeonRunner.java is the file that generates the dungeon based on the information included in NodeWorld.java
DungeonRunner.java will tell LegacyDungeon.java (the main runner) what to display on the screen, so yeah.
*/
//RANDOM NOTES
//difficulty should also increase with skill level
//skills level can increase by # of monsters
public class DungeonRunner
{
   //Fields
   int numMonsters; //# of monsters
   int currentFloor; //keeps track of floor number
   int numFloor; //how many floors are there?
   int theme;//theme of the dungeon
   int skillID;//skill reward at bottom of dungeon
   int difficulty;//difficulty of dungeon
    static int xLengthInput;
    static int yLengthInput;
    static int numTiles;
    public static ArrayList<DungeonTile> tileList = new ArrayList<DungeonTile>();
    public static ArrayList<DungeonTile> connectorList = new ArrayList<DungeonTile>();
   //Constructor
   public DungeonRunner(int theme, int skillID, int difficulty)//Takes in the following parameters from NodeWorld.java
   {
      this.theme = theme;
      this.skillID = skillID;
      this.difficulty = difficulty;//insert random factor that will adjust difficulty
      currentFloor = 1;
      numFloor = (int) (Math.random()*difficulty) + (int)(difficulty/10) + 1;
        //Remove this stuff later. Just for testing.
        xLengthInput = 50;
        yLengthInput = 50;
        numTiles = 50;
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
        DungeonRunner dungeon = new DungeonRunner(1,1,1);
        
        dungeon.assignTilePos(xLengthInput, yLengthInput, numTiles);
        //generateItems();
    
    
    }



    //Charlie, please make this less ugly. This generates the floor tiles of the dungeon. REMOVE STATIC WHEN WORKING.
    public void assignTilePos(int xLength, int yLength, int numTiles)
    {
        //The first seed tile. Perhaps have a random seed tile?
        tileList.add(0, new DungeonTile((int)xLength/2, (int)yLength/2, 0));
        connectorList.add(0, new DungeonTile((int)xLength/2, (int)yLength/2, 0));
        //Starting at 1 because seed is 0.
        for (int i = 1; i < numTiles; i++)
        {
            //Methods needed: get adjacent tile, check if good tile, add to tileList and connectorList, remove tiles from connector list with more than connectionCap connections
            boolean boolGoodTile = true;
            int connectionCap = 2;
            int pickedTileNumber = 0;
            DungeonTile possibleTile = new DungeonTile(0,1,0);
            
            while (boolGoodTile)
            {
                pickedTileNumber = pickConnectorTile();
                //Gets the index of a random connector from the connectorList.
                //Gets a random tile adjacent to picked connector.
                possibleTile = getAdjacentTile(pickedTileNumber);
                //If picked tile is invalid, then restart with another connector.
                boolGoodTile = checkTileSpace(possibleTile, xLength, yLength);            
            }
            
            //Add a connection to the picked tile.
            connectorList.get(pickedTileNumber).numConnections += 1;
            tileList.add(possibleTile);
            connectorList.add(possibleTile);
            //Checks if the latest connector reaches the max number of connections and deletes it if it has.
            if (connectorList.get(pickedTileNumber).numConnections >= connectionCap)
            {
                connectorList.remove(pickedTileNumber);
            }
            System.out.println(possibleTile.x + " " + possibleTile.y);
        }    
    }
    
    //Returns the index of a selected connector.
    private int pickConnectorTile()
    {
        //Pick a random tile from the connector list. Please check if this will work. 
        int tileChooser = (int)(Math.random() * connectorList.size());
        return tileChooser;
    }

    //Pick a tile adjacent to given tile.    
    private DungeonTile getAdjacentTile(int tileChooser)
    {
        int x;
        int y;       
        if (Math.random() >= .5)
        {
            x = (connectorList.get(tileChooser).x + 1);
        }
        
        else
        {
            x = (connectorList.get(tileChooser).x - 1);
        }
        
        if (Math.random() >= .5)
        {
            y = (connectorList.get(tileChooser).y + 1);
        }
        
        else
        {
            y = (connectorList.get(tileChooser).y - 1);
        }
        
        
        DungeonTile newTile = new DungeonTile(x, y, 0);
        return newTile;
    }
    
    
    private boolean checkTileSpace(DungeonTile tile, int xLength, int yLength)
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
    /*
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