package Model;

import java.util.ArrayList;

/**
 * Class to assist attaching, detaching, and notifying observers.
 */
public class Observable {
    /**
     * Maintains list of observers.
     */
    private ArrayList<Observer> d_Observers  = new ArrayList<>();

    /**
     * Attaches an observer.
     * @param p_observer is the observer's reference
     */
    public void attach(Observer p_observer){
        this.d_Observers.add(p_observer);
    }

    /**
     * Detach an observer.
     * @param p_observer is the observer's reference
     */
    public void detach(Observer p_observer){
        if(!d_Observers.isEmpty()){
            d_Observers.remove(p_observer);
        }
    }

    /**
     * Notifies the observers.
     * @param p_observers is the observable reference
     */
    public void notifyObservers(Observable p_observers){
        for(Observer l_observer : d_Observers){
            l_observer.update(p_observers);
        }
    }
}