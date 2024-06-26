package Model;

import Controller.GameEngine;
import View.GameMapView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class containing logic for implementation of StartUp that is implementation of State Pattern
 */
public class StartUp extends Play {

    /**
     * Reference to GameMapView for map printing
     */
    GameMapView d_mapView;
    String playerStrategy;
    /**
     * it is constructor to initialize values
     * @param p_gameEngineObj is the reference of gameEngine class
     */
    public StartUp(GameEngine p_gameEngineObj) {
        d_Ge = p_gameEngineObj;
        d_PhaseName = "StartUp";
        d_mapView = new GameMapView();
        playerStrategy = null;
    }

    /**
     * It will remove the players from the game.
     *
     * @param p_players players list in game
     * @param p_playerName Names of players in the game
     * @return true if the players are removed successfully, else false
     */
    public boolean removePlayer(ArrayList<Player> p_players, String p_playerName){
        Iterator<Player> itr = p_players.listIterator();
        while(itr.hasNext()) {
            Player l_p = itr.next();
            if(l_p.getPlayerName().equals(p_playerName)) {
                d_Ge.d_LogEntry.setMessage("Removed Player "+l_p.getPlayerName());
                p_players.remove(l_p);
                return true;
            }
        }
        return false;
    }

    /**
     * Adding the players to the game
     * 6 is the maximum players you can add.
     *
     * @param p_players players list
     * @param p_playerName Names of players in the game
     * @return true if the players are added successfully, else false
     */
    public boolean addPlayer(ArrayList<Player> p_players, String p_playerName){
        if(p_players.size()>6) {
            System.out.println("You can only add players upto 6 not more than that");
            d_Ge.d_LogEntry.setMessage("Can not add any more. Maximum players allowed are 6.");
            return false;
        }
        p_players.add(new Player(p_playerName));
        return true;
    }

    /**
     * Assigning the countries to players
     * @param p_map WarGame map
     * @param p_players players list
     * @return true if successful, else false
     */
    public boolean assignCountries(WargameMap p_map, ArrayList<Player> p_players) {
        d_Ge.d_LogEntry.setMessage("Assigning Countries to players");
        int l_numberOfPlayers = p_players.size();
        if(p_players.size()<2) {
            System.out.println("At least 2 players are required. ");
            return false;
        }
        int l_counter = 0;
        for(Country l_c : p_map.getCountries().values()) {
            Player l_p = p_players.get(l_counter);
            l_p.getOwnedCountries().put(l_c.getCountryName().toLowerCase(), l_c);
            l_c.setcountryOwnerPlayer(l_p);
            d_Ge.d_LogEntry.setMessage("Country: "+l_c.getCountryName().toLowerCase()+" assigned to player: "+l_p.getPlayerName());
            if(l_counter>=l_numberOfPlayers-1)
                l_counter = 0;
            else
                l_counter++;
        }
        return true;
    }

    public void showMap(ArrayList<Player> p_players, WargameMap p_map) {
        d_mapView.showMap(p_players, p_map);
    }

    @Override
    public void issue_order(Player p_player) {
        printInvalidCommandMessage();
        d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }


    @Override
    public void gamePlayer(String[] p_data,ArrayList<Player> p_players, String p_playerName) {

        String l_playerName = null;
        try {
            for (int i = 1; i < p_data.length; i++) {
                if (p_data[i].equals("-add")) {
                    if (d_Ge.validatePlayerName(p_data[i + 1])) {
                        p_playerName = p_data[i + 1];
                        playerStrategy = p_data[i + 2];
                        boolean l_check = addPlayer(p_players, p_playerName);
                        if (l_check && d_Ge.isAlphabetic(p_data[i + 2])) {
                            for (Player l_p : d_Ge.d_Players) {
                                if (l_p.getPlayerName().equals(p_playerName)) {
                                    switch (playerStrategy) {
                                        case "human":
                                            l_p.set_isHuman(true);
                                            break;
                                        case "aggressive":
                                            l_p.setStrategy(new AggressiveStrategy(l_p, d_Ge.d_map));
                                            l_p.set_isHuman(false);
                                            break;
                                        case "benevolent":
                                            l_p.setStrategy(new BenevolentStrategy(l_p, d_Ge.d_map));
                                            l_p.set_isHuman(false);
                                            break;
                                        case "random":
                                            l_p.setStrategy(new RandomStrategy(l_p, d_Ge.d_map));
                                            l_p.set_isHuman(false);
                                            break;
                                        case "cheater":
                                            l_p.setStrategy(new CheaterStrategy(l_p, d_Ge.d_map));
                                            l_p.set_isHuman(false);
                                            break;
                                        default:
                                            System.out.println("Wrong Strategy, Player set as human");
                                            l_p.set_isHuman(true);
                                            break;
                                    }
                                }
                            }
                            if (l_check) {
                                System.out.println(p_playerName+" added! with strategy : "+playerStrategy);
                                d_Ge.d_LogEntry.setMessage(p_playerName+" added! with strategy : "+playerStrategy);
                            } else {
                                System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
                                d_Ge.d_LogEntry.setMessage("Can not add any more player. Max pool of 6 Satisfied!");
                            }
                            d_Ge.d_GamePhase = GamePhase.STARTPLAY;
                        } else {
                            System.out.println("Invalid Player Name or Strategy");
                            d_Ge.d_LogEntry.setMessage("Invalid Player Name or Strategy");
                        }
                    } else if (p_data[i].equals("-remove")) {
                        if (d_Ge.validatePlayerName(p_data[i + 1])) {
                            p_playerName = p_data[i + 1];
                            boolean l_check = removePlayer(p_players, p_playerName);
                            if (l_check) {
                                System.out.println("Player removed!");
                                d_Ge.d_LogEntry.setMessage("Player removed! " + p_playerName);
                            } else {
                                System.out.println("Player doesn't exist");
                                d_Ge.d_LogEntry.setMessage("Player doesn't exist");
                            }
                            d_Ge.d_GamePhase = GamePhase.STARTPLAY;
                        } else
                            System.out.println("Invalid Player Name");
                        d_Ge.d_LogEntry.setMessage("Invalid Player Name");
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
            d_Ge.d_LogEntry.setMessage("Invalid command - it should be of the form gameplayer -add playername -remove playername");
        } catch (Exception e) {
            System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
            d_Ge.d_LogEntry.setMessage("Invalid command - it should be of the form gameplayer -add playername -remove playername");
        }
    }

}
