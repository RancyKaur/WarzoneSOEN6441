package Model;

import java.util.ArrayList;

/**
 * Implementation of the Play Phase
 */
public abstract class Play extends Phase {

    @Override
    public void loadMap(String[] p_data, String p_mapName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public void showMap(WargameMap p_map) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void editMap(String[] p_data, String p_mapName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public void saveMap(String[] p_data, String p_mapName) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void showMap(ArrayList<Player> p_players, WargameMap p_map) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());

    }

    @Override
    public void validatemap() {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

    @Override
    public void reinforce(){
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }




}
