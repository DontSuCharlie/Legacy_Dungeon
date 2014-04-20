public class NodeWorld{
    //Fields
    double xPos;
    double yPos;

    int skillID; //See list of IDs in Skills.java
    int nodeID; //0 == Dungeon, 1 == Captured node, 2 == Contested node, 3 == Lost node
    int theme; // 0 == Ice, 1 == Cave, 2 == Castle, 3 == Jungle, 4 == Ocean
    int difficulty; // Linear increase in dungeon

    if nodeID == 0 
    {
    

    }

    public ArrayList GenerateNodeWorld(ArrayList xOccupied, ArrayList yOccupied)
    {
        int xPos
        boolean goodPlacement = false;
        while (!goodPlacement)
        {
            xPos = (int) (Math.random() * windowX);
            yPos = (int) (Math.random() * windowY);
            
            int xListSize = xOccupied.size();
            int yListSize = yOccupied.size();
            int xSpacing = 20;
            int ySpacing = 20;
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
        
        return x
    }
}