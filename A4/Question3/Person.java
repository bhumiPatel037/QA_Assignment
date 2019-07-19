public class Person
{
	public String name;

	public Person()
	{
		name = "Rob";
	}

	public boolean isPersonRob()
	{
		Address address = new Address();
		address.setCity("Rob city");
		address.setPostalCode("Rob postalcode");
		address.setProvince("Rob province");
		address.setStreet("Rob street");

		return name.equals("Rob") && isRobsAddress(address);
	}

	private boolean isRobsAddress(Address address)
	{
		return address.getStreet().equals("Rob street") &&
			address.getCity().equals("Rob city") &&
			address.getProvince().equals("Rob province") &&
			address.getPostalCode().equals("Rob postalcode");
	}
}
