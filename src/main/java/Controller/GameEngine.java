package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import static java.lang.System.exit;

/** This class manages game phases, player actions, and map editing. */

public class GameEngine {

    public ArrayList<Player> d_Players;

    public EngineCommand d_RunCommand;
    public WargameMap d_map;
    public StartUp d_gameStartUpPhase;

    public LogEntry d_LogEntry;
    public LogWriter d_LogWriter;
    public GamePhase d_GamePhase;


    public Phase d_Phase;



    public void setD_GamePhase(GamePhase d_GamePhase) {
        this.d_GamePhase = d_GamePhase;
    }

    /**
     * Setting up the phase in game
     * @param p_Phase is the phase that is to be set
     */
    public void setPhase(Phase p_Phase)
    {
        d_Phase = p_Phase;
    }

    public GameEngine() {
        d_GamePhase = GamePhase.BEGINGAME;
        d_RunCommand = new EngineCommand();
        d_map = new WargameMap();
        d_LogEntry = new LogEntry();
        d_Players = new ArrayList<Player>();
        d_LogWriter = new LogWriter();
        d_LogEntry.attachObserver(d_LogWriter);
    }

    /**
     * Checks if map's name is valid
     * The validity checks if name contains alphanumeric characters, no special
     * character is allowed
     */
    public boolean isValidMapName(String p_mapName) {
        return p_mapName != null && p_mapName.matches("^[a-zA-Z0-9]*.map$");
    }

