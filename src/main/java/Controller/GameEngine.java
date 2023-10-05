package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

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

    public void setD_phase(GamePhase d_phase) {
        this.d_phase = d_phase;
    }

    public GameEngine() {
        d_phase = GamePhase.BEGINGAME;
        d_RunCommand = new EngineCommand();
        d_map = new WargameMap();
        d_gameStartPhase = new GameStartPhase();
        d_Players = new ArrayList<>();
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

    /**
     * Ensures string matches the defined criteria of being a Numeric for ID.
     *
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isNumeric(String p_sample) {
        return p_sample != null && p_sample.matches("[0-9]+");
    }

    public void addRemovePlayer(String[] l_data) {
        String l_playerName=null;
        try {
            for (int i = 1; i < l_data.length - 1; i++) {
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
            System.out.println("Error:" + e);
            System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
        }
    }

    public void assignCountriesToPlayers() {
        boolean l_check = d_gameStartPhase.assignCountries(d_map, d_Players);
        if (l_check) {
            System.out.println("Countries allocated randomly amongst Players");
            this.setD_phase(GamePhase.ISSUEORDER);
        }


        this.setD_phase(GamePhase.ISSUEORDER);

    }

    /**
     * This method parses the command input by the player and then executes methods related to the commands entered
     *
     * @param p_givenCommand - Command entered by the player
     * @return - the next phase of the current game
     */
    public GamePhase parseCommand(Player p_player, String p_givenCommand) {
        String l_playerName = null;
        String[] l_param = p_givenCommand.split("\\s+");
        String l_commandName = l_param[0];
        String l_mapName = null;
        int l_numberOfArmies = 0;
        String l_continentId = null;
        String l_countryId = null;
        String l_neighborCountryId = null;
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
                                this.d_phase = GamePhase.EDITMAP;
                                //setting up the map object can be newly created if the user given map name does not exists
                                this.d_map = d_RunCommand.editMap(l_mapName);

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
                            this.d_phase = GamePhase.STARTPLAY;
                            d_map = d_RunCommand.loadMap(l_mapName);
                            System.out.printf("Map %s loaded in game memory successfully\n", l_mapName);
                            System.out.println("Now you are have to follow two following steps IN ORDER");
                            System.out.println("Step 1: add the players to the game by using gameplayer -add <playername>. There total number of players must be between 2 and 6");
                            System.out.println("\t You can also remove the player by replacing -add to -remove exmaple gameplayer -remove <playername>");
                            System.out.println("Step 2: issue 'assigncountries' command to initially assign the countries randomly to all the players");

                        } else {
                            System.out.println("Map does not exist! Select a map from our resources or the one you created!");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception:" + e);
                        System.out.println("Invalid command. To load a map from our resources or the one you created, type loadmap <mapname>.map");
                    }
                    break;
                }

                //command for showing the map
                case "showmap": {
                    try {
                        WargameMap d_temp_map;
                        l_mapName = l_param[1];
                        if (this.isValidMapName(l_mapName)) {
                            this.d_phase = GamePhase.STARTPLAY;
                            d_temp_map = d_RunCommand.loadMap(l_mapName);
                            d_RunCommand.showMap(d_temp_map);
                            d_phase = GamePhase.BEGINGAME;
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
                    System.out.println("At this phase, only 'editmap', 'loadmap'  or 'stopgame' commands are accepted");
                }
            }
        } else if (d_phase == GamePhase.EDITMAP) {
            System.out.println("When you done creating map Do not forget to save the map using the command 'savemap <map name>'");
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
                                    d_phase = GamePhase.BEGINGAME;
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
                    addRemovePlayer(l_param);
                    break;
                }
                case "assigncountries": {
                    if(d_Players.size()<2) {
                        System.out.println("Not enough players in the game. At least two players should be in the game to assign the countries and start the game.");
                    }
                    else{
                        assignCountriesToPlayers();
                    }

                    break;
                }
                //command for showing the map
                case "showmap": {
                    d_RunCommand.showMap(d_map);
                    d_phase = GamePhase.STARTPLAY;
                    break;
                }
                // command to stop and exit from the game
                case "stopgame": {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }



                default: {
                    System.out.println("Invalid command.Type the correct command in StartPlay phase!");
                }
            }
        } else if (d_phase == GamePhase.ISSUEORDER) {
            int l_counter = 0;
            Iterator<Player> l_itr = d_Players.listIterator();
            while (l_itr.hasNext()) {
                Player l_p = l_itr.next();
                System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmies() + " Armies currently!");
                if (l_p.getOwnedArmies() > 0) {
                    l_counter = l_counter + l_p.getOwnedArmies();
                }
            }
            System.out.println("Total Armies left with all Players in Pool: " + l_counter);
            if (l_counter > 0) {
                switch (l_commandName) {
                    case "deploy":
                        try {
                            if (!(l_param[1] == null) || !(l_param[2] == null)) {
                                if (this.isNumeric(l_param[1]) || this.isNumeric(l_param[2])) {
                                    l_countryId = l_param[1];
                                    l_numberOfArmies = Integer.parseInt(l_param[2]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                    boolean l_checkArmies = (p_player.getOwnedArmies() >= l_numberOfArmies);
                                    System.out.println("Player " + p_player.getPlayerName() + " Can provide deploy order or pass order");
                                    if (l_checkOwnedCountry && l_checkArmies) {
                                        ExecuteOrders l_temp = new ExecuteOrders(p_player, l_countryId, l_numberOfArmies);
                                        p_player.addOrder(l_temp);
                                        p_player.issue_order();
                                        p_player.setOwnedArmies(p_player.getOwnedArmies() - l_numberOfArmies);
                                        System.out.println("Player " + p_player.getPlayerName() + " now has " + p_player.getOwnedArmies() + " Army units left!");
                                    } else {
                                        System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                                    }
                                    d_phase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        } catch (Exception e) {
                            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                        }
                        break;

                    case "pass":
                        try {
                            d_phase = GamePhase.TAKETURN;
                        } catch (Exception e) {
                            System.out.println("Invalid Command - it should be of the form -> deploy countryID num | pass");
                        }
                        break;

                    case "showmap":
                        d_gameStartPhase.showMap(d_Players, d_map);
                        System.out.println("Please run 'deploy <countryName> #_of_armies'");
                        break;

                    // command to stop and exit from the game
                    case "stopgame": {
                        this.d_phase = GamePhase.ENDGAME;
                        System.out.println("Stopping the game as requested");
                        exit(0);
                    }

                    default:
                        System.out.println("Invalid command - either use deploy | pass | showmap commands in ISSUE_ORDERS Phase");
                        break;
                }
            } else {
                System.out.println("press ENTER to continue to execute Phase..");
                d_phase = GamePhase.EXECUTEORDER;
                return d_phase;
            }
        }
        //EXECUTE_ORDERS Phase
        //EXECUTE ORDERS : execute, showmap
        else if (d_phase.equals(GamePhase.EXECUTEORDER)) {
            switch (l_commandName) {
                case "execute":
                    int l_count = 0;
                    // get count of total orders for all players
                    for (Player l_p : d_Players) {
                        Queue<ExecuteOrders> l_templist = l_p.getD_orderList();
                        l_count = l_count +l_templist.size();
                    }

                    if(l_count == 0){
                        System.out.println("All orders are already executed!");
                        d_gameStartPhase.showMap(d_Players, d_map);
                        d_phase = GamePhase.ISSUEORDER;
                        return d_phase;
                    }
                    else{
                        System.out.println("Total Orders in Queue are : " + l_count);
                        while (l_count != 0) {
                            for (Player l_p : d_Players) {

                                Queue<ExecuteOrders> l_tempOrderList = l_p.getD_orderList();
                                if (l_tempOrderList.size() > 0) {
                                    ExecuteOrders l_toRemove = l_p.next_order();
                                    System.out.println("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                    l_toRemove.execute();
                                }
                            }
                            l_count--;
                        }

                        System.out.println("Orders executed!");
                        d_gameStartPhase.showMap(d_Players, d_map);
                        d_phase = GamePhase.ISSUEORDER;
                    }
//                    System.out.println("Type Exit to end the game");
                    break;

                case "showmap":
                    d_gameStartPhase.showMap(d_Players, d_map);
                    break;

                case "exit":
                    System.out.println("Build 1 ENDS HERE!");
                    exit(0);

                    // command to stop and exit from the game
                case "stopgame": {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }
                default:
                    System.out.println("Execute Order Phase has commenced, either use showmap | execute");
                    break;
            }
        }

        else if (d_phase == GamePhase.ENDGAME) {
            System.out.println("Stopping the game as requested");
            exit(0);
        }
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
