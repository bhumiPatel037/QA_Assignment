public class Canada implements ICountryList
{
	 private String countryName="Canada";

	public String getAgriculture()
	{
		return "$50000000 CAD";
	}

	public String getManufacturing()
	{
		return "$100000 CAD";
	}

	@Override
	public String getCountryName() {
		return countryName;
	}

	@Override
	public void getCountryGDPReportDeatils()
	{
		System.out.println("   - Agriculture: " + getAgriculture());
		System.out.println("   - Manufacturing: " + getManufacturing());
	}
}