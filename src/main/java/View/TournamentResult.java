package View;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class is responsible for showing result of the tournament.
 */

public class TournamentResult {

    /**
     * Displays the result of the tournament
     * @param p_winner HashMap representing the winner of the game indexed based on the number of the game.
     * @param p_maps List of maps used to play the tournament.
     */
    public void showTournamentResult(HashMap<Integer, String> p_winner, ArrayList<String> p_maps){
        int l_mapChangeAfter = p_winner.size()/p_maps.size();
        int l_index = 0;
        int l_changeIndex = 1;
        System.out.format("%25s%25s%35s\n", "Game number", "Game map", "Winner");
        System.out.format("%85s\n", "-------------------------------------------------------------------------------------------");
        String l_mapName = p_maps.get(l_index);
        for(int i = 1; i<=p_winner.size(); i++){
            if(l_changeIndex>l_mapChangeAfter){
                l_index++;
                l_mapName = p_maps.get(l_index);
                l_changeIndex = 0;
            }
            l_changeIndex++;
            System.out.format("\n%25s%25s%25s\n", i, l_mapName, p_winner.get(i));
        }
    }
}

