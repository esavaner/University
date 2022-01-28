package zad2;

import graph.BipartiteGraph;
import graph.FlowNetwork;

public class Zad21 {
    private static final int NUM_OF_TESTS = 100;
    public static void main(String[] args) {
        System.out.println("size - degree - matching - time");
        for(int k = 3; k <= 10; k++) {
            for(int m = 1; m <= k; m++) {
                int matching = 0;
                long time = 0;
                for (int j = 0; j < NUM_OF_TESTS; j++) {
                    BipartiteGraph bipartiteGraph = new BipartiteGraph(k, m);
                    FlowNetwork matchingFlowNetwork = new FlowNetwork(bipartiteGraph, 0, 1 + (1 << (k+1)));
                    var begin = System.nanoTime();
                    matchingFlowNetwork.countMaximumFlow();
                    matching += matchingFlowNetwork.getMaxFlow();
                    var end = System.nanoTime();
                    time += (end - begin);
                }
                System.out.println(k + " " + m + " " + (matching / NUM_OF_TESTS) + " " + (time / NUM_OF_TESTS));
            }
        }
    }
}