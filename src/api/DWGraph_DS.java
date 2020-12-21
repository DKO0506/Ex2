package api;

import java.util.*;

/**
 * DWGraph_DS class represents a graph using a HashMap of key Integer and node_data as value, through the field
 * Vertices. In directed graphs there are two types of edges connecting the different vertices, for that there are two separated
 * HashMaps both for incoming edges and outgoing edges.
 * The different methods to be performed in a graph:
 * Constructors / Copy constructor (deep copy)
 * Adding a node to the graph.
 * Obtain a vertex (node_data) by its index.
 * Connect between different nodes (a connection isn't bidirectional).
 * Obtain an edge information by its source and destination.
 * Obtain the information regarding all edges that are related to a given node (both out and in).
 * Deleting edges/nodes from the graph.
 */


public class DWGraph_DS implements directed_weighted_graph {

    private HashMap<Integer, node_data> Vertices;

    private HashMap<Integer, HashMap<Integer, edge_data>> inDegree;
    private HashMap<Integer, HashMap<Integer, edge_data>> outDegree;

    private int V;
    private int E;
    private int MC;

    /**
     * Constructor and Copy constructor:
     */

    public DWGraph_DS() {
        Vertices = new HashMap<>();
        inDegree = new HashMap<>();
        outDegree = new HashMap<>();
        V = 0;
        E = 0;
        MC = 0;
    }

    public DWGraph_DS(directed_weighted_graph other) {
        Vertices = new HashMap<>();
        outDegree = new LinkedHashMap<>();
        inDegree = new LinkedHashMap<>();
        for (node_data v : other.getV()) {
            node_data u = new NodeData(v);
            this.addNode(u);
        }

        for (node_data v : getV()) {
            if (other.getE(v.getKey()) != null) {
                for (edge_data e : other.getE(v.getKey())) {
                    this.connect(v.getKey(), e.getDest(), e.getWeight());
                }
            }
        }
        this.V = other.nodeSize();
        this.E = other.edgeSize();
        this.MC = other.getMC();
    }

    /**
     * getNode method returns the reference to the node_data by its unique key in the graph.
     * @param key - the node_id
     * @return the node in the graph by its key
     */
    @Override
    public node_data getNode(int key) {
        return Vertices.get(key);
    }

    /**
     * getEdge returns the reference to the edge between two vertices in the graph
     * @param src - the vertex from which the edge is directed from.
     * @param dest - the vertex which the edge is directed to.
     * @return edge_data between them.
     */

    @Override
    public edge_data getEdge(int src, int dest) {
        boolean firstCondition = Vertices.containsKey(src) && Vertices.containsKey(dest);
        boolean secondCondition = inDegree.get(src).containsKey(dest);

        if (firstCondition) {
            if (secondCondition) {
                return inDegree.get(dest).get(src);
            }
        }
        return null;

    }

    /**
     * Adds the node_data to the HashMap of the vertices.
     * @param n - the new vertex in the graph.
     */

    @Override
    public void addNode(node_data n) {
//        if (Vertices.containsKey(n.getKey())) {
//            System.err.println("Already exists");
//        }
        Vertices.put(n.getKey(), n);
        inDegree.put(n.getKey(), new HashMap<>());
        outDegree.put(n.getKey(), new HashMap<>());

        MC++;
        V++;
    }

    /**
     * connect method is creates a new edge between two vertices in the graph. for a directed graph the edge is represented as in an ordered pair,
     * (src,dest) which are the end points of the edge.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */

    @Override
    public void connect(int src, int dest, double w) {
        boolean primary = Vertices.containsKey(src) && Vertices.containsKey(dest);
        boolean nodes = Vertices.get(src) != null && Vertices.get(dest) != null;
        boolean edge = outDegree.get(src).containsKey(dest);

        if (primary && nodes && src != dest) {
            if (!edge) {
                edge_data e = new EdgeData(src, dest, w);
                outDegree.get(src).put(dest, e);
                inDegree.get(dest).put(src, e);
                E++;
                MC++;
            }
        }

    }

    /**
     * getV return a colection of all the node_data representing the graph vertices.
     * @return Collection<node_data>.
     */
    @Override
    public Collection<node_data> getV() {
        return Vertices.values();
    }

    /**
     * getE returns all the edges related to a given vertex.
     * @param node_id - the vertex to extract the information about.
     * @return - Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {

        return outDegree.get(node_id).values();
    }

    /**
     * Removes a node_data from the graph structure including all referenced edges connected to it and from it.
     * @param key - the node_data to remove.
     * @return - deleted node from the graph.
     */
    @Override
    public node_data removeNode(int key) {

        node_data removedNode = Vertices.get(key);
        if (Vertices.containsKey(key)) {
            for (edge_data e : getE(key)) {
                outDegree.get(e.getDest()).remove(key);
                inDegree.get(key).remove(e.getSrc());
                E--;
                MC++;
            }
            Vertices.remove(key);
        }
        return removedNode;
    }

    /**
     * Removes the edge between two nodes
     * @param src - the source of the edge
     * @param dest - the destination of the edge
     * @return the deleted edge_data
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        boolean outEdge = outDegree.get(src).containsKey(dest);

        if (outEdge) {
            edge_data inRemoved = outDegree.get(src).remove(dest);
            if (inRemoved != null) {
                E--;
                MC++;
            }
            return inRemoved;
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return Vertices.size();
    }

    @Override
    public int edgeSize() {
        return E;
    }

    @Override
    public int getMC() {
        return MC;
    }

    public String toString() {
        String ans = "|V|=" + nodeSize() + ",|E|=" + edgeSize() + ",ModeCount=" + getMC() + "\n";

        for (node_data v : getV()) {
            ans += v.getKey() + ": ";
            for (edge_data e : getE(v.getKey())) {
                ans += "{<V:" + e.getSrc() + ",E:" + e.getDest() + ",W:" + e.getWeight() + ">},";
            }
            ans += "\n";
        }
        return ans;
    }

    public static void main(String[] args) {
        directed_weighted_graph G = new DWGraph_DS();
        G.addNode(new NodeData(2));
        G.addNode(new NodeData(3));
        G.addNode(new NodeData(0));
        G.addNode(new NodeData(1));

        G.connect(0, 3, 10);
        G.connect(0, 2, 10);
        G.connect(0, 1, 10);


        G.removeNode(0);

        directed_weighted_graph o = new DWGraph_DS(G);
        System.out.println(G);
        System.out.println(o.equals(G));
    }
}
