package Model;

/**
 * Class containing logic for implementation of Bomb order
 * @author Alekhya K
 *
 */
public class Bomb {
    private String d_CountryId;
    private Player d_Player;
    private Player d_CPlayer;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_cplayer source player who will bomb
     * @param p_player target player where the bomb will take effect
     * @param p_countryId adjacent opponent country where the bomb card will take effect
     */
    public Bomb(Player p_cplayer, Player p_player, String p_countryId) {
        d_Player = p_player;
        d_CPlayer = p_cplayer;
        d_CountryId = p_countryId;
    }

    /**
     * Method which enacts the order
     * @return true if successful, else false
     */
    public boolean execute() {
        // Check if Source player is negotiating with the target Player
        if (d_CPlayer.getNegotiateList().contains(d_Player)) {
            System.out.println(d_CPlayer.getPlayerName() + " has negotiated " + d_Player.getPlayerName());
            // Skip execution
            return false;
        }
        Country l_c = d_Player.getOwnedCountries().get(d_CountryId.toLowerCase());
        if (l_c != null) {
            int l_existingArmies = l_c.getNumberOfArmies();
            double armies = (double) (l_existingArmies / 2);
            l_c.setNumberOfArmies((int) armies);
            return true;
        }
        return false;
    }

    /**
     * Getter for current player
     * @return d_Player
     */
    public Player getD_Player() {
        return d_Player;
    }

    /**
     * Setter for current player
     * @param d_Player player
     */
    public void setD_Player(Player d_Player) {
        this.d_Player = d_Player;
    }

    /**
     * Getter for ID of Country
     * @return d_CountryId
     */
    public String getD_CountryId() {
        return d_CountryId;
    }

    /**
     * Setter for ID of Country
     * @param d_CountryId country ID
     */
    public void setD_CountryId(String d_CountryId) {
        this.d_CountryId = d_CountryId;
    }
}
