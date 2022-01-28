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
param n := 514;
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
0 65 1
0 66 1
0 67 1
0 68 1
0 69 1
0 70 1
0 71 1
0 72 1
0 73 1
0 74 1
0 75 1
0 76 1
0 77 1
0 78 1
0 79 1
0 80 1
0 81 1
0 82 1
0 83 1
0 84 1
0 85 1
0 86 1
0 87 1
0 88 1
0 89 1
0 90 1
0 91 1
0 92 1
0 93 1
0 94 1
0 95 1
0 96 1
0 97 1
0 98 1
0 99 1
0 100 1
0 101 1
0 102 1
0 103 1
0 104 1
0 105 1
0 106 1
0 107 1
0 108 1
0 109 1
0 110 1
0 111 1
0 112 1
0 113 1
0 114 1
0 115 1
0 116 1
0 117 1
0 118 1
0 119 1
0 120 1
0 121 1
0 122 1
0 123 1
0 124 1
0 125 1
0 126 1
0 127 1
0 128 1
0 129 1
0 130 1
0 131 1
0 132 1
0 133 1
0 134 1
0 135 1
0 136 1
0 137 1
0 138 1
0 139 1
0 140 1
0 141 1
0 142 1
0 143 1
0 144 1
0 145 1
0 146 1
0 147 1
0 148 1
0 149 1
0 150 1
0 151 1
0 152 1
0 153 1
0 154 1
0 155 1
0 156 1
0 157 1
0 158 1
0 159 1
0 160 1
0 161 1
0 162 1
0 163 1
0 164 1
0 165 1
0 166 1
0 167 1
0 168 1
0 169 1
0 170 1
0 171 1
0 172 1
0 173 1
0 174 1
0 175 1
0 176 1
0 177 1
0 178 1
0 179 1
0 180 1
0 181 1
0 182 1
0 183 1
0 184 1
0 185 1
0 186 1
0 187 1
0 188 1
0 189 1
0 190 1
0 191 1
0 192 1
0 193 1
0 194 1
0 195 1
0 196 1
0 197 1
0 198 1
0 199 1
0 200 1
0 201 1
0 202 1
0 203 1
0 204 1
0 205 1
0 206 1
0 207 1
0 208 1
0 209 1
0 210 1
0 211 1
0 212 1
0 213 1
0 214 1
0 215 1
0 216 1
0 217 1
0 218 1
0 219 1
0 220 1
0 221 1
0 222 1
0 223 1
0 224 1
0 225 1
0 226 1
0 227 1
0 228 1
0 229 1
0 230 1
0 231 1
0 232 1
0 233 1
0 234 1
0 235 1
0 236 1
0 237 1
0 238 1
0 239 1
0 240 1
0 241 1
0 242 1
0 243 1
0 244 1
0 245 1
0 246 1
0 247 1
0 248 1
0 249 1
0 250 1
0 251 1
0 252 1
0 253 1
0 254 1
0 255 1
0 256 1
1 260 1
2 484 1
3 393 1
4 417 1
5 473 1
6 346 1
7 289 1
8 312 1
9 439 1
10 394 1
11 496 1
12 398 1
13 274 1
14 370 1
15 359 1
16 431 1
17 443 1
18 484 1
19 322 1
20 400 1
21 345 1
22 462 1
23 465 1
24 499 1
25 408 1
26 499 1
27 433 1
28 314 1
29 340 1
30 421 1
31 322 1
32 411 1
33 432 1
34 384 1
35 428 1
36 331 1
37 336 1
38 308 1
39 259 1
40 266 1
41 382 1
42 484 1
43 355 1
44 485 1
45 407 1
46 277 1
47 371 1
48 380 1
49 391 1
50 384 1
51 481 1
52 426 1
53 478 1
54 436 1
55 296 1
56 258 1
57 368 1
58 329 1
59 266 1
60 268 1
61 419 1
62 410 1
63 315 1
64 419 1
65 503 1
66 305 1
67 323 1
68 333 1
69 419 1
70 369 1
71 477 1
72 452 1
73 457 1
74 352 1
75 432 1
76 462 1
77 325 1
78 277 1
79 455 1
80 387 1
81 505 1
82 440 1
83 347 1
84 402 1
85 443 1
86 272 1
87 265 1
88 422 1
89 280 1
90 328 1
91 314 1
92 424 1
93 296 1
94 423 1
95 488 1
96 444 1
97 380 1
98 410 1
99 350 1
100 277 1
101 483 1
102 479 1
103 393 1
104 499 1
105 302 1
106 496 1
107 293 1
108 459 1
109 460 1
110 477 1
111 397 1
112 320 1
113 290 1
114 440 1
115 390 1
116 498 1
117 358 1
118 458 1
119 458 1
120 407 1
121 378 1
122 300 1
123 363 1
124 500 1
125 371 1
126 358 1
127 390 1
128 473 1
129 445 1
130 485 1
131 502 1
132 324 1
133 492 1
134 394 1
135 317 1
136 309 1
137 365 1
138 464 1
139 390 1
140 478 1
141 442 1
142 509 1
143 454 1
144 381 1
145 299 1
146 259 1
147 424 1
148 326 1
149 341 1
150 380 1
151 298 1
152 481 1
153 279 1
154 377 1
155 460 1
156 284 1
157 315 1
158 309 1
159 382 1
160 286 1
161 468 1
162 319 1
163 356 1
164 387 1
165 393 1
166 281 1
167 386 1
168 480 1
169 320 1
170 397 1
171 338 1
172 395 1
173 306 1
174 426 1
175 484 1
176 487 1
177 319 1
178 397 1
179 281 1
180 404 1
181 390 1
182 493 1
183 460 1
184 463 1
185 493 1
186 487 1
187 268 1
188 302 1
189 336 1
190 474 1
191 428 1
192 500 1
193 421 1
194 467 1
195 410 1
196 375 1
197 445 1
198 489 1
199 435 1
200 378 1
201 484 1
202 430 1
203 328 1
204 311 1
205 335 1
206 380 1
207 364 1
208 450 1
209 318 1
210 329 1
211 453 1
212 328 1
213 402 1
214 429 1
215 349 1
216 377 1
217 433 1
218 329 1
219 385 1
220 496 1
221 319 1
222 258 1
223 413 1
224 420 1
225 311 1
226 267 1
227 301 1
228 260 1
229 497 1
230 460 1
231 457 1
232 397 1
233 498 1
234 328 1
235 378 1
236 392 1
237 285 1
238 450 1
239 289 1
240 428 1
241 451 1
242 459 1
243 280 1
244 400 1
245 415 1
246 395 1
247 272 1
248 483 1
249 388 1
250 360 1
251 475 1
252 504 1
253 258 1
254 506 1
255 264 1
256 340 1
257 513 1
258 513 1
259 513 1
260 513 1
261 513 1
262 513 1
263 513 1
264 513 1
265 513 1
266 513 1
267 513 1
268 513 1
269 513 1
270 513 1
271 513 1
272 513 1
273 513 1
274 513 1
275 513 1
276 513 1
277 513 1
278 513 1
279 513 1
280 513 1
281 513 1
282 513 1
283 513 1
284 513 1
285 513 1
286 513 1
287 513 1
288 513 1
289 513 1
290 513 1
291 513 1
292 513 1
293 513 1
294 513 1
295 513 1
296 513 1
297 513 1
298 513 1
299 513 1
300 513 1
301 513 1
302 513 1
303 513 1
304 513 1
305 513 1
306 513 1
307 513 1
308 513 1
309 513 1
310 513 1
311 513 1
312 513 1
313 513 1
314 513 1
315 513 1
316 513 1
317 513 1
318 513 1
319 513 1
320 513 1
321 513 1
322 513 1
323 513 1
324 513 1
325 513 1
326 513 1
327 513 1
328 513 1
329 513 1
330 513 1
331 513 1
332 513 1
333 513 1
334 513 1
335 513 1
336 513 1
337 513 1
338 513 1
339 513 1
340 513 1
341 513 1
342 513 1
343 513 1
344 513 1
345 513 1
346 513 1
347 513 1
348 513 1
349 513 1
350 513 1
351 513 1
352 513 1
353 513 1
354 513 1
355 513 1
356 513 1
357 513 1
358 513 1
359 513 1
360 513 1
361 513 1
362 513 1
363 513 1
364 513 1
365 513 1
366 513 1
367 513 1
368 513 1
369 513 1
370 513 1
371 513 1
372 513 1
373 513 1
374 513 1
375 513 1
376 513 1
377 513 1
378 513 1
379 513 1
380 513 1
381 513 1
382 513 1
383 513 1
384 513 1
385 513 1
386 513 1
387 513 1
388 513 1
389 513 1
390 513 1
391 513 1
392 513 1
393 513 1
394 513 1
395 513 1
396 513 1
397 513 1
398 513 1
399 513 1
400 513 1
401 513 1
402 513 1
403 513 1
404 513 1
405 513 1
406 513 1
407 513 1
408 513 1
409 513 1
410 513 1
411 513 1
412 513 1
413 513 1
414 513 1
415 513 1
416 513 1
417 513 1
418 513 1
419 513 1
420 513 1
421 513 1
422 513 1
423 513 1
424 513 1
425 513 1
426 513 1
427 513 1
428 513 1
429 513 1
430 513 1
431 513 1
432 513 1
433 513 1
434 513 1
435 513 1
436 513 1
437 513 1
438 513 1
439 513 1
440 513 1
441 513 1
442 513 1
443 513 1
444 513 1
445 513 1
446 513 1
447 513 1
448 513 1
449 513 1
450 513 1
451 513 1
452 513 1
453 513 1
454 513 1
455 513 1
456 513 1
457 513 1
458 513 1
459 513 1
460 513 1
461 513 1
462 513 1
463 513 1
464 513 1
465 513 1
466 513 1
467 513 1
468 513 1
469 513 1
470 513 1
471 513 1
472 513 1
473 513 1
474 513 1
475 513 1
476 513 1
477 513 1
478 513 1
479 513 1
480 513 1
481 513 1
482 513 1
483 513 1
484 513 1
485 513 1
486 513 1
487 513 1
488 513 1
489 513 1
490 513 1
491 513 1
492 513 1
493 513 1
494 513 1
495 513 1
496 513 1
497 513 1
498 513 1
499 513 1
500 513 1
501 513 1
502 513 1
503 513 1
504 513 1
505 513 1
506 513 1
507 513 1
508 513 1
509 513 1
510 513 1
511 513 1
512 513 1
;
end;