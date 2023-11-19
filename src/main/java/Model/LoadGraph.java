package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles loading and parsing game map data from a file.
 */
public class LoadGraph {

    private WargameMap d_map;
//    private Map<Integer, Country> d_countries;
//    public static int d_indexInMap = 1;

    String d_typeOfMap;
    public WargameMap getD_map() {
        return d_map;
    }

    public void setD_map(WargameMap d_map) {
        this.d_map = d_map;
    }


    /**
     * Setter method to set the type of map
     * @param p_mapType type of map
     */
    public void setMapType(String p_mapType) {
        d_typeOfMap = p_mapType;
    }

    /**
     * Getter method to get the type of map
     * @return return the type of map
     */
    public String getMapType() {
        return d_typeOfMap;
    }

    /**
     * this method will determine the type of map and returns it
     * @param p_mapName name of map
     * @return return the type of map
     */
    public String readMap(String p_mapName) {
        d_map = new WargameMap(p_mapName);
        try {
            BufferedReader l_reader = new BufferedReader(new FileReader(p_mapName));
            String l_s;
            while ((l_s = l_reader.readLine()) != null) {
                if (l_s.equals("[Countries]")) {
                    setMapType("domination");
                }
                if (l_s.equals("[Territories]")) {
                    setMapType("conquest");
                }
            }
            l_reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
        }
        return d_typeOfMap;
    }









//    /**
//     * This helper method reads the countries from the file
//     * @param p_reader Stream starting from countries section of ".map" file
//     * @return p_reader BufferedReader stream at the point where it has finished reading countries
//     */
//    private BufferedReader readCountries(BufferedReader p_reader) {
//        String l_s;
//        try {
//            while (!((l_s = p_reader.readLine()).equals(""))) {
//                String[] l_countryString = l_s.split("\\s+");
//                Country l_newCountry = new Country(l_countryString[0], l_countryString[1], l_countryString[2], this.d_map);
//                try {
//                    if (l_newCountry.getContinentName() == null) {
//                        System.out.println("Error reading the file.Restart the game again!");
//                        System.exit(-1);
//                    }
//                    addToContinentMap(l_newCountry);
//                    d_countries.put(l_newCountry.getIndexOfCountry(), l_newCountry);
//                } catch (NullPointerException e) {
//                    System.out.println("Error encountered while loading countries from the file! Either try to load the map again or restart the game");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return p_reader;
//    }

//    /**
//     * Reads the continents from the ".map" files.
//     * Exits the program if error of duplicate continents is found.
//     *
//     * @param p_reader Stream starting from continents section of ".map" file
//     * @return p_reader BufferedReader stream at the point where it has finished reading continents
//     */
//    private BufferedReader readContinents(BufferedReader p_reader) {
//        String l_s;
//        try {
//            while (!((l_s = p_reader.readLine()).equals(""))) {
//                String[] l_continentString = l_s.split("\\s+");
//                if (Integer.parseInt(l_continentString[0]) >= 0) {
//                    //to create new continent we give first its string name and secondly controlvalue
//                    d_map.getContinents().put(l_continentString[1].toLowerCase(), new Continent(l_continentString[1], l_continentString[2],"NaN"));
//                    d_indexInMap++;
//                } else {
//                    System.out.println("Error reading the file.");
//                    System.exit(-1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        d_indexInMap = 1;
//        return p_reader;
//    }

//    /**
//     * Registers this new country as part of its continent.
//     * If duplicate country, exits the program throwing error.
//     *
//     * @param l_newCountry
//     */
//    private void addToContinentMap(Country l_newCountry) {
//
//        if (!ValidateMap.doesCountryExist(d_map, l_newCountry.getCountryName())) {
//            Continent argumentContinent = d_map.getContinents().get(l_newCountry.getContinentName().toLowerCase());
//            argumentContinent.getListOfCountries().put(l_newCountry.getCountryName().toLowerCase(), l_newCountry);
//            d_map.getCountries().put(l_newCountry.getCountryName().toLowerCase(), l_newCountry);
//        } else {
//            System.out.println("Error reading the file.");
//            System.out.println("Two countries of same name exists in the same continent.");
//            System.exit(-1);
//        }
//    }

//    /**
//     * Reads the borders from the ".map" files.
//     * Exits the programming error if attempted to add invalid neighbor or to an invalid country.
//     *
//     * @param p_reader
//     * @return p_reader
//     */
//    private BufferedReader readBorders(BufferedReader p_reader) {
//        String l_s;
//        try {
//            while ((l_s = p_reader.readLine()) != null) {
//                if (!l_s.equals("")) {
//                    String[] l_borderString = l_s.split("\\s+");
//                    Country l_argumentCountry = new Country();
//                    l_argumentCountry = d_countries.get(Integer.parseInt(l_borderString[0]));
//                    for (int l_neighborCount = 1; l_neighborCount < l_borderString.length; l_neighborCount++) {
//                        addNeighbour(l_argumentCountry, l_borderString[l_neighborCount]);
//
//                    }
//
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return p_reader;
//    }


//    /**
//     * Registers the country at argument 'stringIndex' with the argumentCountry.
//     * Exits the programming throwing error if invalid neighbor is found
//     *
//     * @param p_argumentCountry Country to which neighbor is to be registered.
//     * @param p_stringIndex     Index of the country to be added as a neighbor to the argument country
//     */
//    private void addNeighbour(Country p_argumentCountry, String p_stringIndex) {
//        int l_borderIndex = Integer.parseInt(p_stringIndex);
//        Country l_neighbourCountry = new Country();
//        try {
//            l_neighbourCountry = d_countries.get(l_borderIndex);
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("Found error reading the .map file");
//            System.out.println("The neighbour " + l_borderIndex + " does not exist.");
//            System.exit(-1);
//        }
//        if (!p_argumentCountry.getNeighbours().containsKey(l_neighbourCountry.getCountryName().toLowerCase()))
//            p_argumentCountry.getNeighbours().put(l_neighbourCountry.getCountryName().toLowerCase(), l_neighbourCountry);
//    }


}
