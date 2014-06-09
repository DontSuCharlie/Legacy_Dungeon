//Perhaps unneeded?
public class HitNumber extends Number{
    boolean targetIsFriendly;

    public HitNumber(int inputDamage, int xInput, int yInput, boolean isFriendly)
    {
        super(inputDamage, xInput, yInput);
        targetIsFriendly = isFriendly;
    }
}
