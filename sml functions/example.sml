
(* double an integer *)
fun double (x) = x * x;

(* return the length of a list *)
fun listLength (nil) = 0
  | listLength (l::ls) = 1 + listLength ls
  ;
 
double (10);
listLength (1::[2,3,4]);