package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;

import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Tests if addition of orders works
 */
public class TestOrderRemoval {
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
        d_Order = new Deploy(d_Player,"India",5);
        d_OrderList = new ArrayDeque<>();
        d_OrderList.add(d_Order);
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testRemoveOrder(){
        //removes order
        d_OrderList.poll();
        assertEquals(0,d_OrderList.size());
    }

}


