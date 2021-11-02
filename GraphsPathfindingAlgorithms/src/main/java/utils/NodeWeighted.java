package utils;

import java.util.LinkedList;
import java.util.Objects;

public class NodeWeighted {
    private int n;
    private String name;
    private boolean visited;
    private LinkedList<EdgeWeighted> edges;

    public NodeWeighted (int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
    }

    public int getN () {
        return n;
    }

    public String getName () {
        return name;
    }

    public LinkedList<EdgeWeighted> getEdges () {
        return edges;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWeighted that = (NodeWeighted) o;
        return n == that.n &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode () {
        return Objects.hash(n, name);
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }

    public void unvisit() {
        visited = false;
    }


}
