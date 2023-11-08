package Model;

import java.util.HashMap;


/**
 * This class represents country object that consists of country name, continent it resides in and its neighbouring countries.
 */
public class Country {
    private static int countryIndex = 1;
    private String d_countryName = null;
    private String d_ContinentName = null;
    private HashMap<String, Country> d_neighbourCountries;
    private int d_countryIndex;
    private int d_armiesAllocated;
    
    /**
     * Empty default constructor
     */
    public Country() {
    }

    /**
     * Custom Country constructor with the provided country Name and continent name, initializing other properties with defaults.
     *
     * @param p_countryName   The unique name of the country.
     * @param p_ContinentName The name of the continent to which this country belongs.
     */
    public Country(String p_countryName, String p_ContinentName) {
        this.d_countryName = p_countryName.toLowerCase();
        this.d_ContinentName = p_ContinentName.toLowerCase();
        this.d_neighbourCountries = new HashMap<String, Country>();
        this.d_countryIndex = Country.countryIndex++;
        this.d_armiesAllocated = 0;
    }

    /**
     * CountryDetails parameterized constructor
     * This constructor used while reading from ".map" files.
     *
     * @param p_index         index in the ".map" file as per Domination's conventions
     * @param p_countryName   countryName key
     * @param p_unique_continentID continentName key
     * @param p_map           GameMap which was loaded
     */
    public Country(String p_index, String p_countryName, String p_unique_continentID, WargameMap p_map) {
        this.d_countryIndex = Integer.parseInt(p_index);
        this.d_countryName = p_countryName.toLowerCase();
        for (Continent c : p_map.getContinents().values()) {
            if (c.getIndexOfContinent() == Integer.parseInt(p_unique_continentID)) {
                this.d_ContinentName = c.getContinentName();
                //break;
            }
        }
        this.d_neighbourCountries = new HashMap<String, Country>();
        this.d_armiesAllocated = 0;
    }

    /**
     * Getter method for continent name
     *
     * @return d_ContinentName
     */
    public String getContinentName() {
        return this.d_ContinentName;
    }

    /**
     * Getter method for country name
     *
     * @return d_countryName
     */
    public String getCountryName() {
        return this.d_countryName;
    }

    /**
     * Getter method to get all the neighbouring countries
     *
     * @return d_neighbourCountries
     */
    public HashMap<String, Country> getNeighbours() {
        return this.d_neighbourCountries;
    }

    public void setIndexOfCountry(int p_index) {
        this.d_countryIndex = p_index;
    }

    public int getIndexOfCountry() {
        return this.d_countryIndex;
    }

    /**
     * Getter method to get number of armies in the country.
     *
     * @return returns d_numberOfArmies
     */
    public int getNumberOfArmies() {
        return this.d_armiesAllocated;
    }

    /**
     * Set number of armies in the country
     *
     * @param p_numberOfArmies number of armies to be set in the country
     */
    public void setNumberOfArmies(int p_numberOfArmies) {
        this.d_armiesAllocated = p_numberOfArmies;
    }

    // public String getCountryId() {
    //     return this.getCountryId();
    // }
}
