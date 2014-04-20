/*
DungeonRunner.java is the file that generates the dungeon based on the information included in NodeWorld.java
DungeonRunner.java will tell LegacyDungeon.java (the main runner) what to display on the screen, so yeah.
*/

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
		this.difficulty = difficulty;
		currentFloor = 1;
		numFloor = (int) (Math.random()*50) + 1;
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

Method 0: .checkStairs()
Method 1: .checkLastFloor()
Method 2: .monsterGenerator()
Method 3: .loadTiles()
Method 4: .spawnPlayer()
Method 5: .spawnStairs()
*/
	//Method 0: .checkStairs() checks whether or not the player reached the stairs
	public static boolean checkStairs(boolean reachStairs)
	{
		if(reachStairs)
		{
			currentFloor++;
		}
	}
	//Method 1: .checkLastFloor() checks whether or not the player will is at the last floor
	public static boolean checkLastFloor(int currentFloor)
	{
		currentFloor = this.currentFloor;
		if(currentFloor == numFloor)
		{
			
		}
	//Method 2: .monsterGenerator() generates monsters and gives them a position
	//Method 3: .loadTiles() chooses the picture tile to load and draws out the map
	//Method 4: .spawnPlayer() spawns the player at a random position
	//Method 5: .spawnStairs() spawns stairs at a random position significantly far away from the player
	
//This is the method that checks whether or not you've entered another floor
	public static boolean newFloor(boolean reachStairs)
	{
		if(reachStairs)
		{
			currentFloor++;
			if(currentFloor = numFloor)
			{
				return true;//we have reached the end of the dungeon
			}
		}
		//Generate a new map
		numMonsters = (int)(Math.random());
		return false;//we have not reached the end of the dungeon. LegacyDungeon will generate a new map with the current variables
	}
	//Generates monsters
	public static void monsterGenerator(int numMonsters)
	{
		Monster[] monsters = new Monster[numMonsters]; 
		for(int i = numMonsters; i > 0; i--)
		{
			monsters[i] = new Monster();//will insert parameters in here later
		}
	}
	//Checks if Item and Character are in the same box
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
	//
	
}