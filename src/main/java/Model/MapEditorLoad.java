package Model;

import Controller.GameEngine;
import View.GameMapView;

/**
 * Concrete Implentation of the MapEditor.
 */
public class MapEditorLoad extends MapEditorPhase{


    /**
     * Reference to GameMapView for map printing
     */
    GameMapView d_mapView;



    /**
     * it is constructor to initialize values
     * @param p_gameEngineObj is the reference of gameEngine class
     */
    public MapEditorLoad(GameEngine p_gameEngineObj)
    {
        d_Ge = p_gameEngineObj;
        d_PhaseName = "PreLoad";
        d_mapView=new GameMapView();
    }



    @Override
    public void loadMap(String[] p_data,String p_mapName) {

        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+ p_data[0] + " " +p_data[1]);
            if (p_data[1] != null) {
                if (d_Ge.isValidMapName(p_data[1])) {
                    p_mapName = p_data[1];
                    d_Ge.d_map = d_Ge.d_RunCommand.loadMap(p_mapName);
                    if (d_Ge.d_map != null) {
                        if (!d_Ge.d_map.getValid()) {
                            System.out.println("Map does not exist! Select a map from our resources or the one you created!");
                            d_Ge.d_LogEntry.setMessage("Map does not exist! Select a map from our resources or the one you created!");
                            d_Ge.d_GamePhase = GamePhase.BEGINGAME;
                            d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                        } else {
                            System.out.printf("Map %s loaded in game memory successfully\n", p_mapName);
                            System.out.println("Now you are have to follow two following steps IN ORDER");
                            System.out.println(
                                    "Step 1: add the players to the game by using gameplayer -add <playername>. There total number of players must be between 2 and 6");
                            System.out.println(
                                    "\t You can also remove the player by replacing -add to -remove exmaple gameplayer -remove <playername>");
                            System.out.println(
                                    "Step 2: issue 'assigncountries' command to initially assign the countries randomly to all the players");

                            d_Ge.d_LogEntry.setMessage(p_mapName+" Map is valid. Add players -> ");
                            d_Ge.d_GamePhase = GamePhase.STARTPLAY;
                            d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                        }
                    } else {
                        d_Ge.d_GamePhase = GamePhase.BEGINGAME;
                        d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                    }
                } else {
                    System.out.println("Map name not valid");
                    d_Ge.d_LogEntry.setMessage("Map name not valid");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            System.out.println("Invalid command. To load a map from our resources or the one you created, type loadmap <mapname>.map");
            d_Ge.d_LogEntry.setMessage("Invalid command - try -> loadmap sample.map");
        }
        d_Ge.setPhase(new StartUp(d_Ge));
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);
    }

    /**
     * print the continents, countries and each country's neighbor in the map
     * @param p_map the map to be shown.
     */
    @Override
    public void showMap(WargameMap p_map) {
        d_mapView.showMap(p_map);
    }

    /**
     * This method calls the editMap functionalities
     *
     * @param p_data    input command String
     * @param p_mapName name of the map to be edited
     */
    public void editMap(String[] p_data, String p_mapName) {

        try {
            d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data[0] + " " +p_data[1]);
            if (p_data[1] != null) {
                if (d_Ge.isValidMapName(p_data[1])) {
                    p_mapName = p_data[1];
                    System.out.println(" You are editing Map: " + p_mapName);
                    d_Ge.d_map = d_Ge.d_RunCommand.editMap(p_mapName);
                    d_Ge.printEditmapHelpCommands();
                    d_Ge.d_LogEntry.setMessage("Start editing " + p_mapName);
                    d_Ge.d_GamePhase = GamePhase.EDITMAP;
                    d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                } else {
                    System.out.println("Sorry, map name is invalid, please try again with AlphaNumeric characters and no extension");

                    d_Ge.d_LogEntry.setMessage("Map name is invalid!");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Invalid command, it needs name of the map as a parameter, For E.g. editmap mapname");
            d_Ge.d_LogEntry.setMessage("Invalid command structure");
        }

        d_Ge.setPhase(new MapEditorLoad(d_Ge));
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);

    }



    /**
     * This method saves the edited maps
     *
     * @param p_data    input command string
     * @param p_mapName name of the map
     */
    @Override
    public void saveMap(String[] p_data, String p_mapName) {

        try {
            String l_mapName;
            if (p_data[1] != "") {
                if (d_Ge.isValidMapName(p_data[1])) {
                    l_mapName = p_data[1];

                    boolean l_check = d_Ge.d_RunCommand.saveMap(d_Ge.d_map, l_mapName);
                    if (l_check) {
                        System.out.println("Map file has been saved successfully");
                        d_Ge.d_GamePhase = GamePhase.BEGINGAME;
                        d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                    } else
                        System.out.println("Error in saving the map, as it is invalid");
                } else
                    System.out.println("Map name is not valid, please ensure you do not put any extension");
            }
        } catch (Exception e) {
            System.out.println(
                    "Invalid command - it should be of the form(without extension) savemap filename");
        }

    }

    /**
     * This method allows to add neighbors to a country
     *
     * @param p_data              input command string
     * @param p_countryId         countryId of the country
     * @param p_neighborCountryId neighbourCountryId of the country p_countryId
     */
    public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {

        try {
            String l_typeOfEdit = p_data[1];

            if (d_Ge.containsAlphabetsOnly(p_data[2]) && d_Ge.containsAlphabetsOnly(p_data[3])) {
                String l_countryName = p_data[2].toLowerCase();
                String l_neighborCountryName = p_data[3].toLowerCase();

                if ("-add".equals(l_typeOfEdit) || "-remove".equals(l_typeOfEdit)) {
                    if (d_Ge.d_map.getCountries().containsKey(l_countryName)
                            && d_Ge.d_map.getCountries().containsKey(l_neighborCountryName)) {
                        boolean l_success;
                        if ("-add".equals(l_typeOfEdit)) {
                            l_success = d_Ge.d_RunCommand.addNeighbour(d_Ge.d_map, l_countryName,
                                    l_neighborCountryName);
                        } else {
                            l_success = d_Ge.d_RunCommand.removeNeighbour(d_Ge.d_map, l_countryName,
                                    l_neighborCountryName);
                        }
                        if (l_success) {
                            d_Ge.d_GamePhase = GamePhase.EDITMAP;
                            d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                        }
                    } else {
                        System.out.println(
                                "It appears that either one or both of the countries do not exist in the map, please create the country first");
                    }
                } else {
                    System.out.println(
                            "Invalid action command, expecting either 'editneighbor -add' or 'editneighbor -remove'");
                }
            } else {
                System.out.println("Invalid Country Names, please provide valid country name");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    "Command is invalid - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        } catch (Exception e) {
            System.out.println(
                    "Command is invalid  - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
        }

    }


    /**
     * This method edits a country
     *
     * @param p_data        input command string
     * @param p_continentId continentId of the country to be added
     * @param p_countryId   countryId to be added
     */
    public void editCountry(String[] p_data, String p_continentId, String p_countryId) {

        try {
            String l_typeOfEditcountry = p_data[1].toLowerCase(); // -add or -remove
            String l_userGivenCountryName = p_data[2].toLowerCase(); // countryID param

            if (l_typeOfEditcountry.equals("-add")) {
                String l_userGivenContinentName = p_data[3].toLowerCase(); // continentID param
                // Checking for the country name and I given by the user should be string
                // without any special characters
                if (d_Ge.containsAlphabetsOnly(l_userGivenCountryName)
                        && d_Ge.containsAlphabetsOnly(l_userGivenContinentName)) {

                    boolean l_status = d_Ge.d_RunCommand.addCountryToContinent(d_Ge.d_map, l_userGivenCountryName,
                            l_userGivenContinentName);
                    if (l_status) {
                        System.out.println(d_Ge.capitalizeString(l_userGivenCountryName)
                                + " is successfully added to " + l_userGivenContinentName);
                        d_Ge.d_GamePhase = GamePhase.EDITMAP;
                        d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                    }

                } else {
                    System.out.println("Provided name(s) not valid!");
                }
            } else if (l_typeOfEditcountry.equals("-remove")) {
                String l_countryNameToBeRemoved = p_data[2].toLowerCase();

                if (d_Ge.containsAlphabetsOnly(l_countryNameToBeRemoved)) {

                    boolean l_check = d_Ge.d_RunCommand.removeCountry(d_Ge.d_map, l_countryNameToBeRemoved);
                    if (l_check) {
                        System.out.println(d_Ge.capitalizeString(l_countryNameToBeRemoved)
                                + " country is removed from the Map");
                        d_Ge.d_GamePhase = GamePhase.EDITMAP;
                        d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                    }
                } else {
                    System.out.println("Only '-add' or '-remove' parameter allowed in editcontinent");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: The continentID is not a valid integer.");
        } catch (Exception e) {
            System.out.println(
                    "Command is invalid, please note it is for format 'editcountry -add Denmark Europe' or 'editcountry -remove Europe");
        }

    }

    /**
     * This method edits a continent
     *
     * @param p_data         input command string
     * @param p_continentId  continentId for the continent
     * @param p_controlValue controlValue for the continent
     */
    public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {

        d_Ge.d_LogEntry.setMessage("Command given by user:"+p_data[0]);
        String l_typeOfEditcontinent = p_data[1].toLowerCase(); // -add or -remove
        try {
            String l_continentName = null;
            if (l_typeOfEditcontinent.equals("-add")) {
                l_continentName = p_data[2].toLowerCase();
                // Checking for the country name and I given by the user should be string
                // without any special characters
                try {
                    if (d_Ge.containsAlphabetsOnly(l_continentName)) {
                        // 2nd Index Contains Continent ID like 1 2 3 int number
                        p_controlValue = Integer.parseInt(p_data[3]); // continentID param

                        boolean l_status = d_Ge.d_RunCommand.addContinentToMap(d_Ge.d_map, p_controlValue,
                                l_continentName);
                        if (l_status) {
                            System.out.println(d_Ge.capitalizeString(l_continentName) + " Continent is added");

                            d_Ge.d_GamePhase = GamePhase.EDITMAP;
                            d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                        } else {

                            System.out.println("Given Continent Already exists");
                        }
                    } else {
                        System.out.println("Given continent name not valid!");
                    }
                } catch (NumberFormatException ex) {
                    d_Ge.d_LogEntry.setMessage("Invalid control ID");
                    System.out.println("Control Value is invalid, it should be numeric only");
                }
            } else if (l_typeOfEditcontinent.equals("-remove")) {
                String l_continentNameToBeRemoved = p_data[2].toLowerCase();
                if (d_Ge.containsAlphabetsOnly(l_continentNameToBeRemoved)) {

                    boolean l_check = d_Ge.d_RunCommand.removeContinentFromMap(d_Ge.d_map,
                            l_continentNameToBeRemoved);
                    if (l_check) {
                        System.out.println(d_Ge.capitalizeString(l_continentNameToBeRemoved)
                                + " continent is removed from the Map");
                        d_Ge.d_GamePhase = GamePhase.EDITMAP;
                        d_Ge.setD_GamePhase(d_Ge.d_GamePhase);
                    }
                } else {
                    // condition when parameter is neither add or remove
                    System.out.println("Only '-add' or '-remove' parameter allowed in editcountry");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: The continentID is not a valid integer.");
        } catch (Exception e) {
            System.out.println(
                    "Command is invalid, please note it is for format For E.g. 'editcontinent -add Europe 1' or 'editcontinent -remove Europe");
            System.out.println(e);
        }

    }

    /**
     * this function checks whether map is valid or not.
     */
    public void validatemap()
    {

        if (d_Ge.d_RunCommand.checkGameMap(d_Ge.d_map)) {
            System.out.println("Game map has been checked and it is VALID");
        } else {
            System.out.println("Game map is not a valid map.");
            System.out.println("Possible reasons could be having some unconnected entity in the map");
            System.out.println(
                    "Suggestion! You can run 'showmap' command to see the map and can figure out possible unconnected entities");
        }

    }


}
