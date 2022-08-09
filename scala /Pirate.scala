/*
 * Scala Pirate Translation
 * CS 334.
 */

import scala.collection.JavaConverters._
import java.util.StringTokenizer;
import scala.util.control.Breaks._

class Translator {
  var map = Map[String, String]();

  // Add the given translation from an english word to a pirate word
  def += (k : String, v : String) : Unit = { map += (k -> v); }
 
  // Look up the given english word.  If it is in the dictionary,
  //    return the pirate equivalent.  Otherwise, just return english.
  def apply(k : String) = {
    map.get(k) match {
      case None => k
      case Some(res) => res
    };
  }
    
  // Print the dictionary to stdout
  override def toString() = { map.mkString; }
}


object Pirate {

  // Helper method to build up the English-to-Pirate Translator
  def buildDict(translator: Translator, lines:Iterator[String], punctation: String): Unit = {
    // Iterate over each line of given String iterator
    for (line <- lines) {
      // create iterator for all words on a line when broken apart into tokens
      val words = for (s <- new StringTokenizer(line, punctation, true).asScala) yield s.toString();

      // extract english words and their mapped pirate value to build up translator
      val key = words.next();
      words.next();   // skips over the ":"
      // build up the value (Pirate mapping)
      var value = "";
      while (words.hasNext) {
        value += words.next();
      }
      // add English phrase as key and its Pirate translation as value
      translator += (key, value);
    }
  }
  
  def main(args: Array[String]) {
    // PART 1: BUILD DICT
    val translator = new Translator();
    val lines = scala.io.Source.fromFile("pirate.txt").getLines();
    val punctation = "=+!@#$%^&*():_-|\"\\'<>,.?/[]{}`~ \t\n";
    buildDict(translator,lines, punctation);
     // println(translator);

    // PART 2: READ IN LINES & TRANSLATE
    var total = ""
    for(line <- io.Source.stdin.getLines) {
      // create iterator for all words on a line of input, when broken apart into tokens
      val words = for (s <- new StringTokenizer(line + "\n", punctation, true).asScala) yield s.toString();
      while (words.hasNext) {
	      total += translator.apply(words.next())
      }
    }
    println(total);
  }
}

