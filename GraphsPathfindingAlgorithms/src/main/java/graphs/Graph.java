package graphs;

import utils.Node;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    private boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
        adjacencyMap = new HashMap<>();
    }

    public void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjacencyMap.get(a);

        if (tmp != null) {
            tmp.remove(b);
        }
        else tmp = new LinkedList<>();
        tmp.add(b);
        adjacencyMap.put(a, tmp);
    }

    public void addEdge(Node source, Node destination) {

        if (!adjacencyMap.keySet().contains(source))
            adjacencyMap.put(source, null);

        if (!adjacencyMap.keySet().contains(destination))
            adjacencyMap.put(destination, null);

        addEdgeHelper(source, destination);

        if (!directed) {
            addEdgeHelper(destination, source);
        }
    }

    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

    public void resetNodesVisited(){
        for(Node node : adjacencyMap.keySet()){
            node.unvisit();
        }
    }

    public HashMap<Node, LinkedList<Node>> getAdjacencyMap(){
        return adjacencyMap;
    }
}
