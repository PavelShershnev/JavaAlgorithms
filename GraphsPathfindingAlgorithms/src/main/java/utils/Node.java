package utils;

import java.util.Objects;

public class Node {
    int n;
    String name;
    private boolean visited;

    public Node(int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
    }

    public void visit() {
        visited = true;
    }

    public void unvisit() {
        visited = false;
    }

    public boolean isVisited(){
        return visited;
    }

    public String getName(){
        return name;
    }

    public int getN(){
        return n;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return n == node.n &&
                Objects.equals(name, node.name);
    }

    @Override
    public String toString () {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode () {
        return Objects.hash(n, name);
    }
}
