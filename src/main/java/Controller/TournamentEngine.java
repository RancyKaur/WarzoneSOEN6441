package Controller;

import Model.*;
import View.TournamentResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

/**
 * Manages Tournament
 */
public class TournamentEngine extends GameEngine{
    StartUp d_StartUp;
    private String commandErrorMessage = "Incorrect command format, the correct format is: 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}";
    /**
     * Creates a tournament engine object.
     * @param p_Ge is object of gameEngine
     */
    public TournamentEngine(GameEngine p_Ge){
        super();
        d_StartUp = new StartUp(p_Ge);
    }

    /**
     * Function responsible for handling command for tournament
     * @param p_player Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return success if command is valid else appropriate message which indicates reason of failure
     */
    public String parse(Player p_player, String p_newCommand){
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];
        int l_noOfMaps;
        int l_noOfGames;
        int l_noOfTurns;
        ArrayList<String> l_maps = new ArrayList<String>();
        ArrayList<String> l_strategies = new ArrayList<String>();

        if (l_commandName.equals("tournament")) {
            try {
                if (l_data[1].equals("-M")) {
                    int i = 2;
                    while (!l_data[i].equals("-P")) {
                        if (i < l_data.length) {
                            if (isMapNameValid(l_data[i])) {
                                l_maps.add(l_data[i]);
                            } else {
                                printFailureMessage();
                                return commandErrorMessage;
                            }
                        } else {
                            printFailureMessage();
                            return commandErrorMessage;
                        }
                        i++;
                    }

                    if(l_maps.size()>=1 && l_maps.size()<=5 && allMapExists(l_maps)) {

                        if (l_data[i].equals("-P")) {

                            int l_indexNew = i + 1;

                            while (!l_data[l_indexNew].equals("-G")) {
                                if (l_indexNew < l_data.length) {
                                    if (isPlayerStrategyValid(l_data[l_indexNew])) {
                                        l_strategies.add(l_data[l_indexNew]);
                                    } else {
                                        printFailureMessage();
                                        return "";
                                    }

                                } else {
                                    printFailureMessage();
                                    return commandErrorMessage;
                                }
                                l_indexNew++;
                            }

                            if(l_strategies.size()>=2 && l_strategies.size()<=4 && isPlayerStrategyDistinct(l_strategies)) {
                                //System.out.println(strategies);

                                if (l_data[l_indexNew].equals("-G")) {
                                    if (l_indexNew + 1 < l_data.length) {
                                        if (l_data[l_indexNew + 1].matches("[1-5]")) {
                                            l_noOfGames = Integer.parseInt(l_data[l_indexNew + 1]);
                                            //System.out.println("Number of Games:" + noOfGames);
                                        } else {
                                            printFailureMessage();
                                            return commandErrorMessage;
                                        }
                                    } else {
                                        printFailureMessage();
                                        return commandErrorMessage;
                                    }

                                    int l_newIndex = l_indexNew + 2;

                                    if (l_data[l_newIndex].equals("-D")) {

                                        if (l_newIndex + 1 < l_data.length) {

                                            int l_n = Integer.parseInt(l_data[l_newIndex + 1]);

                                            if (l_n >= 10 && l_n <= 50) {
                                                l_noOfTurns = Integer.parseInt(l_data[l_newIndex + 1]);
                                                playTournament(l_maps, l_strategies, l_noOfGames, l_noOfTurns);
                                                return "success";
                                            } else {
                                                printFailureMessage();
                                                return commandErrorMessage;
                                            }
                                        } else {
                                            printFailureMessage();
                                            return commandErrorMessage;
                                        }
                                    } else {
                                        printFailureMessage();
                                        return commandErrorMessage;
                                    }
                                } else {
                                    printFailureMessage();
                                    return commandErrorMessage;
                                }
                            }else{
                                printFailureMessage();
                                return commandErrorMessage;
                            }
                        } else {
                            printFailureMessage();
                            return commandErrorMessage;
                        }
                    }else {
                        printFailureMessage();
                        return commandErrorMessage;
                    }
                } else {
                    printFailureMessage();
                    return commandErrorMessage;
                }
            }catch (ArrayIndexOutOfBoundsException e){
                String message = commandErrorMessage;
                return message;
            }
        } else {
            printFailureMessage();
            return commandErrorMessage;
        }

    }

    /**
     * Conducts the tournament as per the mentioned arguments.
     * @param p_mapFiles  List of map files to play on
     * @param p_strategies    List of player strategies
     * @param p_numberOfGames Number of games to play
     * @param p_numberOfTurns Number of turns to play
     */
    public void playTournament(ArrayList<String> p_mapFiles, ArrayList<String> p_strategies, int p_numberOfGames, int p_numberOfTurns){
        int l_numberOfPlayers = p_strategies.size();
        int l_traversalCounter = 0;
        int l_gameNumber = 0;
        HashMap<Integer, String> l_winner = new HashMap<Integer, String>();
        int Ps = d_Players.size();
        while(Ps != 0 ){
            d_Players.remove(0);
            Ps -= 1;
        }

        //Start playing on each map
        for(String l_mapName : p_mapFiles) {
            System.out.println("Playing on :"+l_mapName);
            //Start playing each game
            int P = d_Players.size();
            while(P != 0 ){
                d_Players.remove(0);
                P -= 1;
            }
            for (int i = 1; i <= p_numberOfGames; i++) {
                l_gameNumber++;
                System.out.println("Playing Game :"+l_gameNumber);
                d_GameEngine = new GameEngine();
                d_GameState = new GameState();
                d_RunCommand = new EngineCommand();
                d_StartUp = new StartUp(d_GameEngine);
                //load the map
                d_map = d_RunCommand.loadMap(l_mapName);

                //Flushing Players
                //Flushing Objects which are getting reused
                int Psiz = d_Players.size();
                while(Psiz != 0 ){
                    d_Players.remove(0);
                    Psiz -= 1;
                }

                //Create player objects
                for (String l_strategy : p_strategies) {
                    d_StartUp.addPlayer(d_Players, l_strategy);
                }
                //Setting strategies as same as Player Names
                for (Player l_p : d_Players) {
                    switch (l_p.getPlayerName().toLowerCase()) {
                        case "aggressive":
                            l_p.setStrategy(new AggressiveStrategy(l_p, d_map));
                            l_p.set_isHuman(false);
                            break;
                        case "benevolent":
                            l_p.setStrategy(new BenevolentStrategy(l_p, d_map));
                            l_p.set_isHuman(false);
                            break;
                        case "random":
                            l_p.setStrategy(new RandomStrategy(l_p, d_map));
                            l_p.set_isHuman(false);
                            break;
                        case "cheater":
                            l_p.setStrategy(new CheaterStrategy(l_p, d_map));
                            l_p.set_isHuman(false);
                            break;
                        default:
                            System.out.println("Invalid Player Strategy");
                            break;
                    }
                }
                //System.out.println(d_Players.size());
                d_StartUp.assignCountries(d_map, d_Players);
//                for (Player p : d_Players) {
//                    System.out.println(p.getOwnedCountries());
//                }
                //AssignCountries and Reinforcements
                assignEachPlayerReinforcements(d_Players);
//                for (Player p : d_Players) {
//                    System.out.println(p.getOwnedArmies());
//                }
                //tournament -M demo.map -P cheater random -G 1 -D 10
                //tournament -M dummy.map ameroki.map -P cheater aggressive -G 4 -D 30

                boolean anyOneWon = false;
                for (int j = 1; j <= p_numberOfTurns; j++) {
                    int l_counter = 0;
                    System.out.println("It's " + j + "'th Turn");

                    //traverses through all players to check if armies left in pool
                    Iterator<Player> l_itr = d_Players.listIterator();
                    while (l_itr.hasNext()) {
                        Player l_p = l_itr.next();
                        if (l_p.getOwnedArmies() > 0) {
                            l_counter = l_counter + l_p.getOwnedArmies();
                        }
                    }

                    System.out.println("Total Armies left with all Players in Pool: " + l_counter);
                    //Case when pool has at least 1 army left

                    //Issued Orders
                    //Gets Orders until all pool is consumed for turn
                    while (l_counter > 0) {
                        //get Orders
                        for (Player p : d_Players) {
                            if (p.getOwnedCountries().size() != 0) {
                                p.issue_order();
                            } else {
                                p.setOwnedArmies(0);
                            }

                        }
                        l_counter = 0;
                        for (Player p : d_Players) {
                            if (p.getOwnedArmies() > 0) {
                                l_counter = l_counter + p.getOwnedArmies();
                            }
                        }
                    }

                    System.out.println("Total Armies left with all Players in Pool: " + l_counter);

                    //Execute current Pool of Orders
                    int l_count = 0;
                    for (Player l_p : d_Players) {
                        //Get Valid Orders Queue
                        Queue<Order> l_temp = l_p.getD_orderList();
                        l_count = l_count + l_temp.size();
                    }

                    if (l_count == 0) {
                        //Case when no Order
                        System.out.println("Orders already executed!");
                    } else {
                        //Execute Orders and check if Player won
                        System.out.println("Total Orders  :" + l_count);
                        //Execute Current Orders
                        boolean win = true;
                        while (l_count != 0 && win ) {
                            if (d_Players.size() == 1){
                                Order l_toRemove = d_Players.get(0).next_order();
                                System.out.println("Order  :"+l_toRemove+" : for "+d_Players.get(0) );
                                l_winner.put(l_gameNumber, d_Players.get(0).getPlayerName());
                                Queue<Order> l_temp = d_Players.get(0).getD_orderList();
                                if (l_temp.size() > 0) {
                                    Order tempO = d_Players.get(0).next_order();
                                    if(tempO != null){
                                        System.out.println("Executing "+l_toRemove);
                                        l_toRemove.execute();
                                        l_count--;
                                    }
                                }
                                anyOneWon = true;
                                l_count = 0;
                                win = false;
                                break;
                            }else{
                                System.out.println("When More Players");
                                for (Player l_p : d_Players) {
                                    Queue<Order> l_temp = l_p.getD_orderList();
                                    System.out.println("Got Order :"+l_temp+" from "+l_p.getPlayerName());
                                    if (l_temp.size() > 0) {
                                        Order l_toRemove = l_p.next_order();
                                        if(l_toRemove != null){
                                            System.out.println("Executing "+l_toRemove);
                                            l_toRemove.execute();
                                            l_count--;
                                        }
                                    }
                                }

                            }
                        }
                        System.out.println("Total Armies left with all Players in Pool: " + l_counter);
                        //Flush Owned Cards
                        for (Player l_p : d_Players) {
                            System.out.println("Flushing Cards of "+l_p.getPlayerName());
                            l_p.removeAllNegotiators();
                        }
                    }
                    assignEachPlayerReinforcements(d_Players);
                }
                //Check if any Player Won
                for (Player l_p : d_Players) {
                    if (l_p.getOwnedCountries().size() == d_map.getCountries().size()) {
                        System.out.println(l_p.getPlayerName() + " has Won the Game!");
                        d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                        l_winner.put(l_gameNumber, l_p.getPlayerName());
                        break;
                    }else{
                        System.out.println("Current Game resulted in a Draw");
                        l_winner.put(l_gameNumber, "Draw");
                        break;
                    }
                }

                //Flushing Objects which are getting reused
                int Psize = d_Players.size();
                while(Psize != 0 ){
                    d_Players.remove(0);
                    Psize -= 1;
                }
                System.out.println("Players left after game "+d_Players.size());
            }
        }
        //return winner;
        TournamentResult tournamentResultView = new TournamentResult();
        tournamentResultView.showTournamentResult(l_winner, p_mapFiles);
    }

    /**
     * Ensures that all maps exist
     * @param p_list list of name of maps
     * @return true if valid else false
     */
    public boolean allMapExists(ArrayList<String> p_list){
        int l_counter=0;
        for(String s:p_list){
            if(isMapExists(s)){
                l_counter++;
            }
        }
        if(l_counter == p_list.size()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if the map with argument name exists or not.
     * If it exists, it also checks if its valid or not.
     * @param p_mapName Name of the map file to be checked.
     * @return true if file exists and is a valid file, otherwise false.
     */
    public boolean isMapExists(String p_mapName) {
        String l_filePath = "src/main/resources/maps/" + p_mapName;
        File f = new File(l_filePath);
        if (f.exists()) {
            EngineCommand rGE = new EngineCommand();
            d_map = rGE.loadMap(p_mapName);
            return true;
        } else {
            return false;
        }
    }

    /**
     *Method prints failure message for tournament command
     */
    public void printFailureMessage(){
        String message = "Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}";
        System.out.println(message);
        d_LogEntry.setMessage("Command has to be in form of 'tournament -M listofmapfiles{1-5} -P listofplayerstrategies{2-4} -G numberofgames{1-5} -D maxnumberofturns{10-50}");
    }

    /**
     * Ensures map name is valid.
     *
     * @param p_s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String p_s) {
        return p_s != null && p_s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures player strategy is valid
     * @param p_s strategy of the player
     * @return true if valid else false
     */
    public boolean isPlayerStrategyValid(String p_s){

        String[] l_array = new String[]{"aggressive", "benevolent", "random", "cheater"};
        for(int i=0; i<4; i++){
            if(p_s.equals(l_array[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure that all strategies of player are distinct
     * @param p_list list of player's strategy
     * @return true if valid else false
     */
    public boolean isPlayerStrategyDistinct(ArrayList<String> p_list){

        for(int i=0;i<p_list.size();i++){
            for(int j = i+1; j<p_list.size(); j++){
                if(p_list.get(i).equals(p_list.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * assigns default reinforcements to each player based on Countries owned.
     * @param p_Players  GameEngine ref from main to get track of players
     */
    public void assignEachPlayerReinforcements(ArrayList<Player> p_Players){
        for(Player l_p: p_Players) {
            ReinforcePlayers.assignReinforcementArmies(l_p);
        }
    }
}
