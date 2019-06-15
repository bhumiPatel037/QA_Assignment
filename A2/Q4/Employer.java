import java.util.ArrayList;

public class Employer
{
	ArrayList<Iworker> iworkers;


	public Employer(ArrayList<Iworker> iworkers)
	{
		this.iworkers=iworkers;
	}

	public void outputWageCostsForAllStaff(int hours)
	{
		float cost = 0.0f;
		for (int i = 0; i < iworkers.size(); i++)
		{
			Iworker worker = iworkers.get(i);
			cost += worker.calculatePay(hours);
		}
		System.out.println("Total wage cost for all staff = $" + cost);
	}
}