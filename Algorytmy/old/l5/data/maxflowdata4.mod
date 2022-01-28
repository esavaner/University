param n, integer, >= 1;

set V, default {0..n-1};

set E, within V cross V;

param capacity{(i,j) in E}, >= 0;

param source, symbolic, in V, default 0;

param sink, symbolic, in V, != source, default n-1;

var flow{(i,j) in E}, >= 0, <= capacity[i,j];

var maxFlow, >= 0;

s.t. condition_1{i in V: i<>source and i<>sink}:
   sum{(j,i) in E} flow[j,i] = sum{(i,j) in E} flow[i,j];
   
s.t. condition_2{i in V: i=source}:
   maxFlow = sum{(i,j) in E} flow[i,j];
   
s.t. condition_3{i in V: i=sink}:
   maxFlow = sum{(j,i) in E} flow[j,i] ;

maximize obj: maxFlow;

solve;

printf "Maximum flow: %s\n", maxFlow;

data;param n := 16;
param : E :   capacity :=
0 1 13
0 2 9
0 4 6
0 8 16
1 3 1
1 5 2
1 9 3
2 3 7
2 6 7
2 10 3
3 7 6
3 11 6
4 5 3
4 6 6
4 12 4
5 7 5
5 13 6
6 7 5
6 14 7
7 15 6
8 9 5
8 10 2
8 12 1
9 11 8
9 13 7
10 11 8
10 14 7
11 15 6
12 13 7
12 14 1
13 15 15
14 15 3
;
end;
