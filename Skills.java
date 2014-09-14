import java.util.ArrayList;

/*
Skills
Attacks
Transforming
Passive
HP Crystal: Increases your HP
Speed Crystal: Increases your movement speed

Can be used as Trap?

1 Time Crystal: Allows you to freeze time to use your skills. The User Interface for using skills also changes so that it involves mouse movement.
2 Area Crystal: Transforms into the crystal of the dungeon.
3 Mimic Crystal: Transforms into a crystal that your opponent has at random
4 Random Crystal: Transforms into a random crystal (could be one that you already have)
5 Teleport Crystal: Allows you to teleport within the dungeon.
6 Slicer Crystal: Allows you to deal one extremely powerful blow
7 Summoner Crystal: Allows you to create your own monsters to assist you in battle
8 Dash Crystal: Dash
9 Block Crystal: Protects you from one hit
10 Escape Crystal: Allows you to escape the dungeon at a small cost
11 Meteor Crystal: Summons a meteor and lands on enemy at random
12 Push Crystal: Push enemy back.
13 Poison Crystal: Damage over time. 
14 Elemental Crystal: Shoots a random burst of energy from an element.
15 Poison Fog: Floods room with poison. Including you.
16 Flood: Floods the entire level with water. Certain skills (such as Freeze, Electricity, Fire -> Steam) will result in more powerful moves. Movement is slowed as well unless one has the Float crystal.
17 Electrocution: Sends a wave of electricity. If water is around, it will follow the path.
18 Flash Freeze: Covers the whole room in a blizzard, temporarily lowering vision while doing some damage. If done on a water zone, enemy(s) will be frozen as well.
19 Fire Ball: Launches a bouncing flame that grows bigger with every bounce. If on grass/combustible material, the whole dungeon will start being engulfed in flames. If done on water, the fire ball will die quicker (depending on strength of fire ball).
20: Flash Fire: Requires charging. Incenerates the whole room. If done in a room with water, damage is weakened, but creates steam.
21: Stun Spore: Throws a bag of spores that temporarily confuses/stuns the enemy. Covers a significant amount of range.
22: Shadow Swipe: Pushes and stuns your opponent. While your opponent is stunned, try smashing your buttons as quickly as possible to strike LOTS of times.
23: Water Absorption: While in water, regenerate your health every few seconds. If bathed in steam, regenerate your health quicker.
24: Levitation: Allows you to float. You become immune to any trap triggers/ground based moves
25: QUAD FIREBALL: SHOOT FOUR FIREBALLS IN AN X PATTERN
26: Invisibility: Visually invisible to monsters who use sight to navigate. Invisibility % is based on how still you remain.
27: Circle Destruction: Creates a path of yellow-orange light that acts as solid barrier. If the end and beginning are connected, every tile within the barrier explodes.
28: Bomb Rock: Throws a rock at one tile, runs away, explode
29: Phasor: Walk through walls and other solid objects
30: Ghost Transform: Possess monsters and use their HP as yours. You also gain their skills (if any)
31: 

Different bosses have different "modes":
Anais: Chess - game becomes turn based - every turn you roll to become a different piece. Anais is only damage-able when she becomes a Queen Piece.
*/

//All skills are their own class; when created they will run and then stop. Used so that they can be placed in an array of skills.
public abstract class Skills
{
    String description = "This is a skill";
    int damage = 0; //Negative for heals, positive for damage.
    
    /*
    public static void useSkill(String skillName)
    {
        if (skillName.equals("fireball"))
        {
            
        }
    }
    */
    
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    {
        System.out.println("Error");
    }
    
    //Gets the tile in front of the sourceCharacter.
    public static DungeonTile getSourceTile(DungeonMain lDungeon, int direction, DungeonTile tile)
    {
      //If bash for direction
        int targetX = tile.x;
        int targetY = tile.y;
        
        if(direction == 6)
        {
            targetX += 1; 
        }
        
        //North
        else if(direction == 8)
        {
            targetY -= 1;
        }
        
        //West
        else if(direction == 4)
        {
            targetX -= 1;
        }
        
        //South
        else if(direction == 2)
        {
            targetY += 1;
        }
        
        else if(direction == 9)
        {
            targetY -= 1;
            targetX += 1;
        }
        
        else if(direction == 7)
        {
            targetY -= 1;
            targetX -= 1;
        }
        
        else if(direction == 1)
        {
            targetY += 1;
            targetX -= 1;
        }
        
        else if(direction == 3)
        {
            targetY += 1;
            targetX += 1;
        }
        
        if (targetX  >= 0 && targetX < DungeonBuilder.xLength && targetY >= 0 && targetY < DungeonBuilder.yLength && lDungeon.dungeon.tileList[targetX][targetY] instanceof DungeonTile)
        {
            return lDungeon.dungeon.tileList[targetX][targetY];
        }
        else
        {
            return null;
        }
        
    }
    
