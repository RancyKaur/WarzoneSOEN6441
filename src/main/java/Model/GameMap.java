package Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * GameMap hold the details of map in the game.
 * Consist of HashMaps to access countries and continents of the map with their names.
 * @author Rucha
 *
 */
public class GameMap implements Serializable{
	/**
	 * d_MapName name of the map
	 */
	private String d_MapName;
	/**
	 * d_Valid determine validity of map
	 */
	private boolean d_Valid;
	/**
	 * d_Continents- store all the continents of map
	 */
	private HashMap<String,Continent> d_Continents;
	/**
	 * d_Countries- store all the countries of map
	 */
	private HashMap<String,CountryDetails> d_Countries;
	
	/**
	 * Create GameMap object without naming the map.
	 */
	public GameMap() {
		this.d_MapName= "";
		this.d_Continents= new HashMap<>();
		this.d_Countries= new HashMap<>();
		this.d_Valid= false;
	}
	
	/**
	 * Create GameMap object with naming the map.
	 * Initialize HashMaps for maintaining continents and countries.
	 * @param p_mapName Name of the map
	 */
	public GameMap(String p_mapName){
		this.d_MapName= p_mapName;
		this.d_Continents= new HashMap<>();
		this.d_Countries= new HashMap<>();
		this.d_Valid= false;
	}
	
	/**
	 * Getter method to return name of the map.
	 * @return return name of the map
	 */
	public String getMapName() {
		return this.d_MapName;
	}

	/**
	 * Setter method to set name of the map to the given name.
	 * @param p_mapName Name of the map
	 */
	public void setMapName(String p_mapName) {
		this.d_MapName = p_mapName;
	}

	/**
	 * Getter method to fetch valid variable.
	 * @return return whether the map is valid for playing game or not
	 */
	public boolean getValid() {
		return this.d_Valid;
	}

	/**
	 * Setter method to set status for validity of the map.
	 * @param p_valid Indicate whether the map is valid for playing game or not
	 */
	public void setValid(boolean p_valid) {
		this.d_Valid = p_valid;
	}

	/**
	 * Getter method to return HashMap maintaining the list of continents in the map.
	 * @return return HashMap maintaining the list of continents in the map.
	 */
	public HashMap<String, Continent> getContinents() {
		return this.d_Continents;
	}

	/**
	 * Setter method to set the d_Continents HashMap to the given HashMap parameter.
	 * @param p_continents HashMap for d_Continents
	 */
	public void setContinents(HashMap<String, Continent> p_continents) {
		this.d_Continents = p_continents;
	}

	/**
	 * Getter method to return HashMap maintaining the list of countries in the map.
	 * @return return HashMap maintaining the list of countries in the map
	 */
	public HashMap<String, CountryDetails> getCountries() {
		return this.d_Countries;
	}

	/**
	 * Setter method to set the d_Countries HashMap to the given HashMap parameter.
	 * @param p_countries HashMap for d_Countries
	 */
	public void setCountries(HashMap<String, CountryDetails> p_countries) {
		this.d_Countries = p_countries;
	}
	
}
