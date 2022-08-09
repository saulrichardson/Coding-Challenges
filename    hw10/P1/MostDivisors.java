public class MostDivisors {

  // Count the number of integer divisors of value,
  // including 1 and the value itself.
  public static int numDivisors(int value) {
    int divisorCount = 0;    
    for (int i = 1; i <= value; i++) { 
      if (value % i == 0) {
        divisorCount++;
      }
    }
    return divisorCount;
  }

  public static void main(String[] args) {

    long initialTime = System.currentTimeMillis();

    int n = Integer.parseInt(args[0]);

    // Take 1 as the first "best" number
    int maxDivisors = 1;  
    int numWithMaxDivisors = 1;   

    for (int i = 2;  i <= n;  i++) {
      int divisorCount = numDivisors(i);
      if (divisorCount > maxDivisors) {
        maxDivisors = divisorCount;
        numWithMaxDivisors = i;
      }
    }

    long endTime = System.currentTimeMillis();
    long elapseTime = endTime - initialTime;
    
    System.out.println("The maximum number of divisors is " + maxDivisors);
    System.out.println("A number with " + maxDivisors + " divisors is " + numWithMaxDivisors);
    System.out.println("Computing that took " + elapseTime + " milliseconds");
  }
}