(* 
   CS334 HW5

*)

Control.Print.printDepth := 100;
Control.Print.printLength := 100;

(*** a ***)
fun concatWords a = foldr (fn (j,k) => (j^k)) "" a;

concatWords nil;
concatWords ["Tkj","H","Ekj"];



(*** b ***)
fun words_length a = foldr (fn (j,k) => size(j) + k) 0 a;

words_length nil;
words_length ["Three", "Short", "Words"];


(*** c ***)
(*
No, you cannot. Suppose f is defined as the subtration opertaion and v and l as defined below. You will get different outputs depending on if you use foldr or foldl because subtraction is not communative or associative. This means that if we evaluate using foldl for the list l defined below, we will output a different answer (~500) compared if we evaluated using foldr (500).  
*)

fun f(a,b) = a - b;
val v = 0;
val l = [505, 5];

foldl f v l; (* evaluates to ~500 *)
foldr f v l; (* evaluates to 500 *)


(*** d ***)
fun count a b = foldl (fn (j,k) => if a = j then 1 + k else 0 + k) 0 b;

count "sheep" ["cow", "sheep", "sheep", "g"];
count 4 [1,2,3,444,4,4,4];


(*** e ***)
fun partition p l = foldr (fn (c, (ab,bc)) => (if c < p then (c::ab,bc) else (ab, c::bc))) (nil, nil) l;

partition 10 [1,4,55,2,44,55,22,1,3,3];

(*** f ***)

fun poly blist a = foldr (fn (j,k) => a*k + j) 0.0 blist;

val g = poly [1.0, 2.0];
g(3.0);
val g = poly [1.0, 2.0, 3.0];
g(2.0);
