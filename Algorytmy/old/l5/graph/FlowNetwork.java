package graph;

import graph.Edge;
import graph.Graph;
import queue.Queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding maximum flow using Edmonds-Karp algorithm
 */
public class FlowNetwork {

    private Graph graph;
    private int s;
    private int t;

    private int maxFlow = 0;
    private int augmentingPaths = 0;

    public FlowNetwork(Graph graph, int s, int t) {
        this.graph = graph;
        this.s = s;
        this.t = t;
    }

    public void countMaximumFlow() {
        //queue for BFS method
        Queue<Integer> Q = new Queue<Integer>(new Integer[graph.getVerticesCount()]);
        //array for predecessors in BFS method
        int[] p = new int[graph.getVerticesCount()];
        //array for residual capacities
        int[] CFP = new int[graph.getVerticesCount()];

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            for(Edge edge : graph.getNeighbours(i)) {
                boolean returnEdge = false;

                for (Edge neighbourEdge : graph.getNeighbours(edge.getEndVertex())) {
                    if (neighbourEdge.getEndVertex() == i) {
                        returnEdge = true;
                        break;
                    }
                }

                if (!returnEdge) {
                    graph.addEdge(edge.getEndVertex(), i, 0);
                }
            }
        }

        int u = 0;
        boolean pathExists = true;
        while(true) { //while augmenting path exists
            for (int i = 0; i < p.length; i++) {
                p[i] = -1;
            }
            p[s] = -2;
            CFP[s] = Integer.MAX_VALUE;
            Q.clear();
            Q.enqueue(s);
            while (!Q.isEmpty()) {
                pathExists = false;
                u = Q.dequeue();
                for(Edge edge : graph.getNeighbours(u)) {
                    int cp = edge.getCapacity() - edge.getFlow();
                    if (cp > 0 && p[edge.getEndVertex()] == -1) {
                        p[edge.getEndVertex()] = u;
                        CFP[edge.getEndVertex()] = (cp < CFP[u]) ? cp : CFP[u];
                        if (edge.getEndVertex() == t) {
                            pathExists = true;
                            augmentingPaths++;
                            break;
                        } else {
                            Q.enqueue(edge.getEndVertex());
                        }
                    }
                }
                if (pathExists) break;
            }
            if (!pathExists) break;
            maxFlow += CFP[t];

            int v = t;
            while (v != s) {
                u = p[v];
                for (Edge edge : graph.getNeighbours(u)) {
                    if (edge.getEndVertex() == v) {
                        edge.setFlow(edge.getFlow() + CFP[t]);
                        break;
                    }
                }
                for (Edge edge : graph.getNeighbours(v)) {
                    if (edge.getEndVertex() == u) {
                        edge.setFlow(edge.getFlow() - CFP[t]);
                        break;
                    }
                }
                v = u;
            }
        }
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public int getAugmentingPaths() {
        return augmentingPaths;
    }
}