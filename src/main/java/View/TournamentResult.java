package View;

import Model.WargameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    /**
     * This method displays results as specified in the build requirements
     * @param p_winner
     * @param p_noOfGames
     */
    public void show_TournamentResult(HashMap<String,HashMap<Integer, String>> p_winner, int p_noOfGames) {

        StringBuilder headerBuilder = new StringBuilder();
        ArrayList<String> printList = new ArrayList<String>() ;
        headerBuilder.append("%-15s");
        printList.add("Map Name");
        for(int i=0;i<p_noOfGames;i++) {
            headerBuilder.append("%-15s");
            printList.add("Game " + (i+1));
        }

        System.out.format(headerBuilder.toString(),printList.toArray());
        System.out.format("\n");
        System.out.format("%15s", "-----------------------------------------------------------------------------");

        for (Map.Entry<String,HashMap<Integer, String>> entry : p_winner.entrySet()) {
            headerBuilder.delete(0, headerBuilder.length());
            printList.clear();

            HashMap<Integer, String> innerMap = entry.getValue();

            // Access the String (value)
            printList.add(entry.getKey());
            headerBuilder.append("%-15s");
            for (String value : innerMap.values())
            {
                headerBuilder.append("%-15s");
                printList.add(value);
            }
            System.out.format("\n");
            System.out.format(headerBuilder.toString(),printList.toArray());
        }

    }


}

