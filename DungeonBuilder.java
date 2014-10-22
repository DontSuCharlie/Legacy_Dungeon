import java.util.ArrayList;

import javax.swing.JPanel;
/*
Progress Bar: [||        ]
DungeonBuilder.java is the file that generates the dungeon based on the information included in NodeWorld.java
DungeonBuilder.java will tell LegacyDungeon.java (the main runner) what to display on the screen, so yeah.
 */
//RANDOM NOTES
//difficulty should also increase with skill level
//skills level can increase by # of monsters
//Remove extends JPanel?
public class DungeonBuilder
{
    //Fields
    int numMonsters;//# of monsters
    int currentFloor;//keeps track of floor number
    int numFloor;//how many floors are there?
    int theme;//theme of the dungeon
    int skillID;//skill reward at bottom of dungeon
    int difficulty;//difficulty of dungeon

    static int xLength;
    static int yLength;
    static int numTiles;

    ////////////////////////////////////DATA///////////////////////////////////
    public int playerFloorDamage;//Keeps track of damage taken on this floor. Used in the difficultyHeuristic.
    public int difficultyHeuristic;//This collects data about what goes on in this floor and compares it to the optimalHeuristic in order to finetune difficulty. We want something like a wave with an upward trend in terms of difficulty. More difficulty may have more treasure being spawned.
    public int optimalHeuristic;//No damage, collecting all treasure, completion in some time(dunno how to measure a "good" time). Compared to difficultyHeuristic in order to finetune difficulty. Maybe having an easy difficulty will have less treasure spawned.

    ////////////////////////////////DUNGEON BUILDING STUFF/////////////////////
    //Stores all tiles.    
    public static DungeonTile[][] tileList;
    ArrayList<Spawner> spawningEnemyList;

    ////////////////////////////////THINGS IN DUNGEON//////////////////////////
    public Player playerCharacter;
    static public ArrayList<Enemy> enemyList;//Perhaps unnecessary memory usage? Just iterate through characterList.
    static public ArrayList<Character> characterList = new ArrayList<Character>();
    static int firstInactiveChar = 0;//Sets up invariant of all active characters to left of this and all inactive characters to right and including this one.

    ///////////////////////////////ITEM SPAWNING///////////////////////////////
    double goldChance = .4;
    ArrayList<ItemSpawner> spawningItemList;

    /**
     * This helper class stores all needed components for Character spawning.
     * @author Anish
     */
    class Spawner
	{
        Class<? extends Enemy> charClass;
        double spawnRate;
        
		public Spawner(Class<? extends Enemy> inputClass, double rate)
        {
            charClass = inputClass;
            spawnRate = rate;
        }
        /**
         * A simple method to change the spawnRate by the input amount.
         * @param rateChange
         */
        public void changeRate(double rateChange)
        {
            spawnRate += rateChange;
        }
    }
	
    class ItemSpawner
	{
        Class<? extends GameItem> itemClass;
        double spawnRate;

        public ItemSpawner(Class<? extends GameItem> inputClass, double rate)
        {
            itemClass = inputClass;
            spawnRate = rate;
        }
        public void changeRate(double rateChange)
        {
            spawnRate += rateChange;
        }
    }
    //Constructor for new dungeon
    public DungeonBuilder(int theme, int skillID, int difficulty, int xLengthInput, int yLengthInput) throws InstantiationException, IllegalAccessException//Takes in the following parameters from NodeWorld.java
    {
        // If this is a completely new dungeon, then start with basic values.
        this.theme = theme;//1 = default.
        this.skillID = skillID;//The skill found there 
        this.difficulty = difficulty;//insert random factor that will adjust difficulty
        currentFloor = 1;
        //Remove this stuff later. Just for testing.
        xLength = xLengthInput;
        yLength = yLengthInput;
        numTiles = 1000;
        //May be randomly chosen.
        playerCharacter = new Player(0);
        getSpawnLists(theme);

        tileList = new DungeonTile[xLength][yLength];
        enemyList = new ArrayList<Enemy>();
        getSpawnLists(theme);
        //Number of floors is based on the difficulty level
    }
    //This overloaded constructor runs when a new floor is spawned. Only issue is changing floor size may be tricky.
    public DungeonBuilder(DungeonBuilder dungeon) throws InstantiationException, IllegalAccessException
    {
        //These three should remain from prev floor.
        this.theme = dungeon.theme;//1 = default.
        this.skillID = dungeon.skillID;//The skill found there 
        this.difficulty = dungeon.difficulty;//insert random factor that will adjust difficulty

        currentFloor = ++dungeon.currentFloor;
        //Will put this in legacyDungeon. Fix next line later
        numFloor = dungeon.currentFloor;
        //Remove this stuff later. Just for testing.
        xLength = dungeon.xLength;
        yLength = dungeon.yLength;
        numTiles = 1000;
        //May be randomly chosen.
        playerCharacter = dungeon.playerCharacter;

        tileList = new DungeonTile[xLength][yLength];

        enemyList = new ArrayList<Enemy>();
        characterList = new ArrayList<Character>();
        spawningEnemyList = dungeon.spawningEnemyList;
        spawningItemList = dungeon.spawningItemList;
        this.build();
    }

