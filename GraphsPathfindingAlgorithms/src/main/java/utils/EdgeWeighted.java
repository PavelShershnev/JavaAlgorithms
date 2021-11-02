package utils;


public class EdgeWeighted implements Comparable<EdgeWeighted> {
    private NodeWeighted source;
    private NodeWeighted destination;
    private double weight;

    public EdgeWeighted (NodeWeighted s, NodeWeighted d, double w) {
        source = s;
        destination = d;
        weight = w;
    }

    public void setWeight (double weight) {
        this.weight = weight;
    }

    public NodeWeighted getSource () {
        return source;
    }

    public NodeWeighted getDestination () {
        return destination;
    }

    public double getWeight () {
        return weight;
    }

    @Override
    public int compareTo(EdgeWeighted otherEdge) {
        if (this.weight > otherEdge.weight) {
            return 1;
        }
        else return -1;
    }
}
