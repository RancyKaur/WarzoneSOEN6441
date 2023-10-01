package Controller;

import Model.*;

import java.util.ArrayList;

import static java.lang.System.exit;

public class GameEngine {

    public ArrayList<Player> d_Players;
    private GamePhase d_phase;
    private EngineCommand d_RunCommand;
    private WargameMap d_map;
    private GameStartPhase d_gameStartPhase;

    public GamePhase getD_phase() {
        return d_phase;
    }

    public GameEngine() {
        d_phase = GamePhase.BEGINGAME;
        d_RunCommand = new EngineCommand();
        d_map = new WargameMap();
    }

    /**
     * Checks if map's name is valid
     * The validity checks if name contains alphanumeric characters, no special character is allowed
     */
    private boolean isValidMapName(String p_mapName) {
        return p_mapName != null && p_mapName.matches("^[a-zA-Z0-9]*.map$");
    }

    /**
     * Checks if passed string just has alphbets
     *
     * @param p_string
     * @return true if string has all alphabets or false
     */
    private boolean containsAlphabetsOnly(String p_string) {
        return p_string != null && p_string.matches("^[a-zA-Z]*$");
    }

    /**
     * Ensures string matches the defined criteria of being an Alpha for Names.
     *
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean validatePlayerName(String p_sample) {
        return p_sample != null && p_sample.matches("[a-zA-Z0-9]+");
    }

    /**
     * This method is to display passed string with first letter capitalized
     *
     * @param p_string input string
     * @return Capitalized string
     */
    public static String capitalizeString(String p_string) {

        return p_string.substring(0, 1).toUpperCase() + p_string.substring(1);
    }

