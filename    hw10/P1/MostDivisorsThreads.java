
class MostDivisorsWorker extends Thread {
    private int lo;
    private int hi;
    private int maxD;
    private int numWithMaxD;


    public MostDivisorsWorker(int low, int high) {
      lo = low;
      hi = high;
    }

    public void run() {

      int maxDivisors = 1;  //best value so far
      int numWithMaxDivisors = 1;
      //look at all values within the range
      for (int j = lo; j <= hi; j++) {
        int divisorCount = numDivHelper(j);
        if (divisorCount > maxDivisors) {
          maxDivisors = divisorCount;
          numWithMaxDivisors = j;
        }
      }
      //set global variables to best solutionn found
      maxD = maxDivisors;
      numWithMaxD = numWithMaxDivisors;
    }
    public int getMostDivisors() {
      return maxD; // change me ...
    }

    public int getNumberWithMostDivisors() {
      return numWithMaxD;  // change me ...
    }

    //helper method to count number of divisors from 1 to input value
    public int numDivHelper(int valuee) {
      int divisorCount = 0;
      for (int i = 1; i <= valuee; i++) {
        if (valuee % i == 0) {
          divisorCount++;
        }
      }
      return divisorCount;
    }
  }

public class MostDivisorsThreads {
  public static void main(String[] args) throws Exception {
    long initialTime = System.currentTimeMillis();

    int n = Integer.parseInt(args[0]);
    int numThreads = Integer.parseInt(args[1]);

    // Take 1 as the first "best" number
    int maxDivisors = 1;
    int numWithMaxDivisors = 1;

    //array to hold each thread
    MostDivisorsWorker[] threads = new MostDivisorsWorker[numThreads];

    ///variable to track which thread currently constructing
    int threadCount = 0;
    int begin = 0;

    //in order to make divide a range of numbers as
    //evenly as possible for any combination of threads
    //n, each thread will work on at least n/numThreads
    //numbers. For cases where the ranges can't be evenly divided
    //across theads, the last thread will get an left over
    //numbers via the if statement below.

    while (threadCount <= numThreads - 1) {
      int l = begin + 1; //low value
	    int h = begin + (n/numThreads);  //high value

      //if we're at the last thread, then all the remaining
      //numbers need to in the range. For cases where numThreads
      //evenly divide n, each thread will work on same input size. But
      //if n can't be evenly divided, then the last thread will get
      //the leftover. This if statement will properly alloocate numbers
      // when user specifies a number of threads that doesn't evently divide
      //the input n.
      if (threadCount == numThreads - 1) {
        h = n;
      }

	    threads[threadCount] = new MostDivisorsWorker(l, h);
	    threads[threadCount].start();
	    begin = begin + (n/numThreads);
	    threadCount++;
    }


    for(int i = 0; i < numThreads; i++) {
      threads[i].join();
    }

    for(int i=0; i < numThreads; i++) {
      //comapre each threads solution
      if (threads[i].getMostDivisors() > maxDivisors) {
        maxDivisors = threads[i].getMostDivisors();
        numWithMaxDivisors = threads[i].getNumberWithMostDivisors();
      }
    }

    long endTime = System.currentTimeMillis();
    long elapseTime = endTime - initialTime;

    System.out.printf("The maximum number of divisors is %d\n", maxDivisors);
    System.out.printf("A number with that many divisors is %d\n", numWithMaxDivisors);
    System.out.printf("Computing that took %3.4g seconds\n", elapseTime / 1000.0);
  }
}
