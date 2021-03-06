package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.io.*;
import java.util.*;


/**
 * DwGraph_Algo class implements the dw_graph_algo interface of exploring directed weighted graphs.
 * The class uses the DWGraph_DS to create a directed weighted graph structure to implement the algorithms to apply on the graph.
 * Class Methods:
 * Constructor
 * Init - initializing the graph structure to perform the methods.
 * getGraph - returns the graph which is manipulated.
 * copy - computes a deep copy of a graph
 * isConnected - a two part method that uses the BFS method as well to check connectivity.
 * shortestPath - a method to calculate the shortest path between two nodes and return a List of it.
 * shortestPathDist - calculating the distance between two nodes and returns the double value of it.
 * save - creates a new Json file format to a relative filepath (by default in the repository) with the data of the specific graph.
 * load - loads a new DWGraph_Algo object from a Jsom file format.
 */


public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph algo;

    //// Constructors
    public DWGraph_Algo() {
        algo = new DWGraph_DS();
    }


    /**
     * Initialize the directed_weighted_graph to a DWGraph_Algo to implement the methods on.
     *
     * @param g - an object that implements the directed_weighted_graph interface to init the methods on.
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.algo = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return algo;
    }

    /**
     * copy creates a new DWGraph_DS deep copy of the DWGraph_Algo that the function was executed on.
     *
     * @return a new DWGraph_DS with the exact data as the other one.
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(algo);
    }

    /**
     * isConnected method uses the BFS algorithm while iterating over all of the nodes existing in the graph.
     * given the graph that's being explored using this method complexity is high.
     *
     * @return true iff it is possible to reach all the nodes from every node.
     */
    @Override
    public boolean isConnected() {
        int counter = 0;
        int V = algo.getV().size();
        Queue<node_data> queue = new LinkedList<>();
        node_data start = null;
        resetAllTags(-1);
        boolean isIt = true;
        for (node_data v : algo.getV()) {
            start = v;
            if (start == null) {
                return true;
            }
            queue.add(start);
            start.setTag(0);
            counter++;
            while (!queue.isEmpty()) {
                node_data polled = queue.poll();
                for (edge_data e : algo.getE(polled.getKey())) {
                    node_data nextNode = algo.getNode(e.getDest());
                    if (nextNode.getTag() !=0) {
                        counter++;
                        nextNode.setTag(0);
                        queue.add(nextNode);
                    }
                }
            }
            if (V != counter) {
                isIt = false;
                break;
            }
            resetAllTags(-1);
            counter = 0;
        }
        return isIt;
    }

    /**
     * calculates the double value of the distance between two nodes. usnig the shortestPath method to obtain the List of the shortest path and sizing it.
     *
     * @param src  - start node.
     * @param dest - end (target) node.
     * @return - the size of the list that is the shortest path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        HashMap<Integer, Double> dist = new HashMap<>();
        Queue<node_data> queue = new LinkedList<>();
        if (algo.getNode(src) == null || algo.getNode(dest) == null) return -1;
        if (src == dest) return 0;
        for (node_data v : algo.getV()) {
            v.setInfo("");
        }
        queue.add(algo.getNode(src));
        dist.put(src, 0.0);
        algo.getNode(src).setInfo(algo.getNode(src).getKey() + "");
        while (!queue.isEmpty()) {
            node_data polled = queue.poll();
            for (edge_data e : algo.getE(polled.getKey())) {
                node_data nextNode = algo.getNode(e.getDest());
                double w = dist.get(e.getSrc()) + e.getWeight();
                if (!dist.containsKey(e.getDest()) || dist.get(e.getDest()) > w) {
                    dist.put(e.getDest(), w);
                    algo.getNode(e.getDest()).setInfo(polled.getInfo() + "_" + algo.getNode(e.getDest()).getKey());
                    queue.add(nextNode);
                }
            }
        }

        if (!dist.containsKey(dest)) return -1;

        return dist.get(dest);

    }

    /**
     * Calculating the shortest path between two nodes using Dijkstra's algorithm to explore the graph.
     * Dijkstra's algorithm is simplified by these steps:
     * Set the distance for the current node to 0 (its own weight rather than its edges weight).
     * Set all distances (in this case the weight of the edge to infinty) for each node in the graph.
     * Visit all of the current node neighbors with the smallest known distance.
     * If the current known distance is smaller thanthe last known distance update the distance to the current distance.
     * Update the previous node for each of the updated distances
     * Add the current node to the list of visited nodes
     * Repeat all previous steps until all nodes are marked as visited.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return a list containing the path of the path. {v/in V | the vertex in the path }
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data>answer=new LinkedList<>();
        double shortDist=shortestPathDist(src,dest);
        if (shortDist==-1)return null;
        else if (shortDist==0){
            answer.add(algo.getNode(src));
            return answer;
        }
        else
        {
            String[] allThePaths=algo.getNode(dest).getInfo().split("_");
            for (int i=0;i<allThePaths.length;i++){
                Integer.parseInt(allThePaths[i]);
                answer.add(algo.getNode((Integer.parseInt(allThePaths[i]))));
            }
        }
        return answer;
    }

    /**
     * This method saves a DWGraph_Algo object in a Json format file containing the information of all other objects used to create the graph.
     * The graph is saved as a Json object to which we assign JsonArrays containing Json objects that make the graph (edge_data, node_data).
     * Gson library is used to manipulate the JSON format into a File and using the FileWriter to write to the new File created.
     *
     * @param file - the file name (may include a relative path) of the location to save the Json File. (inside project directory if a path wasn't assigned)
     * @return true iff the process of saving the file was accomplished.
     */

    @Override
    public boolean save(String file) {

        try {
            Gson convertedGraph = new Gson(); // creating the Gson object to convert java object DWGraph_Algo to a Json format.
            JsonObject json_graph = new JsonObject(); // Json object to accept the graph characteristic in json format
            JsonArray json_vertices = new JsonArray(); // json array to contain the node_data information
            JsonArray json_edges = new JsonArray(); // json array to contain the edge_data information
            for (node_data v : this.algo.getV()) {
                JsonObject node = new JsonObject();
                node.addProperty("pos", v.getLocation().x() + "," + v.getLocation().y() + "," + v.getLocation().z());
                node.addProperty("id", v.getKey());
                json_vertices.add(node);
                for (edge_data e : this.algo.getE(v.getKey())) {
                    JsonObject edge = new JsonObject();
                    edge.addProperty("src", e.getSrc());
                    edge.addProperty("w", e.getWeight());
                    edge.addProperty("dest", e.getDest());
                    json_edges.add(edge);
                } // for each edge_data of the current node adding the properties of it to the JsonArray
                json_graph.add("Edges", json_edges);
                json_graph.add("Nodes", json_vertices);

            } // iterating over the graph nodes and create each one as a Json object to be added to the overall graph Json
            File graphFile = new File(file);
            FileWriter saved_graph = new FileWriter(file);
            saved_graph.write(convertedGraph.toJson(json_graph));
            saved_graph.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * This method loads a new DWGraph_Algo java object from a Json file format. Using FileReader to extract the information in the json text format a new JsonParser is created
     * to convert the text to an Object. Using the methods of Gson library the JsonArrays are created from the file that is read to devide it to the elements that compose the graph (edge_data,node_data).
     *
     * @param file - file name of JSON file (could also be the file path).
     * @return true iff the load of the new DWGraph_Algo was successful.
     */

    @Override
    public boolean load(String file) {
        try {
            FileReader graph_tl = new FileReader(file); // file reader to extract the information from
            JsonObject json_graph = new JsonParser().parse(graph_tl).getAsJsonObject(); // creating the Json object by parsing it by the values and keys
            JsonArray loaded_edges = json_graph.getAsJsonArray("Edges"); // create the JsonArray by the value "Edges"
            JsonArray loaded_vertices = json_graph.getAsJsonArray("Nodes"); //create the JsonArray by the value "Nodes"
            directed_weighted_graph loaded_graph = new DWGraph_DS(); // the new graph to assign the data to.

            for (JsonElement node : loaded_vertices) {
                node_data n = new NodeData(((JsonObject) node).get("id").getAsInt()); // creating the new NodeData by the value of its "id"
                String location = ((JsonObject) node).get("pos").getAsString(); // the 3DPoint field of the NodeData is an object on its own so in a json format it is bounded with {}
                String[] xyz = location.split(","); // for the 3DPoint object the String[] holds the x,y,z positions
                Point3D nLocation = new Point3D(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2])); // converting the String info to the double value of it to create a new 3DPoint object.
                n.setLocation(nLocation); // assign the location to the node.
                loaded_graph.addNode(n); // adding it to the graph
            }

            for (JsonElement edge : loaded_edges) {
                int src = ((JsonObject) edge).get("src").getAsInt(); // source of the edge
                double w = ((JsonObject) edge).get("w").getAsDouble(); // weight of the edge
                int dest = ((JsonObject) edge).get("dest").getAsInt(); // destination of the edge
                edge_data json_edge = new EdgeData(src, dest, w); // new EdgeData object based on the info extracted from the json text.
                loaded_graph.connect(json_edge.getSrc(), json_edge.getDest(), json_edge.getWeight()); // connecting the nodes in the graph

            } // iterating over the JsonArray for the edge_data to add to the graph
            this.algo = loaded_graph;
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return algo.toString();
    }


    /**
     * resetting all the tags of the graph to -1
     * as sign that the node isn't visited yet.
     */

    private void resetAllTags(int t) {
        for (node_data v : algo.getV()) {
            v.setTag(t);
        }
    }

}