    void build() throws InstantiationException, IllegalAccessException
    {
        this.assignTilePos(numTiles);
    }

    /*
     * Use this to test new stuff. This builds a 5x5 room to mess around in.
     */
    void buildTest() throws InstantiationException, IllegalAccessException
    {
        for (int i = 1;i <= 10;i++)
        {
            for (int j = 1;j <= 10;j++)
            {
				/**Charlie: Commented out instead of deleting because I'm not sure if you wanted to put something in the blank if's in the first place
                if (i == 5 && j == 5)
                {
                }
                else
                {
                    tileList[i][j] = new DungeonTile(i,j,1);
                }
				*/
				if(!(i == 5 && j == 5))
					tileList[i][j] = new DungeonTile(i, j, 1);
            }
        }
        spawnPlayer(1,1);
        //spawnIndividualCharacter(new CombatJam(), 4, 4);
        //spawnIndividualCharacter(new RandomJam(), 10, 10);
        //spawnIndividualCharacter(new RandomJam(), 9, 9);
        //spawnIndividualCharacter(new Ghost(), 8, 8);
        spawnIndividualItem(new HealthPot(), 2,2);
        //spawnIndividualCharacter(new RandomJam(), 1, 10);
        //spawnIndividualCharacter(new RandomJam(), 10, 1);
    }

