public interface ISubject {

    //index specify on which index observer will add in observer arraylist
    public void addObserver(IObserver observer,int index);
    public void removeObserver(IObserver observer);
    public void notifyObserver(Square targetedSquare);
}
