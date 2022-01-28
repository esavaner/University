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
1 461 1
1 316 1
1 404 1
2 511 1
2 456 1
2 467 1
3 426 1
3 460 1
3 398 1
4 307 1
4 305 1
4 355 1
5 420 1
5 463 1
5 267 1
6 392 1
6 301 1
6 364 1
7 480 1
7 325 1
7 277 1
8 340 1
8 410 1
8 362 1
9 459 1
9 293 1
9 292 1
10 466 1
10 298 1
10 330 1
11 420 1
11 345 1
11 259 1
12 385 1
12 297 1
12 436 1
13 382 1
13 440 1
13 335 1
14 492 1
14 274 1
14 448 1
15 377 1
15 498 1
15 441 1
16 512 1
16 380 1
16 511 1
17 491 1
17 267 1
17 494 1
18 455 1
18 379 1
18 267 1
19 454 1
19 350 1
19 311 1
20 350 1
20 419 1
20 495 1
21 332 1
21 439 1
21 505 1
22 416 1
22 274 1
22 439 1
23 376 1
23 362 1
23 495 1
24 503 1
24 368 1
24 381 1
25 371 1
25 295 1
25 428 1
26 414 1
26 381 1
26 415 1
27 261 1
27 278 1
27 263 1
28 382 1
28 435 1
28 476 1
29 502 1
29 388 1
29 488 1
30 335 1
30 510 1
30 429 1
31 491 1
31 277 1
31 350 1
32 307 1
32 292 1
32 375 1
33 357 1
33 475 1
33 281 1
34 367 1
34 327 1
34 360 1
35 263 1
35 493 1
35 501 1
36 298 1
36 442 1
36 444 1
37 293 1
37 265 1
37 279 1
38 395 1
38 291 1
38 489 1
39 446 1
39 302 1
39 418 1
40 381 1
40 293 1
40 442 1
41 512 1
41 268 1
41 355 1
42 480 1
42 491 1
42 331 1
43 421 1
43 361 1
43 413 1
44 290 1
44 375 1
44 462 1
45 461 1
45 420 1
45 468 1
46 394 1
46 297 1
46 397 1
47 304 1
47 491 1
47 307 1
48 349 1
48 285 1
48 360 1
49 325 1
49 342 1
49 298 1
50 342 1
50 257 1
50 301 1
51 342 1
51 427 1
51 482 1
52 443 1
52 391 1
52 388 1
53 421 1
53 302 1
53 457 1
54 458 1
54 463 1
54 457 1
55 287 1
55 296 1
55 449 1
56 505 1
56 339 1
56 483 1
57 302 1
57 393 1
57 292 1
58 465 1
58 371 1
58 433 1
59 409 1
59 478 1
59 373 1
60 421 1
60 385 1
60 412 1
61 461 1
61 311 1
61 374 1
62 397 1
62 415 1
62 385 1
63 329 1
63 473 1
63 380 1
64 368 1
64 339 1
64 290 1
65 399 1
65 406 1
65 263 1
66 312 1
66 419 1
66 406 1
67 260 1
67 394 1
67 499 1
68 305 1
68 290 1
68 320 1
69 450 1
69 290 1
69 420 1
70 433 1
70 437 1
70 493 1
71 462 1
71 310 1
71 379 1
72 401 1
72 407 1
72 402 1
73 307 1
73 263 1
73 403 1
74 461 1
74 502 1
74 329 1
75 401 1
75 441 1
75 363 1
76 473 1
76 309 1
76 434 1
77 313 1
77 432 1
77 303 1
78 453 1
78 316 1
78 306 1
79 278 1
79 433 1
79 435 1
80 463 1
80 340 1
80 274 1
81 280 1
81 370 1
81 408 1
82 476 1
82 263 1
82 300 1
83 306 1
83 441 1
83 475 1
84 326 1
84 436 1
84 338 1
85 429 1
85 459 1
85 406 1
86 423 1
86 272 1
86 282 1
87 412 1
87 413 1
87 326 1
88 492 1
88 339 1
88 449 1
89 315 1
89 496 1
89 368 1
90 430 1
90 263 1
90 433 1
91 507 1
91 495 1
91 275 1
92 372 1
92 416 1
92 448 1
93 316 1
93 397 1
93 344 1
94 501 1
94 355 1
94 464 1
95 447 1
95 458 1
95 377 1
96 309 1
96 318 1
96 323 1
97 492 1
97 433 1
97 380 1
98 358 1
98 332 1
98 348 1
99 264 1
99 303 1
99 442 1
100 356 1
100 436 1
100 385 1
101 352 1
101 339 1
101 401 1
102 263 1
102 283 1
102 457 1
103 279 1
103 457 1
103 311 1
104 372 1
104 294 1
104 310 1
105 389 1
105 273 1
105 509 1
106 303 1
106 351 1
106 405 1
107 505 1
107 270 1
107 462 1
108 411 1
108 279 1
108 479 1
109 509 1
109 413 1
109 503 1
110 431 1
110 464 1
110 435 1
111 363 1
111 291 1
111 497 1
112 508 1
112 454 1
112 377 1
113 410 1
113 304 1
113 321 1
114 340 1
114 389 1
114 447 1
115 351 1
115 388 1
115 512 1
116 278 1
116 401 1
116 411 1
117 399 1
117 338 1
117 312 1
118 374 1
118 268 1
118 265 1
119 390 1
119 269 1
119 476 1
120 257 1
120 440 1
120 296 1
121 401 1
121 257 1
121 483 1
122 286 1
122 305 1
122 259 1
123 463 1
123 338 1
123 500 1
124 335 1
124 328 1
124 427 1
125 387 1
125 302 1
125 360 1
126 441 1
126 444 1
126 423 1
127 260 1
127 346 1
127 470 1
128 433 1
128 410 1
128 351 1
129 449 1
129 437 1
129 415 1
130 360 1
130 440 1
130 295 1
131 323 1
131 488 1
131 319 1
132 369 1
132 508 1
132 260 1
133 346 1
133 258 1
133 398 1
134 288 1
134 466 1
134 282 1
135 389 1
135 507 1
135 460 1
136 300 1
136 358 1
136 338 1
137 338 1
137 460 1
137 322 1
138 472 1
138 387 1
138 428 1
139 395 1
139 291 1
139 420 1
140 443 1
140 435 1
140 344 1
141 447 1
141 489 1
141 291 1
142 461 1
142 259 1
142 388 1
143 438 1
143 342 1
143 476 1
144 412 1
144 486 1
144 465 1
145 417 1
145 350 1
145 460 1
146 498 1
146 377 1
146 314 1
147 444 1
147 296 1
147 464 1
148 358 1
148 382 1
148 407 1
149 338 1
149 342 1
149 336 1
150 472 1
150 291 1
150 344 1
151 398 1
151 286 1
151 257 1
152 293 1
152 384 1
152 306 1
153 429 1
153 325 1
153 347 1
154 298 1
154 358 1
154 478 1
155 456 1
155 299 1
155 408 1
156 268 1
156 300 1
156 485 1
157 279 1
157 275 1
157 333 1
158 444 1
158 384 1
158 483 1
159 366 1
159 298 1
159 265 1
160 462 1
160 367 1
160 430 1
161 298 1
161 331 1
161 461 1
162 371 1
162 375 1
162 468 1
163 376 1
163 472 1
163 412 1
164 282 1
164 422 1
164 334 1
165 442 1
165 270 1
165 299 1
166 345 1
166 372 1
166 424 1
167 342 1
167 484 1
167 340 1
168 478 1
168 264 1
168 400 1
169 493 1
169 499 1
169 451 1
170 392 1
170 336 1
170 371 1
171 395 1
171 482 1
171 480 1
172 296 1
172 367 1
172 448 1
173 358 1
173 371 1
173 319 1
174 434 1
174 499 1
174 341 1
175 478 1
175 489 1
175 496 1
176 417 1
176 507 1
176 369 1
177 384 1
177 349 1
177 396 1
178 367 1
178 468 1
178 352 1
179 494 1
179 407 1
179 448 1
180 509 1
180 487 1
180 342 1
181 432 1
181 497 1
181 461 1
182 360 1
182 370 1
182 444 1
183 289 1
183 369 1
183 463 1
184 420 1
184 270 1
184 499 1
185 271 1
185 390 1
185 428 1
186 296 1
186 510 1
186 365 1
187 351 1
187 395 1
187 324 1
188 481 1
188 314 1
188 462 1
189 406 1
189 303 1
189 337 1
190 318 1
190 500 1
190 392 1
191 394 1
191 383 1
191 416 1
192 496 1
192 460 1
192 424 1
193 455 1
193 350 1
193 466 1
194 357 1
194 268 1
194 470 1
195 476 1
195 263 1
195 510 1
196 353 1
196 377 1
196 381 1
197 425 1
197 288 1
197 450 1
198 439 1
198 361 1
198 502 1
199 449 1
199 295 1
199 343 1
200 321 1
200 383 1
200 280 1
201 287 1
201 273 1
201 499 1
202 445 1
202 291 1
202 348 1
203 473 1
203 511 1
203 370 1
204 470 1
204 443 1
204 273 1
205 349 1
205 347 1
205 403 1
206 276 1
206 477 1
206 388 1
207 357 1
207 481 1
207 305 1
208 294 1
208 364 1
208 326 1
209 314 1
209 402 1
209 260 1
210 345 1
210 408 1
210 495 1
211 326 1
211 322 1
211 270 1
212 455 1
212 487 1
212 428 1
213 312 1
213 287 1
213 363 1
214 365 1
214 402 1
214 504 1
215 342 1
215 282 1
215 289 1
216 282 1
216 433 1
216 364 1
217 277 1
217 275 1
217 323 1
218 272 1
218 370 1
218 440 1
219 444 1
219 372 1
219 485 1
220 420 1
220 286 1
220 419 1
221 410 1
221 354 1
221 413 1
222 508 1
222 276 1
222 343 1
223 420 1
223 479 1
223 409 1
224 436 1
224 393 1
224 409 1
225 398 1
225 328 1
225 305 1
226 287 1
226 370 1
226 401 1
227 391 1
227 307 1
227 495 1
228 469 1
228 375 1
228 505 1
229 284 1
229 360 1
229 348 1
230 491 1
230 396 1
230 337 1
231 383 1
231 453 1
231 257 1
232 439 1
232 346 1
232 339 1
233 321 1
233 386 1
233 393 1
234 320 1
234 293 1
234 475 1
235 476 1
235 338 1
235 433 1
236 345 1
236 350 1
236 320 1
237 404 1
237 430 1
237 298 1
238 274 1
238 262 1
238 271 1
239 301 1
239 348 1
239 285 1
240 469 1
240 336 1
240 303 1
241 481 1
241 361 1
241 269 1
242 414 1
242 383 1
242 461 1
243 296 1
243 259 1
243 306 1
244 292 1
244 337 1
244 389 1
245 488 1
245 397 1
245 492 1
246 493 1
246 495 1
246 475 1
247 261 1
247 290 1
247 281 1
248 410 1
248 259 1
248 455 1
249 455 1
249 342 1
249 332 1
250 443 1
250 307 1
250 426 1
251 394 1
251 321 1
251 413 1
252 303 1
252 450 1
252 345 1
253 512 1
253 505 1
253 448 1
254 493 1
254 372 1
254 423 1
255 337 1
255 390 1
255 473 1
256 387 1
256 476 1
256 489 1
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
