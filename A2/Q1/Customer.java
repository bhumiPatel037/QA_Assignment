public class Customer
{

	private String country;
	private String province;
	private String email;

	public Customer(String country,String province,String email)
	{
		this.country = country;
		this.province = province;
		this.email=email;
	}

	public boolean isCanadian()
	{
		return country.equals("Canada");
	}

	public boolean isNovaScotian()
	{
		return province.equals("Nova Scotia");
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}