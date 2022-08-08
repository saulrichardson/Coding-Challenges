(*
 * expr-sml
 * cs334
 *)

(* Magic constant to make datatypes print out fully *)
Control.Print.printDepth:= 100;
Control.Print.printLength:= 100;

(* Expr is now a function that takes a pair of reals
     and returns a real.
*)
type Expr = real * real -> real;

(* build functions:
     Use these helper functions to generate elements of the Expr
     type.  They are identical in signature to those in expr.sml.
     You will need to add additional functions for the expression
     forms that you added.
*)
fun buildX()                = fn(x,y) => x;
fun buildY()                = fn(x,y) => y;
fun buildSine(e)       = fn(x,y) => Math.sin(Math.pi * e(x,y));
fun buildCosine(e)     = fn(x,y) => Math.cos(Math.pi * e(x,y));
fun buildAverage(e1, e2)  = fn(x,y) => (e1(x,y) + e2(x,y))/2.0;
fun buildTimes(e1, e2):Expr    = fn(x,y) => e1(x,y) * e2(x,y);
fun buildHalf(e)         = fn(x,y) => e(x,y)/2.0;
fun buildSquare(e):Expr       = fn(x,y) => e(x,y) * e(x,y);
fun buildSineCosineSine(e1, e2, e3) = fn(x,y) => Math.sin(Math.pi*e1(x,y)) * Math.cos(Math.pi*e2(x,y)) * Math.sin(Math.pi*e3(x,y));


(* exprToString : Expr -> string
   Complete this function to convert an Expr to a string
*)
fun exprToString e = "unknown";

(* eval : Expr -> real*real -> real
   Evaluator for expressions in x and y
 *)
fun eval e (x,y) = e(x,y);

val sampleExpr =
       buildCosine(buildSine(buildTimes(buildCosine(buildAverage(buildCosine(
       buildX()),buildTimes(buildCosine (buildCosine (buildAverage
       (buildTimes (buildY(),buildY()),buildCosine (buildX())))),
       buildCosine (buildTimes (buildSine (buildCosine
       (buildY())),buildAverage (buildSine (buildX()), buildTimes
       (buildX(),buildX()))))))),buildY())));

(************** Add Testing Code Here ***************)
