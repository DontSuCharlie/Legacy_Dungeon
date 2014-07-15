/**
 * A test for consumables and items. This boring item merely restores 30 health.
 * 
 *
 */
public class HealthPot extends Consumable {

    int healAmount = 30;
    
    public void use(Character sourceCharacter, DungeonMain lDungeon)
    {
        super.use(sourceCharacter, lDungeon);
        sourceCharacter.heal(healAmount, sourceCharacter, lDungeon);
    }
}
