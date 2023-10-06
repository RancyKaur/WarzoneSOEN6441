package View;

import Controller.GameEngine;
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

        System.out.println("You can start the game by EITHER: Selecting an existing map as given below or Creating new map");
        System.out.println("At any point if you type 'stopgame' the game would be stopped");
        l_game.displaySavedMaps();
        l_game.enterGamePhases();
    }

    /**
     * As the game moves in phases, this method is called as soon as the player enters their first command
     * This would in-turn call GameEngine controller that would execute commands
     */
    private void enterGamePhases() {
        GameEngine l_cmd = new GameEngine();
        String l_command = GetCommands.validateCommand(l_cmd.getD_phase());

        GamePhase l_phase = l_cmd.parseCommand(null, l_command);
        l_phase = handleStartPhase(l_phase, l_cmd, l_command);
        l_game.assignEachPlayerReinforcements(l_cmd);

//        Iterator<Player> l_itr = l_cmd.d_Players.listIterator();
//        while (l_itr.hasNext()) {
//            Player l_p = l_itr.next();
//            System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmies() + " Armies currently left to be deployed!");
//        }
        takeOrders(l_cmd, l_phase, l_command);
    }

    /**
     * Purpose of this method is to check if there are SAVED maps in the system
     * If there are saved maps, those would be displayed and player can decide to use saved map to play or create new one
     * If there are none, then user would be prompted to create new map
     */
    private void displaySavedMaps() {
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
                //listen orders from players - deploy | pass
                l_phase = GamePhase.ISSUEORDER;
                l_cmd.setD_phase(l_phase);
                System.out.println("Player " + l_p.getPlayerName() + " Can provide deploy order or pass order");
                while (l_phase != GamePhase.TAKETURN) {
                    l_command = d_inp.nextLine();
                    l_phase = l_cmd.parseCommand(l_p, l_command);
                }
                //gets to next Player
                l_traversalCounter++;
            }
            l_phase = GamePhase.ISSUEORDER;
            l_cmd.setD_phase(l_phase);
            l_traversalCounter = 0;
        }
    }

    public GamePhase handleStartPhase(GamePhase p_phase, GameEngine p_cmd, String p_command) {
        while (p_phase != GamePhase.ENDGAME && p_phase != GamePhase.ISSUEORDER) {
            p_command = GetCommands.validateCommand(p_cmd.getD_phase());
            p_phase = p_cmd.parseCommand(null, p_command);
            System.out.println("Waiting for next command...");
        }
        return p_phase;
    }

}
