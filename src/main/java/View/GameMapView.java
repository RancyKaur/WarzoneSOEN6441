package View;

import Controller.GameEngine;
import Model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is responsible for showing the map.
 */

public class GameMapView implements Serializable {

    /**
     * Simple Constructor
     */
    public GameMapView(){
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

    /**
     * This method has the logic to display the passed map into text format to the user
     *
     * @param p_map map object to be displayed
     */
    public void showMap(WargameMap p_map) {
        if (p_map != null) {
            System.out.println("The map " + p_map.getMapName() + " is displayed below");
            System.out.println();
            System.out.printf("%75s\n", "-------------------------------------------------------------------------------------------");
            System.out.printf("%25s%25s%25s\n", "Continents", "Country", "Neighbours");
            System.out.printf("%75s\n", "-------------------------------------------------------------------------------------------");
            boolean l_displayContinent = true;
            boolean l_displayCountry = true;

            for (Continent l_continent : p_map.getContinents().values()) {
                if (l_continent.getListOfCountries().isEmpty()) {
                    System.out.printf("\n%25s%25s%25s\n", GameEngine.capitalizeString(l_continent.getContinentName()), "", "");
                    continue;
                }
                Collection<Country> tt = l_continent.getListOfCountries().values();

                for (Country l_country : l_continent.getListOfCountries().values()) {
                    if (l_country.getNeighbours().isEmpty()) {
                        System.out.printf("\n%25s%25s%25s\n", l_displayContinent ? GameEngine.capitalizeString(l_continent.getContinentName()) : "", l_displayCountry ? GameEngine.capitalizeString(l_country.getCountryName()) : "", "");
                        l_displayContinent = false;
                        l_displayCountry = false;
                    }

                    for (Country l_neighbor : l_country.getNeighbours().values()) {
                        System.out.printf("\n%25s%25s%25s\n", l_displayContinent ? GameEngine.capitalizeString(l_continent.getContinentName()) : "", l_displayCountry ? GameEngine.capitalizeString(l_country.getCountryName()) : "", GameEngine.capitalizeString(l_neighbor.getCountryName()));
                        l_displayContinent = false;
                        l_displayCountry = false;
                    }

                    l_displayCountry = true;
                }
                l_displayContinent = true;
            }
        }
    }




}
