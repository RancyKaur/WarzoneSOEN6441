package Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Map hold the details of map in the game. Consist of HashMaps to access countries and continents of the map with their
 * names.
 *
 */
public class Map implements Serializable {
	/**
	 * d_MapName is the name of the map
	 */
	private String d_MapName;
	/**
	 * d_Valid determines validity of the map
	 */
	private boolean d_Valid;
	/**
	 * d_MapContinents stores all the continents of the map
	 */
	private HashMap<String, Continent> d_MapContinents;
	/**
	 * d_MapCountries stores all the countries of the map
	 */
	private HashMap<String, CountryDetails> d_MapCountries;

	/**
	 * Constructor for creating Map object without naming the map.
	 */
	public Map() {
		this.d_MapName = "";
		this.d_MapContinents = new HashMap<>();
		this.d_MapCountries = new HashMap<>();
		this.d_Valid = false;
	}

	/**
	 * Create Map object with naming the map.
	 * Initialize HashMaps for maintaining continents and countries.
	 * 
	 * @param p_mapName Name of the map
	 */
	public Map(String p_mapName) {
		this.d_MapName = p_mapName;
		this.d_MapContinents = new HashMap<>();
		this.d_MapCountries = new HashMap<>();
		this.d_Valid = false;
	}

	/**
	 * Getter method to return name of the map.
	 * 
	 * @return return name of the map
	 */
	public String getMapName() {
		return this.d_MapName;
	}

	/**
	 * Setter method to set name of the map to the given name.
	 * 
	 * @param p_mapName Name of the map
	 */
	public void setMapName(String p_mapName) {
		this.d_MapName = p_mapName;
	}

	/**
	 * Getter method to fetch valid variable.
	 * 
	 * @return return whether the map is valid for playing game or not
	 */
	public boolean getValid() {
		return this.d_Valid;
	}

	/**
	 * Setter method to set status for validity of the map.
	 * 
	 * @param p_valid Indicate whether the map is valid for playing game or not
	 */
	public void setValid(boolean p_valid) {
		this.d_Valid = p_valid;
	}

	/**
	 * Getter method to return HashMap maintaining the list of continents in the map.
	 * 
	 * @return return HashMap maintaining the list of continents in the map.
	 */
	public HashMap<String, Continent> getContinents() {
		return this.d_MapContinents;
	}

	/**
	 * Setter method to set the d_MapContinents HashMap to the given HashMap parameter.
	 * 
	 * @param p_continents HashMap for d_MapContinents
	 */
	public void setContinents(HashMap<String, Continent> p_continents) {
		this.d_MapContinents = p_continents;
	}

	/**
	 * Getter method to return HashMap maintaining the list of countries in the map.
	 * 
	 * @return return HashMap maintaining the list of countries in the map
	 */
	public HashMap<String, CountryDetails> getCountries() {
		return this.d_MapCountries;
	}

	/**
	 * Setter method to set the d_MapCountries HashMap to the given HashMap parameter.
	 * 
	 * @param p_countries HashMap for d_MapCountries
	 */
	public void setCountries(HashMap<String, CountryDetails> p_countries) {
		this.d_MapCountries = p_countries;
	}

}
