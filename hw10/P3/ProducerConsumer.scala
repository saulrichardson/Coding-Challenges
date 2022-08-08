import scala.actors.Actor;
import Actor._;

/*
 * A simple Actor that sends two messages to a Consumer
 */
class Producer(cons : Actor) extends Actor {
  def act = {
    println("Producer Starting")
    cons ! "Hi"
    cons ! 3
    println("Producer Done")
  }

  this.start();  // start running as soon as created
}


/*
 * Another Actor that recieves three messages of type
 * Int or String and then exits.
 */
class Consumer() extends Actor {
  def act : Unit = {
    println("Consumer Starting")
    for (x <- 1 to 2) {
      receive {
	case i : Int  => println("  Number " + i)
	case s : String => println("  String " + s)
      }	
    }
    println("Consumer Done")
  }

  this.start(); // start running as soon as created
}

/*
 * Create a Producer and Consumer and let them run.
 */
object ProducerConsumer {
  def main(args : Array[String]) : Unit = { 
    val cons = new Consumer()
    val prod = new Producer(cons)
    println("Main Done")
  }
}
