(*
 * expr.sml
 * cs334
 *)

(* Magic constant to make datatypes print out fully *)
Control.Print.printDepth:= 100;
Control.Print.printLength:= 100;

datatype Expr =
    VarX
  | VarY
  | Sine     of Expr
  | Cosine   of Expr
  | Average  of Expr * Expr
  | Times    of Expr * Expr
  | Half     of Expr
  | Square  of Expr
  | SineCosineSine of Expr * Expr * Expr;





(* build functions:
     Use these helper functions to generate elements of the Expr
     datatype rather than using the constructors directly.  This
     provides a little more modularity in the design of your program
*)
fun buildX()             = VarX;
fun buildY()             = VarY;
fun buildSine(e)         = Sine(e);
fun buildCosine(e)       = Cosine(e);
fun buildAverage(e1,e2)  = Average(e1,e2);
fun buildTimes(e1,e2)    = Times(e1,e2);
fun buildHalf(e)         = Half(e);
fun buildSquare(e)      = Square(e);
fun buildSineCosineSine(e1,e2,e3) = SineCosineSine(e1,e2,e3);


(* exprToString : Expr -> string
   Complete this function to convert an Expr to a string
*)
fun exprToString (Times(e1, e2)) = exprToString(e1)^"*"^exprToString(e2)
  | exprToString (VarX) = "x"
  | exprToString (VarY) = "y"
  | exprToString (Sine(e)) = "sin(pi*"^exprToString(e)^")"
  | exprToString (Cosine(e)) = "cos(pi*"^exprToString(e)^")"
  | exprToString (Average(e1, e2)) = "(("^exprToString(e1)^") + ("^exprToString(e2)^"))/2.0"
  | exprToString (Half(e))  = "("^exprToString(e)^")/2.0"
  | exprToString (Square(e)) = "("^exprToString(e)^") * ("^exprToString(e)^")"
  | exprToString (SineCosineSine(e1,e2,e3)) = "sin(pi*"^exprToString(e1)^") * cos(pi*"^exprToString(e2)^") * sin(pi*"^exprToString(e3)^")";


(* eval : Expr -> real*real -> real
   Evaluator for expressions in x and y
 *)
fun eval e (x,y) =
    let
	fun evltr (Times(e1, e2)) = (evltr(e1)*evltr(e2))
	  | evltr (VarX) = x
	  | evltr (VarY) = y
	  | evltr (Sine(e)) = Math.sin(Math.pi*evltr(e))
	  | evltr (Cosine(e)) = Math.cos(Math.pi*evltr(e))
	  | evltr (Average(e1, e2)) = (evltr(e1) + evltr(e2))/2.0
	  | evltr (Half(e)) = evltr(e)/2.0
	  | evltr (Square(e)) = evltr(e)*evltr(e)
	  | evltr (SineCosineSine(e1,e2,e3)) = Math.sin(Math.pi*evltr(e1)) * Math.cos(Math.pi*evltr(e2)) * Math.sin(Math.pi*evltr(e3));
    in
	evltr(e)
    end;



val sampleExpr =
      buildCosine(buildSine(buildTimes(buildCosine(buildAverage(buildCosine(
      buildX()),buildTimes(buildCosine (buildCosine (buildAverage
      (buildTimes (buildY(),buildY()),buildCosine (buildX())))),
      buildCosine (buildTimes (buildSine (buildCosine
      (buildY())),buildAverage (buildSine (buildX()), buildTimes
      (buildX(),buildX()))))))),buildY())));

(************** Add Testing Code Here ***************)
eval sampleExpr (0.1, 0.1);