    private void getSpawnLists(int theme)
    {
        if (theme == 1)
        {
            spawningEnemyList = new ArrayList<Spawner>();
            spawningEnemyList.add(new Spawner(RandomJam.class, .7));
            spawningEnemyList.add(new Spawner(CombatJam.class, .2));
            spawningEnemyList.add(new Spawner(Ghost.class, .1));
            spawningItemList = new ArrayList<ItemSpawner>();
            spawningItemList.add(new ItemSpawner(HealthPot.class, 1));
        }
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

    /**
    public static void main(String[] args) throws InstantiationException, IllegalAccessException
    {
        //Remove when done testing.
        DungeonBuilder dungeon = new DungeonBuilder(1,1,1,100,100,1);
        //generateItems();

    }*/

    /**
     * This generates the floor tiles of the dungeon.
     * Using theme and some randomness, we spawn several variations. USING:
     * A forest should feel like a forest and be more congested. USING:
     * A town may just load a pregenerated map we make. USING:
     * Grasslands should be pretty open. USING: .99- spawnBasic(), .01- spawnBlob()
     * And of course the blob is always interesting. 
     * A dungeon may be claustrophobic, and have long, 1 tile pathways. USING:
     * 
     * After spawning tiles, we spawn everything else. Player, then stairs, then enemies, then items.
     * 
     */
    public void assignTilePos(int numTiles) throws InstantiationException, IllegalAccessException
    {
        final double BLOB_CHANCE = .99;
        //Used for tile generation
        ArrayList<DungeonTile> connectorList = new ArrayList<DungeonTile>();
        //Used to pick a tile for enemy generation, player generation, item generation, etc.
        ArrayList<DungeonTile> checkList = new ArrayList<DungeonTile>();
        if (theme == 1)
        {
            double chooser = Math.random();
            if (chooser < BLOB_CHANCE)
            {
                setSeed(connectorList);
                spawnBasic(connectorList, checkList);
            }
            else 
            {
                setSeed(connectorList);
                spawnBlob(connectorList, checkList);
            }
        }
        //Spawn everything else.
        this.spawnPlayer(checkList);
        this.spawnStairs(checkList);
        this.spawnItems(spawningItemList, checkList);
        this.spawnEnemies(spawningEnemyList, checkList);
    }

    /**
     * Set the starting seed for floor building.
     * Picks a seed in a square from .25% of x and y lengths to .75 of x and y lengths;
     */
    private void setSeed(ArrayList<DungeonTile> connectorList)
    {
        //Spawn seed tile. Variation from .25 to .75 of x and y lengths.
        //Maybe have multiple seeds?
        double xSeed = .5*Math.random() + .25;
        double ySeed = .5*Math.random() + .25;

        tileList[(int)(xSeed*xLength)][(int)(ySeed*yLength)] = new DungeonTile((int)(xSeed*xLength), (int)(ySeed*yLength), 1);
        connectorList.add(new DungeonTile((int)(xSeed*xLength), (int)(ySeed*yLength), 1));
    }
    /**
     * The first decent algorithm.
     * 
     * Essentially, how this works is that each tile can spawn one tile in an unoccupied space. If it tries and fails 4 times, a previously spawned tile is randomly chosen to continue spawning. 
     * 
     * RESULTS:
     * Some interesting spaces, semi-large open areas clustered around seed, branching paths that often lead to dead-ends elsewhere.
     * Could use more work.
     * 
     * POTENTIAL USES: Nature-ish levels.
     */
    private void spawnBasic(ArrayList<DungeonTile> connectorList, ArrayList<DungeonTile> checkList)
    {
        for (int i = 1;i < numTiles;i++)
        {
            //Methods needed: get adjacent tile, check if good tile, add to tileList and connectorList, remove tiles from connector list with more than connectionCap connections
            boolean boolBadTile = true;
            int connectionCap = 1;//change to 2 or 3
            int actualX = 0;
            int actualY = 0;
            int connectorNumber = 0;
            connectorNumber = (int) (connectorList.size() * Math.random());
            DungeonTile possibleTile = null;
            int failCount = 0;
            while (boolBadTile)
            {   
                //Gets the index of a random connector from the connectorList.
                //Gets a random tile adjacent to picked connector.
                //Returns a valid adjacent tile. Note, picks one tile adjacent to this and starts entire loop if bad. Theoretically, this promotes branches and unconnected parts to grow (ex. 3 open tiles instead of fewer).
                actualY = connectorList.get(connectorNumber).y;
                actualX = connectorList.get(connectorNumber).x;
                possibleTile = getAdjacentTile(actualX, actualY);
                if (possibleTile.x > 0 && possibleTile.x < xLength && possibleTile.y > 0 && possibleTile.y < yLength && tileList[possibleTile.x][possibleTile.y] instanceof DungeonTile != true)
                {
                    boolBadTile = false;
                    failCount = 0;

                }
                else 
                {
                    boolBadTile = true;
                    connectorNumber = (int) (connectorList.size() * Math.random());
                    failCount++;
                    //If the tile is trapped, then we start again from the seed tile.
                    if (failCount == 4)
                    {
                        connectorList.remove(connectorNumber);
                        connectorList.add(checkList.get((int) (Math.random()*checkList.size())));
                        System.out.println("connectorList modified");
                        failCount = 0;
                    }  
                }
                //If picked tile is invalid, then restart with another connector.          
            }         
            //Add a connection to the picked tile.
            connectorList.get(connectorNumber).numConnections += 1;
            tileList[possibleTile.x][possibleTile.y] = possibleTile;
            connectorList.add(possibleTile);
            //Testing this for generation.
            checkList.add(possibleTile);
            //Checks if the latest connector reaches the max number of connections and deletes it if it has.
            if (connectorList.get(connectorNumber).numConnections >= connectionCap)
                connectorList.remove(connectorNumber);
            //FOR TESTING
            //System.out.println(possibleTile.x + " " + possibleTile.y);
        }
    }

    private void spawnBlob(ArrayList<DungeonTile> connectorList, ArrayList<DungeonTile> checkList)
    {
        //Starting at 1 because seed is 0.
        // Can use for multiple generation types. This one makes a large blob.
        for (int i = 1;i < numTiles;i++)
        {
            //Methods needed: get adjacent tile, check if good tile, add to tileList and connectorList, remove tiles from connector list with more than connectionCap connections
            boolean boolBadTile = true;
            int connectionCap = 2;
            int actualX = 0;
            int actualY = 0;
            int connectorNumber = 0;
            connectorNumber = (int) (connectorList.size() * Math.random());
            DungeonTile possibleTile = null;

            while (boolBadTile)
            {   
                //Gets the index of a random connector from the connectorList.
                //Gets a random tile adjacent to picked connector.
                //Returns a valid adjacent tile. Note, picks one tile adjacent to this and starts entire loop if bad. Theoretically, this promotes branches and unconnected parts to grow (ex. 3 open tiles instead of fewer).
                actualY = connectorList.get(connectorNumber).y;
                actualX = connectorList.get(connectorNumber).x;
                possibleTile = getAdjacentTile(actualX, actualY);
                //Need to protect against OOB exception. :/
                if (tileList[possibleTile.x][possibleTile.y] instanceof DungeonTile)
                {
                    boolBadTile = true;
                    connectorNumber = (int) (connectorList.size() * Math.random());
                }
                else boolBadTile = false;
                //If picked tile is invalid, then restart with another connector.          
            }         
            //Add a connection to the picked tile.
            connectorList.get(connectorNumber).numConnections += 1;
            tileList[possibleTile.x][possibleTile.y] = possibleTile;
            connectorList.add(possibleTile);
            //Testing this for generation.
            checkList.add(possibleTile);
            //Checks if the latest connector reaches the max number of connections and deletes it if it has.
            if (connectorList.get(connectorNumber).numConnections >= connectionCap)
                connectorList.remove(connectorNumber);
            //FOR TESTING
            System.out.println(possibleTile.x + " " + possibleTile.y);
        }
        //Must fill remaining area with walls. Same unneeded note as above.
        //tileList.addAll(unusedTileList);
        System.out.println("Done :>");

    }
    private DungeonTile getAdjacentTile(int xInput, int yInput)
    {         
        double randomNumber = Math.random();
        if (randomNumber <= .25)
            xInput++;
        else if (randomNumber <= .5)
            xInput--;
        else if (randomNumber <= .75)
            yInput++;
        else if (randomNumber < 1)
            yInput--;
        DungeonTile returnTile = new DungeonTile(xInput, yInput, 1);
        return returnTile;
    }

    //Player and enemy locations will be defined by two ways- a dungeonTile stored in each character and a character stored in the dungeonTile on the tileList. Maybe not efficient enough.
    private void spawnPlayer(ArrayList<DungeonTile> checkList)
    {
        boolean isBadTile = true;
        int tileNumber = 0;
        //Loops until a good tile is found
        while (isBadTile)
        {
            tileNumber = (int) (Math.random() * checkList.size());
            if (!(checkList.get(tileNumber).character instanceof Character))
                isBadTile = false;//Breaks loop on valid tile.
            else 
                isBadTile = true;
        }
        //Set a tile for the player.
        playerCharacter.currentTile = checkList.get(tileNumber);
        //Add the character to these tiles. This works for all of the lists.
        tileList[checkList.get(tileNumber).x][checkList.get(tileNumber).y].character = playerCharacter;
        //Add this to the list of characters that will be cycled through for the turns.
        characterList.add(playerCharacter);
        playerCharacter.isActive = true;
        ++firstInactiveChar;
        //System.out.println("I am at: " + playerCharacter.currentTile.x + ", " + playerCharacter.currentTile.y);
    }
    //Player and enemy locations will be defined by two ways- a dungeonTile for each character and a different characterID on the tileList. Maybe not efficient enough.
    //OVERLOADED: For spawning in a given location. Assumed correct.
    private void spawnPlayer(int x, int y)
    {
        //Set a tile for the player.
        playerCharacter.currentTile = tileList[x][y];
        //Add the character to these tiles. This works for all of the lists.
        tileList[x][y].character = playerCharacter;
        //Add this to the list of characters that will be cycled through for the turns.
        characterList.add(playerCharacter);
        playerCharacter.isActive = true;
        ++firstInactiveChar;
        //System.out.println("I am at: " + playerCharacter.currentTile.x + ", " + playerCharacter.currentTile.y);
    }

    private void spawnStairs(ArrayList<DungeonTile> checkList)
    {
        boolean isBadTile = true;
        int tileNumber = 0;

        while (isBadTile)
        {
            tileNumber = (int) (Math.random() * checkList.size());
            if (!(checkList.get(tileNumber).character instanceof Character))
                isBadTile = false;//Breaks loop on valid tile.
            else 
                isBadTile = true;
        }
        tileList[checkList.get(tileNumber).x][checkList.get(tileNumber).y].tileID = 2;
        checkList.get(tileNumber).tileID = 2;
        //System.out.println("Stairs are at: " + checkList.get(tileNumber));
    }

    /**
     * This will work a very similar way to enemy spawning.
     * Check every tile for spawning. If it passes, then check which item to spawn.
     * @param checkList
     */
    private void spawnItems(ArrayList<ItemSpawner> spawningItemList, ArrayList<DungeonTile> checkList)
    {
        for (int i = 0;i < numTiles-1;i++)
        {
            //If the random double is less than itemChance, randomly pick the item that will be there.
            final double ITEM_CHANCE = .05;
            final double GOLD_CHANCE = .40;//Gold is handled separately because it doesn't make sense to keep it in the inventory. Enemies won't pick it up.
            if (Math.random() < ITEM_CHANCE)
            {
                double chooser = Math.random();
                int prevSpawnRate = 0;
                if (chooser < GOLD_CHANCE)
                {
                    System.out.println("Gold");
                    tileList[checkList.get(i).x][checkList.get(i).y].itemID = 1;
                    int amount = (int) (this.currentFloor * (1 + Math.random()));
                    tileList[checkList.get(i).x][checkList.get(i).y].goldAmount = amount;
                    optimalHeuristic++;
                    System.out.println(amount);
                }
                else
                {
                    System.out.println("Item");
                    if (!(checkList.get(i).items instanceof ArrayList))
                    {
                        //Single item.
                        checkList.get(i).items = new ArrayList<GameItem>();
                        checkList.get(i).itemID = 2;
                    }
                    else
                        checkList.get(i).itemID = 3;//Multiple items.
                    //Pick a random item to spawn. Only gets here after Gold failed to spawn.
                    for(int j = 0;j < spawningItemList.size();j++)
                    {
                        //Theoretically should not depends on order in itemTypes.
                        //Using example from enemy, a RandomJam has a spawn rate of .4 while a combatJam has .05.
                        //If my chooser is .43, then it would first check the RandomJam's spawn rate, and fail. Then it would loop back. Because the combatJam spawns if the RandomJam does not spawn, I add the RandomJam's spawn rate and get .45 which would pass.
                        if (chooser < spawningItemList.get(j).spawnRate + prevSpawnRate)
                        {
                            //This new instance will be used for everything.
                            GameItem something = null;
                            try
                            {
                                something = spawningItemList.get(j).itemClass.newInstance();
                            }
                            catch (InstantiationException | IllegalAccessException e)
                            {
                                e.printStackTrace();
                                System.out.println("Something bad happened");
                            }
                            tileList[checkList.get(i).x][checkList.get(i).y].items.add(something);
                            tileList[checkList.get(i).x][checkList.get(i).y].itemID = 2;
                            //optimalHeuristic += something.rarity;
                            System.out.println("I AM a teaPOT");
                        }
                        else 
                            prevSpawnRate += spawningItemList.get(j).spawnRate;
                    }
                }
                
            }
        }
    }
    //When generating dungeons, we choose which sets of enemies spawn with these ArrayLists.
    private void spawnEnemies(ArrayList<Spawner> spawningEnemyList, ArrayList<DungeonTile> checkList) throws InstantiationException, IllegalAccessException
    {
        for (int i = 0;i < numTiles-1;i++)
        {
            if (!(checkList.get(i).character instanceof Character))
            {
                //If the random double is less than itemChance, randomly pick the enemy that will be there.
                final double ENEMY_CHANCE = .05;
                if (Math.random() < ENEMY_CHANCE)
                {
                    double chooser = Math.random();
                    int prevSpawnRate = 0;
                    //Pick a random enemy to spawn
                    for(int j = 0;j < spawningEnemyList.size();j++)
                    {
                        //Theoretically should not depends on order in enemyTypes.
                        //Example, a RandomJam has a spawn rate of .4 while a combatJam has .05.
                        //If my chooser is .43, then it would first check the RandomJam's spawn rate, and fail. Then it would loop back. Because the combatJam spawns if the RandomJam does not spawn, I add the RandomJam's spawn rate and get .45 which would pass.
                        if (chooser < spawningEnemyList.get(j).spawnRate + prevSpawnRate)
                        {
                            //checkList.get(i).character = enemyTypes.get(j).characterID;
                            //tileList[checkList.get(i).x][checkList.get(i).y].character = enemyTypes.get(j).characterID;
                            //This new instance will be used for everything.
                            Enemy something = spawningEnemyList.get(j).charClass.newInstance();
                            something.currentTile = tileList[checkList.get(i).x][checkList.get(i).y];
                            tileList[checkList.get(i).x][checkList.get(i).y].character = something;
                            enemyList.add(something);
                            checkList.get(i).character = something;
                            //System.out.println("I AM JAM");
                        }
                        else 
                            prevSpawnRate += spawningEnemyList.get(j).spawnRate;
                    }
                }
            }
        }  
        //Add these enemies to the list to be looped through.
        characterList.addAll(enemyList);
        //After that activate those next to the player.
        playerCharacter.activateArea(this, playerCharacter.xVision, playerCharacter.yVision);
        //System.out.println("Activated");
        }
    /**
     * When one character in specific needs to be spawned, this will do the job.
     * Assumes tile is good and unoccupied.
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * 
     * 
     */
    private void spawnIndividualCharacter(Character inputChar, int x, int y) throws InstantiationException, IllegalAccessException 
    {
        Class<? extends Character> temp = inputChar.getClass();
        tileList[x][y].character = temp.newInstance();
        tileList[x][y].character.currentTile = tileList[x][x];
        characterList.add(tileList[x][x].character);
        tileList[x][y].character.activate(this);

        if (!(tileList[x][y].character.isFriendly))
            enemyList.add((Enemy) tileList[x][x].character);
    }

    private void spawnIndividualItem(GameItem inputItem, int x, int y) throws InstantiationException, IllegalAccessException 
    {
        Class<? extends GameItem> temp = inputItem.getClass();
        if (!(tileList[x][y].items instanceof ArrayList))
        {
            //Single item.
            tileList[x][y].items = new ArrayList<GameItem>();
            tileList[x][y].itemID = 2;
        }
        else 
            tileList[x][y].itemID = 3;
        tileList[x][y].items.add(temp.newInstance());
    }
    /**
     * A method that returns true if an input DungeonTile is in bounds and is a valid tile (i.e. not a wall)
     * Else returns false.
     * The boolean checkOpenTile, if true adds an additional check for whether it is empty and thus moveable.
     * This mostly just makes code look cleaner elsewhere. 
     */
    public boolean tileChecker(int x, int y, boolean checkOpenTile)
    {
        if (x > 0 && x < xLength && y > 0 && y < yLength && tileList[x][y] instanceof DungeonTile)
        {
            if (checkOpenTile)
            {
                if(!(tileList[x][y].character instanceof Character))
                    return true;
                else
                    return false;
            }
            return true;
        }
        else 
            return false;
    }
    /* FIX WHEN ITEM SYSTEM IS SOLIDIFIED
    private void generateItems()
    {
        for (int i = 0;i < tileList.size();i++)
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
      for(int i = numMonsters;i > 0;i--)
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