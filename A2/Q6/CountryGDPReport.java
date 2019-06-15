import java.util.ArrayList;

public class CountryGDPReport
{

	ICountryList[] iCountryLists;
	public CountryGDPReport(ICountryList[] iCountryLists)
	{
         this.iCountryLists=iCountryLists;
	}
	public void printCountryGDPReport()
	{
		System.out.println("GDP By Country:\n");
		for (int i = 0; i < iCountryLists.length; i++)
		{
			System.out.println(iCountryLists[i].getCountryName());
			iCountryLists[i].getCountryGDPReportDeatils();
		}
	}

}