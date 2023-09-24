package Model;

import java.util.HashMap;

/**
 * The `Continent` class encapsulates information about continents, including their name, ID.
 * It also maintains a mapping of countries belonging to the continent.
 *
 * This class provides a comprehensive representation of continents within the context of a game map.
 */
public class Continent
{
    private int d_continentID;
    private String d_continentName=null;
    private HashMap<String,Country> d_listOfCountries;

    public Continent()
    {}
    /**
     * Constructs the Continent object with given name and ID
     * @param p_continentName Name of the Continent
     * @param p_continentID ID of the Continent
     */

    public Continent(String p_continentName,String p_continentID){
        try
        {
            this.d_continentName = p_continentName;
            this.d_continentID = Integer.parseInt(p_continentID);
            this.d_listOfCountries = new HashMap<String,Country>();
        }
        catch (NumberFormatException numFormatException)
        {
            System.out.println("Invalid ID only numbers accepted");

        }
    }

    /**
     * Getter method for continentName
     * @return ContinentName
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Getter method for List of countries in the continent
     * @return ListOfCountries
     */
    public HashMap<String, Country> getListOfCountries() {
        return d_listOfCountries;
    }

    /**
     * Getter method for continentID
     * @return continentID
     */
    public int getContinentID() {
        return d_continentID;
    }

}