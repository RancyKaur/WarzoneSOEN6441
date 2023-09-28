package Model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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


    public boolean addContinentToMap(WargameMap p_gameMap, int p_continentID, String p_continentName) {
        //first of all checking is this given continent already exists
        //if it does not exists then create the new continent object and add it to the game map
        if (!(GameGraph.isContinentExists(p_gameMap, p_continentName))) {
            Continent l_continent = new Continent(p_continentName, p_continentID);
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
     * @return
     */
    public boolean removeContinentFromMap(WargameMap p_gameMap, String p_continentName) {
        //Checking against existing hashmap of continents
        if (p_gameMap.getContinents().containsKey(p_continentName.toLowerCase())) {
            Continent l_continent = p_gameMap.getContinents().get(p_continentName.toLowerCase());

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
            p_gameMap.getContinents().remove(p_continentName.toLowerCase());
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
                System.out.println(p_userGivenContinentName + " continent does not exist. So first add " + p_userGivenContinentName + " to continents or use another continent which already exists");
                System.out.println("For example: To add " + p_userGivenContinentName + " you can write this command ");
                System.out.println("editcontinent -add <continentID (number)> " + p_userGivenContinentName);
                return false;
            }
        } else {
            System.out.println(p_userGivenCountryName + " already exists in the map.");
            System.out.println(p_userGivenCountryName + " is inside " + p_gameMap.getCountries().get(p_userGivenCountryName).getContinentName());
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
     * @param p_gameMap
     * @param p_countryNameToBeRemoved
     * @return
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
            System.out.println(p_countryNameToBeRemoved + " does not exist");
            return false;
        }
    }


    /**
     * @param p_gameMap                Game map object
     * @param p_neighbourCountryName   the name of the neighbour country of the country which we are removing
     * @param p_toBeRemovedCountryName the actual country name that we are removing.
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
     *
     * @param p_gameMap Game map object
     * @return true if the map is valid otherwise false
     */

    // Here first checking for the empty continents in the map
    // if they do not exist then checking for each continent's subgraph
    // if each continent's subgraph is also connected then checking for the entire game map
    // if it is also connected then simple returning true
    // if any of the above stages returns false then simply return false.
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

}