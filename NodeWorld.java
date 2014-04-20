import java.awt.*;//not sure
import java.awt.event.*;//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
import java.awt.image.*;//image class allows for storage of image files
import javax.swing.*;//part of UI
import javax.imageio.*;//handles the input/output of IMAGE files
import java.io.*;//handles input/output of files (ie: reads files)
import java.util.*;

public class NodeWorld{
    //Fields
    
    int xPos;
    int yPos;
    int skillID; //See list of IDs in Skills.java
    int nodeID; //0 == Dungeon, 1 == Captured node, 2 == Contested node, 3 == Lost node
    int theme; // 0 == Ice, 1 == Cave, 2 == Castle, 3 == Jungle, 4 == Ocean
    int difficulty; // Linear increase in dungeon
    
    
    public NodeWorld(){
    
    nodeID = 0;
    theme = 0;
    skillID = 0;
    xPos = 0;
    xPos = 0;
    difficulty = 0;
    
    }
    
    

 /*   if nodeID == 0 
    {
    

    }
*/
    public void GenerateNodeWorld(ArrayList<Integer> xOccupied, ArrayList<Integer> yOccupied)
    {
        boolean goodPlacement = false;
        while (!goodPlacement)
        {
            xPos = (int) (Math.random() * 2000);
            yPos = (int) (Math.random() * 1500);
            int xListSize = xOccupied.size();
            int yListSize = yOccupied.size();
            int xSpacing = 5;
            int ySpacing = 5;
            if (xListSize == 0);
            {
                xOccupied.add(1000 /*Width/2*/);
                yOccupied.add(750 /*Height/2*/);
            }
            
            for (int i = 0; i < xListSize; i++)
            {
                // Checking a range if far enough from other nodes. Basically, the range from (xPos - 20) to (xPos + 20) must be both less or both greater than the other nodes available.
                if (((xPos + xSpacing) < xOccupied.get(i) && (xPos - xSpacing) < xOccupied.get(i)) || ((xPos + xSpacing) > xOccupied.get(i) && (xPos - xSpacing) > xOccupied.get(i)) || 
                    ((yPos + ySpacing) < yOccupied.get(i) && (yPos - ySpacing) < yOccupied.get(i)) || ((yPos + ySpacing) > yOccupied.get(i) && (yPos - ySpacing) > yOccupied.get(i)))
                {
                    xOccupied.add(xPos);
                    yOccupied.add(yPos);
                    goodPlacement = true;
                }

            }
        }
    }
}