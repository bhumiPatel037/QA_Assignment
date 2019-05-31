// YOU ARE ALLOWED TO MODIFY THIS FILE
public interface IDatabase
{
  //Return drugCount of valid drug
  public Integer drugCount(String drugName);

  // Return True= if drug is exist in system , false=if drug does not exist in system.
  public Boolean isDrugExist(String drugName);

  //return true= if valid drug has enough quantity , false=if drug count is insufficient
  public boolean claimDrug(String drugName,Integer quantity);

}