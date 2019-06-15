public class Mexico implements ICountryList
{
	private String countryName="Mexico";
	public String getAgriculture()
	{
		return "$50000000 MXN";
	}

	public String getTourism()
	{
		return "$100000 MXN";
	}

	@Override
	public String getCountryName() {
		return countryName;
	}

	@Override
	public void getCountryGDPReportDeatils()
	{
		System.out.println("   - Agriculture: " + getAgriculture());
		System.out.println("   - Tourism: " + getTourism());
	}
}