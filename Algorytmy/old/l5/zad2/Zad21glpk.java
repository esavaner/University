package zad2;

import graph.BipartiteGraph;
import graph.Edge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Zad21glpk {
    public static void main(String[] args) throws IOException {
        for (int k = 1; k <= 10; k++) {
            for (int i = 1; i <= k; i++) {
                BipartiteGraph bpg = new BipartiteGraph(k, i);
                FileWriter fw = new FileWriter(new File("associationdata" + k + "_" + i + ".mod"));
                fw.write("param n, integer, >= 1; #\r\n" +
                        "\r\n" +
                        "set V, default {0..n-1}; #\r\n" +
                        "\r\n" +
                        "set E, within V cross V;\r\n" +
                        "\r\n" +
                        "param capacity{(i,j) in E}, >= 0;\r\n" +
                        "\r\n" +
                        "param source, symbolic, in V, default 0;\r\n" +
                        "\r\n" +
                        "param sink, symbolic, in V, != source, default n-1;\r\n" +
                        "\r\n" +
                        "var flow{(i,j) in E}, >= 0, <= capacity[i,j];\r\n" +
                        "\r\n" +
                        "var maxFlow, >= 0;\r\n" +
                        "\r\n" +
                        "s.t. condition_1{i in V: i<>source and i<>sink}:\r\n" +
                        "   sum{(j,i) in E} flow[j,i] = sum{(i,j) in E} flow[i,j];\r\n" +
                        "   \r\n" +
                        "s.t. condition_2{i in V: i=source}:\r\n" +
                        "   maxFlow = sum{(i,j) in E} flow[i,j];\r\n" +
                        "   \r\n" +
                        "s.t. condition_3{i in V: i=sink}:\r\n" +
                        "   maxFlow = sum{(j,i) in E} flow[j,i] ;\r\n" +
                        "\r\n" +
                        "maximize obj: maxFlow;\r\n" +
                        "\r\n" +
                        "solve;\r\n" +
                        "\r\n" +
                        "printf \"Maximum flow: %s\\n\", maxFlow;\r\n" +
                        "\r\n" +
                        "data;\n");
                //fw.write("param source = 0;\n");
                //fw.write("param sink = n;\n");
                fw.write("param n := " + bpg.getVerticesCount() + ";\n");
                fw.write("param : E :   capacity :=\n");
                for (int j = 0; j < bpg.getVerticesCount(); j++) {
                    for (Edge edge : bpg.getNeighbours(j)) {
                        fw.write((j) + " " + (edge.getEndVertex()) + " 1" + "\n");
                    }
                }
                fw.write(";\n");
                fw.write("end;\n");
                fw.close();
            }
        }
    }
}