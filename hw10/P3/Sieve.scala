//
// HW 10 -- P2
//

import scala.actors.Actor;
import Actor._;

class Sieve(prime : Int) extends Actor {
  val primeval = prime;
  var check = false; //indicates whether followe actor has been created
  var done = false;
  var nextActor : Sieve = _;  //set nextActor to default value until a Sieve is actually created 
  def act() = {
    while (!done) {
      receive {
	case "Stop" if (check != false) => nextActor ! "Stop"; println(primeval); done = true;
	case "Stop" if (check == false) => println(primeval); done = true;
	case n : Int if (n % prime != 0 && !check) => check = true; nextActor = new Sieve(n);
	case n : Int if (n % prime != 0 && check) => nextActor ! n;
      }
    }

  }
  start();
}

object Sieve {
  def main(args : Array[String]) : Unit = {
    println("Hello! Give me a number!");
    var s = new Sieve(2);
    var n = Console.readInt(); //take in number from outside world
    println("Primes:");
    for (i <- 3 to n) {
      s ! i;
    }
    s ! "Stop";
  }
}
