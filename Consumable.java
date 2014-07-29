/**
 * This item is one that is removed on use. This may be throwing a projectile, drinking a potion or some stuff like that.
 *
 */
public class Consumable extends GameItem {

    int stackSize = 1; //Amount you are carrying in a slot.
    int maxStackSize; //Max amount that can be carried in a slot.
    
    /**
     * This deals with what is done for every consumable.
     * Reduces one from this stack and removes it if there are none left.
     */
    public void use(Character sourceCharacter, DungeonMain lDungeon)
    {
        ((Consumable)sourceCharacter.charInventory.itemList.get(sourceCharacter.charInventory.itemList.indexOf(this))).stackSize -= 1;
        System.out.println("Consumable used");
        
        if (stackSize <= 0)
        {
            sourceCharacter.charInventory.itemList.remove(this);
            System.out.println("Stack exhausted");
        }
    }
}
