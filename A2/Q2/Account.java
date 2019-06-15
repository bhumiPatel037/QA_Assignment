public abstract class Account
{
	protected float balance;

	public float getBalance()
	{
		return balance;
	}

	abstract void credit(float amount);

	abstract public void debit(float amount);

}