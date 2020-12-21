package api;


import java.io.Serializable;
import java.util.Objects;

/**
 * EdgeData class implements the edge_data interface representing an edge in a graph.
 * Each edge in a graph has a source from which it came from and a destination. The edge also has a weight
 * which will be used to determine shortest paths from two different nodes in the graph, and as well in the Game part of the repository.
 * Methods:
    * Constructor.
    * Getters / Setters
    * equals between two EdgeData objects.
    * compare between two EdgeData objects.
 *
 */

public class EdgeData implements edge_data, Serializable,Comparable<EdgeData> {

    private int source;
    private int dest;
    private int tag;
    private double weight;
    private String info;


    public EdgeData(int src, int d, double w) {
        this.source = src;
        this.dest = d;
        this.weight = w;
    }

    //// Setter & Getters :
    @Override
    public int getSrc() {
        return this.source;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return info;
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

    /**
     * boolean method two check quality between two EdgeData objects.
     * @param o - the object to weight against equality.
     * @return - true iff (if and only if) both edge are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        edge_data edgeData = (edge_data) o;
        if (weight == edgeData.getWeight() && source == edgeData.getSrc() && tag == edgeData.getTag()) return true;


        return false;


    }
    @Override
    public int hashCode() {
        return Objects.hash(source, dest, weight);
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "source=" + source +
                ", dest=" + dest +
                ", weight=" + weight +
                '}';
    }

    /**
     * comparing between two EdgeData objects for use in DWGraph_Algo methods.
     * @param o - the EdgeData object to compare with based on edges weight.
     * @return - 1 if 1st edge is heavier than the other and -1 vice versa.
     */

    @Override
    public int compareTo( EdgeData o) {
        return weight<o.weight?1:-1;
    }
}
