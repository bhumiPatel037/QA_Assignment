// Building is the leaf node for the composite pattern, Square's can have MULTIPLE Buildings
// Buildings cannot have children.
public class Building extends BoardComponent
{
	private int buildingHealth;
	
	public Building()
	{
		super();
		this.buildingHealth = 2;
	}

	@Override
	public void Operation()
	{
		// Buildings just stand there, they don't do anything.
	}

	@Override
	public void Add(BoardComponent child)
	{
		// Do nothing, I'm a leaf.
	}

	@Override
	public void Remove(BoardComponent child)
	{
		// Do nothing, I'm a leaf.
	}

	@Override
	public void asteroidImpact(Square targetedSquare,int index) {

		if (targetedSquare ==((Square) parent))
		{
			this.buildingHealth = this.buildingHealth - 1;
			if (this.buildingHealth == 0)
			{
				parent.Remove(this);

				//decrement building count after building health reached 0
				GameBoard.Instance().DecrementBuildingCount();

			}

		}

	}
}
