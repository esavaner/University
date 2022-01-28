package graph;

import java.util.Random;

public class HyperCube extends Graph {

    private int d;

    public HyperCube(int d) {
        super(1 << d);
        this.d = d;

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 1; j < verticesCount; j *= 2) {
                if ((i&j) == 0) {
                    int end = i + j;
                    addEdge(i, end, findCapacity(i, end));
                }
            }
        }
    }

    private int findCapacity(int u, int v) {
        int uH = Integer.bitCount(u);
        int vH = Integer.bitCount(v);
        int uZ = d - uH;
        int vZ = d - vH;

        int max = maxValue(uH, vH, uZ, vZ);
        return (new Random().nextInt((1 << max)) + 1);
    }

    private int maxValue(int... numbers) {
        int max = Integer.MIN_VALUE;
        for (int n : numbers) {
            if (n > max) max = n;
        }
        return max;
    }
}