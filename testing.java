import java.util.ArrayList;
public class testing
{
	public static void main(String[] args)
	{
		ArrayList<Integer> woof = new ArrayList<Integer>();
		woof.add(2);
		System.out.println(woof.get(0));
		testMethod(woof);
		for(int i = 0; i < woof.size(); i++)
		{
			System.out.println(woof.get(i));
		}
	}
	
	public static void testMethod(ArrayList<Integer> meow)
	{
		meow.add(1);
		meow.add(0);
	}
}