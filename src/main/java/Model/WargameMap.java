package Model;

import java.io.Serializable;
import java.util.HashMap;

/** Represents a wargame map containing continents and countries. */

public class WargameMap implements Serializable {
    private String d_MapName;
    private boolean d_isValid;
    private HashMap<String, Continent> d_Continents;
    private HashMap<String, Country> d_Countries;

    /**
     * Default constructor to create a map with null/empty values
     */
    public WargameMap() {
        this.d_MapName = null;
        this.d_isValid = false;
        this.d_Continents = new HashMap<String, Continent>();
        this.d_Countries = new HashMap<String, Country>();
    }

    /**
     * Custom constructor that takes the name of the map
     * and creates a map object with null/empty values for other data members
     *
     * @param p_MapName
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
     * Getter method to return validity of the map
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
     * Getter method to return a list of continents in the map
     *
     * @return return HashMap of continents
     */
    public HashMap<String, Continent> getContinents() {
        return this.d_Continents;
    }

    /**
     * Add method to add continents to the map
     *
     * @param p_continentName
     * @param p_continent
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
     * @param p_countryName
     * @param p_country
     */
    public void addCountries(String p_countryName, Country p_country) {
        this.getCountries().put(p_countryName.toLowerCase(), p_country);
    }

    public String getD_MapName() {
        return d_MapName;
    }

    public void setD_MapName(String d_MapName) {
        this.d_MapName = d_MapName;
    }

    public boolean isD_isValid() {
        return d_isValid;
    }

    public void setD_isValid(boolean d_isValid) {
        this.d_isValid = d_isValid;
    }
}
