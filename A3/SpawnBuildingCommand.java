public class SpawnBuildingCommand extends Command{

    public SpawnBuildingCommand(Object receiver, String[] args)
    {
        super(receiver, args);
    }
    @Override
    public void Execute() {

        // The receiver for the SpawnBuildingCommand is the Square to spawn the building in.
        Square square = (Square) receiver;
        IAsteroidGameFactory factory = GameBoard.Instance().GetFactory();
        System.out.println("Spawning building at (" + args[0] + "," + args[1]+")");
        square.Add(factory.MakeBuilding());

        //increment the building count after making building in square.
        GameBoard.Instance().IncrementBuildingCount();

    }
}
