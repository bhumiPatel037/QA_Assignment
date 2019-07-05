public class SpawnShieldCommand extends Command{

    public SpawnShieldCommand(Object receiver, String[] args)
    {
        super(receiver, args);
    }

    @Override
    public void Execute() {
        // The receiver for the SpawnBuildingCommand is the Square to spawn the building in.
        Square square = (Square) receiver;
        System.out.println("Spawning Shield at (" + args[0] + "," + args[1]+")");

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        BoardComponent shield = new Shield(square,x,y);

        //replace new shield square at location (X,Y)
        GameBoard.Instance().GetBoard().get(y).set(x, shield);

        //remove square observer in arraylist
        GameBoard.Instance().getSubject().removeObserver(square);

        //add new shield  observer in arraylist
        GameBoard.Instance().getSubject().addObserver(shield,-1);

    }
}
