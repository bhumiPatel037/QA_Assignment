import java.util.ArrayList;
public class MainTest {

    public static void main(String[] args)
    {
        ArrayList<Iworker> iworkers = new ArrayList<Iworker>();
        for (int i = 0; i < 5; i++)
        {
            iworkers.add(new HourlyWorker());
        }
        for (int i = 0; i < 5; i++)
        {
            iworkers.add(new SalaryWorker());
        }
        Employer employer= new Employer(iworkers);
        employer.outputWageCostsForAllStaff(10);
    }
}
