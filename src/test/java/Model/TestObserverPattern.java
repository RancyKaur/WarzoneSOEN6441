package Model;

import Controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *  Test class to confirm if observer pattern is working as intended
 *  That is if LogEntry(observable) updates the LogWriter(observer) and if it writes to log file log.txt
 */
public class TestObserverPattern {

    /**
     * d_Ge is reference for gameEngine
     */
    public GameEngine d_Ge;
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    String d_PlayerName="xyz";
    EngineCommand d_Rge;
    String[] d_Data={"gameplayer", "-add", "xyz"};
    String[] d_Data1 = new String[]{"loadmap","world.map"};
    String d_MapName;

    /**
     * initial setup
     */
    @Before
    public void before(){
        d_Ge = new GameEngine();
        d_Rge = new EngineCommand();
        d_Players = new ArrayList<Player>();
        d_Map = new WargameMap("test2.map");
        d_Player1 = new Player("Vignesh");
        d_Player2 = new Player("Sujith");
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
    }

    /**
     *Test if LogEntryBuffer(observable) updates the WriteLogEntry(observer)
     *  to write to log file log.txt
     */
    @Test
    public void testLogEntryBuffer(){

        //Testing preload phase logs are stored in log file or not
        d_Ge.setPhase(new MapEditorLoad(d_Ge));
        d_MapName= "test2.map";
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);
        d_Ge.d_Phase.loadMap(d_Data1,d_MapName);

        //Testing if invalid
        System.out.println("Testing invalid command(gameplayer) in phase: "+ d_Ge.d_Phase.getD_PhaseName());
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player1.getPlayerName());
        assertEquals("Invalid command in PostLoad state","Invalid command in PostLoad state");

        d_Map = d_Rge.editMap(d_MapName);
        d_Ge.d_Phase.showMap(d_Map);

        //Testing startup phase are stored in log file or not
        d_Ge.setPhase(new StartUp(d_Ge));
        d_Ge.d_LogEntry.setGamePhase(d_Ge.d_Phase);
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player1.getPlayerName());
        d_Ge.d_Phase.gamePlayer(d_Data,d_Players,d_Player2.getPlayerName());
        d_Ge.d_Phase.assignCountries(d_Map,d_Players );

    }


}

