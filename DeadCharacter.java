
public class DeadCharacter{

    Character prevCharacter;
    boolean justDied;
    int deathTimer = 250/DungeonMain.DELAY;
    
    public DeadCharacter(Character character)
    {
        prevCharacter = character;
        justDied = true;
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
                + ((prevCharacter == null) ? 0 : prevCharacter.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * Only checks if the characters are equal. Used for removing the revived character from deadCharList.
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
        if (!(obj instanceof DeadCharacter))
        {
            return false;
        }
        DeadCharacter other = (DeadCharacter) obj;
        if (prevCharacter == null)
        {
            if (other.prevCharacter != null)
            {
                return false;
            }
        }
        else if (!prevCharacter.equals(other.prevCharacter))
        {
            return false;
        }
        return true;
    }
}
