
/** This trait defines the AbstractSet type. Classes that inherit
 *  from AbstractSet must implement at least the following methods:
 *  contains, add and remove
 
 *  This trait defines two methods: addAll and removeAll
 */
trait AbstractSet[T]  {
  def contains(n: T): Boolean;
  def add(n: T): AbstractSet[T];
  def remove(n: T): AbstractSet[T];
  
  def addAll(lst: Iterable[T]): AbstractSet[T] =
      lst.foldLeft(this)((s, elem) => s.add(elem));
  def removeAll(lst: Iterable[T]): AbstractSet[T] =
    lst.foldLeft(this)((s, elem) => s.remove(elem));
}

/** This is the MutableSet class.
 *
 *  The add and remove methods should modify the instance of the
 *  set on which they are called.
 */
class MutableSet[T] extends AbstractSet[T] {
  var s = scala.collection.mutable.MutableList[T]()
  def contains(n: T): Boolean = s.contains(n); // change this method
  
  def add(n: T): AbstractSet[T] = {
    //maintain set property by not adding something already present
    if (this.contains(n)) {return this}
    else {
      s += n
      this
    }
  }
  
  def remove(n: T): AbstractSet[T] = {
    // change this method
    s = s.filter(_ != n)
    this;
  }
}

/** This is the ImmutableSet class.
 *
 *  The add and remove methods should NOT modify the instance of
 *  the set on which they are called.
 */
class ImmutableSet[T] private (val a : List[T]) extends AbstractSet[T] {
  def contains(n: T): Boolean = a.contains(n); // change this method
  def this() = this(Nil)
  def add(n: T): AbstractSet[T] = {
    //maintain set property by not adding something already present
    if (this.contains(n)) {this} 
    else{new ImmutableSet(a :+ n)}
  }
  
  def remove(n: T): AbstractSet[T] = {
    // change this method
    new ImmutableSet(a.filter(_ != n))
  }
}

trait LockingSet[T] extends AbstractSet[T] {
  abstract override def add(n: T): AbstractSet[T] =
    synchronized {super.add(n)}
  abstract override def remove(n: T): AbstractSet[T] =
    synchronized { super.remove(n) }
  abstract override def contains(n: T): Boolean =
    synchronized { super.contains(n) }
}

trait LoggingSet[T] extends AbstractSet[T] { 
  abstract override def add(n: T): AbstractSet[T] = {
    println("add is being called") 
    super.add(n)
  }
  abstract override def remove(n: T) : AbstractSet[T] = {
    println("remove is being called")
    super.remove(n)
  }
  abstract override def contains(n: T) : Boolean = {
    println("contains is being called")
    super.contains(n)
  }
}

/***************************************************************
 *************** BEGIN UNIT TESTING LIBRARY CODE ***************
 ***************************************************************
 *  The following code defines a simple unit testing library for
 *  MutableSet and ImmutableSet. It defines a simple "domain-
 *  specific language" for testing sets of integers.
 *
 *  You need not read or modify this code.
 */

/** The Numbers class represents a range of numbers.
 *
 *  It defines some convenient utility methods to test
 *  sets and see whether they have these numbers or not.
 */
class NumberRange(nums: Iterable[Int]) extends Iterable[Int] {
  def containsNoneFrom_:(s: AbstractSet[Int]): Boolean = {
    !nums.exists(p => s.contains(p));
  }
  
  def containsAllFrom_:(s: AbstractSet[Int]): Boolean = {
    nums.forall(p => s.contains(p));
  }
  
  def iterator = nums.iterator
}


object Sets {
  
  /** Takes an arbitrary number of tests and prints the result of
   *  the tests. A big "FAILED" message should be printed if any
   *  test fails.
   */
  def assert(tests: (String, Boolean)*) = {
    tests.foreach { 
      test => {
	val msg = test._1;
	val pass = test._2;
	
	if (pass) {
	  println("Passed: " + msg);
	} else {
	  println("FAILED: " + msg);
	}
      }
    }
  }
  
  
  
  // ***************************************************************
  // **************** END UNIT TESTING LIBRARY CODE ****************
  // ***************************************************************
    
   
  /** What follows are the unit tests for MutableSet and ImmutableSet.
   *  This code uses the "domain-specific language" defined in the
   *  unit testing library above.
   */
  
