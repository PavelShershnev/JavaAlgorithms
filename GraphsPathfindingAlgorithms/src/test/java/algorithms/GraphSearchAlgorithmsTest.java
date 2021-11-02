package algorithms;

import graphs.Graph;
import graphs.GraphWeighted;
import org.junit.jupiter.api.*;
import utils.Node;
import utils.NodeWeighted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class GraphSearchAlgorithmsTest {

    static Graph graph = null;
    static Node startNode = null;
    static GraphWeighted graphWeighted = null;
    static NodeWeighted sourceNode = null;
    static NodeWeighted destNode = null;
    static LinkedList<NodeWeighted> correctAnsw;
    double memoryBefore;

    @BeforeAll
    public static void init(){

        //init
        graph = new Graph(true);
        graphWeighted = new GraphWeighted(true);
        correctAnsw = new LinkedList<>();
        //Fill Graph
        Node a = new Node(0, "0");
        Node b = new Node(1, "1");
        Node c = new Node(2, "2");
        Node d = new Node(3, "3");
        Node e = new Node(4, "4");

        graph.addEdge(a,d);
        graph.addEdge(a,b);
        graph.addEdge(a,c);
        graph.addEdge(c,d);

        startNode = new Node(0,"0");
        //Fill GraphWeight
        NodeWeighted zero = new NodeWeighted(0, "0");
        NodeWeighted one = new NodeWeighted(1, "1");
        NodeWeighted two = new NodeWeighted(2, "2");
        NodeWeighted three = new NodeWeighted(3, "3");
        NodeWeighted four = new NodeWeighted(4, "4");
        NodeWeighted five = new NodeWeighted(5, "5");
        NodeWeighted six = new NodeWeighted(6, "6");

        graphWeighted.addEdge(zero, one, 8);
        graphWeighted.addEdge(zero, two, 11);
        graphWeighted.addEdge(one, three, 3);
        graphWeighted.addEdge(one, four, 8);
        graphWeighted.addEdge(one, two, 7);
        graphWeighted.addEdge(two, four, 9);
        graphWeighted.addEdge(three, four, 5);
        graphWeighted.addEdge(three, five, 2);
        graphWeighted.addEdge(four, six, 6);
        graphWeighted.addEdge(five, four, 1);
        graphWeighted.addEdge(five, six, 8);


        sourceNode = graphWeighted.getNode(0);
        destNode = graphWeighted.getNode(6);

        //init other params
        correctAnsw.add(zero);
        correctAnsw.add(one);
        correctAnsw.add(three);
        correctAnsw.add(five);
        correctAnsw.add(four);
        correctAnsw.add(six);

    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        String methodName = testInfo.getTestMethod().orElseThrow().getName();
        System.out.println("Method: " + methodName);
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        memoryBefore = (double)(runtime.totalMemory() - runtime.freeMemory());
    }

    @AfterEach
    public void printMemoryUsage (){
        Runtime runtime = Runtime.getRuntime();
        double memoryAfter = (double)(runtime.totalMemory() - runtime.freeMemory());
        System.out.printf("\nMemory used: %.2f KBytes", (memoryAfter-memoryBefore)/1024);
    }


    @Test
    void dijkstraShortestPath (){
        LinkedList<NodeWeighted> answ = GraphSearchAlgorithms.dijkstraShortestPath(sourceNode,
                                                                                   destNode,
                                                                                   graphWeighted);
        Collections.reverse(answ);
        Assertions.assertEquals(correctAnsw, answ);
    }

    @Test
    public void depthFirstSearchModified () {
        Assertions.assertNotEquals(new ArrayList<Node>(), GraphSearchAlgorithms.depthFirstSearchModified(startNode,graph));
    }

    @Test
    void breadthFirstSearch () {
        Assertions.assertNotEquals(new ArrayList<Node>(), GraphSearchAlgorithms.breadthFirstSearch(startNode, graph));
    }

    @Test
    void fordBellman(){
        Assertions.assertEquals(correctAnsw, GraphSearchAlgorithms.fordBellman(sourceNode,destNode, graphWeighted));
    }
}