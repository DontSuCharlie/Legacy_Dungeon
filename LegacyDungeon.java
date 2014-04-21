///////////////////////////////////IMPORTS GALORE////////////////////////////////////
import java.awt.*;//not sure
import java.awt.event.*;//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
import java.awt.image.*;//image class allows for storage of image files
import javax.swing.*;//part of UI
import javax.imageio.*;//handles the input/output of IMAGE files
import java.io.*;//handles input/output of files (ie: reads files)
public class LegacyDungeon extends JPanel
{
  public static void main(String[] args)
  {
	createWindow();//creates window
	
  }
/*///////////////////////////////////PAINTING IMAGES///////////////////////////////////
    WorldMap world = new WorldMap();
	NodeWorld node = new NodeWorld();
	DungeonRunner dungeon = new DungeonRunner();
	//Loads World Map
		//Loads World Map Image
	JPanel insertNameOfFancyMethodHere = (world.map);
		//Creates randomly positioned nodes
			//Gives nodes randomized values
			//Positions player on node the player was previously at (if no previous node, then starts at Heart)
		//Takes input.
		//If input = Enter, then check availability of dungeon
		//If available, enter dungeon
		//Turn screen to black
	
	//If player enters a dungeon, load dungeon
		//Use node information to generate dungeon floor
		//Takes input.
		//Displays UI that reflects HP, Skill EXP (skill EXP is dependent on use), Cooldown, etc.
		//Displays Timer
		//Every 5 minutes, the enemy forces will have moved by 1 turn (will notify player)
		//Check to see if the player has reached another floor
			//If the player is about to approach the final floor, a new type of floor will be generated - the final floor
		//Generates new map
		//repeat until player reaches final floor
		//If player reaches end of dungeon, dies, or uses Escape Crystal, return to World Map
		//Update World Map with movement of Enemy Forces
	//Checks to see if Final Boss is at the Heart. If so, starts counting down on turns left before game ends
	//If Final Boss succeeds, game ends
	//If player defeats Final Boss, the player wins!
	//Creates a drawing based from a picture file
	*/
///////////////////////CREATING THE WINDOW/
public static void createWindow()
{
    JFrame window = new JFrame("Legacy Dungeon");//Creates a JFrame Object and titles it "Legacy Dungeon"
    Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
    window.setSize(500,500);//Sets size in pixels
	int windowX = (int) (window.getWidth());//grabs the width of window made
	int windowY = (int) (window.getHeight());//grabs the height of window made
    int windowPosX = (int) (screenRes.getWidth() - windowX)/2;//obtains width of user screen, divides by two, then subtracts by size of window
    int windowPosY = (int) (screenRes.getHeight() - windowY)/2;//same as above except with height
    //Creating the frame
    JFrame.setDefaultLookAndFeelDecorated(true);//allows for customization of icons/windows/etc.
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image icon = toolkit.getImage("icon.png");
    window.setIconImage(icon); //Sets icon image
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
    window.setLocation(windowPosX, windowPosY);//sets location to center
    window.setVisible(true);//Makes it visible...
}
	
///////////////////////////CLOSING THE GAME/CLEANING UP//////////////////////////////
	//Auto save? Will add in later
//Checks for pressing X/something that ends the game 
}