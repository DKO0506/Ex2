package api;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {




    @Test
    void getNode() {
        directed_weighted_graph graph1 =new DWGraph_DS();
        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        graph1.addNode(n0);
        graph1.addNode(n1);
        System.out.println("need to be null(0->1)"+graph1.getEdge(n0.getKey(),n1.getKey()));
        System.out.println("need to be null(1->0)"+graph1.getEdge(n1.getKey(),n0.getKey()));
        graph1.connect(n0.getKey(), n1.getKey(), 10);
        System.out.println("connect 0->1");
        System.out.println("need to be 20(0->1):rsc is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getSrc()+" dest is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getWeight());
        System.out.println("need to be null(1->0)"+graph1.getEdge(n1.getKey(),n0.getKey()));
        graph1.connect(n1.getKey(), n0.getKey(), 20);
        System.out.println("connect 1->0");
        System.out.println("need to be 10(0->1):rsc is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getSrc()+" dest is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n0.getKey(),n1.getKey()).getWeight());
        System.out.println("need to be 20(1->0):rsc is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getSrc()+" dest is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getDest()+" the Weight is- "+graph1.getEdge(n1.getKey(),n0.getKey()).getWeight());

    }

    @Test
    void getEdge() {


    }

    @Test
    void addNode() {
        directed_weighted_graph graph =new DWGraph_DS();
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        node_data n4=new NodeData(4);

        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        Collection<node_data> coll= new LinkedList();
        coll=graph.getV();
        for (node_data n:coll) {
            System.out.println("node key: "+n.getKey());
            System.out.println("Location: "+"x:"+n.getLocation().x()+"  y:"+n.getLocation().y()+"  z:"+n.getLocation().z());

        }

    }

    @Test
    void connect() {
        directed_weighted_graph graph2 =new DWGraph_DS();
        node_data n5=new NodeData(5);
        node_data n6=new NodeData(6);
        graph2.addNode(n5);
        graph2.addNode(n6);
        assertNull(graph2.getEdge(n5.getKey(),n6.getKey()),"need to be null(0->1)");
        assertNull(graph2.getEdge(n6.getKey(),n5.getKey()),"need to be null(1->0)");
        graph2.connect(n5.getKey(), n6.getKey(), 10);
        System.out.println("connect 0->1");
        assertEquals(10,graph2.getEdge(n5.getKey(),n6.getKey()).getWeight(),"need to be 10(0->1)");
        assertNull(graph2.getEdge(n6.getKey(),n5.getKey()),"need to be null(1->0)");

        graph2.connect(n6.getKey(), n5.getKey(), 20);
        System.out.println("connect 1->0");
        assertEquals(10,graph2.getEdge(n5.getKey(),n6.getKey()).getWeight(),"need to be 10(0->1)");
        System.out.println("need to be 20(1->0):rsc is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getSrc()+" dest is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getDest()+" the Weight is- "+graph2.getEdge(n6.getKey(),n5.getKey()).getWeight());
        assertEquals(20,graph2.getEdge(n6.getKey(),n5.getKey()).getWeight(),"need to be 20(1->0)");


    }

    @Test
    void getV() {
        directed_weighted_graph graph3 =new DWGraph_DS();
        assertEquals(0,graph3.getV().size(),"no nodse in the graph");

        node_data n0=new NodeData(0);
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        graph3.addNode(n0);
        graph3.addNode(n1);
        graph3.addNode(n2);
        graph3.addNode(n3);
        assertEquals(4,graph3.getV().size(),"4 nodse in the graph");
        graph3.removeNode(0);
        graph3.removeNode(1);
        graph3.removeNode(3);
        assertEquals(1,graph3.getV().size(),"1 nodse in the graph");
        assertEquals(true,graph3.getV().contains(n2),"the node in the graph is 2");

    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}