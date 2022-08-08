/*
 * Scala Expression Evaluation with Case Classes...
 */
object Expressions {

  /*
   * A sealed expression algebraic data type equivalent to the
   * following definition in ML:
   * <pre>
   * datatype Expr = Variable of Symbol
   *               | Constant of Double
   *               | Sum of Expr * Expr
   *               | Product of Expr * Expr
   *               | Power of Expr * Expr
   * </pre>
   */
  sealed abstract class Expr
  case class Variable(name: Symbol) extends Expr
  case class Constant(x: Double) extends Expr
  case class Sum(l: Expr, r: Expr) extends Expr
  case class Product(l: Expr, r: Expr) extends Expr
  case class Power(b: Expr, e: Expr) extends Expr
   
  /*
   * Derives an expression with respect to a variable.
   * @param e the expression to derive
   * @param s the symbol representing the variable
   * @return the derivated expression
   * @throws Exception if one exponent is not a constant
   */
  def derive(e: Expr, s: Symbol): Expr = e match {
    case Variable(v) => if (v == s) Constant(1.0) else Constant(0.0)
    case Constant(c) => Constant(0.0)
    case Sum(l, r) => Sum(derive(l, s), derive(r, s))
    case Product(l, r) => Sum(Product(l, derive(r,s)), Product(derive(l,s), r))
    case Power(b, Constant(y)) => Product(Constant(y), Power(b, Constant(y - 1)))
    case Power(b, _) => throw new Exception
  }
 
  /*
   * Evaluates an expression in a given environment.
   * @param e the expression to evaluate
   * @param env a mapping from variable symbols to values
   * @return the evaluated expression
   * @throws Exception if a symbol in the expression is not in env
   */
  def eval(e: Expr, env: Map[Symbol, Double]): Double = e match {
    case Variable(s) => env.getOrElse(s, throw new Exception)
    case Constant(d) => d
    case Sum(l, r) => eval(l, env) + eval(r, env)
    case Product(l, r) => eval(l, env) * eval(r, env)
    case Power(b, e) => scala.math.pow(eval(b, env), eval(e, env))
    case _ => 0.0
  }
 
  /*
   * Simplifies an expression. Simplification should be sound.
   * @param e the expression to simplify
   * @return the simplified expression
   */
  def simplify(e: Expr): Expr = e match {
    // Simplifying sums
    case Sum(Constant(0.0), r) => simplify(r)
    case Sum(l, Constant(0.0)) => simplify(l)

    // Simplifying products
    case Product(Constant(1.0), r) => simplify(r)
    case Product(l, Constant(1.0)) => simplify(l)
    case Product(Constant(0.0), r) => Constant(0.0)
    case Product(l, Constant(0.0)) => Constant(0.0)

    // Simplifying powers
    case Power(b, Constant(1.0)) => b
    case Power(b, Constant(0.0)) => Constant(1.0)

    case catchAll => catchAll  // The fall-through case
  }

  /*
   * Pretty prints an expression.
   * @param exp the expression to print
   */
  def print(exp: Expr): Unit = {
    def stringify(ex: Expr): String = ex match {
      case Constant(x) => x.toString
      case Variable(x) => x.toString.drop(1)
      case Sum(l, r) => stringify(l) + " + " + stringify(r)
      case Product(l @ Sum(_, _), r @ Sum(_, _)) => "(" + stringify(l) + ") * (" + stringify(r) + ")" 
      case Product(l @ Sum(_, _), r) => "(" + stringify(l) + ") * " + stringify(r)
      case Product(l, r @ Sum(_, _)) => stringify(l) + " * (" + stringify(r) + ")"
      case Product(l, r) => stringify(l) + " * " + stringify(r)
      case Power(b, e) => stringify(b) + "^" + stringify(e)
    }
  
    println(stringify(exp))
  }

  /*
   * An expression parser.
   */
  object Parser {
    import scala.util.parsing.combinator._
    import scala.util.parsing.combinator.lexical._
    import scala.util.parsing.combinator.syntactical._

    /*
     * Parses a string to an expression. If parsing fails, the error message
     * is printed on the standard output.
     * @param s the string to parse
     * @return an expression if the parsing succeeds
     */
    def parse(s: String): Option[Expr] = {
      import ExpressionParser._

      phrase(expr)(new lexical.Scanner(s)) match {
        case Success(result, _) => Some(result)
        case Failure(msg, _) => println("Parse error: " + msg); None
        case _ => None
      }
    }

