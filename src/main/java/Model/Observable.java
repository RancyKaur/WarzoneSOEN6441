package Model;

import java.util.*;

/**
 * for performing operations including adding, removing, and informing observers.
 */
public class Observable {
    /**
     * Maintains list of observers.
     */
    private List<Observer> d_Observers = new ArrayList<Observer>();

    /**
     * to add an observer.
     * 
     * @param p_observer is the observer's reference
     */
    public void addObserver(Observer p_observer) {
        this.d_Observers.add(p_observer);
    }

    /**
     * removes an observer.
     * 
     * @param p_observer is the observer's reference
     */
    public void removeObserver(Observer p_observer) {
        // check if observer is empty or not
        if (d_Observers.isEmpty() == false) {
            d_Observers.remove(p_observer);
        }
    }

    /**
     * for informing the observers.
     * 
     * @param p_observers is the observable reference
     */
    public void inform(Observable p_observers) {
        for (Observer l_observer : d_Observers) {
            l_observer.update(p_observers);
        }
    }
}
