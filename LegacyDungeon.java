/*
//Needs to construct Window with JFrame
//Needs to paint the Window constantly
//Needs to take keyboard/mouse input and do something with it
//Collision check should be in a separate file
//music/Sound should be in a different file
//AI should be in a a different file
//Random map generation should be in a different file
//Character stats/skills should also be in a different file
//While in the dungeon, the World Map can be updated (use a thread!!! huuehuuehuue) 
//What the hell are threads??????? - uses the Runnable Interface
//Threads allow for parallel processing - in which multiple methods run at the same time
//We need something that allows the program to shutdown safely
//How do you make it multi-platform? What safety things (prevent crashing, allows for different OS's, etc.) are behind a video game?
//How do games install? wtf??!?!?!?!?!?!?!!?
//We also need to include Java DL files for people without JAVA to guarantee that the game will run
//Also needs safe file (save will be in a different file)
//package LegacyDungeon;//a package ensures that upon mixing it with other people's codes, names won't confuse the compiler
*/
import java.awt.*;//not sure
import java.awt.event.*;//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
import java.awt.image.*;//image class allows for storage of image files
import javax.swing.*;//part of UI
import javax.imageio.*;//handles the input/output of IMAGE files
import java.io.*;//handles input/output of files (ie: reads files)
public class LegacyDungeon
{
  public static void main(String[] args)
  {
    //Some variables here
    JFrame window = new JFrame("Legacy Dungeon");//Creates a JFrame Object and titles it "Legacy Dungeons"
    Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
    window.setSize(500,500);//Sets size in pixels
<<<<<<< HEAD
	int windowX = (int) (window.getWidth());
	int windowY = (int) (window.getHeight());
=======
    int windowX = (int) (window.getWidth());
    int windowY = (int) (window.getWidth());
>>>>>>> af808055283e3d5b6eb13c89da448662d7f10494
    int x = (int) (screenRes.getWidth() - windowX)/2;//obtains width of user screen, divides by two, then subtracts by size of window
    int y = (int) (screenRes.getHeight() - windowY)/2;//same as above except with height
    //Creating the frame
    JFrame.setDefaultLookAndFeelDecorated(true);//allows for customization of icons/windows/etc.
    window.setIconImage(icon()); //Sets icon image
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Makes it so the program stops running when the "X" button is pressed
    window.setLocation(x, y);//sets location to center
    window.setVisible(true);//Makes it visible...
    //Creates a drawing based from a picture file
    
    //Infinite while loop that keeps updating the window with stuff
      //Checks for keyboard input
      //Checks for pressing X/something that ends the game   
  }
  public static Image icon()//This method is used to prevent the game from crashing if it can't locate the icon.jpg
  {
      BufferedImage icon = null;
      try
      {
        icon = ImageIO.read(new File("icon.png"));
      }
      catch (IOException e)
	  {
      }
      return icon;
  }
}