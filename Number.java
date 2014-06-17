public class Number
{
	int amount;
	int timer;
	int x;
	int y;
	int initialTime;
	public Number(int inputAmount, int xInput, int yInput)
	{
		amount = inputAmount;
		int secondsLiving = 2;
		timer = (1000 * secondsLiving)/DungeonMain.DELAY;
		initialTime = timer;
		x = xInput;
		y = yInput;
	}
}
