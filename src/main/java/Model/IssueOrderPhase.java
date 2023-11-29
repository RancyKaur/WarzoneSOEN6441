package Model;

import Controller.GameEngine;
import View.GameMapView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class containing logic for implementation of IssueOrder Phase
 */
public class IssueOrderPhase extends Play implements Serializable {


    public IssueOrderPhase(GameEngine p_ge) {
        d_Ge = p_ge;
        d_PhaseName = "IssueOrderPhase";

    }
    @Override
    public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
        return false;

    }

    @Override
    public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void issue_order(Player p_player) {

        p_player.getD_orderList().add(p_player.getD_Order());

    }
}
