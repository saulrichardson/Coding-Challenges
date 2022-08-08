(*
 CS334 -- HW3
 basic
*) 

(* To make lists and datatypes print better. *)
Control.Print.printDepth := 100;
Control.Print.printLength := 100;


(* Code: *)

(* Function computes the product of two lists *)
fun zip ([], _) = []
  | zip (_, []) = [] 
  | zip (x::ls, y::js) = (x, y)::zip (ls, js);  


(* Function performs the inverse of zip *)
fun unzip [] = (nil, nil) 
  | unzip ((a, b)::rest) =
    let val (first, second) = unzip(rest)
    in
	(a::first, b::second)
    end;

(* Function zips three lists together *)
fun zip3 ([], _, _) = [] 
  | zip3 (_, [], _) = []
  | zip3 (_, _, []) = [] 
  | zip3 (a::az, b::bz, c::cz) = (a, b, c)::zip3 (az, bz, cz);


(* Test Cases *)
zip ([1,3,5,7,8], ["a", "b", "c", "de"]);
unzip [(1, "a"), (3, "b"), (5,"c"), (7,"de")];
zip3 ([1,3,5,7], ["a", "b", "c", "de"], [1, 2, 3, 4]);

(* 
Question Answer:

You can't write a function zip_any that takes any number of lists
becuase you there isn't a correct type for this function. Our type
system won't allow this. The type of a tuple depends on what it stores, 
e.g. a tuple of two ints is int * int. In order to write zip_any we'd
have to account for all types of tuples of any size k, but we don't know
what output type we're gonna have since k is unknown. 
*)


