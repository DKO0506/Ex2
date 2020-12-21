package api;


import gameClient.util.Point3D;

import java.util.Objects;

/**
 * Class NodeData implements the node_data interface. the NodeData class represents a vertex in a graph.
 * The methods that are used in this class are:
    * Constructor / Copy constructor.
    * Getters and Setters.
    * compare method bewteen two nodes.
    * equals method bewteen two nodes.
 * Each NodeData it has its own properties:
        * int key - a unique id for a node in the graph.
        * int tag - mostly used for the graph algorithms (explicit explanation in the DWgraph_algo class).
        * geo_location - will be used for part 2 in the assignment.
        * double weight - more in Game package.
        * String - info for the node.
        *
 */

public class NodeData implements node_data, Comparable {
    private int key;
    private int tag;
    private geo_location nodeLocation;
    private double weight;
    private String info;

    ///Constructor and copy constructor

    public NodeData(int id) {
        this.key = id;
        this.nodeLocation = new Point3D(0, 0, 0);
        info="";
    }
    public NodeData(int id,geo_location g) {
        this.key=id;
        nodeLocation=g;
    }
    public NodeData(node_data other) {
        this.key = other.getKey();
        this.info = other.getInfo();
        this.tag = other.getTag();
        this.weight = other.getWeight();
        this.nodeLocation = other.getLocation();
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return nodeLocation;
    }

    @Override
    public void setLocation(geo_location p) {
        this.nodeLocation = p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }


    @Override
    public String toString() {
        return "NodeData{" +
                "key=" + key +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                '}';
    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass() || obj == null) return false;
        NodeData n = (NodeData) obj;
        return key == n.key && Objects.equals(info, n.info);
    }

    @Override
    public int compareTo(Object o) {
        NodeData n = (NodeData) o;
        return (int) (weight - n.weight);
    }
}
