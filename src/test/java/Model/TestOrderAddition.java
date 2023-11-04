package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;

import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Tests if addition of orders works
 */
public class TestOrderAddition {
    Deploy d_Order;
    Queue<Deploy> d_OrderList;
    Player d_Player;
    String d_PlayerName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_PlayerName = "Ronak";
        d_Player = new Player(d_PlayerName);
        d_Order = new Deploy(d_Player,"Canada",4);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testAddOrder(){
        //adds an order
        d_OrderList.add(d_Order);
        assertEquals(d_OrderList.peek(), d_Order);
    }

}
