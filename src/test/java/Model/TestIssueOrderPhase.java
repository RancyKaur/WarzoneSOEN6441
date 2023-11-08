package Model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import Controller.GameEngine;

/**
 * Test Issue Order phase under State Pattern
 */
public class TestIssueOrderPhase {

    public GameEngine d_Ge;

    EngineCommand d_Rge;
    Order d_Order;
    Queue<Order> d_OrderList;
    Player d_Player;
    String d_PlayerName;
    ArrayList<Player> d_Players;
    WargameMap d_Map;

    /**
     * initial setup
     */
    @Before
    public void before(){
        d_Ge = new GameEngine();
        d_PlayerName = "Rancy";
        d_Player = new Player(d_PlayerName);
        d_Order = new Deploy(d_Player,"India",4);
        d_OrderList = new ArrayDeque<>();
        d_Players = new ArrayList<Player>();
        d_Map = new WargameMap("test2.map");
    }

    @Test
    public void testIssueOrder(){
        //adds an order
        d_Ge.setPhase(new IssueOrderPhase(d_Ge));
        String str=d_Ge.d_Phase.getD_PhaseName();
        System.out.println(str);

        d_Player.addOrder(d_Order);
        d_Ge.d_Phase.issue_order(d_Player);

        boolean a=d_Ge.d_Phase.assignCountries(d_Map,d_Players );
        System.out.println(a);

        assertEquals(false, a);
        String str1=d_Ge.d_Phase.getD_PhaseName();
        System.out.println("Game is in "+str1+" state");
    }
}
