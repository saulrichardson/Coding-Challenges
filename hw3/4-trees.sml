(*
 CS334 -- HW3
 trees
*) 

(* To make lists and datatypes print better. *)
Control.Print.printDepth := 100;
Control.Print.printLength := 100;


(* Code: *)
datatype IntTree = LEAF of int | NODE of (IntTree * IntTree);

fun sum (LEAF(x)) = x 
  | sum (NODE(y,z)) = sum(y) + sum(z);

sum(LEAF 3);
sum(NODE(LEAF 2, LEAF 3));
sum(NODE(LEAF 2, NODE(LEAF 1, LEAF 1)));;


fun height (LEAF(a)) = 1 
  | height (NODE(b,c)) = if height(b) > height(c) then 1 + height(b) else 1 + height(c);

height(LEAF 3);
height(NODE(LEAF 2,LEAF 3));
height(NODE(LEAF 2, NODE(LEAF 1, LEAF 1)));

fun balanced (LEAF(a)) = true 
  | balanced (NODE(b, c)) = if abs(height(b) - height(c)) <= 1 andalso balanced(b) andalso balanced(c) then true else false;

balanced(LEAF 3);
balanced(NODE(LEAF 2, LEAF 3));
balanced(NODE(LEAF 2, NODE(LEAF 3, NODE(LEAF 1, LEAF 1))));


(* 
Question Answer:

The height definition is unoptimal becuase you have to recalculate
the height of a subtree when it's already been calculated. A more efficient
implementation might be to add an argument to the data constructor of node
that stores the height of the node. When a node is initialized, the height
is initialized to zero (we know that no node or leaf can have a height of zero).
When we pass a node to height, we first check if height is equal to zero. If it
is, then we calculate the height of the node using our current definition and
then change the value of the node's height in the datatype. If the height stored
is greater than zero, then we've already calculated it and we can just return
the value stored. This will prevent unnecesary traversals.
 *)

