package utils;

import algorithms.GraphSearchAlgorithms;
import graphs.Graph;
import graphs.GraphWeighted;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Threads(1)
@Timeout(time = 10)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time=10)
@Measurement(iterations =3, time =10)
public class Benchmark {

    TestDataGraph testDataGraph;
    TestDataGraphWeight testDataGraphWeight;

    @State(Scope.Thread)
    public static class TestDataGraph {
        static Graph testGraph = new Graph(true);
        static Node startNode;

        void init(){
            Node a = new Node(0, "0");
            Node b = new Node(1, "1");
            Node c = new Node(2, "2");
            Node d = new Node(3, "3");
            Node e = new Node(4, "4");

            testGraph.addEdge(a,d);
            testGraph.addEdge(a,b);
            testGraph.addEdge(a,c);
            testGraph.addEdge(c,d);
            startNode = new Node(0,"0");
        }
    }

    public static class TestDataGraphWeight {
        static GraphWeighted testGraphWeight = new GraphWeighted(true);
        static NodeWeighted sourceNode;
        static NodeWeighted destNode;

        void init(){
            NodeWeighted zero = new NodeWeighted(0, "0");
            NodeWeighted one = new NodeWeighted(1, "1");
            NodeWeighted two = new NodeWeighted(2, "2");
            NodeWeighted three = new NodeWeighted(3, "3");
            NodeWeighted four = new NodeWeighted(4, "4");
            NodeWeighted five = new NodeWeighted(5, "5");
            NodeWeighted six = new NodeWeighted(6, "6");

            testGraphWeight.addEdge(zero, one, 8);
            testGraphWeight.addEdge(zero, two, 11);
            testGraphWeight.addEdge(one, three, 3);
            testGraphWeight.addEdge(one, four, 8);
            testGraphWeight.addEdge(one, two, 7);
            testGraphWeight.addEdge(two, four, 9);
            testGraphWeight.addEdge(three, four, 5);
            testGraphWeight.addEdge(three, five, 2);
            testGraphWeight.addEdge(four, six, 6);
            testGraphWeight.addEdge(five, four, 1);
            testGraphWeight.addEdge(five, six, 8);


            sourceNode = testGraphWeight.getNode(0);
            destNode = testGraphWeight.getNode(6);
        }
    }


    public static void main (String[] args) throws IOException {
        Main.main(args);
    }

    @Setup(Level.Trial)
    public void initGraph(){
        testDataGraph = new TestDataGraph();
        testDataGraph.init();
    }

    @Setup(Level.Trial)
    public void initGraphWeitght(){
        testDataGraphWeight = new TestDataGraphWeight();
        testDataGraphWeight.init();
    }

    @TearDown(Level.Iteration)
    public void afterEach(){
        Runtime runtime =  Runtime.getRuntime();
        runtime.gc();
        System.out.println("TearDown");
        System.out.println("Memory:" +  (runtime.totalMemory() - runtime.freeMemory())/1024 + "KB");
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void measureDFS () {
        GraphSearchAlgorithms.depthFirstSearchModified(TestDataGraph.startNode, TestDataGraph.testGraph);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void measureBFS () {
        GraphSearchAlgorithms.breadthFirstSearch(TestDataGraph.startNode, TestDataGraph.testGraph);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void measureDijkstra(){
        GraphSearchAlgorithms.dijkstraShortestPath(TestDataGraphWeight.sourceNode,
                                                   TestDataGraphWeight.destNode,
                                                   TestDataGraphWeight.testGraphWeight);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void measureFordBellman(){
        GraphSearchAlgorithms.fordBellman(TestDataGraphWeight.sourceNode,
                                          TestDataGraphWeight.destNode,
                                          TestDataGraphWeight.testGraphWeight
                                          );
    }

}
