HW 10 README
============

a. Number of Cores
4

b. Most Divisors

n = 25000 took 585 milliseconds or 0.585 seconds
n = 50000 took 2279 milliseconds or 2.279 seconds
n = 100000 took 9270 milliseconds or 9.27 seconds


c. A Parallel Version

n = 100000
Threads  Time   Speedup
1        11.49   ---
2        8.565   1.34x
4        5.085   2.32x
8        3.564   3.28x
16       3.449   3.33x


d. Striping vs. Blocking

n = 100000
Threads  Time   Speedup
1        11.22   ---
2        5.669   1.98x
4        3.071   3.65x
8        2.531   4.43x
16       2.494   4.58x

Compared to the earlier parallel version, the 'stripe' methods has a better speedup rate as you add more threads. Speedup is better because threads aren't waiting on each other.


e. Lots of Threads

Blocking:
n = 100000
Threads  Time   Speedup
1        10.93    ---
2        7.895    1.38
4        4.737    2.31
8        2.621    4.17
16       1.512    7.24
32       0.970    11.26
64       0.678    16.12


Striping
n = 100000
Threads  Time   Speedup
1        10.54    ---
2        5.325    1.98
4        2.851    3.69
8        1.531    6.88
16       0.995    10.59
32       0.692    15.23
64       0.419    25.15
