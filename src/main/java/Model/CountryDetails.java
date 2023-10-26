package Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * CountryDetails class have all the details of countries related to the selected .map file.
 * It also maintains a list of it's neighboring Countries.
 * 
 * @author Shravya
 *
 */
public class CountryDetails  implements Serializable {

	/**
	 * d_Index - index of the country
	 */
	private int d_Index;
	/**
	 * d_CountryId - Id of the country
	 */
	private String d_CountryId;
	/**
	 * d_InContinent - Name of the continent in which country is present
	 */
	private String d_InContinent;
	/**
	 * d_Neighbours- neighbor of the country
	 */
	private HashMap<String, CountryDetails> d_Neighbours;
	/**
	 * d_XCoOrdinate - x- coordinate of the country
	 */
	private int d_XCoOrdinate;
	/**
	 * d_YCoOrdinate - y-coordinate of the country
	 */
	private int d_YCoOrdinate;
	/**
	 * d_NumberOfArmies- Armies present on the country
	 */
	private int d_NumberOfArmies;
	/**
	 * ownerPlayer - owner of the country
	 */
	Player ownerPlayer;

	Country country;

	/**
	 * Set CountryDetails object with default values.
	 */
	public CountryDetails(){}
	
	/**
	 * Create CountryDetails object with values in argument parameters and set defaults for the rest.
	 * @param p_countryId ID of the country
	 * @param p_inContinent Name of the continent in which this country belongs
	 */
	public CountryDetails(String p_countryId, String p_inContinent){
		this.d_CountryId = p_countryId;
		this.d_InContinent = p_inContinent;
		this.d_Neighbours = new HashMap<String, CountryDetails>();
		this.d_NumberOfArmies = 0;
	}
	
	/**
	 * Creates CountryDetails object as per the argument parameters.
	 * This constructor used while reading from ".map" files.
	 * @param p_index index in the ".map" file as per Domination's conventions
	 * @param p_countryId ID of the country
	 * @param p_continentIndex index of the continent this country belongs to
	 * @param p_xCoOrdinate x-coordinate on GUI map
	 * @param p_yCoOrdinate y-coordinate on GUI map
	 * @param p_map GameMap in which this country resides
	 */
	public CountryDetails(String p_index, String p_countryId, String p_continentIndex, String p_xCoOrdinate, String p_yCoOrdinate, GameMap p_map){
		this.d_Index = Integer.parseInt(p_index);
		this.d_CountryId = p_countryId;
		for(Continent c : p_map.getContinents().values()) {
			if(c.getInMapIndex()==Integer.parseInt(p_continentIndex)) {
				this.d_InContinent = c.getContinentId();
				//break;
			}	
		}	
		this.d_Neighbours = new HashMap<String, CountryDetails>();
		this.d_XCoOrdinate = Integer.parseInt(p_xCoOrdinate);
		this.d_YCoOrdinate = Integer.parseInt(p_yCoOrdinate);
		this.d_NumberOfArmies = 0;
	}
	
	/**
	 * Creates CountryDetails object as per the argument parameters.
	 * This constructor used while reading from ".map" files.
	 * @param p_countryId Id of country
	 * @param p_xCoOrdinate x-coordinate on GUI map
	 * @param p_yCoOrdinate y-coordinate on GUI map
	 * @param p_inContinent continent in which country is present
	 */
	public CountryDetails(String p_countryId, String p_xCoOrdinate, String p_yCoOrdinate, String p_inContinent){
		this.d_Index = 0;
		this.d_CountryId = p_countryId;
		this.d_InContinent = p_inContinent;
		this.d_Neighbours = new HashMap<String, CountryDetails>();
		this.d_XCoOrdinate = Integer.parseInt(p_xCoOrdinate);
		this.d_YCoOrdinate = Integer.parseInt(p_yCoOrdinate);
		this.d_NumberOfArmies = 0;
		this.ownerPlayer = null;
	}

	/**
	 * Returns the index of this country in the ".map" file
	 * @return returns d_index of this country in the ".map" file
	 */
	public int getIndex() {
		return d_Index;
	}
	
	/**
	 * Returns the name of the Continent in which this country belongs
	 * @return returns d_inContinent in which this country belongs
	 */
	public String getInContinent() {
		return this.d_InContinent;
	}
	
	/**
	 * Returns the name of the country
	 * @return returns d_countryName
	 */
	public String getCountryId() {
		return d_CountryId;
	}
	
	/**
	 * Returns the HashMap holding all the neighbors with their names in lower case as keys and their 
	 * CountryDetails object references as values.
	 * @return returns d_neighbors of this country
	 */
	public HashMap<String, CountryDetails> getNeighbours(){
		return this.d_Neighbours;
	}

	/**
	 * Getter method to fetch x-coordinate value
	 * @return returns d_xCoOrdinate
	 */
	public int getxCoOrdinate() {
		return this.d_XCoOrdinate;
	}
	
	/**
	 * Setter method to fetch x-coordinate
	 * @param p_xCoOrdinate argument d_xCoOrdinate value to be set
	 */
	public void setxCoOrdinate(int p_xCoOrdinate) {
		this.d_XCoOrdinate = p_xCoOrdinate;
	}
	
	/**
	 * Getter method to fetch y-coordinate value
	 * @return returns d_yCoOrdinate
	 */
	public int getyCoOrdinate() {
		return this.d_YCoOrdinate;
	}

	/**
	 * Setter method to fetch y-coordinate
	 * @param p_yCoOrdinate argument d_yCoOrdinate value to be set
	 */
	public void setyCoOrdinate(int p_yCoOrdinate) {
		this.d_YCoOrdinate = p_yCoOrdinate;
	}
	
	/**
	 * Printing the details of country and the continent it belongs to
	 */
	public void printCountry(){
		System.out.println("index: " + d_Index);
		System.out.println("countryName: " + d_CountryId);
		System.out.println("inContinent: " + d_InContinent);
	}
	
	/**
	 * Getter method to get number of armies in the country.
	 * @return returns d_numberOfArmies
	 */
	public int getNumberOfArmies() {
		return this.d_NumberOfArmies;
	}

	/**
	 * Set number of armies in the country
	 * @param p_numberOfArmies number of armies to be set in the country
	 */
	public void setNumberOfArmies(int p_numberOfArmies) {
		this.d_NumberOfArmies = p_numberOfArmies;
	}

	/**
	 * Overrides the String object with countryDetails
	 */
	@Override
	public String toString() {
		return "Country [countryName=" + d_CountryId + ", xCoOrdinate=" + d_XCoOrdinate + ", yCoOrdinate=" + d_YCoOrdinate
				+ "]";
	}
	
	/**
	 * Gets the player owning the country currently.
	 * @return Player owning this country.
	 */
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	/**
	 * Sets the player owning this country currently
	 * @param ownerPlayer Player owning this country.
	 */
	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}

    public String getCountryName() {
        return country.getCountryName();
    }
}
