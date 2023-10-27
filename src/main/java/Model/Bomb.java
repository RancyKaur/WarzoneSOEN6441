package Model;

/**
 * Class containing logic for the implementation of the Bomb order
 *
 * @author Sujith
 *
 */
public class Bomb implements Order {

    private String d_TargetCountryId;
    private Player d_Player;

    /**
     * Constructor of Bomb class
     *
     * @param p_player          player who is using the Bomb card
     * @param p_targetCountryId target country ID
     */
    public Bomb(Player p_player, String p_targetCountryId) {
        d_Player = p_player;
        d_TargetCountryId = p_targetCountryId;
    }

    /**
     * Contains the implementation logic of the Bomb order
     */
    @Override
    public boolean execute() {
        if (d_Player.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {
            System.out.println("The player cannot destroy armies in his own country.");
            return false;
        }

        if (validateCommand()) {
            // Bomb logic
            int targetArmies = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).getNumberOfArmies();
            int newArmies = targetArmies / 2;
            if (newArmies < 0) {
                newArmies = 0;
            }
            d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).setNumberOfArmies(newArmies);
            d_Player.removeCard("BOMB");
            return true;
        }
        return false;
    }

    /**
     * Contains the validations for the Bomb command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        if (d_Player == null) {
            System.out.println("The Player is not valid.");
            return false;
        }

        // Validate that the player has the Bomb card
        if (!d_Player.hasCard("BOMB")) {
            System.out.println("Player doesn't have Bomb Card.");
            return false;
        }

        if (!d_Player.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {
            System.out.println(d_TargetCountryId + " is not owned by the player");
            return false;
        }

        // Check diplomacy
        if (d_Player.getNegotiateList().contains(d_TargetCountryId)) {
            System.out.println("Truce between " + d_Player.getName() + " and " + d_TargetCountryId);
            d_Player.getNegotiateList().remove(d_TargetCountryId);
            // You may want to implement the corresponding logic to handle diplomacy
            return false;
        }

        return true;
    }

    /**
     * Getter for the target country ID
     *
     * @return d_TargetCountryId
     */
    public String getD_targetCountryId() {
        return d_TargetCountryId;
    }

    /**
     * Setter for the target country ID
     *
     * @param p_targetCountryId target country ID
     */
    public void setD_targetCountryId(String p_targetCountryId) {
        this.d_TargetCountryId = p_targetCountryId;
    }

    /**
     * Getter for the player
     *
     * @return d_Player
     */
    public Player getD_player() {
        return d_Player;
    }

    /**
     * Setter for the player
     *
     * @param p_player player
     */
    public void setD_player(Player p_player) {
        this.d_Player = p_player;
    }
}