    /**
     * Checks if passed string just has alphbets
     *
     * @param p_string
     * @return true if string has all alphabets or false
     */
    public boolean containsAlphabetsOnly(String p_string) {
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
     * Ensures string matches the defined criteria of being an Alpha for Names.
     * 
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isAlphabetic(String p_sample) {
        return p_sample != null && p_sample.matches("^[a-zA-Z-_]*$");
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
        String l_playerName = null;
        try {
            for (int i = 1; i < l_data.length - 1; i++) {
                if (l_data[i].equals("-add")) {
                    if (this.validatePlayerName(l_data[i + 1])) {
                        l_playerName = l_data[i + 1];
                        boolean l_check = d_gameStartUpPhase.addPlayer(d_Players, l_playerName);
                        if (l_check) {
                            System.out.println("Player added!");
                        } else {
                            System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
                        }
                        d_GamePhase = GamePhase.STARTPLAY;
                    } else {
                        System.out.println("Invalid Player Name");
                    }
                } else if (l_data[i].equals("-remove")) {
                    if (this.validatePlayerName(l_data[i + 1])) {
                        l_playerName = l_data[i + 1];
                        boolean l_check = d_gameStartUpPhase.removePlayer(d_Players, l_playerName);
                        if (l_check)
                            System.out.println("Player removed!");
                        else
                            System.out.println("Player doesn't exist");
                        d_GamePhase = GamePhase.STARTPLAY;
                    } else
                        System.out.println("Invalid Player Name");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    "Invalid command - it should be of the form gameplayer -add playername -remove playername");
        } catch (Exception e) {
            System.out.println("Error:" + e);
            System.out.println(
                    "Invalid command - it should be of the form gameplayer -add playername -remove playername");
        }
    }

    public void assignCountriesToPlayers() {
        boolean l_check = d_gameStartUpPhase.assignCountries(d_map, d_Players);
        if (l_check) {
            System.out.println("Countries allocated randomly amongst Players");
            this.setD_GamePhase(GamePhase.ISSUEORDER);
        }

        this.setD_GamePhase(GamePhase.ISSUEORDER);

    }

    /**
     * This method parses the command input by the player and then executes methods
     * related to the commands entered
     *
     * @param p_givenCommand - Command entered by the player
     * @return - the next phase of the current game
     */
    public GamePhase parseCommand(Player p_player, String p_givenCommand) {
        String l_playerName = null;
        int l_controlValue = 0;
        String[] l_param = p_givenCommand.split("\\s+");
        String l_commandName = l_param[0];
        String l_mapName = null;
        int l_numberOfArmies = 0;
        String l_continentId = null;
        String l_countryId = null;
        String l_neighborCountryId = null;
        int l_continentControlValue;

        String l_countryNameFrom = null;
        String l_countryNameTo = null;
        String[] l_data = p_givenCommand.split("\\s+");

        /*
         * conditional execution of phases, games starts with Startgame phase on command
         * editmap or loadmap
         * Depending on player's selection it moves to editmap phase or loadmap phase
         */

        if (d_GamePhase == GamePhase.BEGINGAME) {
            setPhase(new MapEditorLoad(this));
            switch (l_commandName) {
                /**/
                case "editmap": {
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.editMap(l_data, l_mapName);
                    break;
                }
                case "stopgame": {
                    this.d_GamePhase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    d_LogEntry.setCommand("Stopping the game as requested");
                    exit(0);
                }
                case "loadmap": {
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.loadMap(l_data,l_mapName);
                    String str1=d_Phase.getD_PhaseName();
                    break;
                }

                // command for showing the map
                case "showmap": {
                    try {
                        WargameMap d_temp_map;
                        l_mapName = l_param[1];
                        if (this.isValidMapName(l_mapName)) {
                            d_temp_map = d_RunCommand.loadMap(l_mapName);
                            d_RunCommand.showMap(d_temp_map);
                            this.d_GamePhase = GamePhase.BEGINGAME;
                        } else {
                            System.out.println(
                                    "Map does not exist! Select a map from our resources or the one you created!");
                        }
                    } catch (Exception e) {
                        System.out.println("Exception:" + e);
                        System.out.println(
                                "Invalid command. To load a map from our resources or the one you created, type loadmap <mapname>.map");
                    }

                    break;
                }

                default: {
                    System.out.println(
                            "At this phase, only 'editmap', 'showmap', 'loadmap'  or 'stopgame' commands are accepted");
                }
            }
        } else if (d_GamePhase == GamePhase.EDITMAP) {
            System.out.println(
                    "When you done creating map Do not forget to save the map using the command 'savemap <map name>'");
            switch (l_commandName) {

                // parsing this type of command here
                // editcontinent -add continentID continentValue OR
                // editcontinent -remove continentID
                // Here countryID and continentID both will be names strings not the numbers

                case "editcontinent": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.editContinent(l_data, l_continentId, l_controlValue);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
                    break;
                }
                // parsing this type of command here
                // editcountry -add countryID continentID OR
                // editcountry -remove countryID
                // Here countryID and continentID both will be names strings not the numbers
                case "editcountry": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.editCountry(l_data, l_continentId, l_countryId) ;
                    d_LogEntry.setGamePhase(d_Phase);
                    String str1=d_Phase.getD_PhaseName();
                    System.out.println(str1);
                    break;
                }

                case "editneighbor": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.editNeighbour(l_data, l_countryId, l_neighborCountryId);
                    String str2=d_Phase.getD_PhaseName();
                    System.out.println(str2);
                    break;
                }

                // case that checks if the map is valid by checking if it is connected graph
                case "validatemap": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.validatemap();
                    String strValidate=d_Phase.getD_PhaseName();
                    System.out.println(strValidate);
                    break;

                }

                // command used to save the map object created on to the file disk
                // saved maps are being stored at /resources/maps
                case "savemap": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.saveMap(l_data, l_mapName);
                    String str3=d_Phase.getD_PhaseName();
                    System.out.println(str3);
                    break;
                }

