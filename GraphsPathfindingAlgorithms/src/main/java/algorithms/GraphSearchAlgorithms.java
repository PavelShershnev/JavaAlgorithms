package algorithms;

import graphs.Graph;
import graphs.GraphWeighted;
import utils.EdgeWeighted;
import utils.Node;
import utils.NodeWeighted;

import java.util.*;

public class GraphSearchAlgorithms {
    public static LinkedList<Node> depthFirstSearchModified (Node node, Graph graph1) {
        LinkedList<Node> solution = new LinkedList<>();
        depthFirstSearch(node, graph1, solution);
        for (Node n : graph1.getAdjacencyMap().keySet()) {
            if (!n.isVisited()) {
                depthFirstSearch(n, graph1, solution);
            }
        }
        return solution;
    }

    private static void depthFirstSearch (Node node, Graph graph, LinkedList<Node> solution) {
        node.visit();
        solution.add(node);

        LinkedList<Node> allNeighbors = graph.getAdjacencyMap().get(node);
        if (allNeighbors == null)
            return;

        for (Node neighbor : allNeighbors) {
            if (!neighbor.isVisited())
                depthFirstSearch(neighbor, graph, solution);
        }
    }

    public static LinkedList<Node> breadthFirstSearch (Node node, Graph graph1) {
        LinkedList<Node> solution = new LinkedList<>();
        if (node == null)
            return null;
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();
            if (currentFirst.isVisited())
                continue;
            currentFirst.visit();
            solution.add(currentFirst);

            LinkedList<Node> allNeighbors = graph1.getAdjacencyMap().get(currentFirst);

            if (allNeighbors == null)
                continue;

            for (Node neighbor : allNeighbors) {
                if (!neighbor.isVisited()) {
                    queue.add(neighbor);
                }
            }
        }
        return solution;
    }

    public static LinkedList<NodeWeighted> dijkstraShortestPath (NodeWeighted start, NodeWeighted end, GraphWeighted graph) {
        LinkedList<NodeWeighted> solution = new LinkedList<>();
        HashMap<NodeWeighted, NodeWeighted> changedAt = new HashMap<>();
        changedAt.put(start, null);
        HashMap<NodeWeighted, Double> shortestPathMap = new HashMap<>();

        for (NodeWeighted node : graph.getNodes()) {
            if (node == start)
                shortestPathMap.put(start, 0.0);
            else shortestPathMap.put(node, Double.POSITIVE_INFINITY);
        }

        for (EdgeWeighted edge : start.getEdges()) {
            shortestPathMap.put(edge.getDestination(), edge.getWeight());
            changedAt.put(edge.getDestination(), start);
        }

        start.visit();

        while (true) {
            NodeWeighted currentNode = closestReachableUnvisited(shortestPathMap, graph);
            if (currentNode == null) {
                return new LinkedList<>();
            }

            if (currentNode == end) {
                NodeWeighted child = end;

                solution.add(end);
                while (true) {
                    NodeWeighted parent = changedAt.get(child);
                    if (parent == null) {
                        break;
                    }

                    solution.add(parent);
                    child = parent;
                }

                return solution;
            }
            currentNode.visit();

            for (EdgeWeighted edge : currentNode.getEdges()) {
                if (edge.getDestination().isVisited())
                    continue;

                if (shortestPathMap.get(currentNode)
                        + edge.getWeight()
                        < shortestPathMap.get(edge.getDestination())) {
                    shortestPathMap.put(edge.getDestination(),
                                        shortestPathMap.get(currentNode) + edge.getWeight());
                    changedAt.put(edge.getDestination(), currentNode);
                }
            }
        }
    }

    private static NodeWeighted closestReachableUnvisited(HashMap<NodeWeighted, Double> shortestPathMap, GraphWeighted graph) {

        double shortestDistance = Double.POSITIVE_INFINITY;
        NodeWeighted closestReachableNode = null;
        for (NodeWeighted node : graph.getNodes()) {
            if (node.isVisited())
                continue;

            double currentDistance = shortestPathMap.get(node);
            if (currentDistance == Double.POSITIVE_INFINITY)
                continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachableNode = node;
            }
        }
        return closestReachableNode;
    }

    public static LinkedList<NodeWeighted> fordBellman(NodeWeighted start, NodeWeighted dest,  GraphWeighted graph){
        LinkedList<NodeWeighted> solution = new LinkedList<>();

        double[] dist = new double[graph.nodeCount()];
        NodeWeighted[] prev = new NodeWeighted[graph.nodeCount()];
        ArrayList<EdgeWeighted>[] adj = new ArrayList[graph.nodeCount()];

        for (NodeWeighted node : graph.getNodes()){
            adj[node.getN()] = new ArrayList<>();
            adj[node.getN()].addAll(node.getEdges());
        }

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, new NodeWeighted(0,"null"));
        dist[start.getN()] = 0;

        LinkedList<NodeWeighted> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            ArrayList<EdgeWeighted> edges = adj[queue.poll().getN()];
            for(EdgeWeighted edge : edges){
                int dis = edge.getDestination().getN();
                int src = edge.getSource().getN();
                if (edge.getDestination() == start) continue;;
                queue.add(edge.getDestination());
                if (dist[dis] > dist[src] + edge.getWeight()){
                    dist[dis] = dist[src] + edge.getWeight();
                    prev[dis] = edge.getSource();
                }
            }
        }

        int i = dest.getN();
        solution.add(dest);
        while(i != start.getN()){
            solution.add(prev[i]);
            i = prev[i].getN();
        }

        Collections.reverse(solution);
        return solution;
    }
}
