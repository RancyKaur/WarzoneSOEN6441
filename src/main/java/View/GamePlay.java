package View;

import Controller.GameEngine;
import Controller.TournamentEngine;
import Model.GamePhase;
import Model.GetCommands;
import Model.Player;
import Model.ReinforcePlayers;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;


/**
 * GamePlay is the entry point of the program and is the view that is presented to the players
 * It calls the controller GameEngine to issue the commands passed by the player
 */
public class GamePlay {
    Scanner d_inp = new Scanner(System.in);
    private static GamePlay l_game;

    static {
        l_game = new GamePlay();
    }

    public static void main(String[] args) {

        System.out.println("****************************************");
        System.out.println("Welcome to the WARZONE game");
        System.out.println("****************************************");
        System.out.println();

        l_game.playGame();
    }

    private void playGame() {
        GameEngine l_cmd = new GameEngine();
        TournamentEngine tEngine;
        boolean valid = true;
        boolean loadGame = false;
        String userCmd, message = null;
        int traversalCounter=0;
        System.out.println("Enter 1 to play single-game mode OR 2 to play tournament mode.");

        while (valid) {
            userCmd = d_inp.nextLine();

            if (userCmd.equals("1")) {

                while (valid)
                {
                    System.out.println("Enter 1 to load a saved game OR 2 to edit/load map for new game.");
                    userCmd = d_inp.nextLine();

                    if (userCmd.equals("1")) {
                        // this block is when user wants to load a saved Game
                        System.out.println("To continue, select a game to load from the existing save games.");
                        boolean returnGames = showSavedGames();
                        if(returnGames)
                        {
                            GamePhase l_gamePhase;
                            valid = false;
                            do{
                                userCmd = d_inp.nextLine();
                                l_gamePhase = l_cmd.parseCommand(null, userCmd);
                            } while (l_gamePhase!=GamePhase.BEGINGAME);

                            //set traversal counter by finding appropriate player
                            traversalCounter = -1;
                            System.out.println(l_cmd.getGame().getPlayers());
                            for (Player player1 : l_cmd.getGame().getPlayers()) {

                                traversalCounter++;
                                if (player1 == l_cmd.getGame().getActivePlayer()) {
                                    break;
                                }
                            }

                            //set controller to turn controller to continue playing the loaded game

                            l_cmd = new GameEngine(l_cmd.getGame());
                            if(l_cmd.getGame().getGamePhase()!=GamePhase.ISSUEORDER )
                            {
                                enterGamePhases(l_cmd);
                            }
                            else {
                                takeOrders(l_cmd, l_gamePhase, null);
                            }
                        }
                        else
                        {
                            System.out.println("There are no saved games, please start a new game  by entering 2");
                            break;
                        }
                    }else if (userCmd.equals("2")) {;
                        // this block is when user wants to start a new game

                        System.out.println("You can start the game by EITHER: Selecting an existing map as given below or Creating new map");
                        System.out.println("At any point if you type 'stopgame' the game would be stopped");
                        System.out.println();
                        displaySavedMaps();
                        enterGamePhases(l_cmd);

                    }else {
                        System.out.println("Invalid command, Enter 1 to load Saved game and enter 2 for working with new maps.");
                        continue;
                    }
                }

            } else if (userCmd.equals("2")) {
                //tournament mode
                tEngine = new TournamentEngine(l_cmd);
                do {
                    System.out.println("Command should be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}");
                    userCmd = d_inp.nextLine();
                    message = tEngine.parse(null, userCmd);
                } while (!message.equals("success"));
                valid = false;
            } else {
                System.out.println("Invalid Command, Enter 1 to play single-game mode or 2 to play tournament mode.");
            }

        }
    }


    /**
     * As the game moves in phases, this method is called as soon as the player enters their first command
     * This would in-turn call GameEngine controller that would execute commands
     */
    private void enterGamePhases(GameEngine p_cmd) {
        String l_command = GetCommands.validateCommand(p_cmd.d_GamePhase);

        GamePhase l_phase = p_cmd.parseCommand(null, l_command);
        l_phase = handleStartPhase(l_phase, p_cmd, l_command);
        l_game.assignEachPlayerReinforcements(p_cmd);

        takeOrders(p_cmd, l_phase, l_command);
    }

