//DungeonTile generates tile
//DungeonMain runs dungeon and just updates shit that goes on
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import java.io.File;

import javax.swing.*;
public class DungeonMain extends JPanel implements Runnable
{
    MusicPlayer musicPlayer = new MusicPlayer();
    ArrayList<Character> visibleCharacters = new ArrayList<Character>();//list of characters that are painted and animated.
    static ArrayList<DeadCharacter> recentDeadCharList = new ArrayList<DeadCharacter>();//list of dead characters that are undergoing death animation
    static ArrayList<DeadCharacter> deadCharList = new ArrayList<DeadCharacter>();//list of dead characters on floor. (Used for AI related to dead creatures (reviver, slime-eater)
    static ArrayList<Number>numberList = new ArrayList<Number>();//list of numbers
    static ArrayList<Projectile> projectileList = new ArrayList<Projectile>();//list of projectiles
    static JFrame window;//to create new window
    DungeonBuilder dungeon;//to create dungeon tile
    static final int DELAY = 25; //sets fps to 55
    final double OVERHEAL_DECAY_PERCENT = .1; //If a character is above this health, then this percent of health will be decayed over time.
    private Thread animator;//new thread
    boolean screenShakeOn;//if true, screen will be shaking; else, screen won't be shaking
    final int numTilesX = 16;
    final int numTilesY = 9;
    //static int firstInactiveChar = 0; //Sets up invariant of all active characters to left of this and all inactive characters to right and including this one.
    //Hashmap would be perfect for images. Will implement if have time.
    //BufferedImage tileImage0 = ImageLoader.loadImage("images/Wall.png");

    BufferedImage money = ImageLoader.loadImage("images/coinGold.png");
    BufferedImage redBarLeft = ImageLoader.loadImage("images/redBarLeft.png");
    BufferedImage redBarRight = ImageLoader.loadImage("images/redBarRight.png");
    BufferedImage redBarMiddle = ImageLoader.loadImage("images/redBarMiddle.png");
    BufferedImage greenBarLeft = ImageLoader.loadImage("images/greenBarLeft.png");
    BufferedImage greenBarRight = ImageLoader.loadImage("images/greenBarRight.png");
    BufferedImage greenBarMiddle = ImageLoader.loadImage("images/greenBarMiddle.png");	
    BufferedImage healthPot = ImageLoader.loadImage("images/healthPot.png");
    //BufferedImage fireball = ImageLoader.loadImage("images/fireball.png");

    static BufferedImage[] slimeImages = new BufferedImage[10];
    static BufferedImage[] slimeImagesAlt = new BufferedImage[10];
    static BufferedImage[] slimeImagesHit = new BufferedImage[10];
    static BufferedImage[] slimeImagesDead = new BufferedImage[10];

    static BufferedImage[] combatSlimeImages = new BufferedImage[10];
    static BufferedImage[] combatSlimeImagesAlt = new BufferedImage[10];
    static BufferedImage[] combatSlimeImagesDead = new BufferedImage[10];

    static BufferedImage[] playerImages = new BufferedImage[10];
    static BufferedImage[] playerImagesAlt = new BufferedImage[10];
    static BufferedImage[] playerImagesHit = new BufferedImage[10];
    //static BufferedImage[] playerImagesDead = new BufferedImage[10];

    static BufferedImage[] ghostImages = new BufferedImage[10];
    static BufferedImage[] ghostImagesAlt = new BufferedImage[10];
    static BufferedImage[] ghostImagesHit = new BufferedImage[10];
    static BufferedImage[] ghostImagesDead = new BufferedImage[10];

    static BufferedImage[] fireballImages = new BufferedImage[8];
    static BufferedImage[] teleballImages = new BufferedImage[8];

    //White numbers
    BufferedImage[] num = new BufferedImage[10];
    //Gold numbers
    BufferedImage[] numG = new BufferedImage[10];
    //Red numbers
    BufferedImage[] numR = new BufferedImage[10];
    //Green numbers
    BufferedImage[] numGr = new BufferedImage[10]; 

    BufferedImage[] tileImagesDefault = new BufferedImage[10];
    BufferedImage plusG = ImageLoader.loadImage("images/plusG.png");
    BufferedImage plusGr = ImageLoader.loadImage("images/plusGr.png");
    BufferedImage plusR = ImageLoader.loadImage("images/plusR.png");
    BufferedImage minus = ImageLoader.loadImage("images/minus.png");
    BufferedImage minusR = ImageLoader.loadImage("images/minusR.png");
    BufferedImage divide = ImageLoader.loadImage("images/slash.png");
    BufferedImage heart = ImageLoader.loadImage("images/heart.png");
    
    BufferedImage ghostEyebrows = ImageLoader.loadImage("images/ghostScaredEyebrows.png");
    BufferedImage ghostHalo = ImageLoader.loadImage("images/ghostHalo.png");

