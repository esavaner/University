package zad2;

import java.io.IOException;

public class Zad22 {
    public static void main(String[] args) throws IOException {
        /*
        if (args.length != 4 && args.length != 5) {
            System.err.println("Usage: ./zad2 --size k --degree m --glpk");
            System.exit(1);
        }
        int k = Integer.parseInt(args[1]);
        int m = Integer.parseInt(args[3]);
        BipartiteGraph bipartiteGraph = new BipartiteGraph(k, m);
        if (args.length == 4) {
            MaxFlowModel maxFlowModel = new MaxFlowModel(bipartiteGraph);//
            maxFlowModel.write("zad2.dat");//
            FlowNetwork matchingFlowNetwork = new FlowNetwork(bipartiteGraph, 0, 1 + (1 << (k + 1)));
            var begin = System.nanoTime();
            matchingFlowNetwork.countMaximumFlow();
            var end = System.nanoTime();
            System.err.println("K: " + k + ", M: " + m);
            System.err.println("Time: " + (end - begin) / 1000000);
            System.out.println(matchingFlowNetwork.getMaxFlow());
        } else {
            MaxFlowModel maxFlowModel = new MaxFlowModel(bipartiteGraph);
            maxFlowModel.write("zad2.dat");
        }
        */
    }
}