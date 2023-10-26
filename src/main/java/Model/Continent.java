package Model;

import java.util.HashMap;

/**
 * The `Continent` class encapsulates information about continents, including
 * their name, ID.
 * It also maintains a mapping of countries belonging to the continent.
 * <p>
 * This class provides a comprehensive representation of continents within the
 * context of a game map.
 */
public class Continent {
    private static int continentCount = 1;
    private int d_continentControlValue;
    private String d_continentName = null;
    private HashMap<String, Country> d_listOfCountries;
    private int d_indexOfContinent;
    private int d_InMapIndex;
    private String d_ContinentId;

    /**
     * This constructor is called when reading existing map file
     * 
     * @param p_continentName
     * @param p_continentID
     * @param p_existingmap
     */
    Continent(String p_continentName, String p_continentID, String p_existingmap) {
        d_ContinentId = p_continentID;
        this.d_continentName = p_continentName;
        d_continentControlValue = Integer.parseInt(p_continentID);
        this.d_indexOfContinent = LoadGraph.d_indexInMap;
        this.d_listOfCountries = new HashMap<>();
    }

    /**
     * This constructor is called when reading existing map file
     * 
     * @param p_continentName
     * @param p_continentID
     * @param p_existingmap
     */
    Continent(String p_continentName, int p_continentID) {
        this.d_continentName = p_continentName;
        d_continentControlValue = p_continentID;
        this.d_indexOfContinent = LoadGraph.d_indexInMap;
        this.d_listOfCountries = new HashMap<>();
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

    public void setIndexOfContinent(int p_index) {
        this.d_indexOfContinent = p_index;
    }

    public int getIndexOfContinent() {
        return this.d_indexOfContinent;
    }

    public String getContinentId() {
        return this.d_ContinentId;
    }

    /**
     * Returns the Index value for this continent when saved in ".map" file
     * following domination's rules
     * 
     * @return returns Index value of the continent
     */
    public int getInMapIndex() {
        return this.d_InMapIndex;
    }

    /**
     * Sets the Index value of this continent
     * 
     * @param p_inMapIndex Index value of the continent
     */
    public void setInMapIndex(int p_inMapIndex) {
        d_InMapIndex = p_inMapIndex;
    }
}
