public class Student
{
	String bannerID;
	String firstName;
	String lastName;
	String email;

	public Student()
	{
		bannerID = null;
		firstName = null;
		lastName = null;
		email = null;
	}
	public Student(String bannerID,String firstName, String lastName,String email)
	{
		this.bannerID = bannerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public void setBannerID(String bannerID)
	{
		this.bannerID = bannerID;
	}

	public String getBannerID() {
		return bannerID;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}