  def main(args : Array[String]) = {
    
    /** These values define ranges of odd numbers, even numbers,
     *  and all numbers.
     */
    val allNumbers = new NumberRange(1 to 10)
    val evenNumbers = new NumberRange((1 to 10).filter(_ % 2 == 0))
    val oddNumbers = new NumberRange((1 to 10).filter(_ % 2 != 0))
    
    val m1 = new MutableSet[Int];
    val m2 = new MutableSet[Int];
    
    assert (
      "m1 contains no numbers" -> (m1 containsNoneFrom_: allNumbers),
      "m2 contains no numbers" -> (m2 containsNoneFrom_: allNumbers)
    )
    
    val m3 = m1 addAll allNumbers
    
    assert (
      "m1 contains all numbers" -> (m1 containsAllFrom_: allNumbers),
      "m2 still contains no numbers" -> (m2 containsNoneFrom_: allNumbers),
      "m3 contains all numbers" -> (m3 containsAllFrom_: allNumbers)
    )
    
    m3 addAll evenNumbers
    m3 removeAll evenNumbers
    
    assert (
      "m1 contains no even numbers" -> (m1 containsNoneFrom_: evenNumbers),
      "m1 contains all odd numbers" -> (m1 containsAllFrom_: oddNumbers),
      "m3 contains no even numbers" -> (m3 containsNoneFrom_: evenNumbers),
      "m3 contains all odd numbers" -> (m3 containsAllFrom_: oddNumbers)
    )
    
    m2 addAll evenNumbers
    
    assert (
      "m2 contains all even numbers" -> (m2 containsAllFrom_: evenNumbers),
      "m2 contains no odd numbers" -> (m2 containsNoneFrom_: oddNumbers)
    )
    
    val i1 = new ImmutableSet[Int];
    val i2 = new ImmutableSet[Int];
    
    assert (
      "i1 contains no numbers" -> (i1 containsNoneFrom_: allNumbers),
      "i2 contains no numbers" -> (i2 containsNoneFrom_: allNumbers)
    )
    
    val i3 = i1 addAll allNumbers
    
    assert (
      "i1 still contains no numbers" -> (i1 containsNoneFrom_: allNumbers),
      "i3 contains all numbers" -> (i3 containsAllFrom_: allNumbers)
    )
    
    val _i3 = i3 addAll evenNumbers
    val i4 = i3 removeAll evenNumbers
    val _i4 = _i3 removeAll evenNumbers
    
    assert (
      "i3 still contains all numbers" -> (i3 containsAllFrom_: allNumbers),
      "i4 contains no even numbers" -> (i4 containsNoneFrom_: evenNumbers),
      "i4 contains all odd numbers" -> (i4 containsAllFrom_: oddNumbers),
      "_i4 contains no even numbers" -> (_i4 containsNoneFrom_: evenNumbers),
      "_i4 contains all odd numbers" -> (_i4 containsAllFrom_: oddNumbers)
    )
    
    val i5 = i2 addAll evenNumbers
    
    assert (
      "i2 still contains no numbers" -> (i2 containsNoneFrom_: allNumbers),
      "i5 contains all even numbers" -> (i5 containsAllFrom_: evenNumbers),
      "i5 contains no odd numbers" -> (i5 containsNoneFrom_: oddNumbers)
    )
    
    // add code to test logging here.
    println()
    println("logging tests")
    val f = new MutableSet[Int] with LoggingSet[Int] with LockingSet[Int]
    val r = new MutableSet[Int] with LockingSet[Int] with LoggingSet[Int]
    f.add(1)
    r.add(1)
    println()
  }
}
/** ANSWERS:
 * d.
 * The end result will not have a difference bettween the two. Performing the same operation on either
 * instances will result in the same changes to the set. But, there is a difference in that in ‘MutableSet
 * with LoggingSet with LockingSet’, the lock will be
 * acquired before a line is printed. In ‘MutableSet with LockingSet with LoggingSet’, a line will be
 * printed before the lock is acquired. So, when comparing the two instances, the order in which the
 * lock is acquired or the message printed will differ. But, the end result is the same. 
 *
 * e.
 * It doesn't make sense to mix the locking set trait into an ImmutableSet becuase the methods of
 * ImmutableSets never alter the instance on which they are called. So, we don't have to make an
 * instance of an ImmutableSet thread-safe, since we are never changing the actual data stored in an
 * instance but instead leaving it unchanged and creating a new instance of ImmutableSet with the
 * desired changes.
*/
