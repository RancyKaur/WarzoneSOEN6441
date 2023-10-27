package Model;

/**
 * Class containing logic for the implementation of the Airlift order
 *
 * @author Sujith
 *
 */
public class Airlift implements Order {

    private int d_NumArmies;
    private String d_SourceCountryId;
    private String d_TargetCountryId;
    private Player d_Player;

    /**
     * Constructor of Airlift class
     *
     * @param p_player          source player who is using the Airlift
     * @param p_sourceCountryId source country ID
     * @param p_targetCountryId target country ID
     * @param p_numArmies       number of armies to Airlift
     */
    public Airlift(Player p_player, String p_sourceCountryId, String p_targetCountryId, int p_numArmies) {
        d_Player = p_player;
        d_SourceCountryId = p_sourceCountryId;
        d_TargetCountryId = p_targetCountryId;
        d_NumArmies = p_numArmies;
    }

    /**
     * Contains the implementation logic of the Airlift order
     */
    @Override
    public boolean execute() {
        if (d_Player.getOwnedCountries().containsKey(d_SourceCountryId.toLowerCase())) {
            if (d_Player.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {
                if (d_NumArmies > 0) {
                    int fromArmies = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase()).getNumberOfArmies();
                    if (fromArmies >= d_NumArmies) {
                        // Airlift logic
                        fromArmies -= d_NumArmies;
                        d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase()).setNumberOfArmies(fromArmies);
                        int toArmies = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).getNumberOfArmies();
                        toArmies += d_NumArmies;
                        d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
                        return true;
                    } else {
                        System.out.println("Not enough armies in the source country.");
                        return false;
                    }
                } else {
                    System.out.println("Number of armies to Airlift must be greater than 0.");
                    return false;
                }
            } else {
                System.out.println(d_TargetCountryId + " is not owned by the player");
                return false;
            }
        } else {
            System.out.println(d_SourceCountryId + " is not owned by the player");
            return false;
        }
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
     * @param p_player player
     */
    public void setD_player(Player p_player) {
        this.d_Player = p_player;
    }

    /**
     * Getter for ID of Source Country
     *
     * @return d_SourceCountryId
     */
    public String getD_sourceCountryId() {
        return d_SourceCountryId;
    }

    /**
     * Setter for ID of source Country
     *
     * @param p_sourceCountryId source country ID
     */
    public void setD_sourceCountryId(String p_sourceCountryId) {
        this.d_SourceCountryId = p_sourceCountryId;
    }

    /**
     * Getter for ID of Target Country
     *
     * @return d_TargetCountryId
     */
    public String getD_targetCountryId() {
        return d_TargetCountryId;
    }

    /**
     * Setter for ID of Target Country
     *
     * @param p_targetCountryId country ID
     */
    public void setD_targetCountryId(String p_targetCountryId) {
        this.d_TargetCountryId = p_targetCountryId;
    }

    /**
     * Getter for number of armies
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
