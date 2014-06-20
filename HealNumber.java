//Theoretically, could just use negative hitNumbers, but this is more clear.
public class HealNumber extends Number{
    boolean targetIsFriendly;
    public HealNumber(int inputDamage, int xInput, int yInput, boolean isFriendly)
    {
        super(inputDamage, xInput, yInput);
        targetIsFriendly = isFriendly;
    }
}
