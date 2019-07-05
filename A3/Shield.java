public class Shield extends SquareDecoratorAbstract {

    private int shieldHealth;
    protected Square square;
    int square_X,square_Y;

    public Shield(Square objectToDecorate,int x,int y)
    {
        super(objectToDecorate);
        this.shieldHealth=2;
        this.square_X=x;
        this.square_Y=y;
        this.square=objectToDecorate;

    }

    @Override
    public void Operation() {

        square.Operation();

    }

    @Override
    public void Add(BoardComponent child) {
        square.Add(child);

    }

    @Override
    public void Remove(BoardComponent child) {
        square.Remove(child);

    }

    @Override
    public void asteroidImpact(Square targetedSquare,int index)
    {
        //first check if this asteroid Impact is for this square
        if(targetedSquare==this.square) {

            //reduce health by 1 if asteroid is impacted
            this.shieldHealth = shieldHealth - 1;
            if (shieldHealth == 0) {

                //replace shield object with square object
                GameBoard.Instance().GetBoard().get(this.square_Y).set(this.square_X, square);

                //replace shield object with square object in observer list at =index
                GameBoard.Instance().getSubject().addObserver(square,index);

                //Remove old shield observer in observer list
                GameBoard.Instance().getSubject().removeObserver(this);


            }
        }

    }
}
