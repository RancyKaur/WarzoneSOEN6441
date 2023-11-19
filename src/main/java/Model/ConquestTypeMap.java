package Model;

import java.io.*;
import java.util.HashMap;

public class ConquestTypeMap {

    WargameMap  d_ConquestMap;
    HashMap<Integer, Country> d_ListOfCountries;
    EngineCommand d_RunGE= new EngineCommand();


    /**
     * Reads the ".map" file and creates a GameMap object accordingly.
     * Performs basic validation checks too.
     * @param p_mapName Name of the map file to be read
     * @return object representing the map just read
     */
    public WargameMap readConquestMap(String p_mapName){
        d_ConquestMap = new WargameMap(p_mapName);
        d_ListOfCountries = new HashMap<Integer, Country>();

        try {
            BufferedReader l_reader = new BufferedReader(new FileReader(p_mapName));
            String l_s;
            while ((l_s = l_reader.readLine()) != null) {
                if(l_s.equals("[Continents]")) {
                    l_reader = readContinents(l_reader, d_ConquestMap);
                }
                // reader = readContinents(reader, d_Map);
                if(l_s.equals("[Territories]")) {
                    l_reader = readTerritories(l_reader, d_ConquestMap);
                }
            }
            l_reader.close();
            //make sure valid countries exists as neighbors
            for(Country l_country : d_ConquestMap.getCountries().values()){
                for(String l_neighbor : l_country.getNeighbours().keySet()){
                    if(l_country.getNeighbours().get(l_neighbor.toLowerCase())==null){
                        if(d_ConquestMap.getCountries().get(l_neighbor.toLowerCase())==null){
                            return null;
                        }
                        l_country.getNeighbours().put(l_neighbor.toLowerCase(), d_ConquestMap.getCountries().get(l_neighbor.toLowerCase()));
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
        }
        return d_ConquestMap;
    }

    /**
     * Reads the continents from the ".map" files.
     * Exits the program if error of duplicate continents is found.
     * @param p_reader Stream starting from continents section of ".map" file
     * @param p_map Represents the game map
     * @return BufferedReader stream at the point where it has finished reading continents
     */
    public BufferedReader readContinents(BufferedReader p_reader, WargameMap p_map){
        String l_s;
        //boolean continentExists = false;
        try {
            while(!((l_s = p_reader.readLine()).equals(""))) {
                String[] continentString = l_s.split("=");

                //Check if continent already exists in the map
                if(Integer.parseInt(continentString[1])>=0) {
                    p_map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], Integer.parseInt(continentString[1])));
                    //d_InMapIndex++;
                }
                else {
                    //terminate the program if continent already exists
                    System.out.println("Error reading the file.");
                    System.out.println("Negative control value exists.");
                    System.exit(-1);
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }



    /**
     * Reads the countries from the ".map" files.
     * Exits the program if duplicate country or in non-existent country.
     * @param p_reader Stream starting from countries section of ".map" file
     * @param p_map Represents the game map
     * @return BufferedReader stream at the point where it has finished reading countries
     */
    public BufferedReader readTerritories(BufferedReader p_reader, WargameMap p_map){
        String l_s;
        try {
            while((l_s = p_reader.readLine()) != null) {
                if(l_s.equals("")){
                    continue;
                }
                String[] l_countryString = l_s.split(",");
                Country l_newCountry = new Country(l_countryString[0], l_countryString[3]);
                //  CountryDetails newCountry = new CountryDetails(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
                try {
                    if(l_newCountry.getContinentName()==null)
                    {
                        System.out.println("Error reading the file.");
                        System.out.println("This continent does not exist.");
                        System.exit(-1);
                    }
                    addToContinentMap(l_newCountry, p_map);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.

                    //add neighbors
                    for(int i=4; i<l_countryString.length; i++){
                        if(p_map.getCountries().containsKey(l_countryString[i].toLowerCase())){
                            l_newCountry.getNeighbours().put(l_countryString[i].toLowerCase(), p_map.getCountries().get(l_countryString[i].toLowerCase()));
                            p_map.getCountries().get(l_countryString[i].toLowerCase()).getNeighbours().put(l_newCountry.getCountryName().toLowerCase(), l_newCountry);
                        } else {
                            l_newCountry.getNeighbours().put(l_countryString[i].toLowerCase(), null);
                        }
                    }
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }


    /**
     * Registers this new country as part of its continent.
     * If duplicate country, exits the program throwing error.
     * @param p_newCountry Country to be registered with the corresponding continent
     * @param p_map Represents the map of the game
     */
    public void addToContinentMap(Country p_newCountry, WargameMap p_map){
        if(!ValidateMap.doesCountryExist(p_map, p_newCountry.getCountryName())) {
            Continent argumentContinent = p_map.getContinents().get(p_newCountry.getContinentName().toLowerCase());
            argumentContinent.getListOfCountries().put(p_newCountry.getCountryName().toLowerCase(), p_newCountry);
            p_map.getCountries().put(p_newCountry.getCountryName().toLowerCase(), p_newCountry);
        }
        else {
            //terminate the program if same name country exists in the continent
            System.out.println("Error reading the file.");
            System.out.println("Two countries of same name exists in the same continent.");
            System.exit(-1);
        }
    }


    /**
     * Saves map in Conquest map file format
     * @param p_map GameMap object representing the map to be saved
     * @param p_fileName Name with which map file is to be saved
     * @return true if successful in saving the map, else false
     */
    public boolean saveMap(WargameMap p_map, String p_fileName) {
        if(d_RunGE.validateMap(p_map)) {
            try{
                BufferedWriter l_writer = new BufferedWriter(new FileWriter("src/main/resources/maps/" + p_fileName + ".map"));

                //write preliminary information
                l_writer.write("[Map]");
                l_writer.newLine();
                l_writer.newLine();
                l_writer.flush();

                //write information about all the continents
                l_writer.write("[Continents]");
                l_writer.newLine();
                for (Continent continent : p_map.getContinents().values()) {
                    l_writer.write(continent.getContinentName() + "=" + continent.getContinentControlValue());
                    l_writer.newLine();
                    l_writer.flush();
                }
                l_writer.newLine();

                //write information about countries and its neighbors
                l_writer.write("[Territories]");
                l_writer.newLine();
                String s;
                for(Country country : p_map.getCountries().values()){
                    s = country.getCountryName() + "," + country.getContinentName();
                    for(Country  neighbor : country.getNeighbours().values()){
                        s += "," + neighbor.getCountryName();
                    }
                    l_writer.write(s);
                    l_writer.newLine();
                    l_writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        } else {
            return false;
        }
    }





}