    /**
     * Purpose of this method is to check if there are SAVED maps in the system
     * If there are saved maps,
     * those would be displayed and player can decide to use saved map to play or create a new one
     * If there are none, then user would be prompted to create a new map
     */
    public void displaySavedMaps() {
        File d_mapContainer = new File("src/main/resources/maps/");
        File[] d_mapFiles = d_mapContainer.listFiles();
        System.out.println();
        if (d_mapFiles.length != 0) {
            System.out.println("Below are the saved game maps:");
            for (int i = 0; i < d_mapFiles.length; i++) {
                if (d_mapFiles[i].isFile())
                    System.out.println(d_mapFiles[i].getName());
            }
            System.out.println();
            //System.out.println("To see an existing map, type 'showmap <name of map>' with extension");
            System.out.println("To edit or create a new map, type 'editmap <name of map>' with extension, if the map name is not part of above list, a new map will be created");
            System.out.println("To start playing on existing map, type 'loadmap <name of map>' with extension");

        } else {
            System.out.println("There are NO saved maps in the game");
            System.out.println("Type 'editmap <name of map>' with extension, to create a new map");
        }
        System.out.println();
    }

    /**
     * This method assigns armies to countries randomly based on the number of countries owned by each player
     *
     * @param p_gameEngine GameEngine Reference
     */
    public void assignEachPlayerReinforcements(GameEngine p_gameEngine) {
        Iterator<Player> itr = p_gameEngine.d_Players.listIterator();
        if (itr.hasNext()) {
            do {
                Player p = itr.next();
                ReinforcePlayers.assignReinforcementArmies(p);
            } while (itr.hasNext());
        }
        System.out.println("Enter 'showmap' to see country assignments given to each player");
    }

    /**
     * This method is used when multiple players play to take turn
     *
     * @param l_cmd
     * @param l_phase
     * @param l_command
     */
    public void takeOrders(GameEngine l_cmd, GamePhase l_phase, String l_command) {
        int l_numberOfPlayers = l_cmd.d_Players.size();
        int l_traversalCounter = 0;
        while (true) {
            while (l_traversalCounter < l_numberOfPlayers) {
                Player l_p = l_cmd.d_Players.get(l_traversalCounter);
                Iterator<Player> l_itr = l_cmd.d_Players.listIterator();
                while (l_itr.hasNext()) {
                    Player l_sp = l_itr.next();
                    System.out.println("Player " + l_sp.getPlayerName() + " has " + l_sp.getOwnedArmies() + " Armies currently left to be deployed!");
                }
                System.out.println();
                System.out.println("It's " + l_p.getPlayerName() + "'s turn");
                l_cmd.setD_ActivePlayer(l_p);
                //show the list of cards that play holds currently
                System.out.println();
                //listen to orders from players - deploy | pass
                l_phase = GamePhase.ISSUEORDER;
                l_cmd.setD_GamePhase(l_phase);
                System.out.println("Player " + l_p.getPlayerName() + " can provide next game command");
                while (l_phase != GamePhase.TAKETURN) {
                    if (l_p.get_isHuman()) {
                        if (!l_p.getCardDeck().isEmpty()) {
                            l_p.showCards();
                        }
                        l_command = d_inp.nextLine();
                        l_phase = l_cmd.parseCommand(l_p, l_command);
                    } else {
                        l_phase = l_cmd.parseCommand(l_p, l_command);
                    }
                }
                //gets to next Player
                l_traversalCounter++;
            }
            l_phase = GamePhase.ISSUEORDER;
            l_cmd.setD_GamePhase(l_phase);
            l_traversalCounter = 0;
        }
    }

    public GamePhase handleStartPhase(GamePhase p_phase, GameEngine p_cmd, String p_command) {
        while (p_phase != GamePhase.ENDGAME && p_phase != GamePhase.ISSUEORDER) {
            p_command = GetCommands.validateCommand(p_cmd.d_GamePhase);
            p_phase = p_cmd.parseCommand(null, p_command);
            System.out.println("Waiting for next command...");
        }
        return p_phase;
    }

    /**
     * Shows names of existing game files under Resources.
     */
    private boolean showSavedGames() {
        System.out.println("Below are the list of saved games: ");
        File d_Folder = new File("src/main/resources/SavedGames/");
        File[] d_Files = d_Folder.listFiles();

        if(d_Files.length>0) {
            for (int i = 0; i < d_Files.length; i++) {
                if (d_Files[i].isFile())
                    System.out.print(d_Files[i].getName() + " | ");
            }
            System.out.println();
            return true;
        }
        else{
            return false;
        }
    }
}
