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
    int currentSize = 0;
    ArrayList<GameItem> itemList = new ArrayList<GameItem>();
    ArrayList<GameItem> displayedItems = new ArrayList<GameItem>();
    public Inventory(int maxSize)
    {
       // Collections.sort(itemList, );
    }
    
    public void sortArmor()
    {        
        //Collections.sort(itemList, );
    }
    public void sortConsumable()
    {
        
    }
    
    public void sortProjectile()
    {
        
    }
}
