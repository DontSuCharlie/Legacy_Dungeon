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
	//Constructor
	public DungeonRunner(int theme, int skillID, int difficulty)//Takes in the following parameters from NodeWorld.java
	{
		this.theme = theme;
		this.skillID = skillID;
		this.difficulty = difficulty;//insert random factor that will adjust difficulty
		currentFloor = 1;
		numFloor = (int) (Math.random()*difficulty) + (int)(difficulty/10) + 1;
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
	}	
}