                // command for showing the map
                case "showmap": {
                    setPhase(new MapEditorLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.showMap(d_map);
                    String str4=d_Phase.getD_PhaseName();
                    System.out.println(str4);
                    d_GamePhase = GamePhase.EDITMAP;
                    break;
                }

                // command to stop and exit from the game
                case "stopgame": {
                    this.d_GamePhase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    d_LogEntry.setCommand("Stopping the game as requested");
                    exit(0);
                }

                // case default invalidcommand
                default: {
                    System.out.println("Invalid command, please check spelling or case sensitivity, commands are in lower case");
                    d_LogEntry.setCommand("Invalid command, please check spelling or case sensitivity, commands are in lower case");
                    this.d_GamePhase = GamePhase.EDITMAP;
                }
            }
        } else if (this.d_GamePhase == GamePhase.STARTPLAY) {
            switch (l_commandName) {
                case "gameplayer": {

                    setPhase(new StartUp(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" command entered");
                    d_Phase.gamePlayer(l_data,d_Players, l_playerName);
                    String str=d_Phase.getD_PhaseName();
                    break;
                }
                case "assigncountries": {
                    if (d_Players.size() < 2) {
                        System.out.println(
                                "Not enough players in the game. At least two players should be in the game to assign the countries and start the game.");
                    } else {
                        boolean l_check = d_Phase.assignCountries(d_map, d_Players);
                        if (l_check) {
                            setPhase(new MainPlay(this));
                            d_LogEntry.setGamePhase(d_Phase);
                            d_LogEntry.setCommand(l_commandName+" command entered");
                            System.out.println("Countries allocated randomly amongst Players");
                            d_LogEntry.setMessage("Countries allocated randomly amongst Players");
                            d_Phase.reinforce();
                            String str1=d_Phase.getD_PhaseName();
                            d_GamePhase = GamePhase.ISSUEORDER;
                        }
                        d_GamePhase = GamePhase.ISSUEORDER;
                    }

                    break;
                }
                // command for showing the map
                case "showmap": {
                    d_GamePhase = GamePhase.STARTPLAY;
                    d_Phase.showMap(d_Players,d_map);
                    String str2=d_Phase.getD_PhaseName();
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" is executed");
                    break;
                }
                // command to stop and exit from the game
                case "stopgame": {
                    this.d_GamePhase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    d_LogEntry.setCommand("Stopping the game as requested");
                    exit(0);
                }

                default: {
                    System.out.println("Invalid command.Type the correct command in StartPlay phase!");
                }
            }
        } else if (d_GamePhase == GamePhase.ISSUEORDER) {
            int l_counter = 0;
            Iterator<Player> l_itr = d_Players.listIterator();
            while (l_itr.hasNext()) {
                Player l_p = l_itr.next();
                if (l_p.getOwnedArmies() > 0) {
                    l_counter = l_counter + l_p.getOwnedArmies();
                }
            }
            System.out.println();

            if (l_counter > 0) {
                switch (l_commandName) {
                    case "deploy":
                        try {

                            if (!(l_param[1] == null) || !(l_param[2] == null)) {
                                if (this.isNumeric(l_param[1]) || this.isNumeric(l_param[2])) {


                                    setPhase(new IssueOrderPhase(this));

                                    l_countryId = l_param[1];
                                    l_numberOfArmies = Integer.parseInt(l_param[2]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries()
                                            .containsKey(l_countryId.toLowerCase());
                                    boolean l_checkArmies = (p_player.getOwnedArmies() >= l_numberOfArmies);
                                    // System.out.println("Player " + p_player.getPlayerName() + " Can provide
                                    // deploy order or pass order");
                                    if (l_checkOwnedCountry && l_checkArmies) {
                                        Deploy l_temp = new Deploy(p_player, l_countryId, l_numberOfArmies);
                                        p_player.addOrder(l_temp);
                                        //p_player.issue_order();

                                        d_Phase.issue_order(p_player);
                                        p_player.setOwnedArmies(p_player.getOwnedArmies() - l_numberOfArmies);
                                        // System.out.println("Player " + p_player.getPlayerName() + " NOW has " +
                                        // p_player.getOwnedArmies() + " Army units left!");
                                    } else {
                                        System.out.println(
                                                "Country not owned by player or insufficient Army units | please pass to next player");
                                        d_LogEntry.setCommand("Country not owned by player or insufficient Army units | please pass to next player");
                                    }

                                    //below code block to avoid extra turn when all armies are deployed
                                    l_itr=d_Players.listIterator();
                                    int l_tempcounter=0;
                                    while(l_itr.hasNext()){
                                        Player l_p=l_itr.next();
                                        if(l_p.getOwnedArmies()>0){
                                            l_tempcounter=l_tempcounter+l_p.getOwnedArmies();
                                        }
                                    }
                                    if(l_tempcounter==0){
                                        System.out.println("press ENTER to continue to execute Phase..");
                                        d_GamePhase =GamePhase.EXECUTEORDER;
                                        return d_GamePhase;
                                    }

                                    d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        } catch (Exception e) {
                            System.out.println(
                                    "Country not owned by player or insufficient Army units | please pass to next player");
                        }
                        break;

                    case "advance":
                        d_LogEntry.setCommand(l_commandName + " command entered");
                        try {

                            setPhase(new IssueOrderPhase(this));

                            if (!(l_data[1] == null) || !(l_data[2] == null) || !(l_data[3] == null)) {
                                if (this.isAlphabetic(l_data[1]) || this.isAlphabetic(l_data[2])
                                        || this.isNumeric(l_data[3])) {
                                    l_countryNameFrom = l_data[1];
                                    l_countryNameTo = l_data[2];
                                    l_numberOfArmies = Integer.parseInt(l_data[3]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries()
                                            .containsKey(l_countryNameFrom.toLowerCase());
                                    Country attackingCountry = p_player.getOwnedCountries()
                                            .get(l_countryNameFrom.toLowerCase());
                                    Country defendingCountry = attackingCountry.getNeighbours()
                                            .get(l_countryNameTo.toLowerCase());
                                    boolean l_checkNeighbourCountry = (l_countryNameTo
                                            .equals(defendingCountry.getCountryName()));

                                    // Checks if required armies present on Source territory
                                    Country l_c = p_player.getOwnedCountries().get(l_countryNameFrom.toLowerCase());
                                    int l_existingArmies = l_c.getNumberOfArmies();

                                    Player l_targetPlayer = null;
                                    for (Player temp : d_Players) {
                                        // check which player has target countryID
                                        if (temp.getOwnedCountries().containsKey(l_countryNameTo.toLowerCase())) {
                                            l_targetPlayer = temp;
                                            break;
                                        }
                                    }

                                    boolean l_checkArmies = (l_existingArmies >= l_numberOfArmies);
                                    if (l_checkOwnedCountry && l_checkNeighbourCountry && l_checkArmies) {
                                        p_player.addOrder(new Advance(p_player, l_countryNameFrom, l_countryNameTo,
                                                l_numberOfArmies, l_targetPlayer));
                                        //p_player.issue_order();
                                        d_Phase.issue_order(p_player);
                                        System.out.println("For player " + p_player.getPlayerName()
                                                + " advance order added to Players OrdersList: " + l_data[0] + "  "
                                                + l_data[1] + " " + l_data[2]);
                                        d_LogEntry.setMessage("For player " + p_player.getPlayerName()
                                                + " advance order added to Players OrdersList: " + l_data[0] + "  "
                                                + l_data[1] + " " + l_data[2]);
                                    } else {
                                        System.out.println(
                                                "Either given country is not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                        d_LogEntry.setMessage(
                                                "Either given country is not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                    }
                                    this.d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command given");
                            d_LogEntry.setMessage("Invalid Command given");
                        } catch (Exception e) {
                            System.out.println(
                                    "Either given country is not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                            d_LogEntry.setMessage(
                                    "Either given country is not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                        }
                        break;

                    case "pass":
                        try {
                            setPhase(new IssueOrderPhase(this));
                            d_GamePhase = GamePhase.TAKETURN;
                        } catch (Exception e) {
                            System.out.println(
                                    "Invalid Command - it should be of the form -> deploy countryID num | pass");
                        }
                        break;

                    case "showmap":
                        setPhase(new MainPlay(this));
                        d_Phase.showMap(d_Players, d_map);
                        System.out.println("Please run 'deploy <countryName> #_of_armies' or 'pass' to pass your turn");
                        break;

                    // command to stop and exit from the game
                    case "stopgame": {
                        this.d_GamePhase = GamePhase.ENDGAME;
                        System.out.println("Stopping the game as requested");
                        exit(0);
                    }

                    case "bomb":
                        System.out.println(l_commandName + " command entered");
                        try {
                            if (!(l_param[1] == null)) {
                                if (this.isAlphabetic(l_param[1])) {
                                    setPhase(new IssueOrderPhase(this));
                                    l_countryId = l_param[1];
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries()
                                            .containsKey(l_countryId.toLowerCase());
                                    boolean checkCard = p_player.checkCardExists("Bomb");
                                    if (l_checkOwnedCountry && checkCard) {
                                        // Create a Bomb order
                                        Bomb bombOrder = new Bomb(p_player, l_countryId);

                                        // Add the Bomb order to the current player's order list
                                        p_player.addOrder(bombOrder);

                                        //p_player.issue_order();
                                        d_Phase.issue_order(p_player);

                                        System.out.println("For player " + p_player.getPlayerName()
                                                + " Bomb order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1]);
                                        d_LogEntry.setMessage("For player " + p_player.getPlayerName()
                                                + " Bomb order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1]);
                                        p_player.removeCard("Bomb");
                                        System.out.println("Bomb card used hence it is now removed from Player's cardList ");
                                        d_LogEntry.setMessage("Bomb card used hence it is now removed from Player's cardList ");
                                    } else {
                                        System.out.println(
                                                "Bomb Card not owned or Country not owned by current player | please pass to next player");
                                        d_LogEntry.setMessage(
                                                "Bomb Card not owned or Country not owned by the current player | please pass to the next player");
                                    }
                                    this.d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else {
                                System.out.println("Invalid Command");
                            }
                        } catch (Exception e) {
                            System.out.println(
                                    "Bomb Card is not Owned or Country not owned by current player | please pass to next player");
                            d_LogEntry.setMessage(
                                    "Bomb Card is not Owned or Country not owned by current player | please pass to the next player");
                        }
                        break;


                    case "airlift":
                        System.out.println(l_commandName + " command entered");
                        try {
                            if (!(l_param[1] == null) && !(l_param[2] == null) && !(l_param[3] == null)) {
                                if (this.isAlphabetic(l_param[1]) && this.isNumeric(l_param[2]) && this.isNumeric(l_param[3])) {
                                    setPhase(new IssueOrderPhase(this));
                                    String sourceCountryId = l_param[1];
                                    int numArmiesToAirlift = Integer.parseInt(l_param[2]);
                                    String targetCountryId = l_param[3];

                                    boolean sourceCountryOwned = p_player.getOwnedCountries()
                                            .containsKey(sourceCountryId.toLowerCase());
                                    boolean targetCountryOwned = p_player.getOwnedCountries()
                                            .containsKey(targetCountryId.toLowerCase());
                                    boolean checkCard = p_player.checkCardExists("Airlift");

                                    if (sourceCountryOwned && targetCountryOwned && checkCard) {
                                        // Create an Airlift order
                                        Airlift airliftOrder = new Airlift(p_player, sourceCountryId, numArmiesToAirlift, targetCountryId);

                                        // Add the Airlift order to the current player's order list
                                        p_player.addOrder(airliftOrder);

                                        //p_player.issue_order();
                                        d_Phase.issue_order(p_player);

                                        System.out.println("For player " + p_player.getPlayerName()
                                                + " Airlift order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1] + " " + l_param[2] + " " + l_param[3]);
                                        d_LogEntry.setMessage("For player " + p_player.getPlayerName()
                                                + " Airlift order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1] + " " + l_param[2] + " " + l_param[3]);
                                        p_player.removeCard("Airlift");
                                        System.out.println("Airlift card used hence it is now removed from Player's cardList ");
                                        d_LogEntry.setMessage("Airlift card used hence it is now removed from Player's cardList ");
                                    } else {
                                        System.out.println(
                                                "Airlift Card not owned or Source/Target country not owned by current player | please pass to next player");
                                        d_LogEntry.setMessage(
                                                "Airlift Card not owned or Source/Target country not owned by current player | please pass to the next player");
                                    }
                                    this.d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else {
                                System.out.println("Invalid Command");
                            }
                        } catch (Exception e) {
                            System.out.println(
                                    "Airlift Card is not Owned or Source/Target country not owned by current player | please pass to next player");
                            d_LogEntry.setMessage(
                                    "Airlift Card is not Owned or Source/Target country not owned by current player | please pass to the next player");
                        }
                        break;

                    case "blockade":
                        System.out.println(l_commandName + " command entered");
                        try {
                            if (!(l_param[1] == null)) {
                                if (this.isAlphabetic(l_param[1])) {
                                    setPhase(new IssueOrderPhase(this));
                                    l_countryId = l_param[1];
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries()
                                            .containsKey(l_countryId.toLowerCase());
                                    boolean checkCard = p_player.checkCardExists("Blockade");
                                    if (l_checkOwnedCountry && checkCard) {
                                        p_player.addOrder(new Blockade(p_player, l_countryId));
                                        //p_player.issue_order();
                                        d_Phase.issue_order(p_player);
                                        System.out.println("For player " + p_player.getPlayerName()
                                                + " Blockade order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1]);
                                        d_LogEntry.setMessage("For player " + p_player.getPlayerName()
                                                + " Blockade order added to Players OrdersList: " + l_param[0] + "  "
                                                + l_param[1]);
                                        p_player.removeCard("Blockade");
                                        System.out.println("Blockade card used hence it is now removed from Player's cardList ");
                                        d_LogEntry.setMessage("Blockade card used hence it is now removed from Player's cardList ");
                                    } else {
                                        System.out.println(
                                                "Blockade Card not owned or Country not owned by current player | please pass to next player");
                                        d_LogEntry.setMessage(
                                                "Blockade Card not qwned or Country not owned by current player | please pass to next player");
                                    }
                                    this.d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            }
                            else
                            {
                                System.out.println("Invalid Command");
                            }

                        } catch (Exception e) {
                            System.out.println(
                                    "Blockade Card is not Owned or Country not owned by current player | please pass to next player");
                            d_LogEntry.setMessage(
                                    "Blockade Card is not Owned or Country not owned by current player | please pass to next player");
                        }
                        break;

                    case "negotiate":
                        d_LogEntry.setCommand(l_commandName+" command given");
                        try {
                            if (!(l_data[1] == null)){
                                if (this.isAlphabetic(l_data[1])) {
                                    Player l_NegPlayer = fetchPlayerByName(l_data[1]);
                                    boolean checkCard = p_player.checkCardExists("Diplomacy");
                                    if(checkCard){
                                        p_player.addOrder(new Negotiate(p_player, l_NegPlayer));
                                        p_player.issue_order();
                                        System.out.println("For player " + p_player.getPlayerName()+" Diplomacy order added to Players OrdersList: "+l_data[0]+"  "+l_data[1]+" "+l_data[2]+" "+l_data[3]);
                                        d_LogEntry.setMessage("For player " + p_player.getPlayerName()+" Diplomacy order added to Players OrdersList: "+l_data[0]+"  "+l_data[1]+" "+l_data[2]+" "+l_data[3]);
                                        p_player.removeCard("Diplomacy");
                                        d_LogEntry.setMessage("Diplomacy card used hence it is now removed from Player's cardList ");
                                    }
                                    else{
                                        System.out.println("Diplomacy Card is not  by the player");
                                        d_LogEntry.setMessage("Diplomacy Card is not  by the player");
                                    }
                                    this.d_GamePhase = GamePhase.TAKETURN;
                                    break;
                                }
                            } else {
                                System.out.println("Invalid Country entered");
                                d_LogEntry.setMessage("Invalid Country entered");
                            }
                        }catch (Exception e) {
                            System.out.println("Either Diplomacy Card not Owned or Invalid Player name");
                            d_LogEntry.setMessage("Either Diplomacy Card not Owned or Invalid Player name");
                        }
                        break;

                    default:
                        System.out.println(
                                "Invalid command entered in ISSUE_ORDERS Phase");
                        break;
                }
            } else {
                System.out.println("press ENTER to continue to execute Phase..");
                d_GamePhase = GamePhase.EXECUTEORDER;
                return d_GamePhase;
            }
        }
        // EXECUTE_ORDERS Phase
        // EXECUTE ORDERS : execute, showmap
        else if (d_GamePhase.equals(GamePhase.EXECUTEORDER)) {
            d_LogEntry.setMessage("Entered Execute Orders Phase:");
            switch (l_commandName) {
                case "execute":
                    setPhase(new MainPlay(this));
                    d_LogEntry.setMessage("Orders in process of being executed for all players");
                    int l_count = 0;
                    // get count of total orders for all players
                    for (Player l_p : d_Players) {
                        Queue<Order> l_templist = l_p.getD_orderList();
                        l_count = l_count + l_templist.size();
                    }

                    if (l_count == 0) {
                        System.out.println("No order left to execute, all orders have been executed!");
                        d_LogEntry.setMessage("No order left to execute, all orders have been executed!");
                        d_gameStartUpPhase.showMap(d_Players, d_map);
                        d_GamePhase = GamePhase.ISSUEORDER;
                        return d_GamePhase;
                    } else {
                        System.out.println("Total Orders in Queue are : " + l_count);
                        d_LogEntry.setMessage("Total Orders in Queue are : " + l_count);
                        while (l_count != 0) {
                            for (Player l_p : d_Players) {

                                Queue<Order> l_tempOrderList = l_p.getD_orderList();
                                if (l_tempOrderList.size() > 0) {
                                    Order l_toRemove = l_p.next_order();
                                    System.out.println("Order executed for player: " + l_p.getPlayerName());
                                    d_LogEntry.setMessage("Order executed for player: " + l_p.getPlayerName());
                                    l_toRemove.execute();
                                }
                            }
                            l_count--;
                        }

                        for(Player l_p : d_Players) {
                            System.out.println("Negotiations reset since turn has ended");
                            d_LogEntry.setMessage("Negotiations reset since turn has ended");
                            l_p.removeAllNegotiators();
                        }
                        System.out.println("All Orders executed!");
                        d_LogEntry.setMessage("All Orders executed!");
                        d_Phase.showMap(d_Players,d_map);

                        /** Below block needs to be refactored as part of State Pattern **/
                        Iterator<Player> l_playerItr = d_Players.listIterator();
                        while(l_playerItr.hasNext()) {
                            Player p = l_playerItr.next();
                            ReinforcePlayers.assignReinforcementArmies(p);
                        }
                        //above block ends here

                        //Check if any Player owns all Countries
                        for (Player l_p : d_Players){
                            if(l_p.getOwnedCountries().size() == d_map.getCountries().size()){
                                System.out.println("HURRAY!!" + l_p.getPlayerName()+" has WON the Game!");
                                d_LogEntry.setMessage("HURRAY!!" + l_p.getPlayerName()+" has WON the Game!");
                                d_LogEntry.detachObserver(d_LogWriter);
                                System.exit(0);
                            }
                        }
                        //check if any player needs to be removed as of losing all territories
                        for (Player l_p : d_Players){
                            if(l_p.getOwnedCountries().size() == 0){
                                System.out.println(l_p.getPlayerName()+" is no longer part of the game since all the territories owned are lost");
                                d_LogEntry.setMessage(l_p.getPlayerName()+" is no longer part of the game since all the territories owned are lost");
                                d_Players.remove(l_p);
                            }
                        }

                        System.out.println("All orders for all players are executed, now assigning NEW Reinforcements!");
                        System.out.println("Reinforcements assigned! Players can start with deploying Orders again!");

                        d_LogEntry.setMessage("All orders for all players are executed, now assigning NEW Reinforcements!");
                        d_LogEntry.setMessage("Reinforcements assigned! Players can start with deploying Orders again!");

                        d_GamePhase = GamePhase.TAKETURN;
                    }
                    break;

                case "showmap":
                    setPhase(new MainPlay(this));
                    d_gameStartUpPhase.showMap(d_Players, d_map);
                    break;

                default:
                    System.out.println("Entered Execute Order Phase, please type 'execute' to execute all orders");
                    break;
            }
        }

        else if (d_GamePhase == GamePhase.ENDGAME) {
            System.out.println("Stopping the game as requested");
            exit(0);
        }
        return d_GamePhase;
    }

    public void printEditmapHelpCommands() {
        System.out.println();
        System.out.println(
                "To add continents: editcontinent -add <continentName> <ControlValue>, E.g. editcontinent -add Europe 1");
        System.out.println(
                "To add country: editcountry -add <countryName> <continentName>, E.g. editcountry -add Germany Europe");
        System.out.println(
                "To add neighbor relation: editneighbor -add <countryName> <countryName>, E.g. editneighbor -add Germany Denmark");
        System.out.println("To save map file: savemap <map file name with extension>, E.g. savemap Asia.map");
        System.out.println("Waiting for next command..");
    }

    private Player fetchPlayerByName(String p_playerName) {
        for(Player l_player:d_Players) {
            if(l_player.getPlayerName().equals(p_playerName))
                return l_player;
        }
        return null;
    }
}
