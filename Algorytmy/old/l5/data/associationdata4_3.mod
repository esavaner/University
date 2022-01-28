param n, integer, >= 1; #

set V, default {0..n-1}; #

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

data;
param n := 34;
param : E :   capacity :=
0 1 1
0 2 1
0 3 1
0 4 1
0 5 1
0 6 1
0 7 1
0 8 1
0 9 1
0 10 1
0 11 1
0 12 1
0 13 1
0 14 1
0 15 1
0 16 1
1 18 1
1 29 1
1 31 1
2 25 1
2 21 1
2 32 1
3 21 1
3 30 1
3 22 1
4 22 1
4 21 1
4 24 1
5 22 1
5 21 1
5 28 1
6 27 1
6 29 1
6 19 1
7 18 1
7 28 1
7 23 1
8 29 1
8 30 1
8 19 1
9 31 1
9 19 1
9 29 1
10 17 1
10 20 1
10 19 1
11 18 1
11 32 1
11 24 1
12 17 1
12 23 1
12 22 1
13 25 1
13 18 1
13 20 1
14 27 1
14 25 1
14 31 1
15 32 1
15 18 1
15 24 1
16 24 1
16 29 1
16 18 1
17 33 1
18 33 1
19 33 1
20 33 1
21 33 1
22 33 1
23 33 1
24 33 1
25 33 1
26 33 1
27 33 1
28 33 1
29 33 1
30 33 1
31 33 1
32 33 1
;
end;
