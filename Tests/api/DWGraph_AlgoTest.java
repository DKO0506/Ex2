package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    static dw_graph_algorithms ga = new DWGraph_Algo();
    static directed_weighted_graph g1 = new DWGraph_DS();
    static directed_weighted_graph g2 = new DWGraph_DS();
    static directed_weighted_graph g3 = new DWGraph_DS();
    static directed_weighted_graph g4 = new DWGraph_DS();
    static directed_weighted_graph g5 = new DWGraph_DS();


    static void createG(int size, directed_weighted_graph g) {
        for (int i = 1; i <= size; i++) {
            g.addNode(new NodeData(i));
        }
    }


    @BeforeAll
    static void initialize() {
        createG(10, g1);
        g1.connect(1, 2, 0.5);
        g1.connect(1, 3, 2.5);
        g1.connect(3, 4, 1.98);
        g1.connect(2, 5, 8.3);
        g1.connect(5, 7, 4.1);
        g1.connect(9, 7, 2.4);
        g1.connect(6, 7, 3.1);
        g1.connect(7, 6, 3.1);
        g1.connect(8, 9, 1.8);
        g1.connect(4, 9, 9.6);
        g1.connect(2, 6, 5.6);

        createG(10, g2);
        g2.connect(1, 6, 5);
        g2.connect(1, 5, 20);
        g2.connect(1, 4, 20);
        g2.connect(1, 2, 10);
        g2.connect(1, 7, 15);
        g2.connect(2, 4, 10);
        g2.connect(2, 3, 5);
        g2.connect(3, 4, 5);
        g2.connect(3, 2, 15);
        g2.connect(4, 5, 10);
        g2.connect(5, 6, 5);
        g2.connect(7, 6, 10);
        g2.connect(8, 7, 5);
        g2.connect(8, 1, 5);
        g2.connect(8, 2, 20);
        g2.connect(9, 8, 20);
        g2.connect(9, 2, 15);
        g2.connect(9, 10, 10);
        g2.connect(10, 3, 15);
        g2.connect(10, 2, 5);

        createG(4, g3);
        g3.connect(1, 2, 5);
        g3.connect(3, 2, 5);
        g3.connect(1, 3, 5);
        g3.connect(4, 1, 5);
        g3.connect(2, 4, 5);
        g3.connect(4, 2, 5);

        createG(4, g4);
        g4.connect(1, 2, 5);
        g4.connect(3, 2, 5);
        g4.connect(3, 1, 5);
        g4.connect(1, 4, 5);
        g4.connect(2, 4, 5);
        g4.connect(4, 2, 5);


        createG(3, g5);
        g5.connect(1, 2, 5);
        g5.connect(2, 3, 5);
        g5.connect(3, 1, 5);

    }

    @Test
    void isConnected() {
        ga.init(g1);
        System.out.println("g1 = " + ga.isConnected());
        assertFalse(ga.isConnected());

        ga.init(g2);
        System.out.println("g2 = " + ga.isConnected());
        assertFalse(ga.isConnected());
        ga.init(g3);
        System.out.println("g3 = " + ga.isConnected());
        assertTrue(ga.isConnected());
        ga.init(g4);
        System.out.println("g4 = " + ga.isConnected());
        assertFalse(ga.isConnected());
        ga.init(g5);
        System.out.println("g5 = " + ga.isConnected());
        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms algo1 = new DWGraph_Algo();
        algo1.init(g1);
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();
        g1.addNode(n1);
        g1.addNode(n2);
        boolean e = algo1.isConnected();
        g1.connect(n1.getKey(), n2.getKey(), 3.0D);
        g1.connect(n2.getKey(), n1.getKey(), 4.0D);
        boolean e1 = algo1.isConnected();
        double d = algo1.shortestPathDist(n1.getKey(), n2.getKey());
        Assertions.assertEquals(false, e, "Test1 Error in isConnected");
        Assertions.assertEquals(true, e1, "Test1 Error in isConnected");
        Assertions.assertEquals(3.0D, d, "Test1 Error in ShortestPathDist");
    }

    @Test
    void Test2() {
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        node_data n3 = new NodeData(3);
        node_data n4 = new NodeData(4);
        node_data n5 = new NodeData(5);
        directed_weighted_graph graph1 = new DWGraph_DS();
        graph1.addNode(n0);
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addNode(n4);
        graph1.addNode(n5);
        graph1.connect(0, 1, 4.0D);
        graph1.connect(0, 2, 3.0D);
        graph1.connect(1, 2, 5.0D);
        graph1.connect(1, 3, 2.0D);
        graph1.connect(2, 3, 7.0D);
        graph1.connect(3, 4, 2.0D);
        graph1.connect(4, 5, 6.0D);
        graph1.connect(4, 0, 4.0D);
        graph1.connect(4, 1, 4.0D);
        dw_graph_algorithms algo1 = new DWGraph_Algo();
        algo1.init(graph1);
        double d1 = algo1.shortestPathDist(5, 3);
        double d2 = algo1.shortestPathDist(4, 4);
        double d3 = algo1.shortestPathDist(0, 4);
        boolean e = algo1.isConnected();
        LinkedList<node_data> path = (LinkedList) algo1.shortestPath(0, 4);
        Assertions.assertEquals(-1.0D, d1, "ERROR in shortestPathDist");
        Assertions.assertEquals(0.0D, d2, "ERROR in shortestPathDist");
        Assertions.assertEquals(8.0D, d3, "ERROR in shortestPathDist");
        Assertions.assertEquals(false, e, "ERROR in isConnected");
        int i = 0;

        for (Iterator var18 = path.iterator(); var18.hasNext(); ++i) {
            node_data n = (node_data) var18.next();
            if (i == 0) {
                Assertions.assertEquals(0, n.getKey(), "Error in ShortestPath ");
            } else if (i == 1) {
                Assertions.assertEquals(1, n.getKey(), "Error in ShortestPath ");
            } else if (i == 2) {
                Assertions.assertEquals(3, n.getKey(), "Error in ShortestPath ");
            } else if (i == 3) {
                Assertions.assertEquals(4, n.getKey(), "Error in ShortestPath ");
            } else {
                Assertions.assertEquals(true, false, "Error in ShortestPath - too many nodes in the path");
            }
        }
    }
}