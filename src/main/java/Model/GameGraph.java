package Model;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashMap;


/**
 * The GameGraph class is responsible for creating, validating and analyzing the game map.
 *
 */
public class GameGraph {


    private Graph<Country, DefaultEdge> d_gameMapLogicalGraph;

    /**
     * Constructs a MapValidator object and initializes the Directed Graph using JGraphT.
     */
    public GameGraph()
    {
        d_gameMapLogicalGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    }


    /**
     * Checking whether the continent exists in the war game map and return boolean value
     * @param p_gameMap Object of the WargameMap class consist of the details like # of continents and # of countries
     * @param p_continentName Continent's name that we are checking against
     * @return false if there is no continent of given p_continentName in WargameMap else returns true
     */
    public static boolean isContinentExists(WargameMap p_gameMap, String p_continentName){
        p_continentName=p_continentName.toLowerCase();
        if(p_gameMap.getContinents().containsKey(p_continentName))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * This function checks whether there exists given country in the game map.
     * @param p_gameMap Game map object
     * @param p_countryName Name of the country that we are checking against.
     * @return true if country exists in the game map or false otherwise
     */
    public static boolean isCountryExists(WargameMap p_gameMap, String p_countryName){
        if(p_gameMap.getCountries().containsKey(p_countryName))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     *This function will create the whole game map graph.
     * @param p_gameMap Game map object
     * @return Graph<Country,DefaultEdge>
     */
    public Graph<Country,DefaultEdge> makeGraph(WargameMap p_gameMap)
    {
        //Creating each country as a separate vertex
        for (Country l_countryData: p_gameMap.getCountries().values())
        {
            d_gameMapLogicalGraph.addVertex(l_countryData);

        }

        //After successfully adding the vertices in the graph now adding the links between the country and its neighbours
        for (Country l_countryData: p_gameMap.getCountries().values())
        {
            for (Country l_neighbour: l_countryData.getNeighbours().values())
            {
                d_gameMapLogicalGraph.addEdge(l_countryData,l_neighbour);
            }
        }
        return d_gameMapLogicalGraph;
    }

    /** This function checks whether are there any continents in map that do not have any country or empty in other words
     *
     * @param p_gameMap Game map object
     * @return true if there is some empty continents or false if there is no any empty continents
     */
    public boolean isEmptyContinentExist(WargameMap p_gameMap){
        for(Continent l_continent : p_gameMap.getContinents().values()){
            if(l_continent.getListOfCountries().size()==0){
                return true;
            }
        }

        return false;
    }

    /**This function simple checks whether given Graph is connected or not.
     *
     * @param p_gameGraph Graph object
     * @return true if connected or false if not connected
     */
    public boolean isEntireGameMapConnected(Graph<Country,DefaultEdge> p_gameGraph){
        ConnectivityInspector<Country,DefaultEdge> l_connectivityChecker = new ConnectivityInspector<>(p_gameGraph);
        if(l_connectivityChecker.isConnected()){
            return true;
        }
        return false;
    }


    /** This function creates the sub map of the single continent.
     *
     * @param p_subMap the submap that it is creating
     * @param p_listOfCountriesOfContinent the list of countries any particular continent is having
     * @return Graph<Country,DefaultEdge>
     */
    //It iterates over the list of countries and make them vertices of the sub graph.
    // After that it iterates over their neighbour countries and adds the edge between them
    // at the end it returns the submap
    public Graph<Country,DefaultEdge> createSubMap (Graph<Country,DefaultEdge> p_subMap, HashMap<String,Country> p_listOfCountriesOfContinent){

        for(Country l_country: p_listOfCountriesOfContinent.values()){
            p_subMap.addVertex(l_country);
        }

        for(Country l_country:p_listOfCountriesOfContinent.values()){
            for(Country l_neighbour : l_country.getNeighbours().values()){
                if(p_listOfCountriesOfContinent.containsKey(l_neighbour.getCountryName())){
                    p_subMap.addEdge(l_country,l_neighbour);
                }
            }
        }

        return p_subMap;
    }


    /**This function checks whether each continent forms a valid sub map or not.
     * Valid here means connected
     *
     * @param p_gameGraph Object of the game map
     * @return true or false
     */
    //It first gets all the continents presents in the map
    //Then it creates the sub graphs for each of the continents and checks for its validity.
    // if all of the continents's subgraphs form a valid subgraphs then it will return true otherwise false.
    public boolean isEachContinentConnected(WargameMap p_gameGraph){
        for(Continent l_continent: p_gameGraph.getContinents().values()){
            Graph<Country,DefaultEdge> l_subMap = new DefaultUndirectedGraph<>(DefaultEdge.class);
            l_subMap=createSubMap(l_subMap,l_continent.getListOfCountries());
            if(isEntireGameMapConnected(l_subMap)){
                return true;
            }
        }
        return false;
    }








}
