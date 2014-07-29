import java.awt.image.BufferedImage;
import java.util.Comparator;


public class GameItem implements Comparable<GameItem>{
    
    int rarity; //1 is common. 2 is uncommon. 3 is  rare,  etc.
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
    
    public BufferedImage getImage(DungeonMain lDungeon)
    {
        System.out.println("Error: getImage not initialized properly");
        return null;
    }

    @Override
    /**
     * This allows for a sort prioritizing rarity. Used to display rarest item first.
     * 
     */
    public int compareTo(GameItem other)
    {
        return (other.rarity - this.rarity);
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
