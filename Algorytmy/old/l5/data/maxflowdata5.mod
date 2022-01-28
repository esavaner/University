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

data;param n := 32;
param : E :   capacity :=
0 1 27
0 2 20
0 4 13
0 8 25
0 16 17
1 3 9
1 5 4
1 9 5
1 17 16
2 3 12
2 6 4
2 10 14
2 18 12
3 7 6
3 11 8
3 19 5
4 5 15
4 6 14
4 12 14
4 20 7
5 7 4
5 13 5
5 21 2
6 7 2
6 14 8
6 22 7
7 15 9
7 23 9
8 9 1
8 10 6
8 12 15
8 24 16
9 11 1
9 13 7
9 25 8
10 11 6
10 14 5
10 26 5
11 15 8
11 27 11
12 13 7
12 14 3
12 28 2
13 15 15
13 29 15
14 15 16
14 30 8
15 31 10
16 17 14
16 18 1
16 20 11
16 24 1
17 19 2
17 21 3
17 25 4
18 19 8
18 22 8
18 26 2
19 23 3
19 27 6
20 21 8
20 22 3
20 28 7
21 23 3
21 29 11
22 23 16
22 30 12
23 31 3
24 25 8
24 26 2
24 28 2
25 27 8
25 29 5
26 27 1
26 30 13
27 31 20
28 29 12
28 30 15
29 31 17
30 31 8
;
end;
