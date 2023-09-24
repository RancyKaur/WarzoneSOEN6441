package View;

import Controller.GameEngine;
import Model.GamePhase;
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
        System.out.println("Type 'editmap <name of map> without extension, if the map name is not part of above list, a new map will be created");
        l_game.enterGamePhases();
    }

    /**
     * As the game moves in phases, this method is called as soon as the player enters their first command
     * This would in-turn call GameEngine controller that would execute commands
     */
    private void enterGamePhases()
    {
        GameEngine l_cmd = new GameEngine();
        String l_command = d_inp.nextLine();
        GamePhase l_phase = l_cmd.parseCommand(l_command);

        while( l_phase!=GamePhase.ENDGAME || l_phase!=GamePhase.ISSUEORDER)
        {
            l_command = d_inp.nextLine();
            l_phase = l_cmd.parseCommand(l_command);
            System.out.println("Waiting for next command...");
        }
    }

    private void displaySavedMaps()
    {

    }
}
