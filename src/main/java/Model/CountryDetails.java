package Model;

import java.io.Serializable;
import java.util.*;

/**
 * CountryDetails class have all the details of countries related to the
 * selected .map file and also maintains a list of it's neighboring Countries.
 * 
 */
public class CountryDetails implements Serializable {

	/**
	 * d_Index is the index of the country
	 */
	private int d_Index;
	/**
	 * d_CountryId is the id of the country
	 */
	private String d_CountryId;
	/**
	 * d_InContinent is the name of the continent in which country is present
	 */
	private String d_InContinent;
	/**
	 * d_XCoordinate is the x-coordinate of the country
	 */
	private int d_XCoordinate;
	/**
	 * d_YCoordinate is the y-coordinate of the country
	 */
	private int d_YCoordinate;
	/**
	 * d_NumOfArmies is the armies present on the country
	 */
	private int d_NumOfArmies;
	/**
	 * d_Neighboursis the neighbor of the country
	 */
	private HashMap<String, CountryDetails> d_Neighbours;
	/**
	 * ownerPlayer is the owner of the country
	 */
	private Player ownerPlayer;

	Country country;

	/**
	 * collection for storing continents
	 */
	private Collection<Continent> values;

	/**
	 * Set CountryDetails object with default values.
	 */
	public CountryDetails() {
	}

	/**
	 * Create CountryDetails object with values in argument parameters and set
	 * defaults for the rest.
	 * 
	 * @param p_countryId   ID of the country
	 * @param p_inContinent Name of the continent in which this country belongs
	 */
	public CountryDetails(String p_countryId, String p_inContinent) {
		this.d_CountryId = p_countryId;
		this.d_InContinent = p_inContinent;
		this.d_NumOfArmies = 0;
		this.d_Neighbours = new HashMap<String, CountryDetails>();
	}

	/**
	 * Creates CountryDetails object as per the argument parameters.
	 * This constructor used while reading from ".map" files.
	 * 
	 * @param p_index          index in the ".map" file as per Domination's
	 *                         conventions
	 * @param p_countryId      ID of the country
	 * @param p_continentIndex index of the continent this country belongs to
	 * @param p_xCoordinate    x-coordinate on GUI map
	 * @param p_yCoordinate    y-coordinate on GUI map
	 * @param p_map            GameMap in which this country resides
	 */
	public CountryDetails(String p_index, String p_countryId, String p_continentIndex, String p_xCoordinate,
			String p_yCoordinate, GameMap p_map) {
		this.d_Index = Integer.parseInt(p_index);
		this.d_CountryId = p_countryId;

		values = p_map.getContinents().values();

		for (Continent ct : values) {
			if (ct.getInMapIndex() == Integer.parseInt(p_continentIndex)) {
				this.d_InContinent = ct.getContinentId();
			}
		}

		this.d_Neighbours = new HashMap<String, CountryDetails>();
		this.d_XCoordinate = Integer.parseInt(p_xCoordinate);
		this.d_YCoordinate = Integer.parseInt(p_yCoordinate);
		this.d_NumOfArmies = 0;
	}

	/**
	 * Creates CountryDetails object as per the argument parameters.
	 * This constructor used while reading from ".map" files.
	 * 
	 * @param p_countryId   Id of country
	 * @param p_xCoordinate x-coordinate on GUI map
	 * @param p_yCoordinate y-coordinate on GUI map
	 * @param p_inContinent continent in which country is present
	 */
	public CountryDetails(String p_countryId, String p_xCoordinate, String p_yCoordinate, String p_inContinent) {
		this.d_Index = 0;
		this.d_CountryId = p_countryId;
		this.d_InContinent = p_inContinent;
		this.d_Neighbours = new HashMap<String, CountryDetails>();
		this.d_XCoordinate = Integer.parseInt(p_xCoordinate);
		this.d_YCoordinate = Integer.parseInt(p_yCoordinate);
		this.d_NumOfArmies = 0;
		this.ownerPlayer = null;
	}

	/**
	 * Returns the index of this country in the ".map" file
	 * 
	 * @return returns d_index of this country in the ".map" file
	 */
	public int getIndex() {
		return d_Index;
	}

	/**
	 * Returns the name of the Continent in which this country belongs
	 * 
	 * @return returns d_inContinent in which this country belongs
	 */
	public String getInContinent() {
		return this.d_InContinent;
	}

	/**
	 * Returns the name of the country
	 * 
	 * @return returns d_countryName
	 */
	public String getCountryId() {
		return d_CountryId;
	}

	/**
	 * Returns the HashMap holding all the neighbors with their names in lower case
	 * as keys and their
	 * CountryDetails object references as values.
	 * 
	 * @return returns d_neighbors of this country
	 */
	public HashMap<String, CountryDetails> getNeighbours() {
		return this.d_Neighbours;
	}

	/**
	 * Getter method to fetch x-coordinate value
	 * 
	 * @return returns d_xCoordinate
	 */
	public int getxCoordinate() {
		return this.d_XCoordinate;
	}

	/**
	 * Setter method to fetch x-coordinate
	 * 
	 * @param p_xCoordinate argument d_xCoordinate value to be set
	 */
	public void setxCoordinate(int p_xCoordinate) {
		this.d_XCoordinate = p_xCoordinate;
	}

	/**
	 * Getter method to fetch y-coordinate value
	 * 
	 * @return returns d_yCoordinate
	 */
	public int getyCoordinate() {
		return this.d_YCoordinate;
	}

	/**
	 * Setter method to fetch y-coordinate
	 * 
	 * @param p_yCoordinate argument d_yCoordinate value to be set
	 */
	public void setyCoordinate(int p_yCoordinate) {
		this.d_YCoordinate = p_yCoordinate;
	}

	/**
	 * Printing the details of country and the continent it belongs to
	 */
	public void printCountry() {
		System.out.println("Index of the country : " + d_Index);
		System.out.println("ID of the country : " + d_CountryId);
		System.out.println("Name of the continent in which country is present : " + d_InContinent);
	}

	/**
	 * Getter method to get number of armies in the country.
	 * 
	 * @return returns d_numberOfArmies
	 */
	public int getNumberOfArmies() {
		return this.d_NumOfArmies;
	}

	/**
	 * Set number of armies in the country
	 * 
	 * @param p_numberOfArmies number of armies to be set in the country
	 */
	public void setNumberOfArmies(int p_numberOfArmies) {
		this.d_NumOfArmies = p_numberOfArmies;
	}

	/**
	 * Overrides the String object with countryDetails
	 */
	@Override
	public String toString() {
		return "Country [countryName = " + d_CountryId + ", xCoordinate = " + d_XCoordinate + ", yCoordinate = "
				+ d_YCoordinate + "]";
	}

	/**
	 * Gets the player owning the country currently.
	 * 
	 * @return Player owning this country.
	 */
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	/**
	 * Sets the player owning this country currently
	 * 
	 * @param ownerPlayer Player owning this country.
	 */
	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}

	public String getCountryName() {
		return country.getCountryName();
	}
}