    /**
     * All stuff to be run after using a skill
     * @param lDungeon
     * @param sourceCharacter
     */
    public static void skillHelper(DungeonMain lDungeon, Character sourceCharacter)
    {
        //Is enemy turn is true when source is friendly. It is false when source is unfriendly.
        lDungeon.isEnemyTurn = sourceCharacter.isFriendly;
        System.out.println("Enemy turn: " + lDungeon.isEnemyTurn);
        
        //Put this skill on cooldown.
        
    }
}
    
    /* Fun error: Moves you with projectile, killing things in way. Then softlocks. Moves the tile coordinates as well
    public void QuadFireball(DungeonMain lDungeon, Character sourceCharacter)
    {
        description = "Launches 4 fireballs in an X pattern. More is better";
        System.out.println("Boosh");
        
        DungeonTile startTile = sourceCharacter.currentTile;
        
        lDungeon.ProjectileList.add(new Projectile (5, sourceCharacter.direction, getSourceTile(sourceCharacter.direction, startTile)));
        
        //Is enemy turn is true when source is friendly. It is false when source is unfriendly.
        lDungeon.isEnemyTurn = sourceCharacter.isFriendly;
        System.out.println("Enemy turn: " + lDungeon.isEnemyTurn);
        System.out.println(lDungeon.dungeon.playerCharacter.currentTile.x + " " + lDungeon.dungeon.playerCharacter.currentTile.y);
    }
    */
    
class FireballCommand extends Skills
{
    String description = "Launches 4 fireballs in an X pattern. More is better";
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    {
        System.out.println("Boosh");
        
        DungeonTile startTile = lDungeon.dungeon.tileList[sourceCharacter.currentTile.x][sourceCharacter.currentTile.y];
        
        lDungeon.projectileList.add(new Fireball (5, sourceCharacter.direction, 5, startTile, sourceCharacter));
        
        skillHelper(lDungeon, sourceCharacter);
    }
}

//Gives you health. OVERHEAL.
class HealCommand extends Skills
{
    int healAmount;
    public HealCommand(int healAmount)
    {
        this.healAmount = healAmount;
    }
    String description = "Gives you some health. If you heal above 100%, then the extra points will decay over turns. 10% of max health per turn. Max overheal is 200%";
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    {
        System.out.println("whoosh");
        sourceCharacter.heal(healAmount, sourceCharacter, lDungeon);
        
        skillHelper(lDungeon, sourceCharacter);
    }
}

class ReviveCommand extends Skills
{
    String description = "Revives a dead creature to fight for you";
    double healPercent = 0;
    
    public ReviveCommand(double healPercent)
    {
        this.healPercent = healPercent;
    }
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    { 
        System.out.println("pow");
        
        DungeonTile targetTile = getSourceTile(lDungeon, sourceCharacter.direction, sourceCharacter.currentTile);
        if (targetTile instanceof DungeonTile && !(targetTile.deadCharTileList.isEmpty()))
        {
            sourceCharacter.revive(healPercent, targetTile.deadCharTileList.get(0), lDungeon);
        }
        skillHelper(lDungeon, sourceCharacter);
    }
}

class RandomTeleportCommand extends Skills
{
    int range;
    public RandomTeleportCommand(int range)
    {
        this.range = range;
    }
    public void use(DungeonMain lDungeon, Character targetCharacter)
    {
        //Get an arraylist of the tiles within range of this characte.
        int centerX = targetCharacter.currentTile.x;
        int centerY = targetCharacter.currentTile.y;
        ArrayList<DungeonTile> tempTileList = new ArrayList<DungeonTile>();
        
        for (int i = centerX - range; i < centerX + range; i++)
        {
            for (int j = centerY - range; j < centerY + range; j++)
            {
                if (lDungeon.dungeon.tileChecker(i,j,true))
                {
                    tempTileList.add(lDungeon.dungeon.tileList[i][j]);
                }
            }
        }
        
        
        DungeonTile chosen = tempTileList.get((int) (Math.random()*tempTileList.size()));
        targetCharacter.charMove(chosen, lDungeon.dungeon);
        targetCharacter.activateArea(lDungeon.dungeon, lDungeon.dungeon.playerCharacter.xVision, lDungeon.dungeon.playerCharacter.yVision);
    
        skillHelper(lDungeon, targetCharacter);
    }
}

class TeleballCommand extends Skills
{
    int teleRange = 0;
    public TeleballCommand(int teleRange)
    {
        this.teleRange = teleRange;
    }
    
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    {
        description = "Launches a slowmoving, transporting projectile. Useful for getting allies out of tough situations and moving enemies away for yourself.";
        System.out.println("Boosh");
        
        DungeonTile startTile = lDungeon.dungeon.tileList[sourceCharacter.currentTile.x][sourceCharacter.currentTile.y];
        
        lDungeon.projectileList.add(new TeleBall (2, sourceCharacter.direction, 5, startTile, sourceCharacter, teleRange));
        
        skillHelper(lDungeon, sourceCharacter);
    }
}

class IcePathCommand extends Skills
{
    int range;
    public IcePathCommand(int range)
    {
        this.range = range;
    }
    public void use(DungeonMain lDungeon, Character sourceCharacter)
    {
   
    }
}