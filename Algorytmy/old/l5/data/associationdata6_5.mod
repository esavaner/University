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
param n := 130;
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
0 33 1
0 34 1
0 35 1
0 36 1
0 37 1
0 38 1
0 39 1
0 40 1
0 41 1
0 42 1
0 43 1
0 44 1
0 45 1
0 46 1
0 47 1
0 48 1
0 49 1
0 50 1
0 51 1
0 52 1
0 53 1
0 54 1
0 55 1
0 56 1
0 57 1
0 58 1
0 59 1
0 60 1
0 61 1
0 62 1
0 63 1
0 64 1
1 75 1
1 95 1
1 67 1
1 90 1
1 72 1
2 78 1
2 106 1
2 120 1
2 75 1
2 125 1
3 82 1
3 67 1
3 80 1
3 88 1
3 71 1
4 85 1
4 74 1
4 78 1
4 72 1
4 114 1
5 118 1
5 81 1
5 109 1
5 88 1
5 91 1
6 93 1
6 128 1
6 89 1
6 121 1
6 81 1
7 78 1
7 112 1
7 110 1
7 126 1
7 99 1
8 112 1
8 110 1
8 108 1
8 93 1
8 109 1
9 123 1
9 84 1
9 80 1
9 118 1
9 105 1
10 93 1
10 86 1
10 128 1
10 126 1
10 119 1
11 94 1
11 68 1
11 75 1
11 109 1
11 122 1
12 77 1
12 111 1
12 98 1
12 122 1
12 97 1
13 89 1
13 85 1
13 84 1
13 126 1
13 74 1
14 67 1
14 117 1
14 109 1
14 72 1
14 128 1
15 77 1
15 99 1
15 120 1
15 117 1
15 100 1
16 93 1
16 87 1
16 110 1
16 91 1
16 89 1
17 91 1
17 79 1
17 102 1
17 99 1
17 106 1
18 74 1
18 104 1
18 90 1
18 67 1
18 100 1
19 77 1
19 108 1
19 95 1
19 113 1
19 117 1
20 66 1
20 71 1
20 115 1
20 121 1
20 118 1
21 112 1
21 80 1
21 127 1
21 113 1
21 78 1
22 125 1
22 85 1
22 72 1
22 110 1
22 86 1
23 91 1
23 87 1
23 120 1
23 108 1
23 119 1
24 86 1
24 83 1
24 117 1
24 100 1
24 97 1
25 95 1
25 67 1
25 112 1
25 87 1
25 115 1
26 65 1
26 109 1
26 97 1
26 87 1
26 93 1
27 71 1
27 112 1
27 95 1
27 102 1
27 85 1
28 111 1
28 121 1
28 126 1
28 70 1
28 117 1
29 123 1
29 93 1
29 92 1
29 98 1
29 110 1
30 108 1
30 99 1
30 90 1
30 73 1
30 110 1
31 70 1
31 108 1
31 84 1
31 124 1
31 107 1
32 101 1
32 68 1
32 106 1
32 91 1
32 116 1
33 78 1
33 92 1
33 95 1
33 106 1
33 103 1
34 122 1
34 67 1
34 69 1
34 103 1
34 80 1
35 66 1
35 102 1
35 65 1
35 97 1
35 79 1
36 79 1
36 102 1
36 116 1
36 81 1
36 76 1
37 69 1
37 103 1
37 73 1
37 88 1
37 77 1
38 93 1
38 89 1
38 90 1
38 124 1
38 76 1
39 106 1
39 87 1
39 98 1
39 95 1
39 81 1
40 100 1
40 101 1
40 77 1
40 115 1
40 113 1
41 125 1
41 70 1
41 118 1
41 79 1
41 74 1
42 82 1
42 98 1
42 91 1
42 72 1
42 103 1
43 89 1
43 74 1
43 128 1
43 116 1
43 127 1
44 120 1
44 112 1
44 94 1
44 121 1
44 90 1
45 85 1
45 89 1
45 73 1
45 118 1
45 123 1
46 93 1
46 106 1
46 103 1
46 123 1
46 84 1
47 104 1
47 75 1
47 78 1
47 66 1
47 106 1
48 76 1
48 95 1
48 119 1
48 126 1
48 86 1
49 71 1
49 88 1
49 109 1
49 97 1
49 90 1
50 65 1
50 115 1
50 126 1
50 123 1
50 107 1
51 87 1
51 117 1
51 82 1
51 94 1
51 85 1
52 91 1
52 104 1
52 124 1
52 75 1
52 98 1
53 89 1
53 90 1
53 78 1
53 97 1
53 106 1
54 116 1
54 69 1
54 89 1
54 88 1
54 67 1
55 70 1
55 128 1
55 94 1
55 115 1
55 125 1
56 119 1
56 114 1
56 72 1
56 83 1
56 121 1
57 75 1
57 72 1
57 83 1
57 96 1
57 89 1
58 77 1
58 89 1
58 121 1
58 78 1
58 69 1
59 71 1
59 67 1
59 73 1
59 119 1
59 110 1
60 98 1
60 115 1
60 107 1
60 106 1
60 65 1
61 77 1
61 120 1
61 94 1
61 111 1
61 107 1
62 69 1
62 121 1
62 124 1
62 83 1
62 99 1
63 119 1
63 106 1
63 72 1
63 86 1
63 95 1
64 71 1
64 100 1
64 87 1
64 123 1
64 88 1
65 129 1
66 129 1
67 129 1
68 129 1
69 129 1
70 129 1
71 129 1
72 129 1
73 129 1
74 129 1
75 129 1
76 129 1
77 129 1
78 129 1
79 129 1
80 129 1
81 129 1
82 129 1
83 129 1
84 129 1
85 129 1
86 129 1
87 129 1
88 129 1
89 129 1
90 129 1
91 129 1
92 129 1
93 129 1
94 129 1
95 129 1
96 129 1
97 129 1
98 129 1
99 129 1
100 129 1
101 129 1
102 129 1
103 129 1
104 129 1
105 129 1
106 129 1
107 129 1
108 129 1
109 129 1
110 129 1
111 129 1
112 129 1
113 129 1
114 129 1
115 129 1
116 129 1
117 129 1
118 129 1
119 129 1
120 129 1
121 129 1
122 129 1
123 129 1
124 129 1
125 129 1
126 129 1
127 129 1
128 129 1
;
end;
