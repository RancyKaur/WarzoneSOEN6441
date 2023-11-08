package Model;

import java.util.ArrayList;

/**
 * This is the implementation of the Map Edit phase of the game.
 */
public abstract class MapEditorPhase extends Phase {

    @Override
    public void reinforce() {

        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }
    @Override
    public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
        return false;
    }

    @Override
    public void showMap(ArrayList<Player> p_players, WargameMap p_map) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public void issue_order(Player p_player) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

}
