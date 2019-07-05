import java.util.ArrayList;

public class AsteroidImpacts implements ISubject {

    private ArrayList<IObserver> observerList= new ArrayList<IObserver>();

    @Override
    public void addObserver(IObserver observer,int index) {

        /*if index is less than 0 than i will add to Arraylist without providing specific index of arraylist
        I need this logic when shield object will remove and square object will replaced at its place.
        I replaced observer in arraylist.
        */
        if(index<0)
        {
            this.observerList.add(observer);
        }
        else
        {
            this.observerList.add(index,observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {

        this.observerList.remove(observer);

    }

    @Override
    public void notifyObserver(Square targetedSquare)
    {
        //This method notify to all observers.
        for(int temp=0;temp<observerList.size();temp++)
        {
            observerList.get(temp).asteroidImpact(targetedSquare,temp);
        }
    }
}