    /*
     * Private implementation of the expression parser using the Scala
     * parser combinator library.
     * @see http://en.wikipedia.org/wiki/Parser_Combinator
     */
    private object ExpressionParser extends StandardTokenParsers {   
      lexical.delimiters ++= List("(", ")", "+", "*", "^", ".", "-")

      // expr := sum
      def expr: Parser[Expr] = sum
  
      // sum := product { ("+" | "-") product }
      def sum: Parser[Expr] =
        product ~ rep(("+" | "-") ~ product) ^^ {
          case x ~ xs => (x /: xs)((l, r) => r._1 match {
            case "+" => Sum(l, r._2)
            case "-" => Sum(l, Product(Constant(-1.0), r._2))
          })
        }
    
      // product := power { "*" power }
      def product: Parser[Expr] =
        power ~ rep("*" ~ power) ^^ {
          case x ~ xs => (x /: xs)((l, r) => Product(l, r._2))
        }
    
      // power := factor [ "^" factor ]
      def power: Parser[Expr] =
        factor ~ opt("^" ~ factor) ^^ {
          case f ~ None => f
          case f ~ Some(c) => Power(f, c._2)
        }

      // factor := "(" expr ")" | variable | constant 
      def factor: Parser[Expr] =
        "(" ~> expr <~ ")" | variable | constant 
    
      // variable := ident
      def variable: Parser[Variable] =
        ident ^^ (s => Variable(Symbol(s)))
    
      // constant := floatLit
      def constant: Parser[Constant] = floatLit
    
      // floatLit := [ "-" ] positiveFloat
      def floatLit: Parser[Constant] =
        opt("-") ~ positiveFloat ^^ {
          case Some(s) ~ Constant(n) => Constant(-n)
          case None ~ Constant(n) => Constant(n)
        }
    
      // positiveFloat := numericLit [ "." [ numericLit ] ]
      def positiveFloat: Parser[Constant] =
        numericLit ~ opt("." ~ opt(numericLit)) ^^ {
          case n ~ None => Constant(n.toInt)
          case n ~ Some(p ~ None) => Constant(n.toInt)
          case n ~ Some(p ~ Some(m)) => Constant((n + p + m).toDouble)
        }
    }
  }

  /*
   * An expression factory.
   */
  object Expr {
  
    /*
     * Creates an expression from a string. 
     * @param s the string to parse
     * @return the parsed expression or the constant 0 if the parsing fails
     */
    def apply(s: String): Expr =
      Parser.parse(s) match {
        case Some(e) => e
        case None => Constant(0)
      }
  }

  /*
   * Asserts that two values are equal. Prints an error message on the standard
   * output if the assertion fails.
   * @param expected the expected value
   * @param actual the actual value
   */
  def assertEquals(expected: => Any, actual: Any): Unit =
    if (actual != expected)
      println("Assertion failed: " + expected + " expected, got " + actual)

  /*
   * Runs all tests.
   */
  def main(args : Array[String]) : Unit = {

    /* derive() Tests */
    // if you do the full chain rule, you may get a different result for this first test --
    // that's fine; just update the test...
    println("DERIVE TESTS:")
    assertEquals(Expr("3 * x^2"), derive(Expr("x^3"), 'x))
    assertEquals(Expr("2 * x^1"), derive(Expr("x^2"), 'x))
    assertEquals(Expr("1.0"), derive(Expr("x"), 'x))
    println("ALL TESTS PASSED")
    println("-------------------------")

    /* eval() Tests */
    println("EVAL TESTS:")
    assertEquals(8, eval(Expr("x^3"), Map('x -> 2)))
    // error case where variable in the expression is not in the given environment
    try {
      eval(Expr("x^3"), Map('y -> 2))
    } catch {
      case e: Exception => None
    }
    assertEquals(160, eval(Expr("y * x^4"), Map('x -> 2, 'y -> 10)))
    println("ALL TESTS PASSED")
    println("-------------------------")

    /* simplify() Tests */
    println("SIMPLIFY TESTS:")
    assertEquals(Variable('x), simplify(Sum(Variable('x),Constant(0))))
    assertEquals(Constant(1.0), simplify(Power(Variable('x),Constant(0))))
    assertEquals(Variable('x), simplify(Power(Variable('x),Constant(1.0))))
    println("ALL TESTS PASSED")

    assertEquals(Expr("x"), simplify(Expr("x + 0")))
  }
}
