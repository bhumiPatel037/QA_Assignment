public  abstract class SquareDecoratorAbstract extends BoardComponent {

    protected Square objectToDecorate;
    public SquareDecoratorAbstract(Square objectToDecorate)
    {
        this.objectToDecorate = objectToDecorate;
    }
    public abstract void Operation();
    public abstract void Add(BoardComponent child);
    public abstract void Remove(BoardComponent child);
}
