package Model;

/**
 * For notifying the observing classes when it receives a notification from the Observable.
 */
public interface Observer {
    /**
     * carrying out updates in result of an observed action
     * @param p_observer Observable object
     */
    public void workUpdate(Observable p_observer);

}
