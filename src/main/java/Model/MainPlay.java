package Model;

import Controller.GameEngine;
import View.GameMapView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class containing logic for implementation of MainPlay that is implementation of State Pattern
 */
public class MainPlay extends Play{
    /**
     * Reference to GameMapView for map printing
     */
    GameMapView d_mapView;

    public MainPlay(GameEngine p_ge) {
        d_Ge = p_ge;
        d_PhaseName = "MainPlay";
        d_mapView=new GameMapView();
    }

    @Override
    public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
        return false;
    }

    @Override
    public void showMap(ArrayList<Player> p_players, WargameMap p_map) {
        d_mapView.showMap(p_players, p_map);
    }

    @Override
    public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public void reinforce() {
        Iterator<Player> itr = d_Ge.d_Players.listIterator();
        while(itr.hasNext()) {
            Player p = itr.next();
            ReinforcePlayers.assignReinforcementArmies(p);
            d_Ge.d_LogEntry.setMessage("Assign reinforcement armies to player "+p.getPlayerName());
        }
    }

    @Override
    public void issue_order(Player p_player) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }
}