    public void addRemovePlayer(String[] l_data, String l_playerName) {
        try {
            for (int i = 1; i < l_data.length; i++) {
                if (l_data[i].equals("-add")) {
                    if (this.validatePlayerName(l_data[i + 1])) {
                        l_playerName = l_data[i + 1];
                        boolean l_check = d_gameStartPhase.addPlayer(d_Players, l_playerName);
                        if (l_check) {
                            System.out.println("Player added!");
                        } else {
                            System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
                        }
                        d_phase = GamePhase.STARTPLAY;
                    } else {
                        System.out.println("Invalid Player Name");
                    }
                } else if (l_data[i].equals("-remove")) {
                    if (this.validatePlayerName(l_data[i + 1])) {
                        l_playerName = l_data[i + 1];
                        boolean l_check = d_gameStartPhase.removePlayer(d_Players, l_playerName);
                        if (l_check)
                            System.out.println("Player removed!");
                        else
                            System.out.println("Player doesn't exist");
                        d_phase = GamePhase.STARTPLAY;
                    } else
                        System.out.println("Invalid Player Name");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
        }
    }

    public void assignCountriesToPlayers() {
        boolean l_check = d_gameStartPhase.assignCountries(d_map, d_Players);
        if (l_check) {
            System.out.println("Countries allocated randomly amongst Players");
            d_phase = GamePhase.ISSUEORDER;
        }

        for (Player p : d_Players) {
            System.out.println("Player:" + p.getPlayerName());
            p.printOwnedCountries();
            p.printOwnedContinents();
        }

        d_phase = GamePhase.ISSUEORDER;
    }

    /**
     * This method parses the command input by the player and then executes methods related to the commands entered
     *
     * @param p_givenCommand - Command entered by the player
     * @return - the next phase of the current game
     */
    public GamePhase parseCommand(String p_givenCommand) {
        String l_playerName = null;
        String[] l_param = p_givenCommand.split("\\s+");
        String l_commandName = l_param[0];
        String l_mapName = null;
        int l_continentControlValue;

        /* conditional execution of phases, games starts with Startgame phase on command editmap or loadmap
           Depending on player's selection it moves to editmap phase or loadmap phase
        */
        if (d_phase == GamePhase.BEGINGAME) {
            switch (l_commandName) {
                /**/
                case "editmap": {
                    try {
                        if (l_param[1] != "") {
                            if (this.isValidMapName(l_param[1])) {
                                l_mapName = l_param[1];
                                System.out.println(" You are editing Map: " + l_mapName);
                                this.d_map = d_RunCommand.editMap(l_mapName);
                                this.d_phase = GamePhase.EDITMAP;
                                this.printEditmapHelpCommands();
                            } else {
                                System.out.println("Sorry, map name is invalid, please try again with AlphaNumeric characters and no extension");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid command, it needs name of the map as a parameter, For E.g. editmap mapname");
                    }
                    break;
                }
                case "stopgame": {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }
                case "loadmap": {
                    try {
                        l_mapName = l_param[1];
                        if (this.isValidMapName(l_mapName)) {
                            d_map = d_RunCommand.loadMap(l_mapName);
                            System.out.printf("Map %s loaded in game memory successfully\nAdd players now\n", l_mapName);
                            this.d_phase = GamePhase.STARTPLAY;
                        } else {
                            System.out.println("Map does not exist! Select a map from our resources or the one you created!");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception:" + e);
                        System.out.println("Invalid command. To load a map from our resources or the one you created, type loadmap <mapname>.map");
                    }
                    break;
                }
                default: {
                    System.out.println("At this phase, only 'editmap' or 'stopgame' commands are accepted");
                }
            }
        } else if (d_phase == GamePhase.EDITMAP) {
            switch (l_commandName) {

                //parsing this type of command here
                // editcontinent -add continentID continentValue OR
                // editcontinent -remove continentID
                // Here countryID and continentID both will be names strings not the numbers

                case "editcontinent": {
                    String l_typeOfEditcontinent = l_param[1].toLowerCase(); // -add or -remove

                    try {
                        String l_continentName = null;

                        if (l_typeOfEditcontinent.equals("-add")) {
                            l_continentName = l_param[2].toLowerCase();
                            //Checking for the country name and I given by the user should be string without any special characters
                            try {
                                if (this.containsAlphabetsOnly(l_continentName)) {
                                    //2nd Index Contains Continent ID like 1 2 3 int number
                                    l_continentControlValue = Integer.parseInt(l_param[3]); //continentID param

                                    boolean l_status = d_RunCommand.addContinentToMap(d_map, l_continentControlValue, l_continentName);
                                    if (l_status) {
                                        System.out.println(capitalizeString(l_continentName) + " Continent is added");
                                        d_phase = GamePhase.EDITMAP;
                                    } else {
                                        System.out.println("Given Continent Already exists");
                                    }
                                } else {
                                    System.out.println("Given continent name not valid!");
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Control Value is invalid, it should be numeric only");
                            }
                        } else if (l_typeOfEditcontinent.equals("-remove")) {
                            String l_continentNameToBeRemoved = l_param[2].toLowerCase();
                            if (this.containsAlphabetsOnly(l_continentNameToBeRemoved)) {

                                boolean l_check = d_RunCommand.removeContinentFromMap(d_map, l_continentNameToBeRemoved);
                                if (l_check) {
                                    System.out.println(capitalizeString(l_continentNameToBeRemoved) + " continent is removed from the Map");
                                    d_phase = GamePhase.EDITMAP;
                                }
                            } else {
                                //condition when parameter is neither add or remove
                                System.out.println("Only '-add' or '-remove' parameter allowed in editcountry");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: The continentID is not a valid integer.");
                    } catch (Exception e) {
                        System.out.println("Command is invalid, please note it is for format For E.g. 'editcontinent -add Europe 1' or 'editcontinent -remove Europe");
                        System.out.println(e);
                    }

                    break;
                }
                //parsing this type of command here
                // editcountry -add countryID continentID OR
                // editcountry -remove countryID
                // Here countryID and continentID both will be names strings not the numbers
                case "editcountry": {
                    try {
                        String l_typeOfEditcountry = l_param[1].toLowerCase(); // -add or -remove
                        String l_userGivenCountryName = l_param[2].toLowerCase(); //countryID param

                        if (l_typeOfEditcountry.equals("-add")) {
                            String l_userGivenContinentName = l_param[3].toLowerCase(); //continentID param
                            //Checking for the country name and I given by the user should be string without any special characters
                            if (this.containsAlphabetsOnly(l_userGivenCountryName) && this.containsAlphabetsOnly(l_userGivenContinentName)) {

                                boolean l_status = d_RunCommand.addCountryToContinent(d_map, l_userGivenCountryName, l_userGivenContinentName);
                                if (l_status) {
                                    System.out.println(capitalizeString(l_userGivenCountryName) + " is successfully added to " + l_userGivenContinentName);
                                    d_phase = GamePhase.EDITMAP;
                                }

                            } else {
                                System.out.println("Provided name(s) not valid!");
                            }
                        } else if (l_typeOfEditcountry.equals("-remove")) {
                            String l_countryNameToBeRemoved = l_param[2].toLowerCase();

                            if (this.containsAlphabetsOnly(l_countryNameToBeRemoved)) {

                                boolean l_check = d_RunCommand.removeCountry(d_map, l_countryNameToBeRemoved);
                                if (l_check) {
                                    System.out.println(capitalizeString(l_countryNameToBeRemoved) + " country is removed from the Map");
                                    d_phase = GamePhase.EDITMAP;
                                }
                            } else {
                                System.out.println("Only '-add' or '-remove' parameter allowed in editcontinent");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: The continentID is not a valid integer.");
                    } catch (Exception e) {
                        System.out.println("Command is invalid, please note it is for format 'editcountry -add Denmark Europe' or 'editcountry -remove Europe");
                    }

                    break;
                }

                case "editneighbor": {
                    try {
                        String l_typeOfEdit = l_param[1];

                        if (this.containsAlphabetsOnly(l_param[2]) && this.containsAlphabetsOnly(l_param[3])) {
                            String l_countryName = l_param[2].toLowerCase();
                            String l_neighborCountryName = l_param[3].toLowerCase();

                            if ("-add".equals(l_typeOfEdit) || "-remove".equals(l_typeOfEdit)) {
                                if (d_map.getCountries().containsKey(l_countryName) && d_map.getCountries().containsKey(l_neighborCountryName)) {
                                    boolean l_success;
                                    if ("-add".equals(l_typeOfEdit)) {
                                        l_success = d_RunCommand.addNeighbour(d_map, l_countryName, l_neighborCountryName);
                                    } else {
                                        l_success = d_RunCommand.removeNeighbour(d_map, l_countryName, l_neighborCountryName);
                                    }
                                    if (l_success) {
                                        d_phase = GamePhase.EDITMAP;
                                    }
                                } else {
                                    System.out.println("It appears that either one or both of the countries do not exist in the map, please create the country first");
                                }
                            } else {
                                System.out.println("Invalid action command, expecting either 'editneighbor -add' or 'editneighbor -remove'");
                            }
                        } else {
                            System.out.println("Invalid Country Names, please provide valid country name");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Command is invalid - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                    } catch (Exception e) {
                        System.out.println("Command is invalid  - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                    }
                    break;
                }

                // case that checks if the map is valid by checking if it is connected graph
                case "validatemap": {
                    if (d_RunCommand.checkGameMap(d_map)) {
                        System.out.println("Game map has been checked and it is VALID");
                    } else {
                        System.out.println("Game map is not a valid map.");
                        System.out.println("Possible reasons could be having some unconnected entity in the map");
                        System.out.println("Suggestion! You can run 'showmap' command to see the map and can figure out possible unconnected entities");
                    }

                    break;

                }

                // command used to save the map object created on to the file disk
                // saved maps are being stored at /resources/maps
                case "savemap": {
                    try {
                        if (l_param[1] != "") {
                            if (this.isValidMapName(l_param[1])) {
                                l_mapName = l_param[1];
                                boolean l_check = d_RunCommand.saveMap(d_map, l_mapName);
                                if (l_check) {
                                    System.out.println("Map file has been saved successfully");
                                    d_phase = GamePhase.EDITMAP;
                                } else
                                    System.out.println("Error in saving the map, as it is invalid");
                            } else
                                System.out.println("Map name is not valid, please ensure you do not put any extension");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
                    }
                    break;
                }

                //command for showing the map
                case "showmap": {
                    d_RunCommand.showMap(d_map);
                    d_phase = GamePhase.EDITMAP;
                    break;
                }

                case "loadmap": {
                    try {
                        l_mapName = l_param[1];
                        if (this.isValidMapName(l_mapName)) {
                            d_map = d_RunCommand.loadMap(l_mapName);
                            System.out.printf("Map %s loaded in game memory successfully\n", l_mapName);
                        } else {
                            System.out.println("Map does not exist! Select a map from our resources or the one you created!");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid command. To load a map from our resources or the one you created, type loadmap <mapname>.map");
                    }
                    break;
                }

                // command to stop and exit from the game
                case "stopgame": {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }

                //case default invalidcommand
                default: {
                    System.out.println("Invalid command, please check spelling or case sensitivity, commands are in lower case");
                    d_phase = GamePhase.EDITMAP;
                }
            }
        } else if (d_phase == GamePhase.STARTPLAY) {
            switch (l_commandName) {
                case "gameplayer": {
                    System.out.println("reached!");
                    addRemovePlayer(l_param, l_playerName);
                    break;
                }
                case "assigncountries": {
                    assignCountriesToPlayers();
                    break;
                }
            }
        } else if (d_phase == GamePhase.ENDGAME) {
            System.out.println("Stopping the game as requested");
            exit(0);
        }
        System.out.println("reached 2");
        return d_phase;
    }

    private void printEditmapHelpCommands() {
        System.out.println();
        System.out.println("To add continents: editcontinent -add <continentName> <ControlValue>, E.g. editcontinent -add Europe 1");
        System.out.println("To add country: editcountry -add <countryName> <continentName>, E.g. editcountry -add Germany Europe");
        System.out.println("To add neighbor relation: editneighbor -add <countryName> <countryName>, E.g. editneighbor -add Germany Denmark");
        System.out.println("To save map file: savemap <map file name without extension>, E.g. savemap AsiaMap");
        System.out.println("Waiting for next command..");
    }
}
