import java.awt.image.BufferedImage;


public class Ghost extends Enemy{

    private int runTimer = 0;
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
     * The ghost revives the closest thing. If it is hit, it runs away from the aggressor for a time dependent on the amount of health it has.
     * If none of these are true, it just runs around randomly.
     */
        public void act(DungeonMain lDungeon)
        {
            final int RUN_TIME = 10;//Runs for 10 turns.
            //If hit, try to run away.
            if(this.wasHit)
            {
                runTimer = (RUN_TIME * this.currentHealth / this.maxHealth);
                this.wasHit = false;
            }

            if (runTimer != 0)
            {
                AIRun(lDungeon, storedTargetCharacter);
                runTimer--;
            }
            
            else if (!(lDungeon.deadCharList.isEmpty()))
            {
                this.AIReviver(lDungeon);
            }
            
            else
            {
                AIRandom(lDungeon);
            }
            endTurn(lDungeon);
        }
}
