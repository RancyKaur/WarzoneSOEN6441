package Model;

import java.util.HashMap;

public class WargameMap {
    private String d_MapName;
    private boolean d_isValid;
    private HashMap<String,Continent> d_Continents;
    private HashMap<String,Country> d_Countries;

    /**
     * Default constructor to create map with null/empty values
      */
    public WargameMap()
    {
        this.d_MapName=null;
        this.d_isValid= false;
        this.d_Continents = new HashMap<String,Continent>();
        this.d_Countries = new HashMap<String,Country>();
    }

    /**
     * Custom constructor that takes name of the map and creates map object with null/empty values for other data members
     * @param p_MapName
     */
    public WargameMap(String p_MapName)
    {
        this.d_MapName = p_MapName;
        this.d_isValid= false;
        this.d_Continents = new HashMap<String,Continent>();
        this.d_Countries = new HashMap<String,Country>();

    }

    /**
     * Getter method to return validity of map
     * @return return whether the map is suitable for playing
     */
    public boolean getValid()
    {
        return this.d_isValid;
    }

    /**
     * Setter method to set status for validity of the map.
     * @param p_isValid Indicate whether the map is suitable for playing
     */
    public void setValid(boolean p_isValid)
    {
        this.d_isValid = p_isValid;
    }

    /**
     * Getter method to return list of continents in the map
     * @return return HashMap of continents
     */
    public HashMap<String, Continent> getContinents()
    {
        return this.d_Continents;
    }

    /**
     * Setter method to set the d_Continents HashMap to the given HashMap parameter.
     * @param p_countryName
     * @param p_country
     */
    public void setContinents(String p_countryName, Country p_country)
    {
        this.getCountries().put(p_countryName.toLowerCase(), p_country);
    }

    /**
     * Getter method to return HashMap maintaining the list of countries in the map.
     * @return return HashMap maintaining the list of countries in the map
     */
    public HashMap<String, Country> getCountries() {
        return this.d_Countries;
    }

    /**
     * Setter method to set the d_Countries HashMap to the given HashMap parameter.
     * @param p_countries HashMap for d_Countries
     */
    public void setCountries(HashMap<String, Country> p_countries) {
        this.d_Countries = p_countries;
    }
}
