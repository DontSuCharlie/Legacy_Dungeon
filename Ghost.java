import java.awt.image.BufferedImage;


public class Ghost extends Enemy{

    private int runTimer = 0;
    private int cooldownTimer1Max = 10; //Time to charge abilities. Reduced by one each turn.
    public int cooldownTimer1 = cooldownTimer1Max;//Deals with cooldowns for the first ability of this creature. 

    public Ghost()
    {
        description = "This is a lost spirit seeking revenge for long forgotten"
                + "crimes. It was likely awakened when the ship crash landed."
                + "They are able to revive other dead, a bit sad considering"
                + "their position. You can hit them to make them run away.";
        //characterID = 2;
        maxHealth = 10;
        currentHealth = 10;
        IS_RUNNING = true;
        direction = 2;


    }

    public BufferedImage getImage()
    {
        if (this.imageID == 0 && this.isHit == false)
        {
            return DungeonMain.ghostImages[direction];
        }

        //Jam alt. image
        else if(this.imageID == 1 && this.isHit == false)
        {
            return DungeonMain.ghostImagesAlt[direction];
        }

        else if(this.isHit == true)
        {
            return DungeonMain.ghostImagesHit[direction];
        }

        else 
        {
            //Should not run.
            System.out.println("Error missing ghost image");
            return DungeonMain.ghostImages[6];
        }
    }
    
    /**
     * Mostly just test usage. Trying to see what looks good.
     * @return
     */
    public BufferedImage getImageHit()
    {
        return DungeonMain.ghostImagesHit[direction];
    }

    public BufferedImage getImageDead()
    {
        return DungeonMain.ghostImagesDead[direction];
    }
    
    public void endTurn(DungeonMain lDungeon)
    {
        super.endTurn(lDungeon);
        cooldownTimer1--;
    }

    public void resetCooldown()
    {
        cooldownTimer1 = cooldownTimer1Max;
    }
    /**
     * The ghost revives the closest thing. If it is hit, it runs away from the aggressor for a time dependent on the amount of health it has.
     * If none of these are true, it just runs around randomly.
     * Weird structure, hope it makes sense.
     */
    public void act(DungeonMain lDungeon)
    {
        final int RUN_TIME = 10;//Runs for 10 turns.
        //If hit, try to run away. Amount of running depends on proportion of health.
        //First, if hit then plan run.
        if(this.wasHit)
        {
            runTimer = (RUN_TIME * this.currentHealth / this.maxHealth);
            this.wasHit = false;
        }

        //If hit, then run.
        if (runTimer != 0)
        {
            AIRun(lDungeon, storedTargetCharacter);
            runTimer--;
            
            //Sloppy way to take care of post-running, should fix later.
            if (runTimer == 0)
            {
            	storedTargetCharacter = null;
            }
        }

        //Try to move to heal someone. If that fails, then move randomly.
        //Bah, too much if-bashing.
        else
        {
            if (this.AIReviver(lDungeon, (cooldownTimer1 <= 0)))
            {
                resetCooldown();
            }
            
            else if (!(storedTargetCharacter instanceof Character))
            {
                AIRandom(lDungeon);
            }
        }
        endTurn(lDungeon);

    }
}
