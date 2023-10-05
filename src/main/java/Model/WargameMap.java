package Model;

import java.util.HashMap;

/**
 * WargameMap class
 */
public class WargameMap {
    private String d_MapName;
    private boolean d_isValid;
    private HashMap<String, Continent> d_Continents;
    private HashMap<String, Country> d_Countries;

    /**
     * Default constructor to create map with null/empty values
     */
    public WargameMap() {
        this.d_MapName = null;
        this.d_isValid = false;
        this.d_Continents = new HashMap<String, Continent>();
        this.d_Countries = new HashMap<String, Country>();
    }

    /**
     * Custom constructor that takes name of the map and creates map object with null/empty values for other data members
     *
     * @param p_MapName mapname
     */
    public WargameMap(String p_MapName) {
        this.d_MapName = p_MapName;
        this.d_isValid = false;
        this.d_Continents = new HashMap<String, Continent>();
        this.d_Countries = new HashMap<String, Country>();

    }

    /**
     * Getter method for name of the map
     *
     * @return map's name
     */
    public String getMapName() {
        return this.d_MapName;
    }

    /**
     * Getter method to return validity of map
     *
     * @return return whether the map is suitable for playing
     */
    public boolean getValid() {
        return this.d_isValid;
    }

    /**
     * Setter method to set status for validity of the map.
     *
     * @param p_isValid Indicate whether the map is suitable for playing
     */
    public void setValid(boolean p_isValid) {
        this.d_isValid = p_isValid;
    }

    /**
     * Getter method to return list of continents in the map
     *
     * @return return HashMap of continents
     */
    public HashMap<String, Continent> getContinents() {
        return this.d_Continents;
    }

    /**
     * Add method to add continents to the map
     *
     * @param p_continentName continent name
     * @param p_continent p_continent
     */
    public void addContinents(String p_continentName, Continent p_continent) {
        this.getContinents().put(p_continentName.toLowerCase(), p_continent);
    }

    /**
     * Getter method to return HashMap maintaining the list of countries in the map.
     *
     * @return return HashMap maintaining the list of countries in the map
     */
    public HashMap<String, Country> getCountries() {
        return this.d_Countries;
    }

    /**
     * Add method to add countries to the map
     *
     * @param p_countryName country name
     * @param p_country p_contry
     */
    public void addCountries(String p_countryName, Country p_country) {
        this.getCountries().put(p_countryName.toLowerCase(), p_country);
    }

    /**
     * getter
     * @return d_MapName
     */
    public String getD_MapName() {
        return d_MapName;
    }

    /**
     * setter
     * @param d_MapName
     */
    public void setD_MapName(String d_MapName) {
        this.d_MapName = d_MapName;
    }

    /**
     * return boolean
     * @return d_isValid
     */
    public boolean isD_isValid() {
        return d_isValid;
    }

    /**
     * setter
     * @param d_isValid
     */
    public void setD_isValid(boolean d_isValid) {
        this.d_isValid = d_isValid;
    }
}
