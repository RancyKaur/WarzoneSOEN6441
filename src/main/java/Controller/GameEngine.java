package Controller;

import Model.EngineCommand;
import Model.GamePhase;
import Model.WargameMap;

import static java.lang.System.exit;

public class GameEngine {

    private GamePhase d_phase;
    private EngineCommand d_RunCommand;
    private WargameMap d_map;
    public GameEngine()
    {
        d_phase = GamePhase.BEGINGAME;
        d_RunCommand = new EngineCommand();
        d_map = new WargameMap();
    }

    /**
     * Checks if map's name is valid
     * The validity checks if name contains alphanumeric characters, no special character is allowed
     */
    private boolean isValidMapName(String p_mapName)
    {
        return p_mapName != null && p_mapName.matches("^[a-zA-Z0-9]*$") ;
    }
    /**
     * This method parses the command input by the player and then executes methods related to the commands entered
     * @param p_givenCommand - Command entered by the player
     * @return - the next phase of the current game
     */
    public GamePhase parseCommand(String p_givenCommand)
    {
        String[] l_param = p_givenCommand.split("\\s+");
        String l_commandName = l_param[0];
        String l_mapName = null;
        int l_continentID;

        /* conditional execution of phases, games starts with Startgame phase on command editmap or loadmap
           Depending on player's selection it moves to editmap phase or loadmap phase
        */
        if(d_phase==GamePhase.BEGINGAME)
        {
            switch (l_commandName)
            {
                /**/
                case "editmap":
                {
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
                        System.out.println("Invalid command - try command -> editmap sample.map");
                    }
                    break;
                }
                case "stopgame":
                {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }
            }
        }
        else if(d_phase==GamePhase.EDITMAP)
        {
            switch (l_commandName) {
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
                                    System.out.println("Error in saving - invalid map");
                            } else
                                System.out.println("Map name not valid!");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
                    }
                    break;
                }
                //parsing this type of command here
                // editcontinent -add continentID continentValue OR
                // editcontinent -remove continentID
                // Here countryID and continentID both will be names strings not the numbers

                case "editcontinent":
                {
                    String l_typeOfEditcontinent = l_param[1].toLowerCase(); // -add or -remove

                    try
                    {
                        String l_continentName = null;

                        if(l_typeOfEditcontinent.equals("-add"))
                        {
                            l_continentName = l_param[3].toLowerCase();
                            //Checking for the country name and I given by the user should be string without any special characters
                            if(this.isValidMapName(l_continentName))
                            {
                                //2nd Index Contains Continent ID like 1 2 3 int number
                                l_continentID = Integer.parseInt(l_param[2]); //continentID param

                                boolean l_status = d_RunCommand.addContinentToMap(d_map,l_continentID,l_continentName);
                                if(l_status)
                                {
                                    System.out.println("Continent added");
                                    //d_phase = GamePhase.EDITMAP;
                                }
                                else
                                {
                                    System.out.println("Given Continent Already exists");
                                }
                            }
                            else
                            {
                                System.out.println("Given name(s) not valid!");
                            }
                        } else if (l_typeOfEditcontinent.equals("-remove")) {
                            String l_continentNameToBeRemoved = l_param[2].toLowerCase();
                            if(this.isValidMapName(l_continentNameToBeRemoved)){

                                boolean l_check = d_RunCommand.removeContinentFromMap(d_map, l_continentNameToBeRemoved);
                                if (l_check) {
                                    System.out.println(l_continentNameToBeRemoved+" continent is removed from the Map");
                                    //d_phase = GamePhase.EDITMAP;
                                }
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: The continentID is not a valid integer.");
                    }

                    break;
                }
                //parsing this type of command here
                    // editcountry -add countryID continentID OR
                    // editcountry -remove countryID
                    // Here countryID and continentID both will be names strings not the numbers
                case "editcountry":
                {
                    try
                    {
                        String l_typeOfEditcountry = l_param[1].toLowerCase(); // -add or -remove
                        String l_userGivenCountryName = l_param[2].toLowerCase(); //countryID param

                        if(l_typeOfEditcountry.equals("-add"))
                        {
                            String l_userGivenContinentName = l_param[3].toLowerCase(); //continentID param
                            //Checking for the country name and I given by the user should be string without any special characters
                            if(this.isValidMapName(l_userGivenCountryName.toLowerCase()) && this.isValidMapName(l_userGivenContinentName.toLowerCase()))
                            {

                                boolean l_status = d_RunCommand.addCountryToContinent(d_map,l_userGivenCountryName,l_userGivenContinentName);
                                if(l_status)
                                {
                                    System.out.println(l_userGivenCountryName+" is successfully added to "+l_userGivenContinentName);
                                    //d_phase = GamePhase.EDITMAP;
                                }

                            }
                            else
                            {
                                System.out.println("Given name(s) not valid!");
                            }
                        } else if (l_typeOfEditcountry.equals("-remove")) {
                            String l_countryNameToBeRemoved = l_param[2].toLowerCase();

                            if(this.isValidMapName(l_countryNameToBeRemoved)){

                                boolean l_check = d_RunCommand.removeCountry(d_map, l_countryNameToBeRemoved);
                                if (l_check) {
                                    System.out.println(l_countryNameToBeRemoved+" country is removed from the Map");
                                    //d_phase = GamePhase.EDITMAP;
                                }
                            }else {
                                System.out.println("Invalid country name");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: The continentID is not a valid integer.");
                    }

                    break;
                }


                case "stopgame":
                {
                    this.d_phase = GamePhase.ENDGAME;
                    System.out.println("Stopping the game as requested");
                    exit(0);
                }
            }
        }
        else if (d_phase==GamePhase.ENDGAME)
        {
            System.out.println("Stopping the game as requested");
            exit(0);
        }
        return d_phase;
    }

    private void printEditmapHelpCommands()
    {
        System.out.println();
        System.out.println("To add continents: editcontinent -add <continentName> <continentID>, E.g. editcontinent -add Europe 11");
        System.out.println("To add country: editcountry -add <countryName> <continentName>, E.g. editcountry -add Germany Europe");
        System.out.println("To add neighbor relation: editneighbor -add <countryName> <countryName>, E.g. editneighbor -add Germany Denmark");
        System.out.println("To save map file: savemap <map file name without extension>, E.g. savemap AsiaMap");
        System.out.println("Waiting for next command..");
    }
}
