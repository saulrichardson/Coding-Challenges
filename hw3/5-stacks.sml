(*
 CS334 -- HW3
 stacks
*) 

(* To make lists and datatypes print better. *)
Control.Print.printDepth := 100;
Control.Print.printLength := 100;

(* Create the opcode datatype *)
datatype OpCode =
        PUSH of real
      | ADD
      | MULT
      | SUB
      | DIV
      | SWAP
      ;

(* Create the stack datatype *)
type Stack = real list;

(* Function evaluates the stack expression *)
fun eval (nil,a::st) = a
    | eval (PUSH(n)::ops,st) = eval (ops,n::st) (* Pushes to the top of the stack *)
    | eval (ADD::ops,a::(b::st)) = eval (ops,(a + b)::st) (* Adds the first two numns *)
    | eval (MULT::ops,a::(b::st)) = eval (ops,(a * b)::st) (* Multiplies the first two nums *)
    | eval (SUB::ops,a::(b::st)) = eval (ops,(b - a)::st) (* Subtracts the first two nums *)
    | eval (DIV::ops,a::(b::st)) = eval (ops,(b / a)::st) (* Divides the first two nums *)
    | eval (SWAP::ops,a::(b::st)) = eval (ops,b::(a::st)) (* Swaps the first two nums *)
    | eval (_,_) = 0.0
    ;

(* Test Cases *)
eval([SUB, ADD, MULT, SWAP, DIV],[5.0,4.0,3.0,1.0,2.0]); (* Should return 1.0 *)
eval([SUB, ADD, MULT, MULT, ADD, SWAP, DIV],[3.0,4.0,3.0,1.0,2.0,4.0,6.0]); (* Should return 2.0 *)