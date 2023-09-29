package Model;

import java.util.HashMap;

/**
 * The `Continent` class encapsulates information about continents, including their name, ID.
 * It also maintains a mapping of countries belonging to the continent.
 * <p>
 * This class provides a comprehensive representation of continents within the context of a game map.
 */
public class Continent {
    private static int continentCount=1;
    private int d_continentControlValue;
    private String d_continentName = null;
    private HashMap<String, Country> d_listOfCountries;
    private int d_indexOfContinent;

    /**
     * Constructs the Continent object with given name and ID
     *
     * @param p_continentName Name of the Continent
     * @param p_continentID   ID of the Continent
     */

    public Continent(String p_continentName, int p_continentID) {
        this.d_continentName = p_continentName.toLowerCase();
        this.d_continentControlValue = p_continentID;
        this.d_listOfCountries = new HashMap<String, Country>();
        this.d_indexOfContinent=Continent.continentCount++;
    }

    /**
     * Getter method for continentName
     *
     * @return ContinentName
     */
    public String getContinentName() {
        return d_continentName;
    }

    /**
     * Getter method for List of countries in the continent
     *
     * @return ListOfCountries
     */
    public HashMap<String, Country> getListOfCountries() {
        return this.d_listOfCountries;
    }

    /**
     * Getter method for continentID
     *
     * @return continentID
     */
    public int getContinentControlValue() {
        return this.d_continentControlValue;
    }


    public void setIndexOfContinent(int p_index)
    {
        this.d_indexOfContinent = p_index;
    }

    public int getIndexOfContinent()
    {
        return this.d_indexOfContinent;
    }
}
