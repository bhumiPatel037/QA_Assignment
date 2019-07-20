public class Person
{
	private String name;



     PersonAuthentication personAuthentication = new PersonAuthentication();
     PersonContactDetails personContactDetails= new PersonContactDetails();

	public Person(String name)
	{
		this.name = name;
	}

	public void setAreaCode(String areaCode)
	{
		personContactDetails.setAreaCode(areaCode);
	}
	public String getAreaCode()
	{
		return  personContactDetails.getAreaCode();
	}
	public void setPhoneNumber(String phoneNumber)
	{
		personContactDetails.setPhoneNumber(phoneNumber);
	}
	public String getPhoneNumber()
	{
		return personContactDetails.getPhoneNumber();
	}

	public void setLoginCredentials(String userName, String password)
	{
		personAuthentication.setLoginCredentials(userName,password);
	}
	public boolean authenticateUser()
	{
		return personAuthentication.authenticateUser();
	}
}
