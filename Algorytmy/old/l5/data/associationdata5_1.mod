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
param n := 66;
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
0 17 1
0 18 1
0 19 1
0 20 1
0 21 1
0 22 1
0 23 1
0 24 1
0 25 1
0 26 1
0 27 1
0 28 1
0 29 1
0 30 1
0 31 1
0 32 1
1 52 1
2 35 1
3 36 1
4 63 1
5 35 1
6 41 1
7 37 1
8 42 1
9 61 1
10 47 1
11 57 1
12 53 1
13 60 1
14 55 1
15 49 1
16 39 1
17 45 1
18 56 1
19 57 1
20 48 1
21 61 1
22 39 1
23 55 1
24 41 1
25 61 1
26 64 1
27 39 1
28 49 1
29 57 1
30 44 1
31 62 1
32 37 1
33 65 1
34 65 1
35 65 1
36 65 1
37 65 1
38 65 1
39 65 1
40 65 1
41 65 1
42 65 1
43 65 1
44 65 1
45 65 1
46 65 1
47 65 1
48 65 1
49 65 1
50 65 1
51 65 1
52 65 1
53 65 1
54 65 1
55 65 1
56 65 1
57 65 1
58 65 1
59 65 1
60 65 1
61 65 1
62 65 1
63 65 1
64 65 1
;
end;
