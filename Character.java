/*
Character.java is the superclass of NPC.java, Player.java, and Enemy.java. All characters will have the following attributes in the file.
1] Loading Sprite (not sure about animation)
2] HP
3] Max HP
4] X and Y coordinate positions (in the map)
5] Inventory
6] Skills
*/
public class Character extends JFrame
{
//Note to self: Remember to update public or not based on how you want other classes to access this file
	//Field
	BufferedImage image; //Creates an image and loads it. 
	int maxHealth;//maximum HP
	int currentHealth; //current HP
	double xPos; //current x-coordinate position
	double yPos; //current y-coordinate position
	ArrayList<Integer> inventory = new ArrayList<Integer>;//this is the inventory
	Skill[] skill = new Skill[20];//maximum number of skills you can hold is 20.
	public int spriteHeight;//height of image, so rescaling is possible
	public int spriteWidth;//width of image, so rescaling is possible
    public int attemptedX;// These are the coordinates of a tile the character tries to go to. 
    public int attemptedY;
    
	//Constructor
    

	public Character(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	//Superclass's Methods
    
    //Used to see if tile Character is attempting to go to will do something, nothing, or not allow movement.
    public int CheckTile(
    
        
    
    }
    
    
    
    public void draw(Graphics g)
    {
        g.drawImage(image, XPos, YPos, XSpriteSize, YSpriteSize, null);
    }
   
    public void paint(Graphics g)
    {
       ObjectProperites.super.paint(g);
    }
    //Methods unique to Character.java
}