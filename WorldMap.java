//import Dungeon.java;
import NodeWorld.java;

public class WorldMap extends JPanel
{

public static ArrayList<Integer> xOccupied = new ArrayList<Integer>();
public static ArrayList<Integer> yOccupied = new ArrayList<Integer>();
public static ArrayList<Integer> nodeIDList = new ArrayList<Integer>();
public ArrayList<NodeWorld> nodeWorldList = new ArrayList<NodeWorld>();


    public WorldMap(){
        
        int numDungeons = (int) (Math.random() * 20) + 20
        
        for (int i = 0; i < numDungeons ; i++)
        { 
            nodeWorldList.add(i) = new NodeWorld();
            nodeWorldList.i.GenerateNodeWorld;
            system.out.println(nodeWorldList.i.xPos
        }
        
        
        
        
    }
    //Fields
    int turnNumber
    BufferedImage image;
    /*
  
    heart
  
    nodes
  
    make lines
  
    enemy movement
  
    load dungeon
    */
    public void Run(){
    
        frame = new JFrame("Legacy Dungeon: World Map");
        
        animation();
        movement();
        
    
    
    }
    

    Dungeon.main(
    //Constructor
    //Methods

    public class Movement(){
        
        turnNumber += 1
    
    
    }
    
}