package Model;

/**
 * Negotiate class implements the Diplomacy card
 * Diplomacy card enforces peace between two players for a variable number of turns.
 * While peace is enforced, neither player will be able to attack the other.
 * The card takes effect the turn after it is played.
 *
 */
public class Negotiate implements Order{
    private Player d_sourcePlayer, d_targetPlayer;
    /**
     * Constructor that initializes class variable.
     * @param p_currentPlayer sorce player giving order
     * @param p_targetPlayer target player
     */
    public Negotiate(Player p_currentPlayer,Player p_targetPlayer){
        this.d_sourcePlayer = p_currentPlayer;
        this.d_targetPlayer = p_targetPlayer;
    }

    @Override
    public boolean execute() {
        this.d_sourcePlayer.addNegotiatePlayer(d_targetPlayer);
        this.d_targetPlayer.addNegotiatePlayer(d_sourcePlayer);

        return true;
    }
}
