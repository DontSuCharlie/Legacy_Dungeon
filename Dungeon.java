/*
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
	//Constructor
	public DungeonRunner(int theme, int skillID, int difficulty)//Takes in the following parameters from NodeWorld.java
	{
		this.theme = theme;
		this.skillID = skillID;
		this.difficulty = difficulty;//insert random factor that will adjust difficulty
		currentFloor = 1;
		numFloor = (int) (Math.random()*difficulty) + (int)(difficulty/10) + 1;
	}
//////////////////////////////////METHODS HERE///////////////////////////////////////
/*
List of methods:
1] What happens when I reach the stairs?
Answer: You will move to a new map
	a] What happens when I reach the last floor?
Answer: A unique map will be generated

2] How many monsters will be spawned and where will they be?
Answer: Random # of monsters and random positions

3] What tiles should be loaded?
Answer: Depends on the theme based on the node's data.
	How many tiles should be loaded?
Answer: Random #

4] Where will I be spawned?
Answer: Random position

5] Where IS the stairs?
Answer: Random position set as far from you as possible

Method 0: .checkStairs()//if true everything runs from here except Method 3
Method 1: .checkLastFloor()//first one to run
Method 2: .monsterGenerator()
Method 3: .checkSpace() //WILL ALWAYS RUN EVERY TIME THERE IS MOVEMENT
Method 3: .loadFloor() //.loadFloor() should be the second one that gets run
Method 4: .spawnPlayer()
Method 5: .spawnStairs()
Method 6: .loadLastFloor()
Method 7: .checkAtBorder()//makes sure character doesn't run off map
*/
	//Method 0: .checkStairs() checks whether or not the player reached the stairs. Note that on LegacyDungeon.java, it will run this method first, and if it returns true, it will run every other method.
	public static boolean checkStairs(boolean reachStairs)
	{
		if(reachStairs)
		{
			currentFloor++;
			return true;
		}
		return false;
	}
	//Method 1: .checkLastFloor() checks whether or not the player will is at the last floor
	public static boolean checkLastFloor(int currentFloor)
	{
		currentFloor = this.currentFloor;
		if(currentFloor == numFloor)
		{
			return true;
		}
		return false;
	}
	//Method 2: .monsterGenerator() generates monsters and gives them a position
	public static void monsterGenerator()
	{
		numMonsters = (int)(Math.random());
		Monster[] monsters = new Monster[numMonsters];
		for(int i = numMonsters; i > 0; i--)
		{
			monsters[i] = new Monster();//will insert Monster parameter in here later
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
		//check if last floor
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
	}	
}