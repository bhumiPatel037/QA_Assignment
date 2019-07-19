public class Calculator
{
	public static int divide(int left, int right)
	{
		MathOperation op = new Divide();
		return op.getResult(left,right);
	}

	public static int multiply(int left, int right)
	{
		MathOperation op = new Multiply();
		return op.getResult(left,right);
	}

	public static int add(int left, int right)
	{
		MathOperation op = new Add();
		return op.getResult(left,right);
	}

	public static int subtract(int left, int right)
	{
		MathOperation op = new Subtract();
		return op.getResult(left,right);
	}
}
