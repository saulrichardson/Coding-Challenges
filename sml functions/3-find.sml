(*
 CS334 -- HW3
 basic
*)

Control.Print.printDepth := 100;
Control.Print.printLength := 100;

(* Function returns the first occurence of item in list *)
fun find (item, list) =
    let fun count (itemm, [], counter) = ~1
	  | count (itemm, a::ab, counter)  = if itemm = a then counter else count(item, ab, counter + 1)
    in
	count(item, list, 0)
    end;

(* Test cases *)
find(3, [1, 2, 3, 4, 5]);
find("cow", ["cow", "dog"]);
find("rabbit", ["cow", "dog"]);

