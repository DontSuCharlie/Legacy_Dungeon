import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * Every character has a distinct inventory to store stuff.
 * Multiple sorts for easy display. Time received, Armor only, consumables only, 
 * 
 * 
 *
 */
public class Inventory{

    int maxSize;
    //int currentSize = 0; Use size of itemList instead. Could not change from within items, eg. using a consumable.
    ArrayList<GameItem> itemList = new ArrayList<GameItem>();
    ArrayList<GameItem> displayedItems = new ArrayList<GameItem>();
    
    public Inventory(int maxSize)
    {
        this.maxSize = maxSize;
       // Collections.sort(itemList, );
    }
    
    public void sortArmor()
    {
        
       // Collections.sort(itemList, );

    }
    
    public void sortConsumable()
    {
        
    }
    
    public void sortProjectile()
    {
        
    }

    /**
     * This method adds the tile's stuff to the invoking character's inventory.
     * The order should already be sorted, as this simply takes everything in order.
     * If full, then it makes the tile's item arrayList null and removes the icon.
     * @param currentTile
     */
    public void add(DungeonTile currentTile)
    {
        for (int i = 0; i < currentTile.items.size(); ++i)
        {
            if (!(itemList.size() >= maxSize))
            {
                itemList.add(currentTile.items.get(i));
                currentTile.items.remove(i);
                --i;
                
                //Show item was received
                HitNumber temp = new HitNumber(5, currentTile.x, currentTile.y, true);
                currentTile.number = temp;
                DungeonMain.NumberList.add(temp);
            }
        }
        
        //All items taken
        if (currentTile.items.size() == 0)
        {
            currentTile.items = null;
            currentTile.itemID = 0;
        }
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((itemList == null) ? 0 : itemList.hashCode());
        result = prime * result + maxSize;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Inventory))
        {
            return false;
        }
        Inventory other = (Inventory) obj;
        if (itemList == null)
        {
            if (other.itemList != null)
            {
                return false;
            }
        }
        else if (!itemList.equals(other.itemList))
        {
            return false;
        }
        if (maxSize != other.maxSize)
        {
            return false;
        }
        return true;
    }
}
