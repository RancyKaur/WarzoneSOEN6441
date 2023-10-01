package Model;

/**
 * Handles tasks related to Assign_Reinforcement Phase which assigns armies according to WarZone rules.
 */
public class ReinforcePlayers {
    /**
     * Method assigns to each player the correct number of reinforcement armies according to the Warzone rules.
     *
     * @param p_player current player
     */
    public static void assignReinforcementArmies(Player p_player) {
        int l_totalControlValue = 0;
        int l_totalReinforcementArmies;
        if (p_player.getOwnedCountries().size() >= 9) {
            if (p_player.getOwnedContinents().size() > 0) {
                for (Continent l_c : p_player.getOwnedContinents().values()) {
                    l_totalControlValue += l_c.getContinentControlValue();
                }
                l_totalReinforcementArmies = (int) (p_player.getOwnedCountries().size() / 3) + l_totalControlValue;
            } else {
                l_totalReinforcementArmies = (int) (p_player.getOwnedCountries().size() / 3);
            }
        } else {
            l_totalReinforcementArmies = 3;
        }
        p_player.setOwnedArmies(l_totalReinforcementArmies);
    }
}

