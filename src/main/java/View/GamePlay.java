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

    /**
     * Ensures string matches the defined criteria of being a Numeric for ID.
     *
     * @param args input string array
     */
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
        takeOrders(l_cmd);
    }

    /**
     * Purpose of this method is to check if there are SAVED maps in the system
     * If there are saved maps, those would be displayed and player can decide to use saved map to play or create new one
     * If there are none, then user would be prompted to create new map
     */
    private void displaySavedMaps() {
    File mapContainer = new File("src/main/resources/maps/");
    File[] mapFiles = mapContainer.listFiles();

    System.out.println();

    if (mapFiles.length != 0) {
        System.out.println("Below are the saved game maps:");
        for (File mapFile : mapFiles) {
            if (mapFile.isFile()) {
                System.out.println(mapFile.getName());
            }
        }
        System.out.println();
        System.out.println("Type 'editmap <name of map> with extension. If the map name is not in the list above, a new map will be created.");
    } else {
        System.out.println("There are NO saved maps in the game.");
        System.out.println("Type 'editmap <name of map>' with extension to create a new map.");
    }

    System.out.println();
}

    /**
     * This method assigns armies to countries randomly based on the number of countries owned by each player
     *
     * @param gameEngine GameEngine Reference
     */
    public void assignEachPlayerReinforcements(GameEngine gameEngine) {
        for (Player player : gameEngine.d_Players) {
            ReinforcePlayers.assignReinforcementArmies(player);
        }
    }


    /**
     * Ensures string matches the defined criteria of being a Numeric for ID.
     *
     * @param gameEngine GameEngine
     */
    public void takeOrders(GameEngine gameEngine) {
        int numberOfPlayers = gameEngine.d_Players.size();
    
        while (true) {
            for (int traversalCounter = 0; traversalCounter < numberOfPlayers; traversalCounter++) {
                Player currentPlayer = gameEngine.d_Players.get(traversalCounter);
                System.out.println("It's " + currentPlayer.getPlayerName() + "'s turn");
    
                processPlayerOrders(gameEngine, currentPlayer);
            }
    
            resetPhaseAndCounter(gameEngine);
        }
    }
    
    private void processPlayerOrders(GameEngine gameEngine, Player player) {
        GamePhase phase = GamePhase.ISSUEORDER;
        gameEngine.setD_phase(phase);
    
        while (phase != GamePhase.TAKETURN) {
            String command = d_inp.nextLine();
            phase = gameEngine.parseCommand(player, command);
        }
    }
    
    private void resetPhaseAndCounter(GameEngine gameEngine) {
        GamePhase phase = GamePhase.ISSUEORDER;
        gameEngine.setD_phase(phase);
    }

    /**
     * Ensures string matches the defined criteria of being a Numeric for ID.
     *
     * @param p_phase GamePhase
     * @param p_cmd GameEnginer
     * @param p_command string
     * @return true if valid match, else false
     */
    public GamePhase handleStartPhase(GamePhase p_phase, GameEngine p_cmd, String p_command) {
        while (p_phase != GamePhase.ENDGAME && p_phase != GamePhase.ISSUEORDER) {
            p_command = GetCommands.validateCommand(p_cmd.getD_phase());
            p_phase = p_cmd.parseCommand(null, p_command);
            System.out.println("Waiting for next command...");
        }
        return p_phase;
    }

}
