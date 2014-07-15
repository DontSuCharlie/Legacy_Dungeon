import java.util.Comparator;


public class GameItem implements Comparable<GameItem>{
    //Selling items dealt with by a HashMap with Key as the object and the value as its worth. This is only handled by the seller.
    //No weight. Just max items.
    public final Comparator<GameItem> ARMOR_ORDER = new Armor_Order();
    
    /**
     * This is merely an idea for how to implement using items. 
     * I don't want to have to add a number of items for all items (e.g holding multiple projectiles). Just consumables should be good.
     * @param sourceCharacter
     */
    public void use(Character sourceCharacter, DungeonMain lDungeon)
    {
        sourceCharacter.charInventory.itemList.remove(this);
        System.out.println("Error");
    }

    @Override
    public int compareTo(GameItem other)
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    private class Armor_Order implements Comparator<GameItem>
    {

        @Override
        public int compare(GameItem o1, GameItem o2)
        {
            // TODO Auto-generated method stub
            return 0;
        }
        
    }
    
}
