import java.io.FileInputStream;
import java.util.Scanner;
import scala.util.Random;

import scala.List;

/*
 * Abstract class the all expandable parts of
 * a grammar extend (Terminal, NonTerminal, Production, Definition).
 */
abstract class GrammarElement {
  
  /**
   * Expand the grammar element as part of a random 
   * derivation.  Use grammar to look up the definitions
   * of any non-terminals encountered during expansion.
   */
  def expand(grammar : Grammar) : String;
  
  /**
   * Return a string representation of this grammar element.
   * This is useful for debugging.  (Even though we inherit a
   * default version of toString() from the Object superclass, 
   * I include it as an abstract method here to ensure that 
   * all subclasses provide their own implementation.)
   */
  def toString() : String;	
  
}


/**
 * Represents a grammar as a map from non-terminal names (Strings) to
 * Defintions.
 */
class Grammar {
  var g = scala.collection.mutable.Map[String, Definition]()
  
  // add a new non-terminal, with the given definition
  def +=(nt : String, defn : Definition) = {
    if (g.contains(nt)) {
      RandomSentenceGenerator.fail("non-terminal already defined")
    }
    else {
      g(nt) = defn
    }
  }
  
  // look up a non-terminal, and return the definition, or null
  // if not def exists.
  def apply(nt : String) : Definition = {
    if (g.contains(nt)) {
      g(nt)
    } else {
      null
    }
  }
  
  // Expand the start symbol for the grammar.
  def expand() : String = {
    var st = apply("<start>")
    st.expand(this)
  }
  
  // return a String representation of this object.
  override def toString() : String = {
    var acu = ""
    for ((k, v) <- g) {
      acu += k + ": "
      acu += v + "\n"
    }
    acu
  }
}

class Definition extends GrammarElement {
  var l = scala.collection.mutable.MutableList[Production]()
  def +=(p:Production) {l += p}
  override def expand(grammar : Grammar) : String = {
    val r = Random
    l(r.nextInt(l.length)).expand(grammar)
  }
  override def toString() : String = {
    var aaa = ""
    for (dd <- l) {
      aaa += dd.toString() + " "
    }
    aaa = aaa + ";"
    aaa
  }
}

class Production extends GrammarElement {
  var l = scala.collection.mutable.MutableList[GrammarElement]()
  def +=(ge:GrammarElement)= {
    l += ge
  }
  override def expand(grammar : Grammar) : String = {
    var output = ""
    for(ll<-l) {
      output = output + ll.expand(grammar) 
    }
    output
  }  
  override def toString() : String = {
    var accc = ""
    for(aa <- l) {
      accc += aa.toString() + " "
    }
    accc = accc + "|"
    accc
  } 
}

class NonTerminal extends GrammarElement { 
  var nt = ""
  def +=(str:String) = {nt = str}
  override def expand(grammar : Grammar) : String = {
    if (grammar.apply(nt) != null) {
      grammar.apply(nt).expand(grammar)
    } else {RandomSentenceGenerator.fail("nonterminal not defined")}
  }
  override def toString() : String = { nt }
}

class Terminal extends GrammarElement {
  var t = ""
  def +=(str : String) = {t = str}
  override def expand(grammar : Grammar) : String = { t + " " }
  override def toString() : String = { t }
}


object RandomSentenceGenerator {
    /**
     * Read tokens up to the end of a production and return 
     * them as a Production.
     *
     * Parses "Production ::= [ Word ]*"
     * where word is any terminal/non-terminal.
     */
  protected def readProduction(in : Scanner) : Production = {
    
    val p = new Production();
    var acc = ""
    while (in.hasNext() && !(in.hasNext(";") || in.hasNext("\\|"))) {
      val word = in.next();
      if (word.startsWith("<")) {
	var n = new NonTerminal()
	n += word
	p += n
      }
      else {
	var t = new Terminal()
	t += word
	p += t
      }
      // word is next word in production (either a Non-Terminal or Terminal).
      
    }
    
    p;
  }
  
  /**
   * Read a group of productions and return them as a Definition.
   *
   * Parses "<Definition> ::= <Production> [ '|' <Production> ]* ';'" 
   */
  def readDefinition(in : Scanner) : Definition = {
    
    val d = new Definition();
    
    val production = readProduction(in);
    
    d += production
    // production is first production for definition
    
    while (in.hasNext("\\|")) {
      expect(in, "\\|");
      val production = readProduction(in);
      d += production
      // production is the next production for definition
      
    }
    expect(in, ";");
    
    d;  			// return the new production
  }
  
  /**
   * Repeatedly read non-terminal definitions and insert them into
   * the grammar.
   *
   * Parses "<Grammar> ::= [ <Non-Terminal> '=' <Definition> ';' ]*" 
   */
  protected def readGrammar(in : Scanner) : Grammar = {
    
    // the grammar for this generator
    val grammar = new Grammar();
    
    while (in.hasNext()) {
      val name = in.next();
      expect(in, "=");
      
      val defn = readDefinition(in);
      
      grammar += (name, defn)
      // defn is next definition to add to grammar
      
    }
    
    grammar;   // return the grammar
  }
  
  /**
   * A helper method than matches s to the next token returned from
   * the scanner.  If it matches, throw away the token and get ready
   * to read the next one.  If it doesn't match, generate an error.
   * 
   * Since s is used as a regular expression, be sure to escape any
   * special characters like |, which should become \\|
   */
  protected def expect(in : Scanner, s : String) = {
    if (!in.hasNext(s)) {
      println(in.next);
      RandomSentenceGenerator.fail("expected " + s);
    }
    in.next();  // skip s
  }
  
  
  /**
   * Helper method to abort gracefully when an error occurs.
   * <p>
   * Usage: RandomSentenceGenerator.fail("Error Message");
   */
  def fail(msg : String) = {
    throw new RuntimeException(msg);
  }
  
  /**
   * Create a random sentence generator and print out
   * three random productions.
   */
  def main(args: Array[String]) = {
    val grammar = this.readGrammar(new Scanner(scala.io.Source.stdin.mkString));

    println("Grammar is: \n" + grammar);
    println(grammar.expand() + "\n");
    println(grammar.expand() + "\n");
    println(grammar.expand() + "\n");
  }
}

