//Compilation of methods that are no longer used. Archived for 
public class dumpedMethods
{
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