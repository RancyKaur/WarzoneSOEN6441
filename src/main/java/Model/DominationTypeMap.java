package Model;

import Controller.GameEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DominationTypeMap {

    private WargameMap d_DominationMap;
    private Map<Integer, Country> d_countries;
    public static int d_indexOfContinent = 1;

    EngineCommand d_RunGE = new EngineCommand();

    /**
     * getter for Map
     * @return d_DominationMap
     */
    public WargameMap getMap() {
        return this.d_DominationMap;
    }


    /**
     *  setter method for map
     * @param p_map is the reference to GameMap class
     */
    public void setMap(WargameMap p_map) {
        this.d_DominationMap = p_map;
    }



    /**
     * This method reads the map from the drive and runs validation on the map
     *
     * @param p_map Name of the map file to be read
     * @return d_Map WargameMap instance for the map read from the drive
     */
    public WargameMap readDominationMap(String p_map) {
        String p_mapName = p_map.substring(p_map.lastIndexOf('/')+1);
        //System.out.println(p_mapName);
        d_DominationMap = new WargameMap(p_mapName);
        d_countries = new HashMap<>();

        try {
            BufferedReader l_reader = new BufferedReader(new FileReader(p_map));
            String l_s;
            while ((l_s = l_reader.readLine()) != null) {
                if (l_s.equalsIgnoreCase("[Continents]")){
                    l_reader = readContinents(l_reader);
                }
                if (l_s.equalsIgnoreCase("[Countries]")){
                    l_reader = readCountries(l_reader);
                }
                if (l_s.equalsIgnoreCase("[Borders]")){
                    l_reader = readBorders(l_reader);
                }
            }
            l_reader.close();
        } catch (FileNotFoundException e) {
            System.out.printf("Map %s could not be found in our resources\n",p_map);
        } catch (IOException e) {
            System.out.println("Error reading the map file! Try running the command again or restart the game");
        }
        return d_DominationMap;
    }

    /**
     * This helper method reads the countries from the file
     * @param p_reader Stream starting from countries section of ".map" file
     * @return p_reader BufferedReader stream at the point where it has finished reading countries
     */
    private BufferedReader readCountries(BufferedReader p_reader) {
        String l_s;
        try {
            while (!((l_s = p_reader.readLine()).equals(""))) {
                //String[] l_countryString = l_s.split("\\s+");

                String l_countryString = l_s;
                String firstNumber=null;
                String middleString =null;
                String secondNumber = null;
                String input = l_s;
                // Regular expression pattern
                String regex = "(\\d+)\\s+(.*?)\\s+(\\d+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(input);

                if (matcher.find()) {
                    // Extracted values
                    firstNumber = matcher.group(1);
                    middleString = matcher.group(2).toLowerCase();
                    secondNumber = matcher.group(3);

//                    System.out.println("First Number: " + firstNumber);
//                    System.out.println("Middle String: " + middleString);
//                    System.out.println("Second Number: " + secondNumber);
                } else {
                    System.out.println("No match found.");
                }






                Country l_newCountry = new Country(firstNumber, middleString, secondNumber, this.d_DominationMap);
                try {
                    if (l_newCountry.getContinentName() == null) {
                        System.out.println("Error reading the file.Restart the game again!");
                        System.exit(-1);
                    }
                    addToContinentMap(l_newCountry);
                    d_countries.put(l_newCountry.getIndexOfCountry(), l_newCountry);
                } catch (NullPointerException e) {
                    System.out.println("Error encountered while loading countries from the file! Either try to load the map again or restart the game");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }


    /**
     * Reads the continents from the ".map" files.
     * Exits the program if error of duplicate continents is found.
     *
     * @param p_reader Stream starting from continents section of ".map" file
     * @return p_reader BufferedReader stream at the point where it has finished reading continents
     */
    private BufferedReader readContinents(BufferedReader p_reader) {
        String l_s;
        try {
            while (!((l_s = p_reader.readLine()).equals(""))) {
                String l_continentString = l_s;
                String firstNumber=null;
                String middleString =null;
                String secondNumber = null;
                String input = l_s;
                // Regular expression pattern
                String regex = "(\\d+)\\s+(.*?)\\s+(\\d+)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(input);

                if (matcher.find()) {
                    // Extracted values
                     firstNumber = matcher.group(1);
                     middleString = matcher.group(2).toLowerCase();
                     secondNumber = matcher.group(3);

//                    System.out.println("First Number: " + firstNumber);
//                    System.out.println("Middle String: " + middleString);
//                    System.out.println("Second Number: " + secondNumber);
                } else {
                    System.out.println("No match found.");
                }

                if (Integer.parseInt(firstNumber) >= 0) {
                    //to create new continent we give first its string name and secondly controlvalue
                    // for l_continentString in general it will be in number string(can be with spaces)
                    // and then number now i want to read these three values and store it in three diff variables.

                    // write the code

                    d_DominationMap.getContinents().put(middleString, new Continent(middleString, secondNumber,"NaN"));
                    d_indexOfContinent++;
                } else {
                    System.out.println("Error reading the file.");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        d_indexOfContinent = 1;
        return p_reader;
    }



    /**
     * Reads the borders from the ".map" files.
     * Exits the programming error if attempted to add invalid neighbor or to an invalid country.
     *
     * @param p_reader
     * @return p_reader
     */
    private BufferedReader readBorders(BufferedReader p_reader) {
        String l_s;
        try {
            while ((l_s = p_reader.readLine()) != null) {
                if (!l_s.equals("")) {
                    String[] l_borderString = l_s.split("\\s+");
                    Country l_argumentCountry = new Country();
                    l_argumentCountry = d_countries.get(Integer.parseInt(l_borderString[0]));
                    for (int l_neighborCount = 1; l_neighborCount < l_borderString.length; l_neighborCount++) {
                        addNeighbour(l_argumentCountry, l_borderString[l_neighborCount]);

                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p_reader;
    }

    /**
     * Registers the country at argument 'stringIndex' with the argumentCountry.
     * Exits the programming throwing error if invalid neighbor is found
     *
     * @param p_argumentCountry Country to which neighbor is to be registered.
     * @param p_stringIndex     Index of the country to be added as a neighbor to the argument country
     */
    private void addNeighbour(Country p_argumentCountry, String p_stringIndex) {
        int l_borderIndex = Integer.parseInt(p_stringIndex);
        Country l_neighbourCountry = new Country();
        try {
            l_neighbourCountry = d_countries.get(l_borderIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Found error reading the .map file");
            System.out.println("The neighbour " + l_borderIndex + " does not exist.");
            System.exit(-1);
        }
        if (!p_argumentCountry.getNeighbours().containsKey(l_neighbourCountry.getCountryName().toLowerCase()))
            p_argumentCountry.getNeighbours().put(l_neighbourCountry.getCountryName().toLowerCase(), l_neighbourCountry);
    }


    /**
     * Registers this new country as part of its continent.
     * If duplicate country, exits the program throwing error.
     *
     * @param l_newCountry
     */
    private void addToContinentMap(Country l_newCountry) {

        if (!ValidateMap.doesCountryExist(d_DominationMap, l_newCountry.getCountryName())) {
            Continent argumentContinent = d_DominationMap.getContinents().get(l_newCountry.getContinentName().toLowerCase());
            argumentContinent.getListOfCountries().put(l_newCountry.getCountryName().toLowerCase(), l_newCountry);
            d_DominationMap.getCountries().put(l_newCountry.getCountryName().toLowerCase(), l_newCountry);
        } else {
            System.out.println("Error reading the file.");
            System.out.println("Two countries of same name exists in the same continent.");
            System.exit(-1);
        }
    }


    /**
     * This function is used to write continent, country and neighbours/borders to a map file
     * It is executed when user enters savemap command
     * It first displays continents then countries and then borders
     *
     * @param p_map      map object whose value needs to be saved
     * @param p_fileName filename where map's values are saved
     * @return true if map save is successful otherwise false
     */
    public boolean saveMap(WargameMap p_map, String p_fileName) {
        if (d_RunGE.checkGameMap(p_map)) {
            try {
                BufferedWriter l_fileWriter = new BufferedWriter(new FileWriter("src/main/resources/maps/" + p_fileName));
                l_fileWriter.write("map:" + p_fileName);
                l_fileWriter.newLine();
                l_fileWriter.newLine();
                l_fileWriter.flush();
                l_fileWriter.write("[Continents]");
                l_fileWriter.newLine();

                //write continents first to the file in country Index, country name and continent's control value
                for (Continent l_continent : p_map.getContinents().values()) {
                    l_fileWriter.write(l_continent.getIndexOfContinent() + " " + GameEngine.capitalizeString(l_continent.getContinentName()) + " " + l_continent.getContinentControlValue());
                    l_fileWriter.newLine();
                    l_fileWriter.flush();
                }

                // Writing country details
                l_fileWriter.newLine();
                l_fileWriter.write("[Countries]");
                l_fileWriter.newLine();

                List<String> l_bordersList = new ArrayList<>();
                String l_bordersMetaData = new String();

                System.out.println(p_map.getCountries());
                for (Country l_country : p_map.getCountries().values()) {
                    String l_countryName = GameEngine.capitalizeString(l_country.getCountryName());

                    String l_line = l_country.getIndexOfCountry() + " " + l_countryName + " " + p_map.getContinents().get(l_country.getContinentName()).getIndexOfContinent();

                    l_fileWriter.write(l_line);
                    l_fileWriter.newLine();
                    l_fileWriter.flush();

                    if (l_country.getNeighbours() != null && !l_country.getNeighbours().isEmpty()) {
                        l_bordersMetaData = new String();
                        l_bordersMetaData = Integer.toString(l_country.getIndexOfCountry());
                        for (Country l_neighbor : l_country.getNeighbours().values()) {
                            l_bordersMetaData = l_bordersMetaData.concat(" ").concat(Integer.toString(l_neighbor.getIndexOfCountry()));
                        }
                        l_bordersList.add(l_bordersMetaData);
                    }
                }

                System.out.println();
                l_fileWriter.newLine();
                l_fileWriter.write("[Borders]");
                l_fileWriter.newLine();
                l_fileWriter.flush();


                // Writes Border data to the File
                if (l_bordersList != null && !l_bordersList.isEmpty()) {
                    for (String l_borderStr : l_bordersList) {
                        l_fileWriter.write(l_borderStr);
                        l_fileWriter.newLine();
                    }
                    l_fileWriter.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            System.out.println("Map is invalid, it is not suitable for the game.");
            System.out.println("Please either correct the map so that it is connected to be valid or load an existing saved map");
            return false;
        }
    }






}
