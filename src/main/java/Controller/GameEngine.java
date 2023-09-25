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
        String l_continentName=null;
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

                case "editcontinent":
                {
                    try
                    {
                        if(l_param[1].equals("-add"))
                        {
                            //Checking for the country name and I given by the user should be string without any special characters
                            if(this.isValidMapName(l_param[3]))
                            {
                                l_continentName=l_param[3];
                                l_continentID = Integer.parseInt(l_param[2]);

                                boolean l_status = d_RunCommand.addContinentToMap(d_map,l_continentID,l_continentName);
                                if(l_status)
                                {
                                    System.out.println("Continent added");
                                    d_phase = GamePhase.EDITMAP;
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
