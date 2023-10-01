package Model;

import java.util.*;

/**
 * Implementation of startup phase of the game.
 */
public class GameStartPhase {


    /**
     * Addition of players to the game
     * The maximum no. of players assigned is restricted to 6
     *
     * @param p_players    List of players in the game
     * @param p_playerName Names of players in the game
     * @return true if the players are added successfully, else false
     */
    public boolean addPlayer(ArrayList<Player> p_players, String p_playerName) {
        if (p_players.size() == 6) {
            System.out.println("Can not add any more player. Maximum no. of players allowed is 6.");
            return false;
        }
        p_players.add(new Player(p_playerName));
        return true;
    }

    /**
     * Removing players from the game
     *
     * @param p_players    List of players in the game
     * @param p_playerName Names of players in the game
     * @return true if the players are removed successfully, else false
     */
    public boolean removePlayer(ArrayList<Player> p_players, String p_playerName) {
        Iterator<Player> itr = p_players.listIterator();
        while (itr.hasNext()) {
            Player l_p = itr.next();
            if (l_p.getPlayerName().equals(p_playerName)) {
                p_players.remove(l_p);
                return true;
            }
        }
        return false;
    }

    /**
     * Responsible for distributing countries amongst players.
     *
     * @param p_map     Game map
     * @param p_players List of players in the game
     * @return true if successful, else false
     */
    public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players) {
        int l_numberOfPlayers = p_players.size();
        if (p_players.size() < 2) {
            System.out.println("Minimum two players are required to play the game.");
            return false;
        }
        int l_counter = 0;
        for (Country l_c : p_map.getCountries().values()) {
            Player l_p = p_players.get(l_counter);
            l_p.getOwnedCountries().put(l_c.getCountryName().toLowerCase(), l_c);
            if (l_counter >= l_numberOfPlayers - 1)
                l_counter = 0;
            else
                l_counter++;
        }

        return true;
    }

    /**
     * Shows map with along with Owner and Army units.
     *
     * @param p_players List of players in the game
     * @param p_map     Game map
     */
    public void showMap(ArrayList<Player> p_players, WargameMap p_map) {
        if (p_map == null)
            return;
        if (p_players.size() == 0 || p_players.get(0).getOwnedCountries().size() == 0) {
            EngineCommand l_rc = new EngineCommand();
            l_rc.showMap(p_map);
            return;
        }
        System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
        System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
        boolean l_printPlayerName = true;
        boolean l_printContinentId = true;
        boolean l_printCountryId = true;
        boolean l_printNumberOfArmies = true;

        for (int i = 0; i < p_players.size(); i++) {
            Player l_p = p_players.get(i);
            for (Country l_country : l_p.getOwnedCountries().values()) {
                for (Country l_neighbor : l_country.getNeighbours().values()) {
                    if (l_printPlayerName && l_printContinentId && l_printCountryId) {
                        System.out.format("\n%25s%25s%35s%25s%10d\n", l_p.getPlayerName(), l_country.getCountryName(), l_neighbor.getCountryName(), l_country.getContinentName(), l_country.getNumberOfArmies());
                        l_printPlayerName = false;
                        l_printContinentId = false;
                        l_printCountryId = false;
                        l_printNumberOfArmies = false;
                    } else if (l_printContinentId && l_printCountryId && l_printNumberOfArmies) {
                        System.out.format("\n%25s%25s%35s%25s%10d\n", "", l_country.getCountryName(), l_neighbor.getCountryName(), l_country.getContinentName(), l_country.getNumberOfArmies());
                        l_printPlayerName = false;
                        l_printCountryId = false;
                        l_printNumberOfArmies = false;
                    } else {
                        System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", l_neighbor.getCountryName(), "", "");
                    }
                }
                l_printContinentId = true;
                l_printCountryId = true;
                l_printNumberOfArmies = true;
            }
            l_printPlayerName = true;
            l_printContinentId = true;
            l_printCountryId = true;
            l_printNumberOfArmies = true;
        }
    }
}
