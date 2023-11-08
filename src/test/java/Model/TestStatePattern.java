package Model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Controller.GameEngine;

/**
 *
 *Test case to validate state pattern by checking if phase state is running properly or not
 */
public class TestStatePattern {
    public GameEngine d_Ge;
    Player d_Player1;
    Player d_Player2;
    WargameMap d_Map;
    ArrayList<Player> d_Players;
    String d_PlayerName="player1";
    EngineCommand d_Rge;
    String[] d_Data={"gameplayer", "-add", "player1"};
    String[] d_Data1 = new String[]{"edit","test2.map"};
    String d_MapName;

    /**
     * initial setup
     */
    @Before
    public void before(){
        d_Ge = new GameEngine();
        d_Players = new ArrayList<Player>();
        d_Map = new WargameMap("test2.map");
        d_Player1 = new Player("Player1");
        d_Player2 = new Player("Player2");
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_MapName= "world.map";
    }
    /**
     * Test if the Reinforcements is allowed in pre-load or post-load phase as it is part of startup phase.
     */
    @Test
    public void testMapEditorPhase(){

        d_Ge.setPhase(new MapEditorLoad(d_Ge));

        d_Ge.setPhase(new MapEditorLoad(d_Ge));
        String str=d_Ge.d_Phase.getD_PhaseName();
        System.out.println(str);
        d_Ge.d_Phase.editMap(d_Data1,d_MapName);
        boolean a=d_Ge.d_Phase.assignCountries(d_Map,d_Players );
        System.out.println(a);
        assertEquals(false, a);
        String str1=d_Ge.d_Phase.getD_PhaseName();
        System.out.println("Game is in "+str1+" state");
    }
}
