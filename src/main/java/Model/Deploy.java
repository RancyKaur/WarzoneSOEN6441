package Model;

/**
 * class contains the logic for the deploy order functionality
 */
public class Deploy implements Order{

    /**
     * @param d_CountryId is for country name
     * @param d_Player player issuing deploy order
     */
    private int d_NumArmies;
    private String d_CountryId;
    private Player d_Player;


    /**
     * This constructor will initialize the order object with deploy details.
     *
     * @param p_player    current player issuing deploy order
     * @param p_countryId country where armies will be deployed
     * @param p_numArmies total armies which will be deployed
     */
    public Deploy(Player p_player, String p_countryId, int p_numArmies) {
        d_Player = p_player;
        d_CountryId = p_countryId;
        d_NumArmies = p_numArmies;
    }

    /**
     * method which enacts the order
     *
     * @return true if successful, else false
     */
    public boolean execute() {
        Country l_c = d_Player.getOwnedCountries().get(d_CountryId.toLowerCase());
        int l_existingArmies = l_c.getNumberOfArmies();
        l_existingArmies += d_NumArmies;
        l_c.setNumberOfArmies(l_existingArmies);
        return true;
    }

    /**
     * Getter for current player
     *
     * @return d_player
     */
    public Player getD_player() {
        return d_Player;
    }

    /**
     * Setter for current player
     *
     * @param d_player player
     */
    public void setD_player(Player d_player) {
        this.d_Player = d_player;
    }

    /**
     * Getter for ID of Country
     *
     * @return d_countryId
     */
    public String getD_countryId() {
        return d_CountryId;
    }

    /**
     * Setter for ID of Country
     *
     * @param d_countryId country ID
     */
    public void setD_countryId(String d_countryId) {
        this.d_CountryId = d_countryId;
    }

    /**
     * Setter for number of armies
     *
     * @return d_numArmies
     */
    public int getD_numArmies() {
        return d_NumArmies;
    }

    /**
     * Setter for number of armies
     *
     * @param d_numArmies number of armies
     */
    public void setD_numArmies(int d_numArmies) {
        this.d_NumArmies = d_numArmies;
    }

}
