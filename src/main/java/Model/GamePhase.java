package Model;

/**
 * GamePhases is an enum of the several phases that the game moves through
 * As per the Warzone project description we have defined the phases
 */
public enum GamePhase {
    /**
     * BEGIN phase is the first phase when the sample maps are loaded and displayed to the player
     * User can decide to move to next phase of either EDITMAP or STARTPLAY
     */
    BEGINGAME,

    /**
     * EDITMAP phase indicates that player can edit existing map or create new map
     * This phase starts when player enters the command 'editmap'
     * The phase ends when player enters 'loadmap' or 'stop'
     */
    EDITMAP,

    /**
     * This phase starts when player enters the command 'loadmap'
     * In this phase players are assigned to the game using 'gameplayer' command
     * The map is selected with loadmap command
     * 'assigncountries' command leads to end of this phase and start next phase
     */
    STARTPLAY,

    /**
     * Player assigns initial armies to the countries owned by him/her.
     * Phase ends when all player's have assigned initial armies.
     */
    ASSIGN_REINFORCEMENTS,

    /**
     * This phase starts when player executes 'deploy' command
     * That would add the orders to the order list until all reinforcement armies are deployed
     * It ends after all players have played their turn in round-robin
     */
    ISSUEORDER,


    EXECUTEORDER,

    /**
     * TAKETURN is a signal for end of current player's turn and start of next player's turn.
     */
    TAKETURN,

    /**
     * This is signal for ending the game, when the player enter the command 'stopgame'
     */
    ENDGAME
}
