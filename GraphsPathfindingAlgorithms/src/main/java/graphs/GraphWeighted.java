package graphs;

import utils.EdgeWeighted;
import utils.NodeWeighted;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GraphWeighted {
    private Set<NodeWeighted> nodes;
    private boolean directed;

    public GraphWeighted (boolean directed) {
        this.directed = directed;
        nodes = new HashSet<>();
    }

    public Set<NodeWeighted> getNodes () {
        return nodes;
    }

    public boolean isDirected () {
        return directed;
    }

    public void addNode(NodeWeighted... n) {
        nodes.addAll(Arrays.asList(n));
    }

    public void addEdge(NodeWeighted source, NodeWeighted destination, double weight) {
        nodes.add(source);
        nodes.add(destination);

        addEdgeHelper(source, destination, weight);

        if (!directed && source != destination) {
            addEdgeHelper(destination, source, weight);
        }
    }
    public void addEdge(NodeWeighted source, NodeWeighted destination) {
        nodes.add(source);
        nodes.add(destination);

        addEdgeHelper(source, destination, 0);

        if (!directed && source != destination) {
            addEdgeHelper(destination, source, 0);
        }
    }

    private void addEdgeHelper(NodeWeighted a, NodeWeighted b, double weight) {
        for (EdgeWeighted edge : a.getEdges()) {
            if (edge.getSource() == a && edge.getDestination() == b) {
                edge.setWeight(weight);
                return;
            }
        }
        a.getEdges().add(new EdgeWeighted(a, b, weight));
    }

    @Override
    public String toString () {
        return "GraphWeighted{" +
                "nodes=" + nodes;
    }

    public boolean hasEdge(NodeWeighted source, NodeWeighted destination) {
        LinkedList<EdgeWeighted> edges = source.getEdges();
        for (EdgeWeighted edge : edges) {
            if (edge.getDestination() == destination) {
                return true;
            }
        }
        return false;
    }

    public int nodeCount(){
        return nodes.size();
    }

    public NodeWeighted getNode(int id){
        return getNodes().stream().filter(node -> node.getN() == id).findFirst().get();
    }

    public void resetNodesVisited() {
        for (NodeWeighted node : nodes) {
            node.unvisit();
        }
    }
}
