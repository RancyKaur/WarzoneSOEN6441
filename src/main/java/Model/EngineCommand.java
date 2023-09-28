package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import Controller.GameEngine;

/**
 * This class contains implementation of all the actions defined by the game
 */
public class EngineCommand {

    /**
     * This method would check if the map with given Name exists
     * If it does it would load that map and would check with player if they want to edit the map
     * Otherwise it would create new WargameMap object and would prompt user for next commands
     *
     * @param p_mapName name of the given map
     * @return existing or new WargameMap object
     */
    public WargameMap editMap(String p_mapName) {
        WargameMap l_map = null;
        String l_mapfilePath = "src/main/resources/maps/" + p_mapName + ".map";
        File l_mapFile = new File(l_mapfilePath);
        if (l_mapFile.exists()) {
            System.out.println("Map " + p_mapName + ".map already exists, follow below commands to edit it");
        } else {
            System.out.println(p_mapName + " does not exist.");
            System.out.println("Creating a new Map named " + p_mapName);
            l_map = new WargameMap(p_mapName);
        }
        return l_map;
    }

    public boolean saveMap(WargameMap p_map, String p_fileName) {
        try {
            BufferedWriter l_writer = new BufferedWriter(new FileWriter("src/main/resources/maps/" + p_fileName + ".map"));
            l_writer.write("name " + p_fileName + " Map");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean addContinentToMap(WargameMap p_gameMap, int p_continentControlValue, String p_continentName) {
        //first of all checking is this given continent already exists
        //if it does not exists then create the new continent object and add it to the game map
        if (!(GameGraph.isContinentExists(p_gameMap, p_continentName))) {
            Continent l_continent = new Continent(p_continentName, p_continentControlValue);
            p_gameMap.addContinents(p_continentName, l_continent);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This will remove continent from the map. In order to do that we first have to remove each country resides in that continent
     * and then we can remove the continent itself.
     *
     * @param p_gameMap
     * @param p_continentName
     * @return returns true if continent is successfully remove otherwise false
     */
    public boolean removeContinentFromMap(WargameMap p_gameMap, String p_continentName) {
        //Checking against existing hashmap of continents
        if (p_gameMap.getContinents().containsKey(p_continentName)) {
            Continent l_continent = p_gameMap.getContinents().get(p_continentName);

            //l_countryList stores the list of countries that belongs to the specific continent
            ArrayList<Country> l_countryList = new ArrayList<Country>();
            for (Country l_cd : l_continent.getListOfCountries().values()) {
                l_countryList.add(l_cd);
            }
            Iterator<Country> l_countryListIterator = l_countryList.listIterator();
            while (l_countryListIterator.hasNext()) {
                Country l_eachCountry = l_countryListIterator.next();
                //Removing each country from MAP object
                if (p_gameMap.getCountries().containsKey(l_eachCountry.getCountryName())) {
                    p_gameMap.getCountries().remove(l_eachCountry.getCountryName());
                }
            }
            //Removing continent itself
            p_gameMap.getContinents().remove(p_continentName);
            return true;
        } else {
            System.out.println("Given continent name does not exist.");
            return false;
        }
    }


    /**
     * This function add the given country into the given continent.
     *
     * @param p_gameMap                Game map object
     * @param p_userGivenCountryName   country name that we are adding to the continent
     * @param p_userGivenContinentName continent name that we are adding into
     * @return true if country is added successfully to the continent otherwise false
     */

    public boolean addCountryToContinent(WargameMap p_gameMap, String p_userGivenCountryName, String p_userGivenContinentName) {

        //check whether given country exists or not in the map first
        if (!GameGraph.isCountryExists(p_gameMap, p_userGivenCountryName)) {
            // if not then making sure the continent exists
            if (p_gameMap.getContinents().containsKey(p_userGivenContinentName)) {
                Country l_countryObjToBeAdded = new Country(p_userGivenCountryName, p_userGivenContinentName);
                Continent l_continentObjToBeUsed = p_gameMap.getContinents().get(p_userGivenContinentName);
                //adding the country object into the list of countries of the continents
                l_continentObjToBeUsed.getListOfCountries().put(p_userGivenCountryName, l_countryObjToBeAdded);
                //adding country to map object as well
                p_gameMap.addCountries(p_userGivenCountryName, l_countryObjToBeAdded);

                return true;
            }
            //if continent does not exist
            else {
                System.out.println(GameEngine.capitalizeString(p_userGivenContinentName) + " continent does not exist. So first add " + p_userGivenContinentName + " to continents or use another continent which already exists");
                System.out.println("For example: To add " + GameEngine.capitalizeString(p_userGivenContinentName) + " you can write this command ");
                System.out.println("editcontinent -add <continentID (number)> " + GameEngine.capitalizeString(p_userGivenContinentName));
                return false;
            }
        } else {
            System.out.println(GameEngine.capitalizeString(p_userGivenCountryName) + " already exists in the map.");
            System.out.println(GameEngine.capitalizeString(p_userGivenCountryName) + " is inside " + GameEngine.capitalizeString(p_gameMap.getCountries().get(p_userGivenCountryName).getContinentName()));
            return false;
        }
    }


    /**
     * This methods removes the country from map.
     * <p>
     * To remove country, first check that country exists in map if yes then get all the neighbouring countries of the country that we want to remove.
     * Then iterate over those neighbouring countries and remove this given country from their list of neighbouring countries.
     * Then remove the country itself from the continent and map.
     *
     * @param p_gameMap                map object
     * @param p_countryNameToBeRemoved country to be removed
     * @return true if country is removed else false
     */
    public boolean removeCountry(WargameMap p_gameMap, String p_countryNameToBeRemoved) {
        //check whether given country exists or not in the map first
        if (GameGraph.isCountryExists(p_gameMap, p_countryNameToBeRemoved)) {
            Country l_countryObjToBeRemoved = p_gameMap.getCountries().get(p_countryNameToBeRemoved);
            ArrayList<Country> l_listOfNeighbourCountriesOfGivenCountry = new ArrayList<Country>();

            //Iterate over each neighbour and add it to the list
            for (Country l_neighbourCountry : l_countryObjToBeRemoved.getNeighbours().values()) {
                l_listOfNeighbourCountriesOfGivenCountry.add(l_neighbourCountry);
            }

            Iterator<Country> l_iterator = l_listOfNeighbourCountriesOfGivenCountry.listIterator();
            while (l_iterator.hasNext()) {
                Country l_currentNeighbourCountry = l_iterator.next();
                //Removing the p_countryNameToBeRemoved from the neighbour list of the l_currentNeighbourCountry and vice versa
                if (!removeNeighbour(p_gameMap, l_currentNeighbourCountry.getCountryName(), p_countryNameToBeRemoved)) {
                    System.out.println("Internal country Neighbour removal went wrong !!");
                    return false;
                }
            }
            //removing given country obj from the list of countries of the continent object.
            //continent name in which l_countryObjToBeRemoved resides
            Continent l_continentOfCountryWhichToBeRemoved = p_gameMap.getContinents().get(l_countryObjToBeRemoved.getContinentName());
            l_continentOfCountryWhichToBeRemoved.getListOfCountries().remove(p_countryNameToBeRemoved);

            //removing given country object from map itself
            p_gameMap.getCountries().remove(p_countryNameToBeRemoved);

            //removed successfully
            return true;


        } else {
            System.out.println(GameEngine.capitalizeString(p_countryNameToBeRemoved) + " does not exist");
            return false;
        }
    }

    /**
     * @param p_map                 Game map object
     * @param p_countryName         name of the country to be added
     * @param p_neighborCountryName name of the neighbouring country to be added
     * @return returns true if neighbour is successfully added otherwise false
     */
    public boolean addNeighbour(WargameMap p_map, String p_countryName, String p_neighborCountryName) {
        //Check whether both the country exists
        if (p_map.getCountries().containsKey(p_countryName) && p_map.getCountries().containsKey(p_neighborCountryName)) {
            Country l_country = p_map.getCountries().get(p_countryName);
            Country l_neighbourCountry = p_map.getCountries().get(p_neighborCountryName);
            //check if both countries are neighbor to each other or not
            if (!l_country.getNeighbours().containsKey(l_neighbourCountry.getCountryName())) {
                l_country.getNeighbours().put(p_neighborCountryName, l_neighbourCountry);
                System.out.println(GameEngine.capitalizeString(p_countryName) + " added as neighbor to " + GameEngine.capitalizeString(p_neighborCountryName));
            } else
                System.out.println("already neighbor");
            if (!l_neighbourCountry.getNeighbours().containsKey(l_country.getCountryName())) {
                l_neighbourCountry.getNeighbours().put(p_countryName, l_country);
                System.out.println(GameEngine.capitalizeString(p_neighborCountryName) + " added as neighbor to " + GameEngine.capitalizeString(p_countryName));
            } else
                System.out.println("already neighbor");
            return true;
        } else {
            if (!p_map.getCountries().containsKey(p_countryName) && !p_map.getCountries().containsKey(p_neighborCountryName))
                System.out.println(GameEngine.capitalizeString(p_countryName) + " and " + GameEngine.capitalizeString(p_neighborCountryName) + "  does not exist. Create country first and then set their neighbors.");
            else if (!p_map.getCountries().containsKey(p_countryName))
                System.out.println(GameEngine.capitalizeString(p_countryName) + " does not exist. Create country first and then set its neighbors.");
            else
                System.out.println(GameEngine.capitalizeString(p_neighborCountryName) + " does not exist. Create country first and then set its neighbors.");
            return false;
        }
    }

    /**
     * @param p_gameMap                Game map object
     * @param p_neighbourCountryName   the name of the neighbour country of the country which we are removing
     * @param p_toBeRemovedCountryName the name of the actual country name that we are removing.
     * @return true if neighbour removal operation is successful otherwise false
     */
    public boolean removeNeighbour(WargameMap p_gameMap, String p_neighbourCountryName, String p_toBeRemovedCountryName) {
        //Fist Check both the countries exists

        if (GameGraph.isCountryExists(p_gameMap, p_neighbourCountryName) && GameGraph.isCountryExists(p_gameMap, p_toBeRemovedCountryName)) {

            Country l_country1 = p_gameMap.getCountries().get(p_neighbourCountryName);
            Country l_country2 = p_gameMap.getCountries().get(p_toBeRemovedCountryName);

            //Check whether these two countries are neighbour to each other or not

            if (l_country1.getNeighbours().containsKey(p_toBeRemovedCountryName) && l_country2.getNeighbours().containsKey(p_neighbourCountryName)) {
                l_country1.getNeighbours().remove(p_toBeRemovedCountryName);
                l_country2.getNeighbours().remove(p_neighbourCountryName);
                return true;
            } else {
                System.out.println("Any one of the countries is not the neighbour to the other country");
                return false;
            }

        } else {
            System.out.println("Any one of the countries does not exist");
            return false;
        }
    }


    /**
     * This function checks whether the game map is valid or not.
     * Here first checking for the empty continents in the map
     * if they do not exist then checking for each continent's subgraph
     * if each continent's subgraph is also connected then checking for the entire game map
     * if it is also connected then simple returning true
     * if any of the above stages returns false then simply return false.
     *
     * @param p_gameMap Game map object
     * @return true if the map is valid otherwise false
     */

    public boolean checkGameMap(WargameMap p_gameMap) {
        GameGraph l_gameGraphObj = new GameGraph();

        if (l_gameGraphObj.isEmptyContinentExist(p_gameMap)) {
            System.out.println("Empty Continent Exist in the Map. ");
            return false;
        } else if (!l_gameGraphObj.isEachContinentConnected(p_gameMap)) {
            System.out.println("Not all Continents are connected");
            return false;
        } else if (!l_gameGraphObj.isEntireGameMapConnected(l_gameGraphObj.makeGraph(p_gameMap))) {
            System.out.println("It is not a connected graph");
            return false;
        }
        return true;
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