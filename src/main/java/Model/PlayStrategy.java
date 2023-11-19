package Model;

/**
 * This abstract class defines Game Strategy that compute player can adopt
 */

public abstract class PlayStrategy {
    WargameMap d_Map;
    Player d_Player;

    /**
     * default Constructor for PlayerStrategy
     * @param p_player player object
     * @param p_map map object
     */
    PlayStrategy(Player p_player, WargameMap p_map){
        d_Player = p_player;
        d_Map = p_map;
    }

    /**
     * Create Orders based on specific Strategy chosen by player
     * @return Order
     */
    public abstract Order createOrder();

    /**
     * method to return the Country from where attack takes place
     * @return Country
     */
    protected abstract Country toAttackFrom();

    /**
     * method to return the Country on which attack happens
     * @return Country
     */
    protected abstract Country toAttack();

    /**
     * method to return Country to move armies from
     * @return Country
     */
    protected abstract Country toMoveFrom();
}


