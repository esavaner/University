package zad1;

import graph.Edge;
import graph.HyperCube;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Zad11glpk {
    public static void main(String[] args) throws IOException {
        for (int k = 1; k <= 16; k++) {
            HyperCube hyperCube = new HyperCube(k);
            FileWriter fw = new FileWriter(new File("maxflowdata" + k + ".mod"));
            fw.write("param n, integer, >= 1;\r\n" +
                    "\r\n" +
                    "set V, default {0..n-1};\r\n" +
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
                    "data;");
            fw.write("param n := " + hyperCube.getVerticesCount() + ";\n");
            fw.write("param : E :   capacity :=\n");
            for (int i = 0; i < hyperCube.getVerticesCount(); i++) {
                for (Edge edge : hyperCube.getNeighbours(i)) {
                    fw.write((i) + " " + (edge.getEndVertex()) + " " + (edge.getCapacity()) + "\n");
                }
            }
            fw.write(";\n");
            fw.write("end;\n");
            fw.close();
        }
    }
}