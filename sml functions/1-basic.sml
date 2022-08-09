(*
 CS334 -- HW3
 basic
*) 

(* To make lists and datatypes print better. *)
Control.Print.printDepth := 100;
Control.Print.printLength := 100;

(* Code: *)
Control.Print.printDepth := 100;
Control.Print.printLength := 100;

(* Function returns the sum of the squares of the number from 1 to input x *)
fun sumSquares (1) = 1
  | sumSquares (x) = x*x + sumSquares(x-1);

sumSquares(4);
sumSquares(5);

(* Function returns a list with y copies of item x *)
fun listDup (x,0) = [] 
  | listDup (x,y) = [x] @ listDup(x, y - 1);

(* Test cases *)
sumSquares(4);
sumSquares(5);
listDup("moo", 4);
listDup(1, 2);
listDup(listDup("cow", 2), 2);

(* 
Question Answer:

This means that the functions takes in a tuple
first containing a type 'a arg and then an int arg. The function
then outputs a list containing elements of type 'a. This is appropiate
for our function becuase the element we are passing could be of any type
and 'a is just a placeholder for that. Our second argument will always be
an int since we want to copy the element a certain number of times. The output
makes sense since the list returned should be the same type as the element we 
input, 'a. 
*)

