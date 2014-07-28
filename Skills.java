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
public class Skills
{
    String description = "This is a skill";
    int damage = 0; //Negative for heals, positive for damage.
    
    //Gets the tile in front of the sourceCharacter.
    public DungeonTile getSourceTile(DungeonMain lDungeon, int direction, DungeonTile tile)
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
    
    //All stuff to be run after using a skill
    private void skillHelper(DungeonMain lDungeon, Character sourceCharacter)
    {
        //Is enemy turn is true when source is friendly. It is false when source is unfriendly.
        lDungeon.isEnemyTurn = sourceCharacter.isFriendly;
        System.out.println("Enemy turn: " + lDungeon.isEnemyTurn);
        
        //Put this skill on cooldown.
        
    }
    
    /* Fun error: Moves you with projectile, killing things in way. Then softlocks
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
    
    public void Fireball(DungeonMain lDungeon, Character sourceCharacter)
    {
        description = "Launches 4 fireballs in an X pattern. More is better";
        System.out.println("Boosh");
        
        DungeonTile startTile = lDungeon.dungeon.tileList[sourceCharacter.currentTile.x][sourceCharacter.currentTile.y];
        
        lDungeon.ProjectileList.add(new Projectile (5, sourceCharacter.direction, getSourceTile(lDungeon, sourceCharacter.direction, startTile)));
        
        skillHelper(lDungeon, sourceCharacter);
    }
    
    //Gives you health. Please make cooler. OVERHEAL.
    public void Heal(DungeonMain lDungeon, Character sourceCharacter)
    {
        int healAmount = 30;
        description = "Gives you " + healAmount +" health. If you heal above 100%, then the extra points will decay over turns." + lDungeon.OVERHEAL_DECAY_PERCENT + "& of max health per turn. Max overheal is 200%";
        System.out.println("whoosh");
        sourceCharacter.heal(healAmount, sourceCharacter, lDungeon);
        
        skillHelper(lDungeon, sourceCharacter);
    }
    
    
    public void revive(DungeonMain lDungeon, Character sourceCharacter)
    {
        double healAmount = .25;
        System.out.println("pow");
        description = "Revives a dead creature to fight for you";
        DungeonTile targetTile = getSourceTile(lDungeon, sourceCharacter.direction, sourceCharacter.currentTile);
        if (targetTile instanceof DungeonTile && targetTile.deadCharacter instanceof DeadCharacter)
        {
            sourceCharacter.revive(healAmount, targetTile.deadCharacter, lDungeon);
        }
        skillHelper(lDungeon, sourceCharacter);
        
        
    }
    
}