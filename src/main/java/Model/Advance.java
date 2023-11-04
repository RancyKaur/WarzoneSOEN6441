package Model;

/**
 * Class containing logic for implementation of Advance order
 * 
 * @author Rucha
 *
 */
public class Advance implements Order {

	private int d_NumArmies;
	private String d_SourceCountryId, d_TargetCountryId;
	private Player d_Player, d_TargetPlayer;

	/**
	 * Constructor of advance class
	 *
	 * @param p_player          source player who is advancing armies
	 * @param p_sourceCountryId source country Id
	 * @param p_targetCountryId target country Id
	 * @param p_numArmies       number of armies
	 * @param p_targetPlayer    target player on whom advance is to be performed
	 */
	public Advance(Player p_player, String p_sourceCountryId, String p_targetCountryId, int p_numArmies,
			Player p_targetPlayer) {
		d_Player = p_player;
		d_SourceCountryId = p_sourceCountryId;
		d_TargetCountryId = p_targetCountryId;
		d_NumArmies = p_numArmies;
		d_TargetPlayer = p_targetPlayer;
	}

	/**
	 * Contain the implementation logic of advance order
	 */
	@Override
	public boolean execute() {

		if (d_Player.getOwnedCountries().containsKey(d_SourceCountryId.toLowerCase())) {
			if (((d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase()).getNumberOfArmies())
					- d_NumArmies) >= 1) {
				if (d_Player.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {

					// advance logic
					int fromArmies = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase())
							.getNumberOfArmies();
					fromArmies -= d_NumArmies;
					d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase()).setNumberOfArmies(fromArmies);
					int toArmies = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase())
							.getNumberOfArmies();
					toArmies += d_NumArmies;
					d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
					return true;

				} else {
					System.out.println(d_TargetCountryId + " is not owned by the player");

					if (d_Player.d_NegotiateList.contains(d_TargetPlayer)) {
						// skip execute
						return false;
					} else {
						// attack logic
						System.out.println("Attack Occur between: " + d_TargetCountryId + " and " + d_SourceCountryId);

						// fetching the countries and its armies
						Country attackingCountry = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
						Country defendingCountry = attackingCountry.getNeighbours()
								.get(d_TargetCountryId.toLowerCase());

						int defendArmy = defendingCountry.getNumberOfArmies();

						// logic to kill the opponent country armies
						int armyToAttack = (d_NumArmies * 60) / 100;
						int armyForDefend = (defendArmy * 70 / 100);

						// after attack
						int defenderArmyLeft = defendArmy - armyToAttack;
						int attackerArmyLeft = d_NumArmies - armyForDefend;

						// if defending country has no armies
						if (defenderArmyLeft <= 0) {
							d_Player.getOwnedCountries().put(d_TargetCountryId, defendingCountry);
							d_TargetPlayer.getOwnedCountries().remove(d_TargetCountryId);
							defendingCountry.setNumberOfArmies(attackerArmyLeft);
							attackingCountry.setNumberOfArmies(((d_Player.getOwnedCountries()
									.get(d_SourceCountryId.toLowerCase()).getNumberOfArmies()) - d_NumArmies));
							// If Attack Successful and new territory added to Player
							// Generate a random Card from {'BOMB', 'AIRLIFT', 'BLOCKADE', 'DIPLOMACY'}
							d_Player.addCard();
							System.out.println(attackingCountry.getCountryName() + " won the attack and country " + defendingCountry.getCountryName());
						}
						// if defending country has armies
						else {
							System.out.println(defendingCountry.getCountryName() + " won the attack");
							defendingCountry.setNumberOfArmies(defenderArmyLeft);
							if (attackerArmyLeft < 0)
								attackingCountry.setNumberOfArmies(((d_Player.getOwnedCountries()
										.get(d_SourceCountryId.toLowerCase()).getNumberOfArmies()) - d_NumArmies));
							else
								attackingCountry.setNumberOfArmies(
										((d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase())
												.getNumberOfArmies()) - d_NumArmies) + attackerArmyLeft);
						}
						return true;
					}
				}
			} else {
				System.out.println("player don't have enough armies.");
				return false;
			}
		} else {
			System.out.println(d_SourceCountryId + " is not owned by the player");
			return false;
		}

	}

	public boolean executes() {
		if (!isSourceCountryOwned()) {
			return false;
		}

		if (!hasEnoughArmies()) {
			return false;
		}

		if (isTargetCountryOwnedByPlayer()) {
			return executeAdvanceLogic();
		} else {
			System.out.println(d_TargetCountryId + " is not owned by the player");

			if (d_Player.d_NegotiateList.contains(d_TargetPlayer)) {
				return false; // Skip execute
			} else {
				System.out.println("Attack Occurs between : " + d_TargetCountryId + " and " + d_SourceCountryId);

				Country attackingCountry = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase());

				int defendArmy = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).getNumberOfArmies();

				int armyToAttack = (d_NumArmies * 60) / 100;
				int armyForDefend = (defendArmy * 70 / 100);

				int defenderArmyLeft = defendArmy - armyToAttack;
				int attackerArmyLeft = d_NumArmies - armyForDefend;

				if (defenderArmyLeft <= 0) {
					handleSuccessfulAttack(attackingCountry, attackerArmyLeft);
					d_Player.addCard();
				} else {
					handleFailedAttack(attackingCountry, defenderArmyLeft, attackerArmyLeft);
				}

				return true;
			}
		}
	}

	private boolean isSourceCountryOwned() {
		if (d_Player.getOwnedCountries().containsKey(d_SourceCountryId.toLowerCase())) {
			return true;
		} else {
			System.out.println(d_SourceCountryId + " is not owned by the player");
			return false;
		}
	}

	private boolean hasEnoughArmies() {
		Country sourceCountry = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
		int availableArmies = sourceCountry.getNumberOfArmies();

		if (availableArmies - d_NumArmies >= 1) {
			return true;
		} else {
			System.out.println("Player doesn't have enough armies.");
			return false;
		}
	}

	private boolean isTargetCountryOwnedByPlayer() {
		if (d_Player.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {
			return true; // Advance Logic
		} else {
			return false;
		}
	}

	private boolean executeAdvanceLogic() {
		// Advance Logic (Same as before)
		int fromArmies = d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase())
				.getNumberOfArmies();
		fromArmies -= d_NumArmies;
		d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase()).setNumberOfArmies(fromArmies);
		int toArmies = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase())
				.getNumberOfArmies();
		toArmies += d_NumArmies;
		d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
		return true;
	}

	private void handleSuccessfulAttack(Country attackingCountry, int attackerArmyLeft) {
		Country defendingCountry = d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase());
		d_Player.getOwnedCountries().put(d_TargetCountryId, defendingCountry);
		d_TargetPlayer.getOwnedCountries().remove(d_TargetCountryId);
		defendingCountry.setNumberOfArmies(attackerArmyLeft);
		attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies() - d_NumArmies);
	}

	private void handleFailedAttack(Country attackingCountry, int defenderArmyLeft, int attackerArmyLeft) {
		d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase()).setNumberOfArmies(defenderArmyLeft);

		if (attackerArmyLeft < 0)
			attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies() - d_NumArmies);
		else
			attackingCountry.setNumberOfArmies(attackingCountry.getNumberOfArmies() - d_NumArmies + attackerArmyLeft);
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
		this.d_SourceCountryId = p_targetCountryId;
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
