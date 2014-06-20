//DungeonTile generates tile
//DungeonMain runs dungeon and just updates shit that goes on
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
public class DungeonMain extends JPanel implements Runnable
{
	ArrayList<Character> visibleCharacters = new ArrayList<Character>();//list of characters that ou want to make visible?
	static ArrayList<DeadCharacter> recentDeadCharList = new ArrayList<DeadCharacter>();//list of dead characters?
	ArrayList<Enemy> activeEnemyList = new ArrayList<Enemy>();//list of live enemies
	static ArrayList<Number> NumberList = new ArrayList<Number>();//list of numbers
	static ArrayList<Projectile> ProjectileList = new ArrayList<Projectile>();//list of projectiles
	static JFrame window;//to create new window
	DungeonBuilder dungeon;//to create dungeon tile
	final static int DELAY = 25;//sets fps to 55
	private Thread animator;//new thread
	boolean screenShakeOn;//if true, screen will be shaking; else, screen won't be shaking
	static ImageLoader imageLoader = new ImageLoader();//in charge of loading images
	BufferedImage tileImage0 = imageLoader.loadImage("images/Wall.png");
	BufferedImage money = imageLoader.loadImage("images/coinGold.png");
	static BufferedImage playerImageEast = imageLoader.loadImage("images/playerEast.png");
	static BufferedImage playerImageWest = imageLoader.loadImage("images/playerWest.png");
	static BufferedImage playerImageNorth = imageLoader.loadImage("images/playerNorth.png");
	static BufferedImage playerImageSouth = imageLoader.loadImage("images/playerSouth.png");
	static BufferedImage playerImageEastAlt = imageLoader.loadImage("images/playerEastAlt.png");
	static BufferedImage playerImageWestAlt = imageLoader.loadImage("images/playerWestAlt.png");
	static BufferedImage playerImageNorthAlt = imageLoader.loadImage("images/playerNorthAlt.png");
	static BufferedImage playerImageSouthAlt = imageLoader.loadImage("images/playerSouthAlt.png");
	//static BufferedImage playerImageEastDead = imageLoader.loadImage("images/slimeEastDead.png");
	//static BufferedImage playerImageWestDead = imageLoader.loadImage("images/slimeWestDead.png");
	//static BufferedImage playerImageNorthDead = imageLoader.loadImage("images/slimeNorthDead.png");
	//static BufferedImage playerImageSouthDead = imageLoader.loadImage("images/slimeSouthDead.png");
	static BufferedImage playerImageEastHit = imageLoader.loadImage("images/playerEastHit.png");
	static BufferedImage playerImageWestHit = imageLoader.loadImage("images/playerWestHit.png");
	//static BufferedImage playerImageNorthHit = imageLoader.loadImage("images/slimeNorthHit.png");
	//static BufferedImage playerImageSouthHit = imageLoader.loadImage("images/slimeSouthHit.png");
	static BufferedImage slimeImageEast = imageLoader.loadImage("images/slimeEast.png");
	static BufferedImage slimeImageWest = imageLoader.loadImage("images/slimeWest.png");
	static BufferedImage slimeImageNorth = imageLoader.loadImage("images/slimeNorth.png");
	static BufferedImage slimeImageSouth = imageLoader.loadImage("images/slimeSouth.png");
	static BufferedImage slimeImageEastWalk = imageLoader.loadImage("images/slimeEastWalk.png");
	static BufferedImage slimeImageWestWalk = imageLoader.loadImage("images/slimeWestWalk.png");
	static BufferedImage slimeImageNorthWalk = imageLoader.loadImage("images/slimeNorthWalk.png");
	static BufferedImage slimeImageSouthWalk = imageLoader.loadImage("images/slimeSouthWalk.png");
	static BufferedImage slimeImageEastDead = imageLoader.loadImage("images/slimeEastDead.png");
	static BufferedImage slimeImageWestDead = imageLoader.loadImage("images/slimeWestDead.png");
	static BufferedImage slimeImageNorthDead = imageLoader.loadImage("images/slimeNorthDead.png");
	static BufferedImage slimeImageSouthDead = imageLoader.loadImage("images/slimeSouthDead.png");
	static BufferedImage slimeImageEastHit = imageLoader.loadImage("images/slimeEastHit.png");
	static BufferedImage slimeImageWestHit = imageLoader.loadImage("images/slimeWestHit.png");
	static BufferedImage slimeImageNorthHit = imageLoader.loadImage("images/slimeNorthHit.png");
	static BufferedImage slimeImageSouthHit = imageLoader.loadImage("images/slimeSouthHit.png");
	static BufferedImage combatSlimeImageEast = imageLoader.loadImage("images/combatSlimeEast.png");
	static BufferedImage combatSlimeImageWest = imageLoader.loadImage("images/combatSlimeWest.png");
	static BufferedImage combatSlimeImageNorth = imageLoader.loadImage("images/combatSlimeNorth.png");
	static BufferedImage combatSlimeImageSouth = imageLoader.loadImage("images/combatSlimeSouth.png");
	static BufferedImage combatSlimeImageEastWalk = imageLoader.loadImage("images/combatSlimeEastWalk.png");
	static BufferedImage combatSlimeImageWestWalk = imageLoader.loadImage("images/combatSlimeWestWalk.png");
	static BufferedImage combatSlimeImageNorthWalk = imageLoader.loadImage("images/combatSlimeNorthWalk.png");
	static BufferedImage combatSlimeImageSouthWalk = imageLoader.loadImage("images/combatSlimeSouthWalk.png");
	static BufferedImage combatSlimeImageEastDead = imageLoader.loadImage("images/combatSlimeEastDead.png");
	static BufferedImage combatSlimeImageWestDead = imageLoader.loadImage("images/combatSlimeWestDead.png");
	static BufferedImage combatSlimeImageNorthDead = imageLoader.loadImage("images/combatSlimeNorthDead.png");
	static BufferedImage combatSlimeImageSouthDead = imageLoader.loadImage("images/combatSlimeSouthDead.png");
	BufferedImage[] num = new BufferedImage[10];
	BufferedImage[] numG = new BufferedImage[10];
	BufferedImage[] numR = new BufferedImage[10];
	BufferedImage plusG = imageLoader.loadImage("images/PlusG.png");
	BufferedImage minus = imageLoader.loadImage("images/Minus.png");
	BufferedImage minusR = imageLoader.loadImage("images/MinusR.png");
	BufferedImage divide = imageLoader.loadImage("images/Slash.png");
	public static boolean isEnemyTurn = false;
	public DungeonMain() throws InstantiationException, IllegalAccessException
	{
		window = new JFrame("Hazardous Laboratory");
		dungeon = new DungeonBuilder(1,1,1,100,100,1,null, null);
		for(int i = 0; i < 9; i++)
		{
			num[i] = imageLoader.loadImage("images/" + i + ".png");
		}
		for(int i = 0; i < 9; i++)
		{
			numR[i] = imageLoader.loadImage("images/" + i + "R.png");
		}
		for(int i = 0; i < 9; i++)
		{
			numG[i] = imageLoader.loadImage("images/" + i + "G.png");
		}
	}
	//Basically runs the dungeonmain object. It goes into a loop
	public void dungeonLoop() throws InstantiationException, IllegalAccessException
	{
		Window window = new Window();
		Window.createWindow();//creates window
		Window.window.add(this);//adds game file to the window
		boolean inGame = true;
		while (inGame)
		{
			//Player actions
			if (KeyboardInput.boolIsMoving)
				dungeon.playerCharacter.move(this);
			if (KeyboardInput.boolIsAttack)
				dungeon.playerCharacter.attack(this);
			if(KeyboardInput.boolIsInteracting)
				dungeon.playerCharacter.interact(this);
			if(KeyboardInput.boolIs1)
			{
				dungeon.playerCharacter.useSkill1(this);
				KeyboardInput.boolIs1 = false;
			}
			if(KeyboardInput.diagnostic)
				System.out.println(dungeon.playerCharacter.currentTile.x + " " + dungeon.playerCharacter.currentTile.y);
			//Enemy actions
			for (Projectile i: ProjectileList)
			{
				i.act(this);
			}
			while(isEnemyTurn)
			{
				for (Enemy i: dungeon.enemyList)
				{
					i.act(this);
				}
				//This can be changed if speed changes occur.
				isEnemyTurn = false;
			}
		}
	}
	//Methods that already work
	//Run when panel has started.
	public void addNotify()
	{
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	//Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
	//Calling game.repaint() will update what's in here.
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);//we have to do super because magic
		visibleCharacters.clear();
		BufferedImage image = null;//used to draw whatever image is needed
		//
		final int numTilesX = 16;
		final int numTilesY = 9;
		//Needed length and height of tiles in pixels
		int tileLengthX = (int) Window.windowX / numTilesX;
		int tileLengthY = (int) Window.windowY / numTilesY;
		int enemyNumber = 0;
		int screenShakeX = 0;
		int screenShakeY = 0;
		if(recentDeadCharList.size() != 0 || dungeon.playerCharacter.isHit)
			screenShakeOn = true;
		else
			screenShakeOn = false;
		if(screenShakeOn)
		{
			screenShakeX = (int)(20 * Math.random() - 10);
			screenShakeY = (int)(20 * Math.random() - 10);
		}
		for (int i = 0; i < numTilesX; i++)
		{
			for (int j = 0; j < numTilesY; j++)
			{
				//Draw tiles
				DungeonTile drawnTile = null;
				if (dungeon.playerCharacter.currentTile.x - numTilesX/2 + i>= 0 && dungeon.playerCharacter.currentTile.x - numTilesX/2 + i < DungeonBuilder.xLength && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j >= 0 && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j < DungeonBuilder.yLength)
				{
					drawnTile = dungeon.tileList[dungeon.playerCharacter.currentTile.x - numTilesX/2 + i][dungeon.playerCharacter.currentTile.y - numTilesY/2 + j];
				}
				// Draws a row of tiles.
				if (!(drawnTile instanceof DungeonTile))
					image = tileImage0;
				g.drawImage(image, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, image.getWidth(null), image.getHeight(null), null);

				//Draw bodies :<
				if (drawnTile instanceof DungeonTile && drawnTile.deadCharacter instanceof DeadCharacter)
				{
					if (drawnTile.deadCharacter.prevCharacter instanceof Jam)
					{
						Image slimeImage = null;
						switch(drawnTile.deadCharacter.prevCharacter.direction)
						{
							case 0: slimeImage = slimeImageEastDead;
							break;
							case 1: slimeImage = slimeImageNorthDead;
							break;
							case 2: slimeImage = slimeImageWestDead;
							break;
							case 3: slimeImage = slimeImageSouthDead;
							break;
							default: slimeImage = slimeImageEastDead;
						}
						g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
					}
					if (drawnTile.deadCharacter.prevCharacter instanceof CombatJam)
					{
						Image combatSlimeImage = null;
						switch(drawnTile.deadCharacter.prevCharacter.direction)
						{
						case 0: combatSlimeImage = combatSlimeImageEastDead;
						break;
						case 1: combatSlimeImage = combatSlimeImageNorthDead;
						break;
						case 2: combatSlimeImage = combatSlimeImageWestDead;
						break;
						case 3: combatSlimeImage = combatSlimeImageSouthDead;
						break;
						default: combatSlimeImage = combatSlimeImageEastDead;
						}
						g.drawImage(combatSlimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, combatSlimeImage.getWidth(null) + 50, combatSlimeImage.getHeight(null) + 100, null);
					}
				}
				//Draw money
				if (drawnTile instanceof DungeonTile && drawnTile.itemID != 0)
				{
					if (drawnTile.itemID == 1)
					{
						image = money;
						g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (int)((.5+j) * tileLengthY), 0, 0, image.getWidth(null), image.getHeight(null), null);
					}
				}
				//Draw player
				if (drawnTile instanceof DungeonTile && drawnTile.character instanceof Character)
				{
					visibleCharacters.add(drawnTile.character);
					if (drawnTile.character instanceof Enemy && (((Enemy)(drawnTile.character)).isActive == false))
					{
						((Enemy)(drawnTile.character)).isActive = true;
						//activeEnemyList.add((Enemy)drawnTile.character);
						//Start aim mode, preventing movement here.
					}
					if (drawnTile.character instanceof Player)
					{
						Image playerImage;
						playerImage = dungeon.playerCharacter.getImage();
						g.drawImage(playerImage, (int)((numTilesX/2 + .15) * tileLengthX), (int)((numTilesY/2 + .15) * tileLengthY), (int)((numTilesX/2 + .85) * tileLengthX), (int)((numTilesY/2 + .85) * tileLengthY), 0, 0, playerImage.getWidth(null), playerImage.getHeight(null), null);
					}
					//Draw Jam
					else if (drawnTile.character instanceof Jam)
					{
						BufferedImage slimeImage = ((Jam)drawnTile.character).getImage();
						g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
					}
				}
				if (drawnTile instanceof DungeonTile && drawnTile.projectile instanceof Projectile)
				{

				}
				//Draw floating numbers
				if(drawnTile instanceof DungeonTile && drawnTile.number instanceof Number)
				{
					//-2 for making the numbers fit in correct space.
					int numCounter = -2;
					if(drawnTile.number instanceof HitNumber)
					{
						int damageDisplayed = ((HitNumber)drawnTile.number).amount;
						int oneDigit = 0;
						do
						{
							if(damageDisplayed > 9)
							{
								oneDigit = damageDisplayed % 10;
								damageDisplayed /= 10;
								numCounter++;
							}
							else
							{
								oneDigit = damageDisplayed;
								damageDisplayed = 0;
								numCounter++;
							}
							//Draws white numbers if damage is dealt to an enemy
							if(((HitNumber)drawnTile.number).targetIsFriendly == false)
							{
								image = num[oneDigit];
							}
							//Draws red damage numbers if damage is dealt to an ally.
							else if(((HitNumber)drawnTile.number).targetIsFriendly == true)
							{
								image = numR[oneDigit];
							}
						}while(damageDisplayed != 0);
						
						//Stop displaying number after some time.
						if (drawnTile.number.timer <= 0)
						{
							drawnTile.number = null;
							//NumberList.remove(drawnTile.number.timer);
						}						
						
						//Using the timer for y coordinates allow it to float up.
						g.drawImage(image, i * tileLengthX - numCounter * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - numCounter * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, image.getWidth(null), image.getHeight(null), null);
					}
					//Draws corresponding minuses
					if(!((HitNumber)drawnTile.number).targetIsFriendly)
					{
						g.drawImage(minus, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);
					}
					else if(((HitNumber)drawnTile.number).targetIsFriendly)
					{
						g.drawImage(minusR, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minusR.getWidth(null), minusR.getHeight(null), null);
					}
				}
				//Floating gold numbers.
				System.out.println("This is the drawnTile " + drawnTile);
				if(drawnTile != null && drawnTile.number instanceof GoldNumber)
				{
					int goldDisplayed = ((GoldNumber)drawnTile.number).amount;
					int numCounter = -2;
					int oneDigit = 0;
					do
					{
						if(goldDisplayed > 9)
						{
							oneDigit = goldDisplayed % 10;
							goldDisplayed /= 10;
							numCounter++;
						}
						else
						{
							oneDigit = goldDisplayed;
							goldDisplayed = 0;
							numCounter++;
						}
							image = numG[oneDigit];
							g.drawImage(image, i * tileLengthX - numCounter * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - numCounter * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, image.getWidth(null), image.getHeight(null), null);
						if (drawnTile.number.timer <= 0)
						{
							drawnTile.number = null;
							NumberList.remove(drawnTile.number.timer);
						}
					}while(goldDisplayed != 0);
					g.drawImage(plusG, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);
				}
			}
		}
		//Death animations. Very broken
		for (DeadCharacter i : recentDeadCharList)
		{
			if (i.prevCharacter instanceof Jam)
			{
			Image slimeImage = null;
			switch(i.prevCharacter.direction)
			{
			case 0: slimeImage = slimeImageEastHit;
			break;
			case 1: slimeImage = slimeImageNorthHit;
			break;
			case 2: slimeImage = slimeImageWestHit;
			break;
			case 3: slimeImage = slimeImageSouthHit;
			break;
			default: slimeImage = slimeImageEastHit;
			}
			if (i.deathTimer <= 0)
			{
			recentDeadCharList.remove(i);
			}
			g.drawImage(slimeImage, i.prevCharacter.currentTile.x * tileLengthX + 25, i.prevCharacter.currentTile.y * tileLengthY + 25, (i.prevCharacter.currentTile.x+1) * tileLengthX, (i.prevCharacter.currentTile.y+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null); 
			}
		}
		//UI is drawn last so it's on top of everything else.
		int floorNum = dungeon.currentFloor;
		int numCounter = 0;
		int oneDigit = 0;
		//Supports 3-digit numbers for floors.Theoretically could do more, but that makes GUI :<.
		do
		{
			if(floorNum > 9)
			{
				oneDigit = floorNum % 10;
				floorNum /= 10;
				numCounter++;
			}
			else
			{
				oneDigit = floorNum;
				floorNum = 0;
				numCounter++;
			}
			image = num[oneDigit];
			g.drawImage(image, tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 5*tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
		}while(floorNum != 0);

		int goldNum = dungeon.playerCharacter.goldAmount;
		numCounter = 0;
		oneDigit = 0;
		do
		{
			if(goldNum > 9)
			{
				oneDigit = goldNum % 10;
				goldNum /= 10;
				numCounter++;
			}
			else
			{
				oneDigit = goldNum;
				goldNum = 0;
				numCounter++;
			}
			image = num[oneDigit];
			g.drawImage(image, 2 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 9*tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
		}while(goldNum != 0);
		//Display health. 3 digits each. (current/total)
		int healthNum = dungeon.playerCharacter.currentHealth;
		numCounter = 0;
		oneDigit = 0;
		image = num[0];
		do
		{
			if(healthNum > 9)
			{
				oneDigit = healthNum % 10;
				healthNum /= 10;
				numCounter++;
			}
			else
			{
				oneDigit = healthNum;
				healthNum = 0;
				numCounter++;
			}
			image = num[oneDigit];
			g.drawImage(image, 5 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 21 * tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
		}while(healthNum != 0);

		g.drawImage(divide, 5 * tileLengthX, 3 * tileLengthY/4, 21 * tileLengthX/4, tileLengthY, 0, 0, divide.getWidth(null), divide.getHeight(null),(null));
		int maxHealthNum = dungeon.playerCharacter.maxHealth;
		numCounter = 0;
		oneDigit = 0;
		image = num[0];
		do
		{
			if(maxHealthNum > 9)
			{
				oneDigit = maxHealthNum % 10;
				maxHealthNum /= 10;
				numCounter++;
			}
			else
			{
				oneDigit = maxHealthNum;
				maxHealthNum = 0;
				numCounter++;
			}
			image = num[oneDigit];
			g.drawImage(image, 6 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 25 * tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
		}while(maxHealthNum != 0);
}
	@Override
	public void run()
	{
		long previousTime, sleepTime, timeDifference;
		int counter = 0;
		previousTime = System.currentTimeMillis();
		while (true)
		{
			repaint();
			timeDifference = System.currentTimeMillis() - previousTime;
			sleepTime = DELAY - timeDifference;
			if (sleepTime < 0)
				sleepTime = 2;
			try
			{
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			previousTime = System.currentTimeMillis();
			counter++;
			// Note: change to visible enemyList.
			for (Character i : visibleCharacters)
			{
				if (counter%i.altTimer == 0)
				{
					if (i.imageID == 0)
						i.imageID = 1;
					else if(i.imageID == 1)
					{
						i.imageID = 0;
					}
				}
				if (i.isHit == true)
				{
					i.currentHitTime--;
					if(i.currentHitTime <= 0 )
					{
						i.isHit = false;
						i.currentHitTime = i.hitTimer;
					}
				}
			}
			//Complains about multiple modifications. Need to fix.
			if(recentDeadCharList.size() != 0)
			{
				for(DeadCharacter j : recentDeadCharList)
				{
					j.deathTimer--;
					//Removal takes place in paint.
				}
			}
			if(NumberList.size() != 0)
			{
				for(Number j : NumberList)
				{
					j.timer--;
				}
			}
		}
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException
	{
		//Testing
		DungeonMain game = new DungeonMain();
		game.dungeonLoop();
	}
}