/*
 * Scala Cows
 * CS 334.
 */

object Cows {
    def main(args : Array[String]) : Unit = {
      // reads in file and stores in Iterator type `lines`
      val lines = scala.io.Source.fromFile("cows.txt").getLines();
      // convert Iterator into List
      val data = lines.toList;

      // (A) prints out dataset
      for (cow <- data) println(cow);

      // (B)
      data.foreach(println(_));

      // (C) prints all cows containing "s" in their name
      for (cow <- data if cow.contains("s")) println(cow);

      // (D)
      for (cow <- data if cow.contains("s") if !cow.contains("h")) println(cow);

      // (E) prints out the milk production of cows containing "s" in their name
      def getMilkProd(cow:String): Double = {
        val last = cow.lastIndexOf(",");
        cow.substring(last+1).toDouble;
      }
      val cowMilk =
        for (cow <- data if cow.contains("s"))  yield getMilkProd(cow); // add this to cowMilk
      cowMilk.foreach(println(_));

      // (F)
      val cowObjects = data.map((cow : String) => new Cow(cow));
      for (cow <- cowObjects) println(cow);

      // (G)
      val fourGallons =
        for (cow <- cowObjects if cow.milk > 4.0)  yield cow; // add this to fourGallons
      fourGallons.foreach(println(_));

      // (H)
      val nameSorted = cowObjects.sortWith(_.name < _.name)
      nameSorted.foreach(println(_));
      // tally up the milk production for the whole herd
      println(cowObjects.foldLeft (0.0) ( (result,x) => result + x.milk ));

      // (I)
      println("Lowest milk production: " + cowObjects.minBy(_.milk));
      println("Highest milk production: " + cowObjects.maxBy(_.milk));
    }
 }

class Cow(s : String) {
  // splits into a String array of [id, name, milk]
  val arr = s.split(",");

  def id = arr(0).toInt;
  def name = arr(1);
  def milk = arr(2).toDouble;

  override def toString() = {
    "ID: %d, Name: %s, Milk Production: %f".format(id, name, milk);
  }
}

