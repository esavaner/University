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
1 35 1
1 37 1
2 37 1
2 42 1
3 52 1
3 64 1
4 50 1
4 55 1
5 39 1
5 40 1
6 35 1
6 47 1
7 57 1
7 45 1
8 57 1
8 44 1
9 63 1
9 37 1
10 37 1
10 58 1
11 40 1
11 33 1
12 59 1
12 34 1
13 33 1
13 37 1
14 43 1
14 42 1
15 38 1
15 53 1
16 38 1
16 57 1
17 54 1
17 59 1
18 52 1
18 60 1
19 62 1
19 60 1
20 42 1
20 64 1
21 58 1
21 42 1
22 40 1
22 34 1
23 49 1
23 46 1
24 48 1
24 39 1
25 53 1
25 58 1
26 63 1
26 38 1
27 57 1
27 39 1
28 53 1
28 45 1
29 58 1
29 47 1
30 41 1
30 60 1
31 33 1
31 59 1
32 47 1
32 40 1
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
