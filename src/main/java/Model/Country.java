package Model;

import java.util.HashMap;


/**
 * This class represents country object that comprises of country name, continent it resides in and its neighbhouring countries.
 */
public class Country
{
    private String d_countryName=null;
    private String d_ContinentName=null;
    private HashMap<String,Country> d_neighbourCountries;

    public Country(){
    }

    /**
     * Custom Country constructor with the provided country Name and continent name, initializing other properties with defaults.
     *
     * @param p_countryName   The unique name of the country.
     * @param p_ContinentName The name of the continent to which this country belongs.
     */
    public Country(String p_countryName, String p_ContinentName)
    {
        this.d_countryName = p_countryName;
        this.d_ContinentName = p_ContinentName;
        this.d_neighbourCountries = new HashMap<String, Country>();
    }

    /**
     * Getter method for continent name
     *
     * @return d_ContinentName
     */
    public String getContinentName()
    {
        return this.d_ContinentName;
    }

    /**
     * Getter method for country name
     *
     * @return d_countryName
     */
    public String getCountryName()
    {
        return this.d_countryName;
    }

    /**
     * Getter method to get all the neighbhouring countries
     *
     * @return d_neighbourCountries
     */
    public HashMap<String,Country> getNeighbours()
    {
        return this.d_neighbourCountries;
    }



}
