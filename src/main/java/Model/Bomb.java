package Model;

/**
 * Class containing logic for implementation of BOMB order
 */
public class Bomb implements Order {
    private Player sourcePlayer;
    private Player targetPlayer;
    private String countryId;

    public Bomb(Player currentPlayer, Player targetPlayer, String countryId) {
        this.sourcePlayer = currentPlayer;
        this.targetPlayer = targetPlayer;
        this.countryId = countryId;
    }

    /**
     * Method which enacts the order
     *
     * @return true if successful, else false
     */
    public boolean execute() {
        // Check if Source player is negotiating with the target Player
        if (targetPlayer.getNegotiateList().contains(sourcePlayer)) {
            System.out.println(targetPlayer.getPlayerName() + " has negotiated " + sourcePlayer.getPlayerName());
            // Skip execution
            return false;
        }
        Country targetCountry = targetPlayer.getOwnedCountries().get(countryId.toLowerCase());
        if (targetCountry != null) {
            int existingArmies = targetCountry.getNumberOfArmies();
            double remainingArmies = existingArmies / 2.0;
            targetCountry.setNumberOfArmies((int) remainingArmies);
            return true;
        }
        return false;
    }

    /**
     * Getter for source player
     *
     * @return sourcePlayer
     */
    public Player getSourcePlayer() {
        return sourcePlayer;
    }

    /**
     * Setter for source player
     *
     * @param sourcePlayer player
     */
    public void setSourcePlayer(Player sourcePlayer) {
        this.sourcePlayer = sourcePlayer;
    }

    /**
     * Getter for target player
     *
     * @return targetPlayer
     */
    public Player getTargetPlayer() {
        return targetPlayer;
    }

    /**
     * Setter for target player
     *
     * @param targetPlayer player
     */
    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    /**
     * Getter for country ID
     *
     * @return countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * Setter for country ID
     *
     * @param countryId country ID
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
