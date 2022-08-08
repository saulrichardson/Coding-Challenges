//
// HW 10 -- P1
//

public class Sieve extends Thread {

  private final Buffer<Integer> in;

    public Sieve(Buffer<Integer> buf) {
       in= buf;
    }


    public void run() {
      try {
        //take out prime
        Integer first = in.delete();
        if (first >= 0) {
          System.out.println(first);
          Buffer<Integer> out = new Buffer<Integer>(5);
          Sieve ss = new Sieve(out);
          ss.start();
          int num = in.delete();
          while (num > 0) {
            //if num isn't divisible, add it to follower
            if (num % first != 0) {
              out.insert(num);
            }
            num = in.delete();
          }
          //insert negative number in the follower
          out.insert(num);
        }
    } catch(Exception e) {
      System.out.println("exception!");
    }
    }
    public static void main(String args[]) {
      int n = Integer.parseInt(args[0]);
      Buffer<Integer> bb = new Buffer<Integer>(5);
      Sieve ss = new Sieve(bb);
      ss.start();
      try {
        //give all numbers up to n to sieve
        for (int i = 2; i <= n; i++) {
          bb.insert(i);
        }
        bb.insert(-1);
      } catch (Exception e) {
        System.out.println("Exception!");
      }


    }

}
