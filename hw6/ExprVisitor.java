/**
 CS 334 - HW 6: Programming Question 3

Answers:

a. When adding a new class to represent division expressions, no preexisitng classes would have to be changed since we only need to add division expression as another extension of Expr.  

b. n classes woould need to be changed to allow for graphical repreenation of parse treee since we would need to add a method to draw each extension of Expr.

c.

e.accept(printer)
left.accept(printer)
printer.visitNumber(3)
right.accept(printer)
printer.visitNumber(2)
v.visitSum(leftVal,rightVal)
 

d. see below!!

e. m subclasses would have to change in order to add functionality for the visitDiv call we would have in our new division class that now has to be implemented by each subclass of Visitor. One class would have to be added that extended Expr to create division funcionality. 

f. 0 subclasses would have to be changed. We only need to create 1 subclass of Visitor to implement how to draw each subclass of Expr. 

g. I would recomend using the standard design if theey forsaw more expansion in the type Expr's relative to future changes to the reprentation (Visitor claass) of this object hierarchy. This way, most of the future work being done will bee self-contained rather than require changing preexisting code too often.

h. I would reccomend the Visitor Design pattern when more changes to the represetations of Expr are  expected in the future relative to future extentions of Expr.


**/

/** Abstract class for all expressions */
abstract class Expr {
    abstract <T> T accept(Visitor<T> v);
}



class Number extends Expr {
    protected int n;

    public Number(int n) { this.n = n; }

    public <T> T accept(Visitor<T> v) {
	return v.visitNumber(this.n);
    }
}

class Subtract extends Expr {
    protected Expr left, right;

    public Subtract(Expr left, Expr right) {
	this.left = left;
	this.right = right;
    }

    public <T> T accept(Visitor<T> v) {
	return v.visitSubtract(left.accept(v), right.accept(v));
    }
}

class Times extends Expr {
    protected Expr left, right;

    public Times(Expr left, Expr right) {
	this.left = left;
	this.right = right;
    }

    public <T> T accept(Visitor<T> v) {
	return v.visitTimes(left.accept(v), right.accept(v));
    }
}


class Sum extends Expr {
    protected Expr left, right;

    public Sum(Expr left, Expr right) {
	this.left = left;
	this.right = right;
    }

    public <T> T accept(Visitor<T> v) {
	return v.visitSum(left.accept(v), right.accept(v));
    }
}


/** Abstract class for all visitors */
abstract class Visitor<T> {
    abstract T visitNumber(int n);
    abstract T visitSum(T left, T right);
    abstract T visitSubtract(T left, T right);
    abstract T visitTimes(T left, T right);
}

class Eval extends Visitor<Integer> {
    public Integer visitNumber(int  n) {
	return n;
    }

    public Integer visitSum(Integer  left, Integer right) {
	return (left + right);
    }

    public Integer visitTimes(Integer left, Integer right) {
	return (left * right);
    }

    public Integer visitSubtract(Integer left, Integer right) {
	return (left - right);
    }
 
}

/** Example visitor to convert an Expr to a String */
class ToString extends Visitor<String> {
    public String visitNumber(int n) { 
	return "" + n;
    }
    public String visitSum(String left, String right) {
	return "(" + left + " + " + right + ")";
    }
    public String visitSubtract(String left, String right) {
	return "(" + left + " - " + right + ")";
    }
    public String visitTimes(String left, String right) {
	return "(" + left + " * " + right + ")";
    }
}


class Compile extends Visitor<String> {
    public String visitNumber(int n) {
	return ""+n;
    }
    public String visitSum(String left, String right) {
	return "PUSH("+left+") PUSH(" + right + ") ADD";
    }
    public String visitSubtract(String left, String right) {
	return "PUSH("+left+") PUSH(" + right + ") SUB";
    }
    public String visitTimes(String left, String right) {
	return "PUSH("+left+") PUSH(" + right + ") MULT";
    }
    public String visitDiv(String left, String right) {
	return "PUSH("+left+") PUSH(" + right + ") DIV";
    }
}


public class ExprVisitor { 
    public static void main(String args[]) { 
	Expr e = new Sum(new Number(3), new Number(2)); 
        Eval printer = new Eval();
	Integer stringRep = e.accept(printer); 
	System.out.println(stringRep);
    }

}
