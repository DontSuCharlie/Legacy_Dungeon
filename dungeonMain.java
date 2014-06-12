/*
////////////////////////////////////LIST OF METHODS////////////////////////////////////////////
List of useable methods:
createWindow() - creates the window
paint() - needed to actually draw graphics
*/
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class DungeonMain extends JPanel implements Runnable
{
    static Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();//gets size of screen
    ArrayList<Node> nodeList;
    ArrayList<Character> visibleCharacters = new ArrayList<Character>();
    static ArrayList<DeadCharacter> recentDeadCharList = new ArrayList<DeadCharacter>();
    ArrayList<Enemy> activeEnemyList = new ArrayList<Enemy>();
    static ArrayList<Number> NumberList = new ArrayList<Number>();
    static ArrayList<Projectile> ProjectileList = new ArrayList<Projectile>();
    DungeonTile[][] tileArray;
    static JFrame window;
    WorldMap world;
    static int turnCounter = 0;
    DungeonRunner dungeon;    
    //private Timer timer;
    final static int DELAY = 25;
    private Thread animator;
    boolean screenShakeOn;
    
    static ImageLoader imageLoader = new ImageLoader();
    BufferedImage tileImage0 = imageLoader.loadImage("images/Wall.png");
    BufferedImage tileImage1 = imageLoader.loadImage("images/DungeonTile1.png");
    BufferedImage tileImage2 = imageLoader.loadImage("images/DungeonTile2.png");
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
    
    BufferedImage num0 = imageLoader.loadImage("images/0.png");
    BufferedImage num1 = imageLoader.loadImage("images/1.png");
    BufferedImage num2 = imageLoader.loadImage("images/2.png");
    BufferedImage num3 = imageLoader.loadImage("images/3.png");
    BufferedImage num4 = imageLoader.loadImage("images/4.png");
    BufferedImage num5 = imageLoader.loadImage("images/5.png");
    BufferedImage num6 = imageLoader.loadImage("images/6.png");
    BufferedImage num7 = imageLoader.loadImage("images/7.png");
    BufferedImage num8 = imageLoader.loadImage("images/8.png");
    BufferedImage num9 = imageLoader.loadImage("images/9.png");
    BufferedImage num0G = imageLoader.loadImage("images/0G.png");
    BufferedImage num1G = imageLoader.loadImage("images/1G.png");
    BufferedImage num2G = imageLoader.loadImage("images/2G.png");
    BufferedImage num3G = imageLoader.loadImage("images/3G.png");
    BufferedImage num4G = imageLoader.loadImage("images/4G.png");
    BufferedImage num5G = imageLoader.loadImage("images/5G.png");
    BufferedImage num6G = imageLoader.loadImage("images/6G.png");
    BufferedImage num7G = imageLoader.loadImage("images/7G.png");
    BufferedImage num8G = imageLoader.loadImage("images/8G.png");
    BufferedImage num9G = imageLoader.loadImage("images/9G.png");
    BufferedImage num0R = imageLoader.loadImage("images/0R.png");
    BufferedImage num1R = imageLoader.loadImage("images/1R.png");
    BufferedImage num2R = imageLoader.loadImage("images/2R.png");
    BufferedImage num3R = imageLoader.loadImage("images/3R.png");
    BufferedImage num4R = imageLoader.loadImage("images/4R.png");
    BufferedImage num5R = imageLoader.loadImage("images/5R.png");
    BufferedImage num6R = imageLoader.loadImage("images/6R.png");
    BufferedImage num7R = imageLoader.loadImage("images/7R.png");
    BufferedImage num8R = imageLoader.loadImage("images/8R.png");
    BufferedImage num9R = imageLoader.loadImage("images/9R.png");
    BufferedImage plusG = imageLoader.loadImage("images/PlusG.png");
    BufferedImage minus = imageLoader.loadImage("images/Minus.png");
    BufferedImage minusR = imageLoader.loadImage("images/MinusR.png");
    BufferedImage divide = imageLoader.loadImage("images/Slash.png");

    
    public static boolean isEnemyTurn = false;
        
    public DungeonMain() throws InstantiationException, IllegalAccessException
    {
        window = new JFrame("Hazardous Laboratory");
        dungeon = new DungeonRunner(1,1,1,100,100,1,null, null);
        tileArray = DungeonRunner.tileList;
    }
    

    
    public void dungeonLoop() throws InstantiationException, IllegalAccessException
    {
        Window window = new Window();
        Window.createWindow();//creates window
        Window.window.add(this);//adds game file to the window
        boolean inGame = true;
               
        while (inGame)
        {
            //Player actions
            if (KeyboardInput.boolIsMoving == true)
            {
                dungeon.playerCharacter.move(this);
            }
            
            if (KeyboardInput.boolIsAttack == true)
            {
                dungeon.playerCharacter.attack(this);
            }
            
            if(KeyboardInput.boolIsInteracting == true)
            {
                dungeon.playerCharacter.interact(this);
            }
            
            if(KeyboardInput.boolIs1 == true)
            {
                dungeon.playerCharacter.useSkill1(this);
                KeyboardInput.boolIs1 = false;
            }
            
            if(KeyboardInput.diagnostic == true)
            {
                System.out.println(dungeon.playerCharacter.currentTile.x + " " + dungeon.playerCharacter.currentTile.y);
            }
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
       visibleCharacters.clear();
       BufferedImage image = null;
       super.paintComponent(g);//we have to do super because magic
       //Graphics2D g2 = (Graphics2D) g;
       //Border lineborder = 
       //g.setStroke(new BasicStroke(10));
       g.setColor(Color.red);
       final int numTilesX = 16;
       final int numTilesY = 9;
       
       //Needed length and height of tiles in pixels
       int tileLengthX = (int) screenRes.getWidth() / numTilesX;
       int tileLengthY = (int) screenRes.getHeight() / numTilesY;
       int enemyNumber = 0;
       int screenShakeX = 0;
       int screenShakeY = 0;
       
       if(recentDeadCharList.size() != 0 || dungeon.playerCharacter.isHit)
       {
           screenShakeOn = true;
       }
       
       else
       {
           screenShakeOn = false;
       }
       
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
               if (dungeon.playerCharacter.currentTile.x - numTilesX/2 + i  >= 0 && dungeon.playerCharacter.currentTile.x - numTilesX/2 + i < DungeonRunner.xLength && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j >= 0 && dungeon.playerCharacter.currentTile.y - numTilesY/2 + j < DungeonRunner.yLength)
               {
                   drawnTile = tileArray[dungeon.playerCharacter.currentTile.x - numTilesX/2 + i][dungeon.playerCharacter.currentTile.y - numTilesY/2 + j];
               }
               // Draws a row of tiles.
               if (drawnTile instanceof DungeonTile)
               {
                   if (drawnTile.tileID == 1)
                   {
                       image = tileImage1;
                   }
                   else if (drawnTile.tileID == 2)
                   {
                       image = tileImage2;
                   }
                   
                   //More variations of tiles here.
               }       
               
               else
               {           
                       image = tileImage0;
               }
               
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
                   if(drawnTile.number instanceof HitNumber)
                   {
                       int damageDisplayed = ((HitNumber)drawnTile.number).amount;
                       //-2 for making the numbers fit in correct space.
                       int numCounter = -2;
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
                               if(oneDigit == 0)
                               {
                                   image = num0;
                               }
                               
                               if(oneDigit == 1)
                               {
                                   image = num1;
                               }
                           
                               if(oneDigit == 2)
                               {
                                   image = num2;
                               }
                           
                               if(oneDigit == 3)
                               {
                                   image = num3;
                               }
                               
                               if(oneDigit == 4)
                               {
                                   image = num4;
                               }
                           
                               if(oneDigit == 5)
                               {
                                   image = num5;
                               }
                           
                               if(oneDigit == 6)
                               {
                                   image = num6;
                               }
                           
                               if(oneDigit == 7)
                               {
                                   image = num7;
                               }
                           
                               if(oneDigit == 8)
                               {
                                   image = num8;
                               }
                           
                               if(oneDigit == 9)
                               {
                                   image = num9;
                               }
                           }
                               //Draws red damage numbers if damage is dealt to an ally.
                           else if(((HitNumber)drawnTile.number).targetIsFriendly == true)
                           {                           
                                  
                               if(oneDigit == 0)
                               {
                                   image = num0R;
                               }
                                   
                               if(oneDigit == 1)
                               {
                                   image = num1R;
                               }
                               
                               if(oneDigit == 2)
                               {
                                   image = num2R;
                               }
                               
                               if(oneDigit == 3)
                               {
                                   image = num3R;
                               }
                                   
                               if(oneDigit == 4)
                               {
                                   image = num4R;
                               }
                               
                               if(oneDigit == 5)
                               {
                                   image = num5R;
                               }
                               
                               if(oneDigit == 6)
                               {
                                   image = num6R;
                               }
                               
                               if(oneDigit == 7)
                               {
                                   image = num7R;
                               }
                               
                               if(oneDigit == 8)
                               {
                                   image = num8R;
                               }
                               
                               if(oneDigit == 9)
                               {
                                   image = num9R;
                               }
                           }
                               //Using the timer for y coordinates allow it to float up.
                           g.drawImage(image, i * tileLengthX - numCounter * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - numCounter * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, image.getWidth(null), image.getHeight(null), null);
                           //Stop displaying number after some time.
                           if (drawnTile.number.timer <= 0)
                           {
                               drawnTile.number = null;
                               NumberList.remove(drawnTile.number.timer);
                           }
                       }while(damageDisplayed != 0);
                       //Draws corresponding minuses
                       if(((HitNumber)drawnTile.number).targetIsFriendly == false)
                       {
                           g.drawImage(minus, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);
                       }
                       
                       else if(((HitNumber)drawnTile.number).targetIsFriendly == true)
                       {
                           g.drawImage(minusR, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minusR.getWidth(null), minusR.getHeight(null), null);

                       }
                       }
                   
                   //Floating gold numbers.
                   if(drawnTile.number instanceof GoldNumber)
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
                                                          
                           if(oneDigit == 0)
                           {
                               image = num0G;
                           }
                           
                           if(oneDigit == 1)
                           {
                               image = num1G;
                           }
                       
                           if(oneDigit == 2)
                           {
                               image = num2G;
                           }
                       
                           if(oneDigit == 3)
                           {
                               image = num3G;
                           }
                           
                           if(oneDigit == 4)
                           {
                               image = num4G;
                           }
                       
                           if(oneDigit == 5)
                           {
                               image = num5G;
                           }
                       
                           if(oneDigit == 6)
                           {
                               image = num6G;
                           }
                       
                           if(oneDigit == 7)
                           {
                               image = num7G;
                           }
                       
                           if(oneDigit == 8)
                           {
                               image = num8G;
                           }
                       
                           if(oneDigit == 9)
                           {
                               image = num9G;
                           }
                                          
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
                      
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
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
                      
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
       g.drawImage(image, 2 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 9*tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
       
       }while(goldNum != 0);
       
       //Display health. 3 digits each. (current/total)
       int healthNum = dungeon.playerCharacter.currentHealth;
       numCounter = 0;
       oneDigit = 0;
       image = num0;

       
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
                      
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
       g.drawImage(image, 5 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 21 * tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
       
       }while(healthNum != 0);
       
       g.drawImage(divide, 5 * tileLengthX, 3 * tileLengthY/4, 21 * tileLengthX/4, tileLengthY, 0, 0, divide.getWidth(null), divide.getHeight(null),(null));

       
       
       int maxHealthNum = dungeon.playerCharacter.maxHealth;
       numCounter = 0;
       oneDigit = 0;
       image = num0;

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
                      
       if(oneDigit == 0)
       {
           image = num0;
       }
           
       if(oneDigit == 1)
       {
           image = num1;
       }
       
       if(oneDigit == 2)
       {
           image = num2;
       }
       
       if(oneDigit == 3)
       {
           image = num3;
       }
       
       if(oneDigit == 4)
       {
           image = num4;
       }
       
       if(oneDigit == 5)
       {
           image = num5;
       }
       
       if(oneDigit == 6)
       {
           image = num6;
       }
       
       if(oneDigit == 7)
       {
           image = num7;
       }
       
       if(oneDigit == 8)
       {
           image = num8;
       }
       
       if(oneDigit == 9)
       {
           image = num9;
       }
       
       
       g.drawImage(image, 6 * tileLengthX - numCounter * tileLengthX/4, 3 * tileLengthY/4, 25 * tileLengthX/4 - numCounter * tileLengthX/4, tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null),(null));
      // System.out.println(oneDigit);
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
           {
               sleepTime = 2;
           }
        
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
                   {
                       i.imageID = 1;
                   }
                
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
