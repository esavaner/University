package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BipartiteGraph extends Graph {

    private int k; //2
    private int m;
    private int k2; //4

    public BipartiteGraph(int k, int m) {
        super((1 << (k+1)) + 2 ); //2 * 2 ^ k + 2
        this.k = k;
        this.m = m;
        this.k2 = (1 << k);
        initialize();
    }

    private List<Integer> createV2List() {
        List<Integer> result = new ArrayList<>();
        for (int i = k2 + 1; i <= 2*k2; i++) {
            result.add(i);
        }
        return result;
    }

    private void initialize() {
        var list = createV2List();
        for (int start = 1; start <= k2; start++) {
            int counter = 0;
            Collections.shuffle(list);
            for (int j = 0; j < m; j++) {
                addEdge(start, list.get(counter++), 1);
            }
        }
        for (int i = 0; i < k2; i++) {
            addEdge(0, 1 + i, 1);
            addEdge(1 + k2 + i, 1 + (2 * k2), 1);
        }
    }

}