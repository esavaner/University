package zad1;

import graph.HyperCube;
import graph.FlowNetwork;

public class Zad11 {
    private static int NUM_OF_TESTS = 100;
    public static void main(String[] args) {
        //System.out.println(NUM_OF_TESTS + " tests");
        System.out.println("size - maxflow - path - time[ms]");
        for (int i = 1; i <= 16; i++) {
            if (i == 12) NUM_OF_TESTS = 20;
            if (i == 14) NUM_OF_TESTS = 10;
            int millis = 0;
            int paths = 0;
            int flow = 0;
            for (int j = 0; j < NUM_OF_TESTS; j++) {
                var begin = System.nanoTime();
                var hyperCube = new HyperCube(i);
                var flowNetwork = new FlowNetwork(hyperCube, 0, hyperCube.getVerticesCount()-1);
                flowNetwork.countMaximumFlow();
                var end = System.nanoTime();
                millis += (end - begin) / 1000000;
                paths += flowNetwork.getAugmentingPaths();
                flow += flowNetwork.getMaxFlow();
            }

            System.out.println(i + " " + (flow / NUM_OF_TESTS) + " " + (paths / NUM_OF_TESTS) + " " + (millis / NUM_OF_TESTS));
        }
    }
}