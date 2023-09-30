package Model;

import Model.Continent;
import Model.Country;
import Model.WargameMap;
import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.util.HashMap;


/**
 * Validation of map takes place in this class
 */
public class ValidateMap {

    private Graph<Country, DefaultEdge> d_MapGraph; //JGraphT type Graph representing the game map

    /**
     * This constructor instantiates the Directed Graph using JGraphT .
     */
    ValidateMap() {
        d_MapGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Creates a graph(using jgrapht library) by taking countries as vertices and adds edges between country and its neighbors
     *
     * @param p_map Game map representing countries ,continents and borders.
     * @return returns graph representing the map
     */
    public Graph<Country, DefaultEdge> createGraph(WargameMap p_map) {

        //add Country to the Graph
        for (Country l_Country : p_map.getCountries().values()) {
            d_MapGraph.addVertex(l_Country);
        }

        //add Edges between country and its neighbours
        for (Country l_Country : p_map.getCountries().values()) {
            for (Country l_neighbor : l_Country.getNeighbours().values()) {
                d_MapGraph.addEdge(l_Country, l_neighbor);
            }
        }

        return d_MapGraph;
    }

    /**
     *This function creates a subgraph for a continent
     * @param p_subGraph subgraph for a continent
     * @param p_countries countries of a continent
     * @return p_subGraph
     */
    public Graph<Country, DefaultEdge> createSubGraph(Graph<Country, DefaultEdge> p_subGraph, HashMap<String, Country> p_countries) {

        for (Country l_country : p_countries.values()) {
            p_subGraph.addVertex(l_country);
        }

        for (Country l_country : p_countries.values()) {
            for (Country l_neighbour : l_country.getNeighbours().values()) {
                if (p_countries.containsKey(l_neighbour.getCountryName().toLowerCase())) {
                    p_subGraph.addEdge(l_country, l_neighbour);
                }
            }
        }
        return p_subGraph;
    }

    /**
     * This function checks if the graph is a connected graph.
     * @param p_graph The graph whose connectivity is checked
     * @return returns true is graph is connected
     */
    public boolean isGraphConnected(Graph<Country, DefaultEdge> p_graph) {
        ConnectivityInspector<Country, DefaultEdge> l_cInspector = new ConnectivityInspector<>(p_graph);
        if (l_cInspector.isConnected())
            return true;
        else
            return false;
    }

    /**
     * Checks if all continents are connected sub-graphs or not
     * @param p_map WargameMap object representing the game map
     * @return true if all continents are connected sub-graph, else false indicating incorrect map
     */
    public boolean continentConnectivityCheck(WargameMap p_map) {
        for(Continent l_continent : p_map.getContinents().values()) {
            Graph<Country, DefaultEdge> subGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
            subGraph = createSubGraph(subGraph, l_continent.getListOfCountries());
            if(!isGraphConnected(subGraph)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Check if the same continent already exist
     *
     * @param p_map WargameMap object holding record of all the existing continents and countries
     * @param p_continentId name of the continent to be checked
     * @return true if continent already exists, else false
     */
    public static boolean doesContinentExist(WargameMap p_map, String p_continentId) {
        if (p_map.getContinents().containsKey(p_continentId.toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * Check if the same country already exist
     *
     * @param p_map WargameMap object holding record of all the existing continents and countries
     * @param p_countryId name of the country to be checked
     * @return true if country already exists, else false
     */
    public static boolean doesCountryExist(WargameMap p_map, String p_countryId) {
        if (p_map.getCountries().containsKey(p_countryId.toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * Check if any continent is empty and does not hold any country.
     * @param p_map WargameMap object representing the game map
     * @return true if no continent is empty, else false indicating empty continent
     */
    public boolean notEmptyContinent(WargameMap p_map) {
        for(Continent l_continent : p_map.getContinents().values()) {
            if(l_continent.getListOfCountries().size()==0)
                return false;
        }
        return true;
    }


}
