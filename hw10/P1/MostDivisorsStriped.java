
class MostDivisorsStripedWorker extends Thread {
    private int lo;
    private int hi;
    private int maxD;
    private int numWithMaxD;
    private int st;


    public MostDivisorsStripedWorker(int low, int high, int step) {
      lo = low;
      hi = high;
      st = step;
    }

    public void run() {
      int maxDivisors = 1;
      int numWithMaxDivisors = 1;
      for (int j = lo; j <= hi; j = j + st) {
        int divisorCount = numDivHelper(j);
        if (divisorCount > maxDivisors) {
          maxDivisors = divisorCount;
          numWithMaxDivisors = j;
        }
      }
      maxD = maxDivisors;
      numWithMaxD = numWithMaxDivisors;
    }
    public int getMostDivisors() {
      return maxD;
    }

    public int getNumberWithMostDivisors() {
      return numWithMaxD;
    }

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

public class MostDivisorsStriped {
  public static void main(String[] args) throws Exception {
    long initialTime = System.currentTimeMillis();

    int n = Integer.parseInt(args[0]);
    int numThreads = Integer.parseInt(args[1]);

    // Take 1 as the first "best" number
    int maxDivisors = 1;
    int numWithMaxDivisors = 1;

    MostDivisorsStripedWorker[] threads = new MostDivisorsStripedWorker[numThreads];

    int lowww = 1;
    int highh = (n - numThreads) + 1;
    for (int i = 0; i < numThreads; i++) {
      threads[i] = new MostDivisorsStripedWorker(lowww, highh, numThreads);
	    threads[i].start();
      lowww++;
      highh++;
    }

    for(int i = 0; i < numThreads; i++) {
      threads[i].join();
    }

    for(int i=0; i < numThreads; i++) {
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
