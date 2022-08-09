 (*
 CS334 - HW 6: Programming Question 2
*)

(*** a ***)

exception DotProd;

fun dotprod xs ys =
    let fun hello (x::xx, y::yy, acc) = hello (xx, yy, x*y + acc)
	  | hello ([], [], acc) = acc 
	  | hello (e,d,acc) = raise DotProd 
    in
	hello(xs,ys, 0)
    end;


dotprod [1,2,3] [~1,5,3];
dotprod [~1,3,9] [0,0,11];
dotprod [] [];
(*dotprod [1,2,3] [4,5];*)


(*** b ***)

fun fast_fib n =
    let fun yipyip(0,oo,bb)= oo
      | yipyip(nn,oo,bb)= yipyip(nn-1,bb, bb+oo)
    in
	yipyip(n,0,1)
    end;


fast_fib 0;
fast_fib 1;
fast_fib 5;
fast_fib 10;