    public static boolean isEnemyTurn = false;

    public DungeonMain() throws InstantiationException, IllegalAccessException
    {
        //Just a simple way to activate test mode.
        String buildSetting = "Not test";
        System.out.println(buildSetting + " engaged");
        window = new JFrame("Hazardous Laboratory");
        dungeon = new DungeonBuilder(1,1,1,100,100);
        if (buildSetting.equals("test"))
        {
            dungeon.buildTest();
        }

        else
        {
            dungeon.build();
        }

        //dungeon.playerCharacter.activateArea(dungeon, dungeon.playerCharacter.xVision, dungeon.playerCharacter.yVision);

        System.out.println("Loading Character images");
        for(int i = 0; i <= 9; i++)
        {
            num[i] = ImageLoader.loadImage("images/" + i + ".png");
        }
        for(int i = 0; i <= 9; i++)
        {
            numR[i] = ImageLoader.loadImage("images/" + i + "R.png");
        }
        for(int i = 0; i <= 9; i++)
        {
            numG[i] = ImageLoader.loadImage("images/" + i + "G.png");
        }
        for(int i = 0; i <= 9; i++)
        {
            numGr[i] = ImageLoader.loadImage("images/" + i + "Gr.png");
        }
        for(int i = 0; i <= 2; i++)
        {
            tileImagesDefault[i] = ImageLoader.loadImage("images/dungeonTile" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            slimeImages[i] = ImageLoader.loadImage("images/slime" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            slimeImagesAlt[i] = ImageLoader.loadImage("images/slimeAlt" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            slimeImagesHit[i] = ImageLoader.loadImage("images/slimeHit" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            slimeImagesDead[i] = ImageLoader.loadImage("images/slimeDead" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            combatSlimeImages[i] = ImageLoader.loadImage("images/combatSlime" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            combatSlimeImagesAlt[i] = ImageLoader.loadImage("images/combatSlimeAlt" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            combatSlimeImagesDead[i] = ImageLoader.loadImage("images/combatSlimeDead" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            playerImages[i] = ImageLoader.loadImage("images/player" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            playerImagesAlt[i] = ImageLoader.loadImage("images/playerAlt" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //No sprite for center(5)
            if(i == 5)
            {
                i = 6;
            }
            playerImagesHit[i] = ImageLoader.loadImage("images/playerHit" + i + ".png");
        }
        /*
        for(int i = 2; i <= 9; i+=2)
        {
            playerImagesDead[i] = ImageLoader.loadImage("images/playerDead" + i + ".png");
        }
         */

        for(int i = 1; i <= 9; i++)
        {
            if(i == 5)
            {
                i = 6;
            }
            ghostImages[i] = ImageLoader.loadImage("images/ghost" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            if(i == 5)
            {
                i = 6;
            }
            ghostImagesAlt[i] = ImageLoader.loadImage("images/ghostAlt" + i + ".png");
        }
        for(int i = 1; i <= 9; i++)
        {
            //Using same image for all hit animations
            BufferedImage temp = ImageLoader.loadImage("images/ghostHit.png");
            ghostImagesHit[i] = temp;
        }
        for(int i = 1; i <= 9; i++)
        {
            if(i == 5)
            {
                i = 6;
            }
            ghostImagesDead[i] = ImageLoader.loadImage("images/ghostDead" + i + ".png");
        }
        for(int i = 0; i <= 7; i++)
        {
            fireballImages[i] = ImageLoader.loadImage("images/fireball" + i + ".png");
        }
        for(int i = 0; i <= 7; i++)
        {
            teleballImages[i] = ImageLoader.loadImage("images/teleball" + i + ".png");
        }
    }
    //Basically runs the dungeonmain object. It goes into a loop
    public void dungeonLoop() throws InstantiationException, IllegalAccessException
    {
        Window.createWindow(true);//creates window
        Window.window.add(this);//adds game file to the window
        boolean inGame = true;
        musicPlayer.playMusic("sounds/Worldmap2.au");
        while (inGame)
        {
            while(!dungeon.playerCharacter.goingToNewFloor)
            {
                System.out.println("Starting over");
                //Projectile movement and actions
                for (int i = 0; i < projectileList.size() && !projectileList.get(i).isDestroyed; ++i)
                {
                    /*
                    boolean destroyed = projectileList.get(i).act(this);
                    //If the projectile is destroyed, then the next one is pushed up to where i is.
                    if (destroyed)
                    {
                        --i;
                    }*/
                    projectileList.get(i).act(this);
                }

                //Character actions
                //for (Character i: dungeon.characterList)
                for (int i = 0; i < dungeon.firstInactiveChar; i++)
                {
                    System.out.println(i);
                    dungeon.characterList.get(i).act(this);
                    System.out.println(i + "Has acted");
                }
                System.out.println("Relooping");	
            }
            dungeon.playerCharacter.goingToNewFloor = false;
            nextLevel();

        }
        System.out.println("Loop broken");
    }


    /*
     * Performs required tasks for going to next floor or exiting.
     * Runs when player has interacted with stairs, setting goingToNewFloor to be true, after all enemies have taken their turn.
     * 
     */
    private void nextLevel() throws InstantiationException, IllegalAccessException
    {
        changeSpawnRates();
        dungeon.characterList = null;
        dungeon.tileList = null;
        deadCharList = new ArrayList<DeadCharacter>();
        recentDeadCharList = new ArrayList<DeadCharacter>();//list of dead characters that are undergoing death animation
        numberList = new ArrayList<Number>();//list of numbers
        projectileList = new ArrayList<Projectile>();//list of projectiles
        dungeon = new DungeonBuilder(dungeon);
        //dungeon.tileList = DungeonBuilder.tileList;
    }

    /*
     * This method should be used to change spawn rates. It should only be run when running nextLevel() so it can effect on the next floor.
     * PLACEHOLDER: Currently simply increases spawn rate of combatJams and decreases spawn rate of Jams.
     * Place fancy difficulty adjusting algorithm here. Ex. increase harder enemy spawn rate if player did not take much damage/ less spawn if player was nearly killed
     * Compute heuristics and stuff.
     */

    private void changeSpawnRates()
    {
        //Use heuristic to affect spawn rates. Deeper is also more difficult.
        dungeon.difficultyHeuristic = dungeon.playerCharacter.goldAmount - dungeon.playerFloorDamage;
        if (dungeon.theme == 1)
        {
            //If there was a way to get an element of its type, that would be helpful. As is, I if-bash with a for each loop for all elements.
            for(DungeonBuilder.Spawner s : dungeon.spawningEnemyList)
            {
                //Normal jams become less common.
                if(s.charClass.isInstance(RandomJam.class))
                {
                    s.spawnRate -= .1;
                }

                if(s.charClass.isInstance(CombatJam.class))
                {
                    s.spawnRate += .1;
                }
            }
        }

        //Clumsily done, needs more fine-tuning. This adds player wealth and subtracts damage taken.
        if(dungeon.difficultyHeuristic < 0)
        {
            dungeon.goldChance += .05;
        }

        if(dungeon.difficultyHeuristic > 0)
        {
            dungeon.goldChance -= .05;
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
    /**
     * This method will help animate movement between two tiles. It gets the coordinates on screen that should be displayed.
     * @param startTile
     * @param endTile
     * @param time
     */
    private Coordinate animateMovement(DungeonTile startTile, DungeonTile endTile, int totalTime, int elapsedTime, int xPixels, int yPixels)
    {
        //Start by getting the player's tile. This is the center of view.
        DungeonTile centerTile = dungeon.playerCharacter.currentTile;
        double xPos = ((endTile.x - startTile.x) * (double)elapsedTime / totalTime + startTile.x);// * xPixels; //Should be 0 to xLength. Initially at startTile then goes to endTile.
        double yPos = ((endTile.y - startTile.y) * (double)elapsedTime / totalTime + startTile.y);// * yPixels; //Should be 0 to yLength

        //From the tile position it's currently at, get which pixel it should be drawn at.
        //int xCoordinate = (int) (xPixels * (xPos - (centerTile.x - dungeon.playerCharacter.xVision/2.0)));
        //int yCoordinate = (int) (yPixels * (yPos - (centerTile.y - dungeon.playerCharacter.yVision/2.0)));

        int xCoordinate = (int) (xPixels * Math.abs((centerTile.x - dungeon.playerCharacter.xVision/2) - xPos));
        //Got to be careful. Don't want player at tile 4.5. Thus using integer division.
        int yCoordinate = (int) (yPixels * Math.abs((centerTile.y - dungeon.playerCharacter.yVision/2) - yPos));

        return new Coordinate(xCoordinate, yCoordinate);
    }

    /**
     * Method 1: According to java, we have to put everything we want to paint in this method. Making it visible, etc. will involve using ArrayLists. For example, if we have something we don't want to show until it spawns, then we have an ArrayList with a size of 0, and when we want it to spawn, we add 1 of the object to the ArrayList. 
     * Calling game.repaint() will update what's in here.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);//we have to do super because magic
        visibleCharacters.clear();
        BufferedImage image = null;//used to draw whatever image is needed
        //Needed length and height of tiles in pixels
        int tileLengthX = (int) Window.windowX / numTilesX;
        int tileLengthY = (int) Window.windowY / numTilesY;
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
                if (dungeon.tileChecker(dungeon.playerCharacter.currentTile.x-numTilesX/2 + i, dungeon.playerCharacter.currentTile.y-numTilesY/2 + j, false))
                {
                    drawnTile = dungeon.tileList[dungeon.playerCharacter.currentTile.x - numTilesX/2 + i][dungeon.playerCharacter.currentTile.y - numTilesY/2 + j];
                }
                // Draws a row of tiles.
                if (!(drawnTile instanceof DungeonTile))
                {
                    image = tileImagesDefault[0];
                }
                else
                {
                    image = tileImagesDefault[drawnTile.tileID];
                }
                g.drawImage(image, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, image.getWidth(null), image.getHeight(null), null);

                //Draw bodies :<. Draws the newest dead body. Meh, I should sort the list so that higher priority enemies are drawn first. Maybe later.
                if (drawnTile instanceof DungeonTile && !drawnTile.deadCharTileList.isEmpty())
                {
                    if (drawnTile.deadCharTileList.get(0).prevCharacter instanceof RandomJam)
                    {
                        Image slimeImage = null;

                        slimeImage = DungeonMain.slimeImagesDead[drawnTile.deadCharTileList.get(0).prevCharacter.direction];

                        g.drawImage(slimeImage, i * tileLengthX + 25 + screenShakeX, j * tileLengthY + 25 + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                    }

                    else if (drawnTile.deadCharTileList.get(0).prevCharacter instanceof CombatJam)
                    {
                        Image combatSlimeImage = null;
                        combatSlimeImage = DungeonMain.combatSlimeImagesDead[drawnTile.deadCharTileList.get(0).prevCharacter.direction];

                        g.drawImage(combatSlimeImage, i * tileLengthX + 25 + screenShakeX, j * tileLengthY + 25 + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, combatSlimeImage.getWidth(null) + 50, combatSlimeImage.getHeight(null) + 100, null);
                    }


                    //Tiny ghost.
                    else if (drawnTile.deadCharTileList.get(0).prevCharacter instanceof Ghost)
                    {
                        Image ghostImage = null;
                        ghostImage = DungeonMain.ghostImagesDead[drawnTile.deadCharTileList.get(0).prevCharacter.direction];

                        g.drawImage(ghostImage, i * tileLengthX + 25 + screenShakeX, j * tileLengthY + 25 + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, ghostImage.getWidth(null) + 50, ghostImage.getHeight(null) + 100, null);
                    }

                    else
                    {
                        BufferedImage charImage = drawnTile.character.getImageDead();
                        g.drawImage(charImage, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (j+1) * tileLengthY + screenShakeY, 0, 0, charImage.getWidth(null), charImage.getHeight(null), null);
                    }
                }
                //Draw money
                if (drawnTile instanceof DungeonTile && drawnTile.itemID != 0)
                {
                    if (drawnTile.itemID == 1)
                    {
                        image = money;
                        g.drawImage(image, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (int)((.5+j) * tileLengthY) + screenShakeY, 0, 0, image.getWidth(null), image.getHeight(null), null);
                    }
                }

                //Anu helped me with a cool idea. Normally the rarest item is chosen (randomly between the same rarity) to be drawn. If moused over or inspected however, a bubble will appear with all of the items and descriptions as a tooltip.
                if (drawnTile instanceof DungeonTile && drawnTile.items instanceof ArrayList)
                {
                    BufferedImage itemImage = drawnTile.getItemImage(this);
                    g.drawImage(itemImage, i * tileLengthX + screenShakeX, j * tileLengthY + screenShakeY, (i+1) * tileLengthX + screenShakeX, (int)((.5+j) * tileLengthY) + screenShakeY, 0, 0, itemImage.getWidth(null), itemImage.getHeight(null), null);
                }
                //Draw characters
                if (drawnTile instanceof DungeonTile && drawnTile.character instanceof Character)
                {
                    visibleCharacters.add(drawnTile.character);
                    //Note: should change source image to be good and then just use drawnTile.character.getImage() and display that normally.

                    if (!drawnTile.character.isMoving)
                    {

                        if (drawnTile.character instanceof Player)
                        {
                            Image playerImage;
                            playerImage = dungeon.playerCharacter.getImage();
                            g.drawImage(playerImage, (int)((numTilesX/2 + .15) * tileLengthX), (int)((numTilesY/2 + .15) * tileLengthY), (int)((numTilesX/2 + .85) * tileLengthX), (int)((numTilesY/2 + .85) * tileLengthY), 0, 0, playerImage.getWidth(null), playerImage.getHeight(null), null);
                        }
                        //Draw RandomJam
                        else if (drawnTile.character instanceof RandomJam)
                        {
                            BufferedImage slimeImage = ((RandomJam)drawnTile.character).getImage();
                            g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                            //g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null), slimeImage.getHeight(null), null);
                        }

                        //Draw CombatJam
                        else if (drawnTile.character instanceof CombatJam)
                        {
                            BufferedImage slimeImage = ((CombatJam)drawnTile.character).getImage();
                            g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                        }
                        
                        //8 is the back one
                        else if (drawnTile.character instanceof Ghost && drawnTile.character.direction != 8)
                        {
                            BufferedImage ghostImage = drawnTile.character.getImage();
                            g.drawImage(ghostImage, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, ghostImage.getWidth(null), ghostImage.getHeight(null), null);
                            
                            //Running, draw the scared eyebrows.
                            if (((Ghost) drawnTile.character).runTimer != 0)
                            {
                                g.drawImage(ghostEyebrows, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, ghostEyebrows.getWidth(null), ghostEyebrows.getHeight(null), null);
                            }
                            
                            //Ready to heal, draw the halo.
                            if (((Ghost) drawnTile.character).cooldownTimer1 == 0)
                            {
                                g.drawImage(ghostHalo, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, ghostHalo.getWidth(null), ghostHalo.getHeight(null), null);

                            }
                        }

                        //Draw Everything else
                        else
                        {
                            BufferedImage temp = drawnTile.character.getImage();
                            g.drawImage(temp, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, temp.getWidth(null), temp.getHeight(null), null);
                        }

                        //Drawn above characters
                        //if(drawnTile.character.status =! 0)
                        {
                            //Draw status effects.
                        }

                        /* The use of the next part is to draw a heart above friendly characters except the player.
                         * Currently set to draw a heart as 1/4 a tile.
                         * 
                         */

                        if(drawnTile.character.isFriendly && !(drawnTile.character instanceof Player))
                        {
                            g.drawImage(heart, (int)((i+.25)* tileLengthX + screenShakeX), (int)((j+.25) * tileLengthY + screenShakeY), (int)((i+.5) * tileLengthX + screenShakeX), (int)((j+.5) * tileLengthY + screenShakeY), 0, 0, image.getWidth(null), image.getHeight(null), null);
                        }

                    }

                    else
                    {
                        assert drawnTile.character.isMoving;
                        Coordinate coordinate = animateMovement(drawnTile.character.prevTile, drawnTile.character.currentTile, drawnTile.character.moveTimer, drawnTile.character.currentMoveTimer, tileLengthX, tileLengthY);

                        //Bah, really should fix sprites instead of doing this garbage.
                        if (drawnTile.character instanceof Player)
                        {
                            Image playerImage;
                            playerImage = dungeon.playerCharacter.getImage();
                            g.drawImage(playerImage, (int)(.15 * tileLengthX + coordinate.x), (int)( .15 * tileLengthY + coordinate.y), (int)(.85 * tileLengthX + coordinate.x), (int)( .85 * tileLengthY + coordinate.y), 0, 0, playerImage.getWidth(null), playerImage.getHeight(null), null);
                        }
                        //Draw RandomJam
                        else if (drawnTile.character instanceof RandomJam)
                        {
                            BufferedImage slimeImage = ((RandomJam)drawnTile.character).getImage();
                            g.drawImage(slimeImage, coordinate.x + 25, coordinate.y + 25, coordinate.x + 1 * tileLengthX, coordinate.y + 1 * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                            //g.drawImage(slimeImage, i * tileLengthX + 25, j * tileLengthY + 25, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, slimeImage.getWidth(null), slimeImage.getHeight(null), null);
                        }

                        //Draw CombatJam
                        else if (drawnTile.character instanceof CombatJam)
                        {
                            BufferedImage slimeImage = ((CombatJam)drawnTile.character).getImage();
                            g.drawImage(slimeImage, coordinate.x + 25, coordinate.y + 25, coordinate.x + 1 * tileLengthX, coordinate.y + 1 * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null);
                        }

                        //Draw Everything else
                        else
                        {
                            BufferedImage temp = drawnTile.character.getImage();
                            g.drawImage(temp, coordinate.x, coordinate.y, 1* tileLengthX + coordinate.x, 1 * tileLengthY + coordinate.y, 0, 0, temp.getWidth(null), temp.getHeight(null), null);
                        }
                        //g.drawImage(drawnTile.character.getImage(), coordinate.x, coordinate.y, coordinate.x + tileLengthX, coordinate.y + tileLengthY, 0, 0, drawnTile.character.getImage().getWidth(null), drawnTile.character.getImage().getHeight(null), null);
                    }
                }
                /*
                //Draw Projectiles
                if (drawnTile instanceof DungeonTile && !drawnTile.projectileList.isEmpty())
                {
                    for (int projectile = 0; i < drawnTile.projectileList.size(); i++)
                    {
                        if (!drawnTile.projectileList.get(projectile).isMoving)
                        {
                            image = drawnTile.projectileList.get(projectile).getImage(this);
                            g.drawImage(image, i * tileLengthX, j * tileLengthY, (i+1) * tileLengthX, (j+1) * tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);

                            if (drawnTile.projectileList.get(projectile).isDestroyed)
                            {
                                projectileList.remove(this);
                                drawnTile.projectileList.remove(projectile);
                                projectile--;
                            }
                        }

                        else
                        {
                            assert drawnTile.projectileList.get(projectile).isMoving;
                            Coordinate coordinate = animateMovement(drawnTile.projectileList.get(projectile).prevTile, drawnTile.projectileList.get(projectile).currentTile, drawnTile.projectileList.get(projectile).moveTimer, drawnTile.projectileList.get(projectile).currentMoveTimer, tileLengthX, tileLengthY);

                            image = drawnTile.projectileList.get(projectile).getImage(this);
                            g.drawImage(image, coordinate.x, coordinate.y, 1* tileLengthX + coordinate.x, 1 * tileLengthY + coordinate.y, 0, 0, image.getWidth(null), image.getHeight(null), null);
                        }
                    }
                }
                 */
                //Draw floating numbers.This will not shake to improve readability
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
                            g.drawImage(image, i * tileLengthX - numCounter * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - numCounter * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, image.getWidth(null), image.getHeight(null), null);


                        }while(damageDisplayed != 0);



                        //Using the timer for y coordinates allow it to float up.

                        //Draws corresponding minuses
                        if(!((HitNumber)drawnTile.number).targetIsFriendly)
                        {
                            g.drawImage(minus, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);
                        }
                        else if(((HitNumber)drawnTile.number).targetIsFriendly)
                        {
                            g.drawImage(minusR, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minusR.getWidth(null), minusR.getHeight(null), null);
                        }

                        //Stop displaying number after some time.
                        if (drawnTile.number.timer <= 0)
                        {
                            drawnTile.number = null;
                            //NumberList.remove(drawnTile.number.timer);
                        }   					
                    }

                    if(drawnTile.number instanceof HealNumber)
                    {
                        int healDisplayed = ((HealNumber)drawnTile.number).amount;
                        int oneDigit = 0;
                        do
                        {
                            if(healDisplayed > 9)
                            {
                                oneDigit = healDisplayed % 10;
                                healDisplayed /= 10;
                                numCounter++;
                            }
                            else
                            {
                                oneDigit = healDisplayed;
                                healDisplayed = 0;
                                numCounter++;
                            }
                            //Draws green numbers if this heal is performed on an ally.
                            if(((HealNumber)drawnTile.number).targetIsFriendly == true)
                            {
                                image = numGr[oneDigit];
                            }

                            //Draws red damage numbers if the heal is dealt to an enemy.
                            else if(((HealNumber)drawnTile.number).targetIsFriendly == false)
                            {
                                image = numR[oneDigit];
                            }

                            g.drawImage(image, i * tileLengthX - numCounter * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - numCounter * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, image.getWidth(null), image.getHeight(null), null);


                        }while(healDisplayed != 0);



                        //Using the timer for y coordinates allow it to float up.

                        //Draws corresponding plusses.
                        if(((HealNumber)(drawnTile.number)).targetIsFriendly)
                        {
                            g.drawImage(plusGr, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);
                        }

                        else if(!((HealNumber)drawnTile.number).targetIsFriendly)
                        {
                            g.drawImage(plusR, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minusR.getWidth(null), minusR.getHeight(null), null);
                        }

                        //Stop displaying number after some time.
                        if (drawnTile.number.timer <= 0)
                        {
                            drawnTile.number = null;
                        }    



                    }
                }
                //Floating gold numbers.
                //System.out.println("This is the drawnTile " + drawnTile);
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

                    }while(goldDisplayed != 0);
                    g.drawImage(plusG, i * tileLengthX - (numCounter + 1) * tileLengthX/4, (int)(j * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), (int)((i+.25) * tileLengthX - (numCounter + 1) * tileLengthX/4), (int)((.25+j) * tileLengthY - tileLengthY/2 + tileLengthY * ((double)drawnTile.number.timer/drawnTile.number.initialTime)), 0, 0, minus.getWidth(null), minus.getHeight(null), null);

                    if (drawnTile.number.timer <= 0)
                    {
                        drawnTile.number = null;
                        numberList.remove(drawnTile.number);
                    }

                }
            }
        }
        //Death animations. Very broken
        //for (DeadCharacter i : recentDeadCharList)
        for (int i = 0; i < recentDeadCharList.size(); i++)
        {
            /*
            if (recentDeadCharList.get(i).prevCharacter instanceof RandomJam)
            {
                Image slimeImage = null;
                slimeImage = DungeonMain.slimeImagesHit[recentDeadCharList.get(i).prevCharacter.direction];
                g.drawImage(slimeImage, recentDeadCharList.get(i).prevCharacter.currentTile.x * tileLengthX + 25, recentDeadCharList.get(i).prevCharacter.currentTile.y * tileLengthY + 25, (recentDeadCharList.get(i).prevCharacter.currentTile.x+1) * tileLengthX, (recentDeadCharList.get(i).prevCharacter.currentTile.y+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null); 
            }

            if (recentDeadCharList.get(i).prevCharacter instanceof CombatJam)
            {
                Image slimeImage = null;
                slimeImage = DungeonMain.slimeImagesHit[recentDeadCharList.get(i).prevCharacter.direction];
                g.drawImage(slimeImage, recentDeadCharList.get(i).prevCharacter.currentTile.x * tileLengthX + 25, recentDeadCharList.get(i).prevCharacter.currentTile.y * tileLengthY + 25, (recentDeadCharList.get(i).prevCharacter.currentTile.x+1) * tileLengthX, (recentDeadCharList.get(i).prevCharacter.currentTile.y+1) * tileLengthY, 0, 0, slimeImage.getWidth(null) + 50, slimeImage.getHeight(null) + 100, null); 
            }
             */

            BufferedImage deadImage = recentDeadCharList.get(i).prevCharacter.getImageHit();
            g.drawImage(deadImage, recentDeadCharList.get(i).prevCharacter.currentTile.x * tileLengthX, recentDeadCharList.get(i).prevCharacter.currentTile.y * tileLengthY, (recentDeadCharList.get(i).prevCharacter.currentTile.x+1) * tileLengthX, (recentDeadCharList.get(i).prevCharacter.currentTile.y+1) * tileLengthY, 0, 0, deadImage.getWidth(null), deadImage.getHeight(null), null); 


            if (recentDeadCharList.get(i).deathTimer <= 0)
            {
                recentDeadCharList.remove(i);
                i--;
            }
        }


        //Draw Projectiles
        for (int i = 0; i < projectileList.size(); i++)
        {
            if (!projectileList.get(i).isMoving)
            {
                //Within view
                if (Math.abs(projectileList.get(i).currentTile.x - dungeon.playerCharacter.currentTile.x ) < dungeon.playerCharacter.xVision && Math.abs(projectileList.get(i).currentTile.y - dungeon.playerCharacter.currentTile.y ) < dungeon.playerCharacter.yVision)
                {
                    image = projectileList.get(i).getImage(this);
                    //Not actually animating here, just want to easily get drawing coordinates
                    Coordinate coordinate = animateMovement(projectileList.get(i).currentTile, projectileList.get(i).currentTile, 1, 1, tileLengthX, tileLengthY);
                    g.drawImage(image, coordinate.x, coordinate.y, coordinate.x + tileLengthX, coordinate.y +  tileLengthY, 0, 0, image.getWidth(null), image.getHeight(null), null);
                    System.out.println("Stationary");
                }
                if (projectileList.get(i).isDestroyed)
                {
                    System.out.println("Projectile destroyed");
                    projectileList.remove(i);
                    //drawnTile.projectileList.remove(projectile);
                    i--;
                }
            }

            else
            {
                if (Math.abs(projectileList.get(i).currentTile.x - dungeon.playerCharacter.currentTile.x ) < dungeon.playerCharacter.xVision && Math.abs(projectileList.get(i).currentTile.y - dungeon.playerCharacter.currentTile.y ) < dungeon.playerCharacter.yVision || (Math.abs(projectileList.get(i).prevTile.x - dungeon.playerCharacter.currentTile.x ) < dungeon.playerCharacter.xVision && Math.abs(projectileList.get(i).prevTile.y - dungeon.playerCharacter.currentTile.y ) < dungeon.playerCharacter.yVision))
                {
                    System.out.println("Moving Projectile");
                    assert projectileList.get(i).isMoving;
                    Coordinate coordinate = animateMovement(projectileList.get(i).prevTile, projectileList.get(i).currentTile, projectileList.get(i).moveTimer, projectileList.get(i).currentMoveTimer, tileLengthX, tileLengthY);

                    image = projectileList.get(i).getImage(this);
                    g.drawImage(image, coordinate.x, coordinate.y, tileLengthX + coordinate.x, tileLengthY + coordinate.y, 0, 0, image.getWidth(null), image.getHeight(null), null);
                }
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

            //Testing
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

        //Draw the health bar. Split up into 3 cases. Near full health, middle health, near dead.
        //This finds the % health the player is currently at.
        double healthProportion = (double)dungeon.playerCharacter.currentHealth/dungeon.playerCharacter.maxHealth;
        //This determines the amount of health the ends take up. (this amount for each)
        double endPercent = .05;

        //This is the case where the player has nearly full health. 2 edges, right is depleting and a full middle
        if (healthProportion > (1-endPercent))
        {
            //This corresponds to the right edge. The right edge is depleted first. ((healthProportion-endPercent)/endPercent) forces it to be depleted over 5% health.
            g.drawImage(redBarRight, 34 * tileLengthX/4, 3 * tileLengthY/4, (int)(((healthProportion-(1-endPercent))/endPercent) * tileLengthX/4 + (34 * tileLengthX/4)), tileLengthY, 0, 0, (int) (((healthProportion-(1-endPercent))/endPercent)*redBarRight.getWidth(null)), redBarRight.getHeight(null),(null));
            g.drawImage(redBarMiddle, 26 * tileLengthX/4, 3 * tileLengthY/4, (int)(2 * tileLengthX + (26 * tileLengthX/4)), tileLengthY, 0, 0, redBarMiddle.getWidth(null), redBarMiddle.getHeight(null),(null));
            g.drawImage(redBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, 26 * tileLengthX/4, tileLengthY, 0, 0, redBarLeft.getWidth(null), redBarLeft.getHeight(null),(null));
        }

        //This is the middle case. Left edge and a depleting middle.
        else if(healthProportion <= 1-endPercent && healthProportion >= endPercent)
        {
            g.drawImage(redBarMiddle, 26 * tileLengthX/4, 3 * tileLengthY/4, (int)(2 * (healthProportion - endPercent)/(1-endPercent) * tileLengthX + (26 * tileLengthX/4)), tileLengthY, 0, 0, redBarMiddle.getWidth(null), redBarMiddle.getHeight(null),(null));
            g.drawImage(redBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, 26 * tileLengthX/4, tileLengthY, 0, 0, redBarLeft.getWidth(null), redBarLeft.getHeight(null),(null));
        }

        //This is the case where the player is almost dead. Depleting left edge.
        else if (healthProportion < endPercent)
        {
            g.drawImage(redBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, (int)((healthProportion/endPercent) * tileLengthX/4 + (25 * tileLengthX/4)), tileLengthY, 0, 0, (int)((healthProportion/endPercent)*redBarLeft.getWidth(null)), redBarLeft.getHeight(null),(null));
        }

        //Displaying overheal amounts using same previous principle.
        if (healthProportion > 1)
        {
            if (healthProportion > (2-endPercent))
            {
                //This corresponds to the right edge. The right edge is depleted first. ((healthProportion-endPercent)/endPercent) forces it to be depleted over 5% health.
                g.drawImage(greenBarRight, 34 * tileLengthX/4, 3 * tileLengthY/4, (int)(((healthProportion-(2-endPercent))/endPercent) * tileLengthX/4 + (34 * tileLengthX/4)), tileLengthY, 0, 0, (int) (((healthProportion-(2-endPercent))/endPercent)*redBarRight.getWidth(null)), redBarRight.getHeight(null),(null));
                g.drawImage(greenBarMiddle, 26 * tileLengthX/4, 3 * tileLengthY/4, (int)(2 * tileLengthX + (26 * tileLengthX/4)), tileLengthY, 0, 0, greenBarMiddle.getWidth(null), greenBarMiddle.getHeight(null),(null));
                g.drawImage(greenBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, 26 * tileLengthX/4, tileLengthY, 0, 0, greenBarLeft.getWidth(null), greenBarLeft.getHeight(null),(null));
            }

            //This is the middle case. Left edge and a depleting middle.
            else if(healthProportion <= (2-endPercent) && healthProportion >= 1 + endPercent)
            {
                g.drawImage(greenBarMiddle, 26 * tileLengthX/4, 3 * tileLengthY/4, (int)(2 * (healthProportion - 1 - endPercent)/(1-endPercent) * tileLengthX + (26 * tileLengthX/4)), tileLengthY, 0, 0, greenBarMiddle.getWidth(null), greenBarMiddle.getHeight(null),(null));
                g.drawImage(greenBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, 26 * tileLengthX/4, tileLengthY, 0, 0, greenBarLeft.getWidth(null), redBarLeft.getHeight(null),(null));
            }

            //This is the case where the player is almost dead. Depleting left edge.
            else if (healthProportion - 1 < endPercent)
            {
                g.drawImage(greenBarLeft, 25 * tileLengthX/4, 3 * tileLengthY/4, (int)(((healthProportion - 1)/endPercent) * tileLengthX/4 + (25 * tileLengthX/4)), tileLengthY, 0, 0, (int)(((healthProportion - 1)/endPercent)*greenBarLeft.getWidth(null)), greenBarLeft.getHeight(null),(null));
            }
        }
    }
    @Override
    public void run()
    {
        long previousTime, sleepTime, timeDifference;
        long counter = 0;
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

                if (i.isMoving == true)
                {
                    i.currentMoveTimer++;
                    if(i.currentMoveTimer >= i.moveTimer )
                    {
                        i.isMoving = false;
                        i.currentMoveTimer = 0;
                    }
                }
            }

            //Each projectile has an altTimer used for animations.
            for (Projectile p : projectileList)
            {
                if (counter % p.altTimer == 0)
                {
                    p.animationCounter++;
                }

                //Keep track of movement.
                if (p.isMoving)
                {
                    p.currentMoveTimer++;
                    if(p.currentMoveTimer >= p.moveTimer)
                    {
                        p.isMoving = false;
                        p.currentMoveTimer = 0;
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
            if(numberList.size() != 0)
            {
                for(Number j : numberList)
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

    /**
     * This class mostly just allows me to return x and y values.
     */
    private class Coordinate
    {
        int x;
        int y;

        public Coordinate(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}