package Model;

/**
 * Class containing logic for implementation of blockade order
 * @author Rahul
 *
 */
public class Blockade implements Order{
    private String d_CountryId;
    private Player d_Player;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_player current player issuing blockade order
     * @param p_countryId country where blockade will effect
     */
    public Blockade(Player p_player,String p_countryId) {
        d_Player = p_player;
        d_CountryId = p_countryId;
    }

    /**
     * method which enacts the order
     * @return true if successful, else false
     */
    @Override
    public boolean execute() {

        Country l_c= d_Player.getOwnedCountries().get(d_CountryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_existingArmies *= 3;
        l_c.setNumberOfArmies(l_existingArmies);

        //Making territory neutral
        d_Player.getOwnedCountries().remove(l_c.getCountryName().toLowerCase());
        return true;
    }

    /**
     * Getter for current player
     * @return d_player
     */
    public Player getD_player() {
        return d_Player;
    }

    /**
     * Setter for current player
     * @param d_player player
     */
    public void setD_player(Player d_player) {
        this.d_Player = d_player;
    }

    /**
     * Getter for ID of Country
     * @return d_countryId
     */
    public String getD_countryId() {
        return d_CountryId;
    }

    /**
     * Setter for ID of Country
     * @param d_countryId country ID
     */
    public void setD_countryId(String d_countryId) {
        this.d_CountryId = d_countryId;
    }

}
