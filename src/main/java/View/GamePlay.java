package View;

import Controller.GameEngine;
import Model.GamePhase;
import Model.GetCommands;

import java.io.File;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;


/**
 * GamePlay is the entry point of the program and is the view that is presented to the players
 * It calls the controller GameEngine to issue the commands passed by the player
 */
public class GamePlay {

    Scanner d_inp = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("****************************************");
        System.out.println("Welcome to the WARZONE game");
        System.out.println("****************************************");
        System.out.println();
        GamePlay l_game = new GamePlay();

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
        GamePhase l_phase = l_cmd.parseCommand(l_command);

        while (l_phase != GamePhase.ENDGAME || l_phase != GamePhase.ISSUEORDER) {
            l_command = GetCommands.validateCommand(l_cmd.getD_phase());
            l_phase = l_cmd.parseCommand(l_command);
            System.out.println("Waiting for next command...");
        }
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
        if (d_mapContainer.length()!=0) {
            System.out.println("Below are the saved game maps:");
            for (int i = 0; i < d_mapFiles.length; i++) {
                if (d_mapFiles[i].isFile())
                    System.out.println(d_mapFiles[i].getName());
            }
            System.out.println();
            System.out.println("Type 'editmap <name of map> without extension, if the map name is not part of above list, a new map will be created");
        }
        else{
            System.out.println("There are NO saved maps in the game");
            System.out.println("Type 'editmap <name of map>' without extension, to create a new map");
        }
        System.out.println();
    }
}
